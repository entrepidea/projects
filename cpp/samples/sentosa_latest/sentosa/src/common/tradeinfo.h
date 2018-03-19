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

#ifndef __TRADEINFO__
#define __TRADEINFO__

#include <assert.h>
#include <numeric>
#include <sentosaconfig.h>
#include <params.h>
#include <mutex>
#include <datasource.h>
#include <Order.h>
#include <log.h>
#include <regex>
#include <atomic>

#define INICAPITAL 4000.0

/*switch(){
case NOPOS:break;
case OTL:break;
case OTS:break;
case DELIMITER:break;
case LWaitC:break;
case SWaitC:break;
case NWaitL:break;
case NWaitS:break;
case LWaitC2:break;
case SWaitC2:break;
case NWaitL2:break;
case NWaitS2:break;
}*/

enum POSSTATUS{
  NP=0,
  OTL,
  OTS,
  /*上面三个状态不是等待ORDER FILL状态*/

  DELIMITER,//纯粹为了方便用的,不会有状态等于这个,是拿来比较用的

  /**下面4个状态都是在等待FILL的状态**/
  //本来是LONG status,然后发了order去close position,然后正在等着close
  LWaitC,
  LWaitC2,
  //本来是SHORT status,然后发了order去close position,然后正在等着close
  SWaitC,
  SWaitC2,
  //本来是NOPOSITON,然后发了order去LONG新的position,在等ORDER被FILL
  NWaitL,
  NWaitL2,
  //本来是NOPOSITON,然后发了order去SHORT新的position,在等ORDER被FILL
  NWaitS,
  NWaitS2
};
enum ETAG{
  NOTAG,
  LONGATHIGH_SPREAD,
  SHORTATLOW_SPREAD,
  LONGATHIGH_STOCK,
  SHORTATLOW_STOCK,
  SPREAD_WAIT_RANGE_FOR_LONG,
  SPREAD_WAIT_RANGE_FOR_SHORT,
  CONFIDENT_RSI_SUPPORT
};

const char* EST2STR(POSSTATUS st);

struct instrument;

//////////////////////////////////////////////////////////////////////////
struct tradeinfo{
  string syms;
  POSSTATUS statuz = NP;
  ETAG tag = NOTAG;

  double lastp;// 最后一次成交的价格

  /*
   * uPNL = 10; rPNL = 50; aPNL = 100; pnls={100,-50,50};
   * */
  double uPNL=0.0;// unrealized PNL
  //double rPNL=0.0;// last realized PNL
  double aPNL=0.0;// accumulated realized PNL
  //vector<double> pnls;//realized PNLs per CLOSE TRADE
  
  //investment是否等于market value?
  //是这次交易投入的本钱,不包括上次交易
  double inve=0.;// the money we have invested in

  /**看投进去的钱是亏了还是赚了,注意是unrealized return
  比如: investment是3800,现在浮亏-50,那么curReturn是-50/3800.*/
  double curReturn(){ return ((inve==0.0)?(-1000):(uPNL/inve)); }

  //string opendt;

  //这里是指close之前交易的次数
  //本来想多次同方向交易,目前规定只能同方向交易一次
  short aNum = 0;

  uint64_t num_cutgain = 0;
  uint64_t num_cutloss = 0;
  uint64_t num_natural = 0;

  // for every trade(上次一close之后的)
  vector<double> ps;
  vector<int64_t> vo;//注意不能用uint64_t因为trading volume为负当short的时候

  double uPNL4Long(double mp) {
    double yourInv = ValueAtTrade();
    double tmp = abs(TOTALVOL());  //tmp is remaining pos
    double marketvalue = tmp * mp; //你所持股票的市值
    if (yourInv < 1 || marketvalue < 1) {
      printf("%s\n",syms.c_str());
      SENTOSAERROR;return 0;}
    return marketvalue - yourInv;
  }

  double uPNL4Short(double mp) {
    double marketvalue = ValueAtTrade();
    double tmp = abs(TOTALVOL());
    double yourInv = tmp * mp;
    if (yourInv < 1 || marketvalue < 1) {
      SENTOSAERROR;return 0;
    }
    return marketvalue - yourInv;
  }



  //unrealized PNL daily
  //map<string, double> urPNLD;
  //realized PNL daily
  //map<string, double> rPNLD;
  //accumulated PNL daily
  //map<string, double> aPNLD;
  //map<string, double> dailycashRemaing;

  static regex decimal2;
  static regex decimal3;
  static regex nospace;

  template <class Archive>
  void serialize(Archive & ar){
    ar(cereal::make_nvp("sym",syms),
        CEREAL_NVP(uPNL),
        CEREAL_NVP(aPNL),
        CEREAL_NVP(statuz),
        CEREAL_NVP(ps),
        CEREAL_NVP(vo),
        //CEREAL_NVP(aNum),
        //CEREAL_NVP(opendt),
        ////CEREAL_NVP(tag),
        CEREAL_NVP(inve),
        CEREAL_NVP(lcc),
      
        //CEREAL_NVP(pnls),
        //CEREAL_NVP(cdts),
        //CEREAL_NVP(adjs),
        //CEREAL_NVP(oc),
        //CEREAL_NVP(uPNLD),
        //CEREAL_NVP(rPNLD),
        //CEREAL_NVP(aPNLD),
        //CEREAL_NVP(dailycashRemaing),
        CEREAL_NVP(cR)
      );
  }

  // log for unrealized PNL 统计的目的,其实可以不要!
  //vector<double> log_PNL_val;
  //vector<string> log_PNL_dat;
  //vector<string> cdts;//close date time
  //vector<time_t> adjs;//from open time to close time(unit: seconds)???
  //vector<string> oc;//open+close date pairs

  //vector<double> realmeat;



  ///calculated from daily_accumulatedPNL
  //VECDL acc_return;
  //VECST days;

  //initial cash是预分配的资金
  double cR = INICAPITAL;

  //How much cash at the time of last "close" action?
  /*
  比如本金是4000，现在买股票100股花了3600， 还剩400块
  lcc = 4000， investment = 3600， cashRemaining=400
  然后卖100股赚了500块
  lcc = 4500， investment = 0， cashRemaining=4500
  然后再卖空股票100股花了4200，那么投资金额也是4200
  lcc = 4500， investment = 4200， cashRemaining=300
  then buy stock 50 shares with 2000, then gain is 100
    lcc = 4500+100=4600,
    investment =2100,//=pos * original avgprice
    cashRemaining=4600-2100=2500
  then buy stock 50 shares with 1900, gain is 200
    lcc = 4600+200=4800,
    investment =0,
    cashRemaining=4800

  lcc - inv = cR
  net liquidate cash = lcc + uPNL
  */
  double lcc = INICAPITAL;

  void reset(){
    statuz=NP;
    tag = NOTAG;
    ps.clear();
    vo.clear();
    aNum = 0;
    lastp = uPNL = inve = 0.0;
  }
  void gain(double tmp){// no uPNL
    aPNL += tmp;
    lcc += tmp;
    inve=ValueAtTrade();
    cR = lcc - inve;
  }

  //performance related values
  double stratwin = 0.;
  time_t mindur = INT_MAX;
  time_t maxdur = INT_MIN;
  int mindurindex = -1;
  int maxdurindex = -1;
  uint64_t avgDur = 0;

  double sharpeR = -1000.;
  //max drawdown
  double maxDD = 0.;
  //max time of drawdown/max recovery time
  uint64_t maxDDD = 0;

  tradeinfo();

  void updatepnl(double mp);

  bool shortat(double mp, const string& dt, uint64_t vol, double cost, 
    instrument* pmcw = nullptr, instrument* pscw = nullptr);
  
  bool longat(double mp, const string& dt, uint64_t vol, double cost,
    instrument* pmcw = nullptr, instrument* pscw = nullptr);

  void __sendorder(instrument* p, uint64_t vol, bool islong);

  void statetransfer(ORDERSTATUS);
  void statetransfer(ORDERSTATUS, instrument*);
  void statetransfer(ORDERSTATUS, double fillp=0.0f, long quant=0);

  //close的时候对参数进行自动调节!
  bool closeat(instrument* pmcw = nullptr, instrument* pscw = nullptr,
    const param* p=nullptr, double adj1=0., double adj2=0.);

  double total_realizedPNL();
  double total_accumulatedPNL(){ return uPNL + total_realizedPNL(); }
  void print_realized_pnls();
  //算法的性能
  void performance();
  void tradefreq();
  void calsharpe(const string& f, const string& t);
  //drawdown
  void calDD(bool logit=false);

  //可能会返回负值
  double TOTALVOL(){
    return accumulate(vo.begin(), vo.end(), 0.0);
  }

  //总是返回正值
  /**
  刚成交的时候所买或卖的股票的价值
  如果是LONG, 这是你投进去的钱investment，将来所持股票的market value增加了，增加的部分就是你的Profit
  如果是SHORT,这相当于你锁定了股票的market value,以后你close position的时候股票的价值作为你的投进去的钱
  因为:
    PNL = market value - 你投进去的钱

  当LONG的时候: Value at trade = 你投进去的钱
  当SHORT的时候: Value at trade = market value
  */
  double ValueAtTrade(){
    if(ps.size()!=vo.size()){SENTOSAERROR;return 0;}
    return abs(inner_product(ps.begin(), ps.end(), vo.begin(), .0));
  }

  //总是返回正值
  double AVGPRICE(){
    double tmp = abs(TOTALVOL());
    return (tmp>0)?(ValueAtTrade()/tmp):0.0;
  }
  
  void print(){
    TTPrint("[%s]<%s>totalvol=%.2f,avgP=%.2f,rmCash=%.2f,investment=%.2f,unrPNL=%.2f\n",
      __FUNCTION__,syms.c_str(),TOTALVOL(),AVGPRICE(),cR,inve,uPNL);
  }

  bool selfCheck(bool loadfromfile=false);

};

#endif
