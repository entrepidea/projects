#!/bin/bash

printf "\n************************************************\n"
printf "\ninstall nanomsg\n"
printf "\n************************************************\n"


sudo apt-get install -y automake

if [ ! -d tmp_nanomsg ]; then
  git clone https://github.com/nanomsg/nanomsg.git tmp_nanomsg
  pushd tmp_nanomsg
  sudo sh autogen.sh
  ./configure
  make
  sudo make install
  popd
else
  echo tmp_nanomsg already existing!
fi
