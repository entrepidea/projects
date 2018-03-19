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

#ifndef SENTOSA_DEF_H
#define SENTOSA_DEF_H

#include <string>
#include "Contract.h"
#include "Order.h"
#include "cband.h"
#include "EWrapper.h"
#include "util.h"

using namespace std;

typedef struct pporder_ {
  Contract cmaster;
  Order omaster;
  Contract cslave;
  Order oslave;
} pporder;

typedef struct comboorder_ {
  Contract c;
  Order o;
} comboorder;

struct cprice {
  cprice() :
      bid(0), ask(0) {
  }
  double bid;
  int bidsize;
  double ask;
  int asksize;
};

enum ACTIONCLOSETYPE {
  BAND_BUYACTION = 0, BAND_SELLACTION, RANGEACTION
};

struct CDiff {
  CDiff() :
      master(), slave(), red(0.0), blue(.0), masterpos(0), slavepos(0) {
  }
  cprice master;
  cprice slave;
  double red;
  double blue;
  int masterpos;
  int slavepos;
  Contract *mc;
  Contract *sc;

  void checksignal(tobj* band, ORDERTYPE ot);

  void __action_short(tobj* band, ORDERTYPE ot);
  void __action_long(tobj* band, ORDERTYPE ot);

  void __action_close(tobj* band, ORDERTYPE ot, ACTIONCLOSETYPE);

  void runengine_band(cband* spread_band, ORDERTYPE ot);
  void runengine_range(cband* spread_band, ORDERTYPE ot);

  // b - band; r - range
  void logspread(tobj* band, bool boundary_signal, double takeprofit_meat,
      double remaining, bool tofile = true, char engine = 'b');
};

void liquidate_lmorder(Contract* pmster, double mbid, double mask,
    Contract* pslave, double sbid, double sask, int masterposition,
    int slaveposition, ORDERTYPE ot, tobj* band = nullptr);

void tradespread_lmorder(Contract* pmster, double masterprice, Contract* pslave,
    double slaveprice, int size, bool islong, ORDERTYPE ot);

void liquidate_combo(Contract* pmster, double mbid, double mask,
    Contract* pslave, double sbid, double sask, int masterposition,
    int slaveposition);

void tradespread_combo(Contract* pmster, Contract* pslave, int size,
    bool islong);

typedef pair<double, int> PPV;  // pair of price, volume

#endif
