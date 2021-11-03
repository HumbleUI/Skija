package io.github.humbleui.skija;

import org.jetbrains.annotations.*;

public enum FontSlant {
    UPRIGHT,
    ITALIC,
    OBLIQUE;

    @ApiStatus.Internal public static final FontSlant[] _values = values();
}
