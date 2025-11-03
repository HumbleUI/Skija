#include <iostream>
#include <jni.h>
#include "SkPathBuilder.h"
#include "SkPath.h"
#include "SkMatrix.h"
#include "interop.hh"

static void deletePathBuilder(SkPathBuilder* builder) {
    // std::cout << "Deleting [SkPathBuilder " << builder << "]" << std::endl;
    delete builder;
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_PathBuilder__1nGetFinalizer(JNIEnv* env, jclass jclass) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deletePathBuilder));
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_PathBuilder__1nMake(JNIEnv* env, jclass jclass) {
    SkPathBuilder* obj = new SkPathBuilder();
    return reinterpret_cast<jlong>(obj);
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_PathBuilder__1nMakeFromFillMode
  (JNIEnv* env, jclass jclass, jint fillMode) {
    SkPathBuilder* obj = new SkPathBuilder(static_cast<SkPathFillType>(fillMode));
    return reinterpret_cast<jlong>(obj);
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_PathBuilder__1nMakeFromPathBuilder
  (JNIEnv* env, jclass jclass, jlong builderPtr) {
    SkPathBuilder* builder = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(builderPtr));
    SkPathBuilder* obj = new SkPathBuilder(builder->snapshot());
    return reinterpret_cast<jlong>(obj);
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_PathBuilder__1nMakeFromPath
  (JNIEnv* env, jclass jclass, jlong pathPtr) {
    SkPath* path = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(pathPtr));
    SkPathBuilder* obj = new SkPathBuilder(*path);
    return reinterpret_cast<jlong>(obj);
}

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_PathBuilder__1nEquals
  (JNIEnv* env, jclass jclass, jlong ptr, jlong otherPtr) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    SkPathBuilder* other = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(otherPtr));
    return *instance == *other;
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_PathBuilder__1nGetFillMode(JNIEnv* env, jclass jclass, jlong ptr) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>(instance->fillType());
}

extern "C" JNIEXPORT jobject JNICALL Java_io_github_humbleui_skija_PathBuilder__1nComputeFiniteBounds
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    std::optional<SkRect> bounds = instance->computeFiniteBounds();
    return bounds ? types::Rect::fromSkRect(env, *bounds) : nullptr;
}

extern "C" JNIEXPORT jobject JNICALL Java_io_github_humbleui_skija_PathBuilder__1nComputeTightBounds
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    std::optional<SkRect> bounds = instance->computeTightBounds();
    return bounds ? types::Rect::fromSkRect(env, *bounds) : nullptr;
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_PathBuilder__1nSnapshot
  (JNIEnv* env, jclass jclass, jlong ptr, jfloatArray matrixArr) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    SkPath* path;
    if (matrixArr == nullptr) {
      path = new SkPath(instance->snapshot());
    } else {
      std::unique_ptr<SkMatrix> matrix = skMatrix(env, matrixArr);
      path = new SkPath(instance->snapshot(matrix.get()));
    }
    return reinterpret_cast<jlong>(path);
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_PathBuilder__1nDetach
  (JNIEnv* env, jclass jclass, jlong ptr, jfloatArray matrixArr) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    SkPath* path;
    if (matrixArr == nullptr) {
      path = new SkPath(instance->detach());
    } else {
      std::unique_ptr<SkMatrix> matrix = skMatrix(env, matrixArr);
      path = new SkPath(instance->detach(matrix.get()));
    }
    return reinterpret_cast<jlong>(path);
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_PathBuilder__1nSetFillMode(JNIEnv* env, jclass jclass, jlong ptr, jint fillMode) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    instance->setFillType(static_cast<SkPathFillType>(fillMode));
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_PathBuilder__1nSetVolatile(JNIEnv* env, jclass jclass, jlong ptr, jboolean isVolatile) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    instance->setIsVolatile(isVolatile);
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_PathBuilder__1nReset
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    instance->reset();
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_PathBuilder__1nMoveTo(JNIEnv* env, jclass jclass, jlong ptr, jfloat x, jfloat y) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    instance->moveTo(x, y);
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_PathBuilder__1nLineTo(JNIEnv* env, jclass jclass, jlong ptr, jfloat x, jfloat y) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    instance->lineTo(x, y);
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_PathBuilder__1nQuadTo(JNIEnv* env, jclass jclass, jlong ptr, jfloat x1, jfloat y1, jfloat x2, jfloat y2) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    instance->quadTo(x1, y1, x2, y2);
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_PathBuilder__1nConicTo(JNIEnv* env, jclass jclass, jlong ptr, jfloat x1, jfloat y1, jfloat x2, jfloat y2, jfloat w) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    instance->conicTo(x1, y1, x2, y2, w);
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_PathBuilder__1nCubicTo(JNIEnv* env, jclass jclass, jlong ptr, jfloat x1, jfloat y1, jfloat x2, jfloat y2, jfloat x3, jfloat y3) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    instance->cubicTo(x1, y1, x2, y2, x3, y3);
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_PathBuilder__1nClosePath(JNIEnv* env, jclass jclass, jlong ptr) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    instance->close();
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_PathBuilder__1nPolylineTo
  (JNIEnv* env, jclass jclass, jlong ptr, jfloatArray coords) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    jsize len = env->GetArrayLength(coords);
    jfloat* arr = env->GetFloatArrayElements(coords, 0);
    instance->polylineTo(SkSpan(reinterpret_cast<SkPoint*>(arr), len / 2));
    env->ReleaseFloatArrayElements(coords, arr, JNI_ABORT);
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_PathBuilder__1nRMoveTo(JNIEnv* env, jclass jclass, jlong ptr, jfloat dx, jfloat dy) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    instance->rMoveTo({dx, dy});
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_PathBuilder__1nRLineTo(JNIEnv* env, jclass jclass, jlong ptr, jfloat dx, jfloat dy) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    instance->rLineTo(dx, dy);
}
extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_PathBuilder__1nRQuadTo(JNIEnv* env, jclass jclass, jlong ptr, jfloat dx1, jfloat dy1, jfloat dx2, jfloat dy2) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    instance->rQuadTo(dx1, dy1, dx2, dy2);
}
extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_PathBuilder__1nRConicTo(JNIEnv* env, jclass jclass, jlong ptr, jfloat dx1, jfloat dy1, jfloat dx2, jfloat dy2, jfloat w) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    instance->rConicTo(dx1, dy1, dx2, dy2, w);
}
extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_PathBuilder__1nRCubicTo(JNIEnv* env, jclass jclass, jlong ptr, jfloat dx1, jfloat dy1, jfloat dx2, jfloat dy2, jfloat dx3, jfloat dy3) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    instance->rCubicTo(dx1, dy1, dx2, dy2, dx3, dy3);
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_PathBuilder__1nREllipticalArcTo(JNIEnv* env, jclass jclass, jlong ptr, jfloat rx, jfloat ry, jfloat xAxisRotate, jint size, jint direction, jfloat dx, float dy) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    instance->rArcTo({rx, ry}, xAxisRotate, static_cast<SkPathBuilder::ArcSize>(size), static_cast<SkPathDirection>(direction), {dx, dy});
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_PathBuilder__1nArcTo(JNIEnv* env, jclass jclass, jlong ptr, jfloat left, jfloat top, jfloat right, jfloat bottom, jfloat startAngle, jfloat sweepAngle, jboolean forceMoveTo) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    instance->arcTo({left, top, right, bottom}, startAngle, sweepAngle, forceMoveTo);
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_PathBuilder__1nTangentArcTo(JNIEnv* env, jclass jclass, jlong ptr, jfloat x1, jfloat y1, jfloat x2, jfloat y2, jfloat radius) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    instance->arcTo({x1, y1}, {x2, y2}, radius);
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_PathBuilder__1nEllipticalArcTo(JNIEnv* env, jclass jclass, jlong ptr, jfloat rx, jfloat ry, jfloat xAxisRotate, jint size, jint direction, jfloat x, float y) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    instance->arcTo({rx, ry}, xAxisRotate, static_cast<SkPathBuilder::ArcSize>(size), static_cast<SkPathDirection>(direction), {x, y});
}

extern "C" JNIEXPORT void Java_io_github_humbleui_skija_PathBuilder__1nAddArc
  (JNIEnv* env, jclass jclass, jlong ptr, jfloat l, jfloat t, jfloat r, jfloat b, jfloat startAngle, jfloat sweepAngle) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    instance->addArc({l, t, r, b}, startAngle, sweepAngle);
}

extern "C" JNIEXPORT void Java_io_github_humbleui_skija_PathBuilder__1nAddRect
  (JNIEnv* env, jclass jclass, jlong ptr, jfloat l, jfloat t, jfloat r, jfloat b, jint dirInt, jint start) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    SkPathDirection dir = static_cast<SkPathDirection>(dirInt);
    instance->addRect({l, t, r, b}, dir, start);
}

extern "C" JNIEXPORT void Java_io_github_humbleui_skija_PathBuilder__1nAddOval
  (JNIEnv* env, jclass jclass, jlong ptr, jfloat l, jfloat t, jfloat r, jfloat b, jint dirInt, jint start) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    SkPathDirection dir = static_cast<SkPathDirection>(dirInt);
    instance->addOval({l, t, r, b}, dir, start);
}

extern "C" JNIEXPORT void Java_io_github_humbleui_skija_PathBuilder__1nAddRRect
  (JNIEnv* env, jclass jclass, jlong ptr, jfloat l, jfloat t, jfloat r, jfloat b, jfloatArray radii, jint dirInt, jint start) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    SkRRect rrect = types::RRect::toSkRRect(env, l, t, r, b, radii);
    SkPathDirection dir = static_cast<SkPathDirection>(dirInt);
    instance->addRRect(rrect, dir, start);
}

extern "C" JNIEXPORT void Java_io_github_humbleui_skija_PathBuilder__1nAddCircle
  (JNIEnv* env, jclass jclass, jlong ptr, jfloat x, jfloat y, jfloat r, jint dirInt) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    SkPathDirection dir = static_cast<SkPathDirection>(dirInt);
    instance->addCircle(x, y, r, dir);
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_PathBuilder__1nAddPolygon
  (JNIEnv* env, jclass jclass, jlong ptr, jfloatArray coords, jboolean close) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    jsize len = env->GetArrayLength(coords);
    jfloat* arr = env->GetFloatArrayElements(coords, 0);
    instance->addPolygon(SkSpan(reinterpret_cast<SkPoint*>(arr), len / 2), close);
    env->ReleaseFloatArrayElements(coords, arr, JNI_ABORT);
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_PathBuilder__1nAddPath
  (JNIEnv* env, jclass jclass, jlong ptr, jlong srcPtr, jboolean extend) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    SkPath* src = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(srcPtr));
    SkPath::AddPathMode mode = extend ? SkPath::AddPathMode::kExtend_AddPathMode : SkPath::AddPathMode::kAppend_AddPathMode;
    instance->addPath(*src, mode);
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_PathBuilder__1nAddPathOffset
  (JNIEnv* env, jclass jclass, jlong ptr, jlong srcPtr, jfloat dx, jfloat dy, jboolean extend) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    SkPath* src = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(srcPtr));
    SkPath::AddPathMode mode = extend ? SkPath::AddPathMode::kExtend_AddPathMode : SkPath::AddPathMode::kAppend_AddPathMode;
    instance->addPath(*src, dx, dy, mode);
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_PathBuilder__1nAddPathTransform
  (JNIEnv* env, jclass jclass, jlong ptr, jlong srcPtr, jfloatArray matrixArr, jboolean extend) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    SkPath* src = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(srcPtr));
    std::unique_ptr<SkMatrix> matrix = skMatrix(env, matrixArr);
    SkPath::AddPathMode mode = extend ? SkPath::AddPathMode::kExtend_AddPathMode : SkPath::AddPathMode::kAppend_AddPathMode;
    instance->addPath(*src, *matrix, mode);
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_PathBuilder__1nIncReserve(JNIEnv* env, jclass jclass, jlong ptr, jint extraPtCount, jint extraVerbCount, jint extraConicCount) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    instance->incReserve(extraPtCount, extraVerbCount, extraConicCount);
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_PathBuilder__1nOffset
  (JNIEnv* env, jclass jclass, jlong ptr, jfloat dx, jfloat dy) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    instance->offset(dx, dy);
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_PathBuilder__1nTransform
  (JNIEnv* env, jclass jclass, jlong ptr, jfloatArray matrixArr) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    std::unique_ptr<SkMatrix> matrix = skMatrix(env, matrixArr);
    instance->transform(*matrix);
}

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_PathBuilder__1nIsFinite
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    return instance->isFinite();
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_PathBuilder__1nToggleInverseFillType
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    instance->toggleInverseFillType();
}

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_PathBuilder__1nIsEmpty
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    return instance->isEmpty();
}

extern "C" JNIEXPORT jobject JNICALL Java_io_github_humbleui_skija_PathBuilder__1nGetLastPt
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    auto lastPt = instance->getLastPt();
    if (lastPt.has_value())
        return types::Point::fromSkPoint(env, lastPt.value());
    else
        return nullptr;
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_PathBuilder__1nSetPoint
  (JNIEnv* env, jclass jclass, jlong ptr, jint index, jfloat x, jfloat y) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    instance->setPoint(index, {x, y});
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_PathBuilder__1nSetLastPt
  (JNIEnv* env, jclass jclass, jlong ptr, jfloat x, jfloat y) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    instance->setLastPt(x, y);
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_PathBuilder__1nCountPoints
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    return instance->countPoints();
}

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_PathBuilder__1nIsInverseFillType
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    return instance->isInverseFillType();
}

extern "C" JNIEXPORT jobjectArray JNICALL Java_io_github_humbleui_skija_PathBuilder__1nGetPoints
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    SkSpan<const SkPoint> points = instance->points();
    return types::Point::fromSkPoints(env, points);
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_PathBuilder__1nGetVerbs
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    SkSpan<const SkPathVerb> verbs = instance->verbs();
    jintArray result = env->NewIntArray(verbs.size());
    jint* arr = env->GetIntArrayElements(result, 0);
    for (size_t i = 0; i < verbs.size(); i++) {
        arr[i] = static_cast<jint>(verbs[i]);
    }
    env->ReleaseIntArrayElements(result, arr, 0);
    return result;
}

extern "C" JNIEXPORT jfloatArray JNICALL Java_io_github_humbleui_skija_PathBuilder__1nGetConicWeights
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkPathBuilder* instance = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(ptr));
    SkSpan<const float> weights = instance->conicWeights();
    return javaFloatArray(env, weights);
}
