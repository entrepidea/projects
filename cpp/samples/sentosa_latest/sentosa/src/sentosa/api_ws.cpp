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

#include "api_ws.h"
#include "api_core.h"
#include "msgq.h"
#include "gconfig.h"
#include <portfolio.h>
#include <algoFactory.h>
#include <atomic>
#include <nanomsg/pair.h>
#include <nanomsg/reqrep.h>
#include <o2j.h>
#include <JsonDiff.h>

extern std::atomic<bool> g_shutdown;

namespace seasocks {

  void index_json(MWI* cs_, WebSocket* p) {
    static string cache="";
    //timer t_(__FUNCTION__);
    string r;
    MWI& cs = *cs_;
    if (cs[p] == 0) { //new socket
      if (cache.empty()) {
        cache = fullpage();
      }
      r=cache;
    } else {
      string tmp = fullpage();
      r = jsondiff(cache, tmp);
      cache = tmp;
    }
    printf("[%s]%s\n", __FUNCTION__, r.c_str());
    printf("{%d}fp:%luk\n", cs[p], r.size()/1024);
    wssend(p, r);
    wssend(p, orderJson());
    cs[p] += 1;
  }

  void symbl_json(MWI* cs_,WebSocket* p,const string& sym) {
    static map<string,string> sym2cache;
    //timer t_(__FUNCTION__);
    MWI& cs = *cs_;
    string r;
    string& cache = sym2cache[sym];
    if (cs[p] == 0) { //new socket
      if (cache.empty()) {
        cache = tobjpage(sym);
      }
      r = cache;
    } else {
      string tmp = tobjpage(sym);
      r = jsondiff(cache, tmp);
      sym2cache[sym] = tmp;
    }
    //printf("{%d}sy:%luk\n", cs[p], r.size()/1024);
    wssend(p, r);
    wssend(p, orderJson(sym));
    cs[p] += 1;
  }

  void wshandler::initcallbacks() {
    try {
      cb.add("d", [](Server* p) {p->terminate();});
      cb.add("c", [](WebSocket* c) {c->close();});
      cb.add("a", [](WebSocket* c, string s) {c->send(s.c_str());});

      cb.add(CR(completeJ), function<void(MWI*,WebSocket*)>(index_json));
      cb.add(CR(updateJ), function<void(MWI*,WebSocket*,string)>(symbl_json));

      cb.add(CR(lmtorder), [](WebSocket* c,string s) {on_ordermsg(c,s);}); //limit order
      cb.add(CR(mktorder), [](WebSocket* c,string s) {on_ordermsg(c,s);}); //market order
      cb.add(CR(orderid), [](WebSocket* c,string s) {on_oid(c,s);}); //TODO
      cb.add(CR(closeall), []() {algoMQ::R().sendmq(CR(closeall));});
      cb.add("f", []() {}); //TODO
      cb.add("g", []() {}); //TODO
      cb.add("h", []() {}); //TODO
    } catch (bad_typeid& bt) {
      printf("%s\n", bt.what());
    }
  }

  void wshandler::onData(WebSocket* p, const char* data) {
    if (data == nullptr)
      return;
    if (startwith(data, CR(completeJ))) {
      TTPrint("[%s](%d)\n",__FUNCTION__,__LINE__);
      cb.call(CR(completeJ),&cs,p); return;
    } else if(startwith(data,CR(updateJ))) {
      cb.call(CR(updateJ),&cs,p,string(data+1)); return;
    } else if(startwith(data,CR(closeall))) {
      cb.call(CR(closeall),p); return;
    } else if(startwith(data,CR(lmtorder))) {
      cb.call(CR(lmtorder), p, string(data)); return;
    } else if(startwith(data,CR(mktorder))) {
      cb.call(CR(mktorder), p, string(data)); return;
    } else if(startwith(data,CR(orderid))) {
      cb.call(CR(orderid), p, string(data)); return;
    }
  }

  void wshandler::onConnect(WebSocket* p) {
    cs[p] = 0;
    p->send("z");
    TTPrint("Connected:%s,%s\n", p->getRequestUri().c_str(),
    formatAddress(p->getRemoteAddress()).c_str());
  }

  void wshandler::onDisconnect(WebSocket* p) {
    cs.erase(p);
    TTPrint("Disconnected:%s,%s\n", p->getRequestUri().c_str(),
  formatAddress(p->getRemoteAddress()).c_str());
}
}
