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

#ifndef execution_def
#define execution_def

#include "IBString.h"

struct Execution
{
   Execution()
   {
      shares = 0;
      price = 0;
      permId = 0;
      clientId = 0;
      orderId = 0;
	  cumQty = 0;
	  avgPrice = 0;
	  evMultiplier = 0;
   }

   // main order fields
   IBString     execId;
   IBString     time;
   IBString     acctNumber;
   IBString     exchange;
   IBString     side;
   int         shares;
   double      price;
   int         permId;
   long        clientId;
   long        orderId;
   int         liquidation;
   int         cumQty;
   double      avgPrice;
   IBString    orderRef;
   IBString	   evRule;
   double      evMultiplier;
};

struct ExecutionFilter
{
	ExecutionFilter()
		: m_clientId(0)
	{
	}

   // Filter fields
   long        m_clientId;
   IBString     m_acctCode;
   IBString     m_time;
   IBString     m_symbol;
   IBString     m_secType;
   IBString     m_exchange;
   IBString     m_side;
};

#endif // execution_def
