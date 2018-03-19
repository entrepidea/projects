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

#include "volatility.h"
#include <util.h>
#include <gconfig.h>
#include <boost/filesystem.hpp>
namespace fs=boost::filesystem;

using namespace std;

void crange::reset() {
  cDay = ymd();
  tvrange = .0;
  minred = 100000.;
  maxblu = -10000.;
  thigh = -10000.;
  tlow = 100000.;
}

void crange::load(string xmlpath){
    try{
      ifstream f(xmlpath);
      if (fs::exists(xmlpath)){
        SENTOSALOG;
        cereal::JSONInputArchive iarchive(f);//DO NOT change this!!
        //if (f.is_open() && f.good()){
          iarchive(*this);
          if (CConfig::R()._mode==SYS_MODE::TRADE_MODE &&
              (cDay != ymd() || percentTime() < 0))
          {
            reset();
          }
        //}else{
        //  SENTOSAERROR;
        //  exit(1);
        //}
      }else{
        return;
      }
    }catch (cereal::Exception& e){
      TTPrint("CEREAL ERROR: %s", e.what());
    }catch (...){
      TTPrint("File %s cannot be loaded!\n", xmlpath.c_str());
    }
}


/* The purpose of "const crange& volatility=*this":
{
    "volatility": {
        "cDay": "2015-07-15",
        "tvrange": 60.25,
        "minred": -1,
        "maxblue": 59.25,
        "thigh": 1.5345,
        "tlow": 56.715499999999999
    }
}
*/
void crange::save(string xmlpath) {
  ofstream bpConfig(xmlpath, ios::out);        //append mode
  {
    cereal::JSONOutputArchive oarchive(bpConfig);
    cDay = ymd();
    const crange& volatility = *this;
    oarchive(CEREAL_NVP(volatility));
  }
  TTPrint("[%s]Saved to %s\n", __FUNCTION__, xmlpath.c_str());
}

void crange::update(double blu, double red, double dr){
    if (blu > maxblu && blu > 0.1 ) {
      maxblu = blu;
    }
    if (red < minred && red > 0.1 ){//sometimes red=-1
      minred = red;
    }
    tvrange = maxblu - minred;
    //假设最高价格不超过1000
    if (abs(maxblu) < 1000 && abs(minred) < 1000) {
      thigh = minred + dr;
      tlow = maxblu - dr;
    }
}
