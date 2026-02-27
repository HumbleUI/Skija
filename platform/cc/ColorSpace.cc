#include <iostream>
#include <jni.h>
#include "SkColorSpace.h"
#include "SkData.h"
#include "modules/skcms/skcms.h"
#include "interop.hh"

static void unrefColorSpace(SkColorSpace* ptr) {
    // std::cout << "Deleting [SkColorSpace " << ptr << "]" << std::endl;
    ptr->unref();
}

static jfloatArray transferFnToJava(JNIEnv* env, const skcms_TransferFunction& fn) {
    return javaFloatArray(env, {fn.g, fn.a, fn.b, fn.c, fn.d, fn.e, fn.f});
}

static jfloatArray matrix3x3ToJava(JNIEnv* env, const skcms_Matrix3x3& matrix) {
    return javaFloatArray(env,
        {matrix.vals[0][0], matrix.vals[0][1], matrix.vals[0][2],
         matrix.vals[1][0], matrix.vals[1][1], matrix.vals[1][2],
         matrix.vals[2][0], matrix.vals[2][1], matrix.vals[2][2]});
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_ColorSpace__1nGetFinalizer(JNIEnv* env, jclass jclass) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&unrefColorSpace));
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_ColorSpace__1nMakeSRGB(JNIEnv* env, jclass jclass) {
    SkColorSpace* ptr = SkColorSpace::MakeSRGB().release();
    return reinterpret_cast<jlong>(ptr);
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_ColorSpace__1nMakeSRGBLinear(JNIEnv* env, jclass jclass) {
    SkColorSpace* ptr = SkColorSpace::MakeSRGBLinear().release();
    return reinterpret_cast<jlong>(ptr);
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_ColorSpace__1nMakeDisplayP3(JNIEnv* env, jclass jclass) {
    SkColorSpace* ptr = SkColorSpace::MakeRGB(SkNamedTransferFn::kSRGB, SkNamedGamut::kDisplayP3).release();
    return reinterpret_cast<jlong>(ptr);
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_ColorSpace__1nMakeRGB
  (JNIEnv* env, jclass jclass, jfloatArray transferFnArray, jfloatArray toXYZD50Array) {
    if (transferFnArray == nullptr || toXYZD50Array == nullptr)
        return 0;
    if (env->GetArrayLength(transferFnArray) < 7 || env->GetArrayLength(toXYZD50Array) < 9)
        return 0;

    jfloat* transferFnValues = env->GetFloatArrayElements(transferFnArray, 0);
    jfloat* toXYZD50Values = env->GetFloatArrayElements(toXYZD50Array, 0);

    skcms_TransferFunction transferFn = {
        transferFnValues[0], transferFnValues[1], transferFnValues[2], transferFnValues[3],
        transferFnValues[4], transferFnValues[5], transferFnValues[6]
    };
    skcms_Matrix3x3 toXYZD50 = {{
        {toXYZD50Values[0], toXYZD50Values[1], toXYZD50Values[2]},
        {toXYZD50Values[3], toXYZD50Values[4], toXYZD50Values[5]},
        {toXYZD50Values[6], toXYZD50Values[7], toXYZD50Values[8]},
    }};

    env->ReleaseFloatArrayElements(transferFnArray, transferFnValues, JNI_ABORT);
    env->ReleaseFloatArrayElements(toXYZD50Array, toXYZD50Values, JNI_ABORT);

    sk_sp<SkColorSpace> colorSpace = SkColorSpace::MakeRGB(transferFn, toXYZD50);
    return colorSpace ? reinterpret_cast<jlong>(colorSpace.release()) : 0;
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_ColorSpace__1nMakeCICP
  (JNIEnv* env, jclass jclass, jint primaries, jint transferFn) {
    sk_sp<SkColorSpace> colorSpace = SkColorSpace::MakeCICP(
        static_cast<SkNamedPrimaries::CicpId>(primaries),
        static_cast<SkNamedTransferFn::CicpId>(transferFn));
    return colorSpace ? reinterpret_cast<jlong>(colorSpace.release()) : 0;
}

extern "C" JNIEXPORT jfloatArray JNICALL Java_io_github_humbleui_skija_ColorSpace__1nConvert
  (JNIEnv* env, jclass jclass, jlong fromPtr, jlong toPtr, float r, float g, float b, float a) {
    SkColorSpace* from = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(fromPtr));
    SkColorSpace* to = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(toPtr));

    skcms_TransferFunction fromFn;
    from->transferFn(&fromFn);

    skcms_TransferFunction toFn;
    to->invTransferFn(&toFn);

    float r1 = skcms_TransferFunction_eval(&toFn, skcms_TransferFunction_eval(&fromFn, r));
    float g1 = skcms_TransferFunction_eval(&toFn, skcms_TransferFunction_eval(&fromFn, g));
    float b1 = skcms_TransferFunction_eval(&toFn, skcms_TransferFunction_eval(&fromFn, b));
    float a1 = skcms_TransferFunction_eval(&toFn, skcms_TransferFunction_eval(&fromFn, a));
    return javaFloatArray(env, {r1, g1, b1, a1});
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_ColorSpace__1nIsGammaCloseToSRGB
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkColorSpace* instance = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(ptr));
    return instance->gammaCloseToSRGB();
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_ColorSpace__1nIsGammaLinear
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkColorSpace* instance = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(ptr));
    return instance->gammaIsLinear();
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_ColorSpace__1nIsSRGB
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkColorSpace* instance = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(ptr));
    return instance->isSRGB();
}

extern "C" JNIEXPORT jfloatArray JNICALL Java_io_github_humbleui_skija_ColorSpace__1nGetNumericalTransferFn
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkColorSpace* instance = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(ptr));
    skcms_TransferFunction fn;
    return instance->isNumericalTransferFn(&fn) ? transferFnToJava(env, fn) : nullptr;
}

extern "C" JNIEXPORT jfloatArray JNICALL Java_io_github_humbleui_skija_ColorSpace__1nGetToXYZD50
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkColorSpace* instance = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(ptr));
    skcms_Matrix3x3 matrix;
    return instance->toXYZD50(&matrix) ? matrix3x3ToJava(env, matrix) : nullptr;
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_ColorSpace__1nGetToXYZD50Hash
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkColorSpace* instance = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>(instance->toXYZD50Hash());
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_ColorSpace__1nGetTransferFnHash
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkColorSpace* instance = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>(instance->transferFnHash());
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_ColorSpace__1nGetHash
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkColorSpace* instance = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(ptr));
    return static_cast<jlong>(instance->hash());
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_ColorSpace__1nMakeLinearGamma
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkColorSpace* instance = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(ptr));
    sk_sp<SkColorSpace> colorSpace = instance->makeLinearGamma();
    return colorSpace ? reinterpret_cast<jlong>(colorSpace.release()) : 0;
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_ColorSpace__1nMakeSRGBGamma
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkColorSpace* instance = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(ptr));
    sk_sp<SkColorSpace> colorSpace = instance->makeSRGBGamma();
    return colorSpace ? reinterpret_cast<jlong>(colorSpace.release()) : 0;
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_ColorSpace__1nMakeColorSpin
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkColorSpace* instance = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(ptr));
    sk_sp<SkColorSpace> colorSpace = instance->makeColorSpin();
    return colorSpace ? reinterpret_cast<jlong>(colorSpace.release()) : 0;
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_ColorSpace__1nSerializeToData
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkColorSpace* instance = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(ptr));
    sk_sp<SkData> data = instance->serialize();
    return data ? reinterpret_cast<jlong>(data.release()) : 0;
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_ColorSpace__1nMakeFromData
  (JNIEnv* env, jclass jclass, jlong dataPtr) {
    SkData* data = reinterpret_cast<SkData*>(static_cast<uintptr_t>(dataPtr));
    if (data == nullptr)
        return 0;
    sk_sp<SkColorSpace> colorSpace = SkColorSpace::Deserialize(data->data(), data->size());
    return colorSpace ? reinterpret_cast<jlong>(colorSpace.release()) : 0;
}

extern "C" JNIEXPORT jfloatArray JNICALL Java_io_github_humbleui_skija_ColorSpace__1nTransferFn
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkColorSpace* instance = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(ptr));
    skcms_TransferFunction fn;
    instance->transferFn(&fn);
    return transferFnToJava(env, fn);
}

extern "C" JNIEXPORT jfloatArray JNICALL Java_io_github_humbleui_skija_ColorSpace__1nInvTransferFn
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkColorSpace* instance = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(ptr));
    skcms_TransferFunction fn;
    instance->invTransferFn(&fn);
    return transferFnToJava(env, fn);
}

extern "C" JNIEXPORT jfloatArray JNICALL Java_io_github_humbleui_skija_ColorSpace__1nGamutTransformTo
  (JNIEnv* env, jclass jclass, jlong ptr, jlong dstPtr) {
    SkColorSpace* instance = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(ptr));
    SkColorSpace* dst = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(dstPtr));
    skcms_Matrix3x3 matrix;
    instance->gamutTransformTo(dst, &matrix);
    return matrix3x3ToJava(env, matrix);
}

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_ColorSpace__1nEquals
  (JNIEnv* env, jclass jclass, jlong ptr, jlong otherPtr) {
    SkColorSpace* instance = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(ptr));
    SkColorSpace* other = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(otherPtr));
    return SkColorSpace::Equals(instance, other);
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_ColorSpace__1nMakeFromICCProfile
  (JNIEnv* env, jclass jclass, jbyteArray bytesArray) {
    jbyte* bytes = env->GetByteArrayElements(bytesArray, 0);
    jsize length = env->GetArrayLength(bytesArray);

    skcms_ICCProfile profile;
    bool parsed = skcms_Parse(bytes, length, &profile);

    sk_sp<SkColorSpace> colorSpace;
    if (parsed) {
        colorSpace = SkColorSpace::Make(profile);
    }

    env->ReleaseByteArrayElements(bytesArray, bytes, JNI_ABORT);

    return colorSpace ? reinterpret_cast<jlong>(colorSpace.release()) : 0;
}
