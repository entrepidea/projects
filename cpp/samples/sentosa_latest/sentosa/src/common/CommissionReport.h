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

#ifndef commissionreport_def
#define commissionreport_def

#include "IBString.h"

struct CommissionReport
{
	CommissionReport()
	{
		commission = 0;
		realizedPNL = 0;
		yield = 0;
		yieldRedemptionDate = 0;
	}

	// commission report fields
	IBString	execId;
	double		commission;
	IBString	currency;
	double		realizedPNL;
	double		yield;
	int			yieldRedemptionDate; // YYYYMMDD format
};

#endif // commissionreport_def
