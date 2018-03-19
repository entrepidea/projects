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

#ifndef CONTRACTWRAPPER_H
#define CONTRACTWRAPPER_H

#include <sentosaconfig.h>
#include <Contract.h>
#include <mutex>
#include <Order.h>
#include <cband.h>
#include <params.h>
#include <datasource.h>
#include <tradeinfo.h>
#include <swindow.h>
#include <regex>
#include <atomic>

// Runtime market information from IB...will be updated in runtime
// https://www.interactivebrokers.com/en/software/api/apiguide/tables/tick_types.htm

struct mktinfostatic{
  string* psymbol=nullptr;

  double _high =0;// = 1.730
  double _low  =0;// = 1.690
  double _close=0;// = 13.920
  double _open =0;// = 1.690

  double WH13=0;//= 15.910
  double WL13=0;//= 12.250
  double WH26=0;//= 21.650
  double WL26=0;//= 12.250
  double WH52=0;//= 22.190
  double WL52=0;//= 12.250

  template<class Archive>
  void serialize(Archive & ar) {
    string sym(*psymbol);
    ar( CEREAL_NVP(sym),
        cereal::make_nvp("H",_high ),
        cereal::make_nvp("L",_low  ),
        cereal::make_nvp("C",_close),
        cereal::make_nvp("O",_open ),
        CEREAL_NVP(WH13 ),
        CEREAL_NVP(WL13 ),
        CEREAL_NVP(WH26 ),
        CEREAL_NVP(WL26 ),
        CEREAL_NVP(WH52 ),
        CEREAL_NVP(WL52 )
    );
  }

  virtual string O2J(const regex& p) {
    stringstream ss;
    {
      cereal::JSONOutputArchive oarchive(ss);
      oarchive(cereal::make_nvp("mktstatic", *this));
    }
    string r = regex_replace(ss.str(), p, "$1");
    return r;
  }

};

struct mktinfo: mktinfostatic{
  inline bool ready(){ return bid > 0 && ask > 0; }
  double bid=0;
  double ask=0;
  uint64_t bsize=0;
  uint64_t asize=0;

  uint64_t volume   =0;// = 1300000
  uint64_t AvgVolume=0;// = 4765

  //https://www.interactivebrokers.com/en/software/api/apiguide/tables/rtvolume.htm
  //string RTVolume;
  struct RTVolume{
    double LastTradePrice=0.0;
    double LastTradeSize=0.0;
    uint64_t LastTradeTime=0;
    double TotalVolume=0.0;
    double VWAP=0.0;//i think it is the current trading day VWAP - to be verified
    bool SingleTradeFlag=false;
    void setValue(const string& s);
  }rtv;

  long lastTimestamp;// = 1399867086

  double lastPrice = 0;// = 1.710
  uint64_t lastSize = 0;// = 4000

  double ifp;// INDEX_FUTURE_PREMIUM;

  //http://www.itg.com/news_events/papers/Inside_Opening_Auction.pdf
  //http://www.investopedia.com/articles/investing/091113/auction-method-how-nyse-stock-prices-are-set.asp
  int auctionVolume;// = 4000
  int auctionImbalance;// = 4000
  double auctionPrice;// = 1.690

  bool shortable = false;//

  uint64_t trades=0;//= 0

  double vrate=0;//VOLUME_RATE
  double trate=0;//TRADE_RATE

  //regular trading hour trade price
  double LRTH=0;//= 13.930

  //http://www.elitetrader.com/et/index.php?threads/does-anybody-manage-a-book-of-options-in-interactive-brokers.245811/
  double OptionImpliedVolatility   =0;
  double OptionHistoricalVolatility=0;//= 0.426731 // 30天vol的平均值
  
  //http://www.investopedia.com/articles/optioninvestor/04/060904.asp
  double OCVol = -1.0;//= 0
  double OPVol = -1.0;//= 0
  double PCVOR = -1.0;
  void calPCVORatio();

  double OCOI  = -1.0;//= 8203
  double OPOI  = -1.0;//= 8896
  double PCOIR = -1.0;
  void calPCOIRatio();

  // not very useful
  // in order to verify tradeinfo's TOTALVOL();
  // we may remove it in the future
  double pos_ = 0.0;
  double avgP = 0.0;
  


  /// current price level
  double priceLevel(short T);///T: 1-> 3 months; 2 -> 6 months; 3-> 1year

  //friend class cereal::access;
  template<class Archive>
  void serialize(Archive & ar) {
    string sym(*psymbol);
    ar( CEREAL_NVP(sym),
        CEREAL_NVP(bid),
        CEREAL_NVP(ask        ),
        cereal::make_nvp("bs",bsize),
        cereal::make_nvp("as",asize),
        /*cereal::make_nvp("H",_high ),
        cereal::make_nvp("L",_low  ),
        cereal::make_nvp("C",_close),
        cereal::make_nvp("O",_open ),*/
        cereal::make_nvp("LP",lastPrice),
        /*CEREAL_NVP(WH13 ),
        CEREAL_NVP(WL13 ),
        CEREAL_NVP(WH26 ),
        CEREAL_NVP(WL26 ),
        CEREAL_NVP(WH52 ),
        CEREAL_NVP(WL52 ),*/
        CEREAL_NVP(vrate),
        CEREAL_NVP(trate),
        CEREAL_NVP(LRTH),
        CEREAL_NVP(OCVol  ),
        CEREAL_NVP(OPVol   ),
        CEREAL_NVP(PCVOR   ),
        CEREAL_NVP(OCOI   ),
        CEREAL_NVP(OPOI    ),
        CEREAL_NVP(PCOIR   ),
        cereal::make_nvp("pos",pos_),
        CEREAL_NVP(avgP),
        cereal::make_nvp("sal",shortable)
    );
  }

};
//////////////////////////////////////////////////////////////////////////

//For Market order
double getTradePriceM(mktinfo& ri, bool islong);
//For Limit order
double getTradePriceL(mktinfo& ri, bool islong);


//#include "tbb/tbb.h"
//using LORDS = tbb::concurrent_vector<Order*>;
//using LORDS = list<Order*>;
//bool isLordsEmpty(LORDS &l);

typedef struct{
  Order ords[283];//8 ** 0.5
  atomic_int count;
  Order* tail(){return(count==0?nullptr:&ords[int(count-1)]);}
  Order* head(){return(count==0?nullptr:&ords[0]);}
  mutex wlock;
  mutex rlock;
} LORDS;
bool isLordsEmpty(LORDS &l);
void addOrder(instrument* i,CSTR& t,long oid, long q,double p=.0f,double a=.0f);

// a big class including Contract, Order and runtime price information
struct instrument{
  instrument();

  Contract  c;

  LORDS lorders;
  
  // runtime dynamic info
  mktinfo _mkdata;

  //Pair Trading Parameters
  CWTYPE cwtp;
  
  //it is too difficult to find the counterpart for each of the pair, 
  //here use spouse in case we want to locate the counterpart immediately
  instrument* spouse;

  //5s bar data, historical data, algo parameters
  tobj* ptobj;
  
  Order* getOrder(uint64_t oid);
  //void deleteOrder(Order* po);
  void SetTickValue(TickType i, const char* v);
  string mktinfo2Json(const std::regex& rg) {
    stringstream ss;
    {
      cereal::JSONOutputArchive oarchive(ss);
      oarchive(CEREAL_NVP(_mkdata));
    }
    string r = regex_replace (ss.str(),rg,"$1");
    return r;
  }
};


//Order* set_ostatus(instrument* pcs, long oid, const ORDERSTATUS st);

/*
•PendingSubmit - indicates that you have transmitted the order, but have not yet received confirmation that it has been accepted by the order destination.
!!!NOTE: This order status is not sent by TWS and should be explicitly set by the API developer when an order is submitted.!!!
•PendingCancel - indicates that you have sent a request to cancel the order but have not yet received cancel confirmation from the order destination.
At this point, your order is not confirmed canceled. You may still receive an execution while your cancellation request is pending.
NOTE: This order status is not sent by TWS and should be explicitly set by the API developer when an order is canceled.
•PreSubmitted - indicates that a simulated order type has been accepted by the IB system and that this order has yet to be elected.
The order is held in the IB system until the election criteria are met. At that time the order is transmitted to the order destination as specified.
•Submitted - indicates that your order has been accepted at the order destination and is working.
•Cancelled - indicates that the balance of your order has been confirmed canceled by the IB system.
This could occur unexpectedly when IB or the destination has rejected your order.
•Filled - indicates that the order has been completely filled.
•Inactive - indicates that the order has been accepted by the system (simulated orders) or an exchange (native orders)
but that currently the order is inactive due to system, exchange or other issues.
*/
ORDERSTATUS getST(const IBString &status);

const char* ORDST2STR(ORDERSTATUS ost);

#endif
