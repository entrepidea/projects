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

/* Copyright (C) 2013 Interactive Brokers LLC. All rights reserved. This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

#ifndef eposixclientsocketcommon_def
#define eposixclientsocketcommon_def

#if defined(_WIN64) || defined(_WIN32)
  // Windows
  // includes
  #include <WinSock2.h>
  #include <time.h>

  // defines
#ifndef EISCONN
#define EISCONN WSAEISCONN
#endif // !EISCONN
#ifndef EWOULDBLOCK
#define EWOULDBLOCK WSAEWOULDBLOCK
#endif
#ifndef ECONNREFUSED
#define ECONNREFUSED WSAECONNREFUSED
#endif

  //#define EISCONN WSAEISCONN
  //#define EWOULDBLOCK WSAEWOULDBLOCK
  //#define ECONNREFUSED WSAECONNREFUSED

  // helpers
  inline bool SocketsInit( void) {
    WSADATA data;
    return ( !WSAStartup( MAKEWORD(2, 2), &data));
  };
  inline bool SocketsDestroy() { return ( !WSACleanup()); };
  inline int SocketClose(int sockfd) { return closesocket( sockfd); };

  inline bool SetSocketNonBlocking(int sockfd) { 
    unsigned long mode = 1;
    return ( ioctlsocket( sockfd, FIONBIO, &mode) == 0);
  };

#else
  // LINUX
  // includes
  #include <arpa/inet.h>
  #include <errno.h>
  #include <sys/select.h>
  #include <sys/fcntl.h>
  #include <unistd.h>

  // helpers
  inline bool SocketsInit() { return true; };
  inline bool SocketsDestroy() { return true; };
  inline int SocketClose(int sockfd) { return close( sockfd); };

  inline bool SetSocketNonBlocking(int sockfd) { 
    // get socket flags
    int flags = fcntl(sockfd, F_GETFL);
    if (flags == -1)
      return false;

    // set non-blocking mode
    return ( fcntl(sockfd, F_SETFL, flags | O_NONBLOCK) == 0);
  };

#endif

#endif
