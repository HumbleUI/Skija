#include <jni.h>
#include "SkData.h"
#include "SkImage.h"
#include "SkJpegEncoder.h"

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_EncoderJPEG__1nEncode
  (JNIEnv* env, jclass jclass, jlong ctxPtr, jlong imagePtr, jint quality, jint alphaMode, jint downsampleMode) {
    GrDirectContext* ctx = reinterpret_cast<GrDirectContext*>(static_cast<uintptr_t>(ctxPtr));
    SkImage* image = reinterpret_cast<SkImage*>(static_cast<uintptr_t>(imagePtr));
    SkJpegEncoder::Options opts {quality, (SkJpegEncoder::Downsample) downsampleMode, (SkJpegEncoder::AlphaOption) alphaMode};
    sk_sp<SkData> data = SkJpegEncoder::Encode(ctx, image, opts);
    return reinterpret_cast<jlong>(data.release());
}
