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

#ifndef SENTOSA_IBORDER_H
#define SENTOSA_IBORDER_H

#include <mutex>
#include <sentosaconfig.h>
#include <EWrapper.h>
#include "heartbeat.h"

using std::mutex;
using std::string;
using std::list;

class EPosixClientSocket;
class instrument;

enum State {
  ST_DISCONNECTED,
  ST_CONNECT,
  ST_GETORDERID,
  ST_READYTOORDER,
  ST_PLACEORDER_ACK,
  ST_CANCELORDER,
  ST_CANCELORDER_ACK
};

class iborder: public EWrapper, public IBHeartbeat {
  std::unique_ptr<EPosixClientSocket> m_pClient;
  struct timeval timeout;
  list<instrument*> lcw;
public:

  State _state;

  double initialmargin;
  double availablefund;

  iborder();
  ~iborder();

  void processMessages();

  //https://www.interactivebrokers.com/en/software/api/apiguide/java/reqaccountupdates.htm
  void reqAccountUpdates(bool subscribe/* = true*/,
      const string& account/* = SENTOSA_ACCOUNT*/);

  /*Modifying an Order
   To modify an order using the API, resubmit the order you want to modify using the same order
   id, but with the price or quantity modified as required. Only certain fields such as price or
   quantity can be altered using this method. If you want to change the order type or action, you
   will have to cancel the order and submit a new order.
   */
  void modifyOrder_SameT(uint64_t, const string&, double, int);
  void modifyOrder_SameT(uint64_t, double, int);
  void modifyOrder_SameT(const string&, double, int);
  void modifyOrder_SameT(const string&, double, int, bool modifylast = true);

  bool connect(const char * host, unsigned int port, int clientId = 0);
  void disconnect();
  bool isConnected() const;
  void Place_Monitor_Oder();
  void PlaceBlockOrder();
  void placeOrder(const Contract& contract, const Order& order);
  void reqIDs();

  void managedAccounts(const string& accountsList);

  // Cancel Order
  /*Use this function to cancel all open orders globally.
   It cancels both API and TWS open orders.
   If the order was created in TWS, it also gets canceled.
   If the order was initiated in the API, it also gets canceled.*/
  void reqGlobalCancel();
  void cancelOrder(int oid);
  void cancelOrders(const string& symbol);
  //cancelAllOrders is not reentrant!
  static mutex mtx_CANCELALL;
  void cancelAllOrders();
  bool isAllOrdersCancelled(); //TODO

  /*Call this function to request the open orders that were placed from this client.
   Each open order will be fed back through the openOrder() and orderStatus() functions on the EWrapper.

   Note: The client with a clientId of 0 will also receive the TWS-owned open orders.
   These orders will be associated with the client and a new orderId will be generated.
   This association will persist over multiple API and TWS sessions.*/
  void reqOpenOrdersOfThisClient();
  /*Call this function to request the open orders placed from all clients and also from TWS.
   Each open order will be fed back through the openOrder() and orderStatus() functions on the EWrapper.
   Note:  No association is made between the returned orders and the requesting client.*/
  void reqAllOpenOrders();
  /*Call this function to request that newly created TWS orders be implicitly associated with the client.
   When a new TWS order is created, the order will be associated with the client,
   and fed back through the openOrder() and orderStatus() functions on the EWrapper.
   Note:  This request can only be made from a client with clientId of 0.
   If set to TRUE, newly created TWS orders will be implicitly associated with the client.
   If set to FALSE, no association will be made.*/
  void reqAutoOpenOrders(bool);
  void openOrder(OrderId orderId, const Contract&, const Order&,
      const OrderState&);
  void openOrderEnd();

  //https://www.interactivebrokers.com/en/software/api/apiguide/c/exerciseoptions.htm
  void exerciseOptions(TickerId id, const Contract &contract,
      int exerciseAction, int exerciseQuantity, const IBString &account,
      int override);

  /*
   It is possible that orderStatus() may return duplicate messages.
   It is essential that you filter the message accordingly.
   https://www.interactivebrokers.com/en/software/api/apiguide/c/orderstatus.htm
   https://www.interactivebrokers.com/en/software/api/apiguide/tables/order_status_for_partial_fills.htm
   */
  void orderStatus(OrderId orderId, const IBString &status, int filled,
      int remaining, double avgFillPrice, int permId, int parentId,
      double lastFillPrice, int clientId, const IBString& whyHeld);

public:
  // events
  void tickPrice(TickerId tickerId, TickType field, double price,
      int canAutoExecute);
  void tickSize(TickerId tickerId, TickType field, int size);
  void tickOptionComputation(TickerId tickerId, TickType field,
      double impliedVol, double delta, double optPrice, double pvDividend,
      double gamma, double vega, double theta, double undPrice);
  void tickGeneric(TickerId tickerId, TickType field, double value);
  void tickString(TickerId tickerId, TickType field, const IBString& value);
  void tickEFP(TickerId tickerId, TickType field, double basisPoints,
      const IBString& formattedBasisPoints, double totalDividends, int holdDays,
      const IBString& futureExpiry, double dividendImpact,
      double dividendsToExpiry);

  void winError(const IBString &str, int lastError);
  void connectionClosed();
  void updateAccountValue(const IBString& key, const IBString& val,
      const IBString& currency, const IBString& accountName);

  void updatePortfolio(const Contract& contract, int position,
      double marketPrice, double marketValue, double averageCost,
      double unrealizedPNL, double realizedPNL, const IBString& accountName);
  void updateAccountTime(const IBString& timeStamp);
  void accountDownloadEnd(const IBString& accountName);
  void nextValidId(OrderId orderId);
  void contractDetails(int reqId, const ContractDetails& contractDetails) {
  }
  void bondContractDetails(int reqId, const ContractDetails& contractDetails);
  void contractDetailsEnd(int reqId);
  void execDetails(int reqId, const Contract& contract,
      const Execution& execution);
  void execDetailsEnd(int reqId);
  void error(const int id, const int errorCode, const IBString errorString);
  void updateMktDepth(TickerId id, int position, int operation, int side,
      double price, int size);
  void updateMktDepthL2(TickerId id, int position, IBString marketMaker,
      int operation, int side, double price, int size);
  void updateNewsBulletin(int msgId, int msgType, const IBString& newsMessage,
      const IBString& originExch);
  void receiveFA(faDataType pFaDataType, const IBString& cxml);
  void historicalData(TickerId reqId, const IBString& date, double open,
      double high, double low, double close, int volume, int barCount,
      double WAP, int hasGaps);
  void scannerParameters(const IBString &xml);
  void scannerData(int reqId, int rank, const ContractDetails &contractDetails,
      const IBString &distance, const IBString &benchmark,
      const IBString &projection, const IBString &legsStr);
  void scannerDataEnd(int reqId);
  void realtimeBar(TickerId reqId, long time, double open, double high,
      double low, double close, long volume, double wap, int count);
  void currentTime(long time);
  void fundamentalData(TickerId reqId, const IBString& data);
  void deltaNeutralValidation(int reqId, const UnderComp& underComp);
  void tickSnapshotEnd(int reqId);
  void marketDataType(TickerId reqId, int marketDataType);
  void commissionReport(const CommissionReport& commissionReport);
  void position(const IBString& account, const Contract& contract,
      int position);
  void positionEnd();
  void accountSummary(int reqId, const IBString& account, const IBString& tag,
      const IBString& value, const IBString& curency);
  void accountSummaryEnd(int reqId);

  void reqRealTimeBars(TickerId id, const Contract &contract, int barSize,
      const IBString &whatToShow, bool useRTH);
  void cancelRealTimeBars(TickerId tickerId);

};

#endif
