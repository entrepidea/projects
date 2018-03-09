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

#pragma once
#include <sentosaconfig.h>
#include <yaml-cpp/yaml.h>
#include <atomic>

using std::string;
using std::vector;
using std::atomic_int;

class sentosaYAML {
public:
  //fs:
  string DIRR; //: /singapore/research/R/
  string DIRPY; //: /singapore/research/Python/py/
  string DATAVOL; //: /singapore/research/data/volatility/
  string DATATA; //: /singapore/research/data/ta/
  string DIRDOCROOT; //: /singapore/research/sentosa.quant365.com/
  string ROOT; //: /singapore/
  string LOGDIR; //: /singapore/log/
  string LOGFILE; //: /singapore/log/sentosa-
  string CONFIGDIR; //: /singapore/config/
  string RESEARCHDIR; //: /singapore/research/
  string RESEARCHDIRDATA; //: /singapore/research/data/
  string DATAROOT; //: /singapore/data/
  string REPLAYFILEDIR; //: /singapore/data/
  string BIDASKDIR; //: /singapore/data/bidask/
  string PARAMDIR; //: /singapore/data/
  string TRADEINFODIR; //: /singapore/data/tradeinfo/
  string LOGFILESIM;//     : /singapore/log/sim-sentosa-

  //DB:
  string DBHOST; //:  127.0.0.1
  string DBUSER; //:  root
  string DBPASS; //:  sentosa # MySQL pass should be encrypted
  string DBNAME; //:  equities

  //global:
  string mode; //: trade // simulation, record
  string account; //: DU198457 //U8868823
  int debug; //: 0
  int backtest; //: 0
  string IBHOST;// 127.0.0.1
  uint64_t IBPORT;// 7496
  atomic_int IB_CLIENT_ID; //: 1
  string MKD_TO_ALGO_PORT; //: 15555
  string ALGO_TO_OMS_PORT; //: 16666
  string ALGO_ENGINE_PORT;
  string NN_LOG_PORT;
  string NN_MON_PORT;
  string WS_MON_PORT;

  uint64_t histDataReqNum; //: 7 #7 #15 #30
  uint64_t histDataSleepT; //:  75000 #75000 #150000 #300000 #million seconds
  uint64_t histDataBackMN=5;
  uint64_t recordbufsize=1024;

  //protocol
  string completeJ;//: 2.4495 # send complete json msg
  string updateJ;//  : 3.3166 # send updated json msg
  string closeall;// : 3.4641 # clear portfolio
  string closeone;// : 3.8730| # 3.8730|YY -> close YY's position in my portfolio
  string cancelall;//: 3.7417 # cancel all existing  waiting orders
  string lmtorder;// : l| #l|IBM,-100,123.5,1.8 -> sell IBM 100@123.5
  string mktorder;// : m| #m|IBM|500 -> buy IBM 500; m|IBM|-500 -> sell IBM 500
  // i|1234 -> what is the status of order with id=1234
  // i|1234|3 -> the status of order with id=1234 is 3
  string orderid;

  //define currencies
  //fx:
  VECST currencies;    //: EUR,USD,JPY,GBP,CAD,CHF

  //replay:
  string filetoreplay;    //: /singapore/data/replay-2015-01-02.henry.lst
  uint64_t speedup;    //: 1
  uint64_t sleepingtime;  //: 10 #around 10 times faster than the normal trading freq

  //indices:
  VECST market;    //: SPY
  //market : QQQQ
  //china  : QQQC
  VECST china;//  : FXI

  //strategies:
  VECST singleta;    //: [MOBL, CYOU, HMIN]
  //VECST strat_singleta;//: [MOBL, CYOU, HMIN]
  VECST pairs;

  //stocks:
  //p: DBB0,HMIN,HTHT,C:/singapore/config/hmin-htht.xml
  //s: singleta,QQQC
  //s: singleta,QQQQ
  //s: singleta,USD.JPY
  //s: singleta,EUR.USD
  //s: singleta,700

  VECST restricted;
  //s:BIDU,BITA,CEA,ZNH,FXI,DXJ,MOBL,SPY,EJ,LEJU,WB,SINA,SOHU,CYOU,CTRP,NTES,QIHU,QUNR,TAOM,WUBA,YY,RENN,CMCM,LONG,SFUN,FENG,DANG,JRJC,JOBS,PWRD,DATE,NQ,GOMO,CCIH,QQQC,HMIN,HTHT,ATHM

  string config_main;

  bool readconf();
  sentosaYAML();
};
