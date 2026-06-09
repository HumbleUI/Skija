package io.github.humbleui.skija;

import java.util.Objects;

/**
 * <p>Represents the struct {@code GrGLTextureInfo} in GrGLTypes.h</p>
 *
 * <p>Type for interacting with GL texture created externally to Skia.
 * The {@code format} here should be a sized, internal format for the texture.
 * We will try to use the sized format if the GL Context supports it, otherwise
 * we will internally fall back to using the base internal formats.</p>
 */
public class GLTextureInfo {
    private final int target;
    private final int id;
    private final int format;
    private final boolean isProtected;

    public GLTextureInfo(int target, int id, int format) {
        this(target, id, format, false);
    }

    public GLTextureInfo(int target, int id, int format, boolean isProtected) {
        this.target = target;
        this.id = id;
        this.format = format;
        this.isProtected = isProtected;
    }

    public int getTarget() {
        return target;
    }

    public int getId() {
        return id;
    }

    public int getFormat() {
        return format;
    }

    public boolean isProtected() {
        return isProtected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GLTextureInfo that = (GLTextureInfo) o;
        return target == that.target && id == that.id && format == that.format && isProtected == that.isProtected;
    }

    @Override
    public int hashCode() {
        return Objects.hash(target, id, format, isProtected);
    }
}
