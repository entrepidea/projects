project(sentosa)
cmake_minimum_required(VERSION 2.8)

include("../CMakeLists_INC.txt")

include_directories(${SRCPATH}/common
  /usr/include/mysql/
  ${VENPATH}/cereal/include/
  ${VENPATH}/talib/
  ${VENPATH}/mlpack/include/mlpack/
  ${VENPATH}/seasocks/src/main/c/
  ${VENPATH}/googletest/include/
  ${VENPATH}/rapidjson/include/
  ${SRCPATH}/algorithms/
  /usr/local/include/nanomsg/
  /usr/include/python2.7/)

LINK_DIRECTORIES(${ROOTDIR}/bin)

set(SRC_LIST iborder.cpp mkdata.cpp msgq.cpp threadfunc.cpp api_core.cpp
  tradingsystem.cpp api_nn.cpp api_ws.cpp sentosa.cpp)
set(DYN_LIST iborder.cpp mkdata.cpp msgq.cpp threadfunc.cpp api_core.cpp
  tradingsystem.cpp api_nn.cpp api_ws.cpp merlion.cpp)
set(PYSRC_LIST iborder.cpp mkdata.cpp msgq.cpp threadfunc.cpp api_core.cpp
  tradingsystem.cpp api_nn.cpp api_ws.cpp sentosa_.cpp)

set(EXECUTABLE_OUTPUT_PATH ${ROOTDIR}/bin/)
#set(LIBRARY_OUTPUT_PATH ${ROOTDIR}/bin/)

# 1. generate sentosa_.so
add_library(sentosa_so SHARED ${PYSRC_LIST})
#set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -fPIC")
set_target_properties(sentosa_so PROPERTIES PREFIX "")
SET_TARGET_PROPERTIES(sentosa_so PROPERTIES OUTPUT_NAME sentosa_)
SET_TARGET_PROPERTIES(sentosa_so PROPERTIES LIBRARY_OUTPUT_DIRECTORY ${ROOTDIR}/thirdparty/pysentosa/pysentosa/)

# http://stackoverflow.com/questions/3742090/custom-directory-for-cmake-library-output
# 3. generate libsentosa.so - for melissa
add_library(sentosa-so SHARED ${DYN_LIST})
SET_TARGET_PROPERTIES(sentosa-so PROPERTIES OUTPUT_NAME sentosa)
SET_TARGET_PROPERTIES(sentosa-so PROPERTIES LIBRARY_OUTPUT_DIRECTORY ${ROOTDIR}/bin/)

# 3. generate sentosa
add_executable(sentosa-bin ${SRC_LIST})
SET_TARGET_PROPERTIES(sentosa-bin PROPERTIES OUTPUT_NAME sentosa)

target_link_libraries(sentosa-bin
  common
  #mysqlclient
  pthread
  talib
  boost_program_options
  boost_filesystem
  boost_system
  boost_date_time
  algorithm
  yaml-cpp
  nanomsg
  seasocks
  anl
  curl
  rt
  gflags
  #gtest
)

target_link_libraries(sentosa_so
  common
  #mysqlclient
  pthread
  talib
  boost_program_options
  boost_filesystem
  boost_system
  boost_date_time
  boost_python
  algorithm
  yaml-cpp
  nanomsg
  seasocks
  anl
  curl
  rt
  #gtest
)
