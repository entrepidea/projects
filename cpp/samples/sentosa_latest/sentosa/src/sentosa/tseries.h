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

#ifndef SENTOSA_TSERIES_H
#define SENTOSA_TSERIES_H

#include <sentosaconfig.h>
#include <hash_map>

using namespace std;

struct OHLC {
  double _open;
  double _high;
  double _low;
  double _close;

  double _vwap;
  uint _volume;
  short _bcount;

  string _dts;
};

struct tseries {
  //string sdate;
  //ULL ldate;
  //OHLC _ohlc;
  hash_map<ULL, OHLC> hm;
};

#endif
