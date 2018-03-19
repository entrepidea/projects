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

#include "EWrapper.h"



const char* TTField[] = {
  "BID_SIZE",
  "BID_PRICE",
  "ASK_PRICE",
  "ASK_SIZE",
  "LAST_PRICE",
  "LAST_SIZE",
  "HIGH",
  "LOW",
  "VOLUME",
  "CLOSE_PRICE",
  "BID_OPTION_COMPUTATION",
  "ASK_OPTION_COMPUTATION",
  "LAST_OPTION_COMPUTATION",
  "MODEL_OPTION",
  "OPEN_TICK",
  "LOW_13_WEEK",
  "HIGH_13_WEEK",
  "LOW_26_WEEK",
  "HIGH_26_WEEK",
  "LOW_52_WEEK",
  "HIGH_52_WEEK",
  "AVG_VOLUME",
  "OPEN_INTEREST",
  "OPTION_HISTORICAL_VOL",
  "OPTION_IMPLIED_VOL",
  "OPTION_BID_EXCH",
  "OPTION_ASK_EXCH",
  "OPTION_CALL_OPEN_INTEREST",
  "OPTION_PUT_OPEN_INTEREST",
  "OPTION_CALL_VOLUME",
  "OPTION_PUT_VOLUME",
  "INDEX_FUTURE_PREMIUM",
  "BID_EXCH",
  "ASK_EXCH",
  "AUCTION_VOLUME",
  "AUCTION_PRICE",
  "AUCTION_IMBALANCE",
  "MARK_PRICE",
  "BID_EFP_COMPUTATION",
  "ASK_EFP_COMPUTATION",
  "LAST_EFP_COMPUTATION",
  "OPEN_EFP_COMPUTATION",
  "HIGH_EFP_COMPUTATION",
  "LOW_EFP_COMPUTATION",
  "CLOSE_EFP_COMPUTATION",
  "LAST_TIMESTAMP",
  "SHORTABLE",
  "FUNDAMENTAL_RATIOS",
  "RT_VOLUME",
  "HALTED",
  "BID_YIELD",
  "ASK_YIELD",
  "LAST_YIELD",
  "CUST_OPTION_COMPUTATION",
  "TRADE_COUNT",
  "TRADE_RATE",
  "VOLUME_RATE",
  "LAST_RTH_TRADE",
  "NOT_SET",
  //////////////////////////////////////////////////////////////////////////
  "SEN_CONTID",
  "SEN_PORTFOLIO",
  "SEN_ACCOUNT",
  "HENRY_5SBAR",
  "SEN_5SWAPSPD"
};