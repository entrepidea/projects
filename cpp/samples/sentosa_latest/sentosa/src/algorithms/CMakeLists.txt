project(algorithm)
cmake_minimum_required(VERSION 2.8)

include("../CMakeLists_INC.txt")
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -fPIC")

include_directories(${SRCPATH}/common /usr/include/mysql/ ${VENPATH}/cereal/include ${VENPATH}/talib ${VENPATH}/mlpack/include/mlpack/)

set(LIB_SRC algoFactory.cpp dbb0.cpp singleta.cpp volatility.cpp)

set(LIBRARY_OUTPUT_PATH ${ROOTDIR}/bin/)
add_library(algorithm ${LIB_SRC})
