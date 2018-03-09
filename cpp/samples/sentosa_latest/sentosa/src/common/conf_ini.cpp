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

#include "conf_ini.h"

#include <boost/program_options.hpp>
#include <boost/property_tree/ptree.hpp>
#include <boost/property_tree/ini_parser.hpp>
#include <util.h>
#include <cband.h>
#include <boost/filesystem.hpp>
#include <fstream>

//////////////////////////////////////////////////////////////////////////
// Note: This file cannot use uulog function!
//////////////////////////////////////////////////////////////////////////

namespace bpt = boost::property_tree;
namespace bpo = boost::program_options;
namespace fs = boost::filesystem;

bool sentosaINI::readconf(){
  try{
    string _mode;
    VECST __pairs;
    VECST __singl;
    string __cur;
    string __rest;

    bpo::options_description desc("Options");
    desc.add_options()
      ("stocks.p", bpo::value<VECST>(&__pairs)->multitoken(), "stocks pairs")
      ("stocks.s", bpo::value<VECST>(&__singl)->multitoken(), "single stocks")
      ("indices.market", bpo::value<VECST>(&indices_market)->multitoken(), "market index[general]")
      ("indices.china", bpo::value<VECST>(&indices_china)->multitoken(), "market index[china]")
      ("global.mode", bpo::value<string>(&_mode), "system mode")
      ("global.account", bpo::value<string>(&account), "IB account number")
      ("global.MKDATA_CLIENT_ID", bpo::value<uint64_t>(&MKDATA_CLIENT_ID), "client id to start with")
      ("global.MKD_TO_ALGO_PORT", bpo::value<string>(&MKD_TO_ALGO_PORT), "zmq port from mkdata to algo")
      ("global.ALGO_TO_OMS_PORT", bpo::value<string>(&ALGO_TO_OMS_PORT), "zmq port from algo to oms")
      ("global.debug", bpo::value<bool>(&isdebug), "Am I debugging?")
      ("global.backtest", bpo::value<bool>(&isbacktest), "Am I back testing?")
      ("global.histDataReqNum", bpo::value<uint16_t>(&histDataReqNum), "")
      ("global.histDataSleepT", bpo::value<uint64_t>(&histDataSleepT), "")
      ("replay.filetoreplay", bpo::value<string>(&filetoreplay), "file to be used to replay market data")
      ("replay.speedup", bpo::value<bool>(&isspeedyreplay), "Is it a speedy replay?")
      ("replay.sleepingtime", bpo::value<uint64_t>(&sleepingtime), "sleep per loop")
      ("DB.DBHOST", bpo::value<string>(&DBHOST), "")
      ("DB.DBUSER", bpo::value<string>(&DBUSER), "")
      ("DB.DBPASS", bpo::value<string>(&DBPASS), "")
      ("DB.DBNAME", bpo::value<string>(&DBNAME), "")
      ("fs.ROOT", bpo::value<string>(&ROOT), "")
      ("fs.LOGDIR", bpo::value<string>(&LOGDIR), "")
      ("fs.LOGFILE", bpo::value<string>(&LOGFILE), "")
      ("fs.CONFIGDIR", bpo::value<string>(&CONFIGDIR), "")
      ("fs.RESEARCHDIR", bpo::value<string>(&RESEARCHDIR), "")
      ("fs.RESEARCHDIRDATA", bpo::value<string>(&RHDATA), "")
      ("fs.DATAROOT", bpo::value<string>(&DATAROOT), "")
      ("fs.REPLAYFILEDIR", bpo::value<string>(&REPLAYFILEDIR), "")
      ("fs.BIDASKDIR", bpo::value<string>(&BIDASKDIR), "")
      ("fs.PARAMDIR", bpo::value<string>(&PARAMDIR), "")
      ("fs.TRADEINFODIR", bpo::value<string>(&TRADEINFODIR), "")
      ("fx.currencies", bpo::value<string>(&__cur), "currency list")
      ("restricted.s", bpo::value<string>(&__rest), "restriction list")
      ;


    bpo::variables_map vm;
    ifstream settings_file(config_main, std::ifstream::in);
    bpo::store(bpo::parse_config_file(settings_file, desc), vm);
    settings_file.close();
    bpo::notify(vm);

    restrictedstocks = splitv2(__rest, ',');

    if (_mode == "simulation"){
      mode = SYS_MODE::REPLAY_MODE;
    }
    else if (_mode == "trade"){
      mode = SYS_MODE::TRADE_MODE;
    }
    else if (_mode == "record"){
      mode = SYS_MODE::RECORD_MODE;
    }
    /*for (auto& s : __pairs){
      tobj* pcb = new CBand;
      pcb->set(splitv2(s, ','));
      gbands.push_back(pcb);
    }
    for (auto& s : __singl){
      tobj* pcb = new CSing;
      pcb->set(splitv2(s, ','));
      gbands.push_back(pcb);
    }

    VECST tmp = splitv2(__cur, ',');
    for (string& s: tmp){
      currencies.insert(s);
    }*/
  }
  catch (exception& e){
    printfr("%s\n", e.what());
    SENTOSAERROR;
    return false;
  }
  catch (...){
    SENTOSAERROR;
    return false;
  }

  return true;
}
