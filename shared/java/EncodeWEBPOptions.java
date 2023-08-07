package io.github.humbleui.skija;

import org.jetbrains.annotations.*;

import lombok.*;

@lombok.Data @With
public class EncodeWEBPOptions {
    public enum CompressionMode {
        LOSSY,
        LOSSLESS
    }
    
    public static final EncodeWEBPOptions DEFAULT = new EncodeWEBPOptions(CompressionMode.LOSSY, 100f);

    @ApiStatus.Internal 
    public final CompressionMode _compressionMode;

    @ApiStatus.Internal
    public final float _quality;

    @ApiStatus.Internal
    public EncodeWEBPOptions(CompressionMode compression, float quality) {
        _compressionMode = compression;
        _quality = quality;
    }
}
