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

#ifndef COMMON_UTIL_H
#define COMMON_UTIL_H

#include <sstream>
#include <iterator>
#include <EWrapper.h>
#include <sentosaconfig.h>
#include <log.h>
#include <getRealTime.h>
#include <future>

using std::string;
using std::vector;
using std::pair;

enum ORDERTYPE{
  LMT=0,MKT,COMBO
};
enum MARKETNAME{
  USA = 0, HKSE, FOREX
};

string nowMS();

#define GetSystemTimeAsULL getMicroTime

struct timer{
  uint64_t _start;
  const char* str;

  timer(const char* s = "");
  ~timer();
};

#define pairID(M_,S_) (M_+"-"+S_)
#ifdef __linux__
#define LOCALTIME_S(x,y) localtime_r(y,x)
#else
#define LOCALTIME_S(x,y) localtime_s(x,y)
#endif

//#define ISHKStock(s) ('0' <= s[0] && s[0] <= '9')

bool NYSETradingHour();
bool IsAfterNYSETradingHour();
double NYSETradingTimeLeft();

vector<string> splitstrwhitespace(const string& sentence);
vector<string> splitv2(const string &s, char delim);
bool startwith(const string&,const string&);

int SetProcName(char** argv, int argc, const char *procname);
bool isIBRunning();
bool isSentosaRunning();

//return date like: 2014-00-00
string ymd();
string ymdhms(const char* format = DT_MYSQL2, time_t offset = 0);

string expand_user(const string& path);

string changeDateStringFormat(const string& str1,const char* f,char* t);
string openTime(const string& s, MARKETNAME mn=USA);
string closeTime(const string& s, MARKETNAME mn=USA);

//The function won't count non-market time
string RollBackTradeTime(const string& s, time_t offset = 0);
string RollBackTradeTimeFX(const string& s, time_t offset = 0);


enum CONTRACT_TYPE{MKDATA_STOCK,MKDATA_OPTION,MKDPETH_STOCK};
PSS GetExchangeCurrency(const string& symbol, CONTRACT_TYPE rt=MKDATA_STOCK);
time_t getFileWriteTime(const string& p);
uint64_t str2sec(const string& s);

bool isNonTradeDay(const string& str);

PDD mastd(const VECDL& v);

// -1.0...|(MarketOpen)======...======|(MarketClose)....-2.0
double percentTime(const char* curdt=nullptr);

VECST get5sBarEndstrVec(const string& now, uint64_t step=6);

double percentile(const double* head, const double* tail, double d);

#if defined(__linux__)
#include <sys/auxv.h>
#endif
string getCurExePath();

///Cross platform Sleep function
void msleep(uint64_t _ms);

int check_gshutdown(bool force=true);

bool has_suffix(const std::string &str, const std::string &suffix);
bool isHKSE(string s);
bool isSEHKNTL(string s);
string getsymbol(string s);

///@brief Using packaged_task to replace std::async because the future returned by async has a `blocking destructor`
///http://stackoverflow.com/questions/30698207/different-behavior-of-async-with-visual-studio-2013windows8-1-and-gcc-4-9ubun
///http://stackoverflow.com/questions/16296284/workaround-for-blocking-async
template< class Function, class... Args>
std::future<typename std::result_of<Function(Args...)>::type>
async2(Function&& f, Args&&... args){
    typedef typename std::result_of<Function(Args...)>::type R;
    auto bound_task = std::bind(std::forward<Function>(f), std::forward<Args>(args)...);
    std::packaged_task<R()> task(std::move(bound_task));
    std::thread(std::move(task)).detach();
    return task.get_future();
}

// 0 - today, 1 - yesterday(准备的说是last business day)
// 这里改为 1是有记录的last business day,查过去的50天
//////////////////////////////////////////////////////////////////////////
// 一个use case就是:
// 我交易了几天之后停了几天没有交易，这个程序应该可以正确的resume!
// e.g. C:\singapore\data\tradeinfo\DU198457\ATHM
// /singapore/data/tradeinfo/DU198457/ATHM/2015-06-24.xml
string getTIpath(const string& pid_,
    const string& date_,
    int i,
    string postfix=".json");

namespace testing{
  bool AddTimeTest();
  bool test_splitstr();
  bool test_splitv2();
}

#endif
