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

extern "C" JNIEXPORT jobject JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nGetStrokeWidth
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    SkSVGProperty<SkSVGLength, true> property = instance->getStrokeWidth();
    return property.isValue() ? skija::svg::SVGLength::toJava(env, *property) : nullptr;
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetStrokeWidthValue
  (JNIEnv* env, jclass jclass, jlong ptr, jfloat value, jint unit) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    SkSVGLength length(value, static_cast<SkSVGLength::Unit>(unit));
    SkSVGProperty<SkSVGLength, true> prop(length);
    instance->setStrokeWidth(prop);
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_svg_SVGNode__1nSetStrokeWidthNull
  (JNIEnv* env, jclass jclass, jlong ptr, jint state) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    SkSVGProperty<SkSVGLength, true> prop(static_cast<SkSVGPropertyState>(state));
    instance->setStrokeWidth(prop);
}

