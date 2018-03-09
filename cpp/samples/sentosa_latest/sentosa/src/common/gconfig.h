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

#ifndef COMMON_GCONFIG_H
#define COMMON_GCONFIG_H

#include <mutex>
#include <sentosaconfig.h>
#include "conf_yaml.h"

using std::vector;
using std::string;
using std::set;
using std::mutex;
using std::map;

class tobj;

// us.ini file
class CConfig : public sentosaYAML{
  static bool initialized;
  static CConfig* pinstance;
  static mutex conflock_;
  //CConfig(){}
public:

  SYS_MODE _mode = SYS_MODE::RECORD_MODE;
  bool isdebug = false;
  bool isbacktest = false;
  bool isspeedyreplay = false;
  bool ispaper = false;

  // TA window size(taLen)
  map<string, uint32_t> sym2taLen;

  bool isFX(const string& s);

  //void loadTALen();

  void readconf();

  static CConfig& R();

  set<string> stocks;
  set<string> indices;
  set<string> fxs;
  set<string> instru;//include the three above
};

#define CR(x) CConfig::R().x

#endif
