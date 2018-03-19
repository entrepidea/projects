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

#include "singleta.h"
#include <scoreboard.h>
#include <mq.h>
#include <mysql.h>

extern const char* TTField[TTSIZE];

Predictor::Predictor() {
  try {
    string dt = ymd();
    SENTOSALOG2;
    string sql = "SELECT s,AVG(v) AS vol FROM volForecast WHERE dt='" + dt
        + "' GROUP BY s";
    printfr("[%s]%s\n", __FUNCTION__, sql.c_str());
    MYSQL* conn = (MYSQL*)CDB::R().conn;
    if (mysql_query(conn, sql.c_str())) {
      fprintf(stderr, "%s\n", mysql_error(conn));
      exit(1);
    }
    MYSQL_RES *res = mysql_store_result(conn);
    int dbRowNum = mysql_num_rows(res);
    if (dbRowNum < CConfig::R().instru.size()) {
      printfr("[%s]Missing Data: %d - %lu\n",
          __FUNCTION__,
          dbRowNum,
          CConfig::R().instru.size());
    }
    MYSQL_ROW row;
    while ((row = mysql_fetch_row(res)) != nullptr && row[0] != nullptr) {
      int i = 0;
      string sym = row[i++];
      double rng = (atof(row[i++]));
      if (rng > 0) {
        sym2rng[sym] = rng;
        printf("[%s]%s,%.3f\n", __FUNCTION__, sym.c_str(), rng);
      }
    }
    mysql_free_result(res);
  } catch (...) {
  }
}

void singletaalgo::initial_setup(){
  tobj* ptobj = pmcw->ptobj;
  xmlpath = getTIpath(ptobj->pid(),ymd(),0,".vola.json");
  crng.load(xmlpath);
  initialized = true;
  target_gain = 200; // prm.meat;
  target_lose = -200; // -prm.meat * prm.smr;
  updateRTData(RTDALL);
  daily_spread = Predictor::getInstance().sym2rng[ptobj->pid()];
  TTPrint("<%s>daily_spread=%.2f\n", ptobj->pid().c_str(), daily_spread);
}

void singletaalgo::predictRange(){}

void singletaalgo::updateRTData(_RTDATA_TYPE i){
  tobj* ptobj = pmcw->ptobj;
  dSeries& _5s = ptobj->rtd->_5s;
  dSeries& _dy = ptobj->rtd->_dy;

  if (CConfig::R().isbacktest){}
  else{
    // No need to do anything here. The data is pumped in cwrapper.c assign() function
  }
}

void singletaalgo::check_bar_data_backtest(){}

// check_bar_data to calculate TA indicators of 5s bar
// Can it generate signal? NO
void singletaalgo::check_bar_data(){
  actionType = ATNone;
  tobj* ptobj = pmcw->ptobj;
  mktinfo& rti = pmcw->_mkdata;
  param& prm = ptobj->prm;
  tradeinfo& ti = ptobj->_tinfo;

  dSeries& _5s = ptobj->rtd->_5s;
  dSeries& _dy = ptobj->rtd->_dy;

  spd = red - blu;
  wap = _5s.w.back();
  __mp = fixMarketPriceForBT();

LOGANDRETURN:
  char tmp[512];
  sprintf(tmp, "[ind]sym=%s,wap=%.3f,blu=%.3f,red=%.3f,"
    "maxb=%.3f,minr=%.3f,rng=%.3f,pcv=%.3f,pco=%.3f,p13=%.3f,p26=%.3f,p52=%.3f|%s",
    ptobj->pid().c_str(),
    wap, blu, red, crng.maxblu, crng.minred, crng.tvrange,
    rti.PCVOR, rti.PCOIR, rti.priceLevel(1), rti.priceLevel(2), rti.priceLevel(3),
    _5s.ind.toStr(0).c_str());
  rlog->write("%s %s\n", ymdhms().c_str(), tmp);
}

void singletaalgo::check_runtime_data(){
  actionType = ATNone;
  tobj* ptobj = pmcw->ptobj;
  param& prm = ptobj->prm;
  tradeinfo& ti = ptobj->_tinfo;

  dSeries& _5s = ptobj->rtd->_5s;
  dSeries& _dy = ptobj->rtd->_dy;

  if (pmcw && pmcw->_mkdata.ready()){
    if (pmcw && pmcw->_mkdata.lastPrice == 0){
      wap = _5s.w.back();
      if (wap == 0){
        return;
      }
    }
    wap = pmcw->_mkdata.lastPrice;
    blu = pmcw->_mkdata.bid;
    red = pmcw->_mkdata.ask;
    spd = red - blu;

    crng.update(blu, red, daily_spread);
    TTPrint("[%s]<%s>currentVolatility:%.2f,forecast:%.2f\n", __FUNCTION__,
      ti.syms.c_str(), crng.tvrange, daily_spread);

    __mp = fixMarketPriceForBT();
    if (__mp == 0.0){
      return;
    }
    ti.updatepnl(__mp);

    //如果状态是在等待FILL的话，就应该返回,可以查看下目前的order,不要再发order了
    //TTPrint("[%s]<%s>ti.__status=%d\n", __FUNCTION__, ptobj->pid().c_str(), ti.__status);
    if (ti.statuz > DELIMITER){
      //bool co1 = ti.checkOrder(pmcw);
      //bool co2 = ti.checkOrder(pscw);
      return;
    }

    double _rsis = _5s.ind.rsi.back();
    double _rsiD = _dy.ind.rsi.back();

    int decision = decisionTree();

    //如果没到交易时间,不要交易!
    if (remaining_time_percentage<0 && !CConfig::R().ispaper){
      return;
    }
    // order logic with signal
    {
      bool shrtcondition = (_rsis > 50 && _rsiD > 50);
      bool longcondition = (_rsis < 40 && _rsiD < 40);
      //bool shrtcondition = _rsis > 80;
      //bool longcondition = _rsis < 20;
      bool closecondition;
      if (cutLossOrGain(ptobj)){
        actionType = ATclose;
        return;
      }

      assetCost = __mp;
      if (ti.cR < 0){
        return;
      }
      volume = ti.cR / assetCost;
      if (isHKSE(ptobj->pid())){
        volume *= 10;
        volume = STEPFUNC(volume, 1000);
      }else{
        //volume = STEPFUNC(volume, 50);
      }

      switch (ti.statuz){
        //////////////////////////////////////////////////////////////////////////
      case NP:
      {
        if (shrtcondition){
          bool rcode = ti.shortat(blu, __dtm, volume, assetCost, pmcw);
          if (rcode){
            //ti.opendt = __dtm;
            actionType = ATsell;
          }
        }
        else if (longcondition){
          bool rcode = ti.longat(red, __dtm, volume, assetCost, pmcw);
          if (rcode){
            //ti.opendt = __dtm;
            actionType = ATBuy;
          }
        }
      }
        break;
        //////////////////////////////////////////////////////////////////////////
      case OTS:
      {
        if (shrtcondition){
          if (ti.aNum >= prm.mc){
            break;
          }
          bool rcode = ti.shortat(blu, __dtm, volume, assetCost, pmcw);
          if (rcode){
            actionType = ATsell;
          }
        }
        else if (longcondition){
          if (ti.closeat(pmcw, pscw, &prm)){
            TTPrintNoTime("[.]close@%.3f@%s,realizedPNL=%.3f\n", red, __dtm.c_str(), (double)ti.uPNL);
            actionType = ATclose;
          }
        }
      }
        break;
        //////////////////////////////////////////////////////////////////////////
      case OTL:
      {
        if (longcondition){
          if (ti.aNum >= prm.mc){
            break;
          }
          bool rcode = ti.longat(red, __dtm, volume, assetCost, pmcw);
          if (rcode){
            actionType = ATBuy;
          }
        }
        else if (shrtcondition){
          if (ti.closeat(pmcw, pscw, &prm)){
            TTPrintNoTime("[.]close@%.3f@%s,realizedPNL=%.3f\n", blu, __dtm.c_str(), (double)ti.uPNL);
            actionType = ATclose;
          }
        }
      }break;
      default:break;
      }
    }
  }
}

/// check market sentiment - > overnight volatility!!!
/// change with respect to today's open; to yesterday's close
void CheckETF(const char* s, const char* etf){
  mktinfo* p = sboard::R().getRTinfo(etf);
  if (p && p->_open > 0.0){
    //TTPrint("[%s]ETF %s(bp):%.4f,%.4f\n", s, etf,
    //  10000 * (p->lastPrice - p->_open) / p->_open,
    //  10000 * (p->lastPrice - p->_close) / p->_close);
    double etfMoveBPS_open  = 10000 * (p->lastPrice - p->_open) / p->_open;
    double etfMoveBPS_close = 10000 * (p->lastPrice - p->_close) / p->_close;
  }
}

int singletaalgo::decisionTree(){
  tobj* ptobj = pmcw->ptobj;
  param& prm = ptobj->prm;
  tradeinfo& ti = ptobj->_tinfo;
  mktinfo& rti = pmcw->_mkdata;

  // market
  CheckETF(ptobj->pid().c_str(), "SPY");
  CheckETF(ptobj->pid().c_str(), "FXI");
  CheckETF(ptobj->pid().c_str(), "DJS");

  // market sentiment
  double abs_pcvol = fabs(rti.PCVOR);
  if (abs_pcvol > 5 || abs_pcvol < 1/5){
    TTPrint("<%s>market sentiment significant,abs_pcvol=%.3f\n",
      ptobj->pid().c_str(), abs_pcvol);
  }
  double abs_pcoi = fabs(rti.PCOIR);
  if (abs_pcoi > 8 || abs_pcoi < 1 / 8){
    TTPrint("<%s>market sentiment significant,abs_pcoi=%.3f\n",
      ptobj->pid().c_str(), abs_pcoi);
  }

  // Current price level (3 months, 6 months, 1 year)
  TTPrint("<%s>13w:%.3f,26w:%.3f,52w:%.3f\n", ptobj->pid().c_str(),
    rti.priceLevel(1), rti.priceLevel(2), rti.priceLevel(3));

  // TA indicator
  dSeries& _5s = ptobj->rtd->_5s;
  dSeries& _dy = ptobj->rtd->_dy;
  double _stds = _5s.ind.std.back();
  double _stdD = _dy.ind.std.back();
  double _rsis = _5s.ind.rsi.back();
  double _rsiD = _dy.ind.rsi.back();
  double _emas = _5s.ind.ma.back();
  double _emaD = _dy.ind.ma.back();

  // Today's forecast
  TTPrint("<%s>TRP:%.3f,%.3f,%.3f\n", //Today_Range_Prediction
    ptobj->pid().c_str(), crng.tlow, rti.lastPrice, crng.thigh);

  return 0;
}

bool singletaalgo::cutLossOrGain(tobj* ptobj){
  param& prm = ptobj->prm;
  tradeinfo& ti = ptobj->_tinfo;

  if (abs(ti.uPNL) > 0){//Lose
    if (ti.uPNL < target_lose){
      if (remaining_time_percentage < 0.8){
        ti.num_cutloss++;
        TTPrint("[%s]<%s>closeposition,unrPNL=%.3f,target_lose=%.3f\n",__FUNCTION__,
          pmcw->ptobj->pid().c_str(), (double)ti.uPNL, target_lose);
        ti.closeat(pmcw, pscw, &prm);
        return true;
      }
    }else if (ti.uPNL > target_gain){//Win
      ti.num_cutgain++;
      TTPrint("[%s]<%s>closeposition,unrPNL=%.3f,target_gain=%.3f\n", __FUNCTION__,
        pmcw->ptobj->pid().c_str(), (double)ti.uPNL, target_gain);
      ti.closeat(pmcw, pscw, &prm);
      return true;
    }
  }
  return false;
}

void singletaalgo::check_market_data(TickType ttid){
  update_trading_time();
  if (ttid == BID_PRICE || ttid == ASK_PRICE){
    check_runtime_data();
  }else if (ttid == SEN_RT5SBAR){
    check_bar_data();
  }else if (ttid == LAST_PRICE){
    tradeinfo& ti = pmcw->ptobj->_tinfo;
    ti.updatepnl(pmcw->_mkdata.lastPrice);
  }
}
