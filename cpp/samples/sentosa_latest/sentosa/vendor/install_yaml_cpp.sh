#!/bin/bash

printf "\n************************************************\n"
printf "\ninstall yaml cpp\n"
printf "\n************************************************\n"


sudo apt-get install -y python-pip libboost-all-dev git cmake

if [ ! -d tmp_yaml-cpp ];then
  export CXXFLAGS="$CXXFLAGS -fPIC"

  git clone https://github.com/jbeder/yaml-cpp.git tmp_yaml-cpp
  pushd tmp_yaml-cpp/
  mkdir build
  pushd build/
  cmake ..
  make
  sudo cp -f libyaml-cpp.a /usr/lib/x86_64-linux-gnu/
  sudo cp -fr ../include/yaml-cpp/ /usr/include/
  popd
  popd
fi
