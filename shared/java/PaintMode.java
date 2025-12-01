package io.github.humbleui.skija;

import org.jetbrains.annotations.*;

// SkPaint.h - SkPaint::Style
public enum PaintMode {
    FILL,
    STROKE,
    STROKE_AND_FILL;

    @ApiStatus.Internal public static final PaintMode[] _values = values();
}
