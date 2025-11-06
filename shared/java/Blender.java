package io.github.humbleui.skija;

import org.jetbrains.annotations.*;
import io.github.humbleui.skija.impl.*;

/**
 * SkBlender represents a custom blend function in the Skia pipeline.
 * A blender combines a source color (the result of our paint) and destination
 * color (from the canvas) into a final color.
 */
public class Blender extends RuntimeEffectChild {
    static { Library.staticLoad(); }

    /**
     * Create a blender that implements the specified BlendMode.
     *
     * @param mode  The blend mode to use
     * @return      A new Blender that implements the specified blend mode
     */
    @NotNull  @Contract("_ -> new")
    public static Blender makeWithMode(@NotNull BlendMode mode) {
        Stats.onNativeCall();
        return new Blender(_nMakeWithMode(mode.ordinal()));
    }

    /**
     * Create a blender that implements the following arithmetic formula:
     * <pre>
     * k1 * src * dst + k2 * src + k3 * dst + k4
     * </pre>
     *
     * @param k1              First coefficient (src * dst factor)
     * @param k2              Second coefficient (src factor)
     * @param k3              Third coefficient (dst factor)
     * @param k4              Fourth coefficient (constant)
     * @param enforcePremul   If true, the RGB channels will be clamped to the calculated alpha
     * @return                A new Blender that implements arithmetic blending
     */
    @NotNull @Contract("_, _, _, _, _ -> new")
    public static Blender makeArithmetic(float k1, float k2, float k3, float k4, boolean enforcePremul) {
        Stats.onNativeCall();
        return new Blender(_nMakeArithmetic(k1, k2, k3, k4, enforcePremul));
    }

    @NotNull @Contract("_, _, _ -> new")
    public static <T extends RuntimeEffectChild> Blender makeRuntime(@NotNull RuntimeEffect effect, @Nullable Data uniforms, @Nullable T[] children) {
        Stats.onNativeCall();
        int childCount = children == null ? 0 : children.length;
        long[] childrenPtrs = new long[childCount];
        for (int i = 0; i < childCount; i++) {
            childrenPtrs[i] = Native.getPtr(children[i]);
        }
        return new Blender(_nMakeRuntime(Native.getPtr(effect), Native.getPtr(uniforms), childrenPtrs));
    }

    @ApiStatus.Internal
    public Blender(long ptr) {
        super(ptr);
    }

    public static native long _nMakeWithMode(int blendMode);
    public static native long _nMakeArithmetic(float k1, float k2, float k3, float k4, boolean enforcePremul);
    public static native long _nMakeRuntime(long effectPtr, long uniformsPtr, long[] childrenPtrs);
}
