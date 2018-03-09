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

#ifndef eclient_def
#define eclient_def

#include "CommonDefs.h"
#include "IBString.h"

struct Contract;
struct Order;
struct ExecutionFilter;
struct ScannerSubscription;

class EClient
{
public:
   virtual ~EClient() {}
   virtual bool eConnect( const char *host, unsigned int port, int clientId=0) = 0;
   virtual void eDisconnect() = 0;
   virtual int serverVersion() = 0;
   virtual IBString TwsConnectionTime() = 0;
   virtual void reqMktData( TickerId id, const Contract &contract,
	   const IBString& genericTicks, bool snapshot) = 0;
   virtual void cancelMktData( TickerId id) = 0;
   virtual void placeOrder( OrderId id, const Contract &contract, const Order &order) = 0;
   virtual void cancelOrder( OrderId id) = 0;
   virtual void reqOpenOrders() = 0;
   virtual void reqAccountUpdates(bool subscribe, const IBString& acctCode) = 0;
   virtual void reqExecutions(int reqId, const ExecutionFilter& filter) = 0;
   virtual void reqIds( int numIds) = 0;
   virtual bool checkMessages() = 0;
   virtual void reqContractDetails( int reqId, const Contract &contract) = 0;
   virtual void reqMktDepth( TickerId id, const Contract &contract, int numRows) = 0;
   virtual void cancelMktDepth( TickerId id) = 0;
   virtual void reqNewsBulletins( bool allMsgs) = 0;
   virtual void cancelNewsBulletins() = 0;
   virtual void setServerLogLevel(int level) = 0;
   virtual void reqAutoOpenOrders(bool bAutoBind) = 0;
   virtual void reqAllOpenOrders() = 0;
   virtual void reqManagedAccts() = 0;
   virtual void requestFA(faDataType pFaDataType) = 0;
   virtual void replaceFA(faDataType pFaDataType, const IBString& cxml) = 0;
   virtual void reqHistoricalData( TickerId id, const Contract &contract,
	   const IBString &endDateTime, const IBString &durationStr, const IBString &barSizeSetting,
	   const IBString &whatToShow, int useRTH, int formatDate) = 0;
   virtual void exerciseOptions( TickerId id, const Contract &contract,
       int exerciseAction, int exerciseQuantity, const IBString &account, int override) = 0;
   virtual void cancelHistoricalData( TickerId tickerId ) = 0;
   virtual void reqRealTimeBars( TickerId id, const Contract &contract, int barSize,
	   const IBString &whatToShow, bool useRTH) = 0;
   virtual void cancelRealTimeBars( TickerId tickerId) = 0;
   virtual void cancelScannerSubscription( int tickerId) = 0;
   virtual void reqScannerParameters() = 0;
   virtual void reqScannerSubscription( int tickerId, const ScannerSubscription &subscription) = 0;
   virtual void reqCurrentTime() = 0;
   virtual void reqFundamentalData( TickerId reqId, const Contract&, const IBString& reportType) = 0;
   virtual void cancelFundamentalData( TickerId reqId) = 0;
   virtual void calculateImpliedVolatility( TickerId reqId, const Contract &contract, double optionPrice, double underPrice) = 0;
   virtual void calculateOptionPrice( TickerId reqId, const Contract &contract, double volatility, double underPrice) = 0;
   virtual void cancelCalculateImpliedVolatility( TickerId reqId) = 0;
   virtual void cancelCalculateOptionPrice( TickerId reqId) = 0;
   virtual void reqGlobalCancel() = 0;
   virtual void reqMarketDataType( int marketDataType) = 0;
private:
};

#endif
