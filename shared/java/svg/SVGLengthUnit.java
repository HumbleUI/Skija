package io.github.humbleui.skija.svg;

import org.jetbrains.annotations.*;

// SkSVGTypes.h - SkSVGLength::Unit
public enum SVGLengthUnit {
    UNKNOWN,
    NUMBER,
    PERCENTAGE,
    EMS,
    EXS,
    PX,
    CM,
    MM,
    IN,
    PT,
    PC;

    @ApiStatus.Internal public static final SVGLengthUnit[] _values = values();
}
