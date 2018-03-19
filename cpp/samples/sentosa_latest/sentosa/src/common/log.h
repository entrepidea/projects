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

#ifndef COMMON_LOG_H
#define COMMON_LOG_H

#include <stdarg.h>
#include <stdio.h>
#include <time.h>
#include <mutex>
#include <sentosaconfig.h>

using std::string;
using std::mutex;

class uulogging{
  static bool initialized;
  static uulogging* pinstance;
  static mutex loglock_;

  FILE* logfiledescr = nullptr;
  uulogging();
  ~uulogging();
  int socket = -1;
  uint64_t bytes = -1;

public:
  /*** Get the singleton instance*/
  static uulogging& R();

  /*** Initialize the object*/
  void Initialize();

  void Printf2File(const char *format, ...);
  void Printf2FileNoTime(const char *format, ...);
};

//http://stackoverflow.com/questions/5248599/too-many-actual-parameters-for-macro
#ifdef __linux__
#define ANSI_COLOR_RED    "\x1b[31m"
#define ANSI_COLOR_GRE    "\x1b[32m"
#define ANSI_COLOR_YEL    "\x1b[33m"
#define ANSI_COLOR_BLU    "\x1b[34m"
#define ANSI_COLOR_MAG    "\x1b[35m"
#define ANSI_COLOR_CYA    "\x1b[36m"
#define ANSI_COLOR_RESET  "\x1b[0m"
#define printfr(...) do{printf(ANSI_COLOR_RED);printf(__VA_ARGS__);printf(ANSI_COLOR_RESET);}while(0)
#define printfg(...) do{printf(ANSI_COLOR_GRE);printf(__VA_ARGS__);printf(ANSI_COLOR_RESET);}while(0)
#define printfy(...) do{printf(ANSI_COLOR_YEL);printf(__VA_ARGS__);printf(ANSI_COLOR_RESET);}while(0)
#define printfb(...) do{printf(ANSI_COLOR_BLU);printf(__VA_ARGS__);printf(ANSI_COLOR_RESET);}while(0)
#define printfm(...) do{printf(ANSI_COLOR_MAG);printf(__VA_ARGS__);printf(ANSI_COLOR_RESET);}while(0)
#define printfc(...) do{printf(ANSI_COLOR_CYA);printf(__VA_ARGS__);printf(ANSI_COLOR_RESET);}while(0)
#else
#define printfr(...) do{\
HANDLE hConsole = GetStdHandle(STD_OUTPUT_HANDLE);\
SetConsoleTextAttribute(hConsole, FOREGROUND_RED | FOREGROUND_INTENSITY | BACKGROUND_GREEN);\
printf("%s ",ymdhms().c_str());printf(__VA_ARGS__);\
SetConsoleTextAttribute(hConsole, FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);\
}while (0)

#define printfr2(...) do{\
HANDLE hConsole = GetStdHandle(STD_OUTPUT_HANDLE);\
SetConsoleTextAttribute(hConsole, FOREGROUND_RED | FOREGROUND_INTENSITY | BACKGROUND_BLUE);\
printf("%s ",ymdhms().c_str()); printf(__VA_ARGS__);\
SetConsoleTextAttribute(hConsole, FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);\
}while (0)
#endif

#define TTPrint uulogging::R().Printf2File

#define TTPrintr(...) do{uulogging::R().Printf2File(__VA_ARGS__);printfr(__VA_ARGS__);}while(0)
#define TTPrintg(...) do{uulogging::R().Printf2File(__VA_ARGS__);printfg(__VA_ARGS__);}while(0)
#define TTPrinty(...) do{uulogging::R().Printf2File(__VA_ARGS__);printfy(__VA_ARGS__);}while(0)
#define TTPrintb(...) do{uulogging::R().Printf2File(__VA_ARGS__);printfb(__VA_ARGS__);}while(0)
#define TTPrintm(...) do{uulogging::R().Printf2File(__VA_ARGS__);printfm(__VA_ARGS__);}while(0)
#define TTPrintc(...) do{uulogging::R().Printf2File(__VA_ARGS__);printfc(__VA_ARGS__);}while(0)


#define TTPrintNoTime uulogging::R().Printf2FileNoTime
#define TTPrintNoTime2(...) {uulogging::R().Printf2FileNoTime(__VA_ARGS__);printf(__VA_ARGS__);}

class Rlog{
  FILE* __fdesc;
public:

  Rlog();
  ~Rlog();

  bool headerAdded = false;

  void closefile();
  void setfname(const string& fname);
  bool addheader(const string& h);
  void write(const char *format, ...);
};

#endif
