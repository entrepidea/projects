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

#include <myData.h>
#include <params.h>
#include <sentosadb.h>
#include <mysql.h>

//////////////////////////////////////////////////////////////////////////
void hData::tanlaysis(void* prm){
  param* p = (param*)prm;
  int dwin = p->dwin;
  int win = p->win;
  _day.calTAindicator(dwin);
  if (_day.pm){
    _day.pm->calTAindicator(dwin);
  }
  if (_day.ps){
    _day.ps->calTAindicator(dwin);
  }
  _15s.calTAindicator(win);
  if (_15s.pm){
    _15s.pm->calTAindicator(win);
  }
  if (_15s.ps){
    _15s.ps->calTAindicator(win);
  }
}

void tsbar::tanlaysis(void* prm, _RTDATA_TYPE t){
  param* p = (param*)prm;
  int dwin = p->dwin;
  int win = p->win;
  if (t == RTDALL || t == RTDDAY){
    _dy.calTAindicator(dwin);
    if (_dy.pm){
      _dy.pm->calTAindicator(dwin);
    }
    if (_dy.ps){
      _dy.ps->calTAindicator(dwin);
    }
  }
  if (t == RTDALL || t == RTD15s){
    _5s.calTAindicator(win);
    if (_5s.pm){
      _5s.pm->calTAindicator(win);
    }
    if (_5s.ps){
      _5s.ps->calTAindicator(win);
    }
  }
}
//////////////////////////////////////////////////////////////////////////

void getBA(char* tbl, CSTR& symbol, CSTR& from, CSTR& to, sSeries&){
  printf("TODO\n");
}
bool getBlueRed(sSeries& mb, sSeries& ma, sSeries& sb, sSeries& sa, sSeries& spd){
  printf("TODO\n");
  return true;
}
bool getDVRFromBidAsk(hData& hd, CSTR& from, CSTR& to){
  printf("TODO\n");
  return true;
}
hData* prepareData(CSTR& m, CSTR& s, CSTR& f, CSTR& t){
  printf("TODO\n");
  return 0;
}
void getOHLCWV(char* tbl, CSTR& symbol, CSTR& f, CSTR& t, dSeries& ts_){
  printf("TODO\n");
}
void getOHLCWV(char* tbl, CSTR& symbol, CSTR& f, CSTR& t, sSeries& ts_){
  printf("TODO\n");
}

//////////////////////////////////////////////////////////////////////////
//assume bid, ask in some range
/*
void getBA(char* tbl, CSTR& symbol, CSTR& from, CSTR& to, sSeries& t){
  //timer tr;
  char sql[256] = {};
  sprintf(sql, "select h,l,dt from %s where s='%s' "
    "and dt between '%s' and '%s' order by dt ASC",
    tbl, symbol.c_str(), from.c_str(), to.c_str());
  MYSQL* conn = (MYSQL*)CDB::R().conn;
  MYSQL_ROW row;
  if (mysql_query(conn, sql)) {
    fprintf(stderr, "%s\n", mysql_error(conn));
    exit(1);
  }
  MYSQL_RES *res = mysql_store_result(conn);
  //MYSQL_RES *res = mysql_use_result(conn);
  int len = mysql_num_rows(res);
  uint64_t j = 0;

  t.h.resize(len);
  t.l.resize(len);
  t.dt.resize(len);

  double prev_h, prev_l;
  while ((row = mysql_fetch_row(res)) != nullptr){
    int i = 0;
    t.h[j] = atof(row[i++]);
    t.l[j] = atof(row[i++]);
    if (t.h[j] - t.l[j] > 100){
      TTPrintNoTime("DATA ERROR. Auto Fix: %.2f,%.2f => %.2f,%.2f\n",
        t.h[j], t.l[j], prev_h, prev_l);
      t.h[j] = prev_h;
      t.l[j] = prev_l;
    }
    t.dt[j] = row[i++];
    prev_h = t.h[j];
    prev_l = t.l[j];
    j++;
  }
  mysql_free_result(res);
}

//blue = master.bid - slave.ask;//blue
//red = master.ask - slave.bid;//red
bool getBlueRed(sSeries& mb, sSeries& ma, sSeries& sb, sSeries& sa, sSeries& hs){
  if (ma.dt.size() != sa.dt.size()){
    return false;
  }
  size_t datalen = ma.dt.size();
  char fName[64];
  string cvsfile = CConfig::R().BIDASKDIR + "%s-%s-15s.csv";
  sprintf(fName, cvsfile.c_str(), ma.symbol.c_str(), sa.symbol.c_str());
  FILE* fDescr = fopen(fName, "w");
  if (fDescr == nullptr){
    TTPrintNoTime("Cannot create file:%s\n", fName);
    return false;
  }
  hs.b.resize(datalen);
  hs.r.resize(datalen);
  hs.spd.resize(datalen);
  for (uint64_t i = 0; i < datalen; ++i){
    hs.b[i] = mb.l[i] - sa.h[i];
    hs.r[i] = ma.h[i] - sb.l[i];
    hs.spd[i] = hs.r[i] - hs.b[i];
    if (fDescr){
      fprintf(fDescr, "%s,%s,%.2f,%.2f,%s,%.2f,%.2f\n",
        sa.dt[i].c_str(),
        ma.symbol.c_str(), mb.l[i], ma.h[i],
        sa.symbol.c_str(), sb.l[i], sa.h[i]);
    }
  }
  hs.dt = sa.dt;

  fclose(fDescr);
  return true;
}

// TODO - get stock range as well!
bool getDVRFromBidAsk(hData& hd, CSTR& f, CSTR& t){
  sSeries& _dy = hd._day;
  sSeries& _ts = hd._15s;
  char sql[256] = {};
  sprintf(sql,
    "select dt,rng,jmp from `drange` where s='%s' and "
    "dt between '%s' and '%s' order by dt ASC",
    _dy.symbol.c_str(), f.c_str(), t.c_str());
  MYSQL* conn = (MYSQL*)CDB::R().conn;
  if (mysql_query(conn, sql)) {
    SENTOSAERROR;
    TTPrint("%s\n", mysql_error(conn));
    exit(1);
  }
  MYSQL_RES *res = mysql_store_result(conn);
  int len = mysql_num_rows(res);
  _dy.vrange.resize(len);
  _dy.opjump.resize(len);
  int i = 0, j = 0;
  MYSQL_ROW row;
  string dt;
  while ((row = mysql_fetch_row(res)) != nullptr){
    i = 0;
    dt = row[i++];
    if (dt == _dy.dt[j].substr(0, 10)){
      _dy.vrange[j] = (atof(row[i++]));
      _dy.opjump[j] = (atof(row[i++]));
    }
    else{
      _dy.vrange[j] = 0;
      _dy.opjump[j] = 0;
    }
    j++;
  }
  mysql_free_result(res);
  if (_dy.dt.size() == _dy.vrange.size()){
    return true;
  }
  //////////////////////////////////////////////////////////////////////////

  VECDL blu_, red_;
  VECST ds;
  VECDL vr, op;
  blu_.reserve(6.5*SECONDS_IN_ONE_HOU / 15);
  red_.reserve(6.5*SECONDS_IN_ONE_HOU / 15);
  string tmp = _ts.dt[0].substr(0, 10);
  ds.push_back(tmp);
  blu_.push_back(_ts.b[0]);
  red_.push_back(_ts.r[0]);
  for (uint64_t i = 1; i < _ts.dt.size(); ++i){
    CSTR& dt = _ts.dt[i];
    //t.hlspread.push_back(t.h[i] - t.l[i]);
    string d = dt.substr(0, 10);
    if (tmp != d){
      double tmp1 = *max_element(blu_.begin(), blu_.end());
      double tmp2 = *min_element(red_.begin(), red_.end());
      vr.push_back(tmp1 - tmp2);
      op.push_back(_ts.w[i] - _ts.w[i - 1]);
      tmp = d;
      ds.push_back(tmp);
      blu_.clear();// wont affect the capacity!
      red_.clear();
    }
    blu_.push_back(_ts.b[i]);
    red_.push_back(_ts.r[i]);
  }
  while (!blu_.empty() && !blu_.empty()){
    double tmp1 = *max_element(blu_.begin(), blu_.end());
    double tmp2 = *min_element(red_.begin(), red_.end());
    vr.push_back(tmp1 - tmp2);
    op.push_back(_ts.w[i] - _ts.w[i - 1]);
    blu_.clear();
    red_.clear();
  }


  for (int i = 0; i < ds.size(); ++i){
    string d = ds[i];
    sprintf(sql,
      "INSERT IGNORE INTO `drange`(s,dt,rng,jmp)VALUES ('%s','%s',%.2f,%.2f)",
      _ts.symbol.c_str(), d.c_str(), vr[i], op[i]);
    insertARow(sql);
  }
  return true;
}

// m - master; s - slave; f - from; t - to
hData* prepareData(CSTR& m, CSTR& s, CSTR& f, CSTR& t){
  timer __t(__FUNCTION__);
  hData* hd = new hData();

  // 1. get blue, red data for 15s bar
  {
    sSeries mask(m); getBA("bar15sask", m, f, t, mask);
    sSeries sask(s); getBA("bar15sask", s, f, t, sask);
    sSeries mbid(m); getBA("bar15sbid", m, f, t, mbid);
    sSeries sbid(s); getBA("bar15sbid", s, f, t, sbid);
    if (!getBlueRed(mbid, mask, sbid, sask, hd->_15s)){
      return nullptr;
    }
  }

  // 2. get wap data for 15s bar
  {
    sSeries* h1 = new sSeries(m);
    sSeries* h2 = new sSeries(s);
    hd->_15s.pm = h1;
    hd->_15s.ps = h2;
    //h1->resizeBar(hd->_15s.dt.size());
    //h2->resizeBar(hd->_15s.dt.size());
    getOHLCWV("bar15s", m, f, t, *h1);
    getOHLCWV("bar15s", s, f, t, *h2);
    if (h1->dt.size() != h2->dt.size()){
      return nullptr;
    }
    hd->_15s.symbol = pairID(m, s);
    hd->_15s.w.reserve(h1->w.size());
    for (uint64_t i = 0; i < h1->dt.size(); ++i){
      hd->_15s.w.push_back(h1->w[i] - h2->w[i]);
    }
  }

  // 3. get wap daily data
  {
    sSeries* h3 = new sSeries(m);
    sSeries* h4 = new sSeries(s);
    hd->_day.pm = h3;
    hd->_day.ps = h4;
    //h3->resizeBar(hd->_day.dt.size());
    //h4->resizeBar(hd->_day.dt.size());
    getOHLCWV("bar1d", m, f, t, *h3);
    getOHLCWV("bar1d", s, f, t, *h4);
    if (h3->dt.size() != h4->dt.size()){
      return nullptr;
    }
    hd->_day.symbol = pairID(m, s);
    hd->_day.w.reserve(h3->w.size());
    for (uint64_t i = 0; i < h3->dt.size(); ++i){
      hd->_day.w.push_back(h3->w[i] - h4->w[i]);
    }
    hd->_day.dt = h3->dt;
  }

  // 4. get range and open jump data
  getDVRFromBidAsk(*hd, f, t);

  return hd;
}


void getOHLCWV(char* tbl, CSTR& symbol, CSTR& f, CSTR& t, dSeries& ts_){
  char sql[256] = {};
  std::sprintf(sql, "select o,h,l,c,w,v,dt from %s where s='%s' and"
    " dt between '%s' and '%s' order by dt ASC",
    tbl, symbol.c_str(), f.c_str(), t.c_str());
  MYSQL* conn = (MYSQL*)CDB::R().conn;
  if (mysql_query(conn, sql)) {
    fprintf(stderr, "%s\n", mysql_error(conn));
    exit(1);
  }
  MYSQL_RES *res = mysql_store_result(conn);
  int dbRowNum = mysql_num_rows(res);
  int pass = dbRowNum - ts_.w.size();
  int pad = 0;
  if (strcmp(tbl,"bar5s")==0 && dbRowNum> 0 && pass>-500 && pass<0){
    pad = -pass;
    pass = 0;
  }
  if ((dbRowNum>0) && (pass >= 0)){
    int j = 0;
    MYSQL_ROW row;
    while ((row = mysql_fetch_row(res)) != nullptr){
      if (pass <= 0){
        int i = 0;
        ts_.o[j + pad] = (atof(row[i++]));
        ts_.h[j + pad] = (atof(row[i++]));
        ts_.l[j + pad] = (atof(row[i++]));
        ts_.c[j + pad] = (atof(row[i++]));
        ts_.w[j + pad] = (atof(row[i++]));
        ts_.v[j + pad] = (atof(row[i++]));
        ts_.dt[j + pad] = (row[i++]);
        j++;
        if (j >= ts_.w.size()){
          break;
        }
      }
      else{
        pass--;
      }
    }
    for(int x = pad-1; x >= 0; --x){
      ts_.o[x] = ts_.o[x + 1];
      ts_.h[x] = ts_.h[x + 1];
      ts_.l[x] = ts_.l[x + 1];
      ts_.c[x] = ts_.c[x + 1];
      ts_.w[x] = ts_.w[x + 1];
      //ts_.v[x] = ts_.v[x + 1];
      ts_.v[x] = 0;
      ts_.dt[x] = ts_.dt[x+1];
    }
  }else{
    bool HKStock = ('0' <= symbol[0] && symbol[0] <= '9');
    if (!HKStock && !CConfig::R().isFX(symbol)){
      TTPrintr("<%s>Container Size(%s:ts_.w.size())=%lu,dbRowNum=%d\n",
        symbol.c_str(), tbl, ts_.w.size(), dbRowNum);
      TTPrintr("<%s>%s\n", symbol.c_str(), sql);
      TTPrintr("Please check if datasvr is running!\n");
      SENTOSAERROR2("Please check if datasvr is running!");
      //exit(1);
    }
  }
  ts_.symbol = symbol;
  mysql_free_result(res);
  //TTPrint("[%s]%s\n", __FUNCTION__, sql);
  TTPrint("[%s]<%s>,Container Size(%s:ts_.w.size())=%lu,dbRowNum=%d\n",
    __FUNCTION__, symbol.c_str(), tbl, ts_.w.size(), dbRowNum);
}

void getOHLCWV(char* tbl, CSTR& symbol, CSTR& f, CSTR& t, sSeries& ts_){
  char sql[256] = {};
  std::sprintf(sql, "select o,h,l,c,w,v,dt from %s where s='%s' and"
    " dt between '%s' and '%s' order by dt ASC",
    tbl, symbol.c_str(), f.c_str(), t.c_str());
  MYSQL* conn = (MYSQL*)CDB::R().conn;
  if (mysql_query(conn, sql)) {
    fprintf(stderr, "%s\n", mysql_error(conn));
    exit(1);
  }
  MYSQL_RES *res = mysql_store_result(conn);
  int dbRowNum = mysql_num_rows(res);
  int pass = dbRowNum - ts_.w.size();
  int pad = 0;
  if (strcmp(tbl,"bar5s")==0 && dbRowNum> 0 && pass>-500 && pass<0){
    pad = -pass;
    pass = 0;
  }
  if ((dbRowNum>0) && (pass >= 0)){
    int j = 0;
    MYSQL_ROW row;
    while ((row = mysql_fetch_row(res)) != nullptr){
      if (pass <= 0){
        int i = 0;
        ts_.o[j + pad] = (atof(row[i++]));
        ts_.h[j + pad] = (atof(row[i++]));
        ts_.l[j + pad] = (atof(row[i++]));
        ts_.c[j + pad] = (atof(row[i++]));
        ts_.w[j + pad] = (atof(row[i++]));
        ts_.v[j + pad] = (atof(row[i++]));
        ts_.dt[j + pad] = (row[i++]);
        j++;
        if (j >= ts_.w.size()){
          break;
        }
      }
      else{
        pass--;
      }
    }
    for(int x = pad-1; x >= 0; --x){
      ts_.o[x] = ts_.o[x + 1];
      ts_.h[x] = ts_.h[x + 1];
      ts_.l[x] = ts_.l[x + 1];
      ts_.c[x] = ts_.c[x + 1];
      ts_.w[x] = ts_.w[x + 1];
      //ts_.v[x] = ts_.v[x + 1];
      ts_.v[x] = 0;
      ts_.dt[x] = ts_.dt[x+1];
    }
  }else{
    bool HKStock = ('0' <= symbol[0] && symbol[0] <= '9');
    if (!HKStock && !CConfig::R().isFX(symbol)){
      TTPrintr("<%s>Container Size(%s:ts_.w.size())=%lu,dbRowNum=%d\n",
        symbol.c_str(), tbl, ts_.w.size(), dbRowNum);
      TTPrintr("<%s>%s\n", symbol.c_str(), sql);
      TTPrintr("Please check if datasvr is running!\n");
      SENTOSAERROR2("Please check if datasvr is running!");
      //exit(1);
    }
  }
  ts_.symbol = symbol;
  mysql_free_result(res);
  //TTPrint("[%s]%s\n", __FUNCTION__, sql);
  TTPrint("[%s]<%s>,Container Size(%s:ts_.w.size())=%lu,dbRowNum=%d\n",
    __FUNCTION__, symbol.c_str(), tbl, ts_.w.size(), dbRowNum);
}
*/
