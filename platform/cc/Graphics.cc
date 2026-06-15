#include "interop.hh"
#include <jni.h>
#include "SkGraphics.h"

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_Graphics__1nInit
  (JNIEnv* env, jclass jclass) {
    SkGraphics::Init();
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_Graphics__1nGetFontCacheLimit
  (JNIEnv* env, jclass jclass) {
    return static_cast<jlong>(SkGraphics::GetFontCacheLimit());
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_Graphics__1nSetFontCacheLimit
  (JNIEnv* env, jclass jclass, jlong bytes) {
    return static_cast<jlong>(SkGraphics::SetFontCacheLimit(static_cast<size_t>(bytes)));
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_Graphics__1nGetFontCacheUsed
  (JNIEnv* env, jclass jclass) {
    return static_cast<jlong>(SkGraphics::GetFontCacheUsed());
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_Graphics__1nGetFontCacheCountUsed
  (JNIEnv* env, jclass jclass) {
    return static_cast<jint>(SkGraphics::GetFontCacheCountUsed());
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_Graphics__1nGetFontCacheCountLimit
  (JNIEnv* env, jclass jclass) {
    return static_cast<jint>(SkGraphics::GetFontCacheCountLimit());
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_Graphics__1nSetFontCacheCountLimit
  (JNIEnv* env, jclass jclass, jint count) {
    return static_cast<jint>(SkGraphics::SetFontCacheCountLimit(static_cast<int>(count)));
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_Graphics__1nPurgeFontCache
  (JNIEnv* env, jclass jclass) {
    SkGraphics::PurgeFontCache();
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_Graphics__1nPurgePinnedFontCache
  (JNIEnv* env, jclass jclass) {
    SkGraphics::PurgePinnedFontCache();
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_Graphics__1nGetResourceCacheTotalBytesUsed
  (JNIEnv* env, jclass jclass) {
    return static_cast<jlong>(SkGraphics::GetResourceCacheTotalBytesUsed());
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_Graphics__1nGetResourceCacheTotalByteLimit
  (JNIEnv* env, jclass jclass) {
    return static_cast<jlong>(SkGraphics::GetResourceCacheTotalByteLimit());
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_Graphics__1nSetResourceCacheTotalByteLimit
  (JNIEnv* env, jclass jclass, jlong newLimit) {
    return static_cast<jlong>(SkGraphics::SetResourceCacheTotalByteLimit(static_cast<size_t>(newLimit)));
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_Graphics__1nPurgeResourceCache
  (JNIEnv* env, jclass jclass) {
    SkGraphics::PurgeResourceCache();
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_Graphics__1nGetResourceCacheSingleAllocationByteLimit
  (JNIEnv* env, jclass jclass) {
    return static_cast<jlong>(SkGraphics::GetResourceCacheSingleAllocationByteLimit());
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_Graphics__1nSetResourceCacheSingleAllocationByteLimit
  (JNIEnv* env, jclass jclass, jlong newLimit) {
    return static_cast<jlong>(SkGraphics::SetResourceCacheSingleAllocationByteLimit(static_cast<size_t>(newLimit)));
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_Graphics__1nPurgeAllCaches
  (JNIEnv* env, jclass jclass) {
    SkGraphics::PurgeAllCaches();
}
