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

#ifndef ibstring_h__INCLUDED
#define ibstring_h__INCLUDED

#ifndef IB_USE_STD_STRING
#define IB_USE_STD_STRING
#endif // !IB_USE_STD_STRING
#include <string>
typedef std::string IBString;

#include <stdlib.h>

inline bool IsEmpty(const IBString& str)
{
#ifdef IB_USE_STD_STRING
	return str.empty();
#else
	return str.IsEmpty();
#endif
};

inline void Empty(IBString& str)
{
#ifdef IB_USE_STD_STRING
	str.erase();
#else
	str.Empty();
#endif
};

inline bool Compare(IBString str, const char* strToCompare)
{
#ifdef IB_USE_STD_STRING
	return str.compare(strToCompare)!=0;
#else
	return str.CompareNoCase(strToCompare);
#endif
};

inline bool Compare(IBString str, IBString strToCompare)
{
#ifdef IB_USE_STD_STRING
	return str.compare(strToCompare)!=0;
#else
	return str.CompareNoCase(strToCompare);
#endif
};

inline double Atof(IBString str)
{
#ifdef IB_USE_STD_STRING
	return atof(str.c_str());
#else
	return atof(str);
#endif
};

inline int Atoi(IBString str)
{
#ifdef IB_USE_STD_STRING
	return atoi(str.c_str());
#else
	return atoi(str);
#endif
};

#endif


