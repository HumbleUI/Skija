#include <jni.h>
#include "../interop.hh"
#include "interop.hh"
#include "SkSVGNode.h"
#include "SkSVGTypes.h"

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nGetTag
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>(instance->tag());
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nParseAndSetAttribute
  (JNIEnv* env, jclass jclass, jlong ptr, jstring nameStr, jstring valueStr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    SkString name = skString(env, nameStr);
    SkString value = skString(env, valueStr);
    return instance->parseAndSetAttribute(name.c_str(), value.c_str());
}

// Clip Rule

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nHasClipRule
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    return instance->getClipRule().isValue();
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nGetClipRule
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>((*(instance->getClipRule())).type());
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetClipRule
  (JNIEnv* env, jclass jclass, jlong ptr, jint type) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    SkSVGFillRule fill(static_cast<SkSVGFillRule::Type>(type));
    instance->setClipRule(SkSVGProperty<SkSVGFillRule, true>(fill));
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetClipRuleNull
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setClipRule(SkSVGProperty<SkSVGFillRule, true>(SkSVGPropertyState::kUnspecified));
}

// Color

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nHasColor
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    return instance->getColor().isValue();
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nGetColor
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>(*(instance->getColor()));
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetColor
  (JNIEnv* env, jclass jclass, jlong ptr, jint color) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setColor(SkSVGProperty<SkSVGColorType, true>(color));
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetColorNull
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setColor(SkSVGProperty<SkSVGColorType, true>(SkSVGPropertyState::kUnspecified));
}

// Color Interpolation

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nHasColorInterpolation
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    return instance->getColorInterpolation().isValue();
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nGetColorInterpolation
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>(*(instance->getColorInterpolation()));
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetColorInterpolation
  (JNIEnv* env, jclass jclass, jlong ptr, jint space) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setColorInterpolation(SkSVGProperty<SkSVGColorspace, true>(static_cast<SkSVGColorspace>(space)));
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetColorInterpolationNull
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setColorInterpolation(SkSVGProperty<SkSVGColorspace, true>(SkSVGPropertyState::kUnspecified));
}

// Color Interpolation Filters

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nHasColorInterpolationFilters
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    return instance->getColorInterpolationFilters().isValue();
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nGetColorInterpolationFilters
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>(*(instance->getColorInterpolationFilters()));
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetColorInterpolationFilters
  (JNIEnv* env, jclass jclass, jlong ptr, jint space) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setColorInterpolationFilters(SkSVGProperty<SkSVGColorspace, true>(static_cast<SkSVGColorspace>(space)));
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetColorInterpolationFiltersNull
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setColorInterpolationFilters(SkSVGProperty<SkSVGColorspace, true>(SkSVGPropertyState::kUnspecified));
}

// Fill Rule

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nHasFillRule
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    return instance->getFillRule().isValue();
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nGetFillRule
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>((*(instance->getFillRule())).type());
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetFillRule
  (JNIEnv* env, jclass jclass, jlong ptr, jint type) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    SkSVGFillRule fill(static_cast<SkSVGFillRule::Type>(type));
    instance->setFillRule(SkSVGProperty<SkSVGFillRule, true>(fill));
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetFillRuleNull
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setFillRule(SkSVGProperty<SkSVGFillRule, true>(SkSVGPropertyState::kUnspecified));
}

// Fill

extern "C" JNIEXPORT jobject JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nGetFill
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    SkSVGProperty<SkSVGPaint, true> property = instance->getFill();
    return property.isValue() ? skija::svg::SVGPaint::toJava(env, *property) : nullptr;
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetFill
  (JNIEnv* env, jclass jclass, jlong ptr, jint type, jint colorType, jint color, jobjectArray vars, jint iriType, jstring iri) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setFill(SkSVGProperty<SkSVGPaint, true>(skija::svg::SVGPaint::fromJava(env, type, colorType, color, vars, iriType, iri)));
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetFillNull
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setFill(SkSVGProperty<SkSVGPaint, true>(SkSVGPropertyState::kUnspecified));
}

// Stroke Width

extern "C" JNIEXPORT jobject JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nGetStrokeWidth
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    SkSVGProperty<SkSVGLength, true> property = instance->getStrokeWidth();
    return property.isValue() ? skija::svg::SVGLength::toJava(env, *property) : nullptr;
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetStrokeWidth
  (JNIEnv* env, jclass jclass, jlong ptr, jfloat value, jint unit) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    SkSVGLength length(value, static_cast<SkSVGLength::Unit>(unit));
    instance->setStrokeWidth(SkSVGProperty<SkSVGLength, true>(length));
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetStrokeWidthNull
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setStrokeWidth(SkSVGProperty<SkSVGLength, true>(SkSVGPropertyState::kUnspecified));
}

