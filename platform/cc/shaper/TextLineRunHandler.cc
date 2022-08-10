#include <jni.h>
#include "../interop.hh"
#include "interop.hh"
#include "TextLineRunHandler.hh"

static void deleteTextLineRunHandler(TextLineRunHandler* instance) {
    // std::cout << "Deleting [TextLineRunHandler " << instance << "]" << std::endl;
    delete instance;
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_shaper_TextLineRunHandler__1nGetFinalizer
  (JNIEnv* env, jclass jclass) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deleteTextLineRunHandler));
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_shaper_TextLineRunHandler__1nMake
  (JNIEnv* env, jclass jclass, jlong textPtr) {
    SkString* text = reinterpret_cast<SkString*>(static_cast<uintptr_t>(textPtr));
    std::shared_ptr<UBreakIterator> graphemeIter = skija::shaper::graphemeBreakIterator(*text);
    if (!graphemeIter) return 0;
    auto instance = new TextLineRunHandler(*text, graphemeIter);
    return reinterpret_cast<jlong>(instance);
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_shaper_TextLineRunHandler__1nMakeLine
  (JNIEnv* env, jclass jclass, jlong ptr) {
    TextLineRunHandler* instance = reinterpret_cast<TextLineRunHandler*>(static_cast<uintptr_t>(ptr));
    return reinterpret_cast<jlong>(instance->makeLine().release());
}
