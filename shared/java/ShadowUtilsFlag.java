package io.github.humbleui.skija;

import org.jetbrains.annotations.*;

public enum ShadowUtilsFlag {
    /**
     * The occluding object is not opaque. Knowing that the occluder is opaque allows
     * us to cull shadow geometry behind it and improve performance.
     */
    TRANSPARENT_OCCLUDER(0x1),

    /**
     * Don't try to use analytic shadows.
     */
    GEOMETRIC_ONLY(0x2),

    /**
     * Light position represents a direction, light radius is blur radius at elevation 1
     */
    DIRECTIONAL_LIGHT(0x4),

    /**
     * Concave paths will only use blur to generate the shadow
     */
    CONCAVE_BLUR_ONLY(0x8);

    @ApiStatus.Internal public final int _value;

    ShadowUtilsFlag(int value) {
        this._value = value;
    }

    @ApiStatus.Internal
    public static int _collect(ShadowUtilsFlag... flags) {
        int value = 0;
        for (ShadowUtilsFlag flag: flags)
            value |= flag._value;
        return value;
    }
}
