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

// Fill Opacity

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nHasFillOpacity
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    return instance->getFillOpacity().isValue();
}

extern "C" JNIEXPORT jfloat JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nGetFillOpacity
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    return *(instance->getFillOpacity());
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetFillOpacity
  (JNIEnv* env, jclass jclass, jlong ptr, jfloat opacity) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setFillOpacity(SkSVGProperty<SkSVGNumberType, true>(opacity));
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetFillOpacityNull
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setFillOpacity(SkSVGProperty<SkSVGNumberType, true>(SkSVGPropertyState::kUnspecified));
}

// Font Family

extern "C" JNIEXPORT jobject JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nGetFontFamily
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    SkSVGProperty<SkSVGFontFamily, true> property = instance->getFontFamily();
    return property.isValue() ? skija::svg::SVGFontFamily::toJava(env, *property) : nullptr;
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetFontFamily
  (JNIEnv* env, jclass jclass, jlong ptr, jint type, jstring family) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    SkSVGFontFamily::Type castType = static_cast<SkSVGFontFamily::Type>(type);
    if (castType == SkSVGFontFamily::Type::kFamily) {
        instance->setFontFamily(SkSVGProperty<SkSVGFontFamily, true>(SkSVGFontFamily(skString(env, family).c_str())));
    } else {
        instance->setFontFamily(SkSVGProperty<SkSVGFontFamily, true>(SkSVGFontFamily()));
    }
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetFontFamilyNull
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setFontFamily(SkSVGProperty<SkSVGFontFamily, true>(SkSVGPropertyState::kUnspecified));
}

// Font Size

extern "C" JNIEXPORT jobject JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nGetFontSize
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    SkSVGProperty<SkSVGFontSize, true> property = instance->getFontSize();
    return property.isValue() ? skija::svg::SVGFontSize::toJava(env, *property) : nullptr;
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetFontSize
  (JNIEnv* env, jclass jclass, jlong ptr, jint type, jfloat value, jint unit) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    SkSVGLength length(value, static_cast<SkSVGLength::Unit>(unit));
    SkSVGFontSize::Type castType = static_cast<SkSVGFontSize::Type>(type);
    if (castType == SkSVGFontSize::Type::kLength) {
        instance->setFontSize(SkSVGProperty<SkSVGFontSize, true>(SkSVGFontSize(length)));
    } else {
        instance->setFontSize(SkSVGProperty<SkSVGFontSize, true>(SkSVGFontSize()));
    }
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetFontSizeNull
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setFontSize(SkSVGProperty<SkSVGFontSize, true>(SkSVGPropertyState::kUnspecified));
}

// Font Style

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nHasFontStyle
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    return instance->getFontStyle().isValue();
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nGetFontStyle
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>((*(instance->getFontStyle())).type());
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetFontStyle
  (JNIEnv* env, jclass jclass, jlong ptr, jint type) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setFontStyle(SkSVGProperty<SkSVGFontStyle, true>(SkSVGFontStyle(static_cast<SkSVGFontStyle::Type>(type))));
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetFontStyleNull
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setFontStyle(SkSVGProperty<SkSVGFontStyle, true>(SkSVGPropertyState::kUnspecified));
}

// Font Weight

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nHasFontWeight
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    return instance->getFontWeight().isValue();
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nGetFontWeight
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>((*(instance->getFontWeight())).type());
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetFontWeight
  (JNIEnv* env, jclass jclass, jlong ptr, jint type) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setFontWeight(SkSVGProperty<SkSVGFontWeight, true>(SkSVGFontWeight(static_cast<SkSVGFontWeight::Type>(type))));
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetFontWeightNull
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setFontWeight(SkSVGProperty<SkSVGFontWeight, true>(SkSVGPropertyState::kUnspecified));
}

// Stroke

extern "C" JNIEXPORT jobject JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nGetStroke
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    SkSVGProperty<SkSVGPaint, true> property = instance->getStroke();
    return property.isValue() ? skija::svg::SVGPaint::toJava(env, *property) : nullptr;
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetStroke
  (JNIEnv* env, jclass jclass, jlong ptr, jint type, jint colorType, jint color, jobjectArray vars, jint iriType, jstring iri) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setStroke(SkSVGProperty<SkSVGPaint, true>(skija::svg::SVGPaint::fromJava(env, type, colorType, color, vars, iriType, iri)));
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetStrokeNull
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setStroke(SkSVGProperty<SkSVGPaint, true>(SkSVGPropertyState::kUnspecified));
}

// Stroke Dash Array

extern "C" JNIEXPORT jobject JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nGetStrokeDashArray
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    SkSVGProperty<SkSVGDashArray, true> property = instance->getStrokeDashArray();
    return property.isValue() ? skija::svg::SVGDashArray::toJava(env, *property) : nullptr;
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetStrokeDashArray
  (JNIEnv* env, jclass jclass, jlong ptr, jint type, jobjectArray dashArray) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setStrokeDashArray(SkSVGProperty<SkSVGDashArray, true>(skija::svg::SVGDashArray::fromJava(env, type, dashArray)));
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetStrokeDashArrayNull
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setStrokeDashArray(SkSVGProperty<SkSVGDashArray, true>(SkSVGPropertyState::kUnspecified));
}

// Stroke Offset

extern "C" JNIEXPORT jobject JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nGetStrokeDashOffset
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    SkSVGProperty<SkSVGLength, true> property = instance->getStrokeDashOffset();
    return property.isValue() ? skija::svg::SVGLength::toJava(env, *property) : nullptr;
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetStrokeDashOffset
  (JNIEnv* env, jclass jclass, jlong ptr, jfloat value, jint unit) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    SkSVGLength length(value, static_cast<SkSVGLength::Unit>(unit));
    instance->setStrokeDashOffset(SkSVGProperty<SkSVGLength, true>(length));
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetStrokeDashOffsetNull
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setStrokeDashOffset(SkSVGProperty<SkSVGLength, true>(SkSVGPropertyState::kUnspecified));
}

// Stroke Line Cap

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nHasStrokeLineCap
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    return instance->getStrokeLineCap().isValue();
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nGetStrokeLineCap
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>(*(instance->getStrokeLineCap()));
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetStrokeLineCap
  (JNIEnv* env, jclass jclass, jlong ptr, jint type) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setStrokeLineCap(SkSVGProperty<SkSVGLineCap, true>(static_cast<SkSVGLineCap>(type)));
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetStrokeLineCapNull
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setStrokeLineCap(SkSVGProperty<SkSVGLineCap, true>(SkSVGPropertyState::kUnspecified));
}

// Stroke Line Join

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nHasStrokeLineJoin
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    return instance->getStrokeLineJoin().isValue();
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nGetStrokeLineJoin
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>((*(instance->getStrokeLineJoin())).type());
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetStrokeLineJoin
  (JNIEnv* env, jclass jclass, jlong ptr, jint type) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setStrokeLineJoin(SkSVGProperty<SkSVGLineJoin, true>(SkSVGLineJoin(static_cast<SkSVGLineJoin::Type>(type))));
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetStrokeLineJoinNull
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setStrokeLineJoin(SkSVGProperty<SkSVGLineJoin, true>(SkSVGPropertyState::kUnspecified));
}

// Stroke Miter Limit

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nHasStrokeMiterLimit
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    return instance->getStrokeMiterLimit().isValue();
}

extern "C" JNIEXPORT jfloat JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nGetStrokeMiterLimit
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    return *(instance->getStrokeMiterLimit());
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetStrokeMiterLimit
  (JNIEnv* env, jclass jclass, jlong ptr, jfloat opacity) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setStrokeMiterLimit(SkSVGProperty<SkSVGNumberType, true>(opacity));
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetStrokeMiterLimitNull
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setStrokeMiterLimit(SkSVGProperty<SkSVGNumberType, true>(SkSVGPropertyState::kUnspecified));
}

// Stroke Opacity

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nHasStrokeOpacity
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    return instance->getStrokeOpacity().isValue();
}

extern "C" JNIEXPORT jfloat JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nGetStrokeOpacity
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    return *(instance->getStrokeOpacity());
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetStrokeOpacity
  (JNIEnv* env, jclass jclass, jlong ptr, jfloat opacity) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setStrokeOpacity(SkSVGProperty<SkSVGNumberType, true>(opacity));
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetStrokeOpacityNull
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    instance->setStrokeOpacity(SkSVGProperty<SkSVGNumberType, true>(SkSVGPropertyState::kUnspecified));
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

