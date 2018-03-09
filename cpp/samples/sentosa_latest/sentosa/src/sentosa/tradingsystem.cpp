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

#include "tradingsystem.h"
#include <atomic>
#include <gconfig.h>
#include <portfolio.h>
#include <mq.h>
#include <httpclient.h>
#include <util.h>
#include "threadfunc.h"
#include "api_nn.h"

using namespace seasocks;

extern std::atomic<bool> g_shutdown;
extern atomic<uint64_t> ALGO_THREADNUM;

void Thread_API_WS(Server* p) {
  const string DOC_ROOT = "~/.sentosa/";

  shared_ptr<wshandler> handler(new wshandler(p));
  handler->initcallbacks();
  p->addWebSocketHandler("/ws", handler);
  p->serve(expand_user(DOC_ROOT).c_str(), stoi(CR(WS_MON_PORT)));
}

tradingsystem::tradingsystem() {
  if (isSentosaRunning()) {
    g_shutdown = true;
    return;
  }
  mode = CR(_mode);
  if (CR(account)=="DU198456") {
    printfy("%s%s%s\n",
    "Warning: You haven't set your IB account yet. You need to open ",
    expand_user("~/.sentosa/sentosa.yml").c_str(),
    ", find 'account' under 'global' section, and replace the demo "
    "account with your own IB account.");
  }
}

int tradingsystem::run() {
  if (g_shutdown)
    return 1;
  //if (percentTime() < -1.5) {
  //  g_shutdown = true;
  //  return 1;
  //}
  try {
    //printf("pid: %d\n",getpid());
    auto fu1 = async(launch::async, check_gshutdown, false);
    sboard::R();
    if (mode == SYS_MODE::RECORD_MODE) {
      printf("Mode:record\n");
      ptik = make_shared<mkdata>();
      threads.push_back(new thread(Thread_MKDataTick, ptik,
      CR(IB_CLIENT_ID)++));
      threads.push_back(new thread(Thread_Record));
    } else if (mode == SYS_MODE::REPLAY_MODE) {
      printf("Mode:sim\n");
      threads.push_back(new thread(Thread_Replay, CR(filetoreplay)));
      poms = make_shared<iborder>();
      startOMS(poms);
    } else if (mode == SYS_MODE::TRADE_MODE) {
      printf("Mode:trade\n");
      ptik = make_shared<mkdata>();
      threads.push_back(new thread(Thread_MKDataTick, ptik,
      CR(IB_CLIENT_ID)++));
      threads.push_back(new thread(Thread_Record));
      poms = make_shared<iborder>();
      startOMS(poms);
    } else if (mode == SYS_MODE::MERLION_MODE) {
      printf("Mode:merlion\n");
      ptik = make_shared<mkdata>();
      threads.push_back(new thread(Thread_MKDataTick, ptik,
      CR(IB_CLIENT_ID)++));
      poms = make_shared<iborder>();
      startOMS(poms);
    } else {
      TTPrint("Mode %d doesn't exist.\n", mode);
      return 1;
    }

    threads.push_back(new thread(Thread_API_NN));
    threads.push_back(new thread(Thread_UpdateSboard));

    Logger::Level log_level = CR(isdebug)? Logger::Level::DEBUG : Logger::Level::ERROR;
    shared_ptr<Logger> logger(new PrintfLogger(log_level));
    pws = new Server(logger);
    threads.push_back(new thread(Thread_API_WS, pws));

    fu1.get(); //block here
  } catch (exception& e) {
    printfr("GoodBye: %s\n", e.what());
  } catch (...) {
    printfr("Terminated unexpectedly!\n");
  }
  return 0;
}

bool tradingsystem::live() const{
  return g_shutdown == true;
}

void tradingsystem::startReport() {
  portfolio pot;
  for (int i = 0; i < sboard::R().tradeobjs.n; ++i) {
    pot.merge(sboard::R().tradeobjs[i]->_tinfo);
  }
  //pot.report();
}

/*void tradingsystem::startALGO(){
 printf("[%s(%d)]Algo per CBand Launched!\n", __FUNCTION__, __LINE__);
 algoMQ::R();
 vector<string>& vs1 = CConfig::R().restricted;
 for(int i=0;i<sboard::R().tradeobjs.n;++i){
 tobj* p = sboard::R().tradeobjs[i];
 string pid = p->pid();
 vector<string> vs0 = splitv2(pid, '-');
 if (find_first_of(vs1.begin(), vs1.end(), vs0.begin(), vs0.end()) == vs1.end()){
 threads.push_back(new thread(Thread_Algo, (void*)p));
 }
 }
 }*/

void tradingsystem::startMon() {
  printf("[%s(%d)]Monitor Launched!\n", __FUNCTION__, __LINE__);
  vector<string>& vs1 = CConfig::R().restricted;
}

void tradingsystem::startOMS(shared_ptr<iborder> poms) {
  threads.push_back(
      new thread(Thread_OMS_ib, poms, CConfig::R().IB_CLIENT_ID++));
  threads.push_back(new thread(Thread_OMS_algo, poms));
}

tradingsystem::~tradingsystem() {
  while ((poms && poms->isConnected()) || (ptik && ptik->isConnected())) {
    msleep(100);
  }
  while (ALGO_THREADNUM > 0) {
    msleep(100);
  }
  nn_term();
  if (pws)
    pws->terminate();
  //SENTOSALOG;
  //printf("waiting for threads joined...\n");
  for (thread* t : threads) {
    if (t->joinable()) {
      t->join();
      delete t;
    }
  }
  //delete ptik;
  //delete poms;
  delete pws;
  SENTOSALOG;
}
