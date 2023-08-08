package io.github.humbleui.skija;

import org.jetbrains.annotations.*;

import lombok.*;

@lombok.Data @With
public class EncodeJPEGOptions {
    public static final EncodeJPEGOptions DEFAULT = new EncodeJPEGOptions(100, EncodeJPEGDownsampleMode.DS_420, EncodeJPEGAlphaMode.IGNORE);

    @ApiStatus.Internal
    public final int _quality;

    @ApiStatus.Internal 
    public final EncodeJPEGDownsampleMode _downsampleMode;

    @ApiStatus.Internal 
    public final EncodeJPEGAlphaMode _alphaMode;
}
