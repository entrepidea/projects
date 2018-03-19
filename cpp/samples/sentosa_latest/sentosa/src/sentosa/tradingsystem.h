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

#ifndef SENTOSA_TRADINSGSYSTEM_H
#define SENTOSA_TRADINSGSYSTEM_H

#include <thread>
#include <memory>
#include <sentosaconfig.h>
#include "iborder.h"
#include "mkdata.h"
#include "daemon.h"
#include "api_ws.h"

class tradingsystem {
  SYS_MODE mode = SYS_MODE::REPLAY_MODE;
  mkdata *pbar = nullptr;
  shared_ptr<mkdata> ptik;
  shared_ptr<iborder> poms;

  vector<thread*> threads;

  //void startALGO();
  void startMon();
  void startReport();
  void startOMS(shared_ptr<iborder>);

  seasocks::Server* pws = nullptr; //pointer to websocket server
public:

  int run();
  bool live() const;

  tradingsystem();
  ~tradingsystem();

  // https://mail.python.org/pipermail/cplusplus-sig/2004-July/007472.html
  // http://stackoverflow.com/questions/10142417/boostpython-compilation-fails-because-copy-constructor-is-private
  // For Boost::Python - The copy and assignment constructors would be wrapped automatically
  // Cannot build for boost_python if disable copy constructor
  tradingsystem(tradingsystem&&) = delete;
  tradingsystem(const tradingsystem&) = delete;
  tradingsystem& operator=(tradingsystem&&) = delete;
  tradingsystem& operator=(const tradingsystem&) = delete;
};

/// Trading system daemon
class tsdaemon: public tradingsystem, public daemon {
  tsdaemon(tsdaemon&&) = delete;
  tsdaemon(const tsdaemon&) = delete;
  tsdaemon& operator=(tsdaemon&&) = delete;
  tsdaemon& operator=(const tsdaemon&) = delete;
public:
  tsdaemon() = default;
  void loop() override {
    run();
  }
};

#endif // !__TRADINSGSYSTEM__
