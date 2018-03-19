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

#include "gconfig.h"

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

CConfig* CConfig::pinstance = nullptr;
mutex CConfig::conflock_;

CConfig& CConfig::R(){
  if (pinstance == nullptr){
    lock_guard<mutex> g(conflock_);
    if (pinstance == nullptr){
      pinstance = new CConfig();
      pinstance->readconf();
    }
  }
  return *pinstance;
}

void CConfig::readconf(){
  // http://stackoverflow.com/questions/357307
  sentosaYAML::readconf();

  if (mode == "simulation") {
    _mode = SYS_MODE::REPLAY_MODE;
  } else if (mode == "trade") {
    _mode = SYS_MODE::TRADE_MODE;
  } else if (mode == "record") {
    _mode = SYS_MODE::RECORD_MODE;
  } else if (mode == "merlion") {
    _mode = SYS_MODE::MERLION_MODE;
  }

  set<string>* p;
  for (auto& s : pairs) {
    //s is like DBB0|HMIN|HTHT|/singapore/config/hmin-htht.xml
    VECST vs= splitv2(s, '|');
    for (int i=1;i<=2; ++i) {
      p=isFX(vs[i]) ? &fxs : &stocks;
      p->insert(vs[i]);
    }
  }
  for (auto& s : singleta) {
    p=isFX(s) ? &fxs : &stocks;
    p->insert(s);
  }
  isdebug        = (debug==1);
  isbacktest     = (backtest==1);
  isspeedyreplay = (speedup==1);
  ispaper        = (account[0]=='D' && account[1]=='U');

  for (string& s : market){
    indices.insert(s);
  }
  for (string& s : china){
    indices.insert(s);
  }
  instru.insert(stocks.cbegin(), stocks.cend());
  instru.insert(fxs.cbegin(), fxs.cend());
  instru.insert(indices.cbegin(), indices.cend());

  printfg("Trading Universe: ");
  for(auto const& kv:instru){printfb("%s ",kv.c_str());}
  printf("\n");

  //loadTALen();
}

bool CConfig::isFX(const string& s){
  return find(currencies.cbegin(),currencies.cend(),s.substr(0,3))
      != currencies.cend();
}

/*void CConfig::loadTALen(){
  try{
    VECST ss;

    bpo::options_description desc("Options");
    desc.add_options()
      ("WindowSize.s", bpo::value<VECST>(&ss)->multitoken(), "stocks pairs")
      ;
    bpo::variables_map vm;
    ifstream settings_file(taLen_file, std::ifstream::in);
    bpo::store(bpo::parse_config_file(settings_file, desc), vm);
    settings_file.close();
    bpo::notify(vm);
    for (auto& s : ss){
      VECST vs = splitv2(s, ',');
      sym2taLen[vs[0]] = atof(vs[1].c_str());
    }
  }
  catch (exception& e){
    printfr("%s\n", e.what());
    SENTOSAERROR;
  }
  catch (...){
    SENTOSAERROR;
  }
}*/
