#include <jni.h>
#include "include/core/SkColor.h"
#include "interop.hh"

extern "C" JNIEXPORT jfloatArray JNICALL Java_io_github_humbleui_skija_Color__1nConvertRGBToHSV
  (JNIEnv* env, jclass jclass, jint red, jint green, jint blue) {
    SkScalar hsv[3];
    SkRGBToHSV(static_cast<U8CPU>(red), static_cast<U8CPU>(green), static_cast<U8CPU>(blue), hsv);
    return javaFloatArray(env, {hsv[0], hsv[1], hsv[2]});
}

extern "C" JNIEXPORT jfloatArray JNICALL Java_io_github_humbleui_skija_Color__1nConvertToHSV
  (JNIEnv* env, jclass jclass, jint color) {
    SkScalar hsv[3];
    SkColorToHSV(static_cast<SkColor>(color), hsv);
    return javaFloatArray(env, {hsv[0], hsv[1], hsv[2]});
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_Color__1nMakeFromHSV
  (JNIEnv* env, jclass jclass, jint alpha, jfloatArray hsvArray) {
    jfloat* hsv = env->GetFloatArrayElements(hsvArray, 0);
    SkScalar skHsv[3] = { hsv[0], hsv[1], hsv[2] };
    SkColor color = SkHSVToColor(static_cast<U8CPU>(alpha), skHsv);
    env->ReleaseFloatArrayElements(hsvArray, hsv, JNI_ABORT);
    return static_cast<jint>(color);
}
