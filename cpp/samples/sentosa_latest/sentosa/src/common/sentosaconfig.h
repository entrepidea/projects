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

#ifndef __CONFIG__
#define __CONFIG__

#include <inttypes.h>
#include <cmath>

//http://google-styleguide.googlecode.com/svn/trunk/cppguide.xml

#define OPTION_SUFFIX "_O"

#define TIMEGAP_BETWEEN_TRADES 60*5

//const char* const HOST = "127.0.0.1";
//const unsigned int PORT = 7496;

#define CLIENTID_SENTOSA  0
#define CLIENTID_HISTDATA 6000
#define CLIENTID_DATASVR  10000

#define ALGOTHREADMAX 20
#define BARBASEINDEX 3000
#define OPTIONBASEINDEX 6000
#define RECV_TIMEOUT 500

enum class SYS_MODE:uint8_t{
  RECORD_MODE=0,TRADE_MODE,REPLAY_MODE,MERLION_MODE};

typedef double PRICE;
typedef uint64_t QUANT;

//block orders
#define BOParam map<PRICE, QUANT>

#define CMD_TERM_ALGO_THREAD "RELOAD_ALGO"

//TIME
#define SECONDS_IN_ONE_MIN  60
#define SECONDS_IN_ONE_HOU (SECONDS_IN_ONE_MIN*60)
#define SECONDS_IN_ONE_DAY (SECONDS_IN_ONE_HOU*24)
#define SECONDS_IN_ONE_TRADEDAY (SECONDS_IN_ONE_HOU*6.5)
#define SECONDS_IN_ONE_WEE (SECONDS_IN_ONE_DAY*7)
#define SECONDS_IN_ONE_MON (SECONDS_IN_ONE_DAY*31)
#define SECONDS_IN_ONE_YEA (SECONDS_IN_ONE_DAY*365)
#define INVALIDRANGE -1

#include <memory>
#include <vector>
#include <map>
#include <set>
#include <queue>
#include <string>
#include <list>
#include <algorithm>
#include <deque>

#include <fstream>
#include <memory>

#include <sstream>

#include <cereal/types/unordered_map.hpp>
#include <cereal/types/memory.hpp>
#include <cereal/archives/json.hpp>
#include <cereal/types/vector.hpp>
#include <cereal/types/string.hpp>
#include <cereal/types/map.hpp>

#define VECDL vector < double >
#define VECST vector < string >
#define VECIN vector < int >
#define VECUI vector < unsigned int >
#define CSTR  const string
#define PDD pair<double,double>
#define PLD pair<long, double>
#define PSS pair<string,string>

#define SENTOSAERROR {TTPrint("ERROR:[%s@%d][%s]\n", \
__FILE__, __LINE__, __FUNCTION__);}
#define SENTOSALOG {TTPrint("INFO:[%s@%d][%s]\n", \
__FILE__, __LINE__, __FUNCTION__);}
#define SENTOSALOG2 {printf("INFO:[%s@%d][%s]\n", \
__FILE__, __LINE__, __FUNCTION__);}
#define SENTOSAERROR2(x) {TTPrint("ERROR:[%s@%d][%s]%s\n", \
__FILE__, __LINE__, __FUNCTION__,(x));}

#define DT_IB_RES "%4d%02d%02d  %02d:%02d:%02d"
#define DT_IB_RES2 "%Y%m%d  %H:%M:%S"

#define DT_IB_REQ "%4d%02d%02d %02d:%02d:%02d"
#define DT_MYSQL  "%4d-%02d-%02d %02d:%02d:%02d"

#define DT_IB_REQ2 "%Y%m%d %H:%M:%S"
#define DT_MYSQL2 "%Y-%m-%d %H:%M:%S"

#define DT_FILENAME "%Y-%m-%d_%H-%M-%S"

#define STEPFUNC(x,y) int(y*ceil(x/(y*1.0)))

// For pair trading, we use master and slave; For single stock trading, we use SINGLE.
enum CWTYPE{ MASTER = 1, SLAVE, SINGLE };

#include <boost/filesystem.hpp>
namespace fs = boost::filesystem;
#define FILESEP fs::path::preferred_separator
#define PROCESSNAME "SENTOSA"

#include <versiondef.h>

#endif
