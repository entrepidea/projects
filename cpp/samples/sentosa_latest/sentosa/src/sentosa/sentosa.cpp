// Sentosa - An Automatic Algorithmic Trading System
// Copyright (C) 2015
// Henry Fuheng Wu (wufuheng AT gmail.com)
// Homepage:  http://www.quant365.com
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

#include "tradingsystem.h"
#include <versiondef.h>
#include <gflags/gflags.h>

void CheckIB();

DEFINE_bool(daemon, false, "run daemon mode");

int main(int argc, char** argv) {
  google::SetVersionString(quant365_filever_str);
  google::SetUsageMessage("Usage : ./sentosa [-daemon]");
  google::ParseCommandLineFlags(&argc, &argv, true);

  SetProcName(argv, argc, PROCESSNAME);

  print_version();
  print_copyright();
  if (FLAGS_daemon) {
    CheckIB();
    tsdaemon tsd;
    tsd.daemonize();
  } else {
    CheckIB();
    tradingsystem ts;
    TTPrinty("Type Ctrl-C to exit\n\n");
    ts.run();
  }

  return 0;
}

void CheckIB() {
  TTPrintNoTime("--Program started@%s--\n", ymdhms().c_str());
  if (!isIBRunning()) {
    TTPrinty("Warning: IB is not running.\n");
  }
}
