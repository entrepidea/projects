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

#include "params.h"
#include <cband.h>
#include <mysql.h>

CParams* CParams::pinstance = nullptr;
mutex CParams::lock_;

CParams& CParams::R(){
  if (pinstance == nullptr){
    lock_guard<mutex> g(lock_);
    if (pinstance == nullptr){
      pinstance = new CParams();
    }
  }
  return *pinstance;
}

void CParams::loadParams(){
  string fpath1 = CConfig::R().PARAMDIR + "Params.xml";
  timer t(__FUNCTION__);
  //checkXML();
  try{
    ifstream f(fpath1);
    SENTOSALOG;
    cereal::JSONInputArchive iarchive(f);

    param x;
    while (f.is_open() && f.good()){
      try{
        iarchive(x);
#if 0
        char sql[512] = {0};
        sprintf(sql, "INSERT ignore INTO `params`(`pid`, `f`, `t`, `win`, "
          "`step`, `bhigh`, `blow`, `meat`, `dst`, "
          "`dwin`, `mconn`, `smr`, `maxshit`, `cash`, `yield`, `sharpe`, `maxdd`, `maxddd`) "
          "VALUES ('%s','%s','%s',%d,%.2f,%.2f,%.2f,"
          "%.2f,%.2f,%d,%d,%d,%d,%.2f,"
          "%.2f,%.2f,%.2f,%d)",x.pid.c_str(),x.f.c_str(),x.t.c_str(),x.win,x.step,x.bbh,x.bbl,x.meat,
          x.dst,x.dwin,x.mc,x.smr,x.ms,x.cash,x.y,x.sr,x.mdd,x.md3);

        MYSQL* conn = (MYSQL*)CDB::R().conn;
        if (mysql_query(conn, sql)) {
          fprintf(stderr, "%s\n", mysql_error(conn));
          exit(1);
        }
#endif
        //msp[x.pid].push_back(x);
      }
      catch (cereal::Exception& e){
        TTPrint("Data Loading Error: %s", e.what());
        break;
      }
    }
  }
  catch (...){}
}

void CParams::SaveParam(const param& x){
  //char sql[512] = { 0 };
  //sprintf(sql, "INSERT ignore INTO `params`(`pid`, `f`, `t`, `win`, "
  //  "`step`, `bhigh`, `blow`, `meat`, `dst`, "
  //  "`dwin`, `mconn`, `smr`, `maxshit`, `cash`, `yield`, `sharpe`, `maxdd`, `maxddd`) "
  //  "VALUES ('%s','%s','%s',%d,%.2f,%.2f,%.2f,"
  //  "%.2f,%.2f,%d,%d,%d,%d,%.2f,"
  //  "%.2f,%.2f,%.2f,%d)", x.pid.c_str(), x.f.c_str(), x.t.c_str(), x.win, x.step, x.bbh, x.bbl, x.meat,
  //  x.dst, x.dwin, x.mc, x.smr, x.ms, x.cash, x.y, x.sr, x.mdd, x.md3);

  //MYSQL* conn = (MYSQL*)CDB::R().conn;
  //if (mysql_query(conn, sql)) {
  //  fprintf(stderr, "%s\n", mysql_error(conn));
  //  exit(1);
  //}
}

bool CParams::paramExist(const param& x)
{
  //char sql[512] = { 0 };
  //sprintf(sql, "SELECT * FROM params WHERE `pid`='%s' and `f`='%s' and `t`='%s' and `win`=%d and "
  //  "`step`=%d and `bhigh`=%.2f and `blow`=%.2f and `meat`=%.2f and `dst`=%.2f and "
  //  "`dwin`=%d and `mconn`=%d and `smr`=%.2f and `maxshit`=%d",
  //  x.pid.c_str(), x.f.c_str(), x.t.c_str(), x.win, x.step, x.bbh, x.bbl, x.meat,
  //  x.dst, x.dwin, x.mc, x.smr, x.ms);
  //MYSQL* conn = (MYSQL*)CDB::R().conn;
  //if (mysql_query(conn, sql)) {
  //  fprintf(stderr, "%s\n", mysql_error(conn));
  //  exit(1);
  //}
  //MYSQL_RES *res = mysql_store_result(conn);

  //int len = mysql_num_rows(res);
  //return len == 1;
  return true;
}

param CParams::getBestParam(const string& pid)const{
  vector<param> vp = getTopNParams(pid, 1);
  if (vp.empty()){
    return param();
  }
  return vp.at(0);
}

// get top N params
vector<param> CParams::getTopNParams(const string& pid, uint64_t N) const{
  vector<param> vp;
  char sql[512] = { 0 };
  sprintf(sql, "SELECT * FROM params WHERE pid='%s' AND yield>0 "
    "ORDER BY yield DESC ,sharpe DESC, maxdd ASC, maxddd ASC limit %lu",pid.c_str(),N);

  MYSQL* conn = (MYSQL*)CDB::R().conn;
  MYSQL_ROW row;
  if (mysql_query(conn, sql)) {
    fprintf(stderr, "%s\n", mysql_error(conn));
    exit(1);
  }
  MYSQL_RES *res = mysql_store_result(conn);

  int len = mysql_num_rows(res);
  int i = 0;
  param p;
  vp.reserve(len);
  while ((row = mysql_fetch_row(res)) != nullptr){
    i = 0;
    p.pid = row[i++];
    p.f = row[i++];
    p.t = row[i++];
    p.win = atoi(row[i++]);
    p.step = atof(row[i++]);
    p.bbh = atof(row[i++]);
    p.bbl = atof(row[i++]);
    p.meat = atof(row[i++]);
    p.dst = atof(row[i++]);
    p.dwin = atoi(row[i++]);
    p.mc = atoi(row[i++]);
    p.smr = atoi(row[i++]);
    p.ms = atof(row[i++]);

    if (!CConfig::R().isbacktest){
      p.win *= 3;
    }

    vp.push_back(p);
  }
  mysql_free_result(res);
  return vp;
}

/*void SaveBestParam(const param& oprm){
ofstream bpConfig(fpath2, ios::app);
cereal::JSONOutputArchive  oarchive(bpConfig);
oarchive(CEREAL_NVP(oprm));
//oarchive(cereal::make_nvp(name, oprm));
}*/
