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

#ifndef __ALGO_SINGLE_TA__
#define __ALGO_SINGLE_TA__

#include <algoengine.h>
#include <atomic>
#include <singleton.h>
#include "volatility.h"

class Predictor: public Singleton<Predictor>
{
  friend class Singleton<Predictor>;
private:
  Predictor();

public:
  map<string,double> sym2rng;
};

class singletaalgo : public AlgoEngine{
public:
  void check_bar_data();
  void check_runtime_data();
  void check_bar_data_backtest();

  int decisionTree();

  // the trading program must start before 09:30AM
  void initial_setup();

  // Just a simple moving average to update dr
  void predictRange();

  bool cutLossOrGain(tobj* band);

  void updateRTData(_RTDATA_TYPE i = RTDALL);

  void check_market_data(TickType ttid);

  crange crng;
  string xmlpath;

  ~singletaalgo(){
    crng.save(xmlpath);
    TTPrint("[%s]Save crng to %s\n",__FUNCTION__, xmlpath.c_str());
  }
};

#endif
