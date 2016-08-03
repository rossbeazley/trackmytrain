cmake_minimum_required(VERSION 3.4.1)

add_library(cpp SHARED
            cpp.c)

target_link_libraries(cpp log android)