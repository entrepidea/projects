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

#include "myTA.h"

using std::vector;

double getRSI(double* p, uint32_t sz){
  vector<double> vd(p, p + sz);
  auto i = std::unique(vd.begin(), vd.end());
  uint32_t m = distance(vd.begin(), i);
  if (m <= 10){
    return 50.;
  }

  int ob, n, endIndex;
  double result{ .0 };
  endIndex = sz - 1;
  TA_RSI(0, endIndex, p, sz - 1, &ob, &n, &result);
  return result;
}
