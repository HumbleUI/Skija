#include <jni.h>

#include "SkRuntimeEffect.h"
#include "interop.hh"

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_RuntimeEffect__1nMakeForColorFilter
  (JNIEnv* env, jclass jclass, jstring sksl, jboolean forceUnoptimized, jstring name) {
    SkString skslProper = skString(env, sksl);
    SkString nameStr = skString(env, name);
    SkRuntimeEffect::Options options;
    options.forceUnoptimized = forceUnoptimized;
    if (nameStr.size() > 0) {
        options.fName = nameStr.c_str();
    }
    SkRuntimeEffect::Result result = SkRuntimeEffect::MakeForColorFilter(skslProper, options);
    if (result.errorText.isEmpty()) {
        return ptrToJlong(result.effect.release());
    } else {
        env->ThrowNew(java::lang::RuntimeException::cls, result.errorText.c_str());
        return 0;
    }
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_RuntimeEffect__1nMakeForShader
  (JNIEnv* env, jclass jclass, jstring sksl, jboolean forceUnoptimized, jstring name) {
    SkString skslProper = skString(env, sksl);
    SkString nameStr = skString(env, name);
    SkRuntimeEffect::Options options;
    options.forceUnoptimized = forceUnoptimized;
    if (nameStr.size() > 0) {
        options.fName = nameStr.c_str();
    }
    SkRuntimeEffect::Result result = SkRuntimeEffect::MakeForShader(skslProper, options);
    if (result.errorText.isEmpty()) {
        sk_sp<SkRuntimeEffect> effect = result.effect;
        return ptrToJlong(effect.release());
    } else {
        env->ThrowNew(java::lang::RuntimeException::cls, result.errorText.c_str());
        return 0;
    }
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_RuntimeEffect__1nMakeForBlender
  (JNIEnv* env, jclass jclass, jstring sksl, jboolean forceUnoptimized, jstring name) {
    SkString skslProper = skString(env, sksl);
    SkString nameStr = skString(env, name);
    SkRuntimeEffect::Options options;
    options.forceUnoptimized = forceUnoptimized;
    if (nameStr.size() > 0) {
        options.fName = nameStr.c_str();
    }
    SkRuntimeEffect::Result result = SkRuntimeEffect::MakeForBlender(skslProper, options);
    if (result.errorText.isEmpty()) {
        sk_sp<SkRuntimeEffect> effect = result.effect;
        return ptrToJlong(effect.release());
    } else {
        env->ThrowNew(java::lang::RuntimeException::cls, result.errorText.c_str());
        return 0;
    }
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_RuntimeEffect__1nMakeShader
  (JNIEnv* env, jclass jclass, jlong ptr, jlong uniformPtr, jlongArray childrenPtrsArr, jfloatArray localMatrixArr) {
    SkRuntimeEffect* runtimeEffect = jlongToPtr<SkRuntimeEffect*>(ptr);
    SkData* uniform = jlongToPtr<SkData*>(uniformPtr);
    std::unique_ptr<SkMatrix> localMatrix = skMatrix(env, localMatrixArr);

    jsize childCount = env->GetArrayLength(childrenPtrsArr);
    jlong* childrenPtrs = env->GetLongArrayElements(childrenPtrsArr, 0);
    std::vector<SkRuntimeEffect::ChildPtr> children(childCount);
    for (size_t i = 0; i < childCount; i++) {
        SkFlattenable* si = jlongToPtr<SkFlattenable*>(childrenPtrs[i]);
        children[i] = SkRuntimeEffect::ChildPtr(sk_ref_sp(si));
    }
    env->ReleaseLongArrayElements(childrenPtrsArr, childrenPtrs, 0);

    sk_sp<SkShader> shader = runtimeEffect->makeShader(sk_ref_sp<SkData>(uniform), children, localMatrix.get());
    return ptrToJlong(shader.release());
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_RuntimeEffect__1nMakeColorFilter
  (JNIEnv* env, jclass jclass, jlong ptr, jlong uniformPtr, jlongArray childrenPtrsArr) {
    SkRuntimeEffect* runtimeEffect = jlongToPtr<SkRuntimeEffect*>(ptr);
    SkData* uniform = jlongToPtr<SkData*>(uniformPtr);

    jsize childCount = env->GetArrayLength(childrenPtrsArr);
    jlong* childrenPtrs = env->GetLongArrayElements(childrenPtrsArr, 0);
    std::vector<SkRuntimeEffect::ChildPtr> children(childCount);
    for (size_t i = 0; i < childCount; i++) {
        SkFlattenable* si = jlongToPtr<SkFlattenable*>(childrenPtrs[i]);
        children[i] = SkRuntimeEffect::ChildPtr(sk_ref_sp(si));
    }
    env->ReleaseLongArrayElements(childrenPtrsArr, childrenPtrs, 0);

    sk_sp<SkColorFilter> filter = runtimeEffect->makeColorFilter(sk_ref_sp<SkData>(uniform), children);
    return ptrToJlong(filter.release());
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_RuntimeEffect__1nMakeBlender
  (JNIEnv* env, jclass jclass, jlong ptr, jlong uniformPtr, jlongArray childrenPtrsArr) {
    SkRuntimeEffect* runtimeEffect = jlongToPtr<SkRuntimeEffect*>(ptr);
    SkData* uniform = jlongToPtr<SkData*>(uniformPtr);

    jsize childCount = env->GetArrayLength(childrenPtrsArr);
    jlong* childrenPtrs = env->GetLongArrayElements(childrenPtrsArr, 0);
    std::vector<SkRuntimeEffect::ChildPtr> children(childCount);
    for (size_t i = 0; i < childCount; i++) {
        SkFlattenable* si = jlongToPtr<SkFlattenable*>(childrenPtrs[i]);
        children[i] = SkRuntimeEffect::ChildPtr(sk_ref_sp(si));
    }
    env->ReleaseLongArrayElements(childrenPtrsArr, childrenPtrs, 0);

    sk_sp<SkBlender> blender = runtimeEffect->makeBlender(sk_ref_sp<SkData>(uniform), children);
    return ptrToJlong(blender.release());
}

extern "C" JNIEXPORT jstring JNICALL Java_io_github_humbleui_skija_RuntimeEffect__1nGetSource
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkRuntimeEffect* runtimeEffect = jlongToPtr<SkRuntimeEffect*>(ptr);
    const std::string& source = runtimeEffect->source();
    return javaString(env, source);
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_RuntimeEffect__1nGetUniformSize
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkRuntimeEffect* runtimeEffect = jlongToPtr<SkRuntimeEffect*>(ptr);
    return static_cast<jint>(runtimeEffect->uniformSize());
}

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_RuntimeEffect__1nIsShaderAllowed
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkRuntimeEffect* runtimeEffect = jlongToPtr<SkRuntimeEffect*>(ptr);
    return runtimeEffect->allowShader();
}

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_RuntimeEffect__1nIsColorFilterAllowed
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkRuntimeEffect* runtimeEffect = jlongToPtr<SkRuntimeEffect*>(ptr);
    return runtimeEffect->allowColorFilter();
}

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_RuntimeEffect__1nIsBlenderAllowed
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkRuntimeEffect* runtimeEffect = jlongToPtr<SkRuntimeEffect*>(ptr);
    return runtimeEffect->allowBlender();
}

extern "C" JNIEXPORT jobjectArray JNICALL Java_io_github_humbleui_skija_RuntimeEffect__1nGetUniforms
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkRuntimeEffect* runtimeEffect = jlongToPtr<SkRuntimeEffect*>(ptr);
    SkSpan<const SkRuntimeEffect::Uniform> uniforms = runtimeEffect->uniforms();

    jobjectArray result = env->NewObjectArray(uniforms.size(), skija::RuntimeEffectUniformInfo::cls, nullptr);
    for (size_t i = 0; i < uniforms.size(); i++) {
        const SkRuntimeEffect::Uniform& u = uniforms[i];
        jstring name = javaString(env, u.name);
        jobject uniformInfo = env->NewObject(
            skija::RuntimeEffectUniformInfo::cls,
            skija::RuntimeEffectUniformInfo::ctor,
            name,
            static_cast<jint>(u.offset),
            static_cast<jint>(u.type),
            static_cast<jint>(u.count),
            static_cast<jint>(u.flags)
        );
        env->SetObjectArrayElement(result, i, uniformInfo);
        env->DeleteLocalRef(name);
        env->DeleteLocalRef(uniformInfo);
    }
    return result;
}

extern "C" JNIEXPORT jobject JNICALL Java_io_github_humbleui_skija_RuntimeEffect__1nGetUniform
  (JNIEnv* env, jclass jclass, jlong ptr, jstring jname) {
    SkRuntimeEffect* runtimeEffect = jlongToPtr<SkRuntimeEffect*>(ptr);
    SkString name = skString(env, jname);
    const SkRuntimeEffect::Uniform* u = runtimeEffect->findUniform(name.c_str());

    if (u == nullptr) {
        return nullptr;
    }

    jstring javaName = javaString(env, std::string(u->name));
    jobject uniformInfo = env->NewObject(
        skija::RuntimeEffectUniformInfo::cls,
        skija::RuntimeEffectUniformInfo::ctor,
        javaName,
        static_cast<jint>(u->offset),
        static_cast<jint>(u->type),
        static_cast<jint>(u->count),
        static_cast<jint>(u->flags)
    );
    env->DeleteLocalRef(javaName);
    return uniformInfo;
}

extern "C" JNIEXPORT jobjectArray JNICALL Java_io_github_humbleui_skija_RuntimeEffect__1nGetChildren
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkRuntimeEffect* runtimeEffect = jlongToPtr<SkRuntimeEffect*>(ptr);
    SkSpan<const SkRuntimeEffect::Child> children = runtimeEffect->children();

    jobjectArray result = env->NewObjectArray(children.size(), skija::RuntimeEffectChildInfo::cls, nullptr);
    for (size_t i = 0; i < children.size(); i++) {
        const SkRuntimeEffect::Child& c = children[i];
        jstring name = javaString(env, std::string(c.name));
        jobject childInfo = env->NewObject(
            skija::RuntimeEffectChildInfo::cls,
            skija::RuntimeEffectChildInfo::ctor,
            name,
            static_cast<jint>(c.type),
            static_cast<jint>(c.index)
        );
        env->SetObjectArrayElement(result, i, childInfo);
        env->DeleteLocalRef(name);
        env->DeleteLocalRef(childInfo);
    }
    return result;
}

extern "C" JNIEXPORT jobject JNICALL Java_io_github_humbleui_skija_RuntimeEffect__1nGetChild
  (JNIEnv* env, jclass jclass, jlong ptr, jstring jname) {
    SkRuntimeEffect* runtimeEffect = jlongToPtr<SkRuntimeEffect*>(ptr);
    SkString name = skString(env, jname);
    const SkRuntimeEffect::Child* c = runtimeEffect->findChild(name.c_str());

    if (c == nullptr) {
        return nullptr;
    }

    jstring javaName = javaString(env, c->name);
    jobject childInfo = env->NewObject(
        skija::RuntimeEffectChildInfo::cls,
        skija::RuntimeEffectChildInfo::ctor,
        javaName,
        static_cast<jint>(c->type),
        static_cast<jint>(c->index)
    );
    env->DeleteLocalRef(javaName);
    return childInfo;
}
