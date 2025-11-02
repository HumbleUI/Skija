#include <algorithm>
#include <cfloat>
#include <iostream>
#include <vector>
#include <jni.h>
#include "SkPath.h"
#include "SkPathOps.h"
#include "interop.hh"
#include "include/utils/SkParsePath.h"

static void deletePath(SkPath* path) {
    // std::cout << "Deleting [SkPath " << path << "]" << std::endl;
    delete path;
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_Path__1nGetFinalizer(JNIEnv* env, jclass jclass) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deletePath));
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_Path__1nMake(JNIEnv* env, jclass jclass) {
    SkPath* obj = new SkPath();
    return reinterpret_cast<jlong>(obj);
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_Path__1nMakeFromSVGString
  (JNIEnv* env, jclass jclass, jstring d) {
    SkPath* obj = new SkPath();
    SkString s = skString(env, d);
    if (SkParsePath::FromSVGString(s.c_str(), obj))
        return reinterpret_cast<jlong>(obj);
    else {
        delete obj;
        return 0;
    }
}

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_Path__1nEquals(JNIEnv* env, jclass jclass, jlong aPtr, jlong bPtr) {
    SkPath* a = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(aPtr));
    SkPath* b = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(bPtr));
    return *a == *b;
}

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_Path__1nIsInterpolatable(JNIEnv* env, jclass jclass, jlong ptr, jlong comparePtr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    SkPath* compare = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(comparePtr));
    return instance->isInterpolatable(*compare);
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_Path__1nMakeLerp(JNIEnv* env, jclass jclass, jlong ptr, jlong endingPtr, jfloat weight) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    SkPath* ending = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(endingPtr));
    SkPath* out = new SkPath();
    if (instance->interpolate(*ending, weight, out)) {
        return reinterpret_cast<jlong>(out);
    } else {
        delete out;
        return 0;
    }
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_Path__1nGetFillMode(JNIEnv* env, jclass jclass, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>(instance->getFillType());
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_Path__1nSetFillMode(JNIEnv* env, jclass jclass, jlong ptr, jint fillMode) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->setFillType(static_cast<SkPathFillType>(fillMode));
}

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_Path__1nIsConvex(JNIEnv* env, jclass jclass, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return instance->isConvex();
}

extern "C" JNIEXPORT jobject JNICALL Java_io_github_humbleui_skija_Path__1nIsOval(JNIEnv* env, jclass jclass, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    SkRect bounds;
    if (instance->isOval(&bounds))
        return types::Rect::fromSkRect(env, bounds);
    else
        return nullptr;
}

extern "C" JNIEXPORT jobject JNICALL Java_io_github_humbleui_skija_Path__1nIsRRect(JNIEnv* env, jclass jclass, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    SkRRect rrect;
    if (instance->isRRect(&rrect))
        return types::RRect::fromSkRRect(env, rrect);
    else
        return nullptr;
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_Path__1nReset(JNIEnv* env, jclass jclass, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->reset();
}

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_Path__1nIsEmpty(JNIEnv* env, jclass jclass, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return instance->isEmpty();
}

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_Path__1nIsLastContourClosed(JNIEnv* env, jclass jclass, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return instance->isLastContourClosed();
}

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_Path__1nIsFinite(JNIEnv* env, jclass jclass, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return instance->isFinite();
}

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_Path__1nIsVolatile(JNIEnv* env, jclass jclass, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return instance->isVolatile();
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_Path__1nSetVolatile(JNIEnv* env, jclass jclass, jlong ptr, jboolean isVolatile) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->setIsVolatile(isVolatile);
}

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_Path__1nIsLineDegenerate(JNIEnv* env, jclass jclass, jfloat x0, jfloat y0, jfloat x1, jfloat y1, jboolean exact) {
    return SkPath::IsLineDegenerate({x0, y0}, {x1, y1}, exact);
}

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_Path__1nIsQuadDegenerate(JNIEnv* env, jclass jclass, jfloat x0, jfloat y0, jfloat x1, jfloat y1, jfloat x2, jfloat y2, jboolean exact) {
    return SkPath::IsQuadDegenerate({x0, y0}, {x1, y1}, {x2, y2}, exact);
}

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_Path__1nIsCubicDegenerate(JNIEnv* env, jclass jclass, jfloat x0, jfloat y0, jfloat x1, jfloat y1, jfloat x2, jfloat y2, jfloat x3, jfloat y3, jboolean exact) {
    return SkPath::IsCubicDegenerate({x0, y0}, {x1, y1}, {x2, y2}, {x3, y3}, exact);
}

extern "C" JNIEXPORT jobjectArray JNICALL Java_io_github_humbleui_skija_Path__1nMaybeGetAsLine(JNIEnv* env, jclass jclass, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    SkPoint line[2];
    if (instance->isLine(line)) {
        jobjectArray res = env->NewObjectArray(2, types::Point::cls, nullptr);
        env->SetObjectArrayElement(res, 0, types::Point::fromSkPoint(env, line[0]));
        env->SetObjectArrayElement(res, 1, types::Point::fromSkPoint(env, line[1]));
        return res;
    } else
        return nullptr;
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_Path__1nGetPointsCount(JNIEnv* env, jclass jclass, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return instance->countPoints();
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_Path__1nCountVerbs(JNIEnv* env, jclass jclass, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return instance->countVerbs();
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_Path__1nApproximateBytesUsed(JNIEnv* env, jclass jclass, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return (jint) instance->approximateBytesUsed();
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_Path__1nSwap(JNIEnv* env, jclass jclass, jlong ptr, jlong otherPtr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    SkPath* other = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(otherPtr));
    instance->swap(*other);
}

extern "C" JNIEXPORT jobject JNICALL Java_io_github_humbleui_skija_Path__1nGetBounds(JNIEnv* env, jclass jclass, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return types::Rect::fromSkRect(env, instance->getBounds());
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_Path__1nUpdateBoundsCache(JNIEnv* env, jclass jclass, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->updateBoundsCache();
}

extern "C" JNIEXPORT jobject JNICALL Java_io_github_humbleui_skija_Path__1nComputeTightBounds(JNIEnv* env, jclass jclass, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return types::Rect::fromSkRect(env, instance->computeTightBounds());
}

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_Path__1nConservativelyContainsRect(JNIEnv* env, jclass jclass, jlong ptr, float l, float t, float r, float b) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    SkRect rect {l, t, r, b};
    return instance->conservativelyContainsRect(rect);
}


extern "C" JNIEXPORT jobjectArray Java_io_github_humbleui_skija_Path__1nConvertConicToQuads
  (JNIEnv* env, jclass jclass, jfloat x0, jfloat y0, jfloat x1, jfloat y1, jfloat x2, jfloat y2, jfloat w, jint pow2) {
    std::vector<SkPoint> pts(1 + 2 * (1 << pow2));
    int count = SkPath::ConvertConicToQuads({x0, y0}, {x1, y1}, {x2, y2}, w, pts.data(), pow2);
    jobjectArray res = env->NewObjectArray(count, types::Point::cls, nullptr);
    for (int i = 0; i < count; ++i) {
        env->SetObjectArrayElement(res, i, types::Point::fromSkPoint(env, pts[i]));
    }
    return res;
}

extern "C" JNIEXPORT jobject Java_io_github_humbleui_skija_Path__1nIsRect
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    SkRect rect;
    if (instance->isRect(&rect))
        return types::Rect::fromSkRect(env, rect);
    else
        return nullptr;
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_Path__1nGetSegmentMasks
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return instance->getSegmentMasks();
}

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_Path__1nContains
  (JNIEnv* env, jclass jclass, jlong ptr, jfloat x, jfloat y) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return instance->contains(x, y);
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_Path__1nDump
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->dump();
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_Path__1nDumpHex
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->dumpHex();
}

extern "C" JNIEXPORT jbyteArray JNICALL Java_io_github_humbleui_skija_Path__1nSerializeToBytes
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    size_t count = instance->writeToMemory(nullptr);
    jbyteArray bytesArray = env->NewByteArray((jsize) count);
    jbyte* bytes = env->GetByteArrayElements(bytesArray, 0);
    instance->writeToMemory(bytes);
    env->ReleaseByteArrayElements(bytesArray, bytes, 0);
    return bytesArray;
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_Path__1nMakeCombining
  (JNIEnv* env, jclass jclass, jlong aPtr, jlong bPtr, jint jop) {
    SkPath* a = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(aPtr));
    SkPath* b = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(bPtr));
    SkPathOp op = static_cast<SkPathOp>(jop);
    auto res = std::make_unique<SkPath>();
    if (Op(*a, *b, op, res.get()))
        return reinterpret_cast<jlong>(res.release());
    else
        return 0;
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_Path__1nMakeFromBytes
  (JNIEnv* env, jclass jclass, jbyteArray bytesArray) {
    jbyte* bytes = env->GetByteArrayElements(bytesArray, 0);
    int count = env->GetArrayLength(bytesArray);
    std::optional<SkPath> path = SkPath::ReadFromMemory(bytes, count);
    env->ReleaseByteArrayElements(bytesArray, bytes, JNI_ABORT);
    return path ? reinterpret_cast<jlong>(new SkPath(*path)) : 0;
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_Path__1nGetGenerationId
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return instance->getGenerationID();
}

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_Path__1nIsValid
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return instance->isValid();
}