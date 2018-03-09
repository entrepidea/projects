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

#include "conf_yaml.h"
#include "util.h"
#include "httpclient.h" //get_yaml

using namespace YAML;

bool sentosaYAML::readconf() {
  YAML::Node config = YAML::LoadFile(config_main);
  for (auto i : config) {
    string k = i.first.as<std::string>();
    YAML::Node& n = i.second;
    if (k == "linux") {
      this->DIRR      = expand_user(n["DIRR"].as<string>());
      this->DIRPY     = expand_user(n["DIRPY"].as<string>());
      this->DATAVOL   = expand_user(n["DATAVOL"].as<string>());
      this->DATATA    = expand_user(n["DATATA"].as<string>());
      this->DIRDOCROOT= expand_user(n["DIRDOCROOT"].as<string>());
      this->ROOT      = expand_user(n["ROOT"].as<string>());
      this->LOGDIR    = expand_user(n["LOGDIR"].as<string>());
      this->LOGFILE   = expand_user(n["LOGFILE"].as<string>());
      this->CONFIGDIR = expand_user(n["CONFIGDIR"].as<string>());
      this->RESEARCHDIR     = expand_user(n["RESEARCHDIR"].as<string>());
      this->RESEARCHDIRDATA = expand_user(n["RESEARCHDIRDATA"].as<string>());
      this->DATAROOT        = expand_user(n["DATAROOT"].as<string>());
      this->REPLAYFILEDIR   = expand_user(n["REPLAYFILEDIR"].as<string>());
      this->BIDASKDIR       = expand_user(n["BIDASKDIR"].as<string>());
      this->PARAMDIR     = expand_user(n["PARAMDIR"].as<string>());
      this->TRADEINFODIR = expand_user(n["TRADEINFODIR"].as<string>());
      this->LOGFILESIM   = expand_user(n["LOGFILESIM"].as<string>());
    } else if (k == "DB") {
      this->DBHOST = n["DBHOST"].as<string>();
      this->DBUSER = n["DBUSER"].as<string>();
      this->DBPASS = n["DBPASS"].as<string>();
      this->DBNAME = n["DBNAME"].as<string>();
    } else if (k == "global") {
      this->mode = n["mode"].as<string>();
      this->debug = n["debug"].as<int>();
      this->backtest = n["backtest"].as<int>();
      this->account = n["account"].as<string>();
      this->IBHOST = n["IBHOST"].as<string>();
      this->IBPORT = n["IBPORT"].as<uint64_t>();
      this->IB_CLIENT_ID = n["IB_CLIENT_ID"].as<int>();
      this->MKD_TO_ALGO_PORT = n["MKD_TO_ALGO_PORT"].as<string>();
      this->ALGO_TO_OMS_PORT = n["ALGO_TO_OMS_PORT"].as<string>();
      this->ALGO_ENGINE_PORT = n["ALGO_ENGINE_PORT"].as<string>();
      this->NN_LOG_PORT      = n["NN_LOG_PORT"].as<string>();
      this->NN_MON_PORT      = n["NN_MON_PORT"].as<string>();
      this->WS_MON_PORT      = n["WS_MON_PORT"].as<string>();
      this->histDataReqNum = n["histDataReqNum"].as<int>();
      this->histDataSleepT = n["histDataSleepT"].as<int>();
      this->histDataBackMN = n["histDataBackMN"].as<uint64_t>();
      this->recordbufsize  = n["recordbufsize"].as<uint64_t>();
    } else if (k == "protocol") {
      this->completeJ = n["completeJ"].as<string>();
      this->updateJ = n["updateJ"].as<string>();
      this->closeall = n["closeall"].as<string>();
      this->closeone = n["closeone"].as<string>();
      this->cancelall = n["cancelall"].as<string>();
      this->lmtorder = n["lmtorder"].as<string>();
      this->mktorder = n["mktorder"].as<string>();
      this->orderid  = n["orderid"].as<string>();
    } else if (k == "fx") {
      this->currencies = n["currencies"].as<VECST>();
    } else if (k == "replay") {
      this->filetoreplay = expand_user(n["filetoreplay"].as<string>());
      this->speedup = n["speedup"].as<int>();
      this->sleepingtime = n["sleepingtime"].as<int>();
    } else if (k == "indices") {
      this->market = n["market"].as<VECST>();
      this->china  = n["china"].as<VECST>();
    } else if (k == "strategies") {
      //http://stackoverflow.com/questions/21985172/optional-keys-with-yaml-cpp-0-5-1
      if (n["singleta"]){
        this->singleta = n["singleta"].as<VECST>();
      }
      if (n["pairs"]){
        this->pairs = n["pairs"].as<VECST>();
      }
    } else if (k == "restricted") {
      this->restricted = n.as<VECST>();
    }
  }
  return 0;
}

sentosaYAML::sentosaYAML(){
  config_main = get_yaml("sentosa.yml");
}
