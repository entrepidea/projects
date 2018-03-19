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

#ifndef __MYDATA__
#define __MYDATA__

#include <datasource.h>
#include <gconfig.h>
#include <sentosadb.h>

using sSeries = tseries < VECDL > ;
using dSeries = tseries < swind > ;

//#include <boost/circular_buffer.hpp>
//using dSeries = tseries < boost::circular_buffer<double> >;

struct hData{
  sSeries _day;
  sSeries _15s;
  void tanlaysis(void* prm);
};

enum _RTDATA_TYPE{ RTDALL = 0, RTD15s, RTDDAY };

//基本数据类型是一个sliding window buffer
struct tsbar{
  //dSeries _dy; //其实是静态的因为没有新的daily数据进来
  dSeries _dy;
  dSeries _15s;//静态数据,load之后就不变

  dSeries _5s; //动态的数据,load之后会变,新来的数据会"挤掉"旧数据
  void tanlaysis(void* prm, _RTDATA_TYPE = RTDALL);
};

void getBA(char* tbl, CSTR& symbol, CSTR& from, CSTR& to, sSeries&);
bool getBlueRed(sSeries& mb, sSeries& ma, sSeries& sb, sSeries& sa, sSeries& spd);
bool getDVRFromBidAsk(hData& hd, CSTR& from, CSTR& to);//get DVR(daily valid range) from bid ask

//prepareData is only used in BackTest. This is different from loadhData.
// return a pointer to hData.
// the data include:
//   1. 15s bid/ask data
//   2. 15s spread data
//   3. daily valid range
hData* prepareData(CSTR& m, CSTR& s, CSTR& f, CSTR& t);

void getOHLCWV(char* tbl, CSTR& symbol, CSTR& f, CSTR& t, dSeries& ts_);
void getOHLCWV(char* tbl, CSTR& symbol, CSTR& f, CSTR& t, sSeries& ts_);


/*template<typename T>
void getOHLCWV(char* tbl, CSTR& symbol, CSTR& f, CSTR& t, T& ts_){
  char sql[256] = {};
  std::sprintf(sql, "select o,h,l,c,w,v,dt from %s where s='%s' and"
    " dt between '%s' and '%s' order by dt ASC",
    tbl, symbol.c_str(), f.c_str(), t.c_str());
  MYSQL* conn = CDB::R().conn;
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
}*/

#endif
