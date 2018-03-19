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

//#include <czmq.h>
#include <cwrapper.h>
#include <cband.h>
#include <tradeinfo.h>
#include <hook.h>
#include <idxData.h>
#include <gconfig.h>
#include <scoreboard.h>
#include <mq.h>

extern std::atomic<bool> g_shutdown;

regex tradeinfo::decimal2("(\\d+\\.\\d\\d)[\\d]*");
regex tradeinfo::decimal3("(\\d+\\.\\d\\d\\d)[\\d]*");
regex tradeinfo::nospace("\\n\\s+");

const char* EST2STR(POSSTATUS st){
  static const char* const p[] = {
    "NOPOS",
    "OTL",//open trade with long position
    "OTS",//open trade with short position
    "DELIMITER",
    "LWaitC", //Original long position waiting to be closed
    "LWaitC2",//Ackownledged
    "SWaitC", //Original short position waiting to be closed
    "SWaitC2",
    "NWaitL", //Original no position waiting buy order filled
    "NWaitL2",
    "NWaitS", //Original no position waiting sell order filled
    "NWaitS2"
  };
  return p[(int)st];
}

void param::print(){
  //char buf[1024];
  //sprintf(buf, "pid=%s,from=%s,to=%s,win=%d,step=%.2f,bbh=%.2f,bbl=%.2f,meat=%.2f,"
  //  "dst=%.2f,daily_win=%d,mc=%d,smr=%d,ms=%d,"
  //  "cash=%.2f,y=%.2f,sharpe=%.2f,maxDD=%.2f,maxDDD=%d\n",
  //  pid.c_str(), f.c_str(), t.c_str(), win, step, bbh, bbl, meat,
  //  dst, dwin, mc, smr,
  //  ms, cash, y, sr, mdd, md3);
  //TTPrint(buf);
  //printf(buf);
}
//////////////////////////////////////////////////////////////////////////
/**
status             uPNL
----------------------------------------------------
NOPOS              0
OTL           marketvalue - yourInv
OTS           marketvalue - yourInv
DELIMITER          x
LWaitC             -
SWaitC             -
NWaitL             -
NWaitS             -
*/
void tradeinfo::updatepnl(double mp){//market price
  thread_local double previousPNL = 0.0f;
  switch(statuz){
  case NP:
    uPNL = 0.0;break;
  case OTL:
    uPNL=uPNL4Long(mp);break;
  case OTS:
    uPNL=uPNL4Short(mp);break;
  case DELIMITER:break;
  case LWaitC:break;
  case SWaitC:break;
  case NWaitL:break;
  case NWaitS:break;
  case LWaitC2:break;
  case SWaitC2:break;
  case NWaitL2:break;
  case NWaitS2:break;
  default: SENTOSAERROR;
  }
  if (uPNL!=0.0f){
    TTPrint("<%s>uPNL=%.2f,TOTALVOL=%.2f,AVGPRICE=%.3f,marketPrice=%.3f,s=%s\n",
      syms.c_str(),uPNL,TOTALVOL(),AVGPRICE(), mp, EST2STR(statuz));
  }
  if(previousPNL!=0 && uPNL!=0){
    if (abs(uPNL - previousPNL)>1000){SENTOSAERROR;}
  }
  if(uPNL!=0){
    previousPNL = uPNL;
  }
}

bool tradeinfo::longat(double mp, const string& dt, uint64_t vol, double cost_,
  instrument* pmcw, instrument* pscw)
{
  if(vol==0){return false;}
  //if it is pair trading, it means "SHORT SLAVE"
  if (pscw && pscw->cwtp == SLAVE && !pscw->_mkdata.shortable){
    TTPrint("[warning][%s]%s is not shortable!\n", __FUNCTION__,
      pscw->c.symbol.c_str());
    return false;
  }
  TTPrint("[%s](%s)Try long@%.3f\n", __FUNCTION__, pmcw->ptobj->pid().c_str(), mp);
  if (!CConfig::R().isbacktest){
    __sendorder(pmcw, vol, true);
    __sendorder(pscw, vol, false);
    statuz = NWaitL;
  }
  return false;
}

// should return trade price/volume
bool tradeinfo::shortat(double mp, const string& dt, uint64_t vol, double cost_,
  instrument* pmcw, instrument* pscw)
{
  if(vol==0){return false;}
  if (!pmcw->_mkdata.shortable){
    TTPrint("[warning][%s]%s is not shortable!\n", __FUNCTION__,
      pmcw->c.symbol.c_str());
    return false;
  }
  //因为是market order,所以不一定以这个价格执行
  TTPrint("[%s](%s)Try short@%.3f\n", __FUNCTION__, pmcw->ptobj->pid().c_str(),mp);
  if (!CConfig::R().isbacktest){
    __sendorder(pmcw, vol, false);
    __sendorder(pscw, vol, true);
    statuz = NWaitS;
  }
  return false;
}

// 2014-10-21 When close, there is no need to check if the stock is shortable!
bool tradeinfo::closeat(instrument* pmcw, instrument* pscw,
  const param* _param, double adj1, double adj2)
{
  //if (!CConfig::R().isbacktest){
    uint64_t vol = abs(TOTALVOL());
    if(vol==0){return false;}
    if (statuz == OTL){
      __sendorder(pmcw, vol, false);
      __sendorder(pscw, vol, true);
      statuz = LWaitC;
    }else if (statuz == OTS){
      __sendorder(pmcw, vol, true);
      __sendorder(pscw, vol, false);
      statuz = SWaitC;
    }
  //}
  return false;
}

//TODO should return order id. 0 if error happens!
void tradeinfo::__sendorder(instrument* p, uint64_t vol, bool islong){
  if (p != nullptr){
    int quantity = islong ? vol : -1*vol;
      // 采用20分钟(1000秒)的波动率*2 -> 如果正态，应该有90%的可能性在20分钟内fill
      double allowedchange = p->ptobj->rtd->_5s.ind.std.back()*2;
      if (allowedchange==0){
        TTPrint("[%s]ERROR:<%s>,p->ptobj->rtd->_5s.ind.std.back()=0",
          __FUNCTION__, p->c.symbol.c_str());
        allowedchange = 0.06;
      }

      char msg[128];
      sprintf(msg, "%s|%s|%d|%.2f|%.2f",
          CR(lmtorder).c_str(),
          p->c.symbol.c_str(),
          quantity,
          getTradePriceL(p->_mkdata,islong),
          allowedchange);
      TTPrint("[%s]%s\n", __FUNCTION__, msg);

      algoMQ::R().sendmq(msg);
  }
}

//statetransfer(CANCELLED|API_CANCELLED)
//statetransfer(FILLED,fillp,quant) -> affect PNL,vo,ps etc

static mutex statetransfer_mtx;
// TODO - const Order& should be enough
void tradeinfo::statetransfer(ORDERSTATUS newostatus, instrument* i){
  lock_guard<mutex> g(statetransfer_mtx);
  POSSTATUS initial_statuz = statuz;
  switch(newostatus) {
    case NEWBORN:{
      switch(statuz) {
        case LWaitC:
          statuz=LWaitC2;break;
        case SWaitC:
          statuz=SWaitC2;break;
        case NWaitL:
          statuz=NWaitL2;break;
        case NWaitS:
          statuz=NWaitS2;break;
        default:SENTOSAERROR;break;
      }
    }break;
    case PENDING_SUBMIT:break;
    case PENDING_CANCEL:break;
    case PRESUBMITTED:break;
    case SUBMITTED:{}break;//DONOT UPDATE PNL - difficult
    case INACTIVE:break;
    case API_PENDING:break;
    case API_CANCELLED:
    case CANCELLED:{
      Order* po = i->lorders.tail();
      if(po==nullptr){SENTOSAERROR;return;}
      if (po->filled == 0) {
        switch(statuz) {
          case LWaitC2:
          statuz=OTL;break;
          case SWaitC2:
          statuz=OTS;break;
          case NWaitL:
          case NWaitS:
          statuz=NP;break;
          default:SENTOSAERROR;break;
        }
      } else {
        //deal with partial fill if any
        long fil = (long)po->filled;
        double avg = (double)po->avgFillPrice;
        switch(statuz) {
          case LWaitC2: {
            double tmp = fil * (avg - AVGPRICE());
            vo.back() -= fil;
            i->_mkdata.pos_-=fil;
            //i->_mkdata.avgP=0.0;
            gain(tmp);
            uPNL = uPNL4Long(i->_mkdata.bid);
            statuz=OTL;
          }break;
          case SWaitC2: {
            double tmp = fil * (AVGPRICE()-avg);
            vo.back() += fil;
            i->_mkdata.pos_+=fil;
            //i->_mkdata.avgP=0.0;
            gain(tmp);
            uPNL = uPNL4Short(i->_mkdata.ask);
            statuz=OTS;
          }break;
          case NWaitL: {
            vo.emplace_back(fil);
            ps.emplace_back(avg);
            i->_mkdata.pos_+=fil;
            i->_mkdata.avgP=AVGPRICE();
            gain(.0f);
            statuz=OTL;
          }break;
          case NWaitS: {
            vo.emplace_back(-fil);
            ps.emplace_back(avg);
            i->_mkdata.pos_-=fil;
            i->_mkdata.avgP=AVGPRICE();
            gain(.0f);
            statuz=OTS;
          }break;
          default:SENTOSAERROR;break;
        }
      }
      //i->lorders.count--;// if the middle one is cancel??
    }break;
    case FILLED:{
      aNum++;
      Order* po = i->lorders.tail();
      if(po==nullptr){SENTOSAERROR;return;}
      lastp = (double)po->lastFillPrice; //lmtPrice是fill price
      long tq = po->totalQuantity;
      double curInv = lastp * tq;
      //update pnl
      double yourInv=0.0f,marketvalue=0.0f;
      switch(statuz) {
        case LWaitC2:{
          double tmp = uPNL4Long(lastp);
          gain(tmp);
          reset();
          i->_mkdata.pos_=0.0;
          i->_mkdata.avgP=0.0;
        }break;
        case SWaitC2: {
          double tmp = uPNL4Short(lastp);
          gain(tmp);
          reset();//statuz is changed inside
          i->_mkdata.pos_=0.0;
          i->_mkdata.avgP=0.0;
        }break;
        case NWaitL:{
          ps.emplace_back(lastp);
          vo.emplace_back(tq);
          i->_mkdata.pos_ = TOTALVOL();
          i->_mkdata.avgP = AVGPRICE();
          inve += curInv;
          cR = lcc - curInv;
          statuz=OTL;
        }break;
        case NWaitS:{
          ps.emplace_back(lastp);
          vo.emplace_back(tq);
          i->_mkdata.pos_ = TOTALVOL();
          i->_mkdata.avgP = AVGPRICE();
          inve += curInv;
          cR = lcc - curInv;
          statuz=OTS;
        }break;
        default:SENTOSAERROR;break;
      }
    }break;
    default:SENTOSAERROR;break;
  }
  //if(initial_statuz!=statuz){
  //  i->ptobj->saveTradeInfo();
  //}

  TTPrint("[%s](%d)<%s>ORDERSTATUS:%s,statuz:%s->%s\n",
        __FUNCTION__, __LINE__, syms.c_str(),
        ORDST2STR(newostatus), EST2STR(initial_statuz), EST2STR(statuz));
}

tradeinfo::tradeinfo():mindurindex(-1), maxdurindex(-1){
  uPNL = 0;
  statuz = NP;
  aNum = 0;
  stratwin = 0.;
  mindur = INT_MAX;
  maxdur = 0;
  avgDur = 0;
}

double tradeinfo::total_realizedPNL(){
  //double r=accumulate(pnls.begin(), pnls.end(), 0.0) - 4*cdts.size();
  //return r;
  return aPNL;
}

void tradeinfo::print_realized_pnls(){
  /*for (uint64_t i = 0; i < pnls.size(); ++i){
    TTPrintNoTime("[%s]%2.f\n", cdts[i].c_str(), pnls[i]);
  }*/
}

//算法的性能
void tradeinfo::performance(){
  /*
  TTPrintNoTime("\n******************** Performance Report *******************\n");
  TTPrintNoTime("Trade Symbols:%s\n", syms.c_str());
  TTPrintNoTime("Realized PNL number:%d\n", pnls.size());
  TTPrintNoTime("TotalaccumPNL(realized+unrealized):%.2f\n",total_accumulatedPNL());
  if (pnls.size()<5){
    return;
  }

  double minPNL=1000, maxPNL=-1000;
  uint64_t minPNLindex =0, maxPNLindex =0;
  double negPNL=0, posPNL=0;
  uint64_t negNum=0, posNum=0;
  double NPratio=0;//negative positive ratio
  for (uint64_t i = 0; i < pnls.size();++i){
    if (minPNL>pnls[i]){
      minPNL = pnls[i];
      minPNLindex = i;
    }else if (maxPNL<pnls[i]){
      maxPNL = pnls[i];
      maxPNLindex = i;
    }
    if (pnls[i]>=0){
      posNum++;
      posPNL += pnls[i];
    }else{
      negNum++;
      negPNL += pnls[i];
    }
  }
  NPratio = negPNL / posPNL;
  TTPrintNoTime("# of positive PNL:%d\n", posNum);
  TTPrintNoTime("# of negative PNL:%d\n", negNum);
  TTPrintNoTime("minPNL:%.2f occurs @ [open=%s,close=%s]\n", minPNL,
    oc[minPNLindex * 2].c_str(), oc[minPNLindex * 2+1].c_str());
  TTPrintNoTime("maxPNL:%.2f occurs @ [open=%s,close=%s]\n", maxPNL,
    oc[maxPNLindex * 2].c_str(), oc[maxPNLindex * 2 + 1].c_str());
  TTPrintNoTime("value of realized positive PNL:%.2f\n", posPNL);
  TTPrintNoTime("value of realized negative PNL:%.2f\n", negPNL);
  TTPrintNoTime("value of final unrealized PNL:%.2f\n", total_accumulatedPNL() - (posPNL + negPNL));

  {
    tradefreq();
    TTPrintNoTime("minDur:%d seconds = %.2f trade days\n", mindur, 1.0*mindur / SECONDS_IN_ONE_DAY);
    if (2 * mindurindex < oc.size()){
      TTPrintNoTime("\tfrom %s to %s[PNL=%.2f]\n",
        oc[2 * mindurindex].c_str(), oc[2 * mindurindex + 1].c_str(), pnls[mindurindex]);
    }
    TTPrintNoTime("maxDur:%d seconds = %.2f trade days\n",
      maxdur, 1.0*maxdur / SECONDS_IN_ONE_DAY);
    if (2 * maxdurindex < oc.size()){
      TTPrintNoTime("\tfrom %s to %s[PNL=%.2f]\n",
        oc[2 * maxdurindex].c_str(), oc[2 * maxdurindex + 1].c_str(), pnls[maxdurindex]);
    }
    TTPrintNoTime("avgDur:%d seconds = %.2f trade days\n",
      avgDur, 1.0*avgDur / SECONDS_IN_ONE_DAY);
  }

  TTPrintNoTime("# of cut gain:%d\n", num_cutgain);
  TTPrintNoTime("# of cut loss:%d\n", num_cutloss);
  TTPrintNoTime("stratwin=%.2f\n", stratwin);
  TTPrintNoTime("RETURN:%%%.2f\n", acc_return.back()*100);
  TTPrintNoTime("***************************************\n");
  */
}

void tradeinfo::calsharpe(const string& f, const string& t){
  hData tmp = *refData::R(f,t).getspy500();
  /*
  tmp.dss.removeFirstN(tmp.dsize() - acc_return.size());
  const VECDL& spywap = tmp.dss.wap;
  double rspy = (spywap.back() - spywap.front()) / spywap.front();
  VECDL spy = price2return(spywap);
  sharpeR = sharpe(acc_return.back(), rspy, acc_return, spy);
  printf("%s,sharpe=%.2f,yield=%.2f\n",
    symbols.c_str(), sharpeR, acc_return.back());
  */
}

void tradeinfo::calDD(bool logit){
  /*double localMaxR = -1000;
  uint64_t maIndex = 0;
  double localMinR = 1000;
  uint64_t miIndex = 0;
  string rd[2];//recovery dates "from" and "to"
  uint64_t recoverTime;
  for (uint64_t i = 0; i < acc_return.size(); ++i){
    if (localMaxR < acc_return[i]){
      localMaxR = acc_return[i];
      localMinR = 1000;
      maIndex = i;
    }
    if (localMinR > acc_return[i]){
      localMinR = acc_return[i];
      miIndex = i;
    }
    if (miIndex > maIndex){
      double tmp = localMaxR - localMinR;
      if (maxDD < tmp){
        maxDD = tmp;
      }
      string t1 = MOPENTM(days[miIndex]);
      string t2 = MOPENTM(days[maIndex]);
      recoverTime = (str2time_t(t1) - str2time_t(t2))/(SECONDS_IN_ONE_DAY);
      if (maxDDD<recoverTime){
        maxDDD = recoverTime;
        rd[0] = days[maIndex];
        rd[1] = days[miIndex];
      }
    }
  }
  //if(logit){
  if(1){
    printf("%s,MaxDD=%.2f,maxDDD=%lu[%s~%s]\n",
    syms.c_str(), maxDD, maxDDD, rd[0].c_str(), rd[1].c_str());
  }
  if (logit)TTPrintNoTime("%s,MaxDD=%.2f,maxDDD=%lu[%s~%s]\n",
    syms.c_str(), maxDD, maxDDD, rd[0].c_str(), rd[1].c_str());*/
}

void tradeinfo::tradefreq(){
  /*if (mindurindex == -1){
    // trade freq
    uint64_t sz1 = adjs.size();
    if (sz1 == cdts.size() && (sz1+1) * 2 >= oc.size()){
      for (uint64_t i = 0; i < adjs.size(); ++i){
        if (mindur>adjs[i]){
          mindur = adjs[i];
          mindurindex = (int)i;
        }
        if (maxdur < adjs[i]){
          maxdur = adjs[i];
          maxdurindex = (int)i;
        }
      }
    }
    else{
      SENTOSAERROR;
    }
  }*/
}

bool tradeinfo::selfCheck(bool loadfromfile){
    if (!loadfromfile && statuz > DELIMITER){
      return false;
    }
    bool HasChange = false;
    if(loadfromfile && statuz > DELIMITER){
      HasChange = true;
    }

    // 1. verify status and trading volume
    double tmp = TOTALVOL();
    TTPrint("[%s]<%s>totvol=%.2f,oldstatus=%s\n", __FUNCTION__,
        syms.c_str(), tmp, EST2STR(statuz));
    //这个判断很有必要!
    if (tmp == 0 && statuz != NP){
      TTPrint("[%s]<%s>ERROR:totvol=%.2f,oldstatus=%s,newstatus=%s\n", __FUNCTION__,
        syms.c_str(), tmp, EST2STR(statuz),"NOPOS");
      statuz = NP;
      uPNL = 0;
      HasChange = true;
    }else if (tmp > 0 && statuz != OTL){
      TTPrint("[%s]<%s>ERROR:totvol=%.2f,oldstatus=%s,newstatus=%s\n", __FUNCTION__,
        syms.c_str(), tmp, EST2STR(statuz), "OTL");
      statuz = OTL;
      HasChange = true;
    }else if(tmp<0 && statuz!= OTS){
      TTPrint("[%s]<%s>ERROR:totvol=%.2f,oldstatus=%s,newstatus=%s\n", __FUNCTION__,
        syms.c_str(), tmp, EST2STR(statuz), "OTS");
      statuz = OTS;
      HasChange = true;
    }
    // 2. position and uPNL
    if(statuz==NP && uPNL!=0){
      uPNL=0;
      HasChange = true;
    }

    // 3. verify cashRemaining and investment
    tmp = ValueAtTrade();
    if (abs(tmp - inve)>1){
      inve = tmp;
      HasChange = true;
    }
    if (abs(lcc - (inve + cR)) > 0.1){
      cR = lcc - inve;
      HasChange = true;
    }
    return HasChange;
  }
