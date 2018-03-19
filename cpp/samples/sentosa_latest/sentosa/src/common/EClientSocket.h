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

#ifndef eclientsocket_def
#define eclientsocket_def

#ifndef DLLEXP
#define DLLEXP
#endif

#include "EClientSocketBase.h"

#include <memory>

class EWrapper;

class DLLEXP EClientSocket : public EClientSocketBase
{
	class MySocket;
public:

	explicit EClientSocket( EWrapper *ptr);
	~EClientSocket();

	// override virtual funcs from EClient
	bool eConnect( const char *host, UINT port, int clientId=0);
	void eDisconnect();

private:

	int send(const char* buf, size_t sz);
	int receive(char* buf, size_t sz);

	// callback from socket
	void onConnect(int i);
	void onReceive(int i);
	void onSend(int i);
	void onClose(int i);

	// helper
	bool handleSocketError(int i);

	bool isSocketOK() const;

private:

	std::unique_ptr<MySocket> m_pSocket;
};

#endif
