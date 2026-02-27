package io.github.humbleui.skija.test;

import static io.github.humbleui.skija.test.runner.TestRunner.*;

import java.util.*;

import io.github.humbleui.skija.*;
import io.github.humbleui.skija.impl.*;
import io.github.humbleui.skija.paragraph.*;
import io.github.humbleui.skija.skottie.*;
import io.github.humbleui.skija.svg.*;
import io.github.humbleui.skija.test.runner.*;

public class EnumTest implements Executable {
    static { Library.staticLoad(); }

    @Override
    public void execute() throws Exception {
        TestRunner.testMethod(this, "animationDisposalMode");
        TestRunner.testMethod(this, "backendState");
        TestRunner.testMethod(this, "blendMode");
        TestRunner.testMethod(this, "clipMode");
        TestRunner.testMethod(this, "colorAlphaType");
        TestRunner.testMethod(this, "colorChannel");
        TestRunner.testMethod(this, "colorChannelFlag");
        TestRunner.testMethod(this, "colorType");
        TestRunner.testMethod(this, "colorSpaceNamedPrimaries");
        TestRunner.testMethod(this, "colorSpaceNamedTransferFn");
        TestRunner.testMethod(this, "contentChangeMode");
        TestRunner.testMethod(this, "encodedImageFormat");
        TestRunner.testMethod(this, "encodedOrigin");
        TestRunner.testMethod(this, "encodeJPEGAlphaMode");
        TestRunner.testMethod(this, "encodeJPEGDownsampleMode");
        TestRunner.testMethod(this, "encodePNGFilterFlag");
        TestRunner.testMethod(this, "encodeWEBPCompressionMode");
        TestRunner.testMethod(this, "filterBlurMode");
        TestRunner.testMethod(this, "filterMode");
        TestRunner.testMethod(this, "filterTileMode");
        TestRunner.testMethod(this, "framebufferFormat");
        TestRunner.testMethod(this, "fontEdging");
        TestRunner.testMethod(this, "fontHinting");
        TestRunner.testMethod(this, "fontSlant");
        TestRunner.testMethod(this, "fontWeight");
        TestRunner.testMethod(this, "fontWidth");
        TestRunner.testMethod(this, "inversionMode");
        TestRunner.testMethod(this, "mipmapMode");
        TestRunner.testMethod(this, "paintMode");
        TestRunner.testMethod(this, "paintStrokeCap");
        TestRunner.testMethod(this, "paintStrokeJoin");
        TestRunner.testMethod(this, "pathDirection");
        TestRunner.testMethod(this, "pathEffect1DStyle");
        TestRunner.testMethod(this, "pathEllipseArc");
        TestRunner.testMethod(this, "pathFillMode");
        TestRunner.testMethod(this, "pathOp");
        TestRunner.testMethod(this, "pathSegmentType");
        TestRunner.testMethod(this, "pathVerb");
        TestRunner.testMethod(this, "pixelGeometry");
        TestRunner.testMethod(this, "regionOp");
        TestRunner.testMethod(this, "runtimeEffectChildType");
        TestRunner.testMethod(this, "runtimeEffectUniformFlag");
        TestRunner.testMethod(this, "runtimeEffectUniformType");
        TestRunner.testMethod(this, "saveLayerRecFlag");
        TestRunner.testMethod(this, "shadowUtilsFlag");
        TestRunner.testMethod(this, "surfaceOrigin");
        TestRunner.testMethod(this, "paragraphAffinity");
        TestRunner.testMethod(this, "paragraphAlignment");
        TestRunner.testMethod(this, "paragraphBaselineMode");
        TestRunner.testMethod(this, "paragraphDecorationLineStyle");
        TestRunner.testMethod(this, "paragraphDirection");
        TestRunner.testMethod(this, "paragraphHeightMode");
        TestRunner.testMethod(this, "paragraphPlaceholderAlignment");
        TestRunner.testMethod(this, "paragraphRectHeightMode");
        TestRunner.testMethod(this, "paragraphRectWidthMode");
        TestRunner.testMethod(this, "paragraphTextStyleAttribute");
        TestRunner.testMethod(this, "skottieAnimationBuilderFlag");
        TestRunner.testMethod(this, "skottieLogLevel");
        TestRunner.testMethod(this, "skottieRenderFlag");
        TestRunner.testMethod(this, "svgLengthType");
        TestRunner.testMethod(this, "svgLengthUnit");
        TestRunner.testMethod(this, "svgPreserveAspectRatioAlign");
        TestRunner.testMethod(this, "svgPreserveAspectRatioScale");
        TestRunner.testMethod(this, "svgTag");
    }

    public void animationDisposalMode() throws Exception {
        int[] nativeOrdinals = _nGetAnimationDisposalModeOrdinals();
        int[] javaOrdinals = {
            AnimationDisposalMode.KEEP.ordinal(),
            AnimationDisposalMode.RESTORE_BG_COLOR.ordinal(),
            AnimationDisposalMode.RESTORE_PREVIOUS.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void backendState() throws Exception {
        int[] nativeOrdinals = _nGetBackendStateOrdinals();
        int[] javaOrdinals = {
            BackendState.GL_RENDER_TARGET._value,
            BackendState.GL_TEXTURE_BINDING._value,
            BackendState.GL_VIEW._value,
            BackendState.GL_BLEND._value,
            BackendState.GL_MSAA_ENABLE._value,
            BackendState.GL_VERTEX._value,
            BackendState.GL_STENCIL._value,
            BackendState.GL_PIXEL_STORE._value,
            BackendState.GL_PROGRAM._value,
            BackendState.GL_FIXED_FUNCTION._value,
            BackendState.GL_MISC._value,
            BackendState.GL_ALL._value,
            BackendState.ALL._value
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void blendMode() throws Exception {
        int[] nativeOrdinals = _nGetBlendModeOrdinals();
        int[] javaOrdinals = {
            BlendMode.CLEAR.ordinal(),
            BlendMode.SRC.ordinal(),
            BlendMode.DST.ordinal(),
            BlendMode.SRC_OVER.ordinal(),
            BlendMode.DST_OVER.ordinal(),
            BlendMode.SRC_IN.ordinal(),
            BlendMode.DST_IN.ordinal(),
            BlendMode.SRC_OUT.ordinal(),
            BlendMode.DST_OUT.ordinal(),
            BlendMode.SRC_ATOP.ordinal(),
            BlendMode.DST_ATOP.ordinal(),
            BlendMode.XOR.ordinal(),
            BlendMode.PLUS.ordinal(),
            BlendMode.MODULATE.ordinal(),
            BlendMode.SCREEN.ordinal(),
            BlendMode.OVERLAY.ordinal(),
            BlendMode.DARKEN.ordinal(),
            BlendMode.LIGHTEN.ordinal(),
            BlendMode.COLOR_DODGE.ordinal(),
            BlendMode.COLOR_BURN.ordinal(),
            BlendMode.HARD_LIGHT.ordinal(),
            BlendMode.SOFT_LIGHT.ordinal(),
            BlendMode.DIFFERENCE.ordinal(),
            BlendMode.EXCLUSION.ordinal(),
            BlendMode.MULTIPLY.ordinal(),
            BlendMode.HUE.ordinal(),
            BlendMode.SATURATION.ordinal(),
            BlendMode.COLOR.ordinal(),
            BlendMode.LUMINOSITY.ordinal(),

            // special values
            BlendMode.LAST_COEFF_MODE.ordinal(),
            BlendMode.LAST_SEPARABLE_MODE.ordinal(),
            BlendMode.LUMINOSITY.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void clipMode() throws Exception {
        int[] nativeOrdinals = _nGetClipModeOrdinals();
        int[] javaOrdinals = {
            ClipMode.DIFFERENCE.ordinal(),
            ClipMode.INTERSECT.ordinal(),

            // last value
            ClipMode.INTERSECT.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void colorAlphaType() throws Exception {
        int[] nativeOrdinals = _nGetColorAlphaTypeOrdinals();
        int[] javaOrdinals = {
            ColorAlphaType.UNKNOWN.ordinal(),
            ColorAlphaType.OPAQUE.ordinal(),
            ColorAlphaType.PREMUL.ordinal(),
            ColorAlphaType.UNPREMUL.ordinal(),

            // last value
            ColorAlphaType.UNPREMUL.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void colorChannel() throws Exception {
        int[] nativeOrdinals = _nGetColorChannelOrdinals();
        int[] javaOrdinals = {
            ColorChannel.R.ordinal(),
            ColorChannel.G.ordinal(),
            ColorChannel.B.ordinal(),
            ColorChannel.A.ordinal(),

            // last value
            ColorChannel.A.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void colorChannelFlag() throws Exception {
        int[] nativeOrdinals = _nGetColorChannelFlagOrdinals();
        int[] javaOrdinals = {
            ColorChannelFlag.RED.getValue(),
            ColorChannelFlag.GREEN.getValue(),
            ColorChannelFlag.BLUE.getValue(),
            ColorChannelFlag.ALPHA.getValue(),
            ColorChannelFlag.GRAY.getValue(),

            // Convenience values
            ColorChannelFlag.GRAY_ALPHA.getValue(),
            ColorChannelFlag.RG.getValue(),
            ColorChannelFlag.RGB.getValue(),
            ColorChannelFlag.RGBA.getValue()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void colorType() throws Exception {
        int[] nativeOrdinals = _nGetColorTypeOrdinals();
        int[] javaOrdinals = {
            ColorType.UNKNOWN.ordinal(),
            ColorType.ALPHA_8.ordinal(),
            ColorType.RGB_565.ordinal(),
            ColorType.ARGB_4444.ordinal(),
            ColorType.RGBA_8888.ordinal(),
            ColorType.RGB_888X.ordinal(),
            ColorType.BGRA_8888.ordinal(),
            ColorType.RGBA_1010102.ordinal(),
            ColorType.BGRA_1010102.ordinal(),
            ColorType.RGB_101010X.ordinal(),
            ColorType.BGR_101010X.ordinal(),
            ColorType.BGR_101010X_XR.ordinal(),
            ColorType.BGRA_10101010_XR.ordinal(),
            ColorType.RGBA_10X6.ordinal(),
            ColorType.GRAY_8.ordinal(),
            ColorType.RGBA_F16NORM.ordinal(),
            ColorType.RGBA_F16.ordinal(),
            ColorType.RGB_F16F16F16X.ordinal(),
            ColorType.RGBA_F32.ordinal(),
            ColorType.R8G8_UNORM.ordinal(),
            ColorType.A16_FLOAT.ordinal(),
            ColorType.R16G16_FLOAT.ordinal(),
            ColorType.A16_UNORM.ordinal(),
            ColorType.R16G16_UNORM.ordinal(),
            ColorType.R16G16B16A16_UNORM.ordinal(),
            ColorType.SRGBA_8888.ordinal(),
            ColorType.R8_UNORM.ordinal(),

            // last value
            ColorType.R8_UNORM.ordinal(),

            // N32
            ColorType.N32.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);

        for (ColorType type: ColorType.values()) {
            if (ColorType.UNKNOWN == type) {
                TestRunner.assertEquals(0, type.getBytesPerPixel());
                TestRunner.assertEquals(0, type.getShiftPerPixel());
                TestRunner.assertEquals(0, type.getChannelFlags());
                TestRunner.assertEquals(0, type.getNumChannels());
            } else {
                TestRunner.assertNotEquals(0, type.getBytesPerPixel());
                TestRunner.assertNotEquals(-1, type.getShiftPerPixel());
                TestRunner.assertNotEquals(0, type.getChannelFlags());
                TestRunner.assertNotEquals(0, type.getNumChannels());
            }

            for (ColorAlphaType alpha: ColorAlphaType.values()) {
                type.validateAlphaType(alpha);
            }
        }
    }

    public void colorSpaceNamedPrimaries() throws Exception {
        int[] nativeValues = _nGetColorSpaceNamedPrimariesValues();
        int[] javaValues = {
            ColorSpaceNamedPrimaries.REC709._value,
            ColorSpaceNamedPrimaries.REC470_SYSTEM_M._value,
            ColorSpaceNamedPrimaries.REC470_SYSTEM_BG._value,
            ColorSpaceNamedPrimaries.REC601._value,
            ColorSpaceNamedPrimaries.SMPTE_ST_240._value,
            ColorSpaceNamedPrimaries.GENERIC_FILM._value,
            ColorSpaceNamedPrimaries.REC2020._value,
            ColorSpaceNamedPrimaries.SMPTE_ST_428_1._value,
            ColorSpaceNamedPrimaries.SMPTE_RP_431_2._value,
            ColorSpaceNamedPrimaries.SMPTE_EG_432_1._value,
            ColorSpaceNamedPrimaries.ITU_T_H273_VALUE22._value
        };
        TestRunner.assertArrayEquals(nativeValues, javaValues);
    }

    public void colorSpaceNamedTransferFn() throws Exception {
        int[] nativeValues = _nGetColorSpaceNamedTransferFnValues();
        int[] javaValues = {
            ColorSpaceNamedTransferFn.REC709._value,
            ColorSpaceNamedTransferFn.REC470_SYSTEM_M._value,
            ColorSpaceNamedTransferFn.REC470_SYSTEM_BG._value,
            ColorSpaceNamedTransferFn.REC601._value,
            ColorSpaceNamedTransferFn.SMPTE_ST_240._value,
            ColorSpaceNamedTransferFn.LINEAR._value,
            ColorSpaceNamedTransferFn.IEC61966_2_4._value,
            ColorSpaceNamedTransferFn.SRGB._value,
            ColorSpaceNamedTransferFn.REC2020_10BIT._value,
            ColorSpaceNamedTransferFn.REC2020_12BIT._value,
            ColorSpaceNamedTransferFn.PQ._value,
            ColorSpaceNamedTransferFn.SMPTE_ST_428_1._value,
            ColorSpaceNamedTransferFn.HLG._value
        };
        TestRunner.assertArrayEquals(nativeValues, javaValues);
    }

    public void contentChangeMode() throws Exception {
        int[] nativeOrdinals = _nGetContentChangeModeOrdinals();
        int[] javaOrdinals = {
            ContentChangeMode.DISCARD.ordinal(),
            ContentChangeMode.RETAIN.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void encodedImageFormat() throws Exception {
        int[] nativeOrdinals = _nGetEncodedImageFormatOrdinals();
        int[] javaOrdinals = {
            EncodedImageFormat.BMP.ordinal(),
            EncodedImageFormat.GIF.ordinal(),
            EncodedImageFormat.ICO.ordinal(),
            EncodedImageFormat.JPEG.ordinal(),
            EncodedImageFormat.PNG.ordinal(),
            EncodedImageFormat.WBMP.ordinal(),
            EncodedImageFormat.WEBP.ordinal(),
            EncodedImageFormat.PKM.ordinal(),
            EncodedImageFormat.KTX.ordinal(),
            EncodedImageFormat.ASTC.ordinal(),
            EncodedImageFormat.DNG.ordinal(),
            EncodedImageFormat.HEIF.ordinal(),
            EncodedImageFormat.AVIF.ordinal(),
            EncodedImageFormat.JPEGXL.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void encodedOrigin() throws Exception {
        int[] nativeOrdinals = _nGetEncodedOriginOrdinals();
        int[] javaOrdinals = {
            EncodedOrigin.TOP_LEFT.ordinal(),
            EncodedOrigin.TOP_RIGHT.ordinal(),
            EncodedOrigin.BOTTOM_RIGHT.ordinal(),
            EncodedOrigin.BOTTOM_LEFT.ordinal(),
            EncodedOrigin.LEFT_TOP.ordinal(),
            EncodedOrigin.RIGHT_TOP.ordinal(),
            EncodedOrigin.RIGHT_BOTTOM.ordinal(),
            EncodedOrigin.LEFT_BOTTOM.ordinal(),

            // special values
            EncodedOrigin.DEFAULT.ordinal(),
            EncodedOrigin.LEFT_BOTTOM.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void encodeJPEGAlphaMode() throws Exception {
        int[] nativeOrdinals = _nGetEncodeJPEGAlphaModeOrdinals();
        int[] javaOrdinals = {
            EncodeJPEGAlphaMode.IGNORE.ordinal(),
            EncodeJPEGAlphaMode.BLEND_ON_BLACK.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void encodeJPEGDownsampleMode() throws Exception {
        int[] nativeOrdinals = _nGetEncodeJPEGDownsampleModeOrdinals();
        int[] javaOrdinals = {
            EncodeJPEGDownsampleMode.DS_420.ordinal(),
            EncodeJPEGDownsampleMode.DS_422.ordinal(),
            EncodeJPEGDownsampleMode.DS_444.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void encodePNGFilterFlag() throws Exception {
        int[] nativeOrdinals = _nGetEncodePNGFilterFlagOrdinals();
        int[] javaOrdinals = {
            EncodePNGFilterFlag.ZERO._value,
            EncodePNGFilterFlag.NONE._value,
            EncodePNGFilterFlag.SUB._value,
            EncodePNGFilterFlag.UP._value,
            EncodePNGFilterFlag.AVG._value,
            EncodePNGFilterFlag.PAETH._value,
            EncodePNGFilterFlag.ALL._value
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void encodeWEBPCompressionMode() throws Exception {
        int[] nativeOrdinals = _nGetEncodeWEBPCompressionModeOrdinals();
        int[] javaOrdinals = {
            EncodeWEBPCompressionMode.LOSSY.ordinal(),
            EncodeWEBPCompressionMode.LOSSLESS.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void filterBlurMode() throws Exception {
        int[] nativeOrdinals = _nGetFilterBlurModeOrdinals();
        int[] javaOrdinals = {
            FilterBlurMode.NORMAL.ordinal(),
            FilterBlurMode.SOLID.ordinal(),
            FilterBlurMode.OUTER.ordinal(),
            FilterBlurMode.INNER.ordinal(),

            // last value
            FilterBlurMode.INNER.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void filterMode() throws Exception {
        int[] nativeOrdinals = _nGetFilterModeOrdinals();
        int[] javaOrdinals = {
            FilterMode.NEAREST.ordinal(),
            FilterMode.LINEAR.ordinal(),

            // last value
            FilterMode.LINEAR.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void filterTileMode() throws Exception {
        int[] nativeOrdinals = _nGetFilterTileModeOrdinals();
        int[] javaOrdinals = {
            FilterTileMode.CLAMP.ordinal(),
            FilterTileMode.REPEAT.ordinal(),
            FilterTileMode.MIRROR.ordinal(),
            FilterTileMode.DECAL.ordinal(),

            // last value
            FilterTileMode.DECAL.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void framebufferFormat() throws Exception {
        int[] nativeValues = _nGetFramebufferFormatValues();
        int[] javaValues = {
            FramebufferFormat.GR_GL_STENCIL_INDEX,
            FramebufferFormat.GR_GL_DEPTH_COMPONENT,
            FramebufferFormat.GR_GL_DEPTH_STENCIL,
            FramebufferFormat.GR_GL_RED,
            FramebufferFormat.GR_GL_RED_INTEGER,
            FramebufferFormat.GR_GL_GREEN,
            FramebufferFormat.GR_GL_BLUE,
            FramebufferFormat.GR_GL_ALPHA,
            FramebufferFormat.GR_GL_LUMINANCE,
            FramebufferFormat.GR_GL_LUMINANCE_ALPHA,
            FramebufferFormat.GR_GL_RG_INTEGER,
            FramebufferFormat.GR_GL_RGB,
            FramebufferFormat.GR_GL_RGB_INTEGER,
            FramebufferFormat.GR_GL_SRGB,
            FramebufferFormat.GR_GL_RGBA,
            FramebufferFormat.GR_GL_RG,
            FramebufferFormat.GR_GL_SRGB_ALPHA,
            FramebufferFormat.GR_GL_RGBA_INTEGER,
            FramebufferFormat.GR_GL_BGRA,
            FramebufferFormat.GR_GL_STENCIL_INDEX4,
            FramebufferFormat.GR_GL_STENCIL_INDEX8,
            FramebufferFormat.GR_GL_STENCIL_INDEX16,
            FramebufferFormat.GR_GL_DEPTH_COMPONENT16,
            FramebufferFormat.GR_GL_DEPTH24_STENCIL8,
            FramebufferFormat.GR_GL_R8,
            FramebufferFormat.GR_GL_R16,
            FramebufferFormat.GR_GL_R16F,
            FramebufferFormat.GR_GL_R32F,
            FramebufferFormat.GR_GL_R8I,
            FramebufferFormat.GR_GL_R8UI,
            FramebufferFormat.GR_GL_R16I,
            FramebufferFormat.GR_GL_R16UI,
            FramebufferFormat.GR_GL_R32I,
            FramebufferFormat.GR_GL_R32UI,
            FramebufferFormat.GR_GL_LUMINANCE8,
            FramebufferFormat.GR_GL_LUMINANCE8_ALPHA8,
            FramebufferFormat.GR_GL_LUMINANCE16F,
            FramebufferFormat.GR_GL_ALPHA8,
            FramebufferFormat.GR_GL_ALPHA16,
            FramebufferFormat.GR_GL_ALPHA16F,
            FramebufferFormat.GR_GL_ALPHA32F,
            FramebufferFormat.GR_GL_ALPHA8I,
            FramebufferFormat.GR_GL_ALPHA8UI,
            FramebufferFormat.GR_GL_ALPHA16I,
            FramebufferFormat.GR_GL_ALPHA16UI,
            FramebufferFormat.GR_GL_ALPHA32I,
            FramebufferFormat.GR_GL_ALPHA32UI,
            FramebufferFormat.GR_GL_RG8,
            FramebufferFormat.GR_GL_RG16,
            FramebufferFormat.GR_GL_RG16F,
            FramebufferFormat.GR_GL_RG8I,
            FramebufferFormat.GR_GL_RG8UI,
            FramebufferFormat.GR_GL_RG16I,
            FramebufferFormat.GR_GL_RG16UI,
            FramebufferFormat.GR_GL_RG32I,
            FramebufferFormat.GR_GL_RG32UI,
            FramebufferFormat.GR_GL_RGB5,
            FramebufferFormat.GR_GL_RGB565,
            FramebufferFormat.GR_GL_RGB8,
            FramebufferFormat.GR_GL_SRGB8,
            FramebufferFormat.GR_GL_RGBX8,
            FramebufferFormat.GR_GL_RGB8I,
            FramebufferFormat.GR_GL_RGB8UI,
            FramebufferFormat.GR_GL_RGB16I,
            FramebufferFormat.GR_GL_RGB16UI,
            FramebufferFormat.GR_GL_RGB32I,
            FramebufferFormat.GR_GL_RGB32UI,
            FramebufferFormat.GR_GL_RGBA4,
            FramebufferFormat.GR_GL_RGB5_A1,
            FramebufferFormat.GR_GL_RGBA8,
            FramebufferFormat.GR_GL_RGB10_A2,
            FramebufferFormat.GR_GL_SRGB8_ALPHA8,
            FramebufferFormat.GR_GL_RGBA16F,
            FramebufferFormat.GR_GL_RGBA32F,
            FramebufferFormat.GR_GL_RG32F,
            FramebufferFormat.GR_GL_RGBA16,
            FramebufferFormat.GR_GL_RGBA8I,
            FramebufferFormat.GR_GL_RGBA8UI,
            FramebufferFormat.GR_GL_RGBA16I,
            FramebufferFormat.GR_GL_RGBA16UI,
            FramebufferFormat.GR_GL_RGBA32I,
            FramebufferFormat.GR_GL_RGBA32UI,
            FramebufferFormat.GR_GL_BGRA8
        };
        TestRunner.assertArrayEquals(nativeValues, javaValues);
    }

    public void fontEdging() throws Exception {
        int[] nativeOrdinals = _nGetFontEdgingOrdinals();
        int[] javaOrdinals = {
            FontEdging.ALIAS.ordinal(),
            FontEdging.ANTI_ALIAS.ordinal(),
            FontEdging.SUBPIXEL_ANTI_ALIAS.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void fontHinting() throws Exception {
        int[] nativeOrdinals = _nGetFontHintingOrdinals();
        int[] javaOrdinals = {
            FontHinting.NONE.ordinal(),
            FontHinting.SLIGHT.ordinal(),
            FontHinting.NORMAL.ordinal(),
            FontHinting.FULL.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void fontSlant() throws Exception {
        int[] nativeOrdinals = _nGetFontSlantOrdinals();
        int[] javaOrdinals = {
            FontSlant.UPRIGHT.ordinal(),
            FontSlant.ITALIC.ordinal(),
            FontSlant.OBLIQUE.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void fontWeight() throws Exception {
        int[] nativeOrdinals = _nGetFontWeightOrdinals();
        int[] javaOrdinals = {
            FontWeight.INVISIBLE,
            FontWeight.THIN,
            FontWeight.EXTRA_LIGHT,
            FontWeight.LIGHT,
            FontWeight.NORMAL,
            FontWeight.MEDIUM,
            FontWeight.SEMI_BOLD,
            FontWeight.BOLD,
            FontWeight.EXTRA_BOLD,
            FontWeight.BLACK,
            FontWeight.EXTRA_BLACK
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void fontWidth() throws Exception {
        int[] nativeOrdinals = _nGetFontWidthOrdinals();
        int[] javaOrdinals = {
            FontWidth.ULTRA_CONDENSED,
            FontWidth.EXTRA_CONDENSED,
            FontWidth.CONDENSED,
            FontWidth.SEMI_CONDENSED,
            FontWidth.NORMAL,
            FontWidth.SEMI_EXPANDED,
            FontWidth.EXPANDED,
            FontWidth.EXTRA_EXPANDED,
            FontWidth.ULTRA_EXPANDED
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void inversionMode() throws Exception {
        int[] nativeOrdinals = _nGetInversionModeOrdinals();
        int[] javaOrdinals = {
            InversionMode.NO.ordinal(),
            InversionMode.BRIGHTNESS.ordinal(),
            InversionMode.LIGHTNESS.ordinal(),

            // last value
            InversionMode.LIGHTNESS.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void mipmapMode() throws Exception {
        int[] nativeOrdinals = _nGetMipmapModeOrdinals();
        int[] javaOrdinals = {
            MipmapMode.NONE.ordinal(),
            MipmapMode.NEAREST.ordinal(),
            MipmapMode.LINEAR.ordinal(),

            // last value
            MipmapMode.LINEAR.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void paintMode() throws Exception {
        int[] nativeOrdinals = _nGetPaintModeOrdinals();
        int[] javaOrdinals = {
            PaintMode.FILL.ordinal(),
            PaintMode.STROKE.ordinal(),
            PaintMode.STROKE_AND_FILL.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void paintStrokeCap() throws Exception {
        int[] nativeOrdinals = _nGetPaintStrokeCapOrdinals();
        int[] javaOrdinals = {
            PaintStrokeCap.BUTT.ordinal(),
            PaintStrokeCap.ROUND.ordinal(),
            PaintStrokeCap.SQUARE.ordinal(),

            // last value
            PaintStrokeCap.SQUARE.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void paintStrokeJoin() throws Exception {
        int[] nativeOrdinals = _nGetPaintStrokeJoinOrdinals();
        int[] javaOrdinals = {
            PaintStrokeJoin.MITER.ordinal(),
            PaintStrokeJoin.ROUND.ordinal(),
            PaintStrokeJoin.BEVEL.ordinal(),

            // last value
            PaintStrokeJoin.BEVEL.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void pathDirection() throws Exception {
        int[] nativeOrdinals = _nGetPathDirectionOrdinals();
        int[] javaOrdinals = {
            PathDirection.CLOCKWISE.ordinal(),
            PathDirection.COUNTER_CLOCKWISE.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void pathEffect1DStyle() throws Exception {
        int[] nativeOrdinals = _nGetPathEffect1DStyleOrdinals();
        int[] javaOrdinals = {
            PathEffect1DStyle.TRANSLATE.ordinal(),
            PathEffect1DStyle.ROTATE.ordinal(),
            PathEffect1DStyle.MORPH.ordinal(),

            // last value
            PathEffect1DStyle.MORPH.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void pathEllipseArc() throws Exception {
        int[] nativeOrdinals = _nGetPathEllipseArcOrdinals();
        int[] javaOrdinals = {
            PathEllipseArc.SMALLER.ordinal(),
            PathEllipseArc.LARGER.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void pathFillMode() throws Exception {
        int[] nativeOrdinals = _nGetPathFillModeOrdinals();
        int[] javaOrdinals = {
            PathFillMode.WINDING.ordinal(),
            PathFillMode.EVEN_ODD.ordinal(),
            PathFillMode.INVERSE_WINDING.ordinal(),
            PathFillMode.INVERSE_EVEN_ODD.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void pathOp() throws Exception {
        int[] nativeOrdinals = _nGetPathOpOrdinals();
        int[] javaOrdinals = {
            PathOp.DIFFERENCE.ordinal(),
            PathOp.INTERSECT.ordinal(),
            PathOp.UNION.ordinal(),
            PathOp.XOR.ordinal(),
            PathOp.REVERSE_DIFFERENCE.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void pathSegmentType() throws Exception {
        int[] nativeValues = _nGetPathSegmentTypeValues();
        int[] javaValues = {
            PathSegmentType.LINE._value,
            PathSegmentType.QUAD._value,
            PathSegmentType.CONIC._value,
            PathSegmentType.CUBIC._value
        };
        TestRunner.assertArrayEquals(nativeValues, javaValues);
    }

    public void pathVerb() throws Exception {
        int[] nativeOrdinals = _nGetPathVerbOrdinals();
        int[] javaOrdinals = {
            PathVerb.MOVE.ordinal(),
            PathVerb.LINE.ordinal(),
            PathVerb.QUAD.ordinal(),
            PathVerb.CONIC.ordinal(),
            PathVerb.CUBIC.ordinal(),
            PathVerb.CLOSE.ordinal(),

            // last value
            PathVerb.CLOSE.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void pixelGeometry() throws Exception {
        int[] nativeOrdinals = _nGetPixelGeometryOrdinals();
        int[] javaOrdinals = {
            PixelGeometry.UNKNOWN.ordinal(),
            PixelGeometry.RGB_H.ordinal(),
            PixelGeometry.BGR_H.ordinal(),
            PixelGeometry.RGB_V.ordinal(),
            PixelGeometry.BGR_V.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void regionOp() throws Exception {
        int[] nativeOrdinals = _nGetRegionOpOrdinals();
        int[] javaOrdinals = {
            RegionOp.DIFFERENCE.ordinal(),
            RegionOp.INTERSECT.ordinal(),
            RegionOp.UNION.ordinal(),
            RegionOp.XOR.ordinal(),
            RegionOp.REVERSE_DIFFERENCE.ordinal(),
            RegionOp.REPLACE.ordinal(),

            // last value
            RegionOp.REPLACE.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void runtimeEffectChildType() throws Exception {
        int[] nativeOrdinals = _nGetRuntimeEffectChildTypeOrdinals();
        int[] javaOrdinals = {
            RuntimeEffectChildType.SHADER.ordinal(),
            RuntimeEffectChildType.COLOR_FILTER.ordinal(),
            RuntimeEffectChildType.BLENDER.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void runtimeEffectUniformFlag() throws Exception {
        int[] nativeOrdinals = _nGetRuntimeEffectUniformFlagOrdinals();
        int[] javaOrdinals = {
            RuntimeEffectUniformFlag.ARRAY._value,
            RuntimeEffectUniformFlag.COLOR._value,
            RuntimeEffectUniformFlag.VERTEX._value,
            RuntimeEffectUniformFlag.FRAGMENT._value,
            RuntimeEffectUniformFlag.HALF_PRECISION._value
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void runtimeEffectUniformType() throws Exception {
        int[] nativeOrdinals = _nGetRuntimeEffectUniformTypeOrdinals();
        int[] javaOrdinals = {
            RuntimeEffectUniformType.FLOAT.ordinal(),
            RuntimeEffectUniformType.FLOAT2.ordinal(),
            RuntimeEffectUniformType.FLOAT3.ordinal(),
            RuntimeEffectUniformType.FLOAT4.ordinal(),
            RuntimeEffectUniformType.FLOAT2X2.ordinal(),
            RuntimeEffectUniformType.FLOAT3X3.ordinal(),
            RuntimeEffectUniformType.FLOAT4X4.ordinal(),
            RuntimeEffectUniformType.INT.ordinal(),
            RuntimeEffectUniformType.INT2.ordinal(),
            RuntimeEffectUniformType.INT3.ordinal(),
            RuntimeEffectUniformType.INT4.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void saveLayerRecFlag() throws Exception {
        int[] nativeOrdinals = _nGetSaveLayerRecFlagOrdinals();
        int[] javaOrdinals = {
            SaveLayerRecFlag.PRESERVE_LCD_TEXT._flag,
            SaveLayerRecFlag.INIT_WITH_PREVIOUS._flag,
            SaveLayerRecFlag.F16_COLOR_TYPE._flag
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void shadowUtilsFlag() throws Exception {
        int[] nativeOrdinals = _nGetShadowUtilsFlagOrdinals();
        int[] javaOrdinals = {
            ShadowUtilsFlag.NONE._value,
            ShadowUtilsFlag.TRANSPARENT_OCCLUDER._value,
            ShadowUtilsFlag.GEOMETRIC_ONLY._value,
            ShadowUtilsFlag.DIRECTIONAL_LIGHT._value,
            ShadowUtilsFlag.CONCAVE_BLUR_ONLY._value,
            ShadowUtilsFlag.ALL._value
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void surfaceOrigin() throws Exception {
        int[] nativeOrdinals = _nGetSurfaceOriginOrdinals();
        int[] javaOrdinals = {
            SurfaceOrigin.TOP_LEFT.ordinal(),
            SurfaceOrigin.BOTTOM_LEFT.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void paragraphAffinity() throws Exception {
        int[] nativeOrdinals = _nGetParagraphAffinityOrdinals();
        int[] javaOrdinals = {
            Affinity.UPSTREAM.ordinal(),
            Affinity.DOWNSTREAM.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void paragraphAlignment() throws Exception {
        int[] nativeOrdinals = _nGetParagraphAlignmentOrdinals();
        int[] javaOrdinals = {
            Alignment.LEFT.ordinal(),
            Alignment.RIGHT.ordinal(),
            Alignment.CENTER.ordinal(),
            Alignment.JUSTIFY.ordinal(),
            Alignment.START.ordinal(),
            Alignment.END.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void paragraphBaselineMode() throws Exception {
        int[] nativeOrdinals = _nGetParagraphBaselineModeOrdinals();
        int[] javaOrdinals = {
            BaselineMode.ALPHABETIC.ordinal(),
            BaselineMode.IDEOGRAPHIC.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void paragraphDecorationLineStyle() throws Exception {
        int[] nativeOrdinals = _nGetParagraphDecorationLineStyleOrdinals();
        int[] javaOrdinals = {
            DecorationLineStyle.SOLID.ordinal(),
            DecorationLineStyle.DOUBLE.ordinal(),
            DecorationLineStyle.DOTTED.ordinal(),
            DecorationLineStyle.DASHED.ordinal(),
            DecorationLineStyle.WAVY.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void paragraphDirection() throws Exception {
        int[] nativeOrdinals = _nGetParagraphDirectionOrdinals();
        int[] javaOrdinals = {
            Direction.RTL.ordinal(),
            Direction.LTR.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void paragraphHeightMode() throws Exception {
        int[] nativeOrdinals = _nGetParagraphHeightModeOrdinals();
        int[] javaOrdinals = {
            HeightMode.ALL.ordinal(),
            HeightMode.DISABLE_FIRST_ASCENT.ordinal(),
            HeightMode.DISABLE_LAST_DESCENT.ordinal(),
            HeightMode.DISABLE_ALL.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void paragraphPlaceholderAlignment() throws Exception {
        int[] nativeOrdinals = _nGetParagraphPlaceholderAlignmentOrdinals();
        int[] javaOrdinals = {
            PlaceholderAlignment.BASELINE.ordinal(),
            PlaceholderAlignment.ABOVE_BASELINE.ordinal(),
            PlaceholderAlignment.BELOW_BASELINE.ordinal(),
            PlaceholderAlignment.TOP.ordinal(),
            PlaceholderAlignment.BOTTOM.ordinal(),
            PlaceholderAlignment.MIDDLE.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void paragraphRectHeightMode() throws Exception {
        int[] nativeOrdinals = _nGetParagraphRectHeightModeOrdinals();
        int[] javaOrdinals = {
            RectHeightMode.TIGHT.ordinal(),
            RectHeightMode.MAX.ordinal(),
            RectHeightMode.INCLUDE_LINE_SPACING_MIDDLE.ordinal(),
            RectHeightMode.INCLUDE_LINE_SPACING_TOP.ordinal(),
            RectHeightMode.INCLUDE_LINE_SPACING_BOTTOM.ordinal(),
            RectHeightMode.STRUT.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void paragraphRectWidthMode() throws Exception {
        int[] nativeOrdinals = _nGetParagraphRectWidthModeOrdinals();
        int[] javaOrdinals = {
            RectWidthMode.TIGHT.ordinal(),
            RectWidthMode.MAX.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void paragraphTextStyleAttribute() throws Exception {
        int[] nativeOrdinals = _nGetParagraphTextStyleAttributeOrdinals();
        int[] javaOrdinals = {
            TextStyleAttribute.NONE.ordinal(),
            TextStyleAttribute.ALL_ATTRIBUTES.ordinal(),
            TextStyleAttribute.FONT.ordinal(),
            TextStyleAttribute.FOREGROUND.ordinal(),
            TextStyleAttribute.BACKGROUND.ordinal(),
            TextStyleAttribute.SHADOW.ordinal(),
            TextStyleAttribute.DECORATIONS.ordinal(),
            TextStyleAttribute.LETTER_SPACING.ordinal(),
            TextStyleAttribute.WORD_SPACING.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void skottieAnimationBuilderFlag() throws Exception {
        int[] nativeOrdinals = _nGetSkottieAnimationBuilderFlagOrdinals();
        int[] javaOrdinals = {
            AnimationBuilderFlag.DEFER_IMAGE_LOADING._flag,
            AnimationBuilderFlag.PREFER_EMBEDDED_FONTS._flag
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void skottieLogLevel() throws Exception {
        int[] nativeOrdinals = _nGetSkottieLogLevelOrdinals();
        int[] javaOrdinals = {
            LogLevel.WARNING.ordinal(),
            LogLevel.ERROR.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void skottieRenderFlag() throws Exception {
        int[] nativeOrdinals = _nGetSkottieRenderFlagOrdinals();
        int[] javaOrdinals = {
            RenderFlag.SKIP_TOP_LEVEL_ISOLATION._flag,
            RenderFlag.DISABLE_TOP_LEVEL_CLIPPING._flag
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void svgLengthType() throws Exception {
        int[] nativeOrdinals = _nGetSvgLengthTypeOrdinals();
        int[] javaOrdinals = {
            SVGLengthType.HORIZONTAL.ordinal(),
            SVGLengthType.VERTICAL.ordinal(),
            SVGLengthType.OTHER.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void svgLengthUnit() throws Exception {
        int[] nativeOrdinals = _nGetSvgLengthUnitOrdinals();
        int[] javaOrdinals = {
            SVGLengthUnit.UNKNOWN.ordinal(),
            SVGLengthUnit.NUMBER.ordinal(),
            SVGLengthUnit.PERCENTAGE.ordinal(),
            SVGLengthUnit.EMS.ordinal(),
            SVGLengthUnit.EXS.ordinal(),
            SVGLengthUnit.PX.ordinal(),
            SVGLengthUnit.CM.ordinal(),
            SVGLengthUnit.MM.ordinal(),
            SVGLengthUnit.IN.ordinal(),
            SVGLengthUnit.PT.ordinal(),
            SVGLengthUnit.PC.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void svgPreserveAspectRatioAlign() throws Exception {
        int[] nativeOrdinals = _nGetSvgPreserveAspectRatioAlignOrdinals();
        int[] javaOrdinals = {
            SVGPreserveAspectRatioAlign.XMIN_YMIN._value,
            SVGPreserveAspectRatioAlign.XMID_YMIN._value,
            SVGPreserveAspectRatioAlign.XMAX_YMIN._value,
            SVGPreserveAspectRatioAlign.XMIN_YMID._value,
            SVGPreserveAspectRatioAlign.XMID_YMID._value,
            SVGPreserveAspectRatioAlign.XMAX_YMID._value,
            SVGPreserveAspectRatioAlign.XMIN_YMAX._value,
            SVGPreserveAspectRatioAlign.XMID_YMAX._value,
            SVGPreserveAspectRatioAlign.XMAX_YMAX._value,
            SVGPreserveAspectRatioAlign.NONE._value
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void svgPreserveAspectRatioScale() throws Exception {
        int[] nativeOrdinals = _nGetSvgPreserveAspectRatioScaleOrdinals();
        int[] javaOrdinals = {
            SVGPreserveAspectRatioScale.MEET.ordinal(),
            SVGPreserveAspectRatioScale.SLICE.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public void svgTag() throws Exception {
        int[] nativeOrdinals = _nGetSvgTagOrdinals();
        int[] javaOrdinals = {
            SVGTag.CIRCLE.ordinal(),
            SVGTag.CLIP_PATH.ordinal(),
            SVGTag.DEFS.ordinal(),
            SVGTag.ELLIPSE.ordinal(),
            SVGTag.FE_BLEND.ordinal(),
            SVGTag.FE_COLOR_MATRIX.ordinal(),
            SVGTag.FE_COMPONENT_TRANSFER.ordinal(),
            SVGTag.FE_COMPOSITE.ordinal(),
            SVGTag.FE_DIFFUSE_LIGHTING.ordinal(),
            SVGTag.FE_DISPLACEMENT_MAP.ordinal(),
            SVGTag.FE_DISTANT_LIGHT.ordinal(),
            SVGTag.FE_FLOOD.ordinal(),
            SVGTag.FE_FUNC_A.ordinal(),
            SVGTag.FE_FUNC_R.ordinal(),
            SVGTag.FE_FUNC_G.ordinal(),
            SVGTag.FE_FUNC_B.ordinal(),
            SVGTag.FE_GAUSSIAN_BLUR.ordinal(),
            SVGTag.FE_IMAGE.ordinal(),
            SVGTag.FE_MERGE.ordinal(),
            SVGTag.FE_MERGE_NODE.ordinal(),
            SVGTag.FE_MORPHOLOGY.ordinal(),
            SVGTag.FE_OFFSET.ordinal(),
            SVGTag.FE_POINT_LIGHT.ordinal(),
            SVGTag.FE_SPECULAR_LIGHTING.ordinal(),
            SVGTag.FE_SPOT_LIGHT.ordinal(),
            SVGTag.FE_TURBULENCE.ordinal(),
            SVGTag.FILTER.ordinal(),
            SVGTag.G.ordinal(),
            SVGTag.IMAGE.ordinal(),
            SVGTag.LINE.ordinal(),
            SVGTag.LINEAR_GRADIENT.ordinal(),
            SVGTag.MASK.ordinal(),
            SVGTag.PATH.ordinal(),
            SVGTag.PATTERN.ordinal(),
            SVGTag.POLYGON.ordinal(),
            SVGTag.POLYLINE.ordinal(),
            SVGTag.RADIAL_GRADIENT.ordinal(),
            SVGTag.RECT.ordinal(),
            SVGTag.STOP.ordinal(),
            SVGTag.SVG.ordinal(),
            SVGTag.TEXT.ordinal(),
            SVGTag.TEXT_LITERAL.ordinal(),
            SVGTag.TEXTPATH.ordinal(),
            SVGTag.TSPAN.ordinal(),
            SVGTag.USE.ordinal()
        };
        TestRunner.assertArrayEquals(nativeOrdinals, javaOrdinals);
    }

    public static native int[] _nGetAnimationDisposalModeOrdinals();
    public static native int[] _nGetBackendStateOrdinals();
    public static native int[] _nGetBlendModeOrdinals();
    public static native int[] _nGetClipModeOrdinals();
    public static native int[] _nGetColorAlphaTypeOrdinals();
    public static native int[] _nGetColorChannelOrdinals();
    public static native int[] _nGetColorChannelFlagOrdinals();
    public static native int[] _nGetColorTypeOrdinals();
    public static native int[] _nGetColorSpaceNamedPrimariesValues();
    public static native int[] _nGetColorSpaceNamedTransferFnValues();
    public static native int[] _nGetContentChangeModeOrdinals();
    public static native int[] _nGetEncodedImageFormatOrdinals();
    public static native int[] _nGetEncodedOriginOrdinals();
    public static native int[] _nGetEncodeJPEGAlphaModeOrdinals();
    public static native int[] _nGetEncodeJPEGDownsampleModeOrdinals();
    public static native int[] _nGetEncodePNGFilterFlagOrdinals();
    public static native int[] _nGetEncodeWEBPCompressionModeOrdinals();
    public static native int[] _nGetFilterBlurModeOrdinals();
    public static native int[] _nGetFilterModeOrdinals();
    public static native int[] _nGetFilterTileModeOrdinals();
    public static native int[] _nGetFramebufferFormatValues();
    public static native int[] _nGetFontEdgingOrdinals();
    public static native int[] _nGetFontHintingOrdinals();
    public static native int[] _nGetFontSlantOrdinals();
    public static native int[] _nGetFontWeightOrdinals();
    public static native int[] _nGetFontWidthOrdinals();
    public static native int[] _nGetInversionModeOrdinals();
    public static native int[] _nGetMipmapModeOrdinals();
    public static native int[] _nGetPaintModeOrdinals();
    public static native int[] _nGetPaintStrokeCapOrdinals();
    public static native int[] _nGetPaintStrokeJoinOrdinals();
    public static native int[] _nGetPathDirectionOrdinals();
    public static native int[] _nGetPathEffect1DStyleOrdinals();
    public static native int[] _nGetPathEllipseArcOrdinals();
    public static native int[] _nGetPathFillModeOrdinals();
    public static native int[] _nGetPathOpOrdinals();
    public static native int[] _nGetPathSegmentTypeValues();
    public static native int[] _nGetPathVerbOrdinals();
    public static native int[] _nGetPixelGeometryOrdinals();
    public static native int[] _nGetRegionOpOrdinals();
    public static native int[] _nGetRuntimeEffectChildTypeOrdinals();
    public static native int[] _nGetRuntimeEffectUniformFlagOrdinals();
    public static native int[] _nGetRuntimeEffectUniformTypeOrdinals();
    public static native int[] _nGetSaveLayerRecFlagOrdinals();
    public static native int[] _nGetShadowUtilsFlagOrdinals();
    public static native int[] _nGetSurfaceOriginOrdinals();
    public static native int[] _nGetParagraphAffinityOrdinals();
    public static native int[] _nGetParagraphAlignmentOrdinals();
    public static native int[] _nGetParagraphBaselineModeOrdinals();
    public static native int[] _nGetParagraphDecorationLineStyleOrdinals();
    public static native int[] _nGetParagraphDirectionOrdinals();
    public static native int[] _nGetParagraphHeightModeOrdinals();
    public static native int[] _nGetParagraphPlaceholderAlignmentOrdinals();
    public static native int[] _nGetParagraphRectHeightModeOrdinals();
    public static native int[] _nGetParagraphRectWidthModeOrdinals();
    public static native int[] _nGetParagraphTextStyleAttributeOrdinals();
    public static native int[] _nGetSkottieAnimationBuilderFlagOrdinals();
    public static native int[] _nGetSkottieLogLevelOrdinals();
    public static native int[] _nGetSkottieRenderFlagOrdinals();
    public static native int[] _nGetSvgLengthTypeOrdinals();
    public static native int[] _nGetSvgLengthUnitOrdinals();
    public static native int[] _nGetSvgPreserveAspectRatioAlignOrdinals();
    public static native int[] _nGetSvgPreserveAspectRatioScaleOrdinals();
    public static native int[] _nGetSvgTagOrdinals();
}
