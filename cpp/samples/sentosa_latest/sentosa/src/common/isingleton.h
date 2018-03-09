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

#ifndef _ISingleton_H_
#define _ISingleton_H_

#include <memory>
#include <boost/thread.hpp>


/*
这样做有很多优点啦，最显著的比如不需要虚表（节省内存哦）、
多态函数的调用在编译时就确定了（既加快了运行速度，也有利于编译器对代码进行优化）

class MySingleton : public ISingleton < MySingleton >{
public:
  // blah blah

private:
  MySingleton(){
    cout << "Construct MySingleton" << endl;
  }

  ~MySingleton(){
    cout << "Destruct MySingleton" << endl;
  }

  friend ISingleton < MySingleton > ;
  friend class auto_ptr < MySingleton > ;

  MySingleton(const MySingleton&);
  MySingleton& operator =(const MySingleton&);
};
*/
template <typename T>
class ISingleton
{
public:
  static T& getInstance()
  {
    static boost::mutex s_mutex;
    if (s_instance.get() == nullptr)
    {
      boost::mutex::scoped_lock lock(s_mutex);
      if (s_instance.get() == nullptr)
      {
        s_instance.reset(new T());
      }
      // 'lock' will be destructed now. 's_mutex' will be unlocked.
    }
    return *s_instance;
  }

protected:
  ISingleton() { }
  ~ISingleton() { }

  // Use auto_ptr to make sure that the allocated memory for instance
  // will be released when program exits (after main() ends).
  static std::unique_ptr<T> s_instance;

private:
  ISingleton(const ISingleton&);
  ISingleton& operator =(const ISingleton&);
};

template <typename T>
std::unique_ptr<T> ISingleton<T>::s_instance;

#endif
