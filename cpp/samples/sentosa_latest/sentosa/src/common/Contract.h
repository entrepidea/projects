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

#ifndef contract_def
#define contract_def

#include "TagValue.h"

/*
    SAME_POS    = open/close leg value is same as combo
    OPEN_POS    = open
    CLOSE_POS   = close
    UNKNOWN_POS = unknown

*/
enum LegOpenClose { SAME_POS, OPEN_POS, CLOSE_POS, UNKNOWN_POS };

struct ComboLeg
{

   ComboLeg()
      :
      conId(0),
	  ratio(0),
	  openClose(0),
	  shortSaleSlot(0),
	  exemptCode(-1)
   {
   }

   long    conId;
   long    ratio;
   IBString action; //BUY/SELL/SSHORT

   IBString exchange;
   long    openClose; // LegOpenClose enum values

   // for stock legs when doing short sale
   long    shortSaleSlot; // 1 = clearing broker, 2 = third party
   IBString designatedLocation;
   int     exemptCode;

   bool operator==( const ComboLeg &other) const
   {
      return (conId == other.conId &&
         ratio == other.ratio &&
		 openClose == other.openClose &&
		 shortSaleSlot == other.shortSaleSlot &&
		 (Compare(action, other.action) == 0) &&
		 (Compare(exchange, other.exchange) == 0) &&
		 (Compare(designatedLocation, other.designatedLocation) == 0) &&
		 exemptCode == other.exemptCode);
   }
};

struct UnderComp
{
	UnderComp()
		: conId(0)
		, delta(0)
		, price(0)
	{}

	long	conId;
	double	delta;
	double	price;
};

typedef shared_ptr<ComboLeg> ComboLegSPtr;

struct Contract
{
   Contract()
      : conId(0)
	  , strike(0)
	  , includeExpired(false)
	  , comboLegs(nullptr)
	  , underComp(nullptr)
    , isshortable(true)
   {
   }

   long    conId;
   IBString symbol;
   IBString secType;
   IBString expiry;
   double  strike;
   IBString right;
   IBString multiplier;
   IBString exchange;
   IBString primaryExchange; // pick an actual (ie non-aggregate) exchange that the contract trades on.  DO NOT SET TO SMART.
   IBString currency;
   IBString localSymbol;
   bool includeExpired;
   IBString secIdType;		// CUSIP;SEDOL;ISIN;RIC
   IBString secId;

   // COMBOS
   IBString comboLegsDescrip; // received in open order 14 and up for all combos

	// combo legs
   typedef std::vector<ComboLegSPtr> ComboLegList;
   typedef shared_ptr<ComboLegList> ComboLegListSPtr;

   ComboLegListSPtr comboLegs;

   // delta neutral
   UnderComp* underComp;

   bool isshortable; // obsolete - henry -> use RTInfo's shortable

public:

	// Helpers
	static void CloneComboLegs(ComboLegListSPtr& dst, const ComboLegListSPtr& src);
};

struct ContractDetails
{
   ContractDetails()
      : minTick(0)
	  , priceMagnifier(0)
	  , underConId(0)
	  , callable(false)
	  , putable(false)
	  , coupon(0)
	  , convertible(false)
	  , nextOptionPartial(false)
	  , evMultiplier(0)
		
   {
   }

   Contract	summary;
   IBString	marketName;
   IBString	tradingClass;
   double	minTick;
   IBString	orderTypes;
   IBString	validExchanges;
   long		priceMagnifier;
   int		underConId;
   IBString	longName;
   IBString	contractMonth;
   IBString	industry;
   IBString	category;
   IBString	subcategory;
   IBString	timeZoneId;
   IBString	tradingHours;
   IBString	liquidHours;
   IBString evRule;
   double   evMultiplier;

   TagValueListSPtr secIdList;

   // BOND values
   IBString cusip;
   IBString ratings;
   IBString descAppend;
   IBString bondType;
   IBString couponType;
   bool callable;
   bool putable;
   double coupon;
   bool convertible;
   IBString maturity;
   IBString issueDate;
   IBString nextOptionDate;
   IBString nextOptionType;
   bool nextOptionPartial;
   IBString notes;
};

inline void
Contract::CloneComboLegs(ComboLegListSPtr& dst, const ComboLegListSPtr& src)
{
	if (!src.get())
		return;

	dst->reserve(src->size());

	ComboLegList::const_iterator iter = src->begin();
	const ComboLegList::const_iterator iterEnd = src->end();

	for (; iter != iterEnd; ++iter) {
		const ComboLeg* leg = iter->get();
		if (!leg)
			continue;
		dst->push_back(ComboLegSPtr(new ComboLeg(*leg)));
	}
}


#endif
