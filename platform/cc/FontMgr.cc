#include <iostream>
#include <jni.h>
#include "interop.hh"
#include "FontMgr.hh"
#include "SkData.h"
#include "SkTypeface.h"

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_FontMgr__1nGetFamiliesCount
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkFontMgr* instance = reinterpret_cast<SkFontMgr*>(static_cast<uintptr_t>(ptr));
    return instance->countFamilies();
}

extern "C" JNIEXPORT jstring JNICALL Java_io_github_humbleui_skija_FontMgr__1nGetFamilyName
  (JNIEnv* env, jclass jclass, jlong ptr, jint index) {
    SkFontMgr* instance = reinterpret_cast<SkFontMgr*>(static_cast<uintptr_t>(ptr));
    SkString familyName;
    instance->getFamilyName(index, &familyName);
    return javaString(env, familyName);
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_FontMgr__1nMakeStyleSet
  (JNIEnv* env, jclass jclass, jlong ptr, jint index) {
    SkFontMgr* instance = reinterpret_cast<SkFontMgr*>(static_cast<uintptr_t>(ptr));
    sk_sp<SkFontStyleSet> styleSet = instance->createStyleSet(index);
    return reinterpret_cast<jlong>(styleSet.release());
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_FontMgr__1nMatchFamily
  (JNIEnv* env, jclass jclass, jlong ptr, jstring familyNameStr) {
    SkFontMgr* instance = reinterpret_cast<SkFontMgr*>(static_cast<uintptr_t>(ptr));
    SkString familyName = skString(env, familyNameStr);
    sk_sp<SkFontStyleSet> styleSet = instance->matchFamily(familyName.c_str());
    return reinterpret_cast<jlong>(styleSet.release());
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_FontMgr__1nMatchFamilyStyle
  (JNIEnv* env, jclass jclass, jlong ptr, jstring familyNameStr, jint fontStyle) {
    SkFontMgr* instance = reinterpret_cast<SkFontMgr*>(static_cast<uintptr_t>(ptr));
    SkString familyName = skString(env, familyNameStr);
    sk_sp<SkTypeface> typeface = instance->matchFamilyStyle(familyName.c_str(), skija::FontStyle::fromJava(fontStyle));
    return reinterpret_cast<jlong>(typeface.release());
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_FontMgr__1nMatchFamilyStyleCharacter
  (JNIEnv* env, jclass jclass, jlong ptr, jstring familyNameStr, jint fontStyle, jobjectArray bcp47Array, jint character) {
    SkFontMgr* instance = reinterpret_cast<SkFontMgr*>(static_cast<uintptr_t>(ptr));

    SkString familyName = skString(env, familyNameStr);
    
    std::vector<SkString> bcp47Strings = skStringVector(env, bcp47Array);
    std::vector<const char*> bcp47(bcp47Strings.size());
    for (int i = 0; i < bcp47.size(); ++i)
        bcp47[i] = bcp47Strings[i].c_str();
    
    sk_sp<SkTypeface> typeface = instance->matchFamilyStyleCharacter(familyName.c_str(), skija::FontStyle::fromJava(fontStyle), bcp47.data(), (int) bcp47.size(), character);
    
    return reinterpret_cast<jlong>(typeface.release());
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_FontMgr__1nMakeFromFile
  (JNIEnv* env, jclass jclass, jlong ptr, jstring pathStr, jint index) {
    SkFontMgr* instance = reinterpret_cast<SkFontMgr*>(static_cast<uintptr_t>(ptr));
    SkString path = skString(env, pathStr);
    SkTypeface* typeface = instance->makeFromFile(path.c_str(), index).release();
    return reinterpret_cast<jlong>(typeface);
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_FontMgr__1nMakeFromData
  (JNIEnv* env, jclass jclass, jlong ptr, jlong dataPtr, jint ttcIndex) {
    SkFontMgr* instance = reinterpret_cast<SkFontMgr*>(static_cast<uintptr_t>(ptr));
    SkData* data = reinterpret_cast<SkData*>(static_cast<uintptr_t>(dataPtr));
    SkTypeface* typeface = instance->makeFromData(sk_ref_sp(data), ttcIndex).release();
    return reinterpret_cast<jlong>(typeface);
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_FontMgr__1nDefault
  (JNIEnv* env, jclass jclass) {
    sk_sp<SkFontMgr> mgr = skija::FontMgr_impl::RefDefault();
    return reinterpret_cast<jlong>(mgr.release());
}
