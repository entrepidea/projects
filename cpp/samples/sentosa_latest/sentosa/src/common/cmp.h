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

#ifndef COMMON_CMP_H_
#define COMMON_CMP_H_

#include <boost/test/floating_point_comparison.hpp>

namespace btt = boost::test_tools;

#define FPEQ1(x,y)  btt::check_is_close((x-y), .0, btt::percent_tolerance(1e-10));
#define FPEQ2(x,y) (abs(x-y)<1e-12)



#endif /* COMMON_CMP_H_ */
