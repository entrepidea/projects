#!/bin/bash

printf "\n************************************************\n"
printf "\ninstall google test\n"
printf "\n************************************************\n"

if [ ! -d googletest ] ; then
  git clone https://github.com/google/googletest.git

  pushd googletest/googletest/
  if [ ! -d build ] ; then
    mkdir build
  fi
  pushd build
  cmake ../
  make
  cp -f libgtest.a /usr/lib/x86_64-linux-gnu/
  cp -f libgtest_main.a /usr/lib/x86_64-linux-gnu/
  popd
  cp -Rf include/gtest /usr/include/
  popd

fi
