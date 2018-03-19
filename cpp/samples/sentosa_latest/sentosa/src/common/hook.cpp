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

#include "hook.h"
#include <util.h>

std::atomic<bool> g_shutdown{ false };

#define PRINTSHUTDOWNMSG printfg("\n* Thank you for using sentosa. Goodbye! *\n(Sentosa is brought you by http://www.quant365.com) \n");

#if defined(_WIN64) || defined(_WIN32)

#include <windows.h>
#include <stdio.h>
BOOL CtrlHandler(DWORD fdwCtrlType){
  switch (fdwCtrlType){
  case CTRL_C_EVENT:
    Beep(250, 500);
    g_shutdown = true;
    PRINTSHUTDOWNMSG;
    return(TRUE);
  case CTRL_CLOSE_EVENT:
    PRINTSHUTDOWNMSG;
    g_shutdown = true;
    return(TRUE);
  case CTRL_BREAK_EVENT:
    PRINTSHUTDOWNMSG;
    g_shutdown = true;
    return FALSE;
  case CTRL_LOGOFF_EVENT:
    PRINTSHUTDOWNMSG;
    g_shutdown = true;
    return FALSE;
  case CTRL_SHUTDOWN_EVENT:
    PRINTSHUTDOWNMSG;
    g_shutdown = true;
    return FALSE;
  default:
    return FALSE;
  }
}

std::atomic<bool>* setcontrolhandler(void){
  bool b=SetConsoleCtrlHandler((PHANDLER_ROUTINE)CtrlHandler, TRUE);
  if (!b){
    printf("\nERROR: Could not set control handler");
  }
  return &g_shutdown;
}

#elif defined(__linux__)
#include <signal.h>
void CtrlHandler(int sig){
  g_shutdown = true;
  PRINTSHUTDOWNMSG;
}

std::atomic<bool>* setcontrolhandler(void){
  signal(SIGINT, CtrlHandler);//2
  signal(SIGPWR, CtrlHandler);//30
  return &g_shutdown;
}

#endif
