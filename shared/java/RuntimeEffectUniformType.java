package io.github.humbleui.skija;

import org.jetbrains.annotations.*;

public enum RuntimeEffectUniformType {
    FLOAT,
    FLOAT2,
    FLOAT3,
    FLOAT4,
    FLOAT2X2,
    FLOAT3X3,
    FLOAT4X4,
    INT,
    INT2,
    INT3,
    INT4;

    @ApiStatus.Internal public static final RuntimeEffectUniformType[] _values = values();
}
