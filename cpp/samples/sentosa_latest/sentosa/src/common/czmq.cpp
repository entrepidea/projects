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

#include "czmq.h"
#include "zhelpers.hpp"
#include "log.h"
#include "sentosaconfig.h"
#include "util.h"
#include <gconfig.h>

#define DEBUG2

extern const char* TTField[TTSIZE];
/*A ZMQ context is thread safe and may be shared among as many application threads as necessary,
without any additional locking required on the part of the caller.*/
context_t CNTXT(ALGOTHREADMAX);
mkdzmq* mkdzmq::pinstance = nullptr;
mutex mkdzmq::mkdzmqlock_;


mkdzmq::mkdzmq(){
  //pcontext_out = new context_t(1);
  psocket_out = new socket_t(CNTXT, ZMQ_PUB);
  endpoints.push_back("tcp://*:" + CConfig::R().MKD_TO_ALGO_PORT);
  psocket_out->bind(endpoints.back().c_str());
  int linger = 0;
  psocket_out->setsockopt(ZMQ_LINGER, &linger, sizeof(linger));
}

void mkdzmq::disconnect(){
  /*for (uint64_t i = 0; i < endpoints.size(); ++i){
    psocket_out->disconnect(endpoints[i].c_str());
  }*/
  psocket_out->close();
}

void mkdzmq::close(){
  if (pinstance){
    R().disconnect();
  }
}

mkdzmq& mkdzmq::R(){
  if (pinstance == nullptr){
    lock_guard<mutex> g(mkdzmqlock_);
    if (pinstance == nullptr){
      pinstance = new mkdzmq();
    }
  }
  return *pinstance;
}

void mkdzmq::sendmq(const string& msg){
  printf("%s\n",msg.c_str());
  bool r = s_send(*psocket_out, msg, ZMQ_NOBLOCK);
  if (!r){
    TTPrint("[%s(%d)]ZMQ Error!\n", __FUNCTION__, __LINE__);
  }
}

void mkdzmq::sendmq(const string& stock, const string& msg){
  s_sendmore(*psocket_out, stock);
  bool r = s_send(*psocket_out, msg, ZMQ_NOBLOCK);
}

void mkdzmq::sendstr(const string& symbol, TickType field, const char* value){
  char msg[128] = {};
  sprintf(msg, "%s|%d|%s", symbol.c_str(), field, value);
  sendmq(msg);
#ifdef DEBUG2
  sprintf(msg, "%s|%s|%d", symbol.c_str(), TTField[field], value);
  printf("[debug]%llu@%s\n", GetSystemTimeAsULL(), msg);
#endif
}
void mkdzmq::sendint(const string& symbol, TickType field, int value){
  char msg[128] = {};
  sprintf(msg, "%s|%d|%d", symbol.c_str(), field, value);
  sendmq(msg);
#ifdef DEBUG2
  sprintf(msg, "%s|%s|%d", symbol.c_str(), TTField[field], value);
  printf("[debug]%llu@%s\n", GetSystemTimeAsULL(), msg);
#endif
  
}
void mkdzmq::sendflo(const string& symbol, TickType field, double value){
  char msg[128] = {};
  sprintf(msg, "%s|%d|%.2f", symbol.c_str(), field, value);
  sendmq(msg);
#ifdef NDEBUG2
  sprintf(msg, "%s|%s|%.2f", symbol.c_str(), TTField[field], value);
  printf("[debug]%llu@%s\n", GetSystemTimeAsULL(), msg);
#endif
}

//////////////////////////////////////////////////////////////////////////
algozmq* algozmq::pinstance = nullptr;
mutex algozmq::algozmqlock_;

algozmq::algozmq(){
  psocket_out = new socket_t(CNTXT, ZMQ_PAIR);
  endpoints.push_back("tcp://*:" + CConfig::R().ALGO_TO_OMS_PORT);
  psocket_out->bind(endpoints.back().c_str());
  int linger = 0;
  psocket_out->setsockopt(ZMQ_LINGER, &linger, sizeof(linger));
}

void algozmq::disconnect(){
  //for (uint64_t i = 0; i < endpoints.size(); ++i){
  //  psocket_out->disconnect(endpoints[i].c_str());
  //}
  psocket_out->close();
}

void algozmq::close(){
  if (pinstance){
    R().disconnect();
  }
}

algozmq& algozmq::R(){
  if (pinstance == nullptr){
    lock_guard<mutex> g(algozmqlock_);
    if (pinstance == nullptr){
      pinstance = new algozmq();
    }
  }
  return *pinstance;
}

//string algozmq::getmq(int threadid){
//  return s_recv(socket_ins[threadid]);
//}
void algozmq::sendmq(const string& msg){
  bool r = s_send(*psocket_out, msg, ZMQ_NOBLOCK);
  if (!r){
    TTPrint("[%s(%d)]ZMQ Error!\n", __FUNCTION__, __LINE__);
  }
}

//////////////////////////////////////////////////////////////////////////
omszmq* omszmq::pinstance = nullptr;
mutex omszmq::omszmqlock_;

omszmq::omszmq(){
  //pcontext_in = new context_t(1);
  psocket_in = new socket_t(CNTXT, ZMQ_PAIR);
  endpoints.push_back(("tcp://localhost:" + CConfig::R().ALGO_TO_OMS_PORT));
  psocket_in->connect(endpoints.back().c_str());
  int linger = 0;
  psocket_in->setsockopt(ZMQ_LINGER, &linger, sizeof(linger));
  int timeout_recv = RECV_TIMEOUT;
  psocket_in->setsockopt(ZMQ_RCVTIMEO, &timeout_recv, sizeof(timeout_recv));
}

void omszmq::disconnect(){
  //for (uint64_t i = 0; i < endpoints.size(); ++i){
  //  psocket_in->disconnect(endpoints[i].c_str());
  //}
  psocket_in->close();
}
void omszmq::close(){
  if (pinstance){
    R().disconnect();
  }
}

omszmq& omszmq::R(){
  if (pinstance == nullptr){
    lock_guard<mutex> g(omszmqlock_);
    if (pinstance == nullptr){
      pinstance = new omszmq();
    }
  }
  return *pinstance;
}

string omszmq::getmq(){
  return s_recv(*psocket_in);
}

void close_zmq(){
  try{
    mkdzmq::close();
    algozmq::close();
    omszmq::close();
  }
  catch (...){}
}

//////////////////////////////////////////////////////////////////////////
algoBackEnd* algoBackEnd::pinstance = nullptr;
mutex algoBackEnd::lock_;

algoBackEnd::algoBackEnd(){
  psocket_out = new socket_t(CNTXT, ZMQ_REQ);
  endpoints.push_back("tcp://*:" + CConfig::R().ALGO_ENGINE_PORT);
  psocket_out->bind(endpoints.back().c_str());
  int linger = 0;
  psocket_out->setsockopt(ZMQ_LINGER, &linger, sizeof(linger));
}

string algoBackEnd::request(const string& msg){
  bool r = s_send(*psocket_out, msg, ZMQ_NOBLOCK);
  if (r){
    return s_recv(*psocket_out);
  }
  return string();
}

void algoBackEnd::disconnect(){
  psocket_in->close();
}
void algoBackEnd::close(){
  if (pinstance){
    R().disconnect();
  }
}

algoBackEnd& algoBackEnd::R(){
  if (pinstance == nullptr){
    lock_guard<mutex> g(lock_);
    if (pinstance == nullptr){
      pinstance = new algoBackEnd();
    }
  }
  return *pinstance;
}

