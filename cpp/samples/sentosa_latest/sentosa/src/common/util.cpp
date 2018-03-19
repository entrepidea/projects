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

#include "util.h"
#include <ta_libc.h>
#include <bstTime.h>
#include <chrono> //for msleep
#include <thread> //for msleep
#include <boost/filesystem/operations.hpp>//last_write_time
#include <gconfig.h>
#include <mutex>
#include <iostream>
#include <unistd.h>
#include <dirent.h>
#include <sys/types.h> // for opendir(), readdir(), closedir()
#include <sys/stat.h> // for stat()
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdarg.h>

#include <fcntl.h>

using namespace std;

#define PROC_DIRECTORY "/proc/"

int IsNumeric(const char* p){
  for ( ; *p; p++){
    if (*p < '0' || *p > '9'){
      return 0; // false
    }
  }
  return 1; // true
}


string readcmdline(const char* proc_cmd, bool CMDONLY){
  const int BUFSIZE = 4096; // should really get PAGESIZE or something instead...
  char buffer[BUFSIZE]; // dynamic allocation rather than stack/global would be better

  int fd = open(proc_cmd, O_RDONLY);
  int nbytesread = read(fd, buffer, BUFSIZE);
  char *end = buffer + nbytesread;
  string cmd;
  string space(" ");
  int i=0;
  for (char *p = buffer; p < end;){
    if(i++ == 0){
      cmd.append(p);
      if(CMDONLY){return cmd;}
    }else{
      cmd += space + p;
    }
    while (*p++); // skip until start of next 0-terminated section
  }

  close(fd);
  return cmd;
}

pid_t GetPIDbyName(const char* p, bool CMDONLY){
  char cmdpath[128]  ={0};
  char procname[4096]  ={0};
  pid_t r = -1 ;
  struct dirent* de_DirEntity = nullptr ;
  DIR* dir_proc = nullptr ;

  dir_proc = opendir(PROC_DIRECTORY) ;
  if (dir_proc == nullptr){
    perror("Couldn't open the " PROC_DIRECTORY " directory") ;
    return (pid_t) -2 ;
  }

  // Loop while not nullptr
  while ( (de_DirEntity = readdir(dir_proc)) ){
    if (de_DirEntity->d_type == DT_DIR){
      if (IsNumeric(de_DirEntity->d_name)){
        if(atoi(de_DirEntity->d_name) == getpid()){
          continue;
        }
        strcpy(cmdpath, PROC_DIRECTORY) ;
        strcat(cmdpath, de_DirEntity->d_name) ;
        strcat(cmdpath, "/cmdline") ;
        //printf("cmd:%s\n",cmdpath);

        string s = readcmdline(cmdpath, CMDONLY);
        if (s.find(p) != s.npos){
          return atoi(de_DirEntity->d_name);
        }
        //cout << "CMD:" << s << endl;
      }

    }
  }
  closedir(dir_proc) ;
  return r ;
}

/**
 * Set Process Name
 * Directly modify the arguments
 * This needs a pointer to the original arvg, as passed to main(),
 * and is **limited to the length of the original argv[0]**
 */
int SetProcName(char** argv, int argc, const char *procname){
  size_t argv0_len = strlen(argv[0]);
  size_t procname_len = strlen(procname);
  size_t new_procname_len = min(argv0_len, procname_len);

  strncpy(argv[0], procname, new_procname_len);
  // Clear out the rest (yes, this is needed, or the remaining part of the old
  // process name will still show up in ps)
  memset(&argv[0][new_procname_len], 0, argv0_len - new_procname_len);

  // Clear the other passed arguments(optional)
  // Needs to know argv and argc as passed to main()
  for (int i = 1; i < argc; i++) {
    memset(argv[i], 0, strlen(argv[i]));
  }
}

bool isIBRunning(){
  if(startwith(CR(IBHOST),"127") || (CR(IBHOST)=="localhost")){
    return GetPIDbyName("java -cp jts.jar", false) > 0;
  }else{
    return true;
  }
}

bool isSentosaRunning(){
  return (GetPIDbyName("sentosa", true)>0) &&
      (GetPIDbyName(PROCESSNAME, true)>0);
}

string expand_user(const string& p) {
  string path(p);
  if (not path.empty() and path[0] == '~') {
    assert(path.size() == 1 or path[1] == '/');
    char const* home = getenv("HOME");
    if (home || (home = getenv("USERPROFILE"))){
      path.replace(0, 1, home);
    }else{
      char const *hdrive = getenv("HOMEDRIVE"),
        *hpath = getenv("HOMEPATH");
      assert(hdrive);
      assert(hpath);
      path.replace(0, 1, string(hdrive) + hpath);
    }
  }
  return path;
}

string changeDateStringFormat(const string& str1, const char* f, char* t){
  int year, mont, day, h, m, s;
  sscanf(str1.c_str(), f, &year, &mont, &day, &h, &m, &s);
  char buf[64] = {};
  sprintf(buf, t, year, mont, day, h, m, s);
  return buf;
}

timer::timer(const char* s) :_start(GetSystemTimeAsULL()), str(s){}
timer::~timer(){
  uint64_t tmp = GetSystemTimeAsULL() - _start;
  TTPrintr("[%s]%lu ms\n", str, tmp/1000);
}

bool NYSETradingHour(){
  time_t rawtime;
  struct tm timeinfo;
  time(&rawtime);
  LOCALTIME_S(&timeinfo, &rawtime);
  //cout << timeinfo.tm_hour << " " << timeinfo.tm_min << " " << timeinfo.tm_sec << endl;
  if (timeinfo.tm_hour < 9){
    return 0;
  }
  else if (timeinfo.tm_hour == 9 && timeinfo.tm_min<30) {
    return 0;
  }
  else if (timeinfo.tm_hour>16){
    return 0;
  }
  else if (timeinfo.tm_hour == 16 && timeinfo.tm_min>1){
    return 0;
  }
  return 1;
}

// return 10 for invalid time,
double NYSETradingTimeLeft(){
  static const double TOTALTIME = ((16 - 9.5) * 60 * 60);
  time_t rawtime;
  struct tm timeinfo;
  time(&rawtime);
  LOCALTIME_S(&timeinfo, &rawtime);
  int hdiff = 15 - timeinfo.tm_hour;
  int mdiff = 59 - timeinfo.tm_min;
  int sdiff = 60 - timeinfo.tm_sec;
  if (hdiff < 7 && hdiff >= 0){
    double remaining = hdiff * 3600.0 + mdiff * 60 + sdiff;
    if (remaining > TOTALTIME){ return 10.0; }
    return remaining / TOTALTIME;
  }
  return 10.0;
}

bool IsAfterNYSETradingHour(){
  time_t rawtime;
  struct tm timeinfo;
  time(&rawtime);
  LOCALTIME_S(&timeinfo, &rawtime);
  if (timeinfo.tm_hour>16){
    return true;
  } else if (timeinfo.tm_hour == 16 && timeinfo.tm_min>1){
    return true;
  }
  return false;
}

vector<string> splitstrwhitespace(const string& sentence){
  vector<string> r;
  istringstream iss(sentence);
  copy(istream_iterator<string>(iss),
    istream_iterator<string>(),
    back_inserter<vector<string> >(r));
  return r;
}


vector<string> &__splitv2(const string &s, char delim,
  vector<string> &elems)
{
  stringstream ss(s);
  string item;
  while (getline(ss, item, delim)) {
    elems.push_back(item);
  }
  return elems;
}

vector<string> splitv2(const string &s, char delim)
{
  vector<string> elems;
  __splitv2(s, delim, elems);
  return elems;
}

bool startwith(const string& x,const string& y){
  return x.find(y) == 0;
}

string ymd(){
  char buf[128] = { 0 };
  const size_t sz = sizeof("2014-00-00");
  {
    time_t timer;
    struct tm* tm_info;
    time(&timer);
    tm_info = localtime(&timer);
    strftime(buf, sz, "%Y-%m-%d", tm_info);
  }
  return string(buf);
}

string ymdhms(const char* format, time_t offset){
  char buf[128] = { 0 };
  {
    time_t timer;
    time(&timer);
    timer += offset;
    struct tm* tm_info = localtime(&timer);
    strftime(buf, 128, format, tm_info);
  }
  return string(buf);
}

PSS GetExchangeCurrency(const string& symbol, CONTRACT_TYPE rt){
  string exchange, currency;
  if (isSEHKNTL(symbol)){
    return PSS("SEHKNTL", "CNH");
  }
  bool HKStock = isHKSE(symbol);
  if (HKStock){
    exchange = "SEHK";
    currency = "HKD";
  }else{
    if (CConfig::R().isFX(symbol)){
      exchange = "IDEALPRO";
      currency = symbol.substr(4,3);
    }else{
      if (rt == MKDATA_STOCK || rt == MKDATA_OPTION){
        exchange = "SMART";
      }
      else if (rt == MKDPETH_STOCK){
        exchange = "ISLAND";
      }
      currency = "USD";
    }
  }
  return PSS(exchange, currency);
}

time_t getFileWriteTime(const string& p){
  boost::filesystem::path mypath(p);
  if (boost::filesystem::exists(mypath)) {
    return boost::filesystem::last_write_time(mypath);
  }
  return 0;
}

//https://www.interactivebrokers.com/en/software/api/apiguide/api/historical_data_limitations.htm
uint64_t str2sec(const string& s){
  vector<string> vs = splitv2(s, ' ');
  if (vs.size() != 2){
    return 0;
  }
  int i = atoi(vs[0].c_str());
  if (vs[1] == "W")
  {
    return i*SECONDS_IN_ONE_WEE;
  }
  else if (vs[1] == "S" || vs[1] == "secs")
  {
    return i;
  }
  else if (vs[1] == "mins")
  {
    return i * SECONDS_IN_ONE_MIN;
  }
  else if (vs[1] == "hour")
  {
    return i * SECONDS_IN_ONE_HOU;
  }
  else if (vs[1] == "M")
  {
    return i * SECONDS_IN_ONE_MON;
  }
  else if (vs[1] == "D")
  {
    return i * SECONDS_IN_ONE_DAY;
  }
  else if (vs[1] == "Y")
  {
    return i * SECONDS_IN_ONE_YEA;
  }
  return 0;
}

bool __isWeekEnd(const string& str) {
  string tmp = str.substr(0, 10) + " 12:00:00";
  time_t dt = str2time_t(tmp);
  struct tm* tm_info = localtime(&dt);
  return (tm_info->tm_wday == 6 || tm_info->tm_wday == 0);
}

bool isNonTradeDay(const string& str){
  return __isWeekEnd(str) || __isNYSEHoliday(str);
}

PDD mastd(const VECDL& v){
  int outBeg;
  int outNbElement;
  double ma_, std_;
  size_t z = v.size();
  TA_MA(0, z - 1, &v[0], z, TA_MAType_EMA, &outBeg, &outNbElement, &ma_);
  TA_STDDEV(0, z - 1, &v[0], z, 1, &outBeg, &outNbElement, &std_);
  return make_pair(ma_, std_);
}

static string closeT = closeTime(ymdhms());
static time_t todayend = str2time_t(closeT);
static time_t todayopn = todayend-6.5*3600;
static mutex __timemtx;
// -1.0...|(MarketOpen)======...======|(MarketClose)....-2.0
double percentTime(const char* curdt){
  lock_guard<mutex> g(__timemtx);
  string _dt = (curdt)?curdt:ymdhms();
  time_t now = str2time_t(_dt);
  if (todayend>now && todayopn<now){
    return (todayend - now) / (6.5 * 3600);
  }else if(now<todayopn){
    return -1.0;
  }else{
    return -2.0;
  }
}

/*
if str = 2014-08-16 06:20:00, offset = 900s,
it should return 2014-08-15 16:00:00 - 900s.
*/

string RollBackTradeTime(const string& str, time_t offset){
  string tmp(str);
  while(isNonTradeDay(tmp)){
    tmp = closeTime(prevTradeDT(tmp));
  }
  time_t curT = str2time_t(tmp);
  string CurDayOpenStr = openTime(tmp);
  time_t CurDayOpenT = str2time_t(CurDayOpenStr);
  if (curT<CurDayOpenT){
    string prevDCloseT = closeTime(prevTradeDT(tmp));
    return RollBackTradeTime(prevDCloseT, offset);
  }
  int d = (curT - CurDayOpenT) - offset;
  if (d<=0){
    string prevDCloseT = closeTime(prevTradeDT(tmp));
    return RollBackTradeTime(prevDCloseT, -d);
  }

  return rollbackTime(tmp,offset);
}

//TODO
string RollBackTradeTimeFX(const string& str, time_t offset){
  string tmp(str);
  while (__isWeekEnd(tmp)){
    tmp = closeTime(prevTradeDT(tmp),FOREX);
  }
  time_t curT = str2time_t(tmp);
  string CurDayOpenStr = openTime(tmp,FOREX);
  time_t CurDayOpenT = str2time_t(CurDayOpenStr);
  if (curT < CurDayOpenT){
    string prevDCloseT = closeTime(prevTradeDT(tmp), FOREX);
    return RollBackTradeTimeFX(prevDCloseT, offset);
  }
  int d = (curT - CurDayOpenT) - offset;
  if (d <= 0){
    string prevDCloseT = closeTime(prevTradeDT(tmp), FOREX);
    return RollBackTradeTimeFX(prevDCloseT, -d);
  }
  return rollbackTime(tmp, offset);
}

string openTime(const string& s, MARKETNAME mn){
  string OT = ((mn == USA) ? " 09:30:00" : " 21:30:00");
  if (mn == FOREX){
    OT = " 00:00:15";
  }
  const static size_t sz0 = sizeof("0000-00-00 00:00:00") - 1;
  const static size_t sz1 = sizeof("00000000 00:00:00") - 1;
  if (s.size()==sz0){
    return s.substr(0,10) + OT;
  }
  else if (s.size() == sz1){
    return s.substr(0, 8) + OT;
  }
  else if (s.size() == sizeof("00000000")-1 || s.size() == sizeof("0000-00-00")-1){
    return s + OT;
  }
  return "";
}

string closeTime(const string& s, MARKETNAME mn){
  string CT = ((mn == USA) ? " 16:00:00" : " 04:00:00");
  if (mn == FOREX){
    CT = " 23:59:45";
  }
  if (s.size() == sizeof("0000-00-00 00:00:00")-1){
    return s.substr(0, 10) + CT;
  }
  else if (s.size() == sizeof("00000000 00:00:00")-1){
    return s.substr(0, 8) + CT;
  }
  else if (s.size() == sizeof("00000000")-1 || s.size() == sizeof("0000-00-00")-1){
    return s + CT;
  }
  return "";
}

VECST get5sBarEndstrVec(const string& now, uint64_t step)
{
  VECST r;
  if (step == 0){
    return r;
  }
  int s = atoi(now.substr(17, 2).c_str());
  int m = atoi(now.substr(14, 2).c_str());
  int h = atoi(now.substr(11, 2).c_str());

  string endstr;
  if (h < 9 || (h == 9 && m < 30) || h>16 || (h == 16 && (m>1 || s > 1))){
    endstr = normalizeTradeDT(now);
  }
  else{
    endstr = now;
  }
  r.push_back(endstr);
  //string tmp = changeDateStringFormat(endstr, DT_IB_REQ, DT_MYSQL);
  endstr = RollBackTradeTime(endstr, 7200);
  //endstr = changeDateStringFormat(tmp, DT_MYSQL, DT_IB_REQ);
  VECST v = get5sBarEndstrVec(endstr, --step);
  r.insert(r.end(), v.begin(), v.end());
  return r;
}

double percentile(const double* head, const double* tail, double d){
  const double* tmp = head;
  uint64_t totalN = tail - head + 1;
  uint64_t rank = 0;
  while (tmp != tail){
    if (*tmp < d){
      ++rank;
    }
    tmp++;
  }
  return rank*1.0 / totalN;
}

string getCurExePath(){
#if defined(_WIN64)||defined(_WIN32)
  char filename[MAX_PATH];
  GetModuleFileNameA(nullptr, filename, MAX_PATH);
  return filename;
#else
  return (char *)getauxval(AT_EXECFN);
#endif
}

///Cross platform Sleep function
void msleep(uint64_t _ms){
  if(_ms == 0){return;}
  this_thread::sleep_for(chrono::milliseconds(_ms));
}

namespace testing{

  bool AddTimeTest(){
    for (uint64_t i = 1; i <= 24; ++i){
      string ss = rollbackTime("2014-02-04 09:30:00", SECONDS_IN_ONE_HOU * i);
      printf("%s\n", ss.c_str());
    }
    string s = prevTradeDT("2014-02-04 09:30:00");
    string ss = rollbackTime("2014-02-04 01:10:12", SECONDS_IN_ONE_HOU * 2);
    return (ss == "2014-02-03 23:10:12");
  }

  bool test_splitstr(){
    vector<string> vs = splitstrwhitespace("1 2 3 4 5");
    if (vs[0] != "1" ||
      vs[1] != "2" ||
      vs[2] != "3" ||
      vs[3] != "4" ||
      vs[4] != "5")
    {
      TTPrintNoTime("ERROR\n");
      return false;
    }
    return true;
  }

  bool test_splitv2(){
    vector<string> vs = splitv2("1,two,,4,5", ',');
    if (vs[0] != "1" ||
      vs[1] != "two" ||
      vs[2] != "" ||
      vs[3] != "4" ||
      vs[4] != "5")
    {
      TTPrintNoTime("ERROR\n");
      return false;
    }
    return true;
  }

}

#include <hook.h>
int check_gshutdown(bool force){
  atomic_bool* g = setcontrolhandler();
  while (!*g){
    msleep(1 * 1000);
  }
  // due to (1) ctrl-c or (2) hdata thread is done
  //TTPrint("An expected exception:-)\n");
  if (force){
    throw runtime_error("Throw a good exception to shutdown");
  }
  return 0;
}

bool has_suffix(const std::string &str, const std::string &suffix){
  return str.size() >= suffix.size() &&
    str.compare(str.size() - suffix.size(), suffix.size(), suffix) == 0;
}

bool isHKSE(string s){
  if (!isSEHKNTL(s)){
    return ('0' <= s[0] && s[0] <= '9');;
  }
  return false;
}

bool isSEHKNTL(string s){
  return has_suffix(s, ".SS");
}

string getsymbol(string s){
  if (isSEHKNTL(s)){
    return s.substr(0, s.size() - 3);
  }
  else{
    return s;
  }
}

#include <boost/filesystem.hpp>
namespace fs=boost::filesystem;
// 0 - today, 1 - yesterday(Actually是last business day)
// 这里改为 1是有记录的last business day,查过去的50天
//////////////////////////////////////////////////////////////////////////
// 一个use case就是:
// 我交易了几天之后停了几天没有交易，这个程序应该可以正确的resume!
// e.g. C:\singapore\data\tradeinfo\DU198457\ATHM
// /singapore/data/tradeinfo/DU198457/ATHM/2015-06-24.xml
string getTIpath(const string& pid_, const string& date_, int i, string postfix){
  string folder = CConfig::R().TRADEINFODIR +
      CConfig::R().account +
      fs::path::preferred_separator +
      pid_ +
      fs::path::preferred_separator;
  if (!fs::exists(folder)){
    fs::create_directories(folder);
    // Can create parent dir automatically in contrast to create_directory
  }
  string r;
  if (date_.empty()){
    if (i==0){
      r = folder + ymd() + postfix;
    }
    else{
      string tmp = prevTradeDT(ymdhms()).substr(0, 10);
      for (int i = 0; i < 50; ++i){
        r = folder + tmp + postfix;
        if (fs::exists(r)){
          break;
        }
        else{
          tmp = prevTradeDT(tmp);
        }
      }
    }
    return r;
  }else{
    if (date_.size()>10){
      return folder + date_.substr(0,10) + postfix;
    }else{
      return folder + date_ + postfix;
    }
  }
}
