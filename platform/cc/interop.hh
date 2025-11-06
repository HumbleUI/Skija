#pragma once
#include <iostream>
#include <jni.h>
#include <memory>
#include <vector>
#include "SkCodec.h"
#include "SkFontArguments.h"
#include "SkFontMetrics.h"
#include "SkFontStyle.h"
#include "SkImageInfo.h"
#include "SkMatrix.h"
#include "SkM44.h"
#include "SkPaint.h"
#include "SkRefCnt.h"
#include "SkRect.h"
#include "SkRRect.h"
#include "SkScalar.h"
#include "SkShaper.h"
#include "SkString.h"
#include "SkSurfaceProps.h"

namespace java {
    namespace io {
        namespace OutputStream {
            extern jmethodID write;
            extern jmethodID flush;
            void onLoad(JNIEnv* env);
        }
    }

    namespace lang {
        namespace Float {
            extern jclass cls;
            extern jmethodID ctor;
            void onLoad(JNIEnv* env);
            void onUnload(JNIEnv* env);
        }

        namespace RuntimeException {
            extern jclass cls;
            void onLoad(JNIEnv* env);
            void onUnload(JNIEnv* env);
        }

        namespace String {
            extern jclass cls;
            void onLoad(JNIEnv* env);
            void onUnload(JNIEnv* env);
        }

        namespace Throwable {
            extern jmethodID printStackTrace;
            void onLoad(JNIEnv* env);
            bool exceptionThrown(JNIEnv* env);
        }
    }

    namespace util {
        namespace Iterator {
            extern jclass cls;
            extern jmethodID next;
            extern jmethodID hasNext;
            void onLoad(JNIEnv* env);
            void onUnload(JNIEnv* env);
        }

        namespace function {
            namespace BooleanSupplier {
                extern jclass cls;
                extern jmethodID apply;
                void onLoad(JNIEnv* env);
                void onUnload(JNIEnv* env);
            }
        }
    }

    void onLoad(JNIEnv* env);
    void onUnload(JNIEnv* env);
}

namespace skija {
    namespace AnimationFrameInfo {
        extern jclass cls;
        extern jmethodID ctor;
        void onLoad(JNIEnv* env);
        void onUnload(JNIEnv* env);
        jobject toJava(JNIEnv* env, const SkCodec::FrameInfo& i);
    }

    template <typename T>
    class AutoLocal {
    public:
        AutoLocal(JNIEnv* env, T ref): fEnv(env), fRef(ref) {
        }

        AutoLocal(const AutoLocal&) = delete;
        AutoLocal(AutoLocal&&) = default;
        AutoLocal& operator=(AutoLocal const&) = delete;

        ~AutoLocal() {
            if (fRef)
                fEnv->DeleteLocalRef(fRef);
        }

        T get() {
            return fRef;
        }
    private:
        JNIEnv* fEnv;
        T fRef;
    };

    namespace Color4f {
        extern jclass cls;
        extern jmethodID ctor;
        void onLoad(JNIEnv* env);
        void onUnload(JNIEnv* env);
    }

    namespace Drawable {
        extern jclass cls;
        extern jmethodID onDraw;
        extern jmethodID onGetBounds;
        void onLoad(JNIEnv* env);
        void onUnload(JNIEnv* env);
    }

    namespace FontFamilyName {
        extern jclass cls;
        extern jmethodID ctor;
        void onLoad(JNIEnv* env);
        void onUnload(JNIEnv* env);
    }

    namespace FontArguments {
        extern jclass cls;
        extern jfieldID collectionIndex;
        extern jfieldID variations;
        extern jfieldID paletteIndex;
        extern jfieldID paletteOverrides;
        void onLoad(JNIEnv* env);
        void onUnload(JNIEnv* env);
        SkFontArguments toSkFontArguments(JNIEnv* env, jobject fontArgumentsObj);
        void freeSkFontArguments(SkFontArguments& args);
    }

    namespace FontFeature {
        extern jclass cls;
        extern jmethodID ctor;
        extern jfieldID tag;
        extern jfieldID value;
        extern jfieldID start;
        extern jfieldID end;

        void onLoad(JNIEnv* env);
        void onUnload(JNIEnv* env);
        std::vector<SkShaper::Feature> fromJavaArray(JNIEnv* env, jobjectArray featuresArr);
    }

    namespace FontPaletteOverride {
        extern jclass cls;
        extern jfieldID index;
        extern jfieldID color;
        void onLoad(JNIEnv* env);
        void onUnload(JNIEnv* env);
    }

    namespace FontMetrics {
        extern jclass cls;
        extern jmethodID ctor;
        void onLoad(JNIEnv* env);
        void onUnload(JNIEnv* env);
        jobject toJava(JNIEnv* env, const SkFontMetrics& m);
    }

    namespace FontMgr {
        extern jclass cls;
        void onLoad(JNIEnv* env);
        void onUnload(JNIEnv* env);
    }

    namespace FontStyle {
        SkFontStyle fromJava(jint style);
        jint toJava(const SkFontStyle& fs);
    }

    namespace FontVariation {
        extern jclass cls;
        extern jmethodID ctor;
        extern jfieldID tag;
        extern jfieldID value;
        void onLoad(JNIEnv* env);
        void onUnload(JNIEnv* env);
    }

    namespace FontVariationAxis {
        extern jclass cls;
        extern jmethodID ctor;
        void onLoad(JNIEnv* env);
        void onUnload(JNIEnv* env);
    }

    namespace Image {
        extern jclass cls;
        extern jmethodID ctor;
        void onLoad(JNIEnv* env);
        void onUnload(JNIEnv* env);
    }

    namespace ImageInfo {
        extern jclass cls;
        extern jmethodID ctor;
        void onLoad(JNIEnv* env);
        void onUnload(JNIEnv* env);
        jobject toJava(JNIEnv* env, const SkImageInfo& imageInfo);
    }

    namespace ImageWithFilterResult {
        extern jclass cls;
        extern jmethodID ctor;
        void onLoad(JNIEnv* env);
        void onUnload(JNIEnv* env);
        jobject make(JNIEnv* env, SkImage* image, const SkIRect& subset, const SkIPoint& offset);
    }

    namespace Path {
        extern jclass cls;
        extern jmethodID ctor;
        void onLoad(JNIEnv* env);
        void onUnload(JNIEnv* env);
    }

    namespace PathSegment {
        extern jclass cls;
        extern jmethodID ctorDone;
        extern jmethodID ctorMoveClose;
        extern jmethodID ctorLine;
        extern jmethodID ctorQuad;
        extern jmethodID ctorConic;
        extern jmethodID ctorCubic;
        void onLoad(JNIEnv* env);
        void onUnload(JNIEnv* env);
    }

    namespace PaintFilterCanvas {
        extern jmethodID onFilterId;
        void onLoad(JNIEnv* env);
        void onUnload(JNIEnv* env);
        bool onFilter(jobject obj, SkPaint& paint);
        jobject attach(JNIEnv* env, jobject obj);
        void detach(jobject obj);
    }

    namespace RSXform {
        extern jclass cls;
        extern jmethodID ctor;
        void onLoad(JNIEnv* env);
        void onUnload(JNIEnv* env);
    }

    namespace RuntimeEffectUniformInfo {
        extern jclass cls;
        extern jmethodID ctor;
        void onLoad(JNIEnv* env);
        void onUnload(JNIEnv* env);
    }

    namespace RuntimeEffectChildInfo {
        extern jclass cls;
        extern jmethodID ctor;
        void onLoad(JNIEnv* env);
        void onUnload(JNIEnv* env);
    }

    namespace SamplingMode {
        SkSamplingOptions unpack(jlong val);
    }

    namespace SurfaceProps {
        extern jclass cls;
        extern jmethodID ctor;
        extern jmethodID _getFlags;
        extern jmethodID _getPixelGeometryOrdinal;
        void onLoad(JNIEnv* env);
        void onUnload(JNIEnv* env);
        jobject toJava(JNIEnv* env, const SkSurfaceProps& props);
        std::unique_ptr<SkSurfaceProps> toSkSurfaceProps(JNIEnv* env, jobject surfacePropsObj);
    }

    namespace impl {
        namespace Native {
            extern jfieldID _ptr;
            void onLoad(JNIEnv* env);
            void onUnload(JNIEnv* env);
            void* fromJava(JNIEnv* env, jobject obj, jclass cls);
        }
    }

    void onLoad(JNIEnv* env);
    void onUnload(JNIEnv* env);

    class UtfIndicesConverter {
    public:
        UtfIndicesConverter(const char* chars8, size_t len8);
        UtfIndicesConverter(const SkString& s);

        const char* fStart8;
        const char* fPtr8;
        const char* fEnd8;
        uint32_t fPos16;

        size_t from16To8(uint32_t i16);
        uint32_t from8To16(size_t i8);
    };
}

namespace types {
    namespace IPoint {
        extern jclass cls;
        extern jmethodID ctor;
        void onLoad(JNIEnv* env);
        void onUnload(JNIEnv* env);
        jobject make(JNIEnv* env, float x, float y);
        jobject fromSkIPoint(JNIEnv* env, const SkIPoint& p);
    }

    namespace IRect {
        extern jclass cls;
        extern jmethodID makeLTRB;
        extern jfieldID left;
        extern jfieldID top;
        extern jfieldID right;
        extern jfieldID bottom;
        void onLoad(JNIEnv* env);
        void onUnload(JNIEnv* env);
        jobject fromSkIRect(JNIEnv* env, const SkIRect& rect);
        std::unique_ptr<SkIRect> toSkIRect(JNIEnv* env, jobject obj);
    }

    namespace Point {
        extern jclass cls;
        extern jmethodID ctor;
        extern jfieldID x;
        extern jfieldID y;
        void onLoad(JNIEnv* env);
        void onUnload(JNIEnv* env);
        jobject make(JNIEnv* env, float x, float y);
        jobject fromSkPoint(JNIEnv* env, const SkPoint& p);
        jobjectArray fromSkPoints(JNIEnv* env, SkSpan<const SkPoint> ps);
    }

    namespace Rect {
        extern jclass cls;
        extern jmethodID makeLTRB;
        extern jfieldID left;
        extern jfieldID top;
        extern jfieldID right;
        extern jfieldID bottom;
        void onLoad(JNIEnv* env);
        void onUnload(JNIEnv* env);
        std::unique_ptr<SkRect> toSkRect(JNIEnv* env, jobject rect);
        jobject fromLTRB(JNIEnv* env, float left, float top, float right, float bottom);
        jobject fromSkRect(JNIEnv* env, const SkRect& rect);
    }

    namespace RRect {
        extern jclass cls;
        extern jmethodID makeLTRB1;
        extern jmethodID makeLTRB2;
        extern jmethodID makeLTRB4;
        extern jmethodID makeNinePatchLTRB;
        extern jmethodID makeComplexLTRB;
        extern jfieldID left;
        extern jfieldID top;
        extern jfieldID right;
        extern jfieldID bottom;
        extern jfieldID radii;
        void onLoad(JNIEnv* env);
        void onUnload(JNIEnv* env);
        SkRRect toSkRRect(JNIEnv* env, jfloat left, jfloat top, jfloat right, jfloat bottom, jfloatArray jradii);
        jobject fromSkRRect(JNIEnv* env, const SkRRect& rect);
    }

    void onLoad(JNIEnv* env);
    void onUnload(JNIEnv* env);
}

std::unique_ptr<SkMatrix> skMatrix(JNIEnv* env, jfloatArray arr);
std::unique_ptr<SkM44> skM44(JNIEnv* env, jfloatArray arr);

SkString skString(JNIEnv* env, jstring str);
jstring javaString(JNIEnv* env, const SkString& str);
jstring javaString(JNIEnv* env, const char* chars, size_t len);
jstring javaString(JNIEnv* env, const char* chars);
jstring javaString(JNIEnv* env, const std::string& str);
jstring javaString(JNIEnv* env, std::string_view str);

jobject javaFloat(JNIEnv* env, SkScalar val);
jlong packTwoInts(int32_t a, int32_t b);
jlong packIPoint(SkIPoint p);
jlong packISize(SkISize s);

template <typename T>
jbyteArray javaByteArray(JNIEnv* env, const std::vector<T>& bytes) {
    jbyteArray res = env->NewByteArray((jsize) bytes.size());
    env->SetByteArrayRegion(res, 0, (jsize) bytes.size(), bytes.data());
    return res;
}

template <typename T>
jshortArray javaShortArray(JNIEnv* env, const std::vector<T>& shorts) {
    jshortArray res = env->NewShortArray((jsize) shorts.size());
    env->SetShortArrayRegion(res, 0, (jsize) shorts.size(), reinterpret_cast<const jshort*>(shorts.data()));
    return res;
}

template <typename T>
jintArray javaIntArray(JNIEnv* env, const std::vector<T>& ints) {
    jintArray res = env->NewIntArray((jsize) ints.size());
    env->SetIntArrayRegion(res, 0, (jsize) ints.size(), reinterpret_cast<const jint*>(ints.data()));
    return res;
}

template <typename T>
jlongArray javaLongArray(JNIEnv* env, const std::vector<T>& longs) {
    jlongArray res = env->NewLongArray((jsize) longs.size());
    env->SetLongArrayRegion(res, 0, (jsize) longs.size(), longs.data());
    return res;
}

template <typename T>
jfloatArray javaFloatArray(JNIEnv* env, SkSpan<const T> floats) {
    jfloatArray res = env->NewFloatArray((jsize) floats.size());
    env->SetFloatArrayRegion(res, 0, (jsize) floats.size(), floats.data());
    return res;
}

template <typename T>
jfloatArray javaFloatArray(JNIEnv* env, const std::vector<T>& floats) {
    return javaFloatArray(env, SkSpan(floats.data(), floats.size()));
}

template <typename T>
jfloatArray javaFloatArray(JNIEnv* env, std::initializer_list<T> floats) {
    return javaFloatArray(env, SkSpan(floats.begin(), floats.size()));
}

std::vector<SkString> skStringVector(JNIEnv* env, jobjectArray arr);
jobjectArray javaStringArray(JNIEnv* env, const std::vector<SkString>& strings);

void deleteJBytes(void* addr, void*);

template <typename T>
inline T jlongToPtr(jlong ptr) {
    return reinterpret_cast<T>(static_cast<uintptr_t>(ptr));
}

template <typename T>
jlong ptrToJlong(T* ptr) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(ptr));
}
