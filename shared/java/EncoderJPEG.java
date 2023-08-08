package io.github.humbleui.skija;

import io.github.humbleui.skija.impl.*;
import org.jetbrains.annotations.*;

public class EncoderJPEG {
    /**
     * Encode the provided image and return the resulting bytes.
     *
     * @param image  image to encode
     * @return       nullptr if the pixels could not be read or encoding otherwise fails.
     */
    @Nullable
    public static Data encode(@NotNull Image image) {
        return encode(null, image, EncodeJPEGOptions.DEFAULT);
    }

    /**
     * Encode the provided image and return the resulting bytes.
     *
     * @param image  image to encode
     * @param opts   may be used to control the encoding behavior
     * @return       nullptr if the pixels could not be read or encoding otherwise fails.
     */
    @Nullable
    public static Data encode(@NotNull Image image, @NotNull EncodeJPEGOptions opts) {
        return encode(null, image, opts);
    }

    /**
     * Encode the provided image and return the resulting Data.
     *
     * @param ctx    If the image was created as a texture-backed image on a GPU context, 
     *               that ctx must be provided so the pixels can be read before being encoded.
     *               For raster-backed images, ctx can be null.
     * @param image  image to encode
     * @param opts   may be used to control the encoding behavior
     * @return       nullptr if the pixels could not be read or encoding otherwise fails.
     */
    @Nullable
    public static Data encode(@Nullable DirectContext ctx, @NotNull Image image, @NotNull EncodeJPEGOptions opts) {
        try {
            assert image != null : "Can’t EncoderJPEG::encode with image == null";
            assert opts != null : "Can’t EncoderJPEG::encode with opts == null";
            Stats.onNativeCall();
            long ptr = _nEncode(Native.getPtr(ctx), Native.getPtr(image), opts._quality, opts._downsampleMode.ordinal(), opts._alphaMode.ordinal());
            return ptr == 0 ? null : new Data(ptr);
        } finally {
            ReferenceUtil.reachabilityFence(ctx);
            ReferenceUtil.reachabilityFence(image);
        }
    }

    @ApiStatus.Internal public static native long _nEncode(long ctxPtr, long imagePtr, int quality, int downsampleMode, int alphaMode);
}
