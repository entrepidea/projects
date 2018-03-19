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

#include "cwrapper.h"
#include <EWrapper.h>
#include <datasource.h>
#include <scoreboard.h>
#include <unordered_map>

bool isLordsEmpty(LORDS &l){
  for(int i=0;i<l.count;++i){if(ISREMOVED(l.ords[i].status)){return false;}}
  return true;
}
void addOrder(instrument* i,CSTR& t,long oid, long q,double p,double a){
  if (oid<0 || q == 0 || i == nullptr || p < 0){SENTOSAERROR;return;}
  LORDS& l=i->lorders;
  lock_guard<mutex> g(l.wlock);
  ++(l.count);
  Order* o = l.tail();  //no tail()
  o->sym = i->c.symbol;
  o->orderId = oid;
  o->pcw = i;
  o->createTime = time(nullptr);
  o->totalQuantity = abs(q);
  o->action = (q > 0) ? "BUY" : "SELL";
  o->orderType = t;
  if (t=="LMT"){
      o->lmtPrice = p;
      o->orignalPrice = p;
      o->allowedMove = a;
  }else if(t=="MKT"){}
  o->account = CR(account);
  o->status = NEWBORN;
  i->ptobj->_tinfo.statetransfer(NEWBORN, i);
}

void mktinfo::RTVolume::setValue(const string& s){
  VECST vs = splitv2(s, ';');
  if (vs.size() == 6){
    LastTradePrice = atof(vs[0].c_str());
    LastTradeSize = atof(vs[1].c_str());
    LastTradeTime = atoll(vs[2].c_str());
    TotalVolume = atof(vs[3].c_str());
    VWAP = atof(vs[4].c_str());
    SingleTradeFlag = (vs[5] == "true");
  }
}

double mktinfo::priceLevel(short T){///3 months
  double h = ((T == 1) ? WH13 : (T==2?WH26:WH52));
  double l = ((T == 1) ? WL13 : (T == 2 ? WL26 : WL52));
  if (h == 0 || l == 0){
    return 0;
  }
  else if (lastPrice > h){
    return (lastPrice - l) / (h - l);
  }
  else if (lastPrice < l){
    return -(h - lastPrice) / (h - l);
  }
  else{
    return (h - lastPrice) / (h - l);
  }
}

void mktinfo::calPCVORatio(){
  if (OCVol > 1 && OPVol > 1){
    PCVOR = OPVol / OCVol;
    TTPrint("<%s>PCVORatio:%.3f/%.3f=%.3f\n",
      psymbol->c_str(), OPVol, OCVol, PCVOR);
  }
}

void mktinfo::calPCOIRatio(){
  if (OCOI > 1 && OPOI > 1){
    PCOIR = OPOI / OCOI;
    TTPrint("<%s>PCOIRatio:%.3f/%.3f=%.3f\n",
      psymbol->c_str(), OPOI, OCOI, PCOIR);
  }
}

instrument::instrument():cwtp(MASTER),ptobj(nullptr)
{
  _mkdata.psymbol = &c.symbol;
}

Order* instrument::getOrder(uint64_t oid){
  for (int i = 0; i < (int)lorders.count; ++i){
    if (lorders.ords[i].orderId == oid){
      return &lorders.ords[i];
    }
  }
  return nullptr;
}

/*void instrument::deleteOrder(Order* po){
  if (po){
    sboard::R().oid2cwstock.erase(po->orderId); //[po->orderId] = nullptr;
    po->status = REMOVED;
  }
}*/

void instrument::SetTickValue(TickType i, const char* v){
  if (CConfig::R().isdebug){
    TTPrint("<%s>%s=%s\n", _mkdata.psymbol->c_str(), TTField[i], v);
  }
  if (i == BID_SIZE){
    _mkdata.bsize = atoi(v);
  }
  else if (i == BID_PRICE){
    _mkdata.bid = atof(v);
  }
  else if (i == ASK_PRICE){
    _mkdata.ask = atof(v);
  }
  else if (i == ASK_SIZE){
    _mkdata.asize = atoi(v);
  }
  else if (i == LAST_PRICE){
    _mkdata.lastPrice = atof(v);
    this->ptobj->_tinfo.updatepnl(_mkdata.lastPrice);
  }
  else if (i == LAST_SIZE){
    _mkdata.lastSize = atoi(v);
  }
  // get there once per 5 seconds
  else if (i == SEN_RT5SBAR){
    //239494503443928@FXI|62|47.90:47.90:47.87:47.89:47.88:609
    //239494503448605@SFUN|62|8.81:8.81:8.81:8.81:8.81:0
    //printf("%s\n",ymdhms().c_str());
    if (ptobj && ptobj->rtd) {
      if (cwtp==SINGLE) {
        ptobj->rtd->_5s.pushRTBarStr(v);
        ptobj->rtd->_5s.calTAindicator(ptobj->prm.win, INDALL);
      } else {
        if (cwtp==MASTER) {
          if (ptobj->rtd->_5s.pm) {
            ptobj->rtd->_5s.pm->pushRTBarStr(v);
            ptobj->rtd->_5s.pm->calTAindicator(ptobj->prm.win, INDALL);
          }
        } else if(cwtp==SLAVE) {
          if (ptobj->rtd->_5s.ps) {
            ptobj->rtd->_5s.ps->pushRTBarStr(v);
            ptobj->rtd->_5s.ps->calTAindicator(ptobj->prm.win, INDALL);
          }
        }
      }
    }
  }
  else if (i == SEN_5SWAPSPD){
    bool isCSingle = (ptobj->rtd->_5s.pm == nullptr) && (ptobj->rtd->_5s.ps == nullptr);
    if (!isCSingle){
      ptobj->rtd->_5s.w.push_back(atof(v));
      ptobj->rtd->_5s.calTAindicator(ptobj->prm.win, INDWAP);
    }
  }
  else if (i == HIGH){
    _mkdata._high = atof(v);
  }
  else if (i == LOW){
    _mkdata._low = atof(v);
  }
  else if (i == CLOSE_PRICE){
    _mkdata._close = atof(v);
    if(ptobj->_tinfo.uPNL==0){
      this->ptobj->_tinfo.updatepnl(_mkdata._close);
    }
    if(ptobj->_tinfo.lastp==0){
      ptobj->_tinfo.lastp = _mkdata._close;
    }
  }
  else if (i == OPEN_TICK){
    _mkdata._open = atof(v);
  }
  else if (i == VOLUME){
    _mkdata.volume = atoi(v);
  }
  else if (i == LOW_13_WEEK){
    _mkdata.WL13 = atof(v);
  }
  else if (i == HIGH_13_WEEK){
    _mkdata.WH13 = atof(v);
  }
  else if (i == LOW_26_WEEK){
    _mkdata.WL26 = atof(v);
  }
  else if (i == HIGH_26_WEEK){
    _mkdata.WH26 = atof(v);
  }
  else if (i == LOW_52_WEEK){
    _mkdata.WL52 = atof(v);
  }
  else if (i == HIGH_52_WEEK){
    _mkdata.WH52 = atof(v);
  }
  else if (i == AVG_VOLUME){
    _mkdata.AvgVolume = atoi(v);
  }
  else if (i == OPTION_HISTORICAL_VOL){
    _mkdata.OptionHistoricalVolatility = atof(v);
  }
  else if (i == OPTION_IMPLIED_VOL){
    _mkdata.OptionImpliedVolatility = atof(v);
  }
  else if (i == OPTION_CALL_OPEN_INTEREST){
    _mkdata.OCOI = atof(v);
    _mkdata.calPCOIRatio();
  }
  else if (i == OPTION_PUT_OPEN_INTEREST){
    _mkdata.OPOI = atof(v);
    _mkdata.calPCOIRatio();
  }
  else if (i == OPTION_CALL_VOLUME){
    _mkdata.OCVol = atof(v);
    _mkdata.calPCVORatio();
  }
  else if (i == OPTION_PUT_VOLUME){
    _mkdata.OPVol = atof(v);
    _mkdata.calPCVORatio();
  }
  else if (i == SHORTABLE){
    double value = atof(v);
    if (value > 2.5) { // 3.0-There are at least 1000 shares available for a short sale
      _mkdata.shortable = true;//by default it is true
      TTPrint("[cool]%s is shortable!!!\n", this->c.symbol.c_str());
    }
    else if (value > 1.5) {} // 2.0-This contract will be available for short sale if shares can be located
    else if (value > 0.5) {} // 1.0-Not available for short sale
    else {}// unknown value
  }
  else if (i == RT_VOLUME){
    _mkdata.rtv.setValue(v);
  }
  else if (i == INDEX_FUTURE_PREMIUM){
    _mkdata.ifp = atof(v);
  }
  else if (i == AUCTION_VOLUME){
    _mkdata.auctionVolume = atoi(v);
  }
  else if (i == AUCTION_PRICE){
    _mkdata.auctionPrice = atof(v);
  }
  else if (i == AUCTION_IMBALANCE){
    _mkdata.auctionImbalance = atoi(v);
  }
  else if (i == TRADE_COUNT){
    _mkdata.trades = atoi(v);
  }
  else if (i == TRADE_RATE){
    _mkdata.trate = atof(v);
  }
  else if (i == VOLUME_RATE){
    _mkdata.vrate = atof(v);
  }
  else if (i == LAST_RTH_TRADE){
    double tmp = atof(v);
    if (tmp > 0.0001){
      _mkdata.LRTH = tmp;
      this->ptobj->_tinfo.updatepnl(tmp);
    }
  }
  else if (i == SEN_CONTID){
    c.conId = atoi(v);
  }
  else if (i == SEN_PORTFOLIO){
    string s(v);
    VECST vs = splitv2(s, ',');
    _mkdata.pos_ = atof(vs[0].c_str());
    if (vs.size() == 3){
      _mkdata.avgP = atof(vs[1].c_str());
      _mkdata.lastPrice = atof(vs[2].c_str());
      this->ptobj->_tinfo.updatepnl(_mkdata.lastPrice);
    }
    //if (CConfig::R()._mode == SYS_MODE::TRADE_MODE){
    ptobj->VerifyPosition(_mkdata);
    //}
  }
}

const char* ORDST2STR(ORDERSTATUS ost){
  static const char* a[] = { "NEWBORN", "PENDING_SUBMIT",
    "PENDING_CANCEL", "PRESUBMITTED", "SUBMITTED", "CANCELLED",
    "INACTIVE", "API_PENDING", "API_CANCELLED", "FILLED" };
  return a[ost];
};

// TODO
static unordered_map<string,ORDERSTATUS> s2os{
    {string("Filled"),FILLED},
    {string("PreSubmitted"),PRESUBMITTED},
    {string("Submitted"),SUBMITTED},
    {string("Cancelled"),CANCELLED},
    {string("ApiPending"),API_PENDING},
    {string("Inactive"),INACTIVE},
    {string("ApiCancelled"),API_CANCELLED}
  };
ORDERSTATUS getST(const string &status){
  if (s2os.find(status)==s2os.end()){
    return NEWBORN;
  }else{
    return s2os[status];
  }

}
/*
Order* set_ostatus(instrument* pcs, long oid, const ORDERSTATUS st){
  LORDS& lorders = pcs->lorders;
  for (int i = 0; i < lorders.count; ++i) {
    if (oid == lorders.ords[i].orderId && lorders.ords[i].status < st
        && lorders.ords[i].status != REMOVED) {
      lorders.ords[i].status = st;
      pcs->ptobj->_tinfo.statetransfer(st,pcs);
      return &lorders.ords[i];
    }
  }
  return nullptr;
}*/

double getTradePriceM(mktinfo& ri, bool islong){
  if (islong){
    return ri.ask;
  }
  else{
    return ri.bid;
  }
}
double getTradePriceL(mktinfo& ri, bool islong){
  //return islong ? (ri.bid-1) : (ri.ask+1); // TODO
  return islong ? (ri.bid) : (ri.ask);
}
