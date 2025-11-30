package io.github.humbleui.skija;

import org.jetbrains.annotations.*;

public enum EncodeJPEGDownsampleMode {
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
    DS_444;

    @ApiStatus.Internal public static final EncodeJPEGDownsampleMode[] _values = values();
}
