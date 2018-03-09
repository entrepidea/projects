// Sentosa - An Automatic Algorithmic Trading System
// Website: http://www.quant365.com
// Author: Henry Fuheng Wu (wufuheng AT gmail.com)
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

#include "mkdata.h"

#include <mutex>

#include <sentosaconfig.h>
#include <gconfig.h>
#include <EPosixClientSocket.h>
#include <EPosixClientSocketPlatform.h>
#include <log.h>
#include <util.h>

using namespace std;

mkdata::mkdata() :
    m_pClient(new EPosixClientSocket(this)), _state(MK_DISCONNECTED), _mode(
        TICKBAR) {
  timeout.tv_sec = 0;
  timeout.tv_usec = 0;
  vpld.resize(sboard::R().getStockSize());

  sock = nn_socket(AF_SP, NN_PUB);
  string endpoint = "tcp://*:" + CConfig::R().MKD_TO_ALGO_PORT;
  eid = nn_bind(sock, endpoint.c_str());
  last_time = time(0);
}

mkdata::~mkdata() {
  nn_shutdown(sock, eid);
  nn_close(sock);
}

bool mkdata::connect(const char *host, unsigned int port, int clientId) {
  TTPrint("[%s]Connecting to %s:%d clientId:%d\n", __FUNCTION__, host, port, clientId);
  bool bRes = m_pClient->eConnect(host, port, clientId);
  if (bRes) {
    TTPrint("Connected to %s:%d clientId:%d\n", host, port, clientId);
    _state = MK_CONNECT;
  } else {
    TTPrint("Cannot connect to %s:%d clientId:%d\n", host, port, clientId);
  }
  return bRes;
}

void mkdata::disconnect() {
  m_pClient->eDisconnect();
  _state = MK_DISCONNECTED;
  TTPrint("[%s(%d)]TWS connection disconnected!\n", __FUNCTION__, __LINE__);
}

bool mkdata::isConnected() const {
  return m_pClient->isConnected();
}

void mkdata::processMessages() {
  if (!periodic_heartbeat(5)) {
    disconnect();
    return;
  }
  switch (_state) {
  case MK_ACCOUNT: {
    string account = CConfig::R().account;
    reqAccountUpdates(true, account);
  }
    break;
  case MK_ACCOUNTACK:
    break;
  case MK_REQCONTRACT:
    reqContractDetails();
    break;
  case MK_REQCONTRACT_ACK:
    break;
    //case MK_REQHISTBAR:
    //  requestHistData();
    //  break;
  case MK_REQREALTIMEDATA:
    if (_mode == TICKBAR) {
      ReqMkData();
    } else if (_mode == DEPTH) {
      ReqMkDepth();
    }
    break;
  case MK_REQREALTIMEDATAACK:
    break;
  case MK_STOP:
    disconnect();
    break;
  }
  syncZeroPositionTOBJ();

  fd_set readSet, writeSet, errorSet;
  if (m_pClient->fd() >= 0) {
    FD_ZERO(&readSet);
    errorSet = writeSet = readSet;
    FD_SET(m_pClient->fd(), &readSet);
    if (!m_pClient->isOutBufferEmpty()) {
      FD_SET(m_pClient->fd(), &writeSet);
    }
    FD_CLR(m_pClient->fd(), &errorSet);
    int ret = select(m_pClient->fd() + 1, &readSet, &writeSet, &errorSet,
        &timeout);

    if (ret == 0) {
      return;
    }  // timeout
    if (ret < 0) {
      TTPrint("[%s(%d)]ERROR: TWS connection disconnected!\n",
      __FUNCTION__, __LINE__);
      disconnect();
      return;
    }  // error
    if (m_pClient->fd() < 0) {
      return;
    }
    if (FD_ISSET(m_pClient->fd(), &errorSet)) {
      m_pClient->onError();
    }  // error on socket
    if (m_pClient->fd() < 0) {
      return;
    }
    if (FD_ISSET(m_pClient->fd(), &writeSet)) {
      m_pClient->onSend();
    }  // socket is ready for writing
    if (m_pClient->fd() < 0) {
      return;
    }
    if (FD_ISSET(m_pClient->fd(), &readSet)) {
      m_pClient->onReceive();
    }  // socket is ready for reading
  }
}

void mkdata::updateAccountValue(const string &key, const string &val,
    const string &currency, const string &accountName) {
  TTPrint("[%s]%s,%s,%s,%s\n", __FUNCTION__,
  key.c_str(), val.c_str(), currency.c_str(), accountName.c_str());
}

/// Call this function per three minutes
void mkdata::updatePortfolio(const Contract& c, int _position,
    double marketPrice, double marketValue, double averageCost,
    double unrealizedPNL, double realizedPNL, const IBString& accountName) {
  if (t4syncZeroPositionTOBJ == 0) {
    t4syncZeroPositionTOBJ = str2time_t(ymdhms());
  }
  char str[512];
  sprintf(str, "%d,%.3f,%.3f", _position, averageCost, marketPrice);
  sym2HENRYPORT[c.symbol] = string(str);

  ///Note: after closing position, position is 0 and update will happen! bug!
  ///2015-01-06 13:45:42.263 [mkdata::updatePortfolio]<CYOU>,0,0.000,26.285
  ///Don't send message when position is 0
  if (_position != 0) {  //This is a bug! fixed!!
    if (c.secType == "STK") {
      sendstr(c.symbol, SEN_PORTFOLIO, str);
    } else if (c.secType == "OPT") {
      string optname = c.symbol + OPTION_SUFFIX; //TODO - Add expiry, strike etc
      sendstr(optname, SEN_PORTFOLIO, str);  //This position is contract number!
    }
    TTPrint("[%s]<%s>,%s\n", __FUNCTION__, c.symbol.c_str(), str);
  }

  if (_state < MK_REQCONTRACT) {
    _state = MK_REQCONTRACT;
  }
}

void mkdata::syncZeroPositionTOBJ() {
  time_t t = str2time_t(ymdhms());
  if (t4syncZeroPositionTOBJ != 0 && t - t4syncZeroPositionTOBJ > 5) {
    t4syncZeroPositionTOBJ = 0;
    for (int i = 0; i < sboard::R().equitycount; ++i) {
      string& s = sboard::R().cwstocks_[i].c.symbol;
      if (sym2HENRYPORT.find(s) == sym2HENRYPORT.end()) {
        string str = s + ",0";
        sendstr(s, SEN_PORTFOLIO, s.c_str());
      }
    }
  }
}

void mkdata::reqAccountUpdates(bool subscribe, const string& account) {
  m_pClient->reqAccountUpdates(subscribe, account);
  if (_state < MK_ACCOUNTACK) {
    _state = MK_ACCOUNTACK;
  }
}

void mkdata::reqContractDetails() {
  int sz = sboard::R().getStockSize();
  for (int i = 0; i < sz; ++i) {
    const Contract& c = sboard::R().getStockContract(i);
    m_pClient->reqContractDetails(i, c);
  }

  if (_state < MK_REQCONTRACT_ACK) {
    _state = MK_REQCONTRACT_ACK;
  }
}

void mkdata::contractDetails(int reqId,
    const ContractDetails &contractDetails) {
  sboard& sb = sboard::R();
  sboard::R().setStockConId(reqId, contractDetails.summary.conId);
  if (_mode == TICKBAR) {
    sendint(sboard::R().getStockContract(reqId).symbol, SEN_CONTID,
        contractDetails.summary.conId);
  }

  for (int i = 0; i < sboard::R().getStockSize(); ++i) {
    if (sboard::R().getStockContract(i).conId == 0) {
      return;
    }
  }
  //if (_state < MK_REQHISTBAR){_state = MK_REQHISTBAR;}
  if (_state < MK_REQREALTIMEDATA) {
    _state = MK_REQREALTIMEDATA;
  }
}

//This method is called once all contract details for a given request are received.
//This helps to define the end of an option chain.
void mkdata::contractDetailsEnd(int reqId) {
  const Contract& c = sboard::R().getStockContract(reqId);
  TTPrint("[%s]<%s>\n", __FUNCTION__, c.symbol.c_str());
}

void mkdata::requestHistData() {
}

void mkdata::historicalData(TickerId reqId, const IBString& date, double open,
    double high, double low, double close, int volume, int barCount, double WAP,
    int hasGaps) {
}

/*2014-12-08 09:02:41.763 [mkdata::error]id=7,eCode=321,msg:Error validating request:-'vd' : cause - Incorrect
 generic tick list of 100,101,104,106,162,165,221,225,233,236,256,258,411.  Legal ones for (STK) are:
 100(Option Volume),101(Option Open Interest),105(Average Opt Volume),106(Option Implied Volatility),
 107(Close Implied Volatility),125(Bond analytic data),165(Misc. Stats),166(CScreen),225(Auction),
 232/221(Mark Price),233(RTVolume),236(inventory),258/47(Fundamentals),291(Close Implied Volatility),
 293(TradeCount),294(TradeRate),295(VolumeRate),318(LastRTHTrade),370(ParticipationMonitor),
 370(ParticipationMonitor),377(CttTickTag),377(CttTickTag),381(IB Rate),384(RfqTickRespTag),
 384(RfqTickRespTag),387(DMM),388(Issuer Fundamentals),391(IBWarrantImpVolCompeteTick),
 407(FuturesMargins),411(rthistvol),428(Monetary Close Price),439(MonitorTickTag),
 439(MonitorTickTag),456/59(IBDividends),459(RTCLOSE),460(Bond Factor Multiplier),
 499(Fee and Rebate Rate),506(midptiv),511(hvolrt10 (per-underlying)),
 512/104(hvolrt30 (per-underlying)),513(hvolrt50 (per-underlying)),
 514(hvolrt75 (per-underlying)),515(hvolrt100 (per-underlying)),
 516(hvolrt150 (per-underlying)),517(hvolrt200 (per-underlying))*/
void mkdata::ReqMkData() {
  // 236 - shortable; 256 - inventory[error(x)]
  //static const char* gt = "100,101,104,106,165,221,225,233,236,258,411";
  static const char* gt =
      "100,101,104,105,106,107,165,221,225,233,236,258,293,294,295,318,411";
  //TTPrint("[%s]send req '%s' for %s(%d)\n", __FUNCTION__, gt.c_str(),contract.symbol.c_str(),contract.conId);
  uint64_t snum = sboard::R().getStockSize();
  for (int i = 0; i < snum; ++i) {
    const Contract& c = sboard::R().getStockContract(i);
    m_pClient->reqMktData(i, c, gt, false);
  }

  for (int i = BARBASEINDEX; i < BARBASEINDEX + snum; ++i) {
    const Contract& c = sboard::R().getStockContract(i - BARBASEINDEX);
    m_pClient->reqRealTimeBars(i, c, 5, "TRADES", true);
  }

  for (int i = OPTIONBASEINDEX;
      i < OPTIONBASEINDEX + sboard::R().getOptionSize(); ++i) {
    const Contract& c = sboard::R().getOptionContract(i - OPTIONBASEINDEX);
    m_pClient->reqMktData(i, c, gt, false);
  }

  _state = MK_REQREALTIMEDATAACK;

  //ReqMkDepth();
}

/*TWS currently limits users to a maximum of 3 distinct market depth requests.
 This same restriction applies to API clients, however API clients may make
 multiple market depth requests for the same security.*/
void mkdata::ReqMkDepth() {
  static const int IBLIMITMKDEPTHNUM = 3;
  for (int i = 0; i < min(sboard::R().getStockSize(), IBLIMITMKDEPTHNUM); ++i) {
    Contract c = sboard::R().getStockContract(i);
    c.exchange = "ISLAND";
    printf("[%s][%d]%s %s\n", __FUNCTION__, __LINE__, c.symbol.c_str(),
        c.exchange.c_str());
    m_pClient->reqMktDepth(i + 1000, c, 10);
  }
  _state = MK_REQREALTIMEDATAACK;
}

void mkdata::CancelMkData(TickerId reqId) {
  //TTPrint("Cancel market data %d\n", mid);
  m_pClient->cancelMktData(reqId);
}

void mkdata::cancelMktDepth(TickerId reqId) {
  //TTPrint("Cancel market data %d\n", mid);
  m_pClient->cancelMktDepth(reqId);
}

void mkdata::tickPrice(TickerId reqId, TickType field, double price,
    int canAutoExecute) {
  string symbol;
  if (reqId >= OPTIONBASEINDEX) {
    symbol = sboard::R().getOptionContract(reqId - OPTIONBASEINDEX).symbol;
    symbol += "_O";
  } else {
    symbol = sboard::R().getStockContract(reqId).symbol;
  }
  sendflo(symbol, field, price);
}

void mkdata::tickSize(TickerId reqId, TickType field, int size) {
  string symbol;
  if (reqId >= OPTIONBASEINDEX) {
    symbol = sboard::R().getOptionContract(reqId - OPTIONBASEINDEX).symbol;
    symbol += "_O";
  } else {
    symbol = sboard::R().getStockContract(reqId).symbol;
  }
  sendint(symbol, field, size);
}

void mkdata::tickGeneric(TickerId reqId, TickType field, double value) {
  string symbol;
  if (reqId >= OPTIONBASEINDEX) {
    symbol = sboard::R().getOptionContract(reqId - OPTIONBASEINDEX).symbol;
    symbol += "_O";
  } else {
    symbol = sboard::R().getStockContract(reqId).symbol;
  }
  sendflo(symbol, field, value);
}

void mkdata::tickString(TickerId reqId, TickType field, const IBString& value) {
  string symbol;
  if (reqId >= OPTIONBASEINDEX) {
    symbol = sboard::R().getOptionContract(reqId - OPTIONBASEINDEX).symbol;
    symbol += "_O";
  } else {
    symbol = sboard::R().getStockContract(reqId).symbol;
  }
  sendstr(symbol, field, value.c_str());
}

void mkdata::updateMktDepthL2(TickerId reqId, int position,
    IBString marketMaker, int operation, int side, double price, int size) {
  //TTPrint("[%s]\n",__PRETTY_FUNCTION__);
  const char* sidestr = (side == SIDE_BID) ? "BID_PRICE" : "ASK_PRICE";
  printf("%s %c %s %d %s %.3f %d\n",
      sboard::R().getStockContract(reqId - 1000).symbol.c_str(),
      MKDEPTH_OPS[operation], sidestr, position, marketMaker.c_str(), price,
      size);
}

void mkdata::updateMktDepth(TickerId reqId, int position, int operation,
    int side, double price, int size) {
  //TTPrint("[%s]\n",__PRETTY_FUNCTION__);
  const char* sidestr = (side == SIDE_BID) ? "BID_PRICE" : "ASK_PRICE";
  printf("%s %c %s %d %.3f %d\n",
      sboard::R().getStockContract(reqId - 1000).symbol.c_str(),
      MKDEPTH_OPS[operation], sidestr, position, price, size);
}

void mkdata::realtimeBar(TickerId reqId, long t, double open, double high,
    double low, double close, long volume, double wap, int count) {
  string symbol;
  uint64_t index = reqId - BARBASEINDEX;
  if (reqId >= BARBASEINDEX) {
    symbol = sboard::R().getStockContract(index).symbol;
  } else {
    return;
  }

  vpld[index].first = t;
  vpld[index].second = wap;
  /*if (CConfig::R().isdebug){
   printf("[%s](%s)%s,%ld,%.2f,%.2f,%.2f,%.2f,%ld,%.2f,%d\n", __FUNCTION__,
   time_t2str(t).c_str(), symbol.c_str(), reqId, open, high, low, close, volume, wap, count);
   }*/

  // wap of either pm or ps
  char tmp[128];
  sprintf(tmp, "%.2f:%.2f:%.2f:%.2f:%.2f:%ld", open, high, low, close, wap,
      volume);
  sendstr(symbol, SEN_RT5SBAR, tmp);

  instrument* cw = sboard::R().getCW(symbol);
  CWTYPE cwtp = cw->cwtp;
  bool canSendSpread = false;

  // spread of pm wap and ps wap
  if (cwtp == MASTER) {
    if (vpld[index + 1].first == t) {
      sendflo(symbol, SEN_5SWAPSPD, wap - vpld[index + 1].second);
    }
  }
  //
  /*else{
   if (vpld[index - 1].first == t){
   sendflo(symbol, SEN_5SWAPSPD, vpld[index - 1].second - wap);
   }
   }*/
}
void mkdata::cancelRealTimeBars(TickerId tickerId) {
}

void mkdata::cancelOrder(int oid) {
}
void mkdata::tickOptionComputation(TickerId reqId, TickType field,
    double impliedVol, double delta, double optPrice, double pvDividend,
    double gamma, double vega, double theta, double undPrice) {
  //TTPrint("[%s]\n",__PRETTY_FUNCTION__);
}

void mkdata::tickEFP(TickerId reqId, TickType field, double basisPoints,
    const IBString& formattedBasisPoints, double totalDividends, int holdDays,
    const IBString& futureExpiry, double dividendImpact,
    double dividendsToExpiry) {
  //TTPrint("[%s]\n",__PRETTY_FUNCTION__);
}
void mkdata::updateNewsBulletin(int msgId, int msgType,
    const IBString& newsMessage, const IBString& originExch) {
  //TTPrint("[%s]\n",__PRETTY_FUNCTION__);
}
void mkdata::managedAccounts(const IBString& _account) {
  TTPrint("[%s][client_id=%d]the managed account is:[%s]\n",
  __FUNCTION__,m_pClient->clientId(), _account.c_str());
  /*if (CConfig::R().account != _account){
   TTPrintr("Wrong account: %s", CConfig::R().account.c_str());
   exit(1);
   }*/
  //accounts = splitv2(_account, ',');
  //if (find(accounts.begin(),accounts.end(),)
  //TTPrint("[%s]\n",__PRETTY_FUNCTION__);
}
void mkdata::scannerParameters(const IBString &xml) {
  //TTPrint("[%s]\n",__PRETTY_FUNCTION__);
}
void mkdata::scannerData(int reqId, int rank,
    const ContractDetails &contractDetails, const IBString &distance,
    const IBString &benchmark, const IBString &projection,
    const IBString &legsStr) {
  //TTPrint("[%s]\n",__PRETTY_FUNCTION__);
}
void mkdata::scannerDataEnd(int reqId) {
  //TTPrint("[%s]\n",__PRETTY_FUNCTION__);
}
void mkdata::marketDataType(TickerId reqId, int marketDataType) {
  //TTPrint("[%s]\n",__PRETTY_FUNCTION__);
}

void mkdata::reqHistoricalData(TickerId id, const Contract &contract,
    const IBString &endDateTime, const IBString &durationStr,
    const IBString &barSizeSetting, const IBString &whatToShow, int useRTH,
    int formatDate, const TagValueListSPtr& chartOptions) {
}

//Error Code: https://www.interactivebrokers.com/en/software/api/apiguide/tables/api_message_codes.htm
void mkdata::error(const int id, const int errorCode,
    const IBString errorString) {
  if (errorCode == 2108 || errorCode == 2104 || errorCode == 2106) {
    return;
  }
  TTPrint("[%s]id=%d,eCode=%d,msg:%s\n", __FUNCTION__, id, errorCode, errorString.c_str());
  if (id == -1 && errorCode == 1100) { // if "Connectivity between IB and TWS has been lost"
    disconnect();
  } else if (errorCode == 103) {
    cancelOrder(id);
  } else if (errorCode == 326) {
    TTPrint("[%s(%d)]ClientId duplicated! bump up clientID and reconnect!!\n", __FUNCTION__, __LINE__);
    disconnect();
    connect(CConfig::R().IBHOST.c_str(),
    CConfig::R().IBPORT,
    CConfig::R().IB_CLIENT_ID++);
    //exit(0);

  }
}

void mkdata::sendmq(const string& msg) {
  int bytes = nn_send(sock, msg.c_str(), msg.size() + 1, 0);
  /*if (bytes != msg.size()){
   TTPrint("[%s(%d)]NANOMSG ERROR!\n", __FUNCTION__, __LINE__);
   }*/
}

void mkdata::sendmq(const char* msg) {
  int bytes = nn_send(sock, msg, strlen(msg) + 1, 0);
}

void mkdata::sendstr(const string& symbol, TickType field, const char* value) {
  if(strlen(value)>500){
    TTPrint("[%s(%d)]Warning: string too long!\n", __FUNCTION__, __LINE__);
    return;
  }
  char msg[512] = { };
  sprintf(msg, "%s|%d|%s", symbol.c_str(), field, value);
  sendmq(msg);
}

void mkdata::sendint(const string& symbol, TickType field, long value) {
  char msg[128] = { };
  sprintf(msg, "%s|%d|%ld", symbol.c_str(), field, value);
  sendmq(msg);
}

void mkdata::sendflo(const string& symbol, TickType field, double value) {
  char msg[128] = { };
  sprintf(msg, "%s|%d|%.2f", symbol.c_str(), field, value);
  sendmq(msg);
}
