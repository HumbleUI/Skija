package io.github.humbleui.skija;

import org.jetbrains.annotations.*;

import lombok.*;

@lombok.Data @With
public class EncodeWEBPOptions {
    public static final EncodeWEBPOptions DEFAULT = new EncodeWEBPOptions(EncodeWEBPCompressionMode.LOSSY, 100f);

    @ApiStatus.Internal 
    public final EncodeWEBPCompressionMode _compressionMode;

    @ApiStatus.Internal
    public final float _quality;

    @ApiStatus.Internal
    public EncodeWEBPOptions(EncodeWEBPCompressionMode compression, float quality) {
        _compressionMode = compression;
        _quality = quality;
    }
}
