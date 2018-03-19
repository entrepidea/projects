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

#include "sentosaID.h"

using namespace std;

unique_ptr<CsentosaID> CsentosaID::m_instance;
once_flag CsentosaID::m_onceFlag;

CsentosaID& CsentosaID::R()
{
  std::call_once(m_onceFlag,
    [] {
    m_instance.reset(new CsentosaID);
  });
  return *m_instance.get();
}
