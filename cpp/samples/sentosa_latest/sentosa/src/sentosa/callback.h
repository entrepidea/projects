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

#ifndef SENTOSA_CALLBACK_H
#define SENTOSA_CALLBACK_H

#include <iostream>
#include <functional>
#include <stdexcept>
#include <typeinfo>
#include <string>
#include <unordered_map>

template<typename Function>
struct function_traits: public function_traits<decltype(&Function::operator())> {
};

template<typename ClassType, typename ReturnType, typename ... Args>
struct function_traits<ReturnType (ClassType::*)(Args...) const> {
  typedef ReturnType (*pointer)(Args...);
  typedef std::function<ReturnType(Args...)> function;
};

template<typename Function>
typename function_traits<Function>::pointer to_function_pointer(
    Function& lambda) {
  return static_cast<typename function_traits<Function>::pointer>(lambda);
}

template<typename Function>
typename function_traits<Function>::function to_function(Function& lambda) {
  return static_cast<typename function_traits<Function>::function>(lambda);
}

class callbacks final {

  struct callback final {
    void* function;
    const std::type_info* signature;
  };

public:
  callbacks(void) {
    _callbacks.clear();
  }

  ~callbacks(void) {
    for (auto entry : _callbacks) {
      delete static_cast<std::function<void()>*>(entry.second.function);
    }
  }

  template<typename Function>
  void add(const std::string& name, Function lambda) {
    if (_callbacks.find(name) != _callbacks.end()) {
      throw std::invalid_argument("the callback already exists");
    }

    auto function = new decltype(to_function(lambda))(to_function(lambda));
    _callbacks[name].function = static_cast<void*>(function);
    _callbacks[name].signature = &typeid(function);
  }

  void remove(std::string name) {
    if (_callbacks.find(name) == _callbacks.end()) {
      return;
    }
    delete static_cast<std::function<void()>*>(_callbacks[name].function);
  }

  template<typename ...Args>
  void call(const std::string& name, Args ... args) {
    auto callback = _callbacks.at(name);
    auto function =
        static_cast<std::function<void(Args...)>*>(callback.function);

    if (typeid(function) != *(callback.signature)) {
      throw std::bad_typeid();
    }

    (*function)(args...);
  }

private:
  std::unordered_map<std::string, callback> _callbacks;
};

#endif
