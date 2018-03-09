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

#include "mq.h"
#include <gconfig.h>
#include <log.h>
#include <nanomsg/nn.h>
#include <nanomsg/pair.h>

using std::lock_guard;

algoMQ* algoMQ::pinstance = nullptr;
mutex algoMQ::lock_;

algoMQ::algoMQ(){
  endpoint = "tcp://*:" + CConfig::R().ALGO_TO_OMS_PORT;
  sock = nn_socket (AF_SP, NN_PAIR);
  assert(sock >= 0);
  eid = nn_bind(sock, endpoint.c_str());
  //int to = 100;
  //assert (nn_setsockopt (sock, NN_SOL_SOCKET, NN_RCVTIMEO, &to, sizeof (to)) >= 0);
}

algoMQ::~algoMQ(){
  disconnect();
}

void algoMQ::disconnect(){
  SENTOSALOG;
  if (sock>=0){
    SENTOSALOG;
    nn_shutdown(sock, eid);
    nn_close(sock);
  }
}

algoMQ& algoMQ::R(){
  if (pinstance == nullptr){
    lock_guard<mutex> g(lock_);
    if (pinstance == nullptr){
      pinstance = new algoMQ();
    }
  }
  return *pinstance;
}

void algoMQ::sendmq(const string& msg){
  int bytes = nn_send(sock, msg.c_str(), msg.size()+1, 0);
  if (bytes != msg.size()+1){
    TTPrint("[%s(%d)]ZMQ Error!\n", __FUNCTION__, __LINE__);
  }
}
