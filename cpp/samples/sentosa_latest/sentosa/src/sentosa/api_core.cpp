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
#include "oid.h"
#include <nanomsg/nn.h>
#include <scoreboard.h>

using namespace seasocks;

void wssend(WebSocket* p, const string& s) {
  if (p == nullptr || s.empty())
    return;
  p->send(s.c_str());
}

void nnsend(int sock, const string& s) {
  if (sock < 0 || s.empty())
    return;
  nn_send(sock, s.c_str(), s.size(), 0);
}

string __on_ordermsg(const string& msgin) {
  lock_guard<mutex> g(oid_mtx);
  assert(long(m_orderId) > 0);
  string oid = to_string(long(m_orderId));
  ++m_orderId;

  algoMQ::R().sendmq(msgin + "|" + oid);
  return CR(orderid)+ "|" +oid;
}

void on_ordermsg(WebSocket* c, const string& msgin) {
  string msgout = __on_ordermsg(msgin);
  wssend(c, msgout);
}

void on_ordermsg(int sock, const string& msgin) {
  string msgout = __on_ordermsg(msgin);
  TTPrintm("msgin:%s;msgout:%s\n", msgin.c_str(), msgout.c_str());
  nnsend(sock, msgout);
}

// s=oid|1234
// return the id status for oid
string __on_oid(const string& s) {
  string msgout;
  VECST v = splitv2(s, '|');
  if (v.size() == 2) {
    long oid = stol(v[1]);
    while (SR(oid2cwstock).find(oid)==SR(oid2cwstock).end()) {
      msleep(50);
    }
    instrument* p = SR(oid2cwstock)[oid];
    Order* op = p->getOrder(oid);
    msgout = s + "|" + to_string(int(op ? op->status : -1));
  }
  TTPrintm("[%s]msgin:%s;msgout:%s\n", __FUNCTION__, s.c_str(), msgout.c_str());
  return msgout;
}

void on_oid(WebSocket* c, const string& s) {
  wssend(c, __on_oid(s));
}

void on_oid(int sock, const string& s) {
  nnsend(sock, __on_oid(s));
}
