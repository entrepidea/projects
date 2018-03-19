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

#ifndef __SWINDOWS__
#define __SWINDOWS__

#include <sentosaconfig.h>

using std::to_string;
//
// a sliding window data structure
//
//          [++++ WINLEN(_sz) ++++] -->Window Move
// [++++++++++++++++++ SWINDOWBUFFLEN ++++++......++++++++++++++]
//
// Note: can be replaced by boost circular buffer
class swind{

  static const int SWINDOWBUFFLEN = 1024 * 32;

  uint64_t num = 0;
  //(1024*258)
  // the address of the last item
  double *tail = nullptr;
  // head of the data, the address of the first item
  double *head = nullptr;
  //shared_ptr<double> data = make_shared<double>(0);
  double* _d = nullptr;

  uint64_t _sz = 0;//window len

public:

  void resize(int i){
    if (_sz != i){
      reserve(i);
      if (i > 0){
        tail = head + (i - 1);
      }
      num = i;
    }
  }
  void reserve(int i){
    if (_sz != i){
      _sz = i;
      if (_d){ delete [] _d; }
      _d = new double[SWINDOWBUFFLEN]();
      head = _d;
    }
  }
  uint64_t size(){ return num; }
  bool empty(){ return num == 0; }
  double& operator[](int i){
    return head[i];
  }
  double back() const{
    return (tail)?(*tail):(.0);
  }

  void push_back(double x){
    if (tail && _sz>0) {
      num++;
      if (num == 1) {
        *tail = x;
        return;
      }
      tail++;
      *tail = x;
      if (num > _sz) {
        head++;
        num--;
      }
      int used = tail - _d + 1;
      if (used >= SWINDOWBUFFLEN) {
        uint64_t tmp = sizeof(double);
        size_t sz = (_sz) * tmp;
        memcpy(_d, head, sz);
        head = _d;
        tail = _d + _sz;
      }
    }
  }

  swind():num(0),_sz(0){
    //reserve(1024);//by default allocate one k
  }
  swind(size_t wlen_) :_sz(wlen_){}
  ~swind(){ if (_d){ delete[]_d; _d = nullptr; } }

  void print(){
    vector<double> vd(head,tail+1);
    for(double d: vd){
      printf("%.3f,",d);
    }
    printf("\n");
  }

  vector<double> toVec(){
    vector<double> vd(head,tail+1);
    return vd;
  }

  // Enabled for text archives (e.g. XML, JSON)
  template<class Archive, cereal::traits::EnableIf<
      cereal::traits::is_text_archive<Archive>::value> = cereal::traits::sfinae>
  std::string save_minimal(Archive &) const {
    string s;
    for (int i=0;i<num;++i){
      s += to_string(head[i]) + ",";
    }
    return s.substr(0,s.size()-1);
  }

  /*template <class Archive>
  std::string save_minimal( Archive & ar ){
    string s;
    vector<double> vd(head,tail+1);
    for(double d: vd){
      s +=to_string(d) + ",";
    }
    return s.substr(0,s.size()-1);
  }*/

  operator double() const{
    return back();
  }//implicit conversion operator
};

#endif
