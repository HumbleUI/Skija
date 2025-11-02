#include <iostream>
#include <jni.h>
#include "SkData.h"
#include "SkStream.h"
#include "SkTypeface.h"
#include "interop.hh"

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_Typeface__1nGetFontStyle
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkTypeface* instance = reinterpret_cast<SkTypeface*>(static_cast<uintptr_t>(ptr));
    return skija::FontStyle::toJava(instance->fontStyle());
}

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_Typeface__1nIsFixedPitch
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkTypeface* instance = reinterpret_cast<SkTypeface*>(static_cast<uintptr_t>(ptr));
    return instance->isFixedPitch();
}

extern "C" JNIEXPORT jobjectArray JNICALL Java_io_github_humbleui_skija_Typeface__1nGetVariations
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkTypeface* instance = reinterpret_cast<SkTypeface*>(static_cast<uintptr_t>(ptr));
    int count = instance->getVariationDesignPosition({});
    if (count > 0) {
        std::vector<SkFontArguments::VariationPosition::Coordinate> coords(count);
        instance->getVariationDesignPosition(coords);
        jobjectArray res = env->NewObjectArray(count, skija::FontVariation::cls, nullptr);
        for (int i=0; i < count; ++i) {
            jobject var = env->NewObject(skija::FontVariation::cls, skija::FontVariation::ctor, coords[i].axis, coords[i].value);
            env->SetObjectArrayElement(res, i, var);
        }
        return res;
    } else
        return nullptr;
}

extern "C" JNIEXPORT jobjectArray JNICALL Java_io_github_humbleui_skija_Typeface__1nGetVariationAxes
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkTypeface* instance = reinterpret_cast<SkTypeface*>(static_cast<uintptr_t>(ptr));
    int count = instance->getVariationDesignParameters({});
    if (count > 0) {
        std::vector<SkFontParameters::Variation::Axis> params(count);
        instance->getVariationDesignParameters(params);
        jobjectArray res = env->NewObjectArray(count, skija::FontVariationAxis::cls, nullptr);
        for (int i=0; i < count; ++i) {
            jobject var = env->NewObject(skija::FontVariationAxis::cls, skija::FontVariationAxis::ctor, params[i].tag, params[i].min, params[i].def, params[i].max, params[i].isHidden());
            env->SetObjectArrayElement(res, i, var);
        }
        return res;
    } else
        return nullptr;
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_Typeface__1nGetUniqueId
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkTypeface* instance = reinterpret_cast<SkTypeface*>(static_cast<uintptr_t>(ptr));
    return instance->uniqueID();
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_Typeface__1nEquals
  (JNIEnv* env, jclass jclass, jlong ptr, jlong otherPtr) {
    SkTypeface* instance = reinterpret_cast<SkTypeface*>(static_cast<uintptr_t>(ptr));
    SkTypeface* other = reinterpret_cast<SkTypeface*>(static_cast<uintptr_t>(otherPtr));
    return SkTypeface::Equal(instance, other);
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_Typeface__1nMakeClone
  (JNIEnv* env, jclass jclass, jlong typefacePtr, jobject fontArgumentsObj) {
    SkTypeface* typeface = reinterpret_cast<SkTypeface*>(static_cast<uintptr_t>(typefacePtr));
    SkFontArguments args = skija::FontArguments::toSkFontArguments(env, fontArgumentsObj);
    SkTypeface* clone = typeface->makeClone(args).release();
    skija::FontArguments::freeSkFontArguments(args);
    return reinterpret_cast<jlong>(clone);
}

extern "C" JNIEXPORT jshortArray JNICALL Java_io_github_humbleui_skija_Typeface__1nGetUTF32Glyphs
  (JNIEnv* env, jclass jclass, jlong ptr, jintArray uniArr) {
    SkTypeface* instance = reinterpret_cast<SkTypeface*>(static_cast<uintptr_t>(ptr));
    jint count = env->GetArrayLength(uniArr);
    std::vector<SkGlyphID> glyphs(count);
    jint* uni = env->GetIntArrayElements(uniArr, nullptr);
    instance->unicharsToGlyphs(SkSpan(reinterpret_cast<SkUnichar*>(uni), count), glyphs);
    env->ReleaseIntArrayElements(uniArr, uni, 0);
    return javaShortArray(env, glyphs);
}

extern "C" JNIEXPORT jshort JNICALL Java_io_github_humbleui_skija_Typeface__1nGetUTF32Glyph
  (JNIEnv* env, jclass jclass, jlong ptr, jint uni) {
    SkTypeface* instance = reinterpret_cast<SkTypeface*>(static_cast<uintptr_t>(ptr));
    return instance->unicharToGlyph(uni);
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_Typeface__1nGetGlyphsCount
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkTypeface* instance = reinterpret_cast<SkTypeface*>(static_cast<uintptr_t>(ptr));
    return instance->countGlyphs();
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_Typeface__1nGetTablesCount
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkTypeface* instance = reinterpret_cast<SkTypeface*>(static_cast<uintptr_t>(ptr));
    return instance->countTables();
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_Typeface__1nGetTableTags
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkTypeface* instance = reinterpret_cast<SkTypeface*>(static_cast<uintptr_t>(ptr));
    int count = instance->countTables();
    std::vector<SkFontTableTag> tags(count);
    instance->readTableTags(tags);
    return javaIntArray(env, tags);
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_Typeface__1nGetTableSize
  (JNIEnv* env, jclass jclass, jlong ptr, jint tag) {
    SkTypeface* instance = reinterpret_cast<SkTypeface*>(static_cast<uintptr_t>(ptr));
    return instance->getTableSize(tag);
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_Typeface__1nGetTableData
  (JNIEnv* env, jclass jclass, jlong ptr, jint tag) {
    SkTypeface* instance = reinterpret_cast<SkTypeface*>(static_cast<uintptr_t>(ptr));
    SkData* data = instance->copyTableData(tag).release();
    return reinterpret_cast<jlong>(data);
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_Typeface__1nGetUnitsPerEm
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkTypeface* instance = reinterpret_cast<SkTypeface*>(static_cast<uintptr_t>(ptr));
    return instance->getUnitsPerEm();
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_Typeface__1nGetKerningPairAdjustments
  (JNIEnv* env, jclass jclass, jlong ptr, jshortArray glyphsArr) {
    SkTypeface* instance = reinterpret_cast<SkTypeface*>(static_cast<uintptr_t>(ptr));
    int count = glyphsArr == nullptr ? 0 : env->GetArrayLength(glyphsArr);
    if (count > 0) {
        std::vector<int32_t> adjustments(count);
        jshort* glyphs = env->GetShortArrayElements(glyphsArr, nullptr);
        bool res = instance->getKerningPairAdjustments(SkSpan(reinterpret_cast<SkGlyphID*>(glyphs), count), adjustments);
        env->ReleaseShortArrayElements(glyphsArr, glyphs, 0);
        return res ? javaIntArray(env, adjustments) : nullptr;
    } else {
        bool res = instance->getKerningPairAdjustments({}, {});
        return res ? javaIntArray(env, std::vector<jint>(0)) : nullptr;
    }
}

extern "C" JNIEXPORT jobjectArray JNICALL Java_io_github_humbleui_skija_Typeface__1nGetFamilyNames
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkTypeface* instance = reinterpret_cast<SkTypeface*>(static_cast<uintptr_t>(ptr));
    SkTypeface::LocalizedStrings* iter = instance->createFamilyNameIterator();
    std::vector<SkTypeface::LocalizedString> names;
    SkTypeface::LocalizedString name;
    while (iter->next(&name)) {
        names.push_back(name);
    }
    iter->unref();
    jobjectArray res = env->NewObjectArray((jsize) names.size(), skija::FontFamilyName::cls, nullptr);
    for (int i = 0; i < names.size(); ++i) {
        skija::AutoLocal<jstring> nameStr(env, javaString(env, names[i].fString));
        skija::AutoLocal<jstring> langStr(env, javaString(env, names[i].fLanguage));
        skija::AutoLocal<jobject> obj(env, env->NewObject(skija::FontFamilyName::cls, skija::FontFamilyName::ctor, nameStr.get(), langStr.get()));
        env->SetObjectArrayElement(res, i, obj.get());
    }
    return res;
}

extern "C" JNIEXPORT jstring JNICALL Java_io_github_humbleui_skija_Typeface__1nGetFamilyName
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkTypeface* instance = reinterpret_cast<SkTypeface*>(static_cast<uintptr_t>(ptr));
    SkString name;
    instance->getFamilyName(&name);
    return javaString(env, name);
}

extern "C" JNIEXPORT jobject JNICALL Java_io_github_humbleui_skija_Typeface__1nGetBounds
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkTypeface* instance = reinterpret_cast<SkTypeface*>(static_cast<uintptr_t>(ptr));
    return types::Rect::fromSkRect(env, instance->getBounds());
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_Typeface__1nOpenStream
  (JNIEnv* env, jclass jclass, jlong ptr, jintArray ttcIndexArray) {
    SkTypeface* instance = reinterpret_cast<SkTypeface*>(static_cast<uintptr_t>(ptr));
    int ttcIndex = 0;
    std::unique_ptr<SkStreamAsset> streamPtr = instance->openStream(&ttcIndex);
    SkStreamAsset* stream = streamPtr.release(); 
    
    if (ttcIndexArray != nullptr && env->GetArrayLength(ttcIndexArray) > 0) {
        env->SetIntArrayRegion(ttcIndexArray, 0, 1, reinterpret_cast<const jint*>(&ttcIndex));
    }
    
    return reinterpret_cast<jlong>(stream);
}
