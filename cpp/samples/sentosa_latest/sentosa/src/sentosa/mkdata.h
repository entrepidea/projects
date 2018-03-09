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

#ifndef SENTOSA_MKDATA_H
#define SENTOSA_MKDATA_H

#include <sentosaconfig.h>
#include <EWrapper.h>
#include <cband.h>
#include <scoreboard.h>
#ifndef  __linux__
#include <WinSock2.h>
#endif
#include <heartbeat.h>

#include <nanomsg/nn.h>
#include <nanomsg/pubsub.h>

using std::string;
using std::vector;

class EPosixClientSocket;

enum MKState {
  MK_DISCONNECTED,
  MK_CONNECT,
  MK_ACCOUNT,
  MK_ACCOUNTACK,
  MK_REQCONTRACT,
  MK_REQCONTRACT_ACK,
  MK_REQREALTIMEDATA,
  MK_REQREALTIMEDATAACK,
  MK_STOP
};

const static int OPERATION_INSERT = 0;
const static int OPERATION_UPDATE = 1;
const static int OPERATION_DELETE = 2;

const static char MKDEPTH_OPS[3] = { '+', 'u', '-' };

const static int SIDE_ASK = 0;
const static int SIDE_BID = 1;

///market data's category
/// - tick data
/// - real time bar data
/// - market depth
enum MKDMODECAT {
  TICKBAR = 0, DEPTH
};

class mkdata: public EWrapper, public IBHeartbeat {
  std::unique_ptr<EPosixClientSocket> m_pClient;
  struct timeval timeout;

  // time -> wap, index is reqid-BARBASEINDEX
  // [123232,1.2],[121212,2.3][...]...
  vector<PLD> vpld;

  void sendmq(const string& str);
  void sendmq(const char* str);
  void sendstr(const string& symbol, TickType field, const char* value);
  void sendint(const string& symbol, TickType field, long value);
  void sendflo(const string& symbol, TickType field, double value);
  int sock;
  int eid;

public:
  //vector<string> accounts;
      MKState _state;
      MKDMODECAT _mode;

  // This makes sure the zero position tobj also gets updated
      void syncZeroPositionTOBJ();
      map<string,string> sym2HENRYPORT;
      time_t t4syncZeroPositionTOBJ=0;

      mkdata();
      ~mkdata();

      void processMessages();
      bool connect(const char * host, unsigned int port, int clientId = 0);
      void disconnect();
      bool isConnected() const;
      void managedAccounts(const string& accountsList);
      void reqCurrentTime();
  //https://www.interactivebrokers.com/en/software/api/apiguide/tables/generic_tick_types.htm
      void ReqMkData();
      void ReqMkDepth();
      void CancelMkData(TickerId reqId);
      void cancelMktDepth(TickerId reqId);
      void cancelOrder(int oid);
      void reqAccountUpdates(bool subscribe, const string& account);
      void reqContractDetails();
      void requestHistData();
      void setstate(CWTYPE cwtp, int pos);
      void reqHistoricalData(TickerId id, const Contract &contract,
          const IBString &endDateTime, const IBString &durationStr,
          const IBString &barSizeSetting, const IBString &whatToShow,
          int useRTH, int formatDate, const TagValueListSPtr& chartOptions);
      void marketDataType(TickerId reqId, int marketDataType);
      void scannerParameters(const IBString &xml);
      void scannerData(int reqId, int rank, const ContractDetails &contractDetails,
          const IBString &distance, const IBString &benchmark, const IBString &projection,
          const IBString &legsStr);
      void scannerDataEnd(int reqId);
      void updateNewsBulletin(int msgId, int msgType, const IBString& newsMessage, const IBString& originExch);
      void accountSummary(int reqId, const IBString& account,
          const IBString& tag, const IBString& value, const IBString& curency);
      void accountSummaryEnd(int reqId);
      void historicalData(TickerId reqId, const IBString& date, double open, double high,
          double low, double close, int volume, int barCount, double WAP, int hasGaps);
      void tickPrice(TickerId tickerId, TickType field, double price, int canAutoExecute);
      void tickSize(TickerId tickerId, TickType field, int size);
      void tickOptionComputation(TickerId tickerId, TickType field, double impliedVol,
          double delta, double optPrice, double pvDividend, double gamma,
          double vega, double theta, double undPrice);
      void tickGeneric(TickerId tickerId, TickType field, double value);
      void tickString(TickerId tickerId, TickType field, const IBString& value);
      void tickEFP(TickerId tickerId, TickType field, double basisPoints,
          const IBString& formattedBasisPoints, double totalDividends,
          int holdDays, const IBString& futureExpiry,
          double dividendImpact, double dividendsToExpiry);
      void updateAccountValue(const IBString& key, const IBString& val,
          const IBString& currency, const IBString& accountName);
      void updatePortfolio(const Contract& contract, int position, double marketPrice,
          double marketValue, double averageCost, double unrealizedPNL,
          double realizedPNL, const IBString& accountName);
      void contractDetails(int reqId, const ContractDetails& contractDetails);
      void contractDetailsEnd(int reqId);
      void error(const int id, const int errorCode, const IBString errorString);
      void updateMktDepth(TickerId id, int position, int operation, int side,
          double price, int size);
      void updateMktDepthL2(TickerId id, int position, IBString marketMaker, int operation,
          int side, double price, int size);
      void cancelRealTimeBars(TickerId tickerId);

  //https://www.interactivebrokers.com/en/software/api/apiguide/c/realtimebar.htm
      void realtimeBar(TickerId reqId, long time, double open, double high,
          double low, double close, long volume, double wap, int count);

    public:
      void receiveFA(faDataType pFaDataType, const IBString& cxml) {}

      void currentTime(long time) {}
      void fundamentalData(TickerId reqId, const IBString& data) {}
      void deltaNeutralValidation(int reqId, const UnderComp& underComp) {}
      void tickSnapshotEnd(int reqId) {}
      void commissionReport(const CommissionReport& commissionReport) {}
      void position(const IBString& account, const Contract& contract, int position) {}
      void positionEnd() {}
      void orderStatus(OrderId orderId, const IBString &status, int filled,
          int remaining, double avgFillPrice, int permId, int parentId,
          double lastFillPrice, int clientId, const IBString& whyHeld) {}
      void openOrder(OrderId orderId, const Contract&, const Order&, const OrderState&) {}
      void openOrderEnd() {}
      void winError(const IBString &str, int lastError) {}
      void connectionClosed() {printf("connectionClosed()\n");}
      void execDetails(int reqId, const Contract& contract, const Execution& execution) {}
      void execDetailsEnd(int reqId) {}
      void nextValidId(OrderId orderId) {}
      void accountDownloadEnd(const IBString& accountName) {}
      void updateAccountTime(const IBString& timeStamp) {}
      void bondContractDetails(int reqId, const ContractDetails& contractDetails) {}
    };

#endif
