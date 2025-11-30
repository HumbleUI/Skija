#include <jni.h>
#include "SkCodecAnimation.h"
#include "SkBlendMode.h"
#include "SkClipOp.h"
#include "SkAlphaType.h"
#include "SkColor.h"
#include "SkImageInfo.h"
#include "SkSurface.h"
#include "SkEncodedImageFormat.h"
#include "SkJpegEncoder.h"
#include "SkEncodedOrigin.h"
#include "SkPngEncoder.h"
#include "SkWebpEncoder.h"
#include "SkBlurTypes.h"
#include "SkSamplingOptions.h"
#include "SkTileMode.h"
#include "SkFont.h"
#include "SkFontTypes.h"
#include "SkFontStyle.h"

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
