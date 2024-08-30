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
            jobject toJava(JNIEnv* env, const SkSVGColor& color);
        }

        namespace SVGIRI {
            extern jclass cls;
            extern jmethodID ctor;

            void onLoad(JNIEnv* env);
            void onUnload(JNIEnv* env);
            jobject toJava(JNIEnv* env, const SkSVGIRI& paint);
        }

        namespace SVGPaint {
            extern jclass cls;
            extern jmethodID ctorNone;
            extern jmethodID ctorColor;
            extern jmethodID ctorIRI;

            void onLoad(JNIEnv* env);
            void onUnload(JNIEnv* env);
            jobject toJava(JNIEnv* env, const SkSVGPaint& paint);
        }

        namespace SVGLength {
            extern jclass cls;
            extern jmethodID ctor;
            void onLoad(JNIEnv* env);
            void onUnload(JNIEnv* env);
            jobject toJava(JNIEnv* env, const SkSVGLength& length);
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