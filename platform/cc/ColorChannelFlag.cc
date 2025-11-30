#include <jni.h>
#include "SkColor.h"

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_ColorChannelFlag__1nGetValues
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
      kRed_SkColorChannelFlag,
      kGreen_SkColorChannelFlag,
      kBlue_SkColorChannelFlag,
      kAlpha_SkColorChannelFlag,
      kGray_SkColorChannelFlag,
      // Convenience values
      kGrayAlpha_SkColorChannelFlags,
      kRG_SkColorChannelFlags,
      kRGB_SkColorChannelFlags,
      kRGBA_SkColorChannelFlags,
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}
