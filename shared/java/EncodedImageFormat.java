package io.github.humbleui.skija;

import org.jetbrains.annotations.*;

/**
 * @deprecated - use {@link EncoderJPEG}, {@link EncoderPNG} or {@link EncoderWEBP} directly
 */
@Deprecated
public enum EncodedImageFormat {
    BMP,
    GIF,
    ICO,
    JPEG,
    PNG,
    WBMP,
    WEBP,
    PKM,
    KTX,
    ASTC,
    DNG,
    HEIF;

    @ApiStatus.Internal public static final EncodedImageFormat[] _values = values();
}