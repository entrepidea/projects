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

#include "iborder.h"
#include <mutex>
#include "EPosixClientSocket.h"
#include "EPosixClientSocketPlatform.h"
#include <OrderState.h>
#include "log.h"
#include "scoreboard.h"
#include "msgq.h"
#include "oid.h"

using namespace std;
extern std::atomic<bool> g_shutdown;

mutex iborder::mtx_CANCELALL;

long m_orderId = -1; //definition is here
mutex oid_mtx;

iborder::iborder() :
    m_pClient(new EPosixClientSocket(this)), _state(ST_DISCONNECTED) {
  timeout.tv_sec = 0;
  timeout.tv_usec = 500 * 1000;
}

iborder::~iborder() {
}

bool iborder::connect(const char *host, unsigned int port, int clientId) {
  //TTPrint("[%s]Connecting to %s:%d clientId:%d\n", __FUNCTION__, host, port, clientId);
  bool bRes = m_pClient->eConnect(host, port, clientId);
  if (bRes) {
    //TTPrint("Connected to %s:%d clientId:%d\n", host, port, clientId);
    _state = ST_CONNECT;
  } else {
    //TTPrint("Cannot connect to %s:%d clientId:%d\n", host, port, clientId);
  }
  return bRes;
}

void iborder::disconnect() {
  m_pClient->eDisconnect();
  _state = ST_DISCONNECTED;
  //m_orderId = -1;
  TTPrint("[%s(%d)]TWS connection disconnected!\n", __FUNCTION__, __LINE__);
}

bool iborder::isConnected() const {
  return m_pClient->isConnected();
}

void iborder::currentTime(long time) {
}

void iborder::processMessages() {
  if (!periodic_heartbeat(5)) {
    disconnect();
    return;
  }
  switch (_state) {
  case ST_GETORDERID:
    reqIDs();
    break;
  case ST_READYTOORDER:
    Place_Monitor_Oder();
    break;
  case ST_PLACEORDER_ACK:
    break;
  case ST_CANCELORDER:
    cancelOrder(0); //TODO
    break;
  case ST_CANCELORDER_ACK:
    break;
  }

  if (m_pClient->fd() > 0) {
    fd_set readSet, writeSet, errorSet;
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
    } //// timeout
    if (ret < 0) {
      printf("error\n");
      disconnect();
      return;
    } // error
    if (m_pClient->fd() < 0) {
      printf("error\n");
      return;
    }
    if (FD_ISSET(m_pClient->fd(), &errorSet)) {
      printf("error\n");
      m_pClient->onError();
    } // error on socket
    if (m_pClient->fd() < 0) {
      return;
    }
    if (FD_ISSET(m_pClient->fd(), &writeSet)) {
      m_pClient->onSend();
    } // socket is ready for writing
    if (m_pClient->fd() < 0) {
      return;
    }
    if (FD_ISSET(m_pClient->fd(), &readSet)) {
      m_pClient->onReceive();
    } // socket is ready for reading
  }
}

///https://www.interactivebrokers.com/en/software/api/apiguide/java/updateportfolio.htm
void iborder::reqAccountUpdates(bool subscribe, const string& account_) {
  m_pClient->reqAccountUpdates(subscribe, account_);
}

void iborder::updateAccountValue(const string &key, const string &val,
    const string &currency, const string &accountName) {
  //printf("[%s]%s,%s,%s,%s\n", __FUNCTION__, key.c_str(),
  //  val.c_str(), currency.c_str(), accountName.c_str());
  sboard::R().acc.setvalue(key, val, currency);
  /*bool printinfo = false;
   if (key == "EquityWithLoanValue"){
   ibaccount::R().evl = atof(val.c_str());
   printinfo = true;
   }
   else if (key == "FullAvailableFunds-S"){
   double tmp = atof(val.c_str());
   ibaccount::R().availablefund = tmp;
   //if(tmp < ibaccount::R().evl * 0.1){/////if (tmp < 4000){/////
   if (tmp < 4000){/////if (tmp < 4000){/////
   TTPrint("[Warning]Available Fund(%.3f) not enough(<%.3f)! Disable Trade!!!\n", tmp, ibaccount::R().evl * 0.2);
   DISABLETRADE = true;
   }
   printinfo = true;
   }
   else if (key == "InitMarginReq"){
   ibaccount::R().initialmargin = atof(val.c_str());
   printinfo = true;
   }else if (key == "SMA"){
   ibaccount::R().sma = atof(val.c_str());
   printinfo = true;
   }
   if (printinfo){
   string dspStr = key + "=" + val + ",currency=" + currency + ",account=" + accountName;
   TTPrint("[%s]%s\n", __FUNCTION__, dspStr.c_str());
   }*/
}

void iborder::updatePortfolio(const Contract& contract, int _position,
    double marketPrice, double marketValue, double averageCost,
    double unrealizedPNL, double realizedPNL, const IBString& accountName) {
  /*char str[512];
   sprintf(str,"conId=%i symbol=%s secType=%s expiry=%s strike=%.3f right=%s multiplier=%s primaryExchange=%s"
   " currency=%s localSymbol=%s position=%i mktPrice=%.3f mktValue=%.3f avgCost=%.3f unrealizedPNL=%.3f realizedPNL=%.3f account=%s",
   contract.conId, contract.symbol.c_str(), contract.secType.c_str(), contract.expiry.c_str(),
   contract.strike, contract.right.c_str(), contract.multiplier.c_str(), contract.primaryExchange.c_str(),
   contract.currency.c_str(), contract.localSymbol.c_str(),
   _position, marketPrice, marketValue, averageCost, unrealizedPNL, realizedPNL, accountName.c_str());
   if (symbol==contract.symbol){
   myposition =_position;
   }
   TTPrint("[%s]%s\n",__FUNCTION__,str);*/
}

void iborder::reqGlobalCancel() {
  m_pClient->reqGlobalCancel();
  sboard::R().oid2cwstock.clear();
}

//1.from web/distance too long/tws cancel?
void iborder::cancelOrder(int oid) {
  TTPrintr("[%s]Cancel Order %ld\n", __FUNCTION__, (long )m_orderId);
  m_pClient->cancelOrder(oid);

  instrument* p = sboard::R().getCW(oid);
  p->ptobj->_tinfo.statetransfer(CANCELLED, p);
  //--(p->lorders.count);
  //sboard::R().eraseOrder(oid);
}

void iborder::cancelOrders(const string& symbol) {
  vector<Order*> v = sboard::R().getNonFillOrderPtr(symbol);
  instrument* p = nullptr;
  for (Order* i : v) {
    m_pClient->cancelOrder(i->orderId);
    //TTPrint("[%s]Cancel Order %ld\n", __FUNCTION__, v[i]);
    p = sboard::R().oid2cwstock[i->orderId];
    if (p)
      p->ptobj->_tinfo.statetransfer(API_CANCELLED, p);
    sboard::R().oid2cwstock.erase(i->orderId);
  }
  p->lorders.count = 0;
}

void iborder::cancelAllOrders() {
  lock_guard<mutex> _g(iborder::mtx_CANCELALL);
  vector<Order*> v = sboard::R().getNonFillOrderPtr();
  for (Order* i : v) {
    m_pClient->cancelOrder(i->orderId);
    TTPrint("[%s]Cancel Order %ld\n", __FUNCTION__, i->orderId);
    i->status = API_CANCELLED;
    instrument* p = sboard::R().oid2cwstock[i->orderId];
    p->ptobj->_tinfo.statetransfer(API_CANCELLED, p);
    p->lorders.count = 0;  //should be safe
  }
  while (!sboard::R().getNonFillOrderPtr().empty()) {
    msleep(100);
  }
  sboard::R().oid2cwstock.clear();
}

bool iborder::isAllOrdersCancelled() {
  return sboard::R().getNonFillOrderPtr().empty();
}

void iborder::modifyOrder_SameT(uint64_t oid, const string& symbol,
    double price, int quantity) {
  instrument* pc = SR(getCW(symbol));
  Order* po = pc->getOrder(oid);
  po->totalQuantity = abs(quantity);
  po->lmtPrice = price;
  po->action = (quantity > 0) ? "BUY" : "SELL";
  placeOrder(pc->c, *po);
}

void iborder::modifyOrder_SameT(uint64_t oid, double price, int quantity) {
  pair<Contract*, Order*> pco = SR(getCO(oid));
  Order* po = pco.second;
  if (ISREMOVED(po->status))
    return;
  po->totalQuantity = abs(quantity);
  po->lmtPrice = price;
  po->action = (quantity > 0) ? "BUY" : "SELL";
  placeOrder(*(pco.first), *po);
}

void iborder::modifyOrder_SameT(const string& symbol, double price,
    int quantity,
    bool modifylast) {
  instrument* pc = sboard::R().getCW(symbol);
  Order* po = nullptr;
  if (modifylast) {
    po = static_cast<Order*>(pc->lorders.tail());
  } else {
    po = static_cast<Order*>(pc->lorders.head());
  }
  po->totalQuantity = abs(quantity);
  po->lmtPrice = price;
  po->action = (quantity > 0) ? "BUY" : "SELL";
  placeOrder(pc->c, *po);
}

//This function is called after a successful connection to TWS.
void iborder::nextValidId(OrderId orderId) {
  if (orderId > m_orderId) {
    TTPrint("[client id=%d] next_valid_order_id = %ld\n", m_pClient->clientId(), orderId);
    m_orderId = orderId;
    _state = ST_READYTOORDER;
  }
}

void iborder::reqIDs() {
  static int tmp = 1;
  m_pClient->reqIds(tmp++);
}

//Keep calling this function
void iborder::Place_Monitor_Oder() {
  instrument* p = orderQ::R().pop();
  if (p != nullptr) {
    lcw.push_back(p);
    for (int i = 0; i < p->lorders.count; i++) {
      if (p->lorders.ords[i].status == NEWBORN) {
        //p->lorders.ords[i].orderId = m_orderId; ////
        placeOrder(p->c, p->lorders.ords[i]);
      }
    }
  }

  // Monitor and Replace Orders if necessary
  // Enhancement: should be triggered by bid/ask change, now it's busy waiting
  for (instrument* p : lcw) {
    const mktinfo& mkd = p->_mkdata;
    if (isLordsEmpty(p->lorders)) {
      continue;
    }
    for (int i = 0; i < p->lorders.count; i++) {
      Order* po = &p->lorders.ords[i];
      switch (po->status) {
      case NEWBORN:
        break;
      case PENDING_SUBMIT:
        break;
      case PENDING_CANCEL:
        break;
      case PRESUBMITTED:
      case SUBMITTED: {
        if (po->orderType == "LMT") {  //only check limit order
          if (po->action == "SELL") { //如果ask价格产生了变化
            if (po->lmtPrice != mkd.ask) {
              po->distance = po->orignalPrice - mkd.ask; ////
              //TTPrint("[%s]SELL<%s>distance:%.2f,orignalPrice=%.2f,ask=%.2f\n", __FUNCTION__,
              //  p->c.symbol.c_str(), porder->distance, porder->orignalPrice, _rtinfo.ask);
              if (po->distance > po->allowedMove) {
                if (po->distance > (2 * po->allowedMove)) {
                  TTPrint("[%s]<%s>too long distance - dist:%.2f,allowedmove:%.2f\n", __FUNCTION__,
                  p->c.symbol.c_str(), (double)po->distance, (double)po->allowedMove);
                  cancelOrder(po->orderId);
                }
              } else {
                po->lmtPrice = mkd.ask; ////
                placeOrder(p->c, *po);
              }
            }
          } else if (po->action == "BUY") { //如果bid价格产生了变化
            if (po->lmtPrice != mkd.bid) {
              po->distance = mkd.bid - po->orignalPrice; ////
              //TTPrint("[%s]BUY<%s>distance:%.2f,orignalPrice=%.2f,bid=%.2f\n", __FUNCTION__,
              //  p->c.symbol.c_str(), porder->distance, porder->orignalPrice, _rtinfo.bid);
              if (po->distance > po->allowedMove) {
                if (po->distance > (2 * po->allowedMove)) {
                  TTPrint("[%s]<%s>too long distance - dist:%.2f,allowedmove:%.2f\n", __FUNCTION__,
                  p->c.symbol.c_str(), po->distance, po->allowedMove);
                  cancelOrder(po->orderId);
                }
              } else {
                po->lmtPrice = mkd.bid; ////
                placeOrder(p->c, *po);
              }
            }
          }
        } else {}
      }break;
      case CANCELLED:break;
      case INACTIVE:break;
      case API_PENDING:break;
      case API_CANCELLED:break;
      case FILLED:break;
    }
  }
}
//////////////////////////////////////////////////////////////////////////
}

void iborder::placeOrder(const Contract& contract, const Order& o) {
  switch (o.status) {
  case NEWBORN:
    //++m_orderId;//TODO - should not add 1 every time, it can be replacing order
  case PRESUBMITTED:
  case SUBMITTED: {
    //order.lmtPrice不适用于combo order
    if (o.orderType == "LMT") {
      TTPrint("[%s]%ld,%s,%ld,%s,%.2f,LMT\n", __FUNCTION__,
      o.orderId, o.action.c_str(), o.totalQuantity, contract.symbol.c_str(),
      o.lmtPrice);
    }
    //_state = ST_PLACEORDER_ACK;
    m_pClient->placeOrder(o.orderId, contract, o);//order id???

    instrument* pcw = sboard::R().getCW(contract.symbol);
    Order* po = pcw->getOrder(o.orderId);
    po->status = PENDING_SUBMIT;
    sboard::R().oid2cwstock[o.orderId] = pcw;

    //https://www.interactivebrokers.com/en/software/api/apiguide/c/reqids.htm
  }break;

  case PENDING_SUBMIT:break;
  case PENDING_CANCEL:break;
  case CANCELLED:break;
  case INACTIVE:break;
  case API_PENDING:break;
  case API_CANCELLED:break;
  case FILLED:break;
  default:SENTOSAERROR;break;
}
if (o.orderType == "LMT") {
  TTPrint("[%s]Placing Order %ld: %s %ld %s@%.2f,T=%s\n", __FUNCTION__,
  o.orderId, o.action.c_str(), o.totalQuantity, contract.symbol.c_str(),
  o.lmtPrice, o.orderType.c_str());
} else {
  TTPrint("[%s]Placing Order %ld: %s %ld %s,T=%s\n", __FUNCTION__,
  o.orderId, o.action.c_str(), o.totalQuantity, contract.symbol.c_str(),
  o.orderType.c_str());
}
}

static mutex orderStatus_mtx;
///https://www.interactivebrokers.com/en/software/api/apiguide/java/orderstatus.htm
void iborder::orderStatus(OrderId oid, const IBString &status, int filled,
    int remaining, double avgFillPrice, int permId, int parentId,
    double lastFillPrice, int clientId, const IBString& whyHeld) {
  lock_guard<mutex> g(orderStatus_mtx);

  char buf[512] = { };
  instrument* pcw = sboard::R().getCW(oid);
  if (pcw) {
    TTPrint("[%s]<%s>oid=%lu,status=%s,filled=%d,remain=%d,fillP=%.3f,lstfillP=%.3f\n",
    __FUNCTION__, pcw->c.symbol.c_str(), oid, status.c_str(), filled,
    remaining,avgFillPrice,lastFillPrice);

    ORDERSTATUS st = getST(status);
    Order* po = pcw->getOrder(oid);
    if (po) {
      // 1. Update order status and info
      switch(st) {
        /*case NEWBORN:break;
         case PENDING_SUBMIT:break;
         case PENDING_CANCEL:break;
         case PRESUBMITTED:break;*/
        case SUBMITTED: {
          if (filled>0 && remaining>0 &&
          remaining!=po->remaining && filled!=po->filled)
          { // half fill - update tradeinfo
            po->filled = filled;
            po->remaining = remaining;
            po->lastFillPrice = lastFillPrice;
            po->avgFillPrice = avgFillPrice;
            snprintf(buf, 512, "<%s>status=%s,filled=%d,remaining=%d,lastFillPrice=%.2f",
            pcw->c.symbol.c_str(), status.c_str(), filled, remaining, lastFillPrice);
            TTPrint("%s\n", buf);
            //pcw->ptobj->_tinfo.halfFillUpdate(filled,lastFillPrice);
          }
        }break;
        case API_CANCELLED:
        case CANCELLED: { //update tradeinfo
          //sboard::R().eraseOrder(oid);
        }break;
        case INACTIVE:break;
        case API_PENDING:break;
        case FILLED: {
          // NOTE： FILLED orders are removed from lorders in algo threads!!
          //if (remaining==0){
          //  sboard::R().eraseOrder(oid);
          //}
          /*snprintf(buf, 512,
           "<%s>status=%s,filled=%d,remaining=%d,lastFillPrice=%.2f,%s",
           pcw->c.symbol.c_str(), status.c_str(), filled, remaining,
           lastFillPrice, po->action.c_str());
           TTPrint("%s\n", buf);
           int quantity = po->action=="BUY" ? filled : -filled;
           snprintf(buf, 512,
           "insert into transaction(s,q,p,oid,dt)VALUES('%s',%d,%.3f,%lu,'%s')",
           pcw->c.symbol.c_str(), quantity, avgFillPrice, oid, ymdhms().c_str());
           insertARow(buf);*/
        }break;
        //case REMOVED:break;
      }
      po->status = st;
      // 2. update tradeinfo
      pcw->ptobj->_tinfo.statetransfer(st, pcw);
      // 3. remove cancelled or filled order
      /*if (po->status == CANCELLED ||
       po->status == FILLED ||
       po->status == API_CANCELLED)
       {
       po->status = REMOVED;
       }*/
    }
  }
}

void iborder::reqOpenOrdersOfThisClient() {
  m_pClient->reqOpenOrders();
}

void iborder::openOrder(OrderId oid, const Contract& contract,
    const Order& order, const OrderState& ostat) {
  // Will be called when there is existing order before starting sentosa
  TTPrintr("[%s](%d)%lu,%s\n", __FUNCTION__, __LINE__, oid,
      ostat.status.c_str());
}

void iborder::openOrderEnd() {
  TTPrintr("[%s][%d]\n", __FUNCTION__, __LINE__);
}

void iborder::managedAccounts(const string& accountsList) {
  TTPrint("[%s]client_id=%d,the managed account is:[%s]\n",
  __FUNCTION__, m_pClient->clientId(), accountsList.c_str());
  if (CConfig::R().account != accountsList) {
    printf("ERROR:Config account %s does not match IB account %s!\n",
    CConfig::R().account.c_str(), accountsList.c_str());
    disconnect();
    g_shutdown = true;
    //exit(1);
  }
}

void iborder::tickPrice(TickerId tickerId, TickType field, double price,
    int canAutoExecute) {
}
void iborder::tickSize(TickerId tickerId, TickType field, int size) {
}
void iborder::tickOptionComputation(TickerId tickerId, TickType field,
    double impliedVol, double delta, double optPrice, double pvDividend,
    double gamma, double vega, double theta, double undPrice) {
}
void iborder::tickGeneric(TickerId tickerId, TickType field, double value) {
}
void iborder::tickString(TickerId tickerId, TickType field,
    const IBString& value) {
}
void iborder::tickEFP(TickerId tickerId, TickType field, double basisPoints,
    const IBString& formattedBasisPoints, double totalDividends, int holdDays,
    const IBString& futureExpiry, double dividendImpact,
    double dividendsToExpiry) {
}
//void OrderRobot::openOrder( OrderId orderId, const Contract&, const Order&, const OrderState& ostate) {}
void iborder::winError(const IBString &str, int lastError) {
}
void iborder::connectionClosed() {
  printf("connectionClosed()\n");
}
void iborder::updateAccountTime(const IBString& timeStamp) {
}
void iborder::accountDownloadEnd(const IBString& accountName) {
}
void iborder::bondContractDetails(int reqId,
    const ContractDetails& contractDetails) {
}
void iborder::contractDetailsEnd(int reqId) {
}
void iborder::execDetails(int reqId, const Contract& contract,
    const Execution& execution) {
}
void iborder::execDetailsEnd(int reqId) {
}

void iborder::updateMktDepth(TickerId id, int position, int operation, int side,
    double price, int size) {
}
void iborder::updateMktDepthL2(TickerId id, int position, IBString marketMaker,
    int operation, int side, double price, int size) {
}
void iborder::updateNewsBulletin(int msgId, int msgType,
    const IBString& newsMessage, const IBString& originExch) {
}
//void OrderRobot::managedAccounts( const IBString& accountsList) {}
void iborder::receiveFA(faDataType pFaDataType, const IBString& cxml) {
}
void iborder::historicalData(TickerId reqId, const IBString& date, double open,
    double high, double low, double close, int volume, int barCount, double WAP,
    int hasGaps) {
}
void iborder::scannerParameters(const IBString &xml) {
}
void iborder::scannerData(int reqId, int rank,
    const ContractDetails &contractDetails, const IBString &distance,
    const IBString &benchmark, const IBString &projection,
    const IBString &legsStr) {
}
void iborder::scannerDataEnd(int reqId) {
}
void iborder::realtimeBar(TickerId reqId, long time, double open, double high,
    double low, double close, long volume, double wap, int count) {
}
void iborder::fundamentalData(TickerId reqId, const IBString& data) {
}
void iborder::deltaNeutralValidation(int reqId, const UnderComp& underComp) {
}
void iborder::tickSnapshotEnd(int reqId) {
}
void iborder::marketDataType(TickerId reqId, int marketDataType) {
}
void iborder::commissionReport(const CommissionReport& commissionReport) {
}
void iborder::position(const IBString& account, const Contract& contract,
    int position) {
}
void iborder::positionEnd() {
}
void iborder::accountSummary(int reqId, const IBString& account,
    const IBString& tag, const IBString& value, const IBString& curency) {
}
void iborder::accountSummaryEnd(int reqId) {
}
void iborder::reqRealTimeBars(TickerId id, const Contract &contract,
    int barSize, const IBString &whatToShow, bool useRTH) {
}
void iborder::cancelRealTimeBars(TickerId tickerId) {
}

//https://www.interactivebrokers.com/en/software/api/apiguide/tables/api_message_codes.htm
void iborder::error(const int id, const int errorCode,
    const IBString errorString) {
  if (errorCode == 2108 || errorCode == 2104 || errorCode == 2106) {
    return;
  }
  TTPrint("[%s](%d)id=%d,eCode=%d,msg:%s\n", __FUNCTION__, __LINE__, id,
  errorCode, errorString.c_str());
  if (id == -1 && errorCode == 1100) { // if "Connectivity between IB and TWS has been lost"
    disconnect();
  }
}
