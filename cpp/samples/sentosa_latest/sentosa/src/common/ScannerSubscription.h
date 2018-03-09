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

#ifndef scanner_def
#define scanner_def

#include <float.h>
#include <limits.h>

#include "IBString.h"

#define UNSET_DOUBLE DBL_MAX
#define UNSET_INTEGER INT_MAX
#define NO_ROW_NUMBER_SPECIFIED -1;

struct ScannerSubscription {
	ScannerSubscription() {
		numberOfRows = NO_ROW_NUMBER_SPECIFIED;
		abovePrice = DBL_MAX;
		belowPrice = DBL_MAX;
		aboveVolume = INT_MAX;
		marketCapAbove = DBL_MAX;
		marketCapBelow = DBL_MAX;
		couponRateAbove = DBL_MAX;
		couponRateBelow = DBL_MAX;
		excludeConvertible = 0;
		averageOptionVolumeAbove = 0;
	}
    int numberOfRows;
    IBString instrument;
    IBString locationCode;
    IBString scanCode;
    double abovePrice;
    double belowPrice;
    int aboveVolume;
    double marketCapAbove;
    double marketCapBelow;
    IBString moodyRatingAbove;
    IBString moodyRatingBelow;
    IBString spRatingAbove;
    IBString spRatingBelow;
    IBString maturityDateAbove;
    IBString maturityDateBelow;
    double couponRateAbove;
    double couponRateBelow;
    int excludeConvertible;
	int averageOptionVolumeAbove;
	IBString scannerSettingPairs;
	IBString stockTypeFilter;
};

#endif
