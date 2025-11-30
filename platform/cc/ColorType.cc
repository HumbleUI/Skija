#include <jni.h>
#include "SkImageInfo.h"

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_ColorType__1nGetValues
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        kUnknown_SkColorType,
        kAlpha_8_SkColorType,
        kRGB_565_SkColorType,
        kARGB_4444_SkColorType,
        kRGBA_8888_SkColorType,
        kRGB_888x_SkColorType,
        kBGRA_8888_SkColorType,
        kRGBA_1010102_SkColorType,
        kBGRA_1010102_SkColorType,
        kRGB_101010x_SkColorType,
        kBGR_101010x_SkColorType,
        kBGR_101010x_XR_SkColorType,
        kBGRA_10101010_XR_SkColorType,
        kRGBA_10x6_SkColorType,
        kGray_8_SkColorType,
        kRGBA_F16Norm_SkColorType,
        kRGBA_F16_SkColorType,
        kRGB_F16F16F16x_SkColorType,
        kRGBA_F32_SkColorType,
        kR8G8_unorm_SkColorType,
        kA16_float_SkColorType,
        kR16G16_float_SkColorType,
        kA16_unorm_SkColorType,
        kR16G16_unorm_SkColorType,
        kR16G16B16A16_unorm_SkColorType,
        kSRGBA_8888_SkColorType,
        kR8_unorm_SkColorType,
        kLastEnum_SkColorType,
        kN32_SkColorType
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}
