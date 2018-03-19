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

#include "o2j.h"
#include "gconfig.h"
#include <portfolio.h>
#include <scoreboard.h>

static regex decimal0("(\\d+)\\.[\\d]*");
static regex decimal2("(\\d+\\.\\d\\d)[\\d]*");
static regex decimal3("(\\d+\\.\\d\\d\\d)[\\d]*");
static regex nospace("\\n\\s+");

static string fp0="";

string fullpage() {
  sboard& sb = sboard::R();
  string msg = "{\"dt\":\"" + ymdhms() + "\",\"data\":[";
  for (int i = 0; i < sb.tradeobjs.n; ++i) {
    tobj* p = sb.tradeobjs[i];
    msg += p->TI2Json(decimal2) + ",";
    ////msg += p->I2Json1d(decimal2) + ",";
    ////msg += p->rtd->_5s.getLastUpdate("_5su") + ",";
  }
  for (int i = 0; i < sb.equitycount; ++i) {
    msg += sb.cwstocks_[i].mktinfo2Json(decimal2) + ",";
    msg += sb.cwstocks_[i]._mkdata.O2J(decimal2) + ",";
  }
  msg += sb.acc.O2J(&decimal2) + ",";
  portfolio pot;
  for (int i = 0; i < sb.tradeobjs.n; ++i) {
    pot.merge(sb.tradeobjs[i]->_tinfo);
  }
  msg += pot.toJson(decimal0) + "]}";
  msg = regex_replace(msg, nospace, "");
  return msg;
}

string tobjpage(string SYM){
  static string tp0 = "";
  instrument* i = sboard::R().getCW(SYM);
  string msg;
  sboard& sb = sboard::R();
  if (i) { // http://192.168.254.130/sentosa/tobj/NOSTOCK/
    msg += "{\"dt\":\"" + ymdhms() + "\",\"data\":[ ";
    msg += i->ptobj->TI2Json(decimal2) + ",";
    msg += i->mktinfo2Json(decimal2) + ",";
    msg += i->_mkdata.O2J(decimal2) + ",";
    //msg += i->ptobj->rtd->_5s.getLastUpdate("_5su") + ",";
    portfolio pot;
    for (int i = 0; i < sb.tradeobjs.n; ++i) {
      pot.merge(sb.tradeobjs[i]->_tinfo);
    }
    msg += pot.toJson(decimal0) + "]}";
  }
  msg = regex_replace(msg, nospace, "");
  return msg;
}

// Don't cache order in server side
string orderJson(string SYM) {
  instrument* i = sboard::R().getCW(SYM);
  string msg;
  if (i) { // http://192.168.254.130/sentosa/tobj/NOSTOCK/
    msg += "{\"dt\":\"" + ymdhms() + "\",\"orders\":[ ";
    string tmp;
    for (int j = 0; j < i->lorders.count; ++j) {
      if (!ISREMOVED(i->lorders.ords[j].status)){
        tmp += i->lorders.ords[j].O2J(decimal2) + ",";
      }
    }
    if (tmp.empty()){return string("o");}
    msg += tmp;
    msg.back() = ' ';
    msg += "]}";
  }
  msg = regex_replace(msg, nospace, "");
  return msg;
}

string orderJson() {
  sboard& sb = sboard::R();
  string msg = "{\"dt\":\"" + ymdhms() + "\",\"orders\":[ ";
  string tmp;
  for (int i = 0; i < sb.equitycount; ++i) {
    instrument& p = sb.cwstocks_[i];
    for (int j = 0; j < p.lorders.count; ++j) {
      if (!ISREMOVED(p.lorders.ords[j].status)){
        tmp += p.lorders.ords[j].O2J(decimal2) + ",";
      }
    }
  }
  if (tmp.empty()){return string("o");}
  msg += tmp;
  msg.back() = ' ';
  msg += "]}";
  msg = regex_replace(msg, nospace, "");
  return msg;
}
