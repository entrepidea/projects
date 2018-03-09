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

#include "algoFactory.h"
#include <boost/algorithm/string.hpp>

unique_ptr<AlgoEngine> algoFac(const string& _algo){
  /*if (boost::iequals(_algo, "ta")){
    return make_unique<taalgo>();
  }*/
  if(boost::iequals(_algo, "singleta")){
    return make_unique<singletaalgo>();
  }/*
  if (boost::iequals(_algo, "doubleBB")){
    return make_unique<DoubleBBand>();
  }*/
  if (boost::iequals(_algo, "DBB0")){
    return make_unique<DBB0>();
  }/*
  if (boost::iequals(_algo, "HTTL")){
    return make_unique<sbb>();
  }
  if (boost::iequals(_algo, "MDT")){
    return make_unique<midDayTrend>();
  }*/
  return nullptr;
}
