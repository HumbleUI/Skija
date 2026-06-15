package io.github.humbleui.skija;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>Represents the struct {@code GrGLTextureInfo} in GrGLTypes.h</p>
 *
 * <p>Type for interacting with GL texture created externally to Skia.
 * The {@code format} here should be a sized, internal format for the texture.
 * We will try to use the sized format if the GL Context supports it, otherwise
 * we will internally fall back to using the base internal formats.</p>
 */
@AllArgsConstructor @Data
public class GLTextureInfo {
    private final int _target;
    private final int _id;
    private final int _format;
    private final boolean _isProtected;

    public GLTextureInfo(int target, int id, int format) {
        this(target, id, format, false);
    }
}
