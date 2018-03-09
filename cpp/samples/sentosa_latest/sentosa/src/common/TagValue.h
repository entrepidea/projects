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

#ifndef tagvalue_def
#define tagvalue_def

//#include "shared_ptr.h"
#include <memory>
#include <vector>
#include "IBString.h"

using namespace std;

struct TagValue
{
	TagValue() {}
	TagValue(const IBString& p_tag, const IBString& p_value)
		: tag(p_tag), value(p_value)
	{}

	IBString tag;
	IBString value;
};

typedef shared_ptr<TagValue> TagValueSPtr;
typedef vector<TagValueSPtr> TagValueList;
typedef shared_ptr<TagValueList> TagValueListSPtr;

#endif

