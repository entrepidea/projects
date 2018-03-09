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

#ifndef __SPREADRANGE__
#define __SPREADRANGE__

#include <sentosaconfig.h>
#include <mutex>
#include "util.h"

// 通过spreadFile控制range交易
struct spreadRange{
  double s_threshold;
  double liq_upper;
  double liq_lower;
  double l_threshold;

  string spdF;
  time_t lwt;//last write time
  mutex mtx;

  bool spdMonitor = true;

  friend class cereal::access;
  template <class Archive>
  void serialize(Archive & ar){
    ar(CEREAL_NVP(s_threshold), CEREAL_NVP(liq_upper), CEREAL_NVP(liq_lower), CEREAL_NVP(l_threshold));
  }

  void loadXML(){
    ifstream f(spdF);
    if (fs::exists(spdF)){
      SENTOSALOG;
      cereal::JSONInputArchive iarchive(f);
      if (f.is_open() && f.good()){
        iarchive(*this);
        lwt = getFileWriteTime(spdF);
        TTPrint("[%s]Loaded from %s\n", __FUNCTION__, spdF.c_str());
      }
      else{
        SENTOSAERROR;
        exit(1);
      }
    }
  }
};
#endif
