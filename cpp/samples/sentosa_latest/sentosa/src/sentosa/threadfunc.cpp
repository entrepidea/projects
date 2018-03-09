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

#include "threadfunc.h"
#include "msgq.h"
#include "gconfig.h"
#include <portfolio.h>
#include <algoFactory.h>
#include <atomic>
#include <nanomsg/pair.h>
#include <nanomsg/reqrep.h>
#include <mq.h>
#include <cband.h>
#include "oid.h"
#include "mkdata.h"
#include "log.h"

extern std::atomic<bool> g_shutdown;

atomic<uint64_t> ALGO_THREADNUM(0);

void Thread_MKDepth(void* p_) {
  mkdata* pdepth = reinterpret_cast<mkdata*>(p_);

  pdepth->_mode = DEPTH;
  for (int i = 0; i < 1000; ++i) {
    bool cn = pdepth->connect(CConfig::R().IBHOST.c_str(), CConfig::R().IBPORT,
        CConfig::R().IB_CLIENT_ID++);
    if (cn && pdepth->isConnected()) {
      pdepth->_state = MK_REQCONTRACT;
      while (!g_shutdown && pdepth->isConnected()) {
        pdepth->processMessages();
      }
    }

    pdepth->disconnect();
    printf("[%s(%d)]ERROR: TWS connection disconnected!\n", __FUNCTION__,
        __LINE__);
    int t = 10000 * (i + 1);
    printf("Waiting %d seconds to reconnect!\n", t / 1000);
    msleep(t); // sleep for 10 seconds
  }
}

// Pop order from orderQ and send it to ib
void Thread_OMS_ib(shared_ptr<iborder> poms, int clientid) {
  bool cn = false;
  int count = 0;
  while (!g_shutdown) {
    if (isIBRunning()) {
      cn = poms->connect(CR(IBHOST).c_str(), CR(IBPORT),
      clientid);
      if (cn && poms->isConnected()) {
        clientid++;
        msleep(2000);
        poms->reqAccountUpdates(true, CConfig::R().account);
        poms->_state = ST_GETORDERID; // ST_REQCONTRACT;
        while (!g_shutdown && poms->isConnected()) {
          poms->processMessages();
          msleep(10);
        }
      } else {
        msleep(5000);
        count++;
        if (count % 10 == 0) {
          printfr("Cannot connect to IB\n");
        }
      }
    } else {
      msleep(2000);
    }

  }

  //printf("[%s][%d]Jump out!\n", __FUNCTION__, __LINE__);
  poms->cancelAllOrders();
  //while (!poms->isAllOrdersCancelled())
  {
    msleep(100);
  }
  poms->disconnect();
  //SENTOSALOG;
  //printf("[%s(%d)]ERROR: TWS disconnected!\n", __FUNCTION__, __LINE__);
}

void Thread_MKDataTick(shared_ptr<mkdata> pdata, int clientid) {
  bool cn = false;
  int count = 0;

  pdata->_mode = TICKBAR;
  while (!g_shutdown) {
    if (isIBRunning()) {
      cn = pdata->connect(CR(IBHOST).c_str(), CR(IBPORT), clientid);

      if (cn && pdata->isConnected()) {
        msleep(2000);
        pdata->_state = MK_ACCOUNT;
        while (!g_shutdown && pdata->isConnected()) {
          pdata->processMessages();
          msleep(10);
        }
      } else {
        msleep(5000);
        count++;
        if (count % 10 == 0) {
          printfr("Cannot connect to IB\n");
        }
      }
    } else {
      msleep(2000);
    }
  }
  pdata->disconnect();
  SENTOSALOG;
}

void Thread_UpdateSboard() {
  int sock = -1;
  try {
    sock = nn_socket(AF_SP, NN_SUB);
    assert(sock >= 0);
    int to = 100;
    nn_setsockopt(sock, NN_SOL_SOCKET, NN_RCVTIMEO, &to, sizeof(to));
    nn_setsockopt(sock, NN_SUB, NN_SUB_SUBSCRIBE, "", 0);
    //nn_setsockopt(sock, NN_SUB, NN_SUB_SUBSCRIBE, "NQ", 2);
    string endpoint = "tcp://localhost:" + CR(MKD_TO_ALGO_PORT);
    int eid = nn_connect(sock, endpoint.c_str());

    char symbol[8] = { 0 };
    char value[128] = { 0 };
    char *buf = nullptr;

    unordered_map<string, instrument*>& sym2inst = sboard::R().sym2inst;
    while (!g_shutdown) {
      int bytes = nn_recv(sock, &buf, NN_MSG, 0);
      if (bytes > 0) {
        int tmp = 0;
        if (3 == sscanf(buf, "%8[^|]|%d|%s", symbol, &tmp, value)) {
          if (sym2inst.find(symbol) != sym2inst.end()) {
            instrument* pm = sym2inst[symbol];
            if (pm) {
              pm->SetTickValue((TickType) tmp, value);
            }
            //TTPrint("[%s]%s\n", __FUNCTION__, buf);
          } else {
            //symbol not found
          }
        }
        nn_freemsg(buf);
      }
    }
    nn_shutdown(sock, eid);
  } catch (exception& e) {
    TTPrintr("ERROR:%s\n", e.what());
  } catch (...) {
  }
  nn_close(sock);
  SENTOSALOG;
}

// Push order into the orderQ
void Thread_OMS_algo(shared_ptr<iborder> poms) {
  msleep(2000);
  string endpoint = "tcp://localhost:" + CR(ALGO_TO_OMS_PORT);
  int sock = nn_socket(AF_SP, NN_PAIR);
  int eid = nn_connect(sock, endpoint.c_str());
  int to = 100;
  assert(
      nn_setsockopt (sock, NN_SOL_SOCKET, NN_RCVTIMEO, &to, sizeof (to)) >= 0);
  if (eid < 0 || sock < 0) {
    TTPrintr("%d,%d\n", eid, sock);
    return;
  }
  int quantity;
  while (!g_shutdown) {
    char *buf = nullptr;
    int bytes = nn_recv(sock, &buf, NN_MSG, 0);
    if (bytes > 0) {
      string msg(buf, bytes);
      nn_freemsg(buf);
      TTPrintr("[%s](%d)%s\n", __FUNCTION__, __LINE__, msg.c_str());

      if (startwith(msg, CR(closeall))) {  // close all positions
        lock_guard<mutex> g(oid_mtx);
        printfr("Close ALL positions!\n");
        poms->cancelAllOrders();
        int quantity;
        for (int i = 0; i < sboard::R().tradeobjs.n; ++i) {
          tobj* p = sboard::R().tradeobjs[i];
          quantity = p->_tinfo.TOTALVOL();
          int absquant = abs(quantity);
          if (absquant > 0) {
            VECST symbols = p->symbols();
            for (string& s: symbols) {
              printfr("CLOSE - %s\n",s.c_str());
              instrument* pcw = SR(sym2inst)[s];
              if (pcw != nullptr) {
                pcw->ptobj->_tinfo.statuz = (quantity > 0)?LWaitC2:SWaitC2;
                addOrder(pcw, "MKT", m_orderId, -quantity);
                ++m_orderId;
                orderQ::R().push(pcw);
              }
            }
          }
        }
      } else if(startwith(msg, CR(cancelall))) {  //TODO
        //3.7417|YY -> cancel orders of YY
        VECST v = splitv2(msg,'|');
        if (v.size() == 2) {
          poms->cancelOrders(v[1]);
        } else {
          SENTOSAERROR;
        }
      } else if(startwith(msg, CR(closeone))) {
        lock_guard<mutex> g(oid_mtx);
        //3.8730|YY -> close position of YY with market order
        VECST v = splitv2(msg,'|');
        if (v.size() == 2) {
          string& s = v[1];
          printf("CLOSEPOS - %s\n",s.c_str());
          instrument* pcw = SR(sym2inst)[s];
          tobj* p = pcw->ptobj;
          quantity = p->_tinfo.TOTALVOL();
          int absquant = abs(quantity);
          if (absquant > 0) {
            VECST symbols = p->symbols();
            for (string& s: symbols) {
              printf("CLOSEALL - %s\n",s.c_str());
              instrument* pcw = sboard::R().getCW(s);
              if (pcw != nullptr) {
                addOrder(pcw,"MKT",m_orderId,quantity);
                ++m_orderId;
                p->_tinfo.statuz = (quantity > 0)?LWaitC2:SWaitC2;
                orderQ::R().push(pcw);
              }
            }
          }
        } else {SENTOSAERROR;}
      } else if(startwith(msg, "TEST")) {        //TEST|blabla
        static int count=0;
        VECST v = splitv2(msg,'|');
        TTPrintr("TEST:%d,%s\n",++count,msg.c_str());
        if (v.size()>1) {
          bytes = nn_send(sock, v[1].c_str(), v[1].size(), 0);
        }
      } else if(startwith(msg, CR(mktorder))) {
        VECST v = splitv2(msg,'|'); //m|IBM|-500|oid
        instrument* pcw = SR(sym2inst)[v[1]];
        if (pcw != nullptr && v.size()==4) {
          long oid = stoi(v[3]);
          quantity = stoi(v[2]);
          addOrder(pcw, "MKT", oid, quantity);
          orderQ::R().push(pcw);
        }
      } else if(startwith(msg, CR(lmtorder))) {
        VECST v = splitv2(msg,'|'); //m|IBM|-500|125.5|0.5|oid
        if(v.size()==6) {
          string symbol = v[1];
          quantity = stoi(v[2]);
          double price = stof(v[3]);
          double allowedM = stof(v[4]);
          long oid = stoi(v[5]);
          // send LMT order for symbol
          instrument* pcw = SR(sym2inst)[symbol];
          if (pcw != nullptr) {
            addOrder(pcw,"LMT",oid,quantity,price,allowedM);
            orderQ::R().push(pcw);
          } else {
            SENTOSAERROR;
          }
        }
      }
    }
    msleep(500);
  }
  nn_shutdown(sock, eid);
  nn_close(sock);
  SENTOSALOG;
}

void Thread_Record() {
#ifdef FREEVERSION
  return;
#endif
  string endpoint = "tcp://localhost:" + CR(MKD_TO_ALGO_PORT);
  int sock = nn_socket(AF_SP, NN_SUB);
  assert(sock >= 0);
  assert(nn_setsockopt(sock, NN_SUB, NN_SUB_SUBSCRIBE, "", 0) >= 0);
  int eid = nn_connect(sock, endpoint.c_str());

  int to = 100;
  assert(
      nn_setsockopt(sock, NN_SOL_SOCKET, NN_RCVTIMEO, &to, sizeof (to)) >= 0);

  string ymdstr = ymd();
  string fname = CR(REPLAYFILEDIR)+"quant365-" + ymdstr + ".lst";
  FILE* fp = fopen(fname.c_str(), "a+");
  gbuffer gbuf;
  gbuf.fp = fp;

  if (fp) {
    //TTPrint("SAVE REPLAY FILE TO %s.\n", fname.c_str());
    while (!g_shutdown) {
      char *buf = nullptr;
      int bytes = nn_recv(sock, &buf, NN_MSG, 0);
      if (bytes > 0) {
        std::string msg(buf, bytes);
        nn_freemsg(buf);
        //printf("%s:%s\n",__FUNCTION__,buf);
        if (!msg.empty()) {
          gbuf.put(msg);
        }
      }
    }
    nn_shutdown(sock, eid);
  }
  nn_close(sock);
  SENTOSALOG;
}

namespace nsreplay {

  struct TimeAndMsg {
    uint64_t t;
    string msg;
  };

  vector<TimeAndMsg> readreplayfile(const string& filetoreplay) {
    ifstream f(filetoreplay);
    vector<TimeAndMsg> lines;
    string x;
    while (f.is_open() && f.good()) {
      getline(f, x);
      if (!x.empty()) {
        vector<string> tmp = splitv2(x, '@');
        if (tmp.size() == 2) {
          TimeAndMsg tam = { (uint64_t) atoll(tmp[0].c_str()), tmp[1] };
          lines.push_back(tam);
        }
      }
    }
    return lines;
  }
}

void Thread_Replay(const string& filetoreplay) {
  using namespace nsreplay;

  string endpoint = "tcp://*:" + CR(MKD_TO_ALGO_PORT);
  int sock = nn_socket(AF_SP, NN_PUB);
  int eid = nn_bind(sock, endpoint.c_str());
  //int linger = 0;
  //ibsim.setsockopt(ZMQ_LINGER, &linger, sizeof(linger));

  uint64_t curt = 0;
  uint64_t logt = 0;
  vector<TimeAndMsg> lines = readreplayfile(filetoreplay);
  int i = 0, sz = lines.size();
  bool isspeedup = CR(isspeedyreplay);
  uint64_t sleepingtime = CR(sleepingtime);
  while (!g_shutdown && i++ < sz) {
    logt = lines[i].t;
    curt = getMicroTime();
    static uint64_t diff = curt - logt;      //89041208806
    if (isspeedup) {
      msleep(sleepingtime);
    } else {
      while (!g_shutdown && (diff + logt > curt)) {
        curt = getMicroTime();
      }
    }
    string& msg = lines[i].msg;
    int bytes = nn_send(sock, msg.c_str(), msg.size() + 1, 0);
    //s_send(ibsim, msg, ZMQ_NOBLOCK);
    //printf("%llu@%s\n", curt, msg.c_str());
  }
  msleep(2000);
  nn_shutdown(sock, eid);
  nn_close(sock);
  TTPrint("Replay finished!\n");
}
