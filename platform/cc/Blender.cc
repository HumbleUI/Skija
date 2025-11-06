#include <jni.h>
#include "SkBlender.h"
#include "SkBlenders.h"

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_Blender__1nMakeWithMode
  (JNIEnv* env, jclass jclass, jint blendModeInt) {
    SkBlendMode blendMode = static_cast<SkBlendMode>(blendModeInt);
    SkBlender* ptr = SkBlender::Mode(blendMode).release();
    return reinterpret_cast<jlong>(ptr);
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_Blender__1nMakeArithmetic
  (JNIEnv* env, jclass jclass, jfloat k1, jfloat k2, jfloat k3, jfloat k4, jboolean enforcePremul) {
    SkBlender* ptr = SkBlenders::Arithmetic(k1, k2, k3, k4, enforcePremul).release();
    return reinterpret_cast<jlong>(ptr);
}
