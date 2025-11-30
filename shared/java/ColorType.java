package io.github.humbleui.skija;

import org.jetbrains.annotations.*;
import io.github.humbleui.skija.impl.*;

/**
 * <p>Describes how pixel bits encode color. A pixel may be an alpha mask, a
 * grayscale, RGB, or ARGB.</p>
 *
 * <p>{@link #N32} selects the native 32-bit ARGB format for the current
 * configuration. This can lead to inconsistent results across platforms, so
 * use with caution.</p>
 *
 * <p>By default, Skia operates with the assumption of a little-Endian
 * system. The names of each ColorType implicitly define the channel
 * ordering and size in memory. Due to historical reasons the names do not
 * follow 100% identical convention, but are typically labeled from least
 * significant to most significant. To help clarify when the actual data
 * layout differs from the default convention, every ColorType's comment
 * includes a bit-labeled description of a pixel in that color type on a LE
 * system.</p>
 *
 * <p>Unless specified otherwise, a channel's value is treated as an
 * unsigned integer with a range of [0, 2^N-1] and this is mapped uniformly
 * to a floating point value of [0.0, 1.0]. Some color types instead store
 * data directly in 32-bit floating point (assumed to be IEEE), or in 16-bit
 * "half" floating point values. A half float, or F16/float16, is
 * interpreted as FP 1-5-10 or</p>
 *
 * <p>Bits: [sign:15 exp:14..10 man:9..0]</p>
 */
public enum ColorType {
    /**
     * Unknown or unrepresentable as an ColorType.
     */
    UNKNOWN,      

    /**
     * <p>Single channel data (8-bit) interpreted as an alpha value. RGB
     * are 0.</p>
     *
     * <p>Bits: [A:7..0]</p>
     */
    ALPHA_8,      

    /**
     * <p>Three channel BGR data (5 bits red, 6 bits green, 5 bits blue)
     * packed into a LE 16-bit word.</p>
     *
     * <p>NOTE: The name of this enum value does not match the standard
     * convention for ColorType.</p>
     *
     * <p>Bits: [R:15..11 G:10..5 B:4..0]</p>
     */
    RGB_565,      

    /**
     * <p>Four channel ABGR data (4 bits per channel) packed into a LE
     * 16-bit word.</p>
     *
     * <p>NOTE: The name of this enum value does not match the standard
     * convention for SColorType.</p>
     *
     * <p>Bits: [R:15..12 G:11..8 B:7..4 A:3..0]</p>
     */
    ARGB_4444,    

    /**
     * <p>Four channel RGBA data (8 bits per channel) packed into a LE
     * 32-bit word.</p>
     *
     * <p>Bits: [A:31..24 B:23..16 G:15..8 R:7..0]</p>
     */
    RGBA_8888,    

    /**
     * <p>Three channel RGB data (8 bits per channel) packed into a LE
     * 32-bit word. The remaining bits are ignored and alpha is forced to
     * opaque.</p>
     *
     * <p>Bits: [x:31..24 B:23..16 G:15..8 R:7..0]</p>
     */
    RGB_888X,     

    /**
     * <p>Four channel BGRA data (8 bits per channel) packed into a LE
     * 32-bit word. R and B are swapped relative to {@link #RGBA_8888}.</p>
     *
     * <p>Bits: [A:31..24 R:23..16 G:15..8 B:7..0]</p>
     */
    BGRA_8888,    

    /**
     * <p>Four channel RGBA data (10 bits per color, 2 bits for alpha)
     * packed into a LE 32-bit word.</p>
     *
     * <p>Bits: [A:31..30 B:29..20 G:19..10 R:9..0]</p>
     */
    RGBA_1010102, 

    /**
     * <p>Four channel BGRA data (10 bits per color, 2 bits for alpha)
     * packed into a LE 32-bit word. R and B are swapped relative to
     * {@link #RGBA_1010102}.</p>
     *
     * <p>Bits: [A:31..30 R:29..20 G:19..10 B:9..0]</p>
     */
    BGRA_1010102, 

    /**
     * <p>Three channel RGB data (10 bits per channel) packed into a LE
     * 32-bit word. The remaining bits are ignored and alpha is forced to
     * opaque.</p>
     *
     * <p>Bits: [x:31..30 B:29..20 G:19..10 R:9..0]</p>
     */
    RGB_101010X,  

    /**
     * <p>Three channel BGR data (10 bits per channel) packed into a LE
     * 32-bit word. The remaining bits are ignored and alpha is forced to
     * opaque. R and B are swapped relative to {@link #RGB_101010X}.</p>
     *
     * <p>Bits: [x:31..30 R:29..20 G:19..10 B:9..0]</p>
     */
    BGR_101010X,

    /**
     * <p>Three channel BGR data (10 bits per channel) packed into a LE
     * 32-bit word. The remaining bits are ignored and alpha is forced to
     * opaque. Instead of normalizing [0, 1023] to [0.0, 1.0] the color
     * channels map to an extended range of [-0.752941, 1.25098],
     * compatible with MTLPixelFormatBGR10_XR.</p>
     *
     * <p>Bits: [x:31..30 R:29..20 G:19..10 B:9..0]</p>
     */
    BGR_101010X_XR,

    /**
     * <p>Four channel BGRA data (10 bits per channel) packed into a LE 64-bit
     * word. Each channel is preceded by 6 bits of padding.  Instead of
     * normalizing [0, 1023] to [0.0, 1.0] the color and alpha channels map to
     * an extended range of [-0.752941, 1.25098], compatible with
     * MTLPixelFormatBGRA10_XR.</p>
     *
     * <p>Bits: [A:63..54 x:53..48 R:47..38 x:37..32 G:31..22 x:21..16 B:15..6 x:5..0]</p>
     */
    BGRA_10101010_XR,

    /**
     * <p>Four channel RGBA data (10 bits per channel) packed into a LE 64-bit
     * word. Each channel is preceded by 6 bits of padding.</p>
     *
     * <p>Bits: [A:63..54 x:53..48 B:47..38 x:37..32 G:31..22 x:21..16 R:15..6 x:5..0]</p>
     */
    RGBA_10X6,

    /**
     * <p>Single channel data (8-bit) interpreted as a grayscale value
     * (e.g. replicated to RGB).</p>
     *
     * <p>Bits: [G:7..0]</p>
     */
    GRAY_8,

    /**
     * <p>Four channel RGBA data (16-bit half-float per channel) packed
     * into a LE 64-bit word. Values are assumed to be in [0.0,1.0] range,
     * unlike {@link #RGBA_F16}.</p>
     *
     * <p>Bits: [A:63..48 B:47..32 G:31..16 R:15..0]</p>
     */
    RGBA_F16NORM, 

    /**
     * <p>Four channel RGBA data (16-bit half-float per channel) packed
     * into a LE 64-bit word. This has extended range compared to
     * {@link #RGBA_F16NORM}.</p>
     *
     * <p>Bits: [A:63..48 B:47..32 G:31..16 R:15..0]</p>
     */
    RGBA_F16,

    /**
     * <p>Three channel RGB data (16-bit half-float per channel) packed into a
     * LE 64-bit word. The last 16 bits are ignored and alpha is forced to
     * opaque.</p>
     *
     * <p>Bits: [x:63..48 B:47..32 G:31..16 R:15..0]</p>
     */
    RGB_F16F16F16X,

    /**
     * <p>Four channel RGBA data (32-bit float per channel) packed into a
     * LE 128-bit word.</p>
     *
     * <p>Bits: [A:127..96 B:95..64 G:63..32 R:31..0]</p>
     */
    RGBA_F32,     


    // The following 8 colortypes are just for reading from - not for rendering to

    /**
     * <p>Two channel RG data (8 bits per channel). Blue is forced to 0,
     * alpha is forced to opaque.</p>
     *
     * <p>Bits: [G:15..8 R:7..0]</p>
     */
    R8G8_UNORM,        

    /**
     * <p>Single channel data (16-bit half-float) interpreted as alpha.
     * RGB are 0.</p>
     *
     * <p>Bits: [A:15..0]</p>
     */
    A16_FLOAT,         

    /**
     * <p>Two channel RG data (16-bit half-float per channel) packed into
     * a LE 32-bit word. Blue is forced to 0, alpha is forced to
     * opaque.</p>
     *
     * <p>Bits: [G:31..16 R:15..0]</p>
     */
    R16G16_FLOAT,      

    /**
     * <p>Single channel data (16 bits) interpreted as alpha. RGB are 0.</p>
     *
     * <p>Bits: [A:15..0]</p>
     */
    A16_UNORM,         

    /**
     * <p>Two channel RG data (16 bits per channel) packed into a LE
     * 32-bit word. B is forced to 0, alpha is forced to opaque.</p>
     *
     * <p>Bits: [G:31..16 R:15..0]</p>
     */
    R16G16_UNORM,      

    /**
     * <p>Four channel RGBA data (16 bits per channel) packed into a LE
     * 64-bit word.</p>
     *
     * <p>Bits: [A:63..48 B:47..32 G:31..16 R:15..0]</p>
     */
    R16G16B16A16_UNORM,

    /**
     * <p>Four channel RGBA data (8 bits per channel) packed into a LE 32-bit
     * word. The RGB values are assumed to be encoded with the sRGB transfer
     * function, which can be decoded automatically by GPU hardware with
     * certain texture formats.</p>
     *
     * <p>Bits: [A:31..24 B:23..16 G:15..8 R:7..0]</p>
     */
    SRGBA_8888,

    /**
     * <p>Single channel data (8 bits) interpreted as red. G and B are forced
     * to 0, alpha is forced to opaque.</p>
     *
     * <p>Bits: [R:7..0]</p>
     */
    R8_UNORM;

    /**
     * Native ARGB 32-bit encoding
     */
    public static final ColorType N32;

    static {
        // SkTypes.h / SK_R32_SHIFT
        // BUILD.gn / if (is_linux) { defines += [ "SK_R32_SHIFT=16" ] }
        String os = System.getProperty("os.name").toLowerCase();
        N32 = os.contains("mac") || os.contains("darwin") ? RGBA_8888 : BGRA_8888;
    }

    // SkImageInfo.cpp / SkColorTypeBytesPerPixel
    /**
     * Returns the number of bytes required to store a pixel, including unused padding.
     * Returns zero for {@link #UNKNOWN}.
     *
     * @return  bytes per pixel
     */  
    public int getBytesPerPixel() {
        switch (this) {
            case UNKNOWN:            return 0;
            case ALPHA_8:            return 1;
            case RGB_565:            return 2;
            case ARGB_4444:          return 2;
            case RGBA_8888:          return 4;
            case BGRA_8888:          return 4;
            case RGB_888X:           return 4;
            case RGBA_1010102:       return 4;
            case RGB_101010X:        return 4;
            case BGRA_1010102:       return 4;
            case BGR_101010X:        return 4;
            case BGR_101010X_XR:     return 4;
            case BGRA_10101010_XR:   return 8;
            case RGBA_10X6:          return 8;
            case GRAY_8:             return 1;
            case RGBA_F16NORM:       return 8;
            case RGBA_F16:           return 8;
            case RGB_F16F16F16X:     return 8;
            case RGBA_F32:           return 16;
            case R8G8_UNORM:         return 2;
            case A16_UNORM:          return 2;
            case R16G16_UNORM:       return 4;
            case A16_FLOAT:          return 2;
            case R16G16_FLOAT:       return 4;
            case R16G16B16A16_UNORM: return 8;
            case SRGBA_8888:         return 4;
            case R8_UNORM:           return 1;
        }
        throw new RuntimeException("Unreachable");
    }

    // SkImageInfoPriv.h / SkColorTypeShiftPerPixel
    public int getShiftPerPixel() {
        switch (this) {
            case UNKNOWN:            return 0;
            case ALPHA_8:            return 0;
            case RGB_565:            return 1;
            case ARGB_4444:          return 1;
            case RGBA_8888:          return 2;
            case RGB_888X:           return 2;
            case BGRA_8888:          return 2;
            case RGBA_1010102:       return 2;
            case RGB_101010X:        return 2;
            case BGRA_1010102:       return 2;
            case BGR_101010X:        return 2;
            case BGR_101010X_XR:     return 2;
            case BGRA_10101010_XR:   return 3;
            case RGBA_10X6:          return 3;
            case GRAY_8:             return 0;
            case RGBA_F16NORM:       return 3;
            case RGBA_F16:           return 3;
            case RGB_F16F16F16X:     return 3;
            case RGBA_F32:           return 4;
            case R8G8_UNORM:         return 1;
            case A16_UNORM:          return 1;
            case R16G16_UNORM:       return 2;
            case A16_FLOAT:          return 1;
            case R16G16_FLOAT:       return 2;
            case R16G16B16A16_UNORM: return 3;
            case SRGBA_8888:         return 2;
            case R8_UNORM:           return 0;
        }
        throw new RuntimeException("Unreachable");
    }

    // SkImageInfo.cpp / SkColorTypeIsAlwaysOpaque
    /**
     * Returns true if ColorType always decodes alpha to 1.0, making the pixel
     * fully opaque. If true, ColorType does not reserve bits to encode alpha.
     *
     * @return  true if alpha is always set to 1.0
     */
    public boolean isAlwaysOpaque() {
        return 0 == (getChannelFlags() & ColorChannelFlag.ALPHA._value);
    }

    // SkImageInfoPriv.h / SkColorTypeIsAlphaOnly
    public boolean isAlphaOnly() {
        return ColorChannelFlag.ALPHA._value == getChannelFlags();
    }

    // SkImageInfo.cpp / SkColorTypeValidateAlphaType
    /**
     * <p>Returns a valid ColorAlphaType for colorType. If there is more than one valid canonical
     * ColorAlphaType, set to alphaType, if valid.</p>
     *
     * <p>Returns null only if alphaType is {@link ColorAlphaType#UNKNOWN}, color type is not
     * {@link #UNKNOWN}, and ColorType is not always opaque.</p>
     *
     * @return  ColorAlphaType if can be associated with colorType
     */
    @Nullable
    public ColorAlphaType validateAlphaType(ColorAlphaType alphaType) {
        switch (this) {
            case UNKNOWN:
                alphaType = ColorAlphaType.UNKNOWN;
                break;
            case ALPHA_8:         // fall-through
            case A16_UNORM:       // fall-through
            case A16_FLOAT:
                if (ColorAlphaType.UNPREMUL == alphaType) {
                    alphaType = ColorAlphaType.PREMUL;
                }
                // fall-through
            case ARGB_4444:
            case RGBA_8888:
            case SRGBA_8888:
            case BGRA_8888:
            case RGBA_1010102:
            case BGRA_1010102:
            case RGBA_10X6:
            case RGBA_F16NORM:
            case RGBA_F16:
            case RGBA_F32:
            case BGRA_10101010_XR:
            case R16G16B16A16_UNORM:
                if (ColorAlphaType.UNKNOWN == alphaType) {
                    return null;
                }
                break;
            case GRAY_8:
            case R8G8_UNORM:
            case R16G16_UNORM:
            case R16G16_FLOAT:
            case RGB_565:
            case RGB_888X:
            case RGB_101010X:
            case BGR_101010X:
            case BGR_101010X_XR:
            case RGB_F16F16F16X:
            case R8_UNORM:
                alphaType = ColorAlphaType.OPAQUE;
                break;
        }
        return alphaType;
    }

    // SkImageInfoPriv.h / SkColorTypeChannelFlags
    public int getChannelFlags() {
        switch (this) {
            case UNKNOWN:            return 0;
            case ALPHA_8:            return ColorChannelFlag.ALPHA._value;
            case RGB_565:            return ColorChannelFlag.RGB._value;
            case ARGB_4444:          return ColorChannelFlag.RGBA._value;
            case RGBA_8888:          return ColorChannelFlag.RGBA._value;
            case RGB_888X:           return ColorChannelFlag.RGB._value;
            case BGRA_8888:          return ColorChannelFlag.RGBA._value;
            case RGBA_1010102:       return ColorChannelFlag.RGBA._value;
            case RGB_101010X:        return ColorChannelFlag.RGB._value;
            case BGRA_1010102:       return ColorChannelFlag.RGBA._value;
            case BGR_101010X:        return ColorChannelFlag.RGB._value;
            case BGR_101010X_XR:     return ColorChannelFlag.RGB._value;
            case BGRA_10101010_XR:   return ColorChannelFlag.RGBA._value;
            case RGBA_10X6:          return ColorChannelFlag.RGBA._value;
            case GRAY_8:             return ColorChannelFlag.GRAY._value;
            case RGBA_F16NORM:       return ColorChannelFlag.RGBA._value;
            case RGBA_F16:           return ColorChannelFlag.RGBA._value;
            case RGB_F16F16F16X:     return ColorChannelFlag.RGB._value;
            case RGBA_F32:           return ColorChannelFlag.RGBA._value;
            case R8G8_UNORM:         return ColorChannelFlag.RG._value;
            case A16_UNORM:          return ColorChannelFlag.ALPHA._value;
            case R16G16_UNORM:       return ColorChannelFlag.RG._value;
            case A16_FLOAT:          return ColorChannelFlag.ALPHA._value;
            case R16G16_FLOAT:       return ColorChannelFlag.RG._value;
            case R16G16B16A16_UNORM: return ColorChannelFlag.RGBA._value;
            case SRGBA_8888:         return ColorChannelFlag.RGBA._value;
            case R8_UNORM:           return ColorChannelFlag.RED._value;
        }
        throw new RuntimeException("Unreachable");
    }

    // SkImageInfoPriv.h / SkColorTypeNumChannels
    public int getNumChannels() {
        int flags = getChannelFlags();
        if (ColorChannelFlag.RED._value == flags)        return 1;
        if (ColorChannelFlag.ALPHA._value == flags)      return 1;
        if (ColorChannelFlag.GRAY._value == flags)       return 1;
        if (ColorChannelFlag.GRAY_ALPHA._value == flags) return 2;
        if (ColorChannelFlag.RG._value == flags)         return 2;
        if (ColorChannelFlag.RGB._value == flags)        return 3;
        if (ColorChannelFlag.RGBA._value == flags)       return 4;
        if (0 == flags)                                  return 0;
        throw new RuntimeException("Unexpected color channel flags");
    }

    public long computeOffset(int x, int y, long rowBytes) {
        if (this == UNKNOWN)
            return 0;
        return y * rowBytes + (x << getShiftPerPixel());
    }

    public float getR(byte color) {
        switch (this) {
            case GRAY_8:
                return Byte.toUnsignedInt(color) / 255f;
            default:
                throw new IllegalArgumentException("getR(byte) is not supported on ColorType." + this);
        }
    }

    public float getR(short color) {
        switch (this) {
            case RGB_565:
                return ((color >> 11) & 0b11111) / 31f;
            case ARGB_4444:
                return ((color >> 8) & 0xF) / 15f;
            default:
                throw new IllegalArgumentException("getR(short) is not supported on ColorType." + this);
        }
    }

    public float getR(int color) {
        switch (this) {
            case RGBA_8888:
            case SRGBA_8888:
                return ((color >> 24) & 0xFF) / 255f;
            case RGB_888X:
                return ((color >> 24) & 0xFF) / 255f;
            case BGRA_8888:
                return ((color >> 8) & 0xFF) / 255f;
            case RGBA_1010102:
                return ((color >> 22) & 0b1111111111) / 1023f;
            case RGB_101010X:
                return ((color >> 22) & 0b1111111111) / 1023f;
            case BGRA_1010102:
                return ((color >> 2) & 0b1111111111) / 1023f;
            case BGR_101010X:
                return ((color >> 2) & 0b1111111111) / 1023f;
            default:
                throw new IllegalArgumentException("getR(int) is not supported on ColorType." + this);
        }
    }

    public float getG(byte color) {
        switch (this) {
            case GRAY_8:
                return Byte.toUnsignedInt(color) / 255f;
            default:
                throw new IllegalArgumentException("getG(byte) is not supported on ColorType." + this);
        }
    }

    public float getG(short color) {
        switch (this) {
            case RGB_565:
                return ((color >> 5) & 0b111111) / 63f;
            case ARGB_4444:
                return ((color >> 4) & 0xF) / 15f;
            default:
                throw new IllegalArgumentException("getG(short) is not supported on ColorType." + this);
        }
    }

    public float getG(int color) {
        switch (this) {
            case RGBA_8888:
            case SRGBA_8888:
                return ((color >> 16) & 0xFF)  / 255f;
            case RGB_888X:
                return ((color >> 16) & 0xFF) / 255f;
            case BGRA_8888:
                return ((color >> 16) & 0xFF) / 255f;
            case RGBA_1010102:
                return ((color >> 12) & 0b1111111111) / 1023f;
            case RGB_101010X:
                return ((color >> 12) & 0b1111111111) / 1023f;
            case BGRA_1010102:
                return ((color >> 12) & 0b1111111111) / 1023f;
            case BGR_101010X:
                return ((color >> 12) & 0b1111111111) / 1023f;
            default:
                throw new IllegalArgumentException("getG(int) is not supported on ColorType." + this);
        }
    }

    public float getB(byte color) {
        switch (this) {
            case GRAY_8:
                return Byte.toUnsignedInt(color) / 255f;
            default:
                throw new IllegalArgumentException("getB(byte) is not supported on ColorType." + this);
        }
    }

    public float getB(short color) {
        switch (this) {
            case RGB_565:
                return (color & 0b11111) / 31f;
            case ARGB_4444:
                return (color & 0xF) / 15f;
            default:
                throw new IllegalArgumentException("getB(short) is not supported on ColorType." + this);
        }
    }

    public float getB(int color) {
        switch (this) {
            case RGBA_8888:
            case SRGBA_8888:
                return ((color >> 8) & 0xFF) / 255f;
            case RGB_888X:
                return ((color >> 8) & 0xFF) / 255f;
            case BGRA_8888:
                return ((color >> 24) & 0xFF) / 255f;
            case RGBA_1010102:
                return ((color >> 2) & 0b1111111111) / 1023f;
            case RGB_101010X:
                return ((color >> 2) & 0b1111111111) / 1023f;
            case BGRA_1010102:
                return ((color >> 22) & 0b1111111111) / 1023f;
            case BGR_101010X:
                return ((color >> 22) & 0b1111111111) / 1023f;
            default:
                throw new IllegalArgumentException("getB(int) is not supported on ColorType." + this);
        }
    }

    public float getA(byte color) {
        switch (this) {
            case ALPHA_8:
                return Byte.toUnsignedInt(color) / 255f;
            default:
                throw new IllegalArgumentException("getA(byte) is not supported on ColorType." + this);
        }
    }

    public float getA(short color) {
        switch (this) {
            case ARGB_4444:
                return ((color >> 12) & 0xF) / 15f;
            default:
                throw new IllegalArgumentException("getA(short) is not supported on ColorType." + this);
        }
    }

    public float getA(int color) {
        switch (this) {
            case RGBA_8888:
            case SRGBA_8888:
                return (color & 0xFF) / 255f;
            case BGRA_8888:
                return (color & 0xFF) / 255f;
            case RGBA_1010102:
                return (color & 0b11) / 3f;
            case BGRA_1010102:
                return (color & 0b11) / 3f;
            default:
                throw new IllegalArgumentException("getA(int) is not supported on ColorType." + this);
        }
    }

   static { Library.staticLoad(); }

   @ApiStatus.Internal public static final ColorType[] _values = values();
   @ApiStatus.Internal public static native int[] _nGetValues();
}