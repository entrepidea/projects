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

#ifndef ewrapper_def
#define ewrapper_def

#include "CommonDefs.h"
#include "IBString.h"

//C:\sentosa\twintower\EWrapper.cpp

enum TickType : int;

// http://stackoverflow.com/questions/4391467/declare-array-in-c-header-and-define-it-in-cpp-file
// how to use: put "extern const char* TTField[TTSIZE];" in the cpp file
extern const char* TTField[];

struct Contract;
struct ContractDetails;
struct Order;
struct OrderState;
struct Execution;
struct UnderComp;
struct CommissionReport;

class EWrapper
{
public:
   virtual ~EWrapper() {};

   virtual void tickPrice( TickerId tickerId, TickType field, double price, int canAutoExecute) = 0;
   virtual void tickSize( TickerId tickerId, TickType field, int size) = 0;
   virtual void tickOptionComputation( TickerId tickerId, TickType tickType, double impliedVol, double delta,
	   double optPrice, double pvDividend, double gamma, double vega, double theta, double undPrice) = 0;
   virtual void tickGeneric(TickerId tickerId, TickType tickType, double value) = 0;
   virtual void tickString(TickerId tickerId, TickType tickType, const IBString& value) = 0;
   virtual void tickEFP(TickerId tickerId, TickType tickType, double basisPoints, const IBString& formattedBasisPoints,
	   double totalDividends, int holdDays, const IBString& futureExpiry, double dividendImpact, double dividendsToExpiry) = 0;
   virtual void orderStatus( OrderId orderId, const IBString &status, int filled,
	   int remaining, double avgFillPrice, int permId, int parentId,
	   double lastFillPrice, int clientId, const IBString& whyHeld) = 0;
   virtual void openOrder( OrderId orderId, const Contract&, const Order&, const OrderState&) = 0;
   virtual void openOrderEnd() = 0;
   virtual void winError( const IBString &str, int lastError) = 0;
   virtual void connectionClosed() = 0;
   virtual void updateAccountValue(const IBString& key, const IBString& val,
   const IBString& currency, const IBString& accountName) = 0;
   virtual void updatePortfolio( const Contract& contract, int position,
      double marketPrice, double marketValue, double averageCost,
      double unrealizedPNL, double realizedPNL, const IBString& accountName) = 0;
   virtual void updateAccountTime(const IBString& timeStamp) = 0;
   virtual void accountDownloadEnd(const IBString& accountName) = 0;
   virtual void nextValidId( OrderId orderId) = 0;
   virtual void contractDetails( int reqId, const ContractDetails& contractDetails) = 0;
   virtual void bondContractDetails( int reqId, const ContractDetails& contractDetails) = 0;
   virtual void contractDetailsEnd( int reqId) = 0;
   virtual void execDetails( int reqId, const Contract& contract, const Execution& execution) =0;
   virtual void execDetailsEnd( int reqId) =0;
   virtual void error(const int id, const int errorCode, const IBString errorString) = 0;
   virtual void updateMktDepth(TickerId id, int position, int operation, int side,
      double price, int size) = 0;
   virtual void updateMktDepthL2(TickerId id, int position, IBString marketMaker, int operation,
      int side, double price, int size) = 0;
   virtual void updateNewsBulletin(int msgId, int msgType, const IBString& newsMessage, const IBString& originExch) = 0;
   virtual void managedAccounts( const IBString& accountsList) = 0;
   virtual void receiveFA(faDataType pFaDataType, const IBString& cxml) = 0;
   virtual void historicalData(TickerId reqId, const IBString& date, double open, double high, 
	   double low, double close, int volume, int barCount, double WAP, int hasGaps) = 0;
   virtual void scannerParameters(const IBString &xml) = 0;
   virtual void scannerData(int reqId, int rank, const ContractDetails &contractDetails,
	   const IBString &distance, const IBString &benchmark, const IBString &projection,
	   const IBString &legsStr) = 0;
   virtual void scannerDataEnd(int reqId) = 0;
   virtual void realtimeBar(TickerId reqId, long time, double open, double high, double low, double close,
	   long volume, double wap, int count) = 0;
   virtual void currentTime(long time) = 0;
   virtual void fundamentalData(TickerId reqId, const IBString& data) = 0;
   virtual void deltaNeutralValidation(int reqId, const UnderComp& underComp) = 0;
   virtual void tickSnapshotEnd( int reqId) = 0;
   virtual void marketDataType( TickerId reqId, int marketDataType) = 0;
   virtual void commissionReport( const CommissionReport &commissionReport) = 0;
};

//enum的前向声明似乎没啥好处
enum TickType : int{
  BID_SIZE = 0,
  BID_PRICE,
  ASK_PRICE,
  ASK_SIZE,
  LAST_PRICE,
  LAST_SIZE,
  HIGH,
  LOW,
  VOLUME,
  CLOSE_PRICE,//前一天的close price
  BID_OPTION_COMPUTATION,
  ASK_OPTION_COMPUTATION,
  LAST_OPTION_COMPUTATION,
  MODEL_OPTION,
  OPEN_TICK,//开盘价
  LOW_13_WEEK,
  HIGH_13_WEEK,
  LOW_26_WEEK,
  HIGH_26_WEEK,
  LOW_52_WEEK,
  HIGH_52_WEEK,
  AVG_VOLUME,
  OPEN_INTEREST,
  OPTION_HISTORICAL_VOL,
  OPTION_IMPLIED_VOL, //0.67
  OPTION_BID_EXCH,
  OPTION_ASK_EXCH,
  OPTION_CALL_OPEN_INTEREST,
  OPTION_PUT_OPEN_INTEREST,
  OPTION_CALL_VOLUME,
  OPTION_PUT_VOLUME,
  INDEX_FUTURE_PREMIUM,//http://traderfeed.blogspot.sg/2007/12/stock-index-futures-premium-as.html
  BID_EXCH,
  ASK_EXCH,
  AUCTION_VOLUME,
  AUCTION_PRICE,
  AUCTION_IMBALANCE,
  MARK_PRICE,//Mark Price (used in TWS P&L computations)
  BID_EFP_COMPUTATION,
  ASK_EFP_COMPUTATION,
  LAST_EFP_COMPUTATION,
  OPEN_EFP_COMPUTATION,
  HIGH_EFP_COMPUTATION,
  LOW_EFP_COMPUTATION,
  CLOSE_EFP_COMPUTATION,
  //http://www.elitetrader.com/et/index.php?threads/timestamp-for-reqmktdata-reqmktdepth-in-ib-api.219207/
  //This tick represents "timestamp of the last Last tick" value in seconds (counted from 00:00:00 1 
  //Jan 1970 GMT). Value will be sent back by TWS (starting 872 release) in a response to regular 
  //market data subscription as long as client version is 32 or above (API 9.2)
  LAST_TIMESTAMP,
  SHORTABLE,
  FUNDAMENTAL_RATIOS,
  RT_VOLUME,//contains the last trade price, last trade size, last trade time, total volume, VWAP, and single trade flag.
  HALTED,
  BID_YIELD,
  ASK_YIELD,
  LAST_YIELD,
  CUST_OPTION_COMPUTATION,
  TRADE_COUNT,//trade count during the day
  TRADE_RATE,//number of trades in the past 60 seconds(regardless of the sizes of those trades)
  VOLUME_RATE,//volume rate per minute
  LAST_RTH_TRADE,
  NOT_SET,
  //////////////////////////////////////////////////////////////////////////
  SEN_CONTID,//59
  SEN_PORTFOLIO,
  SEN_ACCOUNT,
  SEN_RT5SBAR,
  SEN_5SWAPSPD,

  TTSIZE
};
//https://www.sierrachart.com/SupportBoard.php?ThreadID=342
//Sometime in 2011, Interactive Brokers updated its API to include a new tick type called "RTVolume". 
//This was added to help provide accurate tick volume data. I used it to create the custom data feed.
//I respectfully ask that your development team take a look at IB's updated API and the RTVolume tick
//type. It may help you improve the quality of IB's data more as you have already done with the 
//TrueRealTimeData option.

/*
Here is what I used to code the data feed:

reqMktData() - Call this function to request market data. The market data will be returned by the tickPrice() and tickString() events.

void reqMktData(TickerID id, const Contract &contract, CString genericTicklist, bool snapshot)
The String, generericTickList is passed as string "233"

1.Use event tickPrice() to retrieve the latest askprice (ticktype=1) and bidprice (ticktype=2).
2.Use event tickString() to retrieve rtVolume events (tickType=48). IB's documentation mistakenly says that RTvolume is passed by tickGeneric() but this is wrong. RTVolume is a string and is passed by the tickString() event.

Fields in struct s_IntradayRecord:

double DateTime.............RTVolume(lastprice, volume, dateTimeStamp, TotalVolume, VWAP, Single trade flag)
float Open .................RTVolume(lastprice, volume, dateTimeStamp, TotalVolume, VWAP, Single trade flag)
float High .................RTVolume(lastprice, volume, dateTimeStamp, TotalVolume, VWAP, Single trade flag)
float Low...................RTVolume(lastprice, volume, dateTimeStamp, TotalVolume, VWAP, Single trade flag)
float Close.................RTVolume(lastprice, volume, dateTimeStamp, TotalVolume, VWAP, Single trade flag)
unsigned long NumTrades.....1
unsigned long TotalVolume...RTVolume(lastprice, volume, dateTimeStamp, TotalVolume, VWAP, Single trade flag)
unsigned long BidVolume.....tickPrice() passing ticktype=2. If lastprice<=bidprice then BidVolume=volume else 0
unsigned long AskVolume.....tickPrice() passing ticktype=1. If lastprice>=askprice then AskVolume=volume else 0

BD
*/

#endif
