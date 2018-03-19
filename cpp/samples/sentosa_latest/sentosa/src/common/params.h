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

#ifndef __PARAMS__
#define __PARAMS__

#include <sentosaconfig.h>
#include <sstream>
#include <mutex>

using std::mutex;
using std::string;
using std::vector;

struct param{
  string pid; string f; string t;
  uint64_t win = 0;
  double step = 0.3;
  double bbh = 0.5;//bollinger band higher bound
  double bbl = 0.5;
  double meat = 40;
  double dst = 0.8;//daily standard times
  uint64_t dwin = 5;
  uint64_t mc = 2;//max continuous trades
  uint64_t smr = 1;
  int ms = -800;//maxshit
  double y = -1000;//yield
  double cash = 8000;
  double sr = -1000;//sharpe ratio
  double mdd = -1000;//max draw down
  uint64_t md3 = 0;//max draw down duration

  param(){}
  void reset(){
    pid = "";
    win = 0;
    step = 0.3;
    bbh = 0.5;
    bbl = 0.5;
    meat = 40.;
    dst = 0.8;
    dwin = 5;
    mc = 1;
    smr = 1;
    ms = -800;
    cash = 8000;
    y = -1000;
    sr = -1000; mdd = -1000; md3 = 0;
  }

  void print();

  param& operator=(const param& x){
    pid = x.pid; win = x.win; f = x.f; t = x.t;
    step = x.step;
    bbl = x.bbl;
    bbh = x.bbh;
    meat = x.meat;
    dst = x.dst;
    dwin = x.dwin;
    mc = x.mc;
    smr = x.smr;
    ms = x.ms;
    y = x.y;
    sr = x.sr;
    cash = x.cash; mdd = x.mdd; md3 = x.md3;
    return *this;
  }

  bool operator==(const param& x)const {
    return pid == x.pid && win == x.win && f == x.f && t == x.t &&
      step == x.step &&
      bbl == x.bbl &&
      bbh == x.bbh &&
      meat == x.meat &&
      dst == x.dst &&
      dwin == x.dwin &&
      mc == x.mc &&
      smr == x.smr &&
      ms == x.ms;
  }

  template <class Archive>
  void serialize(Archive & ar)
  {
    ar(CEREAL_NVP(pid), CEREAL_NVP(f), CEREAL_NVP(t), CEREAL_NVP(win), CEREAL_NVP(step),
      CEREAL_NVP(bbh), CEREAL_NVP(bbl), CEREAL_NVP(meat),
      CEREAL_NVP(dst), CEREAL_NVP(dwin), CEREAL_NVP(mc),
      CEREAL_NVP(smr), CEREAL_NVP(ms), CEREAL_NVP(cash), CEREAL_NVP(y), CEREAL_NVP(sr),
      CEREAL_NVP(mdd), CEREAL_NVP(md3));
  }
};

// store training results
// TODO - should store in the database
class CParams
{
  static bool initialized;
  static CParams* pinstance;
  static mutex lock_;

  void loadParams();
public:

  static CParams& R();
  void SaveParam(const param& oprm);
  bool paramExist(const param& prm);
  /*
  C_SHARPE - order by sharpe ratio
  C_YIELD  - order by yield
  C_COMPR  - the smallest maxdd*maxddd among top 5 C_SHARPE and top 5 C_YIELD
  */
  param getBestParam(const string& pid) const;
  // get top N params
  vector<param> getTopNParams(const string& pid, uint64_t N) const;
};



#endif
