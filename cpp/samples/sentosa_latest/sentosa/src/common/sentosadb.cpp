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

#include "sentosadb.h"

#include <mysql.h>

#include "util.h"
#include "gconfig.h"

using std::lock_guard;

CDB* CDB::pinstance = nullptr;
mutex CDB::dblock_;

CDB::CDB(){
    conn = (void*)mysql_init(nullptr);
    connectDB();
}

CDB::~CDB(){
    disconnectDB();
}

CDB& CDB::R(){
  if (pinstance == nullptr){
    lock_guard<mutex> g(dblock_);
    if (pinstance == nullptr){
      pinstance = new CDB();
    }
  }
  return *pinstance;
}

void CDB::connectDB(){
  if (!mysql_real_connect((MYSQL*)conn,
    CConfig::R().DBHOST.c_str(),
    CConfig::R().DBUSER.c_str(),
    CConfig::R().DBPASS.c_str(),
    CConfig::R().DBNAME.c_str(), 0, nullptr, 0))
  {
    printfr("%s\n", mysql_error((MYSQL*)conn));
    exit(1);
  }
}

void CDB::disconnectDB(){
  mysql_close((MYSQL*)conn);
}

uint64_t getRowNum(const char* sql){
  MYSQL* conn = (MYSQL*)CDB::R().conn;
  if (mysql_query(conn, sql)) {
    SENTOSAERROR;
    TTPrint("%s\n", mysql_error(conn));
    exit(1);
  }
  MYSQL_RES* res = mysql_use_result(conn);
  MYSQL_ROW row;
  uint64_t n;
  while ((row = mysql_fetch_row(res)) != nullptr){
    n = atoi(row[0]);
  }
  mysql_free_result(res);
  return n;
}


uint64_t insertARow(const char* sql){
  MYSQL* conn = (MYSQL*)CDB::R().conn;
  if (mysql_query(conn, sql)) {
    SENTOSAERROR;
    TTPrint("%s\n", mysql_error(conn));
    exit(1);
  }
  uint64_t a = mysql_affected_rows(conn);
  return a;
}
