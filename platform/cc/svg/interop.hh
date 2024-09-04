#pragma once
#include <jni.h>
#include "SkSVGTypes.h"

namespace skija {
    namespace svg {
        namespace SVGColor {
            extern jclass cls;
            extern jmethodID ctor;

            void onLoad(JNIEnv* env);
            void onUnload(JNIEnv* env);
            SkSVGColor fromJava(JNIEnv* env, jint jtype, jint color, jobjectArray vars);
            jobject toJava(JNIEnv* env, const SkSVGColor& color);
        }

        namespace SVGIRI {
            extern jclass cls;
            extern jmethodID ctor;

            void onLoad(JNIEnv* env);
            void onUnload(JNIEnv* env);
            SkSVGIRI fromJava(JNIEnv* env, jint jtype, jstring iri);
            jobject toJava(JNIEnv* env, const SkSVGIRI& paint);
        }

        namespace SVGPaint {
            extern jclass cls;
            extern jmethodID ctorNone;
            extern jmethodID ctorColor;
            extern jmethodID ctorIRI;

            void onLoad(JNIEnv* env);
            void onUnload(JNIEnv* env);
            SkSVGPaint fromJava(JNIEnv* env, jint jtype, jint colorType, jint color, jobjectArray vars, jint iriType, jstring iri);
            jobject toJava(JNIEnv* env, const SkSVGPaint& paint);
        }

        namespace SVGLength {
            extern jclass cls;
            extern jmethodID ctor;
            void onLoad(JNIEnv* env);
            void onUnload(JNIEnv* env);
            jobject toJava(JNIEnv* env, const SkSVGLength& length);
        }

        namespace SVGFontFamily {
            extern jclass cls;
            extern jmethodID ctor;
            void onLoad(JNIEnv* env);
            void onUnload(JNIEnv* env);
            jobject toJava(JNIEnv* env, const SkSVGFontFamily& family);
        }

        namespace SVGPreserveAspectRatio {
            extern jclass cls;
            extern jmethodID ctor;
            void onLoad(JNIEnv* env);
            void onUnload(JNIEnv* env);
            jobject toJava(JNIEnv* env, const SkSVGPreserveAspectRatio& ratio);
        }

        void onLoad(JNIEnv* env);
        void onUnload(JNIEnv* env);
    }
}