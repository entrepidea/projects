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

#include "dbb0.h"
#include <log.h>
#include <ta_libc.h>
#include <memory>
#include <algorithm>
#include <functional>

#define max(x,y) (x>y?y:x)
extern const char* TTField[TTSIZE];

void Thread_CheckptobjFile(spreadRange* psrange){
  while (psrange->spdMonitor){
    lock_guard<mutex> _g(psrange->mtx);
    time_t tt = getFileWriteTime(psrange->spdF);
    if (psrange->lwt != tt){
      psrange->lwt = tt;
      psrange->loadXML();
    }
    msleep(500);
  }
}

void DBB0::initial_setup(){
  tobj* ptobj = pmcw->ptobj;
  param& prm = ptobj->prm;
  tradeinfo& ti = ptobj->_tinfo;
  
  srange.spdF = ptobj->efile;
  srange.loadXML();

  //pmcw->ptobj->loadhData();
  //pmcw->ptobj->loadTradeInfo("");
  //pscw->ptobj->loadhData();
  //pscw->ptobj->loadTradeInfo("");


  pt = new thread(Thread_CheckptobjFile, &srange);

  dSeries& _5s = ptobj->rtd->_5s;
  dSeries& _dy = ptobj->rtd->_dy;

  initialized = true;

  /*if (CConfig::R().isbacktest){
    ixdy = hd->_day.getIdx(__ymd);
  }*/
  predictRange();
  //////////////////////////////////////////////////////////////////////////
  if (ti.tag == CONFIDENT_RSI_SUPPORT){
    return;
  }
  target_gain = prm.meat;
  target_lose = -prm.meat * prm.smr;
  if (0.0 < daily_spread){
    target_gain = max(daily_spread * 100 * 2. / 3., prm.meat);
    //ti.realmeat.push_back(target_gain);
    target_lose = max(-(prm.smr * target_gain), prm.ms);
  }
  //////////////////////////////////////////////////////////////////////////
}

// TODO
// Just a simple moving average to update dr
void DBB0::predictRange(){
  /*
  param& prm = pmcw->ptobj->prm;
  string lastTDT = prevTradeDT(__dtm);
  const uint64_t WINDOWLEN = prm.dwin;
  string prevdate = lastTDT;
  uint64_t k = 0;
  double __tmp = 0.0;
  if (CConfig::R().isbacktest){
    try{
      for (; k < WINDOWLEN; k++){
        prevdate = prevTradeDT(prevdate);
        string pd = prevdate.substr(0, 10);
        __tmp += hd->_day.vrange[ixdy];
        //预测今天的range是前prm.dwin天的moving avg
      }
      daily_spread = __tmp / WINDOWLEN;
    }
    catch (out_of_range&){
      if (k>0){
        daily_spread = __tmp / k;
      }
    }
  }
  else{
    //TODO - load from external file <<drange.ini>>
  }*/
}

// check_bar_data to calculate TA indicators of 5s bar
// Can it generate signal? NO
void DBB0::check_bar_data(){
  actionType = ATNone;
  tobj* ptobj = pmcw->ptobj;
  param& prm = ptobj->prm;
  tradeinfo& ti = ptobj->_tinfo;
  dSeries& _5s = ptobj->rtd->_5s;
  dSeries& _dy = ptobj->rtd->_dy;

  target_gain = 100; // prm.meat;
  target_lose = -800; // -prm.meat * prm.smr;
  wap = _5s.w.back();
  
  //spread应该是log-normal分布!如果status是NOPOS的话，__mp是不重要的!
  ti.updatepnl(wap);

LOGANDRETURN:
  char tmp[512];
  sprintf(tmp, "[ind]sym=%s,wap=%.3f,blu=%.3f,red=%.3f,maxb=%.3f,minr=%.3f,rng=%.3f|%s",
    ptobj->pid().c_str(),
    wap, blu, red, crng.maxblu, crng.minred, crng.tvrange, _5s.ind.toStr(0).c_str());
  //TTPrint("%s\n", tmp);
  rlog->write("%s %s\n", ymdhms().c_str(), tmp);
}

void DBB0::check_bar_data_backtest(){}

void DBB0::check_runtime_data(){
  __check_runtime_data_limit();
}

void DBB0::__check_runtime_data_market(){
  actionType = ATNone;
  tobj* ptobj = pmcw->ptobj;
  param& prm = ptobj->prm;
  tradeinfo& ti = ptobj->_tinfo;

  dSeries& _5s = ptobj->rtd->_5s;
  dSeries& _dy = ptobj->rtd->_dy;

  if (!pmcw->_mkdata.ready() || !pscw->_mkdata.ready()){
    return;
  }
  // runtime info
  if (pmcw->_mkdata.lastPrice == 0 || pscw->_mkdata.lastPrice == 0){
    wap = _5s.w.back();
    if (wap == 0){
      return;
    }
  }
  if (wap == 0){
    wap = pmcw->_mkdata.lastPrice - pscw->_mkdata.lastPrice;
  }
  blu = pmcw->_mkdata.bid - pscw->_mkdata.ask;
  red = pmcw->_mkdata.ask - pscw->_mkdata.bid;
  spd = red - blu;

  TTPrint("s=%s,wap=%.2f,blue=%.2f,red=%.2f,spread=%.2f,st=%.2f,lt=%.2f,rng=%.2f\n",
    ptobj->pid().c_str(), wap, blu, red, spd,
    srange.s_threshold, srange.l_threshold, crng.tvrange);

  crng.update(blu, red, daily_spread);
  __mp = fixMarketPriceForBT();

  //spread应该是log-normal分布!
  //如果status是NOPOS的话，__mp是不重要的!
  bool usewap = false;
  if (ti.statuz != NP){
    if (CConfig::R().isbacktest){/*如果red blue差得太离谱，就直接用wap*/
      if (abs(spd - _5s.ind.spdma.back()) > 3 * _5s.ind.spdstd.back()){
        usewap = true;
      }
    }
  }
  ti.updatepnl((usewap ? wap : __mp));
  //updateDBB();
  //if (cutLossOrGain(ptobj)){
  //  goto LOGANDRETURN;
  //}
  //if (skiptoday == __ymd){//如果当天亏了就不再开单了
  //  goto LOGANDRETURN;
  //}

  double depth = 0.0;
  //if (ti.__tag == SHORTATLOW_SPREAD || ti.__tag == LONGATHIGH_SPREAD){
  //  target_lose = -target_gain * 2;
  //}
  double stepsize = prm.step + daily_spread;
  double _stds = _5s.ind.std.back();
  double _stdD = _dy.ind.std.back();
  double _rsis = _5s.ind.rsi.back();
  double _rsiD = _dy.ind.rsi.back();
  double _emas = _5s.ind.ma.back();
  double _emaD = _dy.ind.ma.back();

  // order logic with signal
  {
    bool shrtcondition = (blu > srange.s_threshold);
    bool longcondition = (red < srange.l_threshold);
    //bool closcondition = ();

    switch (ti.statuz){
    //////////////////////////////////////////////////////////////////////////
    case NP:
    {
      if (shrtcondition){
        assetCost = getpaircost(false);
        depth = _stds>0 ? (1.*(blu - srange.s_threshold) / _stds) : 1;
        volume = min((ti.cR / (assetCost*prm.mc))*(1 + depth), ti.cR / assetCost);
        volume = STEPFUNC(volume, 50);
        if (volume == 0){
          goto LOGANDRETURN;
        }
        bool rcode = ti.shortat(blu, __dtm, volume, assetCost, pmcw, pscw);
        if (rcode){
          //ti.opendt = __dtm;
          setsthreshold(blu + stepsize);
          actionType = ATsell;
        }
      }
      else if (longcondition){
        assetCost = getpaircost(true);
        depth = _stds > 0 ? (1.*(srange.l_threshold - red) / _stds) : 1;
        volume = min((ti.cR / (assetCost*prm.mc))*(1 + depth), ti.cR / assetCost);
        volume = STEPFUNC(volume, 50);
        if (volume == 0){
          goto LOGANDRETURN;
        }
        bool rcode = ti.longat(red, __dtm, volume, assetCost, pmcw, pscw);
        if (rcode){
          //ti.opendt = __dtm;
          setlthreshold(red - stepsize);
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
        assetCost = getpaircost(false);
        depth = _stds > 0 ? (1.*(blu - srange.s_threshold) / _stds) : 1;
        volume = min((ti.cR / (assetCost*prm.mc))*(1 + depth), ti.cR / assetCost);
        volume = STEPFUNC(volume, 50);
        if (volume == 0){
          goto LOGANDRETURN;
        }
        bool rcode = ti.shortat(blu, __dtm, volume, assetCost, pmcw, pscw);
        if (rcode){
          setsthreshold(blu + stepsize);
          actionType = ATsell;
        }
      }
      else if (red < srange.liq_upper){
        if (ti.closeat(pmcw, pscw, &prm)){
          TTPrintNoTime("[.]close@%.2f@%s,realizedPNL=%.2f\n", red, __dtm.c_str(), ti.uPNL);
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
        assetCost = getpaircost(true);
        depth = _stds > 0 ? (1.*(srange.l_threshold - red) / _stds) : 1;
        volume = min((ti.cR / (assetCost*prm.mc))*(1 + depth), ti.cR / assetCost);
        volume = STEPFUNC(volume, 50);
        if (volume == 0){
          goto LOGANDRETURN;
        }
        bool rcode = ti.longat(red, __dtm, volume, assetCost, pmcw, pscw);
        if (rcode){
          setlthreshold(red - stepsize);
          actionType = ATBuy;
        }
      }
      else if (blu > srange.liq_lower){
        if (ti.closeat(pmcw, pscw, &prm)){
          actionType = ATclose;
        }
      }
    }break;
    default:break;
    }
  }

LOGANDRETURN:
  ;
}

void DBB0::__check_runtime_data_limit(){
  actionType = ATNone;
  tobj* ptobj = pmcw->ptobj;
  param& prm = ptobj->prm;
  tradeinfo& ti = ptobj->_tinfo;

  dSeries& _5s = ptobj->rtd->_5s;
  dSeries& _dy = ptobj->rtd->_dy;

  if (!pmcw->_mkdata.ready() || !pscw->_mkdata.ready()){
    return;
  }
  // runtime info
  if (pmcw->_mkdata.lastPrice == 0 || pscw->_mkdata.lastPrice == 0){
    wap = _5s.w.back();
    if (wap == 0){
      return;
    }
  }
  if (wap == 0){
    wap = pmcw->_mkdata.lastPrice - pscw->_mkdata.lastPrice;
  }
  blu = pmcw->_mkdata.bid - pscw->_mkdata.ask;
  red = pmcw->_mkdata.ask - pscw->_mkdata.bid;
  spd = red - blu;

  TTPrint("s=%s,wap=%.2f,blue=%.2f,red=%.2f,spread=%.2f,st=%.2f,lt=%.2f,rng=%.2f\n",
    ptobj->pid().c_str(), wap, blu, red, spd,
    srange.s_threshold, srange.l_threshold, crng.tvrange);

  crng.update(blu, red, daily_spread);
  __mp = fixMarketPriceForBT();

  //spread应该是log-normal分布!
  //如果status是NOPOS的话，__mp是不重要的!
  bool usewap = false;
  if (ti.statuz != NP){
    if (CConfig::R().isbacktest){/*如果red blue差得太离谱，就直接用wap*/
      if (abs(spd - _5s.ind.spdma.back()) > 3 * _5s.ind.spdstd.back()){
        usewap = true;
      }
    }
  }
  ti.updatepnl((usewap ? wap : __mp));

  if (ti.statuz > DELIMITER){
    //bool co1 = ti.checkOrder(pmcw);
    //bool co2 = ti.checkOrder(pscw);
    return;
  }
  //updateDBB();
  //if (cutLossOrGain(ptobj)){
  //  goto LOGANDRETURN;
  //}
  //if (skiptoday == __ymd){//如果当天亏了就不再开单了
  //  goto LOGANDRETURN;
  //}

  double depth = 0.0;
  //if (ti.__tag == SHORTATLOW_SPREAD || ti.__tag == LONGATHIGH_SPREAD){
  //  target_lose = -target_gain * 2;
  //}
  double stepsize = prm.step + daily_spread;
  double _stds = _5s.ind.std.back();
  double _stdD = _dy.ind.std.back();
  double _rsis = _5s.ind.rsi.back();
  double _rsiD = _dy.ind.rsi.back();
  double _emas = _5s.ind.ma.back();
  double _emaD = _dy.ind.ma.back();

  // order logic with signal
  {
    bool shrtcondition = (blu > srange.s_threshold);
    bool longcondition = (red < srange.l_threshold);
    //bool closcondition = ();

    switch (ti.statuz){
      //////////////////////////////////////////////////////////////////////////
    case NP:
    {
      if (shrtcondition){
        assetCost = getpaircost(false);
        depth = _stds>0 ? (1.*(red - srange.s_threshold) / _stds) : 1;
        volume = min((ti.cR / (assetCost*prm.mc))*(1 + depth), ti.cR / assetCost);
        volume = STEPFUNC(volume, 20);
        if (volume == 0){
          goto LOGANDRETURN;
        }
        bool rcode = ti.shortat(red, __dtm, volume, assetCost, pmcw, pscw);
        if (rcode){
          //ti.opendt = __dtm;
          setsthreshold(red + stepsize);//TODO
          actionType = ATsell;
        }
      }
      else if (longcondition){
        assetCost = getpaircost(true);
        depth = _stds > 0 ? (1.*(srange.l_threshold - blu) / _stds) : 1;
        volume = min((ti.cR / (assetCost*prm.mc))*(1 + depth), ti.cR / assetCost);
        volume = STEPFUNC(volume, 50);
        if (volume == 0){
          goto LOGANDRETURN;
        }
        bool rcode = ti.longat(blu, __dtm, volume, assetCost, pmcw, pscw);
        if (rcode){
          //ti.opendt = __dtm;
          setlthreshold(blu - stepsize);
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
        assetCost = getpaircost(false);
        depth = _stds > 0 ? (1.*(red - srange.s_threshold) / _stds) : 1;
        volume = min((ti.cR / (assetCost*prm.mc))*(1 + depth), ti.cR / assetCost);
        volume = STEPFUNC(volume, 50);
        if (volume == 0){
          goto LOGANDRETURN;
        }
        bool rcode = ti.shortat(red, __dtm, volume, assetCost, pmcw, pscw);
        if (rcode){
          setsthreshold(red + stepsize);
          actionType = ATsell;
        }
      }
      else if (blu < srange.liq_upper){
        if (ti.closeat(pmcw, pscw, &prm)){
          TTPrintNoTime("[.]close@%.2f@%s,realizedPNL=%.2f\n", red, __dtm.c_str(), ti.uPNL);
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
        assetCost = getpaircost(true);
        depth = _stds > 0 ? (1.*(srange.l_threshold - blu) / _stds) : 1;
        volume = min((ti.cR / (assetCost*prm.mc))*(1 + depth), ti.cR / assetCost);
        volume = STEPFUNC(volume, 50);
        if (volume == 0){
          goto LOGANDRETURN;
        }
        bool rcode = ti.longat(blu, __dtm, volume, assetCost, pmcw, pscw);
        if (rcode){
          setlthreshold(blu - stepsize);
          actionType = ATBuy;
        }
      }
      else if (red > srange.liq_lower){
        if (ti.closeat(pmcw, pscw, &prm)){
          actionType = ATclose;
        }
      }
    }break;
    default:break;
    }
  }

LOGANDRETURN:
  ;
}

void DBB0::check_market_data(TickType ttid){
  if (ttid == BID_PRICE || ttid == ASK_PRICE){
    update_trading_time();
    check_runtime_data();
  }
  else if (ttid == SEN_5SWAPSPD){
    update_trading_time();
    check_bar_data();
  }
}

void DBB0::logTick(TickType ttid, CSTR& sym, CSTR& val){
  //if (CConfig::R().isdebug){
  if (1){
    if (ttid == BID_PRICE || ttid == ASK_PRICE || ttid == SEN_5SWAPSPD){
      TTPrint("[tick][%s]%s=%s\n",
        TTField[ttid], sym.c_str(), val.c_str());
    }
  }
}
