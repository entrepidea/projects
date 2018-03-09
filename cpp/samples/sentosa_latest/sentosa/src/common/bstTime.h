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

#ifndef __SENTOSA_BOOST_TIME__
#define __SENTOSA_BOOST_TIME__

#include <sentosaconfig.h>
#include <boost/date_time/local_time/local_time_types.hpp>
#include <boost/date_time/local_time/local_time_io.hpp>
#include <boost/date_time/posix_time/ptime.hpp>
#include <mutex>
#include "httpclient.h"

using boost::posix_time::ptime;
using std::string;
using std::mutex;
using std::set;
using std::locale;

//<<holiday.yml>>
class gholiday{
  static bool initialized;
  static gholiday* pinstance;
  static mutex ghlock_;
  gholiday(){
    config_holi = get_yaml("holiday.yml");
  }

  string config_holi;
public:
  set<string> ho;
  set<string> ph;
  void loadholiday();

  bool isHO(string s);
  bool isPH(string s);

  static gholiday& R();
};

class glocale{
public:
  locale* _s_loc;
  boost::local_time::time_zone_ptr _ny_tzone;
  locale __locale;
  boost::local_time::local_time_facet* F_DT_MYSQL2;

  static glocale& R();
  ~glocale();
private:
  glocale();

  static bool initialized;
  static glocale* pinstance;
  static mutex __lock;
};


string ptime2str(const ptime& pt);

string NYCurTime();

time_t str2time_t(const string& s);

string time_t2str(time_t tt);

time_t to_time_t(ptime t);

bool __isNYSEHoliday(const string& str);

string rollbackTime(const string& s, int i);

string rollbackTD(const string& s, uint64_t i);

// previous trading date
// string format: 2014-06-11 ...
string prevTradeDT(const string& str);
void Test_prevTDate();


string normalizeTradeDT(const string& str1);
//////////////////////////////////////////////////////////////////////////
string nowMS();
//market open time at 9:30
#define MOPENTM(x) (x+" 09:30:00")

#endif // !__SENTOSA_TIME__

