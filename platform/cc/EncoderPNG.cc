#include <jni.h>
#include "SkData.h"
#include "SkImage.h"
#include "SkPngEncoder.h"

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_EncoderPNG__1nEncode
  (JNIEnv* env, jclass jclass, jlong ctxPtr, jlong imagePtr, jint flagsInt, jint zlibLevel) {
    GrDirectContext* ctx = reinterpret_cast<GrDirectContext*>(static_cast<uintptr_t>(ctxPtr));
    SkImage* image = reinterpret_cast<SkImage*>(static_cast<uintptr_t>(imagePtr));
    SkPngEncoder::FilterFlag flags = static_cast<SkPngEncoder::FilterFlag>(flagsInt);
    SkPngEncoder::Options opts {flags, zlibLevel};
    sk_sp<SkData> data = SkPngEncoder::Encode(ctx, image, opts);
    return reinterpret_cast<jlong>(data.release());
}
