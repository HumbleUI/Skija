package io.github.humbleui.skija.test;

import static io.github.humbleui.skija.test.runner.TestRunner.*;

import java.util.*;

import io.github.humbleui.skija.*;
import io.github.humbleui.skija.impl.*;
import io.github.humbleui.skija.test.runner.*;

public class EnumTest implements Executable {
    static { Library.staticLoad(); }

    @Override
    public void execute() throws Exception {
        TestRunner.testMethod(this, "animationDisposalMode");
        TestRunner.testMethod(this, "blendMode");
        TestRunner.testMethod(this, "clipMode");
        TestRunner.testMethod(this, "colorAlphaType");
        TestRunner.testMethod(this, "colorChannel");
        TestRunner.testMethod(this, "colorChannelFlag");
        TestRunner.testMethod(this, "colorType");
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
        TestRunner.testMethod(this, "fontEdging");
        TestRunner.testMethod(this, "fontHinting");
        TestRunner.testMethod(this, "fontSlant");
        TestRunner.testMethod(this, "fontWeight");
        TestRunner.testMethod(this, "fontWidth");
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

    public static native int[] _nGetAnimationDisposalModeOrdinals();
    public static native int[] _nGetBlendModeOrdinals();
    public static native int[] _nGetClipModeOrdinals();
    public static native int[] _nGetColorAlphaTypeOrdinals();
    public static native int[] _nGetColorChannelOrdinals();
    public static native int[] _nGetColorChannelFlagOrdinals();
    public static native int[] _nGetColorTypeOrdinals();
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
    public static native int[] _nGetFontEdgingOrdinals();
    public static native int[] _nGetFontHintingOrdinals();
    public static native int[] _nGetFontSlantOrdinals();
    public static native int[] _nGetFontWeightOrdinals();
    public static native int[] _nGetFontWidthOrdinals();
}
