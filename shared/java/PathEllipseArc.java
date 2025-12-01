package io.github.humbleui.skija;

import org.jetbrains.annotations.*;

// SkPathBuilder.h - SkPathBuilder::ArcSize
public enum PathEllipseArc {
    /** Smaller of arc pair. */
    SMALLER,

    /** Larger of arc pair. */
    LARGER;

    @ApiStatus.Internal public static final PathEllipseArc[] _values = values();
}
