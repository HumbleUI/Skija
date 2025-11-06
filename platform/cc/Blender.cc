#include <jni.h>
#include "interop.hh"
#include "SkBlender.h"
#include "SkBlenders.h"
#include "src/core/SkRuntimeBlender.h"

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

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_Blender__1nMakeRuntime
  (JNIEnv* env, jclass jclass, jlong effectPtr, jlong uniformPtr, jlongArray childrenPtrsArr) {
    SkRuntimeEffect* effect = jlongToPtr<SkRuntimeEffect*>(effectPtr);
    SkData* uniform = jlongToPtr<SkData*>(uniformPtr);
    jsize childCount = env->GetArrayLength(childrenPtrsArr);
    jlong* childrenPtrs = env->GetLongArrayElements(childrenPtrsArr, 0);
    std::vector<SkRuntimeEffect::ChildPtr> children(childCount);
    for (size_t i = 0; i < childCount; i++) {
        SkFlattenable* si = jlongToPtr<SkFlattenable*>(childrenPtrs[i]);
        children[i] = SkRuntimeEffect::ChildPtr(sk_ref_sp(si));
    }
    env->ReleaseLongArrayElements(childrenPtrsArr, childrenPtrs, 0);
    SkBlender* blender = new SkRuntimeBlender(sk_ref_sp<SkRuntimeEffect>(effect), sk_ref_sp<SkData>(uniform), children);
    return ptrToJlong(blender);
}
