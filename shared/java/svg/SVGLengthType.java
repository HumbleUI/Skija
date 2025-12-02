package io.github.humbleui.skija.svg;

import org.jetbrains.annotations.*;

// SkSVGRenderContext.h - SkSVGLengthContext::LengthType
public enum SVGLengthType {
    HORIZONTAL,
    VERTICAL,
    OTHER;

    @ApiStatus.Internal public static final SVGLengthType[] _values = values();
}
