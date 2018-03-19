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

#include "sharedmem.h"

#include <boost/interprocess/shared_memory_object.hpp>
#include <boost/scoped_ptr.hpp>

using namespace boost::interprocess;

bool create_shared_mem(const char* name){
  boost::scoped_ptr<shared_memory_object> createSharedMemoryOrDie;
  try{
     createSharedMemoryOrDie.reset(
       new shared_memory_object(create_only, name, read_write));
     printf("done\n");
  } catch(...) {
     printf("executable is already running\n");
     return false;
  }
  return true;
  // do your thing here
}
