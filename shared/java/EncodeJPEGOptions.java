package io.github.humbleui.skija;

import org.jetbrains.annotations.*;

import lombok.*;

@lombok.Data @With
public class EncodeJPEGOptions {
    public enum DownsampleMode {
        /**
         * Reduction by a factor of two in both the horizontal and vertical directions.
         */
        DS_420,

        /**
         * Reduction by a factor of two in the horizontal direction.
         */
        DS_422,

        /**
         * No downsampling.
         */
        DS_444
    }

    public enum AlphaMode {
        IGNORE,
        BLEND_ON_BLACK
    }
    
    public static final EncodeJPEGOptions DEFAULT = new EncodeJPEGOptions(100, DownsampleMode.DS_420, AlphaMode.IGNORE);

    @ApiStatus.Internal
    public final int _quality;

    @ApiStatus.Internal 
    public final DownsampleMode _downsampleMode;

    @ApiStatus.Internal 
    public final AlphaMode _alphaMode;
}
