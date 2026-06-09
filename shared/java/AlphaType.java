package io.github.humbleui.skija;

/**
 * <p>Describes how to interpret the alpha component of a pixel. A pixel may
 * be opaque, or alpha, describing multiple levels of transparency.</p>
 *
 * <p>In simple blending, alpha weights the draw color and the destination
 * color to create a new color. If alpha describes a weight from zero to one:</p>
 *
 * <p>new color = draw color * alpha + destination color * (1 - alpha)</p>
 *
 * <p>In practice alpha is encoded in two or more bits, where 1.0 equals all bits set.</p>
 *
 * <p>RGB may have alpha included in each component value; the stored
 * value is the original RGB multiplied by alpha. Premultiplied color
 * components improve performance.</p>
 */
public enum AlphaType {
    /**
     * <p>Unknown or unrepresentable as an AlphaType.</p>
     */
    UNKNOWN,
    /**
     * <p>Pixel is opaque.</p>
     */
    OPAQUE,
    /**
     * <p>Pixel components are premultiplied by alpha.</p>
     */
    PREMUL,
    /**
     * <p>Pixel components are independent of alpha.</p>
     */
    UNPREMUL;

    /**
     * <p>
     * OPAQUE is a hint that the ColorType is opaque, or that all
     * alpha values are set to their 1.0 equivalent. If SkAlphaType is
     * OPAQUE, and ColorType is not opaque, then the result of
     * drawing any pixel with an alpha value less than 1.0 is undefined.
     * </p>
     *
     * @return true if AlphaType equals OPAQUE
     */
    public boolean IsOpaque() {
        return OPAQUE == this;
    }
}
