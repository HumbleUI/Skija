cmake_minimum_required(VERSION 3.10)
project(skija LANGUAGES CXX)
set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

if(NOT SKIA_ARCH)
    if("${CMAKE_SYSTEM_PROCESSOR}" STREQUAL "x86_64" OR "${CMAKE_SYSTEM_PROCESSOR}" STREQUAL "AMD64")
        set(SKIA_ARCH "x64")
    elseif("${CMAKE_SYSTEM_PROCESSOR}" STREQUAL "arm64")
        set(SKIA_ARCH "arm64")
    else()
        set(SKIA_ARCH "x86")
    endif()
endif()

if(DEPS_DIR)
    list(APPEND CMAKE_PREFIX_PATH "${DEPS_DIR}")
endif()

# Sources

file(GLOB SOURCES ${CMAKE_SOURCE_DIR}/cc/*.cc)
file(GLOB IMPL_SOURCES ${CMAKE_SOURCE_DIR}/cc/impl/*.cc)
file(GLOB PARAGRAPH_SOURCES ${CMAKE_SOURCE_DIR}/cc/paragraph/*.cc)
file(GLOB SHAPER_SOURCES ${CMAKE_SOURCE_DIR}/cc/shaper/*.cc)
file(GLOB SVG_SOURCES ${CMAKE_SOURCE_DIR}/cc/svg/*.cc)
file(GLOB RESOURCES_SOURCES ${CMAKE_SOURCE_DIR}/cc/resources/*.cc)
file(GLOB SKOTTIE_SOURCES ${CMAKE_SOURCE_DIR}/cc/skottie/*.cc)
file(GLOB SKSG_SOURCES ${CMAKE_SOURCE_DIR}/cc/sksg/*.cc)
file(GLOB TEST_SOURCES ${CMAKE_SOURCE_DIR}/cc/test/*.cc)
add_library(skija SHARED ${SOURCES} ${IMPL_SOURCES} ${PARAGRAPH_SOURCES} ${SHAPER_SOURCES} ${SVG_SOURCES} ${RESOURCES_SOURCES} ${SKOTTIE_SOURCES} ${SKSG_SOURCES} ${TEST_SOURCES})

# Skia
list(APPEND CMAKE_MODULE_PATH ${CMAKE_SOURCE_DIR}/cmake)
include(FindSkia)
include("${SKIA_LIBRARY_DIR}/defines.cmake")

if(CMAKE_BUILD_TYPE STREQUAL "Release")
    add_definitions(-DNDEBUG)
endif()

if(APPLE)
    target_include_directories(skija PRIVATE $ENV{JAVA_HOME}/include $ENV{JAVA_HOME}/include/darwin)

elseif(ANDROID)
    # JNI headers are provided by NDK
    target_compile_options(skija PRIVATE -fno-rtti)

elseif(UNIX)
    target_include_directories(skija PRIVATE $ENV{JAVA_HOME}/include $ENV{JAVA_HOME}/include/linux)
    target_compile_options(skija PRIVATE -Wno-attributes)

elseif(WIN32)
    set(CMAKE_C_COMPILER cl)
    set(CMAKE_CXX_COMPILER cl)

    target_include_directories(skija PRIVATE $ENV{JAVA_HOME}/include $ENV{JAVA_HOME}/include/win32)
    target_link_libraries(skija d3d12 dxgi d3dcompiler)

    # Suppress warnings from Skia headers and size_t conversions
    target_compile_options(skija PRIVATE
        /wd4244  # conversion from 'int32_t' to 'float'
        /wd4267  # conversion from 'size_t' to smaller type
        /wd5030  # attribute not recognized
    )

    # set_property(TARGET skija PROPERTY MSVC_RUNTIME_LIBRARY "MultiThreaded")
    set(CMAKE_CXX_FLAGS_RELEASE "/MT")
    set(CMAKE_CXX_FLAGS_DEBUG "/MTd")
    target_link_options(skija PRIVATE /INCREMENTAL:NO /NODEFAULTLIB:MSVCRT /machine:X64)
endif()

target_link_libraries(skija skottie sksg svg skparagraph skshaper skunicode skresources skia ${FREETYPE_LIBRARIES} ${HARFBUZZ_LIBRARIES})
