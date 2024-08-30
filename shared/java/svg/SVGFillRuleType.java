package io.github.humbleui.skija.svg;

import org.jetbrains.annotations.ApiStatus;

public enum SVGFillRuleType {
    NON_ZERO,
    EVEN_ODD,
    INHERIT;

    @ApiStatus.Internal public static final SVGFillRuleType[] _values = values();
}
