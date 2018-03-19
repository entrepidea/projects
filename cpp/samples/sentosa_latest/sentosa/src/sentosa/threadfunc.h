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

#ifndef SENTOSA_THREADFUNC_H
#define SENTOSA_THREADFUNC_H

#include <sentosaconfig.h>
#include <memory>
#include "mkdata.h"
#include "iborder.h"

void Thread_MKDepth(void* p_);
void Thread_MKDataTick(std::shared_ptr<mkdata>, int);
void Thread_MKDataBar(void *p_);
void Thread_UpdateSboard();
void Thread_OMS_ib(std::shared_ptr<iborder>, int);
void Thread_OMS_algo(std::shared_ptr<iborder>);
void Thread_Record();
void Thread_Replay(const std::string& filetoreplay);
void Thread_Status(std::shared_ptr<iborder>);

#endif
