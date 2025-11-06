package io.github.humbleui.skija;

import org.jetbrains.annotations.*;

public enum RuntimeEffectChildType {
    SHADER,
    COLOR_FILTER,
    BLENDER;

    @ApiStatus.Internal public static final RuntimeEffectChildType[] _values = values();
}
