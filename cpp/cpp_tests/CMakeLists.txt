cmake_minimum_required(VERSION 3.6)
project(cpp_tests)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES src/main.cpp src/cpp_libs/containers.h src/cpp_libs/basics.h)
add_executable(cpp_tests ${SOURCE_FILES})