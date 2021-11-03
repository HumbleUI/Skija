package io.github.humbleui.skija.paragraph;

import org.jetbrains.annotations.*;

public enum BaselineMode {
    ALPHABETIC,
    IDEOGRAPHIC;

    @ApiStatus.Internal public static final BaselineMode[] _values = values();
}