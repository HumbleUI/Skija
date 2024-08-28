#include <jni.h>
#include "interop.hh"

namespace skija {
    namespace svg {
        namespace SVGProperty {
            jclass cls;
            jmethodID ctor;

            void onLoad(JNIEnv* env) {
                jclass local = env->FindClass("io/github/humbleui/skija/svg/SVGProperty");
                cls  = static_cast<jclass>(env->NewGlobalRef(local));
                ctor = env->GetMethodID(cls, "<init>", "(Ljava/lang/Object;Z)V");
            }

            void onUnload(JNIEnv* env) {
                env->DeleteGlobalRef(cls);
            }

            jobject toJava(JNIEnv* env, jobject value, bool isInheritable) {
                return env->NewObject(cls, ctor, value, isInheritable);
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

            jobject toJava(JNIEnv* env, SkSVGLength length) {
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

            jobject toJava(JNIEnv* env, SkSVGPreserveAspectRatio ratio) {
                return env->NewObject(cls, ctor, static_cast<jint>(ratio.fAlign), static_cast<jint>(ratio.fScale));
            }
        }

        void onLoad(JNIEnv* env) {
            SVGProperty::onLoad(env);
            SVGLength::onLoad(env);
            SVGPreserveAspectRatio::onLoad(env);
        }

        void onUnload(JNIEnv* env) {
            SVGPreserveAspectRatio::onUnload(env);
            SVGLength::onUnload(env);
            SVGProperty::onUnload(env);
        } 
    }
}