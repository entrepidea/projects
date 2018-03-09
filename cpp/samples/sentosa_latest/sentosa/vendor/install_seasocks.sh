#!/bin/bash

printf "\n************************************************\n"
printf "\ninstall seasocks\n"
printf "\n************************************************\n"


if [ ! -d tmp_seasocks ]
then
 git clone https://github.com/mattgodbolt/seasocks.git tmp_seasocks
 pushd tmp_seasocks
 mkdir build
 pushd build
 export CXXFLAGS="$CXXFLAGS -fPIC"
 cmake ..
 make
 popd
 sudo cp -f `find . -name libseasocks.a` /usr/lib/x86_64-linux-gnu/
 sudo cp -Rf src/main/c/seasocks /usr/include
 popd
fi
