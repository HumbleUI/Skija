package io.github.humbleui.skija;

import org.jetbrains.annotations.*;

/**
 * <p>Blends are operators that take in two colors (source, destination) and return a new color.
 * Many of these operate the same on all 4 components: red, green, blue, alpha. For these,
 * we just document what happens to one component, rather than naming each one separately.</p>
 *
 * <p>Different color types have different representations for color components:</p>
 * <ul>
 *   <li>8-bit: 0..255</li>
 *   <li>6-bit: 0..63</li>
 *   <li>5-bit: 0..31</li>
 *   <li>4-bit: 0..15</li>
 *   <li>floats: 0...1</li>
 * </ul>
 *
 * <p>The documentation is expressed as if the component values are always 0..1 (floats).</p>
 *
 * <p>For brevity, the documentation uses the following abbreviations:</p>
 * <ul>
 *   <li>s  : source</li>
 *   <li>d  : destination</li>
 *   <li>sa : source alpha</li>
 *   <li>da : destination alpha</li>
 * </ul>
 *
 * <p>Results are abbreviated:</p>
 * <ul>
 *   <li>r  : if all 4 components are computed in the same manner</li>
 *   <li>ra : result alpha component</li>
 *   <li>rc : result "color": red, green, blue components</li>
 * </ul>
 */
public enum BlendMode {
    /** r = 0 */
    CLEAR,

    /** r = s */
    SRC,

    /** r = d */
    DST,

    /** r = s + (1-sa)*d */
    SRC_OVER,

    /** r = d + (1-da)*s */
    DST_OVER,

    /** r = s * da */
    SRC_IN,

    /** r = d * sa */
    DST_IN,

    /** r = s * (1-da) */
    SRC_OUT,

    /** r = d * (1-sa) */
    DST_OUT,

    /** r = s*da + d*(1-sa) */
    SRC_ATOP,

    /** r = d*sa + s*(1-da) */
    DST_ATOP,

    /** r = s*(1-da) + d*(1-sa) */
    XOR,

    /** r = min(s + d, 1) */
    PLUS,

    /** r = s*d */
    MODULATE,

    /** r = s + d - s*d */
    SCREEN,

    /** Multiply or screen, depending on destination. */
    OVERLAY,

    /** rc = s + d - max(s*da, d*sa), ra = {@link #SRC_OVER} */
    DARKEN,

    /** rc = s + d - min(s*da, d*sa), ra = {@link #SRC_OVER} */
    LIGHTEN,

    /** Brighten destination to reflect source. */
    COLOR_DODGE,

    /** Darken destination to reflect source. */
    COLOR_BURN,

    /** Multiply or screen, depending on source. */
    HARD_LIGHT,

    /** Lighten or darken, depending on source. */
    SOFT_LIGHT,

    /** rc = s + d - 2*(min(s*da, d*sa)), ra = {@link #SRC_OVER} */
    DIFFERENCE,

    /** rc = s + d - two(s*d), ra = {@link #SRC_OVER} */
    EXCLUSION,

    /** r = s*(1-da) + d*(1-sa) + s*d */
    MULTIPLY,

    /** Hue of source with saturation and luminosity of destination. */
    HUE,

    /** Saturation of source with hue and luminosity of destination. */
    SATURATION,

    /** Hue and saturation of source with luminosity of destination. */
    COLOR,

    /** Luminosity of source with hue and saturation of destination. */
    LUMINOSITY;

    /** last porter duff blend mode */
    public static final BlendMode LAST_COEFF_MODE = SCREEN;

    /** last blend mode operating separately on components */
    public static final BlendMode LAST_SEPARABLE_MODE = MULTIPLY;

    @ApiStatus.Internal public static final BlendMode[] _values = values();
}
