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

#ifndef _accountInfo__
#define _accountInfo__

#include <sentosaconfig.h>

class accInfo {
public:
  string AccountCode;
  string AccountType; //INDIVIDUAL
  bool AccountReady; //true
  int DayTradesRemaining; //-1

  string TradingType_S;
  double AvailableFunds_S;
  double EquityWithLoanValue_S;
  double FullAvailableFunds_S;
  double FullExcessLiquidity_S;
  double FullInitMarginReq_S;
  double FullMaintMarginReq_S;
  double GrossPositionValue_S;
  double InitMarginReq_S;
  double MaintMarginReq_S;
  double NetLiquidation_S;
  double PreviousDayEquityWithLoanValue_S;
  double RegTEquity_S;
  double RegTMargin_S;
  double SMA_S;
  double TotalCashValue_S;

  double BuyingPower;
  double CashBalance;
  double NetLiquidationByCurrency;
  double RealizedPnL;
  double UnrealizedPnL;
  double TotalCashBalance;
  double StockMarketValue;

  template<class Archive>
  void serialize(Archive & ar) {
    ar(CEREAL_NVP(AccountCode), CEREAL_NVP(AccountType),
        CEREAL_NVP(AccountReady), CEREAL_NVP(DayTradesRemaining),

        CEREAL_NVP(TradingType_S), CEREAL_NVP(AvailableFunds_S),
        CEREAL_NVP(EquityWithLoanValue_S), CEREAL_NVP(FullAvailableFunds_S),
        CEREAL_NVP(FullExcessLiquidity_S), CEREAL_NVP(FullInitMarginReq_S),
        CEREAL_NVP(FullMaintMarginReq_S), CEREAL_NVP(GrossPositionValue_S),
        CEREAL_NVP(InitMarginReq_S), CEREAL_NVP(MaintMarginReq_S),
        CEREAL_NVP(NetLiquidation_S),
        CEREAL_NVP(PreviousDayEquityWithLoanValue_S), CEREAL_NVP(RegTEquity_S),
        CEREAL_NVP(RegTMargin_S), CEREAL_NVP(SMA_S),
        CEREAL_NVP(TotalCashValue_S),

        CEREAL_NVP(BuyingPower), CEREAL_NVP(CashBalance),
        CEREAL_NVP(NetLiquidationByCurrency), CEREAL_NVP(RealizedPnL),
        CEREAL_NVP(UnrealizedPnL), CEREAL_NVP(TotalCashBalance));
  }

  string O2J(const std::regex* p) {
    stringstream ss;
    {
      cereal::JSONOutputArchive oarchive(ss);
      oarchive(cereal::make_nvp("accinfo", *this));
    }
    string r = ss.str();
    if (p && !r.empty()) {
      string r = regex_replace(r, *p, "$1");
    }
    return r;
  }

  void setvalue(const string &key, const string &val, const string &currency) {
    if (currency == "USD") {
      if (key == "AvailableFunds-S") {
        AvailableFunds_S = atof(val.c_str());
      } else if (key == "EquityWithLoanValue-S") {
        EquityWithLoanValue_S = atof(val.c_str());
      } else if (key == "FullAvailableFunds-S") {
        FullAvailableFunds_S = atof(val.c_str());
      } else if (key == "FullExcessLiquidity-S") {
        FullExcessLiquidity_S = atof(val.c_str());
      } else if (key == "FullInitMarginReq-S") {
        FullInitMarginReq_S = atof(val.c_str());
      } else if (key == "FullMaintMarginReq-S") {
        FullMaintMarginReq_S = atof(val.c_str());
      } else if (key == "GrossPositionValue-S") {
        GrossPositionValue_S = atof(val.c_str());
      } else if (key == "InitMarginReq-S") {
        InitMarginReq_S = atof(val.c_str());
      } else if (key == "MaintMarginReq-S") {
        MaintMarginReq_S = atof(val.c_str());
      } else if (key == "NetLiquidation-S") {
        NetLiquidation_S = atof(val.c_str());
      } else if (key == "PreviousDayEquityWithLoanValue-S") {
        PreviousDayEquityWithLoanValue_S = atof(val.c_str());
      } else if (key == "RegTEquity-S") {
        RegTEquity_S = atof(val.c_str());
      } else if (key == "RegTMargin-S") {
        RegTMargin_S = atof(val.c_str());
      } else if (key == "SMA-S") {
        SMA_S = atof(val.c_str());
      } else if (key == "TotalCashValue-S") {
        TotalCashValue_S = atof(val.c_str());
      } else if (key == "TradingType-S") {
        TradingType_S = val;
      }

      else if (key == "BuyingPower") {
        BuyingPower = atof(val.c_str());
      } else if (key == "CashBalance") {
        CashBalance = atof(val.c_str());
      } else if (key == "NetLiquidationByCurrency") {
        NetLiquidationByCurrency = atof(val.c_str());
      } else if (key == "RealizedPnL") {
        RealizedPnL = atof(val.c_str());
      } else if (key == "UnrealizedPnL") {
        UnrealizedPnL = atof(val.c_str());
      } else if (key == "TotalCashBalance") {
        TotalCashBalance = atof(val.c_str());
      } else if (key == "StockMarketValue") {
        StockMarketValue = atof(val.c_str());
      }
    } else if (key == "AccountCode") {
      AccountCode = val;
    } else if (key == "AccountType") {
      AccountType = val;
    } else if (key == "AccountReady") {
      AccountReady = (val == "true");
    } else if (key == "DayTradesRemaining") {
      DayTradesRemaining = atoi(val.c_str());
    }
  }

};

#endif /* end of include guard:  */
