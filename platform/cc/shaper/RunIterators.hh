#pragma once

#include <jni.h>
#include "../interop.hh"
#include "interop.hh"
#include "SkShaper.h"

template <typename RunIteratorSubclass>
class SkijaRunIterator: public RunIteratorSubclass {
public:
    SkijaRunIterator(JNIEnv* env, jobject obj, SkString& text):
      fEnv(env),
      fIteratorObj(obj),
      fIndicesConverter(text.c_str(), text.size()),
      fEndOfRun(0)
    {
        fHasNext = fEnv->CallBooleanMethod(fIteratorObj, java::util::Iterator::hasNext);
        java::lang::Throwable::exceptionThrown(fEnv);
    }

    void consume() override {
        SkASSERT(fHasNext);
        skija::AutoLocal<jobject> runObj(fEnv, fEnv->CallObjectMethod(fIteratorObj, java::util::Iterator::next));
        java::lang::Throwable::exceptionThrown(fEnv);
        jint endOfRun16 = onConsume(runObj.get());
        fEndOfRun = fIndicesConverter.from16To8(endOfRun16);
        fHasNext = fEnv->CallBooleanMethod(fIteratorObj, java::util::Iterator::hasNext);
        java::lang::Throwable::exceptionThrown(fEnv);
    }

    size_t endOfCurrentRun() const override {
        return fEndOfRun;
    }
    
    bool atEnd() const override {
        return !fHasNext;
    }

protected:
    JNIEnv* fEnv;
    jobject fIteratorObj;
    skija::UtfIndicesConverter fIndicesConverter;
    size_t fEndOfRun;
    bool fHasNext;

    virtual jint onConsume(jobject) = 0;
};

class SkijaFontRunIterator: public SkijaRunIterator<SkShaper::FontRunIterator> {
public:
    SkijaFontRunIterator(JNIEnv* env, jobject obj, SkString text): 
      SkijaRunIterator<SkShaper::FontRunIterator>(env, obj, text)
    {}

    const SkFont& currentFont() const override {
        return *fFont;
    }

    jint onConsume(jobject runObj) override {
        jlong fontPtr = fEnv->CallLongMethod(runObj, skija::shaper::FontRun::_getFontPtr);
        java::lang::Throwable::exceptionThrown(fEnv);
        fFont = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(fontPtr));
        return fEnv->GetIntField(runObj, skija::shaper::FontRun::_end);
    }

protected:
    SkFont* fFont;
};

class SkijaBidiRunIterator: public SkijaRunIterator<SkShaper::BiDiRunIterator> {
public:
    SkijaBidiRunIterator(JNIEnv* env, jobject obj, SkString& text): 
      SkijaRunIterator<SkShaper::BiDiRunIterator>(env, obj, text)
    {}

    uint8_t currentLevel() const override {
        return fLevel;
    }

    jint onConsume(jobject runObj) override {
        fLevel = fEnv->GetIntField(runObj, skija::shaper::BidiRun::_level) & 0xFF;
        return fEnv->GetIntField(runObj, skija::shaper::BidiRun::_end);
    }
    
protected:
    uint8_t fLevel;
};

class SkijaScriptRunIterator: public SkijaRunIterator<SkShaper::ScriptRunIterator> {
public:
    SkijaScriptRunIterator(JNIEnv* env, jobject obj, SkString text):
      SkijaRunIterator<SkShaper::ScriptRunIterator>(env, obj, text)
    {}

    SkFourByteTag currentScript() const override {
        return fScript;
    }

    jint onConsume(jobject runObj) override {
        fScript = fEnv->GetIntField(runObj, skija::shaper::ScriptRun::_scriptTag);
        return fEnv->GetIntField(runObj, skija::shaper::ScriptRun::_end);
    }
    
protected:
    uint32_t fScript;
};

class SkijaLanguageRunIterator: public SkijaRunIterator<SkShaper::LanguageRunIterator> {
public:
    SkijaLanguageRunIterator(JNIEnv* env, jobject obj, SkString text):
      SkijaRunIterator<SkShaper::LanguageRunIterator>(env, obj, text)
    {}

    const char* currentLanguage() const override {
        // std::cout << "fEndOfRun = " << fEndOfRun << ", currentLanguage() = " << fLang.c_str() << std::endl;
        return fLang.c_str();
    }

    jint onConsume(jobject runObj) override {
        jstring langObj = static_cast<jstring>(fEnv->GetObjectField(runObj, skija::shaper::LanguageRun::_language));
        fLang = skString(fEnv, langObj);
        // std::cout << "fLang = " << fLang.c_str() << std::endl;
        return fEnv->GetIntField(runObj, skija::shaper::LanguageRun::_end);
    }
    
protected:
    SkString fLang;
};