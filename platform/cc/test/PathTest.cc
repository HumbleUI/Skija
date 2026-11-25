#include <string>
#include <jni.h>
#include "SkPath.h"
#include "../PathSegmentIterator.hh"

// Iterates the path with skija::PathSegmentIter and with SkPath::Iter side by side.
// Returns description of the first difference, or null if they match.
extern "C" JNIEXPORT jstring JNICALL Java_io_github_humbleui_skija_test_PathTest__1nFindIterMismatch
  (JNIEnv* env, jclass jclass, jlong pathPtr, jboolean forceClose) {
    SkPath* path = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(pathPtr));
    skija::PathSegmentIter ours(*path, forceClose);
    SkPath::Iter skia(*path, forceClose);
    for (int step = 0; ; step++) {
        SkPoint oursPts[4], skiaPts[4];
        SkPath::Verb oursVerb = ours.next(oursPts);
        SkPath::Verb skiaVerb = skia.next(skiaPts);
        if (oursVerb != skiaVerb)
            return env->NewStringUTF(("step " + std::to_string(step) + ": verb " + std::to_string(oursVerb) + ", Skia " + std::to_string(skiaVerb)).c_str());
        if (oursVerb == SkPath::kDone_Verb)
            return nullptr;
        bool oursClosed = ours.isClosedContour();
        bool skiaClosed = skia.isClosedContour();
        if (oursClosed != skiaClosed)
            return env->NewStringUTF(("step " + std::to_string(step) + ", verb " + std::to_string(oursVerb) + ": closedContour " + std::to_string(oursClosed) + ", Skia " + std::to_string(skiaClosed)).c_str());
    }
}
