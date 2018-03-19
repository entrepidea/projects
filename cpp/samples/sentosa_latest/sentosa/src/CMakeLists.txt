project(singapore)
cmake_minimum_required(VERSION 2.8)

set(SRCPATH ${CMAKE_CURRENT_SOURCE_DIR})

include("${SRCPATH}/CMakeLists_INC.txt")

add_subdirectory(${SRCPATH}/common)
add_subdirectory(${SRCPATH}/algorithms)
add_subdirectory(${SRCPATH}/sentosa)

#add_subdirectory(${SRCPATH}/jdiff)
#add_subdirectory(${SRCPATH}/merlion)
#add_subdirectory(${SRCPATH}/histdata)
#add_subdirectory(${SRCPATH}/datasvr)
