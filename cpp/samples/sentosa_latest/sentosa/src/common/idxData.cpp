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

#include "idxData.h"

using std::lock_guard;

refData* refData::pinstance = nullptr;
mutex refData::loglock_;

refData::refData(CSTR& f, CSTR& t){
  string oneYearAgo = rollbackTime(t, SECONDS_IN_ONE_YEA);
  getOHLCWV("bar1d", "SPY", oneYearAgo, t, spy500._day);
  //getOHLCWV("bar15s", "SPY", oneYearAgo, t, spy500._15s);
  TTPrint("Got daily data of SPY500.");
}

refData::~refData(){
}

refData& refData::R(const string& f, const string& t){
  if (pinstance == nullptr){
    lock_guard<mutex> g(loglock_);
    if (pinstance == nullptr){
      pinstance = new refData(f, t);
    }
  }
  return *pinstance;
}
