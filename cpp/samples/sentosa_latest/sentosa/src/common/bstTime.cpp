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

#include "bstTime.h"
#include <boost/date_time.hpp>
#include <log.h>
#include <util.h>
#include <boost/program_options.hpp>
#include <boost/property_tree/ptree.hpp>
#include <boost/property_tree/ini_parser.hpp>
#include <cband.h>
#include <boost/algorithm/string.hpp>
#include <yaml-cpp/yaml.h>

#include <fstream>

namespace bpt = boost::property_tree;
namespace bpo = boost::program_options;

using namespace boost::posix_time;
using namespace boost::local_time;

glocale* glocale::pinstance = nullptr;
mutex glocale::__lock;

glocale& glocale::R(){
  if (pinstance == nullptr) {
    lock_guard<mutex> g(__lock);
    if (pinstance == nullptr) {
      pinstance = new glocale();
    }
  }
  return *pinstance;
}

#define SENTOSA_XSTR(s) SENTOSA_STR(s)
#define SENTOSA_STR(s) #s
#define NYTZ_OFFSET -04
#define NYTZ_STR "UTC" SENTOSA_XSTR(NYTZ_OFFSET) ":00:00"

/// http://www.timeanddate.com/time/zone/usa/new-york
glocale::glocale() :_ny_tzone(new posix_time_zone(NYTZ_STR)){
//glocale::glocale() :_ny_tzone(tz_db.time_zone_from_region("America/New_York")){
  printf("Your time zone is: %s\n", NYTZ_STR);
  __locale = locale(stringstream().getloc());
  F_DT_MYSQL2 = new local_time_facet(DT_MYSQL2);
  _s_loc = new locale(__locale, F_DT_MYSQL2);
}

glocale::~glocale(){
  delete F_DT_MYSQL2;
  delete _s_loc;
}

time_t ptime2time(ptime t){
  static ptime epoch(boost::gregorian::date(1970, 1, 1));
  time_duration::sec_type x = (t - epoch).total_seconds() - 3600 * NYTZ_OFFSET;
  //hours(4).total_seconds() = 3600 * 4
  // ... check overflow here ...
  return time_t(x);
}

gholiday* gholiday::pinstance = nullptr;
mutex gholiday::ghlock_;

gholiday& gholiday::R(){
  if (pinstance == nullptr){
    lock_guard<mutex> g(ghlock_);
    if (pinstance == nullptr){
      pinstance = new gholiday();
      pinstance->loadholiday();
    }
  }
  return *pinstance;
}

void gholiday::loadholiday(){
  YAML::Node config = YAML::LoadFile(config_holi);
  for (auto i : config) {
    string k = i.first.as<std::string>();
    YAML::Node& n = i.second;
    if (k == "us") {
      VECST tmp=n["full"].as<VECST>();
      ho.insert(tmp.cbegin(),tmp.cend());
      tmp = n["partial"].as<VECST>();
      ph.insert(tmp.cbegin(),tmp.cend());
    }
  }
}

bool __isNYSEHoliday(const string& str){
  return gholiday::R().isHO(str);
}

// 先不考虑partial holiday,出了问题再来check是不是partial Holiday的问题
bool gholiday::isHO(string str){
  return (ho.find(str.substr(0, 10)) != ho.end());
}

bool gholiday::isPH(string str){
  return (ph.find(str.substr(0, 10)) != ph.end());
}

string ptime2str(const ptime& pt){
  local_date_time dt_with_zone(pt, glocale::R()._ny_tzone);
#if 1
  tm _t = to_tm(dt_with_zone);
  char buf[32] = {0};
  strftime(buf, 32, DT_MYSQL2, &_t);
  return buf;
#else
  //using stringstream only for logging
  stringstream strm;
  strm.imbue(*glocale::R()._s_loc);
  strm << dt_with_zone;
  //strm << pt;
  return strm.str();
#endif
}

string NYCurTime(){
  ptime pt = second_clock::universal_time();
  return ptime2str(pt);
}

//http://stackoverflow.com/questions/4461586/how-do-i-convert-boostposix-timeptime-to-time-t
time_t str2time_t(const string& s){
  ptime pt(time_from_string(s));
  return ptime2time(pt);
}

string time_t2str(time_t tt){
  ptime pt = from_time_t(tt);
  return ptime2str(pt);
}

string rollbackTime(const string& s, int i){
  if (i == 0)return s;
  time_t t = str2time_t(s);
  return time_t2str(t - i);
}

string rollbackTD(const string& s, uint64_t i){
  string r = s;
  while (i-->0){
    r = prevTradeDT(r);
  }
  return r;
}

//可以接受0000-00-00的格式,输出也是这样的格式
//可以接受0000-00-00 00:00:00的格式,输出也是这样的格式
string prevTradeDT(const string& str){
  string tmp(str);
  if (tmp.size()==10){//0000-00-00
    tmp.append(" 12:00:00");
  }
  string prevdate = rollbackTime(tmp, SECONDS_IN_ONE_DAY);
  while (isNonTradeDay(prevdate)){
    prevdate = rollbackTime(prevdate, SECONDS_IN_ONE_DAY);
  }
  if (str.size()==10){
    return prevdate.substr(0, 10);
  }
  return prevdate;
}

void Test_prevTDate(){
  string dt = "2014-06-11 12:00:12";
  bool b = (prevTradeDT(dt) == "2014-06-10");
  dt = "2014-06-07 12:00:12";
  b = (prevTradeDT(dt) == "2014-06-06");
  dt = "2014-06-09 12:00:12";
  b = (prevTradeDT(dt) == "2014-06-06");
}



string normalizeTradeDT(const string& str1){
  string tmp(str1);
  int year, mont, day, h, m, s;
  sscanf(tmp.c_str(), DT_MYSQL, &year, &mont, &day, &h, &m, &s);
  if (h < 9 || (h == 9 && m < 30)){
    tmp = prevTradeDT(tmp);
    sscanf(tmp.c_str(), DT_MYSQL, &year, &mont, &day, &h, &m, &s);
  }
  char buf[64] = {};
  sprintf(buf, "%04d-%02d-%02d 16:00:00", year, mont, day);
  tmp = buf;
  while (isNonTradeDay(tmp)){
    tmp = prevTradeDT(tmp);
  }
  return tmp;
}
//////////////////////////////////////////////////////////////////////////

string nowMS(){
  char buf[128] = {};
#ifdef __linux__
  struct timespec ts = {0,0};
  struct tm tm = {};
  char timbuf[64]={};
  clock_gettime(CLOCK_REALTIME, &ts);
  time_t tim = ts.tv_sec;
  localtime_r(&tim, &tm);
  strftime(timbuf, sizeof(timbuf), "%F %T", &tm);
  snprintf(buf, 128, "%s.%03d", timbuf, (int)(ts.tv_nsec/1000000));
#else
  SYSTEMTIME SystemTime;
  GetLocalTime(&SystemTime);
  sprintf(buf, "%04u-%02u-%02u %02u:%02u:%02u.%03u",
    SystemTime.wYear, SystemTime.wMonth, SystemTime.wDay,
    SystemTime.wHour, SystemTime.wMinute, SystemTime.wSecond, SystemTime.wMilliseconds);
#endif
  return buf;
}

