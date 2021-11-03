#include <jni.h>
#include "SkImageInfo.h"

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_ColorType__1nIsAlwaysOpaque
  (JNIEnv* env, jclass jclass, jint value) {
    return SkColorTypeIsAlwaysOpaque(static_cast<SkColorType>(value));
}
