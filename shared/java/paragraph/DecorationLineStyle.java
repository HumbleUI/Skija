package io.github.humbleui.skija.paragraph;

import org.jetbrains.annotations.*;

// TextStyle.h - TextDecorationStyle
public enum DecorationLineStyle {
    SOLID,
    DOUBLE,
    DOTTED,
    DASHED,
    WAVY;

    @ApiStatus.Internal public static final DecorationLineStyle[] _values = values();
}
