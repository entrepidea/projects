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

#ifndef ALGORITHM_VOLATILITY_H
#define ALGORITHM_VOLATILITY_H

#include <sentosaconfig.h>

using std::string;

struct crange {
  string cDay;  //current Day
  double tvrange = .0;  //today valid range
  double minred = 100000.;
  double maxblu = -10000.;
  double thigh = -10000.;  // today predicted high value
  double tlow = 100000.;  // today predicted low value

  void reset();
  //load from file
  void load(string fname);
  //save to file
  void save(string xmlpath);

  void update(double blu, double red, double dr);

  friend class cereal::access;
    template <class Archive>
    void serialize(Archive & ar){
      ar(CEREAL_NVP(cDay),
          CEREAL_NVP(tvrange),
          CEREAL_NVP(minred),
          CEREAL_NVP(maxblu),
          CEREAL_NVP(thigh),
          CEREAL_NVP(tlow));
    }
};

#endif
