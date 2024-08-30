package io.github.humbleui.skija.svg;

import org.jetbrains.annotations.ApiStatus;

public enum SVGColorType {
    CURRENT_COLOR,
    COLOR,
    ICCCOLOR;

    @ApiStatus.Internal public static final SVGColorType[] _values = values();
}
