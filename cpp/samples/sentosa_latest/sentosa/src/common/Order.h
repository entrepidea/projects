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

#ifndef order_def
#define order_def

#include "TagValue.h"

#include <float.h>
#include <limits.h>
#include <sentosaID.h>
#include <atomic>
#include <regex>

#define UNSET_DOUBLE DBL_MAX
#define UNSET_INTEGER INT_MAX

enum Origin { CUSTOMER,
              FIRM,
              UNKNOWN };

enum AuctionStrategy { AUCTION_UNSET = 0,
                       AUCTION_MATCH = 1,
                       AUCTION_IMPROVEMENT = 2,
                       AUCTION_TRANSPARENT = 3 };

struct OrderComboLeg
{
	OrderComboLeg()
	{
		price = UNSET_DOUBLE;
	}

	double price;

	bool operator==( const OrderComboLeg &other) const
	{
		return (price == other.price);
	}
};

typedef shared_ptr<OrderComboLeg> OrderComboLegSPtr;

/*The order status. Possible values include:

    PendingSubmit - indicates that you have transmitted the order, but have not yet received confirmation that it has been accepted by the order destination. NOTE: This order status is not sent by TWS and should be explicitly set by the API developer when an order is submitted.
    PendingCancel - indicates that you have sent a request to cancel the order but have not yet received cancel confirmation from the order destination. At this point, your order is not confirmed canceled. You may still receive an execution while your cancellation request is pending. NOTE: This order status is not sent by TWS and should be explicitly set by the API developer when an order is canceled.
    PreSubmitted - indicates that a simulated order type has been accepted by the IB system and that this order has yet to be elected. The order is held in the IB system until the election criteria are met. At that time the order is transmitted to the order destination as specified .
    Submitted - indicates that your order has been accepted at the order destination and is working.
    Cancelled - indicates that the balance of your order has been confirmed canceled by the IB system. This could occur unexpectedly when IB or the destination has rejected your order.
    Filled - indicates that the order has been completely filled.
    Inactive - indicates that the order has been accepted by the system (simulated orders) or an exchange (native orders) but that currently the order is inactive due to system, exchange or other issues.

    A customer reported an exception where ApiCancelled was sent.  I was not able to reproduce it but IB does list it in the change notes for 9.6.

switch(status){
        case NEWBORN:break;
        case PENDING_SUBMIT:break;
        case PENDING_CANCEL:break;
        case PRESUBMITTED:break;
        case SUBMITTED:break;
        case CANCELLED:break;
        case INACTIVE:break;
        case API_PENDING:break;
        case API_CANCELLED:break;
        case FILLED:break;
}
*/
enum ORDERSTATUS{
  NEWBORN = 0,
  PENDING_SUBMIT,
  PENDING_CANCEL,
  PRESUBMITTED,
  SUBMITTED,
  API_PENDING,

  INACTIVE,

  FILLED,
  API_CANCELLED,
  CANCELLED
};

#define ISREMOVED(x) (x>INACTIVE)

struct instrument;
struct Order
{
	Order()
	{
		// order identifier
		orderId  = 0;
		clientId = 0;
		permId   = 0;

		// main order fields
		totalQuantity = 0;
		lmtPrice      = UNSET_DOUBLE;
		auxPrice      = UNSET_DOUBLE;

		// extended order fields
		ocaType        = 0;
		transmit       = true;
		parentId       = 0;
		blockOrder     = false;
		sweepToFill    = false;
		displaySize    = 0;
		triggerMethod  = 0;
		outsideRth     = false;
		hidden         = false;
		allOrNone      = false;
		minQty         = UNSET_INTEGER;
		percentOffset  = UNSET_DOUBLE;
		overridePercentageConstraints = false;
		trailStopPrice = UNSET_DOUBLE;
		trailingPercent = UNSET_DOUBLE;

		// institutional (ie non-cleared) only
		openClose     = "O";
		origin        = CUSTOMER;
		shortSaleSlot = 0;
		exemptCode    = -1;

		// SMART routing only
		discretionaryAmt = 0;
		eTradeOnly       = true;
		firmQuoteOnly    = true;
		nbboPriceCap     = UNSET_DOUBLE;
		optOutSmartRouting = false;

		// BOX exchange orders only
		auctionStrategy = AUCTION_UNSET;
		startingPrice   = UNSET_DOUBLE;
		stockRefPrice   = UNSET_DOUBLE;
		delta           = UNSET_DOUBLE;

		// pegged to stock and VOL orders only
		stockRangeLower = UNSET_DOUBLE;
		stockRangeUpper = UNSET_DOUBLE;

		// VOLATILITY ORDERS ONLY
		volatility            = UNSET_DOUBLE;
		volatilityType        = UNSET_INTEGER;     // 1=daily, 2=annual
		deltaNeutralOrderType = "";
		deltaNeutralAuxPrice  = UNSET_DOUBLE;
		deltaNeutralConId     = 0;
		deltaNeutralSettlingFirm = "";
		deltaNeutralClearingAccount = "";
		deltaNeutralClearingIntent = "";
		continuousUpdate      = false;
		referencePriceType    = UNSET_INTEGER; // 1=Average, 2 = BidOrAsk

		// COMBO ORDERS ONLY
		basisPoints     = UNSET_DOUBLE;  // EFP orders only
		basisPointsType = UNSET_INTEGER; // EFP orders only

		// SCALE ORDERS ONLY
		scaleInitLevelSize  = UNSET_INTEGER;
		scaleSubsLevelSize  = UNSET_INTEGER;
		scalePriceIncrement = UNSET_DOUBLE;
		scalePriceAdjustValue = UNSET_DOUBLE;
		scalePriceAdjustInterval = UNSET_INTEGER;
		scaleProfitOffset = UNSET_DOUBLE;
		scaleAutoReset = false;
		scaleInitPosition = UNSET_INTEGER;
		scaleInitFillQty = UNSET_INTEGER;
		scaleRandomPercent = false;

		// What-if
		whatIf = false;

		// Not Held
		notHeld = false;

    //henry
    //////////////////////////////////////////////////////////////////////////
    status = NEWBORN;
    pcw = nullptr;
    sentosaid = CsentosaID::R().get();
    orignalPrice = 0;
    allowedMove = 0;
    distance = 0;
    createTime = time(nullptr);
	}

  //henry
  //////////////////////////////////////////////////////////////////////////
	instrument* pcw;
	string sym;
  ORDERSTATUS status;
  uint64_t sentosaid;
  double orignalPrice;
  double allowedMove;
  //下buy order@1,现在bid变成了2,distance是2-1=1
  double distance;
  time_t createTime;
  long filled=0;
  long remaining=0;
  double lastFillPrice={0.0f};
  double avgFillPrice={0.0f};
  template<class Archive>
  void serialize(Archive & ar) {
    ar(cereal::make_nvp("id",orderId),
        cereal::make_nvp("sym",sym),
        cereal::make_nvp("ac",action),
        cereal::make_nvp("tq",totalQuantity),
        cereal::make_nvp("t",orderType),
        cereal::make_nvp("lp",lmtPrice),
        cereal::make_nvp("s",status),
        cereal::make_nvp("a",allowedMove),
        cereal::make_nvp("d",distance),
        cereal::make_nvp("afp",avgFillPrice),
        cereal::make_nvp("lfp",lastFillPrice),
        cereal::make_nvp("f",filled),
        cereal::make_nvp("r",remaining),
        cereal::make_nvp("c",createTime)
        );
  }
  string O2J(const regex& p){
    stringstream ss;
    {
      cereal::JSONOutputArchive oarchive(ss);
      oarchive(cereal::make_nvp("order", *this));
    }
    return regex_replace(ss.str(), p, "$1");
  }
  //////////////////////////////////////////////////////////////////////////

	// order identifier
	long     orderId;
	long     clientId;
	long     permId;

	// main order fields
	IBString action;
	long totalQuantity;// supposed to be positive
	IBString orderType;
	double lmtPrice;
	double   auxPrice;

	// extended order fields
	IBString tif;           // "Time in Force" - DAY, GTC, etc.
	IBString ocaGroup;      // one cancels all group name
	int      ocaType;       // 1 = CANCEL_WITH_BLOCK, 2 = REDUCE_WITH_BLOCK, 3 = REDUCE_NON_BLOCK
	IBString orderRef;      // order reference
	bool     transmit;      // if false, order will be created but not transmited
	long     parentId;      // Parent order Id, to associate Auto STP or TRAIL orders with the original order.
	bool     blockOrder;
	bool     sweepToFill;
	int      displaySize;
	int      triggerMethod; // 0=Default, 1=Double_Bid_Ask, 2=Last, 3=Double_Last, 4=Bid_Ask, 7=Last_or_Bid_Ask, 8=Mid-point
	bool     outsideRth;
	bool     hidden;
	IBString goodAfterTime;    // Format: 20060505 08:00:00 {time zone}
	IBString goodTillDate;     // Format: 20060505 08:00:00 {time zone}
	IBString rule80A; // Individual = 'I', Agency = 'A', AgentOtherMember = 'W', IndividualPTIA = 'J', AgencyPTIA = 'U', AgentOtherMemberPTIA = 'M', IndividualPT = 'K', AgencyPT = 'Y', AgentOtherMemberPT = 'N'
	bool     allOrNone;
	int      minQty;
	double   percentOffset; // REL orders only
	bool     overridePercentageConstraints;
	double   trailStopPrice; // TRAILLIMIT orders only
	double   trailingPercent;

	// financial advisors only
	IBString faGroup;
	IBString faProfile;
	IBString faMethod;
	IBString faPercentage;

	// institutional (ie non-cleared) only
	IBString openClose; // O=Open, C=Close
	Origin   origin;    // 0=Customer, 1=Firm
	int      shortSaleSlot; // 1 if you hold the shares, 2 if they will be delivered from elsewhere.  Only for Action="SSHORT
	IBString designatedLocation; // set when slot=2 only.
	int      exemptCode;

	// SMART routing only
	double   discretionaryAmt;
	bool     eTradeOnly;
	bool     firmQuoteOnly;
	double   nbboPriceCap;
	bool     optOutSmartRouting;

	// BOX exchange orders only
	int      auctionStrategy; // AUCTION_MATCH, AUCTION_IMPROVEMENT, AUCTION_TRANSPARENT
	double   startingPrice;
	double   stockRefPrice;
	double   delta;

	// pegged to stock and VOL orders only
	double   stockRangeLower;
	double   stockRangeUpper;

	// VOLATILITY ORDERS ONLY
	double   volatility;
	int      volatilityType;     // 1=daily, 2=annual
	IBString deltaNeutralOrderType;
	double   deltaNeutralAuxPrice;
	long     deltaNeutralConId;
	IBString deltaNeutralSettlingFirm;
	IBString deltaNeutralClearingAccount;
	IBString deltaNeutralClearingIntent;
	bool     continuousUpdate;
	int      referencePriceType; // 1=Average, 2 = BidOrAsk

	// COMBO ORDERS ONLY
	double   basisPoints;      // EFP orders only
	int      basisPointsType;  // EFP orders only

	// SCALE ORDERS ONLY
	int      scaleInitLevelSize;
	int      scaleSubsLevelSize;
	double   scalePriceIncrement;
	double   scalePriceAdjustValue;
	int      scalePriceAdjustInterval;
	double   scaleProfitOffset;
	bool     scaleAutoReset;
	int      scaleInitPosition;
	int      scaleInitFillQty;
	bool     scaleRandomPercent;

	// HEDGE ORDERS
	IBString hedgeType;  // 'D' - delta, 'B' - beta, 'F' - FX, 'P' - pair
	IBString hedgeParam; // 'beta=X' value for beta hedge, 'ratio=Y' for pair hedge

	// Clearing info
	IBString account; // IB account
	IBString settlingFirm;
	IBString clearingAccount; // True beneficiary of the order
	IBString clearingIntent; // "" (Default), "IB", "Away", "PTA" (PostTrade)

	// ALGO ORDERS ONLY
	IBString algoStrategy;

	TagValueListSPtr algoParams;
	TagValueListSPtr smartComboRoutingParams;

	// What-if
	bool     whatIf;

	// Not Held
	bool     notHeld;

	// order combo legs
	typedef std::vector<OrderComboLegSPtr> OrderComboLegList;
	typedef shared_ptr<OrderComboLegList> OrderComboLegListSPtr;

	OrderComboLegListSPtr orderComboLegs;

public:

	// Helpers
	static void CloneOrderComboLegs(OrderComboLegListSPtr& dst, const OrderComboLegListSPtr& src);
};

inline void
Order::CloneOrderComboLegs(OrderComboLegListSPtr& dst, const OrderComboLegListSPtr& src)
{
	if (!src.get())
		return;

	dst->reserve(src->size());

	OrderComboLegList::const_iterator iter = src->begin();
	const OrderComboLegList::const_iterator iterEnd = src->end();

	for (; iter != iterEnd; ++iter) {
		const OrderComboLeg* leg = iter->get();
		if (!leg)
			continue;
		dst->push_back(OrderComboLegSPtr(new OrderComboLeg(*leg)));
	}
}

#endif
