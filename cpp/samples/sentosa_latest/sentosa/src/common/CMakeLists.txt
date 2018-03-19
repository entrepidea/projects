project(common)
cmake_minimum_required(VERSION 2.8)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -fPIC")

include("../CMakeLists_INC.txt")

include_directories(${SRCPATH}/common /usr/include/mysql/ ${VENPATH}/cereal/include
  ${VENPATH}/talib ${VENPATH}/mlpack/include/mlpack/ /usr/local/include/nanomsg/
  ${VENPATH}/rapidjson/include
  ${VENPATH}/googletest/include
)

set(LIB_SRC algoengine.cpp cband.cpp mq.cpp
EClientSocketBase.cpp EWrapper.cpp getRealTime.cpp
idxData.cpp
myData.cpp
params.cpp scoreboard.cpp
tradeinfo.cpp bstTime.cpp cwrapper.cpp
datasource.cpp EPosixClientSocket.cpp gconfig.cpp
hook.cpp log.cpp myTA.cpp portfolio.cpp sentosaID.cpp
strptime.cpp util.cpp conf_yaml.cpp sentosadb.cpp
JsonDiff.cpp o2j.cpp httpclient.cpp
heartbeat.cpp
#sharedmem.cpp
versiondef.cpp
)

message(${ROOTDIR}/bin)
message(${PROJECT_BINARY_DIR})

set(LIBRARY_OUTPUT_PATH ${ROOTDIR}/bin/)
add_library(common ${LIB_SRC})
