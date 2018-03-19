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

#include "scoreboard.h"

sboard* sboard::pinstance = nullptr;
mutex sboard::sblock_;

sboard& sboard::R(){
  if (pinstance == nullptr){
    lock_guard<mutex> g(sblock_);
    if (pinstance == nullptr){
      pinstance = new sboard();
    }
  }
  return *pinstance;
}

sboard::sboard():equitycount(0), optioncount(0){
  rebuild();
}

void sboard::__init(){
  //cwstocks_.resize(100);
  //options_.resize(500);
}

////////////////////////////////////////////////////////////
void sboard::__addStockContract(Contract& c_, CWTYPE cwtp){
  instrument& cw = cwstocks_[equitycount];
  cw.c = c_;
  cw.cwtp = cwtp;
  sym2inst[c_.symbol] = &cw;//not sure if it works for option
  //printf("%s\n",c_.symbol.c_str());
  equitycount++;
}

const Contract& sboard::getStockContract(int index) const{
  return cwstocks_[index].c;
}

Contract* sboard::getStockContract(const char* symbol){
  for (uint64_t i = 0; i < equitycount; ++i){
    if (cwstocks_[i].c.symbol == symbol){
      return &cwstocks_[i].c;
    }
  }
  return nullptr;
}

PCWCW sboard::getCWS(const string& master, const string& slave){
  PCWCW r;
  for (uint64_t i = 0; i < equitycount; ++++i){
    if (cwstocks_[i].c.symbol == master && cwstocks_[i+1].c.symbol == slave){
      return PCWCW(&cwstocks_[i], &cwstocks_[i+1]);
    }
  }
  return r;
}

instrument* sboard::getCW(const string& symbol){
  for (uint64_t i = 0; i < equitycount; ++i){
    if (cwstocks_[i].c.symbol == symbol){
      return &cwstocks_[i];
    }
  }
  return nullptr;
}

//O(N^2)
instrument* sboard::getCW(long oid){
  if (oid2cwstock.find(oid)==oid2cwstock.end()){
    return nullptr;
  }
  return oid2cwstock[oid];
}

/*void sboard::eraseOrder(long oid){
  instrument* pcw = getCW(oid);
  if (pcw){

    for (int i = 0; i < pcw->lorders.count; ++i) {
      if (pcw->lorders.ords[i].orderId == oid && pcw->lorders.ords[i].status != REMOVED) {
        pcw->lorders.ords[i].status = REMOVED;
        break;
      }
    }

    //http://stackoverflow.com/questions/596162/can-you-remove-elements-from-a-stdlist-while-iterating-through-it
  }else{
    printf("[%lu]ACTION from GUI!\n",oid);
    SENTOSALOG;
  }
  oid2cwstock.erase(oid);
  //oid2cwstock[oid] = nullptr;
}*/

vector<Order*> sboard::getNonFillOrderPtr(){
  vector<Order*> v;
  for (uint64_t i = 0; i < equitycount; ++i) {
    LORDS & ref = cwstocks_[i].lorders;
    for (int i = 0; i < ref.count; ++i) {
      switch (ref.ords[i].status) {
      case NEWBORN:
      case PENDING_SUBMIT:
      case PENDING_CANCEL:
      case PRESUBMITTED:
      case SUBMITTED:
      case INACTIVE:
      case API_PENDING: {
        v.push_back(&ref.ords[i]);
      }
        break;
      case CANCELLED:
      case API_CANCELLED:
      case FILLED:
      default:
        break;
      }
    }
  }
  return v;
}

vector<Order*> sboard::getNonFillOrderPtr(const string& symbol){
  vector<Order*> v;
  instrument* pc = getCW(symbol);
  if (pc) {
    LORDS & ref = pc->lorders;
    for (int i = 0; i < ref.count; ++i) {
      switch (ref.ords[i].status) {
      case NEWBORN:
      case PENDING_SUBMIT:
      case PENDING_CANCEL:
      case PRESUBMITTED:
      case SUBMITTED:
      case INACTIVE:
      case API_PENDING: {
        v.push_back(&ref.ords[i]);
      }
        break;
      case CANCELLED:
      case API_CANCELLED:
      case FILLED:
      default:
        break;
      }
    }
  }
  return v;
}

//TODO
vector<long> sboard::getNonFilledOrderID(){
  vector<long> v;
  for (uint64_t i = 0; i < equitycount; ++i) {
    LORDS & ref = cwstocks_[i].lorders;
    for (int i = 0; i < ref.count; ++i) {
      if (!ISREMOVED(ref.ords[i].status)){
        v.push_back(ref.ords[i].orderId);
      }
    }
  }
  return v;
}

vector<long> sboard::getNonFilledOrderID(const string& symbol){
  vector<long> v;
  instrument* pc = getCW(symbol);
  if (pc) {
    LORDS & ref = pc->lorders;
    for (int i = 0; i < ref.count; ++i) {
      if (!ISREMOVED(ref.ords[i].status)){
        v.push_back(ref.ords[i].orderId);
      }
    }
  }
  return v;
}

// O(N^2)
pair<Contract*,Order*> sboard::getCO(long oid){
  pair<Contract*, Order*> pco = {};
  for (uint64_t i = 0; i < equitycount; ++i){
    instrument & ref = cwstocks_[i];
    for(int i=0;i<ref.lorders.count; ++i){
      if (ref.lorders.ords[i].orderId == oid/* && ref.lorders.ords[i].status!=REMOVED*/){
              pco.first = &ref.c;
              pco.second = &ref.lorders.ords[i];
              break;
            }
    }
  }
  return pco;
}

Order* sboard::getOrder(const string& symbol, long oid){
  instrument* p = getCW(symbol);
  return p->getOrder(oid);
}

mktinfo* sboard::getRTinfo(const char* symbol){
  for (uint64_t i = 0; i < equitycount; ++i){
    if (cwstocks_[i].c.symbol == symbol){
      return &cwstocks_[i]._mkdata;
    }
  }
  return nullptr;
}

int sboard::getStockSize(){
  return equitycount;
}

void sboard::setStockConId(int index, long id){
  cwstocks_[index].c.conId = id;
}

void sboard::addStockContract(const string& s, CONTRACT_TYPE rt, CWTYPE cwtp){
  pair<string, string> exchangecurrency = GetExchangeCurrency(s, rt);
  Contract contract;
  contract.symbol = s;
  if (CConfig::R().isFX(s)){
    contract.secType = "CASH";
  }else{
    contract.secType = "STK";
  }
  contract.exchange = exchangecurrency.first;
  contract.currency = exchangecurrency.second;

  __addStockContract(contract, cwtp);
}

void sboard::addOptionContract(const string& s, const string& expiry, double strike, char right, CONTRACT_TYPE rt)
{
  pair<string, string> exchangecurrency = GetExchangeCurrency(s, rt);
  Contract contract;
  contract.symbol = s;
  contract.secType = "OPT";
  contract.exchange = exchangecurrency.first;
  contract.currency = exchangecurrency.second;
  contract.expiry = expiry;
  contract.strike = strike;
  contract.right = right;

  __addOptionContract(contract);
}

////////////////////////////////////////////////////////////
void sboard::__addOptionContract(Contract& c_){
  instrument& cw = options_[optioncount];
  cw.c = c_;
  optioncount++;
}

const Contract& sboard::getOptionContract(int index) const{
  return options_[index].c;
}

int sboard::getOptionSize(){
  return optioncount;
}

void sboard::setOptionConId(int index, long id){
  options_[index].c.conId = id;
}

void sboard::reset(){
  //cwstocks_.clear();
  //options_.clear();
  oid2cwstock.clear();
  equitycount = 0;
  optioncount = 0;
  __init();
}

void sboard::rebuild(){
  reset();
  for (auto& s : CConfig::R().pairs) {
    tobj* pcb = new cband;
    pcb->set(splitv2(s, '|'));
    tradeobjs.push_back(pcb);
  }
  for (auto& s : CConfig::R().singleta) {
    tobj* pcb = new csing;
    pcb->set(VECST{"singleta",s});
    tradeobjs.push_back(pcb);
  }

  //Load historical data and tradeinfo XML
  /*for(int i=0;i<tradeobjs.n;++i){
    tobj* p = tradeobjs[i];
    p->loadhData();
    p->loadTradeInfo("");  //TODO
    msleep(50);
  }*/

  uint64_t j = 0;
  for(int i=0;i<tradeobjs.n;++i){
    tobj* p = tradeobjs[i];
    VECST vs = p->symbols();
    if (vs.size()==2) {  //pair
      addStockContract(vs[0], MKDATA_STOCK, MASTER);
      cwstocks_[j].ptobj = p;
      cwstocks_[j]._mkdata.avgP = p->_tinfo.AVGPRICE();//TODO - to be verify
      cwstocks_[j]._mkdata.pos_ = p->_tinfo.TOTALVOL();
      //p->_tinfo.syms = p->pid();//TODO
      j++;
      addStockContract(vs[1], MKDATA_STOCK, SLAVE);
      cwstocks_[j].ptobj = p;
      cwstocks_[j]._mkdata.avgP = p->_tinfo.AVGPRICE();
      cwstocks_[j]._mkdata.pos_ = p->_tinfo.TOTALVOL();
      j++;
      int ecount = j - 1;
      instrument* cw1 = &sboard::R().cwstocks_[ecount ];
      instrument* cw2 = &sboard::R().cwstocks_[ecount-1];
      cw1->spouse = cw2;
      cw2->spouse = cw1;
    } else if(vs.size()==1) {  //single
      addStockContract(vs[0], MKDATA_STOCK, SINGLE);
      cwstocks_[j].ptobj = p;
      cwstocks_[j]._mkdata.avgP = p->_tinfo.AVGPRICE();
      cwstocks_[j]._mkdata.pos_ = p->_tinfo.TOTALVOL();
      p->_tinfo.syms = p->pid();
      j++;
    }
  }
}
