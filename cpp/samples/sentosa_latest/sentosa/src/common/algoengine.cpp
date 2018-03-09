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

#include "algoengine.h"
#include <gconfig.h>
#include <mq.h>

#define TIME_CONSTRAINT
extern std::atomic<bool> g_shutdown;

double AlgoEngine::fixMarketPriceForBT() {
  tradeinfo& ti = pmcw->ptobj->_tinfo;
  mktinfo& rti = pmcw->_mkdata;
  if (rti.lastPrice > 0) {
    if (rti.lastPrice > blu && rti.lastPrice < red) {
      __mp = rti.lastPrice;
    } else if (0.05 < remaining_time_percentage && remaining_time_percentage < 0.95) {
      if (ti.statuz == OTL) {
        __mp = blu;
      } else if (ti.statuz == OTS) {
        __mp = red;
      } else {
        __mp = wap;
      }
    }
  } else {
    __mp = wap;
  }
  return __mp;
}

AlgoEngine::~AlgoEngine() {
}

void AlgoEngine::update_trading_time() {
  __dtm = ymdhms();
  remaining_time_percentage = percentTime(__dtm.c_str());
  if (remaining_time_percentage < -1) {
    //Use simulation machine, Dont run program after market hour!
    g_shutdown = true;
  }
}

void AlgoEngine::final_teardown() {
  initialized = false;
  if (pmcw && pmcw->ptobj) {
    // fix
    tradeinfo& ti = pmcw->ptobj->_tinfo;
    switch (ti.statuz) {
    case NP:
      break;
    case OTL:
      break;
    case OTS:
      break;
    case DELIMITER:
      break;
    case LWaitC:
    case LWaitC2:
      ti.statuz = OTL;
      break;
    case SWaitC:
    case SWaitC2:
      ti.statuz = OTS;
      break;
    case NWaitL:
    case NWaitL2:
    case NWaitS:
    case NWaitS2:
      ti.statuz = NP;
      break;
    }
  }
}

double AlgoEngine::getpaircost(bool _willLong) {
  if (_willLong) {
    return pmcw->_mkdata.ask + pscw->_mkdata.bid;
  } else {
    return pmcw->_mkdata.bid + pscw->_mkdata.ask;
  }
  return -1;
}
