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

#ifndef __DATASOURCE__
#define __DATASOURCE__

#if defined(_WIN32) || defined(_WIN64)
#include <windows.h>
#endif

#include "util.h"
#include "gconfig.h"
#include <sentosaconfig.h>
#include <ta_libc.h>
#include <bstTime.h>
#include <swindow.h>

enum INDTYPE{ INDSPD=0, INDWAP, INDWAPVOL, INDBAR, INDALL};

// TA indicator for historical data
template <typename T>
struct tsInd{
  T std;   T ma;
  T rsi;
  T roc;
  T obv;
  //Hilbert Transform Trend Line
  T HTtrl; T HTsin; T HTsil;
  T arU;   T arD;   T arOSC;
  T mfi;
  T ad;
  // spread std -only for pair trading
  T spdstd; T spdma;
  T bbdsL;//Bollinger Bands
  T bbdsM;//Bollinger Bands
  T bbdsU;//Bollinger Bands

  template<class Archive>
  void serialize(Archive & ar) {
    ar(cereal::make_nvp("dev",std.back()),
        cereal::make_nvp("ma",ma.back()),
        cereal::make_nvp("rsi",rsi.back()),
        cereal::make_nvp("roc",roc.back())
    );
  }

  static const char* LogHead(int i=0){
    if (i == 0){
      return "std,ma,rsi,roc,HTtrl,HTsin,HTsil";
    } else if (i == 1){
      return "stdD,maD,rsiD,rocD,obvD,HTtrlD,HTsinD,HTsilD,arUD,arDD,arOSCD,mfiD,adD";
    } else if (i == 2){
      return "stdM,maM,rsiM,rocM,obvM,HTtrlM,HTsinM,HTsilM,arUM,arDM,arOSCM,mfiM,adM";
    } else if (i == 3){
      return "stdS,maS,rsiS,rocS,obvS,HTtrlS,HTsinS,HTsilS,arUS,arDS,arOSCS,mfiS,adS";
    }
    return "";
  }

  string toStr(int i = 0){
    char buf[512];
    if (i == 0){//spread intra-day
      //std.print();
      sprintf(buf, "std=%.4f,ma=%.4f,rsi=%.4f,roc=%.4f,h1=%.4f,h2=%.4f,h3=%.4f,obv=%.4f",
                std.back(), ma.back(), rsi.back(), roc.back(),
                HTtrl.back(), HTsin.back(), HTsil.back(), obv.back());
    } else if (i == 1){//spread day
      sprintf(buf, "%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f",
                std.back(), ma.back(), rsi.back(), roc.back(), obv.back(),
                HTtrl.back(), HTsin.back(), HTsil.back(),
                arU.back(), arD.back(), arOSC.back(),
                mfi.back(), ad.back());
    } else if (i == 2){//master intra-day
      sprintf(buf, "%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f",
        std.back(), ma.back(), rsi.back(), roc.back(), obv.back(),
        HTtrl.back(), HTsin.back(), HTsil.back(),
        arU.back(), arD.back(), arOSC.back(),
        mfi.back(), ad.back());
    } else if (i == 3){//slave intra-day
      sprintf(buf, "%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f",
        std.back(), ma.back(), rsi.back(), roc.back(), obv.back(),
        HTtrl.back(), HTsin.back(), HTsil.back(),
        arU.back(), arD.back(), arOSC.back(),
        mfi.back(), ad.back());
    }
    return buf;
  }

};

template <typename T>
struct tseries{
  //"ATHM"  for normal data, "ATHM-BITA" for spread
  string symbol;

  //only valid for spread data
  tseries* pm = nullptr;
  tseries* ps = nullptr;

  // only for spread
  T b; // bid(blue)
  T r; // ask(red)
  T spd;// = r-b
  // only for bar data
  T o; T h; T l; T c; T w; T v;

  tsInd<T> ind;

  // For backtest purpose, empty in real time data
  T vrange;//only valid for daily data
  T opjump;
  VECST dt;

  template<class Archive>
  void serialize(Archive & ar) {
    ar(CEREAL_NVP(symbol),
        //CEREAL_NVP(o),CEREAL_NVP(h),CEREAL_NVP(l),CEREAL_NVP(c),
        CEREAL_NVP(w),CEREAL_NVP(v),
        //CEREAL_NVP(dt),
        CEREAL_NVP(vrange),
        CEREAL_NVP(ind)
    );
  }

  string getLastUpdate(string name){
    char buf[256]={};
    snprintf(buf,1024,
        "{\"%s\":{\"symbol\":\"%s\",\"w\":%.3f,\"v\":%u}}",
        name.c_str(),symbol.c_str(),w.back(),(uint32_t)v.back());
    return string(buf);
  }

  bool pushRTBarStr(const string& s){
    VECST vs = splitv2(s, ':');
    if (vs.size()!=6){
      return false;
    }else{
      o.push_back(atof(vs[0].c_str()));
      h.push_back(atof(vs[1].c_str()));
      l.push_back(atof(vs[2].c_str()));
      c.push_back(atof(vs[3].c_str()));
      w.push_back(atof(vs[4].c_str()));
      v.push_back(atof(vs[5].c_str()));
    }
    return true;
  }

  void calTAindicator(int wlen, INDTYPE it=INDALL){
    if(wlen <= 0) return;
    int _idx = wlen - 1;
    int ob,n,endIndex,sz;
    double result{.0};
    if (!w.empty()){
      const double* p = &w[0];
      sz = w.size();
      endIndex = sz - 1;
      if (it == INDALL || it == INDWAP || it == INDWAPVOL){
        ind.std.resize(sz);
        TA_STDDEV(0, endIndex, p, wlen, 1, &ob, &n, &result);
        ind.std.push_back(result);
        if (endIndex + 1 - (wlen - 1) != n){ SENTOSAERROR; }

        ind.ma.resize(sz);
        TA_MA(0, endIndex, p, wlen, TA_MAType_EMA, &ob, &n, &result);
        ind.ma.push_back(result);
        if (endIndex + 1 - (wlen - 1) != n){ SENTOSAERROR; }

        
        {//改进了的RSI!
          VECDL vd(p, p + sz);
          auto i = unique(vd.begin(), vd.end());
          int n = distance(vd.begin(), i);
          if (n < 10){
            result = 50;
          }else{
            TA_RSI(0, endIndex, p, wlen - 1, &ob, &n, &result);
            if (endIndex + 1 - (wlen - 1) != n){
              SENTOSAERROR;
            }
          }
          ind.rsi.resize(sz);
          ind.rsi.push_back(result);
        }

        ind.roc.resize(sz);
        TA_ROC(0, endIndex, p, wlen-1, &ob, &n, &result);
        ind.roc.push_back(result);
        if (endIndex + 1 - (wlen - 1) != n){ SENTOSAERROR; }

        ind.bbdsU.resize(sz);
        ind.bbdsM.resize(sz);
        ind.bbdsL.resize(sz);
        double r1, r2, r3;
        TA_BBANDS(0, endIndex, p, wlen, 2.0, 2.0, TA_MAType_EMA, &ob, &n,
          &r1, &r2, &r3);
        ind.bbdsU.push_back(r1);
        ind.bbdsM.push_back(r2);
        ind.bbdsL.push_back(r3);
        if (endIndex + 1 - (wlen - 1) != n){ SENTOSAERROR; }

        // endIndex must be equal to or greater than 63
        if (endIndex>=63){
          //ind.HTtrl.resize(sz-63); ind.HTsin.resize(sz-63); ind.HTsil.resize(sz-63);
          ind.HTtrl.resize(sz); ind.HTsin.resize(sz); ind.HTsil.resize(sz);
          TA_HT_TRENDLINE(0, endIndex, p, &ob, &n, &ind.HTtrl[0+63]);
          TA_HT_SINE(0, endIndex, p, &ob, &n, &ind.HTsin[0+63], &ind.HTsil[0+63]);
          if (endIndex + 1 - (63) != n){ SENTOSAERROR; }
        }
      }
      if (!v.empty() && (it == INDALL || it == INDWAPVOL)){
        sz = v.size();
        endIndex = sz - 1;
        ind.obv.resize(sz);
        TA_OBV(0, endIndex, p, &v[0], &ob, &n, &ind.obv[0]);
        if (endIndex + 1 != n){ SENTOSAERROR; }
      }
    }
    if (!h.empty() && !l.empty() && !c.empty() && !v.empty() && (it == INDALL || it == INDBAR))
    {
      sz = h.size();
      endIndex = sz - 1;
      ind.arD.resize(sz); ind.arU.resize(sz); ind.arOSC.resize(sz);
      TA_AROON(0,    endIndex, &h[0], &l[0], wlen - 1, &ob, &n, &ind.arD[_idx], &ind.arU[_idx]);
      TA_AROONOSC(0, endIndex, &h[0], &l[0], wlen - 1, &ob, &n, &ind.arOSC[_idx]);
      if (endIndex + 1 - (wlen - 1) != n){ SENTOSAERROR; }
      ind.mfi.resize(sz);
      TA_MFI(0, endIndex, &h[0], &l[0], &c[0], &v[0], wlen-1, &ob, &n, &ind.mfi[_idx]);
      if (endIndex + 1 - (wlen - 1) != n){ SENTOSAERROR; }
      ind.ad.resize(sz);
      TA_AD(0, endIndex, &h[0], &l[0], &c[0], &v[0], &ob, &n, &ind.ad[0]);
      if (endIndex + 1 != n){ SENTOSAERROR; }
    }
    if (!spd.empty() && (it == INDALL || it == INDSPD)){
      sz = spd.size();
      endIndex = sz - 1;
      const double* p2 = &spd[0];
      ind.spdstd.resize(sz);
      TA_STDDEV(0, endIndex, p2, wlen, 1, &ob, &n, &ind.spdstd[_idx]);
      if (endIndex + 1 - (wlen - 1) != n){ SENTOSAERROR; }
      ind.spdma.resize(sz);
      TA_MA(0, endIndex, p2, wlen, TA_MAType_EMA, &ob, &n, &ind.spdma[_idx]);
      if (endIndex + 1 - (wlen - 1) != n){ SENTOSAERROR; }
    }
  }

  int getIdx(CSTR& s){
    uint64_t i = 0;
    for (; i < dt.size(); ++i){
      if (s == dt[i]){
        return i;
      }
    }
    return -1;
  }

  void resizeBar(int len){
    o.resize(len);
    h.resize(len);
    l.resize(len);
    c.resize(len);
    w.resize(len);
    v.resize(len);
    dt.resize(len);
  }

  tseries(){}
  tseries(CSTR& s):symbol(s){}
  ~tseries(){
    //printf("{%s}%s\n", __FUNCTION__, symbol.c_str());
    if (pm){ delete pm; pm = nullptr; }
    if (ps){ delete ps; ps = nullptr; }
  }
};
/*
struct tseries1{
  //"ATHM"  for normal data, "ATHM-BITA" for spread
  string symbol;

  //only valid for spread data
  tseries* pm = nullptr;
  tseries* ps = nullptr;

  // only for spread
  swind b; // bid(blue)
  swind r; // ask(red)
  swind spd;// = r-b
  // only for bar data
  swind o; swind h; swind l; swind c; swind w; swind v;

  swind vrange;//only valid for daily data
  swind opjump;

  swind dt;

  tsInd<swind> ind;

  string toStr(){
    string r;
  }

  template<class Archive>
  void serialize(Archive & ar) {
    ar(CEREAL_NVP(symbol),
        CEREAL_NVP(o),
        CEREAL_NVP(h),
        CEREAL_NVP(l),
        CEREAL_NVP(c),
        CEREAL_NVP(w),
        CEREAL_NVP(v),
        CEREAL_NVP(dt),
        CEREAL_NVP(vrange)
    );
  }

  bool pushRTBarStr(const string& s){
    VECST vs = splitv2(s, ':');
    if (vs.size()!=6){
      return false;
    }else{
      o.push_back(atof(vs[0].c_str()));
      h.push_back(atof(vs[1].c_str()));
      l.push_back(atof(vs[2].c_str()));
      c.push_back(atof(vs[3].c_str()));
      w.push_back(atof(vs[4].c_str()));
      v.push_back(atof(vs[5].c_str()));
    }
    return true;
  }

  void calTAindicator(int wlen, INDTYPE it=INDALL){
    int _idx = wlen - 1;
    int ob,n,endIndex,sz;
    double result{.0};
    if (!w.empty()){
      const double* p = &w[0];
      sz = w.size();
      endIndex = sz - 1;
      if (it == INDALL || it == INDWAP || it == INDWAPVOL){
        ind.std.resize(sz);
        TA_STDDEV(0, endIndex, p, wlen, 1, &ob, &n, &result);
        ind.std.push_back(result);
        if (endIndex + 1 - (wlen - 1) != n){ SENTOSAERROR; }

        ind.ma.resize(sz);
        TA_MA(0, endIndex, p, wlen, TA_MAType_EMA, &ob, &n, &result);
        ind.ma.push_back(result);
        if (endIndex + 1 - (wlen - 1) != n){ SENTOSAERROR; }


        {//改进了的RSI!
          VECDL vd(p, p + sz);
          auto i = unique(vd.begin(), vd.end());
          int n = distance(vd.begin(), i);
          if (n < 10){
            result = 50;
          }else{
            TA_RSI(0, endIndex, p, wlen - 1, &ob, &n, &result);
            if (endIndex + 1 - (wlen - 1) != n){
              SENTOSAERROR;
            }
          }
          ind.rsi.resize(sz);
          ind.rsi.push_back(result);
        }

        ind.roc.resize(sz);
        TA_ROC(0, endIndex, p, wlen-1, &ob, &n, &result);
        ind.roc.push_back(result);
        if (endIndex + 1 - (wlen - 1) != n){ SENTOSAERROR; }

        ind.bbdsU.resize(sz);
        ind.bbdsM.resize(sz);
        ind.bbdsL.resize(sz);
        double r1, r2, r3;
        TA_BBANDS(0, endIndex, p, wlen, 2.0, 2.0, TA_MAType_EMA, &ob, &n,
          &r1, &r2, &r3);
        ind.bbdsU.push_back(r1);
        ind.bbdsM.push_back(r2);
        ind.bbdsL.push_back(r3);
        if (endIndex + 1 - (wlen - 1) != n){ SENTOSAERROR; }

        // endIndex must be equal to or greater than 63
        if (endIndex>=63){
          //ind.HTtrl.resize(sz-63); ind.HTsin.resize(sz-63); ind.HTsil.resize(sz-63);
          ind.HTtrl.resize(sz); ind.HTsin.resize(sz); ind.HTsil.resize(sz);
          TA_HT_TRENDLINE(0, endIndex, p, &ob, &n, &ind.HTtrl[0+63]);
          TA_HT_SINE(0, endIndex, p, &ob, &n, &ind.HTsin[0+63], &ind.HTsil[0+63]);
          if (endIndex + 1 - (63) != n){ SENTOSAERROR; }
        }
      }
      if (!v.empty() && (it == INDALL || it == INDWAPVOL)){
        sz = v.size();
        endIndex = sz - 1;
        ind.obv.resize(sz);
        TA_OBV(0, endIndex, p, &v[0], &ob, &n, &ind.obv[0]);
        if (endIndex + 1 != n){ SENTOSAERROR; }
      }
    }
    if (!h.empty() && !l.empty() && !c.empty() && !v.empty() && (it == INDALL || it == INDBAR))
    {
      sz = h.size();
      endIndex = sz - 1;
      ind.arD.resize(sz); ind.arU.resize(sz); ind.arOSC.resize(sz);
      TA_AROON(0,    endIndex, &h[0], &l[0], wlen - 1, &ob, &n, &ind.arD[_idx], &ind.arU[_idx]);
      TA_AROONOSC(0, endIndex, &h[0], &l[0], wlen - 1, &ob, &n, &ind.arOSC[_idx]);
      if (endIndex + 1 - (wlen - 1) != n){ SENTOSAERROR; }
      ind.mfi.resize(sz);
      TA_MFI(0, endIndex, &h[0], &l[0], &c[0], &v[0], wlen-1, &ob, &n, &ind.mfi[_idx]);
      if (endIndex + 1 - (wlen - 1) != n){ SENTOSAERROR; }
      ind.ad.resize(sz);
      TA_AD(0, endIndex, &h[0], &l[0], &c[0], &v[0], &ob, &n, &ind.ad[0]);
      if (endIndex + 1 != n){ SENTOSAERROR; }
    }
    if (!spd.empty() && (it == INDALL || it == INDSPD)){
      sz = spd.size();
      endIndex = sz - 1;
      const double* p2 = &spd[0];
      ind.spdstd.resize(sz);
      TA_STDDEV(0, endIndex, p2, wlen, 1, &ob, &n, &ind.spdstd[_idx]);
      if (endIndex + 1 - (wlen - 1) != n){ SENTOSAERROR; }
      ind.spdma.resize(sz);
      TA_MA(0, endIndex, p2, wlen, TA_MAType_EMA, &ob, &n, &ind.spdma[_idx]);
      if (endIndex + 1 - (wlen - 1) != n){ SENTOSAERROR; }
    }
  }

  int getIdx(CSTR& s){
    uint64_t i = 0;
    for (; i < dt.size(); ++i){
      if (s == dt[i]){
        return i;
      }
    }
    return -1;
  }

  void resizeBar(int len){
    o.resize(len);
    h.resize(len);
    l.resize(len);
    c.resize(len);
    w.resize(len);
    v.resize(len);
    dt.resize(len);
  }

  tseries2(){}
  tseries2(CSTR& s):symbol(s){}
  ~tseries2(){
    //printf("{%s}%s\n", __FUNCTION__, symbol.c_str());
    if (pm){ delete pm; pm = nullptr; }
    if (ps){ delete ps; ps = nullptr; }
  }
};

struct tseries2{
  //"ATHM"  for normal data, "ATHM-BITA" for spread
  string symbol;

  //only valid for spread data
  tseries* pm = nullptr;
  tseries* ps = nullptr;

  // only for spread
  VECDL b; // bid(blue)
  VECDL r; // ask(red)
  VECDL spd;// = r-b
  // only for bar data
  VECDL o; VECDL h; VECDL l; VECDL c; VECDL w; VECDL v;

  VECDL vrange;//only valid for daily data
  VECDL opjump;

  VECST dt;

  tsInd<VECDL> ind;

  string toStr(){
    string r;
  }

  template<class Archive>
  void serialize(Archive & ar) {
    ar(CEREAL_NVP(symbol),
        CEREAL_NVP(o),
        CEREAL_NVP(h),
        CEREAL_NVP(l),
        CEREAL_NVP(c),
        CEREAL_NVP(w),
        CEREAL_NVP(v),
        CEREAL_NVP(dt),
        CEREAL_NVP(vrange)
    );
  }

  bool pushRTBarStr(const string& s){
    VECST vs = splitv2(s, ':');
    if (vs.size()!=6){
      return false;
    }else{
      o.push_back(atof(vs[0].c_str()));
      h.push_back(atof(vs[1].c_str()));
      l.push_back(atof(vs[2].c_str()));
      c.push_back(atof(vs[3].c_str()));
      w.push_back(atof(vs[4].c_str()));
      v.push_back(atof(vs[5].c_str()));
    }
    return true;
  }

  void calTAindicator(int wlen, INDTYPE it=INDALL){
    int _idx = wlen - 1;
    int ob,n,endIndex,sz;
    double result{.0};
    if (!w.empty()){
      const double* p = &w[0];
      sz = w.size();
      endIndex = sz - 1;
      if (it == INDALL || it == INDWAP || it == INDWAPVOL){
        ind.std.resize(sz);
        TA_STDDEV(0, endIndex, p, wlen, 1, &ob, &n, &result);
        ind.std.push_back(result);
        if (endIndex + 1 - (wlen - 1) != n){ SENTOSAERROR; }

        ind.ma.resize(sz);
        TA_MA(0, endIndex, p, wlen, TA_MAType_EMA, &ob, &n, &result);
        ind.ma.push_back(result);
        if (endIndex + 1 - (wlen - 1) != n){ SENTOSAERROR; }


        {//改进了的RSI!
          VECDL vd(p, p + sz);
          auto i = unique(vd.begin(), vd.end());
          int n = distance(vd.begin(), i);
          if (n < 10){
            result = 50;
          }else{
            TA_RSI(0, endIndex, p, wlen - 1, &ob, &n, &result);
            if (endIndex + 1 - (wlen - 1) != n){
              SENTOSAERROR;
            }
          }
          ind.rsi.resize(sz);
          ind.rsi.push_back(result);
        }

        ind.roc.resize(sz);
        TA_ROC(0, endIndex, p, wlen-1, &ob, &n, &result);
        ind.roc.push_back(result);
        if (endIndex + 1 - (wlen - 1) != n){ SENTOSAERROR; }

        ind.bbdsU.resize(sz);
        ind.bbdsM.resize(sz);
        ind.bbdsL.resize(sz);
        double r1, r2, r3;
        TA_BBANDS(0, endIndex, p, wlen, 2.0, 2.0, TA_MAType_EMA, &ob, &n,
          &r1, &r2, &r3);
        ind.bbdsU.push_back(r1);
        ind.bbdsM.push_back(r2);
        ind.bbdsL.push_back(r3);
        if (endIndex + 1 - (wlen - 1) != n){ SENTOSAERROR; }

        // endIndex must be equal to or greater than 63
        if (endIndex>=63){
          //ind.HTtrl.resize(sz-63); ind.HTsin.resize(sz-63); ind.HTsil.resize(sz-63);
          ind.HTtrl.resize(sz); ind.HTsin.resize(sz); ind.HTsil.resize(sz);
          TA_HT_TRENDLINE(0, endIndex, p, &ob, &n, &ind.HTtrl[0+63]);
          TA_HT_SINE(0, endIndex, p, &ob, &n, &ind.HTsin[0+63], &ind.HTsil[0+63]);
          if (endIndex + 1 - (63) != n){ SENTOSAERROR; }
        }
      }
      if (!v.empty() && (it == INDALL || it == INDWAPVOL)){
        sz = v.size();
        endIndex = sz - 1;
        ind.obv.resize(sz);
        TA_OBV(0, endIndex, p, &v[0], &ob, &n, &ind.obv[0]);
        if (endIndex + 1 != n){ SENTOSAERROR; }
      }
    }
    if (!h.empty() && !l.empty() && !c.empty() && !v.empty() && (it == INDALL || it == INDBAR))
    {
      sz = h.size();
      endIndex = sz - 1;
      ind.arD.resize(sz); ind.arU.resize(sz); ind.arOSC.resize(sz);
      TA_AROON(0,    endIndex, &h[0], &l[0], wlen - 1, &ob, &n, &ind.arD[_idx], &ind.arU[_idx]);
      TA_AROONOSC(0, endIndex, &h[0], &l[0], wlen - 1, &ob, &n, &ind.arOSC[_idx]);
      if (endIndex + 1 - (wlen - 1) != n){ SENTOSAERROR; }
      ind.mfi.resize(sz);
      TA_MFI(0, endIndex, &h[0], &l[0], &c[0], &v[0], wlen-1, &ob, &n, &ind.mfi[_idx]);
      if (endIndex + 1 - (wlen - 1) != n){ SENTOSAERROR; }
      ind.ad.resize(sz);
      TA_AD(0, endIndex, &h[0], &l[0], &c[0], &v[0], &ob, &n, &ind.ad[0]);
      if (endIndex + 1 != n){ SENTOSAERROR; }
    }
    if (!spd.empty() && (it == INDALL || it == INDSPD)){
      sz = spd.size();
      endIndex = sz - 1;
      const double* p2 = &spd[0];
      ind.spdstd.resize(sz);
      TA_STDDEV(0, endIndex, p2, wlen, 1, &ob, &n, &ind.spdstd[_idx]);
      if (endIndex + 1 - (wlen - 1) != n){ SENTOSAERROR; }
      ind.spdma.resize(sz);
      TA_MA(0, endIndex, p2, wlen, TA_MAType_EMA, &ob, &n, &ind.spdma[_idx]);
      if (endIndex + 1 - (wlen - 1) != n){ SENTOSAERROR; }
    }
  }

  int getIdx(CSTR& s){
    uint64_t i = 0;
    for (; i < dt.size(); ++i){
      if (s == dt[i]){
        return i;
      }
    }
    return -1;
  }

  void resizeBar(int len){
    o.resize(len);
    h.resize(len);
    l.resize(len);
    c.resize(len);
    w.resize(len);
    v.resize(len);
    dt.resize(len);
  }

  tseries2(){}
  tseries2(CSTR& s):symbol(s){}
  ~tseries2(){
    //printf("{%s}%s\n", __FUNCTION__, symbol.c_str());
    if (pm){ delete pm; pm = nullptr; }
    if (ps){ delete ps; ps = nullptr; }
  }
};*/

//typedef tseries<VECDL> sSeries;
//typedef tseries<DSWIN> dSeries;
#endif
