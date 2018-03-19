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

#include "portfolio.h"
#include <scoreboard.h>

void portfolio::merge(const tradeinfo& oti){
  /*for (auto& kv : oti.uPNLD) {
    if (daily_unrealized_PNLs.find(kv.first) == daily_unrealized_PNLs.end()){
      daily_unrealized_PNLs[kv.first] = kv.second;
    }else{
      daily_unrealized_PNLs[kv.first] += kv.second;
    }
  }
  for (auto& kv : oti.aPNLD) {
    try{
      DAPNL.at(kv.first) += kv.second;
    }catch (out_of_range&){
      DAPNL[kv.first] = kv.second;
    }
  }
  for (auto& kv : oti.rPNLD) {
    try{
      daily_realized_PNLs.at(kv.first) += kv.second;
    }
    catch (out_of_range&){
      daily_realized_PNLs[kv.first] = kv.second;
    }
  }
  for (auto& kv : oti.dailycashRemaing) {
    try{
      daily_cashRemaining.at(kv.first) += kv.second;
    }
    catch (out_of_range&){
      daily_cashRemaining[kv.first] = kv.second;
    }
  }
  */
  uPNL += oti.uPNL;
  aPNL += oti.aPNL;
  lcc  += oti.lcc;
  inve += oti.inve;
  cR   += oti.cR;
  nlc  += lcc + uPNL;
}

void portfolio::savePNL(){
  /*string h = "date,accumPNL,dailyPNL,cashReaming";
  for (tobj* p : sboard::R().tradeobjs){
    string pid = p->pid();
    h += "," + pid;
  }
  h.append("\n");
  TTPrintNoTime(h.c_str());

  string fname = CConfig::R().PARAMDIR+"PortfolioPNL_" + ymdhms(DT_FILENAME) + ".csv";
  FILE* fdesc = fopen(fname.c_str(), "w+");
  fwrite(h.c_str(), sizeof(char), h.size(), fdesc);
  char buf[512] = { 0 };

  double prev = 0;
  double dpnl = 0;
  for (auto& kv : DAPNL){
    string dt = kv.first;
    char tmp[512] = { 0 };

    double apnl = -1000;
    for (tobj* p : sboard::R().tradeobjs){
      string pid = p->pid();
      try{
        apnl = sym2ti.at(pid).aPNLD[dt];
      }catch (...){
        apnl = 0;
        TTPrintNoTime("ERROR:%s,%s,%d\n", pid.c_str(), __FUNCTION__, __LINE__);
      }
      sprintf(tmp + strlen(tmp), ",%.2f", apnl);
    }

    double accumPNL = kv.second;
    dpnl = accumPNL - prev;
    prev = accumPNL;
    sprintf(buf, "%s,%.2f,%.2f,%.2f%s\n",
      dt.c_str(), accumPNL, dpnl, daily_cashRemaining[dt], tmp);
    TTPrintNoTime("%s", buf);
    fwrite(buf, sizeof(char), strlen(buf), fdesc);
  }
  fclose(fdesc);*/
}
