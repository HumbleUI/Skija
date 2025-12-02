package io.github.humbleui.skija.paragraph;

import org.jetbrains.annotations.*;

// TextStyle.h - StyleType
public enum TextStyleAttribute {
    NONE,
    ALL_ATTRIBUTES,
    FONT,
    FOREGROUND,
    BACKGROUND,
    SHADOW,
    DECORATIONS,
    LETTER_SPACING,
    WORD_SPACING;

    @ApiStatus.Internal public static final TextStyleAttribute[] _values = values();
}
