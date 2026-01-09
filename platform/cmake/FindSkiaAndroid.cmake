set(SKIA_DIR "" CACHE PATH "Skia source code directory")
if(NOT SKIA_DIR)
  set(SKIA_LIBRARY_DIR "" CACHE PATH "Skia library directory (where libskia.a is located)")
else()
  if(CMAKE_BUILD_TYPE STREQUAL Debug)
    set(SKIA_LIBRARY_DIR "${SKIA_DIR}/out/Debug-${SKIA_ARCH}" CACHE PATH "Skia library directory" FORCE)
  else()
    set(SKIA_LIBRARY_DIR "${SKIA_DIR}/out/Release-${SKIA_ARCH}" CACHE PATH "Skia library directory" FORCE)
  endif()
endif()

# Skia library
find_library(SKIA_LIBRARY NAMES skia HINTS "${SKIA_LIBRARY_DIR}" NO_CMAKE_FIND_ROOT_PATH)

# Android libraries
find_library(SKIA_OPENGL_LIBRARY GLESv2)
find_library(SKIA_EGL_LIBRARY EGL)
find_library(SKIA_ANDROID_LIBRARY android)
find_library(SKIA_LOG_LIBRARY log)

# SkUnicode
find_library(SKUNICODE_CORE_LIBRARY NAMES skunicode_core HINTS "${SKIA_LIBRARY_DIR}" NO_CMAKE_FIND_ROOT_PATH)
find_library(SKUNICODE_ICU_LIBRARY NAMES skunicode_icu HINTS "${SKIA_LIBRARY_DIR}" NO_CMAKE_FIND_ROOT_PATH)
add_library(skunicode INTERFACE)
if (SKUNICODE_CORE_LIBRARY)
    if (SKUNICODE_ICU_LIBRARY)
        target_link_libraries(skunicode INTERFACE ${SKUNICODE_CORE_LIBRARY} ${SKUNICODE_ICU_LIBRARY})
    else()
        target_link_libraries(skunicode INTERFACE ${SKUNICODE_CORE_LIBRARY})
    endif()
else()
    find_library(SKUNICODE_LIBRARY NAMES skunicode HINTS "${SKIA_LIBRARY_DIR}" NO_CMAKE_FIND_ROOT_PATH)
    target_link_libraries(skunicode INTERFACE ${SKUNICODE_LIBRARY})
endif()

# SkShaper module + freetype + harfbuzz
find_library(SKSHAPER_LIBRARY NAMES skshaper HINTS "${SKIA_LIBRARY_DIR}" NO_CMAKE_FIND_ROOT_PATH)

# On Android, Freetype and Harfbuzz are typically built as static libs by Skia
find_library(FREETYPE_LIBRARY NAMES freetype2 freetype HINTS "${SKIA_LIBRARY_DIR}" NO_CMAKE_FIND_ROOT_PATH)
set(FREETYPE_LIBRARIES ${FREETYPE_LIBRARY})

find_library(HARFBUZZ_LIBRARY NAMES harfbuzz HINTS "${SKIA_LIBRARY_DIR}" NO_CMAKE_FIND_ROOT_PATH)
set(HARFBUZZ_LIBRARIES ${HARFBUZZ_LIBRARY})

add_library(skshaper INTERFACE)
target_link_libraries(skshaper INTERFACE ${SKSHAPER_LIBRARY})

# SkParagraph
find_library(SKPARAGRAPH_LIBRARY NAMES skparagraph HINTS "${SKIA_LIBRARY_DIR}" NO_CMAKE_FIND_ROOT_PATH)
add_library(skparagraph INTERFACE)
target_link_libraries(skparagraph INTERFACE ${SKPARAGRAPH_LIBRARY})

# SVG
find_library(SKIA_SVG_LIBRARY NAMES svg HINTS "${SKIA_LIBRARY_DIR}" NO_CMAKE_FIND_ROOT_PATH)
add_library(svg INTERFACE)
target_link_libraries(svg INTERFACE ${SKIA_SVG_LIBRARY})

# SkResources
find_library(SKIA_SKRESOURCES_LIBRARY NAMES skresources HINTS "${SKIA_LIBRARY_DIR}" NO_CMAKE_FIND_ROOT_PATH)
add_library(skresources INTERFACE)
target_link_libraries(skresources INTERFACE ${SKIA_SKRESOURCES_LIBRARY})

# SKOTTIE
find_library(SKIA_SKOTTIE_LIBRARY NAMES skottie HINTS "${SKIA_LIBRARY_DIR}" NO_CMAKE_FIND_ROOT_PATH)
find_library(SKIA_JSONREADER_LIBRARY NAMES jsonreader HINTS "${SKIA_LIBRARY_DIR}" NO_CMAKE_FIND_ROOT_PATH)
add_library(skottie INTERFACE)
target_link_libraries(skottie INTERFACE ${SKIA_SKOTTIE_LIBRARY})
if(SKIA_JSONREADER_LIBRARY)
    target_link_libraries(skottie INTERFACE ${SKIA_JSONREADER_LIBRARY})
endif()

# SKSG
find_library(SKIA_SKSG_LIBRARY NAMES sksg HINTS "${SKIA_LIBRARY_DIR}" NO_CMAKE_FIND_ROOT_PATH)
add_library(sksg INTERFACE)
target_link_libraries(sksg INTERFACE ${SKIA_SKSG_LIBRARY})

set(SKIA_LIBRARIES
  ${SKIA_LIBRARY}
  ${SKIA_OPENGL_LIBRARY}
  ${SKIA_EGL_LIBRARY}
  ${SKIA_ANDROID_LIBRARY}
  ${SKIA_LOG_LIBRARY}
  CACHE INTERNAL "Skia libraries")

add_library(skia INTERFACE)
target_include_directories(skia INTERFACE
  "${SKIA_DIR}"
  "${SKIA_DIR}/include/config"
  "${SKIA_DIR}/include/core"
  "${SKIA_DIR}/include/encode"
  "${SKIA_DIR}/include/pathops"
  "${SKIA_DIR}/include/svg"
  "${SKIA_DIR}/include/utils"
  "${SKIA_DIR}/include/codec"
  "${SKIA_DIR}/include/effects"
  "${SKIA_DIR}/third_party/externals/angle2/include"
  "${SKIA_DIR}/include/third_party/skcms"
  "${SKIA_DIR}/modules/skunicode/include"
  "${SKIA_DIR}/modules/skshaper/include"
  "${SKIA_DIR}/modules/skparagraph/include"
  "${SKIA_DIR}/third_party/externals/freetype/include"
  "${SKIA_DIR}/third_party/externals/harfbuzz/src"
  "${SKIA_DIR}/modules/svg/include"
  "${SKIA_DIR}/modules/skottie/include"
  "${SKIA_DIR}/modules/sksg/include"
  "${SKIA_DIR}/third_party/externals/icu/source/common"
  "${SKIA_DIR}/third_party/icu"
  "${SKIA_DIR}/modules/skresources/include"
)

target_link_libraries(skia INTERFACE ${SKIA_LIBRARIES})
target_compile_definitions(skia INTERFACE
  SK_INTERNAL
  SK_GAMMA_SRGB
  SK_GAMMA_APPLY_TO_A8
  SK_SCALAR_TO_FLOAT_EXCLUDED
  SK_ALLOW_STATIC_GLOBAL_INITIALIZERS=1
  SK_SUPPORT_OPENCL=0
  SK_FORCE_DISTANCE_FIELD_TEXT=0
  SK_SUPPORT_GPU=1
  SK_BUILD_FOR_ANDROID
)