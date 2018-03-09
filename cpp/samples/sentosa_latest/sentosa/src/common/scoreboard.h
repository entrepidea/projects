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

#ifndef SCOREBOARD_H
#define SCOREBOARD_H

#include "cwrapper.h"
#include <util.h>
#include <unordered_map>
#include <accInfo.h>

#define PCWCW pair<instrument*, instrument*>

struct VECTOBJ{
  tobj* o[316];//10**0.5
  atomic<uint64_t> n = {0};
  void push_back(tobj* p){o[(int)n]=p;n++;}
  void clear(){n=0;}
  tobj* operator[](int i){return o[i];}
};

class sboard{
  sboard();
  void __init();

  static bool initialized;
  static sboard* pinstance;
  static mutex sblock_;

public:
  accInfo acc;

  VECTOBJ tradeobjs;

  instrument cwstocks_[100];//股票
  instrument options_[100]; //期权
  unordered_map<long,instrument*> oid2cwstock;//orderID->股票,only used by iborder thread
  unordered_map<string,instrument*> sym2inst;

  atomic<uint64_t> equitycount = {0};
  atomic<uint64_t> optioncount = {0};



  static sboard& R();

  //股票
  void __addStockContract(Contract& c_, CWTYPE cwtp);
  void addStockContract(const string& s, CONTRACT_TYPE rt, CWTYPE cwtp);
  void setStockConId(int index, long id);

  const Contract& getStockContract(int index) const;
  Contract* getStockContract(const char* symbol);
  mktinfo* getRTinfo(const char* symbol);
  int getStockSize();
  PCWCW getCWS(const string& master, const string& slave);
  instrument* getCW(const string& symbol);
  instrument* getCW(long oid);
  pair<Contract*, Order*> getCO(long oid);
  Order* getOrder(const string& symbol, long oid);
  vector<Order*> getNonFillOrderPtr();
  vector<Order*> getNonFillOrderPtr(const string&);
  vector<long> getNonFilledOrderID();
  vector<long> getNonFilledOrderID(const string& symbol);

  //期权
  void __addOptionContract(Contract& c_);
  void addOptionContract(const string& s, const string& expiry,
    double strike, char right, CONTRACT_TYPE rt);
  const Contract& getOptionContract(int index) const;
  int getOptionSize();
  void setOptionConId(int index, long id);

  //订单
  //void eraseOrder(long oid);

  void reset();
  void rebuild();
};

//bool rebuildScoreBoard(const vector<tobj*>& vc);
#define SR(x) sboard::R().x

#endif
