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

#ifndef __SENTOSADB__
#define __SENTOSADB__

#include <sentosaconfig.h>
#include <mutex>

using std::mutex;

class CDB{
  static bool initialized;
  static CDB* pinstance;
  static mutex dblock_;

  void connectDB();
  void disconnectDB();

  CDB();
  ~CDB();
public:
  static CDB& R();
  void *conn;
};

uint64_t getRowNum(const char* sql);

///return the number of rows affected
uint64_t insertARow(const char* sql);

#endif
