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

#ifndef SENTOSA_ALGOENGINE_H
#define SENTOSA_ALGOENGINE_H

#include <datasource.h>
#include <tradeinfo.h>
#include <util.h>
#include <cwrapper.h>

class AlgoEngine {
protected:
  double target_gain;
  double target_lose;

  double daily_spread = .0;//daily price range
  double wap = 0.0; //not real market value
  double blu = 0.0; //bid
  double red = 0.0; //ask
  double spd = 0.0; //bid-ask spread = red - blu


  int64_t volume = 0;
  double fixMarketPriceForBT();
  double __mp;

  enum ACTIONTYPE_ {ATNone = 0, ATBuy, ATsell, ATclose};

  //0 - None, 1-Buy, 2- sell, 3 - close
  uint64_t actionType = ATNone;

public:
  bool initialized = false;

  string __dtm;
  double remaining_time_percentage = 1.0;  // remaining time
  void update_trading_time();

  instrument* pmcw = nullptr;
  instrument* pscw = nullptr;

  virtual void check_market_data(TickType ttid) = 0;
  virtual void check_bar_data() = 0;  //check bar data
  virtual void check_runtime_data() = 0;  //check runtime data
  virtual void check_bar_data_backtest() = 0;  //check bar data for backtest

  AlgoEngine() {
    rlog = make_unique<Rlog>();
  }
  virtual ~AlgoEngine();
  virtual void initial_setup() = 0;
  virtual void final_teardown();
  virtual bool cutLossOrGain(tobj* band) = 0;

  // how much need to pay for buying a pair
  double assetCost = 0.;
  virtual double getpaircost(bool _willLong);

  unique_ptr<Rlog> rlog;
};

#endif
