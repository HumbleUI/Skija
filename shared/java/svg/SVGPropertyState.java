package io.github.humbleui.skija.svg;

import org.jetbrains.annotations.ApiStatus;

public enum SVGPropertyState {
    UNSPECIFIED,
    INHERIT,
    VALUE;

    @ApiStatus.Internal public static final SVGPropertyState[] _values = values();
}
