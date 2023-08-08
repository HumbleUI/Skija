#include <jni.h>
#include "SkData.h"
#include "SkImage.h"
#include "SkWebpEncoder.h"

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_EncoderWEBP__1nEncode
  (JNIEnv* env, jclass jclass, jlong ctxPtr, jlong imagePtr, jint compressionMode, jfloat quality) {
    GrDirectContext* ctx = reinterpret_cast<GrDirectContext*>(static_cast<uintptr_t>(ctxPtr));
    SkImage* image = reinterpret_cast<SkImage*>(static_cast<uintptr_t>(imagePtr));
    SkWebpEncoder::Options opts {(SkWebpEncoder::Compression) compressionMode, quality};
    sk_sp<SkData> data = SkWebpEncoder::Encode(ctx, image, opts);
    return reinterpret_cast<jlong>(data.release());
}
