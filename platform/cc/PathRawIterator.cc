#include <utility>
#include <jni.h>
#include "SkPath.h"
#include "SkPathBuilder.h"
#include "interop.hh"

namespace {
    struct PathRawIter {
        explicit PathRawIter(SkPath path) : fPath(std::move(path)), fIter(fPath) {}
        PathRawIter(const PathRawIter&) = delete;
        PathRawIter& operator=(const PathRawIter&) = delete;

        SkPath          fPath; // must be declared before fIter: fIter points into fPath's storage
        SkPath::RawIter fIter;
    };

    int pointsCount(SkPath::Verb verb) {
        switch (verb) {
            case SkPath::kMove_Verb:  return 1;
            case SkPath::kLine_Verb:  return 2;
            case SkPath::kQuad_Verb:  return 3;
            case SkPath::kConic_Verb: return 3;
            case SkPath::kCubic_Verb: return 4;
            case SkPath::kClose_Verb: return 0;
            case SkPath::kDone_Verb:  return 0;
        }
        return 0;
    }

    void deletePathRawIter(PathRawIter* instance) {
        delete instance;
    }
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_PathRawIterator__1nGetFinalizer
  (JNIEnv* env, jclass jclass) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deletePathRawIter));
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_PathRawIterator__1nMakeFromPath
  (JNIEnv* env, jclass jclass, jlong pathPtr) {
    SkPath* path = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(pathPtr));
    PathRawIter* instance = new PathRawIter(*path);
    return reinterpret_cast<jlong>(instance);
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_PathRawIterator__1nMakeFromPathBuilder
  (JNIEnv* env, jclass jclass, jlong builderPtr) {
    SkPathBuilder* builder = reinterpret_cast<SkPathBuilder*>(static_cast<uintptr_t>(builderPtr));
    PathRawIter* instance = new PathRawIter(builder->snapshot());
    return reinterpret_cast<jlong>(instance);
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_PathRawIterator__1nNext
  (JNIEnv* env, jclass jclass, jlong ptr, jfloatArray ptsArr) {
    PathRawIter* instance = reinterpret_cast<PathRawIter*>(static_cast<uintptr_t>(ptr));
    SkPoint pts[4];
    SkPath::Verb verb = instance->fIter.next(pts);
    int count = pointsCount(verb);
    if (count > 0)
        env->SetFloatArrayRegion(ptsArr, 0, count * 2, reinterpret_cast<const jfloat*>(pts));
    return static_cast<jint>(verb);
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_PathRawIterator__1nPeek
  (JNIEnv* env, jclass jclass, jlong ptr) {
    PathRawIter* instance = reinterpret_cast<PathRawIter*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>(instance->fIter.peek());
}

extern "C" JNIEXPORT jfloat JNICALL Java_io_github_humbleui_skija_PathRawIterator__1nGetConicWeight
  (JNIEnv* env, jclass jclass, jlong ptr) {
    PathRawIter* instance = reinterpret_cast<PathRawIter*>(static_cast<uintptr_t>(ptr));
    return instance->fIter.conicWeight();
}
