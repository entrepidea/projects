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

#ifndef _INCLUDE_SENTOSA_GTEST_H_
#define _INCLUDE_SENTOSA_GTEST_H_

#include <gtest/gtest.h>

namespace testing{
 namespace internal{

   bool equald(const double x_, const double y_){
     FloatingPoint<double> x(x_), y(y_);
     return x.AlmostEquals(y);
   }

   bool equalf(const float x_, const float y_){
        FloatingPoint<float> x(x_), y(y_);
        return x.AlmostEquals(y);
      }

 }
}

#define EQUALD(x,y) (::testing::internal::equald(x,y))
#define EQUALF(x,y) (::testing::internal::equalf(x,y))

#endif
