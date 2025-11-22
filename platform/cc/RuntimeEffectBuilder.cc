#include <jni.h>
#include "SkRuntimeEffect.h"
#include "SkData.h"
#include "interop.hh"

static void deleteRuntimeEffectBuilder(SkRuntimeEffectBuilder* builder) {
    delete builder;
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_RuntimeEffectBuilder__1nGetFinalizer
  (JNIEnv* env, jclass jclass) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deleteRuntimeEffectBuilder));
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_RuntimeEffectBuilder__1nMake
  (JNIEnv* env, jclass jclass, jlong effectPtr) {
    SkRuntimeEffect* effect = jlongToPtr<SkRuntimeEffect*>(effectPtr);
    SkRuntimeEffectBuilder* builder = new SkRuntimeEffectBuilder(sk_ref_sp(effect));
    return ptrToJlong(builder);
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_RuntimeEffectBuilder__1nMakeWithUniforms
  (JNIEnv* env, jclass jclass, jlong effectPtr, jlong uniformsPtr) {
    SkRuntimeEffect* effect = jlongToPtr<SkRuntimeEffect*>(effectPtr);
    SkData* uniforms = jlongToPtr<SkData*>(uniformsPtr);
    SkRuntimeEffectBuilder* builder = new SkRuntimeEffectBuilder(sk_ref_sp(effect), sk_ref_sp(uniforms));
    return ptrToJlong(builder);
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_RuntimeEffectBuilder__1nMakeCopy
  (JNIEnv* env, jclass jclass, jlong builderPtr) {
    SkRuntimeEffectBuilder* other = jlongToPtr<SkRuntimeEffectBuilder*>(builderPtr);
    SkRuntimeEffectBuilder* builder = new SkRuntimeEffectBuilder(*other);
    return ptrToJlong(builder);
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_RuntimeEffectBuilder__1nMakeShader
  (JNIEnv* env, jclass jclass, jlong ptr, jfloatArray localMatrixArr) {
    SkRuntimeEffectBuilder* builder = jlongToPtr<SkRuntimeEffectBuilder*>(ptr);
    std::unique_ptr<SkMatrix> localMatrix = skMatrix(env, localMatrixArr);
    sk_sp<SkShader> shader = builder->makeShader(localMatrix.get());
    return ptrToJlong(shader.release());
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_RuntimeEffectBuilder__1nMakeColorFilter
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkRuntimeEffectBuilder* builder = jlongToPtr<SkRuntimeEffectBuilder*>(ptr);
    sk_sp<SkColorFilter> colorFilter = builder->makeColorFilter();
    return ptrToJlong(colorFilter.release());
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_RuntimeEffectBuilder__1nMakeBlender
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkRuntimeEffectBuilder* builder = jlongToPtr<SkRuntimeEffectBuilder*>(ptr);
    sk_sp<SkBlender> blender = builder->makeBlender();
    return ptrToJlong(blender.release());
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_RuntimeEffectBuilder__1nGetUniforms
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkRuntimeEffectBuilder* builder = jlongToPtr<SkRuntimeEffectBuilder*>(ptr);
    sk_sp<const SkData> uniforms = builder->uniforms();
    return ptrToJlong(uniforms.release());
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_RuntimeEffectBuilder__1nSetUniformFloats
  (JNIEnv* env, jclass jclass, jlong ptr, jstring jname, jfloatArray jvalues) {
    SkRuntimeEffectBuilder* builder = jlongToPtr<SkRuntimeEffectBuilder*>(ptr);
    SkString name = *skString(env, jname);

    jsize count = env->GetArrayLength(jvalues);
    jfloat* values = env->GetFloatArrayElements(jvalues, nullptr);

    builder->uniform(name.c_str()).set(values, count);

    env->ReleaseFloatArrayElements(jvalues, values, JNI_ABORT);
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_RuntimeEffectBuilder__1nSetUniformInts
  (JNIEnv* env, jclass jclass, jlong ptr, jstring jname, jintArray jvalues) {
    SkRuntimeEffectBuilder* builder = jlongToPtr<SkRuntimeEffectBuilder*>(ptr);
    SkString name = *skString(env, jname);

    jsize count = env->GetArrayLength(jvalues);
    jint* values = env->GetIntArrayElements(jvalues, nullptr);

    builder->uniform(name.c_str()).set(values, count);

    env->ReleaseIntArrayElements(jvalues, values, JNI_ABORT);
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_RuntimeEffectBuilder__1nSetChild
  (JNIEnv* env, jclass jclass, jlong ptr, jstring jname, jlong childPtr) {
    SkRuntimeEffectBuilder* builder = jlongToPtr<SkRuntimeEffectBuilder*>(ptr);
    SkString name = *skString(env, jname);
    SkFlattenable* child = jlongToPtr<SkFlattenable*>(childPtr);

    if (child == nullptr) {
        builder->child(name.c_str()) = nullptr;
    } else {
        builder->child(name.c_str()) = sk_sp<SkFlattenable>(sk_ref_sp(child));
    }
}
