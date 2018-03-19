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

#include "EPosixClientSocket.h"

#include "EPosixClientSocketPlatform.h"
#include "TwsSocketClientErrors.h"
#include "EWrapper.h"

#include <string.h>

///////////////////////////////////////////////////////////
// member funcs
EPosixClientSocket::EPosixClientSocket( EWrapper *ptr) : EClientSocketBase( ptr)
{
  m_fd = -1;
}

EPosixClientSocket::~EPosixClientSocket()
{
  SocketsDestroy();//move it from disconnect to destructor!! - henry
}

bool EPosixClientSocket::eConnect( const char *host, unsigned int port, int clientId)
{
  // reset errno
  errno = 0;

  // already connected?
  if( m_fd >= 0) {
    errno = EISCONN;
    getWrapper()->error( NO_VALID_ID, ALREADY_CONNECTED.code(), ALREADY_CONNECTED.msg());
    return false;
  }

  // initialize Winsock DLL (only for Windows)
  if ( !SocketsInit())  {
    return false;
  }

  // create socket
  m_fd = socket(AF_INET, SOCK_STREAM, 0);

  // cannot create socket
  if( m_fd < 0) {
    // uninitialize Winsock DLL (only for Windows)
    SocketsDestroy();
    getWrapper()->error( NO_VALID_ID, FAIL_CREATE_SOCK.code(), FAIL_CREATE_SOCK.msg());
    return false;
  }

  // use local machine if no host passed in
  if ( !( host && *host)) {
    host = "127.0.0.1";
  }

  // starting to connect to server
  struct sockaddr_in sa;
  memset( &sa, 0, sizeof(sa));
  sa.sin_family = AF_INET;
  sa.sin_port = htons( port);
  sa.sin_addr.s_addr = inet_addr( host);

  // try to connect
  if( (connect( m_fd, (struct sockaddr *) &sa, sizeof( sa))) < 0) {
    // error connecting
    // uninitialize Winsock DLL (only for Windows)
    SocketsDestroy();
    getWrapper()->error( NO_VALID_ID, CONNECT_FAIL.code(), CONNECT_FAIL.msg());
    m_fd = -1;
    return false;
  }

  // set client id
  setClientId( clientId);

  onConnectBase();

  while( isSocketOK() && !isConnected()) {
    if ( !checkMessages()) {
      // uninitialize Winsock DLL (only for Windows)
      SocketsDestroy();
      getWrapper()->error( NO_VALID_ID, CONNECT_FAIL.code(), CONNECT_FAIL.msg());
      return false;
    }
  }


  // set socket to non-blocking state
  if ( !SetSocketNonBlocking(m_fd)){
    // error setting socket to non-blocking
    SocketsDestroy();
    getWrapper()->error( NO_VALID_ID, CONNECT_FAIL.code(), CONNECT_FAIL.msg());
    return false;
  }

  // successfully connected
  return true;
}

void EPosixClientSocket::eDisconnect()
{
  if ( m_fd >= 0 )
    // close socket
    SocketClose(m_fd);
  m_fd = -1;
  //Un-initialize WinSock DLL (only for Windows)
  //SocketsDestroy(); // we should destrory this if zmq is still running! - henry
  eDisconnectBase();
}

bool EPosixClientSocket::isSocketOK() const
{
  return ( m_fd >= 0);
}

int EPosixClientSocket::fd() const
{
  return m_fd;
}

int EPosixClientSocket::send(const char* buf, size_t sz)
{
  if( sz <= 0)
    return 0;

  int nResult = ::send( m_fd, buf, sz, 0);

  if( nResult == -1 && !handleSocketError()) {
    return -1;
  }
  if( nResult <= 0) {
    return 0;
  }
  return nResult;
}

int EPosixClientSocket::receive(char* buf, size_t sz)
{
  if( sz <= 0)
    return 0;

  int nResult = ::recv( m_fd, buf, sz, 0);

  if( nResult == -1 && !handleSocketError()) {
    return -1;
  }
  if( nResult <= 0) {
    return 0;
  }
  return nResult;
}

///////////////////////////////////////////////////////////
// callbacks from socket

void EPosixClientSocket::onConnect()
{
  if( !handleSocketError())
    return;

  onConnectBase();
}

void EPosixClientSocket::onReceive()
{
  if( !handleSocketError())
    return;

  checkMessages();
}

void EPosixClientSocket::onSend()
{
  if( !handleSocketError())
    return;

  sendSeriesedData();
}

void EPosixClientSocket::onClose()
{
  printf("EPosixClientSocket::onClose\n");
  if(!handleSocketError())
    return;

  eDisconnect();
  getWrapper()->connectionClosed();
}

void EPosixClientSocket::onError()
{
  handleSocketError();
}

///////////////////////////////////////////////////////////
// helper
bool EPosixClientSocket::handleSocketError()
{
  // no error
  if( errno == 0)
    return true;

  // Socket is already connected
  if( errno == EISCONN) {
    return true;
  }

  if( errno == EWOULDBLOCK)
    return false;

  if (errno == EAGAIN){
    errno = 0;
    return false;
  }

  if( errno == ECONNREFUSED) {
    getWrapper()->error(NO_VALID_ID, CONNECT_FAIL.code(), CONNECT_FAIL.msg());
  }else {
    getWrapper()->error(NO_VALID_ID, SOCKET_EXCEPTION.code(),SOCKET_EXCEPTION.msg() + strerror(errno));
  }
  // reset errno
  errno = 0;
  eDisconnect();
  return false;
}
