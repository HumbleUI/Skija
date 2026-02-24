#include <jni.h>
#include "include/effects/Sk1DPathEffect.h"
#include "include/effects/SkRuntimeEffect.h"
#include "include/gpu/ganesh/GrTypes.h"
#include "include/pathops/SkPathOps.h"
#include "include/utils/SkShadowUtils.h"
#include "SkAlphaType.h"
#include "SkBlendMode.h"
#include "SkBlurTypes.h"
#include "SkCanvas.h"
#include "SkClipOp.h"
#include "SkCodecAnimation.h"
#include "SkColor.h"
#include "SkColorSpace.h"
#include "SkEncodedImageFormat.h"
#include "SkEncodedOrigin.h"
#include "SkFont.h"
#include "SkFontStyle.h"
#include "SkFontTypes.h"
#include "SkHighContrastFilter.h"
#include "SkImageInfo.h"
#include "SkJpegEncoder.h"
#include "SkPaint.h"
#include "SkPathBuilder.h"
#include "SkPathTypes.h"
#include "SkPngEncoder.h"
#include "SkRegion.h"
#include "SkSamplingOptions.h"
#include "SkSurface.h"
#include "SkSurfaceProps.h"
#include "SkTileMode.h"
#include "modules/skparagraph/include/DartTypes.h"
#include "modules/skparagraph/include/TextStyle.h"
#include "modules/skottie/include/Skottie.h"
#include "modules/svg/include/SkSVGNode.h"
#include "modules/svg/include/SkSVGRenderContext.h"
#include "modules/svg/include/SkSVGTypes.h"
#include "src/gpu/ganesh/gl/GrGLDefines.h"
#include "SkWebpEncoder.h"

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetAnimationDisposalModeOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkCodecAnimation::DisposalMethod::kKeep),
        static_cast<jint>(SkCodecAnimation::DisposalMethod::kRestoreBGColor),
        static_cast<jint>(SkCodecAnimation::DisposalMethod::kRestorePrevious),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetBackendStateOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        kRenderTarget_GrGLBackendState,
        kTextureBinding_GrGLBackendState,
        kView_GrGLBackendState,
        kBlend_GrGLBackendState,
        kMSAAEnable_GrGLBackendState,
        kVertex_GrGLBackendState,
        kStencil_GrGLBackendState,
        kPixelStore_GrGLBackendState,
        kProgram_GrGLBackendState,
        kFixedFunction_GrGLBackendState,
        kMisc_GrGLBackendState,
        kALL_GrGLBackendState,
        (jint) kAll_GrBackendState,
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetBlendModeOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkBlendMode::kClear),
        static_cast<jint>(SkBlendMode::kSrc),
        static_cast<jint>(SkBlendMode::kDst),
        static_cast<jint>(SkBlendMode::kSrcOver),
        static_cast<jint>(SkBlendMode::kDstOver),
        static_cast<jint>(SkBlendMode::kSrcIn),
        static_cast<jint>(SkBlendMode::kDstIn),
        static_cast<jint>(SkBlendMode::kSrcOut),
        static_cast<jint>(SkBlendMode::kDstOut),
        static_cast<jint>(SkBlendMode::kSrcATop),
        static_cast<jint>(SkBlendMode::kDstATop),
        static_cast<jint>(SkBlendMode::kXor),
        static_cast<jint>(SkBlendMode::kPlus),
        static_cast<jint>(SkBlendMode::kModulate),
        static_cast<jint>(SkBlendMode::kScreen),
        static_cast<jint>(SkBlendMode::kOverlay),
        static_cast<jint>(SkBlendMode::kDarken),
        static_cast<jint>(SkBlendMode::kLighten),
        static_cast<jint>(SkBlendMode::kColorDodge),
        static_cast<jint>(SkBlendMode::kColorBurn),
        static_cast<jint>(SkBlendMode::kHardLight),
        static_cast<jint>(SkBlendMode::kSoftLight),
        static_cast<jint>(SkBlendMode::kDifference),
        static_cast<jint>(SkBlendMode::kExclusion),
        static_cast<jint>(SkBlendMode::kMultiply),
        static_cast<jint>(SkBlendMode::kHue),
        static_cast<jint>(SkBlendMode::kSaturation),
        static_cast<jint>(SkBlendMode::kColor),
        static_cast<jint>(SkBlendMode::kLuminosity),

        static_cast<jint>(SkBlendMode::kLastCoeffMode),
        static_cast<jint>(SkBlendMode::kLastSeparableMode),
        static_cast<jint>(SkBlendMode::kLastMode)
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetClipModeOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkClipOp::kDifference),
        static_cast<jint>(SkClipOp::kIntersect),
        static_cast<jint>(SkClipOp::kMax_EnumValue)
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetColorAlphaTypeOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(kUnknown_SkAlphaType),
        static_cast<jint>(kOpaque_SkAlphaType),
        static_cast<jint>(kPremul_SkAlphaType),
        static_cast<jint>(kUnpremul_SkAlphaType),
        static_cast<jint>(kLastEnum_SkAlphaType),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

////

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetColorChannelOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkColorChannel::kR),
        static_cast<jint>(SkColorChannel::kG),
        static_cast<jint>(SkColorChannel::kB),
        static_cast<jint>(SkColorChannel::kA),
        static_cast<jint>(SkColorChannel::kLastEnum),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetColorChannelFlagOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
      kRed_SkColorChannelFlag,
      kGreen_SkColorChannelFlag,
      kBlue_SkColorChannelFlag,
      kAlpha_SkColorChannelFlag,
      kGray_SkColorChannelFlag,
      // Convenience values
      kGrayAlpha_SkColorChannelFlags,
      kRG_SkColorChannelFlags,
      kRGB_SkColorChannelFlags,
      kRGBA_SkColorChannelFlags,
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetColorTypeOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        kUnknown_SkColorType,
        kAlpha_8_SkColorType,
        kRGB_565_SkColorType,
        kARGB_4444_SkColorType,
        kRGBA_8888_SkColorType,
        kRGB_888x_SkColorType,
        kBGRA_8888_SkColorType,
        kRGBA_1010102_SkColorType,
        kBGRA_1010102_SkColorType,
        kRGB_101010x_SkColorType,
        kBGR_101010x_SkColorType,
        kBGR_101010x_XR_SkColorType,
        kBGRA_10101010_XR_SkColorType,
        kRGBA_10x6_SkColorType,
        kGray_8_SkColorType,
        kRGBA_F16Norm_SkColorType,
        kRGBA_F16_SkColorType,
        kRGB_F16F16F16x_SkColorType,
        kRGBA_F32_SkColorType,
        kR8G8_unorm_SkColorType,
        kA16_float_SkColorType,
        kR16G16_float_SkColorType,
        kA16_unorm_SkColorType,
        kR16G16_unorm_SkColorType,
        kR16G16B16A16_unorm_SkColorType,
        kSRGBA_8888_SkColorType,
        kR8_unorm_SkColorType,
        kLastEnum_SkColorType,
        kN32_SkColorType
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetColorSpaceNamedPrimariesValues
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkNamedPrimaries::CicpId::kRec709),
        static_cast<jint>(SkNamedPrimaries::CicpId::kRec470SystemM),
        static_cast<jint>(SkNamedPrimaries::CicpId::kRec470SystemBG),
        static_cast<jint>(SkNamedPrimaries::CicpId::kRec601),
        static_cast<jint>(SkNamedPrimaries::CicpId::kSMPTE_ST_240),
        static_cast<jint>(SkNamedPrimaries::CicpId::kGenericFilm),
        static_cast<jint>(SkNamedPrimaries::CicpId::kRec2020),
        static_cast<jint>(SkNamedPrimaries::CicpId::kSMPTE_ST_428_1),
        static_cast<jint>(SkNamedPrimaries::CicpId::kSMPTE_RP_431_2),
        static_cast<jint>(SkNamedPrimaries::CicpId::kSMPTE_EG_432_1),
        static_cast<jint>(SkNamedPrimaries::CicpId::kITU_T_H273_Value22),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetColorSpaceNamedTransferFnValues
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkNamedTransferFn::CicpId::kRec709),
        static_cast<jint>(SkNamedTransferFn::CicpId::kRec470SystemM),
        static_cast<jint>(SkNamedTransferFn::CicpId::kRec470SystemBG),
        static_cast<jint>(SkNamedTransferFn::CicpId::kRec601),
        static_cast<jint>(SkNamedTransferFn::CicpId::kSMPTE_ST_240),
        static_cast<jint>(SkNamedTransferFn::CicpId::kLinear),
        static_cast<jint>(SkNamedTransferFn::CicpId::kIEC61966_2_4),
        static_cast<jint>(SkNamedTransferFn::CicpId::kSRGB),
        static_cast<jint>(SkNamedTransferFn::CicpId::kRec2020_10bit),
        static_cast<jint>(SkNamedTransferFn::CicpId::kRec2020_12bit),
        static_cast<jint>(SkNamedTransferFn::CicpId::kPQ),
        static_cast<jint>(SkNamedTransferFn::CicpId::kSMPTE_ST_428_1),
        static_cast<jint>(SkNamedTransferFn::CicpId::kHLG),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetContentChangeModeOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkSurface::kDiscard_ContentChangeMode),
        static_cast<jint>(SkSurface::kRetain_ContentChangeMode),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetEncodedImageFormatOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkEncodedImageFormat::kBMP),
        static_cast<jint>(SkEncodedImageFormat::kGIF),
        static_cast<jint>(SkEncodedImageFormat::kICO),
        static_cast<jint>(SkEncodedImageFormat::kJPEG),
        static_cast<jint>(SkEncodedImageFormat::kPNG),
        static_cast<jint>(SkEncodedImageFormat::kWBMP),
        static_cast<jint>(SkEncodedImageFormat::kWEBP),
        static_cast<jint>(SkEncodedImageFormat::kPKM),
        static_cast<jint>(SkEncodedImageFormat::kKTX),
        static_cast<jint>(SkEncodedImageFormat::kASTC),
        static_cast<jint>(SkEncodedImageFormat::kDNG),
        static_cast<jint>(SkEncodedImageFormat::kHEIF),
        static_cast<jint>(SkEncodedImageFormat::kAVIF),
        static_cast<jint>(SkEncodedImageFormat::kJPEGXL),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetEncodedOriginOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(kTopLeft_SkEncodedOrigin),
        static_cast<jint>(kTopRight_SkEncodedOrigin),
        static_cast<jint>(kBottomRight_SkEncodedOrigin),
        static_cast<jint>(kBottomLeft_SkEncodedOrigin),
        static_cast<jint>(kLeftTop_SkEncodedOrigin),
        static_cast<jint>(kRightTop_SkEncodedOrigin),
        static_cast<jint>(kRightBottom_SkEncodedOrigin),
        static_cast<jint>(kLeftBottom_SkEncodedOrigin),
        // special values
        static_cast<jint>(kDefault_SkEncodedOrigin),
        static_cast<jint>(kLast_SkEncodedOrigin),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetEncodeJPEGAlphaModeOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkJpegEncoder::AlphaOption::kIgnore),
        static_cast<jint>(SkJpegEncoder::AlphaOption::kBlendOnBlack),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetEncodeJPEGDownsampleModeOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkJpegEncoder::Downsample::k420),
        static_cast<jint>(SkJpegEncoder::Downsample::k422),
        static_cast<jint>(SkJpegEncoder::Downsample::k444),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetEncodePNGFilterFlagOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkPngEncoder::FilterFlag::kZero),
        static_cast<jint>(SkPngEncoder::FilterFlag::kNone),
        static_cast<jint>(SkPngEncoder::FilterFlag::kSub),
        static_cast<jint>(SkPngEncoder::FilterFlag::kUp),
        static_cast<jint>(SkPngEncoder::FilterFlag::kAvg),
        static_cast<jint>(SkPngEncoder::FilterFlag::kPaeth),
        static_cast<jint>(SkPngEncoder::FilterFlag::kAll),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetEncodeWEBPCompressionModeOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkWebpEncoder::Compression::kLossy),
        static_cast<jint>(SkWebpEncoder::Compression::kLossless),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetFilterBlurModeOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(kNormal_SkBlurStyle),
        static_cast<jint>(kSolid_SkBlurStyle),
        static_cast<jint>(kOuter_SkBlurStyle),
        static_cast<jint>(kInner_SkBlurStyle),
        static_cast<jint>(kLastEnum_SkBlurStyle),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetFilterModeOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkFilterMode::kNearest),
        static_cast<jint>(SkFilterMode::kLinear),
        static_cast<jint>(SkFilterMode::kLast),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetFilterTileModeOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkTileMode::kClamp),
        static_cast<jint>(SkTileMode::kRepeat),
        static_cast<jint>(SkTileMode::kMirror),
        static_cast<jint>(SkTileMode::kDecal),
        static_cast<jint>(SkTileMode::kLastTileMode),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetFramebufferFormatValues
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        GR_GL_STENCIL_INDEX,
        GR_GL_DEPTH_COMPONENT,
        GR_GL_DEPTH_STENCIL,
        GR_GL_RED,
        GR_GL_RED_INTEGER,
        GR_GL_GREEN,
        GR_GL_BLUE,
        GR_GL_ALPHA,
        GR_GL_LUMINANCE,
        GR_GL_LUMINANCE_ALPHA,
        GR_GL_RG_INTEGER,
        GR_GL_RGB,
        GR_GL_RGB_INTEGER,
        GR_GL_SRGB,
        GR_GL_RGBA,
        GR_GL_RG,
        GR_GL_SRGB_ALPHA,
        GR_GL_RGBA_INTEGER,
        GR_GL_BGRA,
        GR_GL_STENCIL_INDEX4,
        GR_GL_STENCIL_INDEX8,
        GR_GL_STENCIL_INDEX16,
        GR_GL_DEPTH_COMPONENT16,
        GR_GL_DEPTH24_STENCIL8,
        GR_GL_R8,
        GR_GL_R16,
        GR_GL_R16F,
        GR_GL_R32F,
        GR_GL_R8I,
        GR_GL_R8UI,
        GR_GL_R16I,
        GR_GL_R16UI,
        GR_GL_R32I,
        GR_GL_R32UI,
        GR_GL_LUMINANCE8,
        GR_GL_LUMINANCE8_ALPHA8,
        GR_GL_LUMINANCE16F,
        GR_GL_ALPHA8,
        GR_GL_ALPHA16,
        GR_GL_ALPHA16F,
        GR_GL_ALPHA32F,
        GR_GL_ALPHA8I,
        GR_GL_ALPHA8UI,
        GR_GL_ALPHA16I,
        GR_GL_ALPHA16UI,
        GR_GL_ALPHA32I,
        GR_GL_ALPHA32UI,
        GR_GL_RG8,
        GR_GL_RG16,
        GR_GL_RG16F,
        GR_GL_RG8I,
        GR_GL_RG8UI,
        GR_GL_RG16I,
        GR_GL_RG16UI,
        GR_GL_RG32I,
        GR_GL_RG32UI,
        GR_GL_RGB5,
        GR_GL_RGB565,
        GR_GL_RGB8,
        GR_GL_SRGB8,
        GR_GL_RGBX8,
        GR_GL_RGB8I,
        GR_GL_RGB8UI,
        GR_GL_RGB16I,
        GR_GL_RGB16UI,
        GR_GL_RGB32I,
        GR_GL_RGB32UI,
        GR_GL_RGBA4,
        GR_GL_RGB5_A1,
        GR_GL_RGBA8,
        GR_GL_RGB10_A2,
        GR_GL_SRGB8_ALPHA8,
        GR_GL_RGBA16F,
        GR_GL_RGBA32F,
        GR_GL_RG32F,
        GR_GL_RGBA16,
        GR_GL_RGBA8I,
        GR_GL_RGBA8UI,
        GR_GL_RGBA16I,
        GR_GL_RGBA16UI,
        GR_GL_RGBA32I,
        GR_GL_RGBA32UI,
        GR_GL_BGRA8,
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetFontEdgingOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkFont::Edging::kAlias),
        static_cast<jint>(SkFont::Edging::kAntiAlias),
        static_cast<jint>(SkFont::Edging::kSubpixelAntiAlias),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetFontHintingOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkFontHinting::kNone),
        static_cast<jint>(SkFontHinting::kSlight),
        static_cast<jint>(SkFontHinting::kNormal),
        static_cast<jint>(SkFontHinting::kFull),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}


extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetFontSlantOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkFontStyle::kUpright_Slant),
        static_cast<jint>(SkFontStyle::kItalic_Slant),
        static_cast<jint>(SkFontStyle::kOblique_Slant),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetFontWeightOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        SkFontStyle::kInvisible_Weight,
        SkFontStyle::kThin_Weight,
        SkFontStyle::kExtraLight_Weight,
        SkFontStyle::kLight_Weight,
        SkFontStyle::kNormal_Weight,
        SkFontStyle::kMedium_Weight,
        SkFontStyle::kSemiBold_Weight,
        SkFontStyle::kBold_Weight,
        SkFontStyle::kExtraBold_Weight,
        SkFontStyle::kBlack_Weight,
        SkFontStyle::kExtraBlack_Weight,
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetFontWidthOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        SkFontStyle::kUltraCondensed_Width,
        SkFontStyle::kExtraCondensed_Width,
        SkFontStyle::kCondensed_Width,
        SkFontStyle::kSemiCondensed_Width,
        SkFontStyle::kNormal_Width,
        SkFontStyle::kSemiExpanded_Width,
        SkFontStyle::kExpanded_Width,
        SkFontStyle::kExtraExpanded_Width,
        SkFontStyle::kUltraExpanded_Width,
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetInversionModeOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkHighContrastConfig::InvertStyle::kNoInvert),
        static_cast<jint>(SkHighContrastConfig::InvertStyle::kInvertBrightness),
        static_cast<jint>(SkHighContrastConfig::InvertStyle::kInvertLightness),
        static_cast<jint>(SkHighContrastConfig::InvertStyle::kLast),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetMipmapModeOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkMipmapMode::kNone),
        static_cast<jint>(SkMipmapMode::kNearest),
        static_cast<jint>(SkMipmapMode::kLinear),
        static_cast<jint>(SkMipmapMode::kLast),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetPaintModeOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkPaint::kFill_Style),
        static_cast<jint>(SkPaint::kStroke_Style),
        static_cast<jint>(SkPaint::kStrokeAndFill_Style),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetPaintStrokeCapOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkPaint::kButt_Cap),
        static_cast<jint>(SkPaint::kRound_Cap),
        static_cast<jint>(SkPaint::kSquare_Cap),
        static_cast<jint>(SkPaint::kLast_Cap),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetPaintStrokeJoinOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkPaint::kMiter_Join),
        static_cast<jint>(SkPaint::kRound_Join),
        static_cast<jint>(SkPaint::kBevel_Join),
        static_cast<jint>(SkPaint::kLast_Join),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetPathDirectionOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkPathDirection::kCW),
        static_cast<jint>(SkPathDirection::kCCW),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetPathEffect1DStyleOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkPath1DPathEffect::kTranslate_Style),
        static_cast<jint>(SkPath1DPathEffect::kRotate_Style),
        static_cast<jint>(SkPath1DPathEffect::kMorph_Style),
        static_cast<jint>(SkPath1DPathEffect::kLastEnum_Style),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetPathEllipseArcOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkPathBuilder::kSmall_ArcSize),
        static_cast<jint>(SkPathBuilder::kLarge_ArcSize),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetPathFillModeOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkPathFillType::kWinding),
        static_cast<jint>(SkPathFillType::kEvenOdd),
        static_cast<jint>(SkPathFillType::kInverseWinding),
        static_cast<jint>(SkPathFillType::kInverseEvenOdd),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetPathOpOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(kDifference_SkPathOp),
        static_cast<jint>(kIntersect_SkPathOp),
        static_cast<jint>(kUnion_SkPathOp),
        static_cast<jint>(kXOR_SkPathOp),
        static_cast<jint>(kReverseDifference_SkPathOp),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetPathSegmentTypeValues
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        kLine_SkPathSegmentMask,
        kQuad_SkPathSegmentMask,
        kConic_SkPathSegmentMask,
        kCubic_SkPathSegmentMask,
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetPathVerbOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkPathVerb::kMove),
        static_cast<jint>(SkPathVerb::kLine),
        static_cast<jint>(SkPathVerb::kQuad),
        static_cast<jint>(SkPathVerb::kConic),
        static_cast<jint>(SkPathVerb::kCubic),
        static_cast<jint>(SkPathVerb::kClose),
        static_cast<jint>(SkPathVerb::kLast_Verb),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetPixelGeometryOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(kUnknown_SkPixelGeometry),
        static_cast<jint>(kRGB_H_SkPixelGeometry),
        static_cast<jint>(kBGR_H_SkPixelGeometry),
        static_cast<jint>(kRGB_V_SkPixelGeometry),
        static_cast<jint>(kBGR_V_SkPixelGeometry),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetRegionOpOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkRegion::kDifference_Op),
        static_cast<jint>(SkRegion::kIntersect_Op),
        static_cast<jint>(SkRegion::kUnion_Op),
        static_cast<jint>(SkRegion::kXOR_Op),
        static_cast<jint>(SkRegion::kReverseDifference_Op),
        static_cast<jint>(SkRegion::kReplace_Op),
        static_cast<jint>(SkRegion::kLastOp),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetRuntimeEffectChildTypeOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkRuntimeEffect::ChildType::kShader),
        static_cast<jint>(SkRuntimeEffect::ChildType::kColorFilter),
        static_cast<jint>(SkRuntimeEffect::ChildType::kBlender),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetRuntimeEffectUniformFlagOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkRuntimeEffect::Uniform::kArray_Flag),
        static_cast<jint>(SkRuntimeEffect::Uniform::kColor_Flag),
        static_cast<jint>(SkRuntimeEffect::Uniform::kVertex_Flag),
        static_cast<jint>(SkRuntimeEffect::Uniform::kFragment_Flag),
        static_cast<jint>(SkRuntimeEffect::Uniform::kHalfPrecision_Flag),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetRuntimeEffectUniformTypeOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkRuntimeEffect::Uniform::Type::kFloat),
        static_cast<jint>(SkRuntimeEffect::Uniform::Type::kFloat2),
        static_cast<jint>(SkRuntimeEffect::Uniform::Type::kFloat3),
        static_cast<jint>(SkRuntimeEffect::Uniform::Type::kFloat4),
        static_cast<jint>(SkRuntimeEffect::Uniform::Type::kFloat2x2),
        static_cast<jint>(SkRuntimeEffect::Uniform::Type::kFloat3x3),
        static_cast<jint>(SkRuntimeEffect::Uniform::Type::kFloat4x4),
        static_cast<jint>(SkRuntimeEffect::Uniform::Type::kInt),
        static_cast<jint>(SkRuntimeEffect::Uniform::Type::kInt2),
        static_cast<jint>(SkRuntimeEffect::Uniform::Type::kInt3),
        static_cast<jint>(SkRuntimeEffect::Uniform::Type::kInt4),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetSaveLayerRecFlagOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkCanvas::kPreserveLCDText_SaveLayerFlag),
        static_cast<jint>(SkCanvas::kInitWithPrevious_SaveLayerFlag),
        static_cast<jint>(SkCanvas::kF16ColorType),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetShadowUtilsFlagOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        kNone_ShadowFlag,
        kTransparentOccluder_ShadowFlag,
        kGeometricOnly_ShadowFlag,
        kDirectionalLight_ShadowFlag,
        kConcaveBlurOnly_ShadowFlag,
        kAll_ShadowFlag,
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetSurfaceOriginOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(kTopLeft_GrSurfaceOrigin),
        static_cast<jint>(kBottomLeft_GrSurfaceOrigin),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetParagraphAffinityOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(skia::textlayout::kUpstream),
        static_cast<jint>(skia::textlayout::kDownstream),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetParagraphAlignmentOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(skia::textlayout::TextAlign::kLeft),
        static_cast<jint>(skia::textlayout::TextAlign::kRight),
        static_cast<jint>(skia::textlayout::TextAlign::kCenter),
        static_cast<jint>(skia::textlayout::TextAlign::kJustify),
        static_cast<jint>(skia::textlayout::TextAlign::kStart),
        static_cast<jint>(skia::textlayout::TextAlign::kEnd),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetParagraphBaselineModeOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(skia::textlayout::TextBaseline::kAlphabetic),
        static_cast<jint>(skia::textlayout::TextBaseline::kIdeographic),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetParagraphDecorationLineStyleOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(skia::textlayout::kSolid),
        static_cast<jint>(skia::textlayout::kDouble),
        static_cast<jint>(skia::textlayout::kDotted),
        static_cast<jint>(skia::textlayout::kDashed),
        static_cast<jint>(skia::textlayout::kWavy),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetParagraphDirectionOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(skia::textlayout::TextDirection::kRtl),
        static_cast<jint>(skia::textlayout::TextDirection::kLtr),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetParagraphHeightModeOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(skia::textlayout::kAll),
        static_cast<jint>(skia::textlayout::kDisableFirstAscent),
        static_cast<jint>(skia::textlayout::kDisableLastDescent),
        static_cast<jint>(skia::textlayout::kDisableAll),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetParagraphPlaceholderAlignmentOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(skia::textlayout::PlaceholderAlignment::kBaseline),
        static_cast<jint>(skia::textlayout::PlaceholderAlignment::kAboveBaseline),
        static_cast<jint>(skia::textlayout::PlaceholderAlignment::kBelowBaseline),
        static_cast<jint>(skia::textlayout::PlaceholderAlignment::kTop),
        static_cast<jint>(skia::textlayout::PlaceholderAlignment::kBottom),
        static_cast<jint>(skia::textlayout::PlaceholderAlignment::kMiddle),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetParagraphRectHeightModeOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(skia::textlayout::RectHeightStyle::kTight),
        static_cast<jint>(skia::textlayout::RectHeightStyle::kMax),
        static_cast<jint>(skia::textlayout::RectHeightStyle::kIncludeLineSpacingMiddle),
        static_cast<jint>(skia::textlayout::RectHeightStyle::kIncludeLineSpacingTop),
        static_cast<jint>(skia::textlayout::RectHeightStyle::kIncludeLineSpacingBottom),
        static_cast<jint>(skia::textlayout::RectHeightStyle::kStrut),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetParagraphRectWidthModeOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(skia::textlayout::RectWidthStyle::kTight),
        static_cast<jint>(skia::textlayout::RectWidthStyle::kMax),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetParagraphTextStyleAttributeOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(skia::textlayout::kNone),
        static_cast<jint>(skia::textlayout::kAllAttributes),
        static_cast<jint>(skia::textlayout::kFont),
        static_cast<jint>(skia::textlayout::kForeground),
        static_cast<jint>(skia::textlayout::kBackground),
        static_cast<jint>(skia::textlayout::kShadow),
        static_cast<jint>(skia::textlayout::kDecorations),
        static_cast<jint>(skia::textlayout::kLetterSpacing),
        static_cast<jint>(skia::textlayout::kWordSpacing)
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetSkottieAnimationBuilderFlagOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(skottie::Animation::Builder::kDeferImageLoading),
        static_cast<jint>(skottie::Animation::Builder::kPreferEmbeddedFonts),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetSkottieLogLevelOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(skottie::Logger::Level::kWarning),
        static_cast<jint>(skottie::Logger::Level::kError),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetSkottieRenderFlagOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(skottie::Animation::kSkipTopLevelIsolation),
        static_cast<jint>(skottie::Animation::kDisableTopLevelClipping),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetSvgLengthTypeOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkSVGLengthContext::LengthType::kHorizontal),
        static_cast<jint>(SkSVGLengthContext::LengthType::kVertical),
        static_cast<jint>(SkSVGLengthContext::LengthType::kOther),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetSvgLengthUnitOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkSVGLength::Unit::kUnknown),
        static_cast<jint>(SkSVGLength::Unit::kNumber),
        static_cast<jint>(SkSVGLength::Unit::kPercentage),
        static_cast<jint>(SkSVGLength::Unit::kEMS),
        static_cast<jint>(SkSVGLength::Unit::kEXS),
        static_cast<jint>(SkSVGLength::Unit::kPX),
        static_cast<jint>(SkSVGLength::Unit::kCM),
        static_cast<jint>(SkSVGLength::Unit::kMM),
        static_cast<jint>(SkSVGLength::Unit::kIN),
        static_cast<jint>(SkSVGLength::Unit::kPT),
        static_cast<jint>(SkSVGLength::Unit::kPC),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetSvgPreserveAspectRatioAlignOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkSVGPreserveAspectRatio::kXMinYMin),
        static_cast<jint>(SkSVGPreserveAspectRatio::kXMidYMin),
        static_cast<jint>(SkSVGPreserveAspectRatio::kXMaxYMin),
        static_cast<jint>(SkSVGPreserveAspectRatio::kXMinYMid),
        static_cast<jint>(SkSVGPreserveAspectRatio::kXMidYMid),
        static_cast<jint>(SkSVGPreserveAspectRatio::kXMaxYMid),
        static_cast<jint>(SkSVGPreserveAspectRatio::kXMinYMax),
        static_cast<jint>(SkSVGPreserveAspectRatio::kXMidYMax),
        static_cast<jint>(SkSVGPreserveAspectRatio::kXMaxYMax),
        static_cast<jint>(SkSVGPreserveAspectRatio::kNone),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetSvgPreserveAspectRatioScaleOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkSVGPreserveAspectRatio::kMeet),
        static_cast<jint>(SkSVGPreserveAspectRatio::kSlice),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_humbleui_skija_test_EnumTest__1nGetSvgTagOrdinals
  (JNIEnv* env, jclass jclass) {
    jint values[] = {
        static_cast<jint>(SkSVGTag::kCircle),
        static_cast<jint>(SkSVGTag::kClipPath),
        static_cast<jint>(SkSVGTag::kDefs),
        static_cast<jint>(SkSVGTag::kEllipse),
        static_cast<jint>(SkSVGTag::kFeBlend),
        static_cast<jint>(SkSVGTag::kFeColorMatrix),
        static_cast<jint>(SkSVGTag::kFeComponentTransfer),
        static_cast<jint>(SkSVGTag::kFeComposite),
        static_cast<jint>(SkSVGTag::kFeDiffuseLighting),
        static_cast<jint>(SkSVGTag::kFeDisplacementMap),
        static_cast<jint>(SkSVGTag::kFeDistantLight),
        static_cast<jint>(SkSVGTag::kFeFlood),
        static_cast<jint>(SkSVGTag::kFeFuncA),
        static_cast<jint>(SkSVGTag::kFeFuncR),
        static_cast<jint>(SkSVGTag::kFeFuncG),
        static_cast<jint>(SkSVGTag::kFeFuncB),
        static_cast<jint>(SkSVGTag::kFeGaussianBlur),
        static_cast<jint>(SkSVGTag::kFeImage),
        static_cast<jint>(SkSVGTag::kFeMerge),
        static_cast<jint>(SkSVGTag::kFeMergeNode),
        static_cast<jint>(SkSVGTag::kFeMorphology),
        static_cast<jint>(SkSVGTag::kFeOffset),
        static_cast<jint>(SkSVGTag::kFePointLight),
        static_cast<jint>(SkSVGTag::kFeSpecularLighting),
        static_cast<jint>(SkSVGTag::kFeSpotLight),
        static_cast<jint>(SkSVGTag::kFeTurbulence),
        static_cast<jint>(SkSVGTag::kFilter),
        static_cast<jint>(SkSVGTag::kG),
        static_cast<jint>(SkSVGTag::kImage),
        static_cast<jint>(SkSVGTag::kLine),
        static_cast<jint>(SkSVGTag::kLinearGradient),
        static_cast<jint>(SkSVGTag::kMask),
        static_cast<jint>(SkSVGTag::kPath),
        static_cast<jint>(SkSVGTag::kPattern),
        static_cast<jint>(SkSVGTag::kPolygon),
        static_cast<jint>(SkSVGTag::kPolyline),
        static_cast<jint>(SkSVGTag::kRadialGradient),
        static_cast<jint>(SkSVGTag::kRect),
        static_cast<jint>(SkSVGTag::kStop),
        static_cast<jint>(SkSVGTag::kSvg),
        static_cast<jint>(SkSVGTag::kText),
        static_cast<jint>(SkSVGTag::kTextLiteral),
        static_cast<jint>(SkSVGTag::kTextPath),
        static_cast<jint>(SkSVGTag::kTSpan),
        static_cast<jint>(SkSVGTag::kUse),
    };
    size_t len = sizeof(values) / sizeof(values[0]);
    jintArray result = env->NewIntArray(len);
    env->SetIntArrayRegion(result, 0, len, values);
    return result;
}
