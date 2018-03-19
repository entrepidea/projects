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

#ifndef common_defs_h_INCLUDED
#define common_defs_h_INCLUDED

typedef long TickerId;
typedef long OrderId;

enum faDataType { GROUPS=1, PROFILES, ALIASES } ;

inline const char* faDataTypeStr ( faDataType pFaDataType )
{
	switch (pFaDataType) {
		case GROUPS:
			return "GROUPS" ;
			break ;
		case PROFILES:
			return "PROFILES" ;
			break ;
		case ALIASES:
			return "ALIASES" ;
			break ;
	}
	return 0 ;
}

enum MarketDataType { 
	REALTIME = 1, 
	FROZEN = 2
};

#endif /* common_defs_h_INCLUDED */
