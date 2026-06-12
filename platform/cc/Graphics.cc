#include "interop.hh"
#include <jni.h>
#include "SkGraphics.h"

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_Graphics__1nInit
  (JNIEnv* env, jclass jclass) {
    SkGraphics::Init();
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_Graphics__1nPurgeAllCaches
  (JNIEnv* env, jclass jclass) {
    SkGraphics::PurgeAllCaches();
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_Graphics__1nPurgeFontCache
  (JNIEnv* env, jclass jclass) {
    SkGraphics::PurgeFontCache();
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_Graphics__1nPurgeResourceCache
  (JNIEnv* env, jclass jclass) {
    SkGraphics::PurgeResourceCache();
}
