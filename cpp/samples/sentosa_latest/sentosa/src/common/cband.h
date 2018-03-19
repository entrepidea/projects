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

#pragma once

#include <sentosaconfig.h>
#include <datasource.h>
#include <tradeinfo.h>
#include <swindow.h>
#include <myData.h>
#include <regex>

//#define BARTABLE ((CConfig::R().isbacktest)?"bar15s":"bar5s")
#define BARTABLE "bar5s"

struct mktinfo;

// tobj means "trade object"
// every trade object can send order to OMS via msg queue
class tobj{
public:
  static string __dt;

  string algo  = "";
  string efile = "";//some external file like spread file

  tsbar* rtd = nullptr;
  tradeinfo _tinfo;
  param prm;

  //virtual bool loadhData()=0;
  virtual void load(VECST&) = 0;
  virtual void set(const VECST&) = 0;

  //pair trading的strategy,pid是maste+"-"+slave;单独的股票,pid就是symbol
  virtual string pid() = 0;

  virtual VECST symbols() = 0;

  //bool saveTradeInfo(const string& d = tobj::__dt);
  string TI2Json(const regex& p);//tradeinfo to string
  string I2Json5s(const regex& p);//5s indication to string
  string I2Json1d(const regex& p);//daily indication to string
  //bool loadTradeInfo(const string& d);
  bool VerifyPosition(const mktinfo& _rtinfo);

  //tobj() :rtd(nullptr),algo(""){}
  tobj() = default;
  tobj& operator=(const tobj&) = delete;
  ~tobj(){ if (rtd) delete rtd; }
};

// for pair trading
class cband final: public tobj{
public:
  string maste = "";
  string slave = "";

  //override明确地表示一个函数是对基类中一个虚函数的重载
  string pid() override{ return maste+"-"+slave; }

  void set(const VECST& tmp){
    int i = 0;
    algo = tmp.at(i++);
    maste = tmp.at(i++);
    slave = tmp.at(i++);
    if (tmp.size() >= 4){
      efile = tmp.at(i++);
    }
  }

  //bool loadhData() override;
  void load(vector<string>&) override final{}

  VECST symbols(){ return VECST{maste,slave}; }
};

// trade one single instrument
class csing final: public tobj{
  string symbol = "";
  
  void set(const VECST& tmp){
    int i = 0;
    algo = tmp.at(i++);
    symbol = tmp.at(i++);
  }
  //bool loadhData() override;
  void load(vector<string>&) override{}
  string pid() override{ return symbol; }
  VECST symbols(){ return VECST{ symbol }; }
};

