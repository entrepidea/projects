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

// An extraordinarily simple test which presents a web page with some buttons.
// Clicking on the numbered button increments the number, which is visible to
// other connected clients.  WebSockets are used to do this: by the rather
// suspicious means of sending raw JavaScript commands to be executed on other
// clients.

#ifndef SENTOSA_API_WS_H
#define SENTOSA_API_WS_H

#include <seasocks/PrintfLogger.h>
#include <seasocks/Server.h>
#include <seasocks/StringUtil.h>
#include <seasocks/WebSocket.h>
#include <seasocks/util/Json.h>
#include "callback.h"

namespace seasocks {
#define MWI std::map<WebSocket*,int>

  void index_json(MWI* cs_, WebSocket* p);
  void symbl_json(MWI* cs_, WebSocket* p, const std::string& sym);

  class wshandler: public WebSocket::Handler {
  private:
    MWI cs;
    callbacks cb;
    Server* _server;

  public:
    wshandler(Server* server): _server(server) {}

    virtual void onConnect(WebSocket* connection);
    virtual void onData(WebSocket* connection, const char* data);
    virtual void onDisconnect(WebSocket* connection);
    void initcallbacks();
  };

}

#endif
