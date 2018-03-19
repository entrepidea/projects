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

#include "cband.h"
#include <gconfig.h>
#include <cwrapper.h>

#include <boost/filesystem.hpp>
namespace fs=boost::filesystem;

string tobj::__dt = ymdhms();


/*bool tobj::saveTradeInfo(const string& d){
  string pid_ = pid();
  string path = getTIpath(pid_, d, 0);

  string s = TI2Json(tradeinfo::decimal3);
  ofstream myfile;
  myfile.open (path);
  myfile << s;
  myfile.close();

  TTPrint("[%s]Saved to %s\n", __FUNCTION__, path.c_str());
  return true;
}*/

string tobj::TI2Json(const regex& p){
  // https://github.com/USCiLab/cereal/issues/101
  stringstream ss;
  {
    cereal::JSONOutputArchive oarchive(ss);
    oarchive(CEREAL_NVP(_tinfo));
  }
  return regex_replace(ss.str(), p ,"$1");
}

string tobj::I2Json5s(const regex& p){
  stringstream ss;
  {
    cereal::JSONOutputArchive oarchive(ss);
    //oarchive(CEREAL_NVP(rtd->_dy.ind));
    //oarchive(CEREAL_NVP(rtd->_dy));
    oarchive(cereal::make_nvp("bar5s",rtd->_5s));
  }
  string s=regex_replace(ss.str(), p ,"$1");
  return s;//regex_replace(s, tradeinfo::nospace ,"$1");
}

string tobj::I2Json1d(const regex& p){
  stringstream ss;
  {
    cereal::JSONOutputArchive oarchive(ss);
    oarchive(cereal::make_nvp("bar1d",rtd->_dy));
  }
  return regex_replace(ss.str(), p ,"$1");
}

/*bool tobj::loadTradeInfo(const string& d){
  string tipath;
  bool shouldWeSaveIt=false;
  try{
    string pid_ = pid();
    //try to load today's file, if today file doesn't exist, load last trading day's file
    //printf("%s\n",pid_.c_str());
    tipath = getTIpath(pid_, d, 0);
    //printf("(%d)%s\n",__LINE__,tipath.c_str());
    if (!fs::exists(tipath)){
      tipath = getTIpath(pid_, d, 1);
      //printf("(%d)%s\n",__LINE__,tipath.c_str());
    }
    ifstream f(tipath);
    if (fs::exists(tipath)){
      //SENTOSALOG;
      if (f.is_open() && f.good()){
      //if (true){
        cereal::JSONInputArchive iarchive(f);//DO NOT change this!!
        iarchive(_tinfo);

        shouldWeSaveIt = _tinfo.selfCheck(true);
        _tinfo.syms = pid_;
        if (_tinfo.statuz>DELIMITER){
          _tinfo.statuz = NP;
        }
        TTPrint("[%s]%s\n", __FUNCTION__, tipath.c_str());
        _tinfo.print();
      }else{
        TTPrint("[%s]%s is wrong.\n", __FUNCTION__, tipath.c_str());
        SENTOSAERROR;
        exit(1);
      }
    }else{
      _tinfo.syms = pid_;
      //return false;
    }
  }catch (cereal::Exception& e){
    TTPrint("CEREAL ERROR: %s", e.what());
  }catch (...){
    TTPrint("File %s cannot be loaded!\n", tipath.c_str());
  }
  if (shouldWeSaveIt){
    saveTradeInfo(ymd());
  }
  return true;
}*/

bool tobj::VerifyPosition(const mktinfo& _mkd){
  //trading volume recorded in XML file
  double tradingVol = _tinfo.TOTALVOL();
  // trading volume summation should be equal to _rtinfo.position
  double voldiff = _mkd.pos_ - tradingVol;
  if (abs(voldiff) > 0.1 || abs(_tinfo.AVGPRICE()-_mkd.avgP)>0.1){
    _tinfo.vo.clear();
    _tinfo.ps.clear();
    if (_mkd.pos_!=0){
      _tinfo.vo.emplace_back(_mkd.pos_);
      _tinfo.ps.emplace_back(_mkd.avgP);
    }
    _tinfo.selfCheck();
    TTPrint("[%s]ERROR: position does not match."
        "(json tradeinfo:%.2f)-(IB rtinfo:%.2f)\n",
        pid().c_str(), tradingVol, _mkd.pos_);
    //saveTradeInfo();
    return false;
  }
  return true;
}

//////////////////////////////////////////////////////////////////////////
/*bool cband::loadhData(){
  timer t_(__FUNCTION__);
  string pid_ = pid();

  prm.win = CConfig::R().sym2taLen[pid_];
  prm.win = (prm.win==0 ? 500 : prm.win);
  prm.dwin = 22;//TODO - should not be hard coded!
  prm.f = prm.t = "";
  if (prm.win * 15 >= prm.dwin*SECONDS_IN_ONE_DAY){
    return false;
  }
  rtd = new tsbar;

  rtd->_5s.symbol = pid_;
  rtd->_5s.w.resize(prm.win);
  if (CConfig::R().isbacktest){
    rtd->_5s.spd.resize(prm.win);
  }
  rtd->_dy.symbol = pid_;
  rtd->_dy.w.resize(prm.dwin);

  string t = cband::__dt;
  // for testing purpose
  //////////////////////////////////////////////////////////////////////////
  double pt = percentTime();
  if (pt < 0){
    t = normalizeTradeDT(t);
  }
  //////////////////////////////////////////////////////////////////////////
  string f = RollBackTradeTime(t, (CConfig::R().isbacktest ? 15 : 5) * prm.win);

  {
    dSeries* h1 = new dSeries(maste);
    dSeries* h2 = new dSeries(slave);
    rtd->_5s.pm = h1;
    rtd->_5s.ps = h2;
    h1->resizeBar(prm.win);
    h2->resizeBar(prm.win);
    getOHLCWV(BARTABLE, maste, f, t, *h1);
    getOHLCWV(BARTABLE, slave, f, t, *h2);

    for (uint64_t i = 0; i < h1->dt.size(); ++i){
      rtd->_5s.w.push_back(h1->w[i] - h2->w[i]);
    }
  }

  {
    //TODO - check again!!!
    // because there is Saturday and Sunday, I am times it with 2!!!
    f = rollbackTD(t, prm.dwin + 2);
    dSeries* h3 = new dSeries(maste);
    dSeries* h4 = new dSeries(slave);
    rtd->_dy.pm = h3;
    rtd->_dy.ps = h4;
    h3->resizeBar(prm.dwin);
    h4->resizeBar(prm.dwin);
    string pday = prevTradeDT(t);
    getOHLCWV("bar1d", maste, f, pday, *h3);
    getOHLCWV("bar1d", slave, f, pday, *h4);
    for (uint64_t i = 0; i < h3->dt.size(); ++i){
      rtd->_dy.w.push_back(h3->w[i] - h4->w[i]);
    }
  }

  rtd->tanlaysis(&prm);

  //NOTE: spd5s主要用在backtest的时候,所以在CBand的初始化的时候可以不考虑
  return true;
}

//////////////////////////////////////////////////////////////////////////
bool csing::loadhData(){
  //timer t_(__FUNCTION__);
  string pid_ = pid();

  //prm = CParams::R().getBestParam(pid_);

  prm.dwin = 22;//TODO - should not be hard coded!
  prm.win = CConfig::R().sym2taLen[pid_];
  if (prm.win<1){prm.win=500;}
  prm.f = prm.t = "";
  if (prm.win * 15 >= prm.dwin*SECONDS_IN_ONE_DAY){
    return false;
  }
  rtd = new tsbar;

  rtd->_5s.symbol = pid_;//??
  rtd->_dy.symbol = pid_;
  rtd->_15s.symbol = pid_;
  
  rtd->_5s.w.resize(prm.win);//??
  rtd->_dy.w.resize(prm.dwin);//??

  string t = tobj::__dt;
  // for testing purpose
  //////////////////////////////////////////////////////////////////////////
  double pt = percentTime();
  if (pt < 0){
    t = normalizeTradeDT(t);
  }
  //////////////////////////////////////////////////////////////////////////
  string f = RollBackTradeTime(t, 5 * prm.win);
  rtd->_5s.resizeBar(prm.win);
  getOHLCWV(BARTABLE, symbol, f, t, rtd->_5s);
  
  f = rollbackTD(t, prm.dwin + 2);
  string pday = prevTradeDT(t);
  
  rtd->_dy.resizeBar(prm.dwin);
  getOHLCWV("bar1d", symbol, f, pday, rtd->_dy);
  
  //rtd->_15s.resizeBar(prm.dwin*1560); //6.5*3600/15 = 1560
  //getOHLCWV("bar15s", symbol, f, pday, rtd->_15s);

  rtd->tanlaysis(&prm);

  //NOTE: spd5s主要用在backtest的时候,所以在CBand的初始化的时候可以不考虑
  return true;
}*/
