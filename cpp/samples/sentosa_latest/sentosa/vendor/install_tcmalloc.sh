#!/bin/bash

printf "\n************************************************\n"
printf "\ninstall tcmalloc\n"
printf "\n************************************************\n"


sudo apt-get install -y libunwind-dev automake

if [ ! -d tmp_gperftools ]; then
  git clone https://github.com/gperftools/gperftools.git tmp_gperftools
  pushd tmp_gperftools
  ./autogen.sh
  ./configure && make && sudo make install
  popd
  #rm -fr tmp_gperftools
  #echo compile with gcc:
  #echo -ltcmalloc -fno-builtin-malloc -fno-builtin-calloc -fno-builtin-realloc -fno-builtin-free
else
  echo tmp_gperftools already existing!
fi
