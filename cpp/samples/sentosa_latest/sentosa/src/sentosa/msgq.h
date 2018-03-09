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

#ifndef SENTOSA_MSGQ_H
#define SENTOSA_MSGQ_H

#include <condition_variable>
#include <mutex>
#include <sentosaconfig.h>
#include <cwrapper.h>

using std::mutex;

///@brief A singleton Queue of orders
class orderQ {
private:
  static bool initialized;
  static orderQ* pinstance;
  static mutex pcoQlock_;
  orderQ();

public:
  queue<instrument*> pcoQ;

  static orderQ& R();

  instrument* pop();
  void push(instrument*);
};

//////////////////////////////////////////////////////////////////////////
template<typename T>
class msgQ {
  queue<T> q;

  mutex mtx;
  condition_variable cv;
public:

  void push(const T& e) {
    lock_guard<mutex> lg(mtx);
    q.push(e);
    cv.notify_one();
  }

  // Here is OK as we will put a pointer into the queue
  T pop() {
    unique_lock<mutex> ulk(mtx);
    cv.wait(ulk, [this]() {return !q.empty();});
    T r = q.front();
    q.pop();
    return r;
  }

};

//#define OrderQ msgQ<Order*>
//////////////////////////////////////////////////////////////////////////
struct gbuffer {
  int bufSize;
  FILE* fp = nullptr;
  int count = 0; //length of string in the buffer
  char* head = nullptr; // = raiibuf.get();

  gbuffer() {
    bufSize = CConfig::R().recordbufsize;
    head = new char[bufSize];
  }

  ~gbuffer() {
    if (fp) {
      fwrite(head, sizeof(char), count, fp);
      fflush(fp);
      fclose(fp);
    }
    delete[] head;
  }

  void put(const string& _str) {
    if (!_str.empty()) {
      char tmp[256] = { };
      sprintf(tmp, "%lu@%s\n", getMicroTime(), _str.c_str());
      uint32_t strsize = strlen(tmp); // + 1;
      uint32_t required_buffer_len = count + strsize;

      if (required_buffer_len > bufSize) {
        size_t r = fwrite(head, sizeof(char), count, fp);
        //printf("write files\n");
        if (r == count) {
          memcpy(head, tmp, strsize * sizeof(char));
          count = strsize;
          fflush(fp);
          return;
        } else {
          //error
          //http://www.cplusplus.com/reference/cstdio/fwrite/
        }
      }
      memcpy(head + count, tmp, strsize * sizeof(char));
      count = required_buffer_len;
    }
  }
};

#endif
