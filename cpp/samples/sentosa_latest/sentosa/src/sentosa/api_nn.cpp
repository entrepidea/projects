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

#include "api_core.h"
#include "api_nn.h"
#include "msgq.h"
#include <atomic>
#include <assert.h>
#include <nanomsg/reqrep.h>
#include <gconfig.h>
#include <o2j.h>

extern std::atomic<bool> g_shutdown;

#define SFROM(msgin,N) msgin.substr(0,msgin.size()-N)

nnhandler::nnhandler() {
  sock = nn_socket(AF_SP, NN_REP);
  endpoint = "tcp://*:" + CR(NN_MON_PORT);
  eid = nn_bind(sock, endpoint.c_str());
  int to = 100;
  nn_setsockopt(sock, NN_SOL_SOCKET, NN_RCVTIMEO, &to, sizeof(to));
}

nnhandler::~nnhandler() {
  nn_shutdown(sock, eid); // http://nanomsg.org/v0.2/nn_shutdown.3.html
  nn_close(sock);
  SENTOSALOG;
}

void nnhandler::run() {
  while (!g_shutdown) {
    char *buf = nullptr;
    int bytes = nn_recv(sock, &buf, NN_MSG, 0);
    if (bytes > 0 && buf != nullptr) {
      string msgin(buf, bytes);
      onData(sock, msgin);
      buf != nullptr && nn_freemsg(buf);
    }
    msleep(10);
  }
}

void nnhandler::onData(int sock, const string& msgin) {
  printf("msg:%s\n", msgin.c_str());

  if (startwith(msgin, CR(completeJ))) {
    string msg = fullpage();
    nn_send(sock, msg.c_str(), msg.size(), 0);
  } else if (startwith(msgin, CR(updateJ))) {
    string sym = SFROM(msgin, 1);
    string msg = tobjpage(sym);
    nn_send(sock, msg.c_str(), msg.size(), 0);
  } else if (startwith(msgin, CR(lmtorder))) {
    on_ordermsg(sock, msgin);
  } else if (startwith(msgin, CR(mktorder))) {
    on_ordermsg(sock, msgin);
  } else if (msgin == CR(closeall)) {
    algoMQ::R().sendmq(CR(closeall));
    /*printfr("%s\n", "Got closeall, sent closeall");
     msg = "CLOSEALL SENT";
     nn_send(sock, msg.c_str(), msg.size(), 0);*/
  } else if (startwith(msgin, CR(orderid))) {
    on_oid(sock, msgin);
  }
}

/*
 * API for nanomsg protocol
 * */
void Thread_API_NN() {
  nnhandler nh;
  nh.run();
  SENTOSALOG;
}
