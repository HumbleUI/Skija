package io.github.humbleui.skija;

import org.jetbrains.annotations.*;

public enum RuntimeEffectUniformFlag {
    /** Uniform is declared as an array. 'count' contains array length. */
    ARRAY(0x1),

    /**
     * Uniform is declared with layout(color). Colors should be supplied as unpremultiplied,
     * extended-range (unclamped) sRGB (ie {@link Color4f}). The uniform will be automatically
     * transformed to unpremultiplied extended-range working-space colors.
     */
    COLOR(0x2),

    /**
     * When used with MeshSpecification, indicates that the uniform is present in the
     * vertex shader. Not used with {@link RuntimeEffect}.
     */
    VERTEX(0x4),

    /**
     * When used with MeshSpecification, indicates that the uniform is present in the
     * fragment shader. Not used with {@link RuntimeEffect}.
     */
    FRAGMENT(0x8),

    /**
     * This flag indicates that the SkSL uniform uses a medium-precision type
     * (i.e., `half` instead of `float`).
     */
    HALF_PRECISION(0x10);

    @ApiStatus.Internal public final int _value;

    RuntimeEffectUniformFlag(int value) {
        this._value = value;
    }
}
