#include <iostream>
#include <jni.h>
#include "../interop.hh"
#include "interop.hh"
#include "FontRunIterator.hh"
#include "SkShaper.h"
#include "src/base/SkUTF.h"
#include "TextLineRunHandler.hh"
#include "RunIterators.hh"
#include "unicode/ubidi.h"
#include "SkUnicode.h"

static void deleteShaper(SkShaper* instance) {
    // std::cout << "Deleting [SkShaper " << instance << "]" << std::endl;
    delete instance;
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_shaper_Shaper__1nGetFinalizer(JNIEnv* env, jclass jclass) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deleteShaper));
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_shaper_Shaper__1nMakePrimitive
  (JNIEnv* env, jclass jclass) {
    return reinterpret_cast<jlong>(SkShaper::MakePrimitive().release());
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_shaper_Shaper__1nMakeShaperDrivenWrapper
  (JNIEnv* env, jclass jclass, jlong fontMgrPtr) {
    SkFontMgr* fontMgr = reinterpret_cast<SkFontMgr*>(static_cast<uintptr_t>(fontMgrPtr));
    return reinterpret_cast<jlong>(SkShaper::MakeShaperDrivenWrapper(sk_ref_sp(fontMgr)).release());
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_shaper_Shaper__1nMakeShapeThenWrap
  (JNIEnv* env, jclass jclass, jlong fontMgrPtr) {
    SkFontMgr* fontMgr = reinterpret_cast<SkFontMgr*>(static_cast<uintptr_t>(fontMgrPtr));
    return reinterpret_cast<jlong>(SkShaper::MakeShapeThenWrap(sk_ref_sp(fontMgr)).release());
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_shaper_Shaper__1nMakeShapeDontWrapOrReorder
  (JNIEnv* env, jclass jclass, jlong fontMgrPtr) {
    SkFontMgr* fontMgr = reinterpret_cast<SkFontMgr*>(static_cast<uintptr_t>(fontMgrPtr));
    return reinterpret_cast<jlong>(SkShaper::MakeShapeDontWrapOrReorder(SkUnicode::MakeIcuBasedUnicode(), sk_ref_sp(fontMgr)).release());
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_shaper_Shaper__1nMakeCoreText
  (JNIEnv* env, jclass jclass) {
    #ifdef SK_SHAPER_CORETEXT_AVAILABLE
        return reinterpret_cast<jlong>(SkShaper::MakeCoreText().release());
    #else
        return 0;
    #endif
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_shaper_Shaper__1nMake
  (JNIEnv* env, jclass jclass, jlong fontMgrPtr) {
    SkFontMgr* fontMgr = reinterpret_cast<SkFontMgr*>(static_cast<uintptr_t>(fontMgrPtr));
    return reinterpret_cast<jlong>(SkShaper::Make(sk_ref_sp(fontMgr)).release());
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_shaper_Shaper__1nShapeBlob
  (JNIEnv* env, jclass jclass, jlong ptr, jstring textObj, jlong fontPtr, jobject opts, jfloat width, jfloat offsetX, jfloat offsetY) {
    SkShaper* instance = reinterpret_cast<SkShaper*>(static_cast<uintptr_t>(ptr));
    SkString text = skString(env, textObj);
    std::shared_ptr<UBreakIterator> graphemeIter = skija::shaper::graphemeBreakIterator(text);
    if (!graphemeIter) return 0;
    SkFont* font = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(fontPtr));
    std::vector<SkShaper::Feature> features = skija::shaper::ShapingOptions::getFeatures(env, opts);

    std::unique_ptr<SkShaper::FontRunIterator> fontRunIter(new FontRunIterator(
        text.c_str(),
        text.size(),
        *font,
        SkFontMgr::RefDefault(),
        graphemeIter,
        env->GetBooleanField(opts, skija::shaper::ShapingOptions::_approximateSpaces),
        env->GetBooleanField(opts, skija::shaper::ShapingOptions::_approximatePunctuation)
    ));
    if (!fontRunIter) return 0;

    uint8_t defaultBiDiLevel = env->GetBooleanField(opts, skija::shaper::ShapingOptions::_leftToRight) ? UBIDI_DEFAULT_LTR : UBIDI_DEFAULT_RTL;
    std::unique_ptr<SkShaper::BiDiRunIterator> bidiRunIter(SkShaper::MakeBiDiRunIterator(text.c_str(), text.size(), defaultBiDiLevel));
    if (!bidiRunIter) return 0;

    std::unique_ptr<SkShaper::ScriptRunIterator> scriptRunIter(SkShaper::MakeHbIcuScriptRunIterator(text.c_str(), text.size()));
    if (!scriptRunIter) return 0;

    std::unique_ptr<SkShaper::LanguageRunIterator> languageRunIter(SkShaper::MakeStdLanguageRunIterator(text.c_str(), text.size()));
    if (!languageRunIter) return 0;

    SkTextBlobBuilderRunHandler rh(text.c_str(), {offsetX, offsetY});
    instance->shape(text.c_str(), text.size(), *fontRunIter, *bidiRunIter, *scriptRunIter, *languageRunIter, features.data(), features.size(), width, &rh);
    SkTextBlob* blob = rh.makeBlob().release();
    
    return reinterpret_cast<jlong>(blob);
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_shaper_Shaper__1nShapeLine
  (JNIEnv* env, jclass jclass, jlong ptr, jstring textObj, jlong fontPtr, jobject opts) {
    SkShaper* instance = reinterpret_cast<SkShaper*>(static_cast<uintptr_t>(ptr));

    SkString text = skString(env, textObj);
    std::shared_ptr<UBreakIterator> graphemeIter = skija::shaper::graphemeBreakIterator(text);
    if (!graphemeIter) return 0;

    SkFont* font = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(fontPtr));
    std::vector<SkShaper::Feature> features = skija::shaper::ShapingOptions::getFeatures(env, opts);

    std::unique_ptr<SkShaper::FontRunIterator> fontRunIter(new FontRunIterator(
        text.c_str(),
        text.size(),
        *font,
        SkFontMgr::RefDefault(),
        graphemeIter, 
        env->GetBooleanField(opts, skija::shaper::ShapingOptions::_approximateSpaces),
        env->GetBooleanField(opts, skija::shaper::ShapingOptions::_approximatePunctuation)));
    if (!fontRunIter) return 0;

    uint8_t defaultBiDiLevel = env->GetBooleanField(opts, skija::shaper::ShapingOptions::_leftToRight) ? UBIDI_DEFAULT_LTR : UBIDI_DEFAULT_RTL;
    std::unique_ptr<SkShaper::BiDiRunIterator> bidiRunIter(SkShaper::MakeBiDiRunIterator(text.c_str(), text.size(), defaultBiDiLevel));
    if (!bidiRunIter) return 0;

    std::unique_ptr<SkShaper::ScriptRunIterator> scriptRunIter(SkShaper::MakeHbIcuScriptRunIterator(text.c_str(), text.size()));
    if (!scriptRunIter) return 0;

    std::unique_ptr<SkShaper::LanguageRunIterator> languageRunIter(SkShaper::MakeStdLanguageRunIterator(text.c_str(), text.size()));
    if (!languageRunIter) return 0;

    TextLine* line;
    if (text.size() == 0)
        line = new TextLine(*font);
    else {
        TextLineRunHandler rh(text, graphemeIter);
        instance->shape(text.c_str(), text.size(), *fontRunIter, *bidiRunIter, *scriptRunIter, *languageRunIter, features.data(), features.size(), std::numeric_limits<float>::infinity(), &rh);
        line = rh.makeLine().release();
    }
    return reinterpret_cast<jlong>(line);
}

class SkijaRunHandler: public SkShaper::RunHandler {
public:
    SkijaRunHandler(JNIEnv* env, jobject runHandler, const SkString& text):
        fEnv(env),
        fRunHandler(runHandler),
        fIndicesConverter(text.c_str(), text.size())
    {}

    void beginLine() {
        fEnv->CallVoidMethod(fRunHandler, skija::shaper::RunHandler::beginLine);
        java::lang::Throwable::exceptionThrown(fEnv);
    }

    void runInfo(const SkShaper::RunHandler::RunInfo& info) {
        skija::AutoLocal<jobject> runInfoObj(fEnv, skija::shaper::RunInfo::toJava(fEnv, info, fIndicesConverter));
        fEnv->CallVoidMethod(fRunHandler, skija::shaper::RunHandler::runInfo, runInfoObj.get());
        java::lang::Throwable::exceptionThrown(fEnv);
        fEnv->SetLongField(runInfoObj.get(), skija::shaper::RunInfo::_fontPtr, 0);
    }

    void commitRunInfo() {
        fEnv->CallVoidMethod(fRunHandler, skija::shaper::RunHandler::commitRunInfo);
        java::lang::Throwable::exceptionThrown(fEnv);
    }

    SkShaper::RunHandler::Buffer runBuffer(const SkShaper::RunHandler::RunInfo& info) {
        fGlyphs    = std::vector<jshort>(info.glyphCount);
        fPositions = std::vector<SkPoint>(info.glyphCount);
        fClusters  = std::vector<jint>(info.glyphCount);

        skija::AutoLocal<jobject> runInfoObj(fEnv, skija::shaper::RunInfo::toJava(fEnv, info, fIndicesConverter));
        skija::AutoLocal<jobject> point(fEnv, fEnv->CallObjectMethod(fRunHandler, skija::shaper::RunHandler::runOffset, runInfoObj.get()));
        java::lang::Throwable::exceptionThrown(fEnv);
        fEnv->SetLongField(runInfoObj.get(), skija::shaper::RunInfo::_fontPtr, 0);

        jfloat x = fEnv->GetFloatField(point.get(), types::Point::x);
        jfloat y = fEnv->GetFloatField(point.get(), types::Point::y);

        return SkShaper::RunHandler::Buffer{
            reinterpret_cast<SkGlyphID*>(fGlyphs.data()),
            fPositions.data(),
            nullptr,
            reinterpret_cast<uint32_t*>(fClusters.data()),
            {x, y}};
    }

    void commitRunBuffer(const SkShaper::RunHandler::RunInfo& info) {
        size_t begin = fIndicesConverter.from8To16(info.utf8Range.fBegin);
        for (int i = 0; i < fClusters.size(); ++i)
            fClusters[i] = fIndicesConverter.from8To16(fClusters[i]);
        skija::AutoLocal<jintArray> clusters(fEnv, javaIntArray(fEnv, fClusters));
        size_t end = fIndicesConverter.from8To16(info.utf8Range.fBegin + info.utf8Range.fSize);
        skija::AutoLocal<jobject> runInfoObj(fEnv, skija::shaper::RunInfo::toJava(fEnv, info, begin, end));
        skija::AutoLocal<jshortArray> glyphs(fEnv, javaShortArray(fEnv, fGlyphs));
        skija::AutoLocal<jobjectArray> positions(fEnv, types::Point::fromSkPoints(fEnv, fPositions));
        fEnv->CallVoidMethod(fRunHandler, skija::shaper::RunHandler::commitRun, runInfoObj.get(), glyphs.get(), positions.get(), clusters.get());
        java::lang::Throwable::exceptionThrown(fEnv);
        fEnv->SetLongField(runInfoObj.get(), skija::shaper::RunInfo::_fontPtr, 0);
    }

    void commitLine() {
        fEnv->CallVoidMethod(fRunHandler, skija::shaper::RunHandler::commitLine);
        java::lang::Throwable::exceptionThrown(fEnv);
    }

private:
    JNIEnv* fEnv;
    jobject fRunHandler;
    skija::UtfIndicesConverter fIndicesConverter;
    std::vector<jshort> fGlyphs;
    std::vector<SkPoint> fPositions;
    std::vector<jint> fClusters;
};

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_shaper_Shaper__1nShape
  (JNIEnv* env, jclass jclass, jlong ptr, jlong textPtr, jobject fontRunIterObj, jobject bidiRunIterObj, jobject scriptRunIterObj, jobject languageRunIterObj, jobject opts, jfloat width, jobject runHandlerObj)
{
    SkShaper* instance = reinterpret_cast<SkShaper*>(static_cast<uintptr_t>(ptr));
    SkString* text = reinterpret_cast<SkString*>(static_cast<uintptr_t>(textPtr));

    auto nativeFontRunIter = (SkShaper::FontRunIterator*) skija::impl::Native::fromJava(env, fontRunIterObj, skija::shaper::FontMgrRunIterator::cls);
    std::unique_ptr<SkijaFontRunIterator> localFontRunIter;
    if (nativeFontRunIter == nullptr)
        localFontRunIter.reset(new SkijaFontRunIterator(env, fontRunIterObj, *text));

    auto nativeBidiRunIter = (SkShaper::BiDiRunIterator*) skija::impl::Native::fromJava(env, bidiRunIterObj, skija::shaper::IcuBidiRunIterator::cls);
    std::unique_ptr<SkijaBidiRunIterator> localBidiRunIter;
    if (nativeBidiRunIter == nullptr)
        localBidiRunIter.reset(new SkijaBidiRunIterator(env, bidiRunIterObj, *text));

    auto nativeScriptRunIter = (SkShaper::ScriptRunIterator*) skija::impl::Native::fromJava(env, scriptRunIterObj, skija::shaper::HbIcuScriptRunIterator::cls);
    std::unique_ptr<SkijaScriptRunIterator> localScriptRunIter;
    if (nativeScriptRunIter == nullptr)
        localScriptRunIter.reset(new SkijaScriptRunIterator(env, scriptRunIterObj, *text));
    
    auto languageRunIter = SkijaLanguageRunIterator(env, languageRunIterObj, *text);

    std::vector<SkShaper::Feature> features = skija::shaper::ShapingOptions::getFeatures(env, opts);
    
    auto nativeRunHandler = (SkShaper::RunHandler*) skija::impl::Native::fromJava(env, runHandlerObj, skija::shaper::TextBlobBuilderRunHandler::cls);
    if (nativeRunHandler == nullptr)
        nativeRunHandler = (SkShaper::RunHandler*) skija::impl::Native::fromJava(env, runHandlerObj, skija::shaper::TextLineRunHandler::cls);
    std::unique_ptr<SkijaRunHandler> localRunHandler;
    if (nativeRunHandler == nullptr)
        localRunHandler.reset(new SkijaRunHandler(env, runHandlerObj, *text));

    instance->shape(text->c_str(), text->size(),
        nativeFontRunIter != nullptr ? *nativeFontRunIter : *localFontRunIter,
        nativeBidiRunIter != nullptr ? *nativeBidiRunIter : *localBidiRunIter,
        nativeScriptRunIter != nullptr ? *nativeScriptRunIter : *localScriptRunIter,
        languageRunIter,
        features.data(),
        features.size(),
        width,
        nativeRunHandler != nullptr ? nativeRunHandler : localRunHandler.get());
}