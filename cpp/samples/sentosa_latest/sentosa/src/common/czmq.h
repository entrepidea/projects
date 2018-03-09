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

#ifndef __CZMQ__
#define __CZMQ__
#include <zmq.hpp>

#include <stdarg.h>
#include <mutex>
#include <stdio.h>
#include <time.h>
#include <string>
#include "EWrapper.h"
#include <vector>

using namespace zmq;
using namespace std;

//////////////////////////////////////////////////////////////////////////
class mkdzmq{
  static bool initialized;
  static mkdzmq* pinstance;
  static mutex mkdzmqlock_;

  mkdzmq();

  //context_t *pcontext_out;
  socket_t *psocket_out;

  //context_t *pcontext_in;
  socket_t *psocket_in;

  vector<string> endpoints;
  void disconnect();

public:

  static mkdzmq& R();

  void sendmq(const string& str);
  void sendmq(const string& stock, const string& msg);

  void sendstr(const string& symbol, TickType field, const char* value);
  void sendint(const string& symbol, TickType field, int    value);
  void sendflo(const string& symbol, TickType field, double value);

  static void close();
};

//////////////////////////////////////////////////////////////////////////
class algozmq{
  static bool initialized;
  static algozmq* pinstance;
  static mutex algozmqlock_;

  algozmq();
  
  //context_t *pcontext_out;
  socket_t *psocket_out;

  //context_t *pcontext_in;
  //socket_t socket_ins[ALGOTHREADMAX];
  vector<string> endpoints;
  void disconnect();

public:

  static algozmq& R();

  //string getmq(int threadid);
  void sendmq(const string& str);

  static void close();
};

//////////////////////////////////////////////////////////////////////////
class omszmq{
  static bool initialized;
  static omszmq* pinstance;
  static mutex omszmqlock_;

  omszmq();

  //context_t *pcontext_out;
  //socket_t *psocket_out;

  //context_t *pcontext_in;
  socket_t *psocket_in;
  vector<string> endpoints;
  void disconnect();

public:
  static omszmq& R();

  string getmq();
  static void close();
};

//////////////////////////////////////////////////////////////////////////
class algoBackEnd{
  static bool initialized;
  static algoBackEnd* pinstance;
  static mutex lock_;

  algoBackEnd();
  socket_t *psocket_out;
  socket_t *psocket_in;

  vector<string> endpoints;
  void disconnect();

public:

  static algoBackEnd& R();

  string request(const string& str);
  static void close();
};

void close_zmq();

#endif
