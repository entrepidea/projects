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

#ifndef __SENTOSA_MQ__
#define __SENTOSA_MQ__

#include <mutex>
#include <sentosaconfig.h>

using std::mutex;
using std::string;

// Why need this singleton?
// There are probably many tradeobject which will send order to OMS.
class algoMQ{
  static bool initialized;
  static algoMQ* pinstance;
  static mutex lock_;

  algoMQ();
  ~algoMQ();

  int sock = -1;
  int eid  = 0;
  string endpoint;

  void disconnect();

public:

  static algoMQ& R();

  //string getmq(int threadid);
  void sendmq(const string& str);

  static void close();
};

//#define MQPAIR(x) algoMQ::R().x

#endif
