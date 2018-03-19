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

#include <sentosaconfig.h>

// us.ini file

class sentosaINI{
public:
  //[FX]
  set<string> currencies;


  SYS_MODE mode = SYS_MODE::RECORD_MODE;

  uint64_t MKDATA_CLIENT_ID = 0;
  string MKD_TO_ALGO_PORT = "15555";
  string ALGO_TO_OMS_PORT = "16666";

  string account = "";
  string filetoreplay = "";

  // bands info must be a global
  vector<tobj*> gbands;

  VECST indices_market;
  VECST indices_china;
  vector<string> restrictedstocks;

#if __linux__
  string config_main = "/singapore/config/us_linux.ini";
  string taLen_file = "/singapore/config/ta.ini";
#else
  string config_main = "C:\\singapore\\config\\us.ini";
  string taLen_file = "C:\\singapore\\config\\ta.ini";
#endif

  bool isdebug = false;
  bool isbacktest = false;

  bool isspeedyreplay = false;
  uint64_t sleepingtime = 100;

  uint16_t histDataReqNum = 10;
  uint64_t histDataSleepT = 5 * 60 * 1000;

  //[DB]
  string  DBHOST = "192.168.254.1";
  string  DBUSER = "root";
  string  DBPASS = "";
  string  DBNAME = "equities";

#if __linux__
  //[fs]
  string  ROOT = "/singapore/";
  string  LOGDIR = "/singapore/log/";
  string  LOGFILE = "/singapore/log/sentosa-";
  string  LOGFILESIM = "/singapore/log/sim-sentosa-";
  string  CONFIGDIR = "/singapore/config/";
  string  RESEARCHDIR = "/singapore/research/";
  //research data
  string  RHDATA = "/singapore/research/data/";
  string  SIMRLOG = "/singapore/research/data/sim-";
  string  DATAROOT = "/singapore/data/";
  string  REPLAYFILEDIR = "/singapore/data/";
  string  BIDASKDIR = "/singapore/data/bidask/";
  string  PARAMDIR = "/singapore/data/";
  string  TRADEINFODIR = "/singapore/data/tradeinfo/";
#else
  //[fs]
  string  ROOT = "C:\\singapore\\";
  string  LOGDIR = "C:\\singapore\\log\\";
  string  LOGFILE = "C:\\singapore\\log\\sentosa-";
  string  LOGFILESIM = "C:\\singapore\\log\\sim-sentosa-";
  string  CONFIGDIR = "C:\\singapore\\config\\";
  string  RESEARCHDIR = "C:\\singapore\\research\\";
  //research data
  string  RHDATA = "C:\\singapore\\research\\data\\";
  string  SIMRLOG = "C:\\singapore\\research\\data\\sim-";
  string  DATAROOT = "C:\\singapore\\data\\";
  string  REPLAYFILEDIR = "C:\\singapore\\data\\";
  string  BIDASKDIR = "C:\\singapore\\data\\bidask\\";
  string  PARAMDIR = "C:\\singapore\\data\\";
  string  TRADEINFODIR = "C:\\singapore\\data\\tradeinfo\\";
#endif


  bool readconf();

};
