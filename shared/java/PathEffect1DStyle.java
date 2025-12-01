package io.github.humbleui.skija;

import org.jetbrains.annotations.*;

// Sk1DPathEffect.h - SkPath1DPathEffect::Style
public enum PathEffect1DStyle {
    /** translate the shape to each position */
    TRANSLATE,
    /** rotate the shape about its center */
    ROTATE,
    /** transform each point, and turn lines into curves */
    MORPH;

    @ApiStatus.Internal public static final PathEffect1DStyle[] _values = values();
}
