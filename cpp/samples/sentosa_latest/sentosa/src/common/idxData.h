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

#ifndef __IDXDATA__
#define __IDXDATA__

#include "myData.h"
#include <mutex>

using std::mutex;
using std::string;

/** A singleton for Market Historical Data
//NASDAQ-100 Index -> market capitalization
hData index_QQQ;
// S&P 500 Index
hData index_SPY;
//NASDAQ OMX China Technology Index - the technology sector in China
hData index_QQQC;
//FTSE China 25 Index
//Financial, Telecommunication, Oil & gas, Technology and Consumer goods in China
hData index_FXI;
*/
class refData{
  static bool initialized;
  static refData* pinstance;
  static mutex loglock_;

  hData spy500;

  // f should be removed!
  refData(CSTR& f, CSTR& t);
  ~refData();

public:
  /*** Get the singleton instance*/
  static refData& R(const string& f, const string& t);
  hData* getspy500(){ return &spy500; }
};

#endif
