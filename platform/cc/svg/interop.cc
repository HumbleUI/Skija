#include <jni.h>
#include "interop.hh"
#include "../interop.hh"

namespace skija {
    namespace svg {
        namespace SVGIRI {
            jclass cls;
            jmethodID ctor;

            void onLoad(JNIEnv* env) {
                jclass local = env->FindClass("io/github/humbleui/skija/svg/SVGLength");
                cls  = static_cast<jclass>(env->NewGlobalRef(local));
                ctor = env->GetMethodID(cls, "<init>", "(ILjava.lang.String;)V");
            }

            void onUnload(JNIEnv* env) {
                env->DeleteGlobalRef(cls);
            }

            jobject toJava(JNIEnv* env, const SkSVGIRI& paint) {
                return env->NewObject(cls, ctor, static_cast<jint>(paint.type()), javaString(env, paint.iri()));
            }
        }

        namespace SVGPaint {
            jclass cls;
            jmethodID ctorNone;
            jmethodID ctorColor;
            jmethodID ctorIRI;

            void onLoad(JNIEnv* env) {
                jclass local = env->FindClass("io/github/humbleui/skija/svg/SVGLength");
                cls  = static_cast<jclass>(env->NewGlobalRef(local));
                ctorNone = env->GetMethodID(cls, "<init>", "()V");
                ctorColor = env->GetMethodID(cls, "<init>", "(I)V");
                ctorIRI = env->GetMethodID(cls, "<init>", "(Lio.github.humbleui.skija.svg.SVGIRI;I)V");
            }

            void onUnload(JNIEnv* env) {
                env->DeleteGlobalRef(cls);
            }

            jobject toJava(JNIEnv* env, const SkSVGPaint& paint) {
                jobject result = nullptr;
                switch (paint.type()) {
                    case SkSVGPaint::Type::kNone:
                        result = env->NewObject(cls, ctorNone);
                        break;
// TODO: handle these cases
//                    case SkSVGPaint::Type::kColor:
//                        result = env->NewObject(cls, ctorColor, static_cast<jint>(paint.color()));
//                        break;
//                    case SkSVGPaint::Type::kIRI:
//                        result = env->NewObject(cls, ctorIRI, skija::svg::SVGIRI::toJava(paint.iri()), static_cast<jint>(paint.color()))
                }
                return result;
            }
        }

        namespace SVGLength {
            jclass cls;
            jmethodID ctor;

            void onLoad(JNIEnv* env) {
                jclass local = env->FindClass("io/github/humbleui/skija/svg/SVGLength");
                cls  = static_cast<jclass>(env->NewGlobalRef(local));
                ctor = env->GetMethodID(cls, "<init>", "(FI)V");
            }

            void onUnload(JNIEnv* env) {
                env->DeleteGlobalRef(cls);
            }

            jobject toJava(JNIEnv* env, const SkSVGLength& length) {
                return env->NewObject(cls, ctor, length.value(), static_cast<jint>(length.unit()));
            }
        }

        namespace SVGPreserveAspectRatio {
            jclass cls;
            jmethodID ctor;

            void onLoad(JNIEnv* env) {
                jclass local = env->FindClass("io/github/humbleui/skija/svg/SVGPreserveAspectRatio");
                cls  = static_cast<jclass>(env->NewGlobalRef(local));
                ctor = env->GetMethodID(cls, "<init>", "(II)V");
            }

            void onUnload(JNIEnv* env) {
                env->DeleteGlobalRef(cls);
            }

            jobject toJava(JNIEnv* env, const SkSVGPreserveAspectRatio& ratio) {
                return env->NewObject(cls, ctor, static_cast<jint>(ratio.fAlign), static_cast<jint>(ratio.fScale));
            }
        }

        void onLoad(JNIEnv* env) {
            SVGIRI::onLoad(env);
            SVGPaint::onLoad(env);
            SVGLength::onLoad(env);
            SVGPreserveAspectRatio::onLoad(env);
        }

        void onUnload(JNIEnv* env) {
            SVGIRI::onLoad(env);
            SVGPreserveAspectRatio::onUnload(env);
            SVGLength::onUnload(env);
            SVGPaint::onUnload(env);
        }
    }
}