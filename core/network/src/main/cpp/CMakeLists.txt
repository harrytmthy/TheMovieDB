cmake_minimum_required(VERSION 3.19)

project("TheMovieDB")

add_library(native-lib SHARED jni-keys.cpp)

find_library(log-lib log)

target_link_libraries(native-lib ${log-lib})