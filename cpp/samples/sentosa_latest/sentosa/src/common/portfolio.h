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

#ifndef __PORTFOLIO__
#define __PORTFOLIO__

#include <sentosaconfig.h>
#include <tradeinfo.h>
#include <cband.h>

class portfolio{
public:
  //optimization params
  map<string, tradeinfo> sym2ti;
  map<string, double> daily_unrealized_PNLs;
  map<string, double> daily_realized_PNLs;
  map<string, double> daily_cashRemaining;
  map<string, double> DAPNL;  //daily_accumulate_PNLs

  void merge(const tradeinfo& optimizedti);
  void savePNL();

  portfolio(){}

  double uPNL=.0; //浮亏或赚
  double aPNL=.0; //accumulated PNL
  double lcc=.0;  //= myInv + cashRemaining
  double inve=.0; //投进去的钱 cash at risk exposure
  double cR=.0;   //剩下的可活动的钱
  double nlc=.0;  //如果现在close所有的position,我有多少钱? = lcc + unrPNL

  template<class Archive>
  void serialize(Archive & ar) {
    ar( CEREAL_NVP(uPNL),
        CEREAL_NVP(aPNL),
        CEREAL_NVP(lcc),
        CEREAL_NVP(inve),
        CEREAL_NVP(cR),
        CEREAL_NVP(nlc));
  }

  void report(){
    //TTPrint("Portfolio:nlc=%.4f,cashRemaining=%.4f,myInv=%.4f,lcc=%.4f,unrPNL=%.4f\n",
    //  nlc,cR,inve,lcc,uPNL);
  }
  
  string toJson(const regex& p) {
    stringstream ss;
    {
      cereal::JSONOutputArchive oarchive(ss);
      oarchive(cereal::make_nvp("_portfolio",*this));
    }
    return regex_replace(ss.str(), p, "$1");
  }

};


#endif
