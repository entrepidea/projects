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

#ifndef __DBB0__
#define __DBB0__

#include <algoengine.h>
#include <cereal/archives/json.hpp>
#include <sstream>
#include <thread>
#include "spreadRange.h"
#include "volatility.h"

#define TIME_CONSTRAINT

//TODO
//intraday dynamics of Daily Range
struct intradayDynamicsDR{
  double operator()(){
    return 0.0;
  }
};


class DBB0 : public AlgoEngine{
  spreadRange srange;
protected:

public:
  thread* pt;
  DBB0(){}
  ~DBB0(){
    srange.spdMonitor = false;
    pt->join();
    delete pt;
  }

  void check_bar_data();
  void check_runtime_data();
  void __check_runtime_data_market();
  void __check_runtime_data_limit();
  void check_bar_data_backtest();

  // the trading program must start before 09:30AM
  void initial_setup();

  bool cutLossOrGain(tobj*){ return false; }

  // Just a simple moving average to update dr
  void predictRange();

  void setsthreshold(double d){
    srange.s_threshold = d;
    saveXML(srange.spdF);
  }
  void setlthreshold(double d){
    srange.l_threshold = d;
    saveXML(srange.spdF);
  }

  bool saveXML(const string& xmlpath){
    lock_guard<mutex> _g(srange.mtx);
    {
      ofstream bpConfig(xmlpath, ios::out);//append mode
      cereal::JSONOutputArchive  oarchive(bpConfig);
      oarchive(CEREAL_NVP(srange));
    }
    TTPrint("[%s]Saved to %s\n", __FUNCTION__, xmlpath.c_str());
    srange.lwt = getFileWriteTime(xmlpath);
    return true;
  }

  void check_market_data(TickType ttid);

  void logTick(TickType ttid, CSTR& sym, CSTR& val);

  crange crng;

};

#endif
