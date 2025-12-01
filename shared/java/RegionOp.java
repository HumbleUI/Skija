package io.github.humbleui.skija;

import org.jetbrains.annotations.*;

// SkRegion.h - SkRegion::Op
public enum RegionOp {
    DIFFERENCE,
    INTERSECT,
    UNION,
    XOR,
    REVERSE_DIFFERENCE,
    REPLACE;

    @ApiStatus.Internal public static final RegionOp[] _values = values();
}
