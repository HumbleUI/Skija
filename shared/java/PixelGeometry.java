package io.github.humbleui.skija;

import org.jetbrains.annotations.*;

// SkSurfaceProps.h - SkPixelGeometry
public enum PixelGeometry {
    UNKNOWN,
    RGB_H,
    BGR_H,
    RGB_V,
    BGR_V;

    @ApiStatus.Internal public static final PixelGeometry[] _values = values();
}