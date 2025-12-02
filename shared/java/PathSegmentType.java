package io.github.humbleui.skija;

import org.jetbrains.annotations.*;

// SkPathTypes.h - SkPathSegmentMask
public enum PathSegmentType {
    LINE(1 << 0),
    QUAD(1 << 1),
    CONIC(1 << 2),
    CUBIC(1 << 3);

    @ApiStatus.Internal public static final PathSegmentType[] _values = values();

    @ApiStatus.Internal public final int _value;

    PathSegmentType(int value) {
        this._value = value;
    }
}
