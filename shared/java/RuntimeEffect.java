package io.github.humbleui.skija;

import org.jetbrains.annotations.*;
import io.github.humbleui.skija.impl.*;

/**
 * <p>RuntimeEffect supports creating custom {@link Shader} and {@link ColorFilter}
 * objects using Skia's SkSL shading language.</p>
 *
 * <p>NOTE: This API is experimental and subject to change.</p>
 */
public class RuntimeEffect extends RefCnt {
    static { Library.staticLoad(); }

    @NotNull @Contract("_ -> new")
    public static RuntimeEffect makeForColorFilter(@NotNull String sksl) {
        return makeForColorFilter(sksl, RuntimeEffectOptions.DEFAULT);
    }

    /**
     * <p>MakeForColorFilter and MakeForShader verify that the SkSL code is valid for those stages of
     * the Skia pipeline. In all of the signatures described below, color parameters and return
     * values are flexible. They are listed as being 'vec4', but they can also be 'half4' or
     * 'float4'. ('vec4' is an alias for 'float4').</p>
     *
     * <p>Color filter SkSL requires an entry point that looks like:</p>
     *
     * <pre>{@code
     * vec4 main(vec4 inColor) { ... }
     * }</pre>
     *
     * @param sksl     shader code
     * @param options  options
     * @return         new RuntimeEffect
     * @see <a href="https://fiddle.skia.org/c/@runtimeeffect_colorfilter_grid">https://fiddle.skia.org/c/@runtimeeffect_colorfilter_grid</a>
     */
    @NotNull @Contract("_, _ -> new")
    public static RuntimeEffect makeForColorFilter(@NotNull String sksl, @NotNull RuntimeEffectOptions options) {
        Stats.onNativeCall();
        return new RuntimeEffect(_nMakeForColorFilter(sksl, options._forceUnoptimized, options._name));
    }

    @NotNull @Contract("_ -> new")
    public static RuntimeEffect makeForShader(@NotNull String sksl) {
        return makeForShader(sksl, RuntimeEffectOptions.DEFAULT);
    }

    /**
     * <p>Shader SkSL requires an entry point that looks like:</p>
     *
     * <pre>{@code
     * vec4 main(vec2 inCoords) { ... }
     * }</pre>
     *
     * <p>The color that is returned should be premultiplied.</p>
     *
     * @param sksl     shader code
     * @param options  options
     * @return         new RuntimeEffect
     */
    @NotNull @Contract("_, _ -> new")
    public static RuntimeEffect makeForShader(@NotNull String sksl, @NotNull RuntimeEffectOptions options) {
        Stats.onNativeCall();
        return new RuntimeEffect(_nMakeForShader(sksl, options._forceUnoptimized, options._name));
    }

    @NotNull @Contract("_ -> new")
    public static RuntimeEffect makeForBlender(@NotNull String sksl) {
        return makeForBlender(sksl, RuntimeEffectOptions.DEFAULT);
    }

    /**
     * <p>Blend SkSL requires an entry point that looks like:</p>
     *
     * <pre>{@code
     * vec4 main(vec4 srcColor, vec4 dstColor) { ... }
     * }</pre>
     *
     * @param sksl     shader code
     * @param options  options
     * @return         new RuntimeEffect
     */
    @NotNull @Contract("_, _ -> new")
    public static RuntimeEffect makeForBlender(@NotNull String sksl, @NotNull RuntimeEffectOptions options) {
        Stats.onNativeCall();
        return new RuntimeEffect(_nMakeForBlender(sksl, options._forceUnoptimized, options._name));
    }

    @NotNull @Contract("_, _ -> new")
    public <T extends RuntimeEffectChild> ColorFilter makeColorFilter(@Nullable Data uniforms, @Nullable T[] children) {
        Stats.onNativeCall();
        int childCount = children == null ? 0 : children.length;
        long[] childrenPtrs = new long[childCount];
        for (int i = 0; i < childCount; i++)
            childrenPtrs[i] = Native.getPtr(children[i]);
        return new ColorFilter(_nMakeColorFilter(_ptr, Native.getPtr(uniforms), childrenPtrs));
    }

    @NotNull @Contract("_ -> new")
    public ColorFilter makeColorFilter(@Nullable Data uniforms) {
        return makeColorFilter(uniforms, (RuntimeEffectChild[]) null);
    }

    @NotNull @Contract("_, _, _ -> new")
    public <T extends RuntimeEffectChild> Shader makeShader(@Nullable Data uniforms, @Nullable T[] children, @Nullable Matrix33 localMatrix) {
        Stats.onNativeCall();
        int childCount = children == null ? 0 : children.length;
        long[] childrenPtrs = new long[childCount];
        for (int i = 0; i < childCount; i++) {
            childrenPtrs[i] = Native.getPtr(children[i]);
        }
        float[] matrix = localMatrix == null ? null : localMatrix._mat;
        return new Shader(_nMakeShader(_ptr, Native.getPtr(uniforms), childrenPtrs, matrix));
    }

    @NotNull @Contract("_, _ -> new")
    public <T extends RuntimeEffectChild> Shader makeShader(@Nullable Data uniforms, @Nullable T[] children) {
        return makeShader(uniforms, children, null);
    }

    @NotNull @Contract("_, _ -> new")
    public <T extends RuntimeEffectChild> Blender makeBlender(@Nullable Data uniforms, @Nullable T[] children) {
        Stats.onNativeCall();
        int childCount = children == null ? 0 : children.length;
        long[] childrenPtrs = new long[childCount];
        for (int i = 0; i < childCount; i++) {
            childrenPtrs[i] = Native.getPtr(children[i]);
        }
        return new Blender(_nMakeBlender(_ptr, Native.getPtr(uniforms), childrenPtrs));
    }

    @NotNull @Contract("_ -> new")
    public Blender makeBlender(@Nullable Data uniforms) {
        return makeBlender(uniforms, (RuntimeEffectChild[]) null);
    }

    /**
     * Returns the SkSL source of the runtime effect.
     *
     * @return  the SkSL source code
     */
    @NotNull
    public String getSource() {
        try {
            Stats.onNativeCall();
            return _nGetSource(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Returns the combined size of all 'uniform' variables.
     * When calling makeColorFilter, makeShader, or makeBlender, provide a Data of this size.
     *
     * @return  size in bytes of all uniform variables combined
     */
    public int getUniformSize() {
        try {
            Stats.onNativeCall();
            return _nGetUniformSize(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Returns true if this runtime effect can be used as a shader.
     *
     * @return  true if allowShader flag is set
     */
    public boolean isShaderAllowed() {
        try {
            Stats.onNativeCall();
            return _nIsShaderAllowed(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Returns true if this runtime effect can be used as a color filter.
     *
     * @return  true if allowColorFilter flag is set
     */
    public boolean isColorFilterAllowed() {
        try {
            Stats.onNativeCall();
            return _nIsColorFilterAllowed(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Returns true if this runtime effect can be used as a blender.
     *
     * @return  true if allowBlender flag is set
     */
    public boolean isBlenderAllowed() {
        try {
            Stats.onNativeCall();
            return _nIsBlenderAllowed(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Returns information about all uniform variables in the runtime effect.
     *
     * @return  array of RuntimeEffectUniformInfo objects
     */
    @NotNull
    public RuntimeEffectUniformInfo[] getUniforms() {
        try {
            Stats.onNativeCall();
            return _nGetUniforms(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Returns information about a specific uniform variable by name.
     *
     * @param name  the name of the uniform to find
     * @return      RuntimeEffectUniformInfo for the uniform, or null if not found
     */
    @Nullable
    public RuntimeEffectUniformInfo getUniform(@NotNull String name) {
        try {
            Stats.onNativeCall();
            return _nGetUniform(_ptr, name);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Returns information about all child effects (shaders, color filters, blenders) in the runtime effect.
     *
     * @return  array of RuntimeEffectChildInfo objects
     */
    @NotNull
    public RuntimeEffectChildInfo[] getChildren() {
        try {
            Stats.onNativeCall();
            return _nGetChildren(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Returns information about a specific child effect by name.
     *
     * @param name  the name of the child to find
     * @return      RuntimeEffectChildInfo for the child, or null if not found
     */
    @Nullable
    public RuntimeEffectChildInfo getChild(@NotNull String name) {
        try {
            Stats.onNativeCall();
            return _nGetChild(_ptr, name);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @ApiStatus.Internal
    public RuntimeEffect(long ptr) {
        super(ptr);
    }

    @ApiStatus.Internal public static native long _nMakeForColorFilter(String sksl, boolean forceUnoptimized, String name);
    @ApiStatus.Internal public static native long _nMakeForShader(String sksl, boolean forceUnoptimized, String name);
    @ApiStatus.Internal public static native long _nMakeForBlender(String sksl, boolean forceUnoptimized, String name);
    @ApiStatus.Internal public static native long _nMakeShader(long runtimeEffectPtr, long uniformPtr, long[] childrenPtrs, float[] localMatrix);
    @ApiStatus.Internal public static native long _nMakeColorFilter(long runtimeEffectPtr, long uniformPtr, long[] childrenPtrs);
    @ApiStatus.Internal public static native long _nMakeBlender(long runtimeEffectPtr, long uniformPtr, long[] childrenPtrs);
    @ApiStatus.Internal public static native String _nGetSource(long ptr);
    @ApiStatus.Internal public static native int _nGetUniformSize(long ptr);
    @ApiStatus.Internal public static native boolean _nIsShaderAllowed(long ptr);
    @ApiStatus.Internal public static native boolean _nIsColorFilterAllowed(long ptr);
    @ApiStatus.Internal public static native boolean _nIsBlenderAllowed(long ptr);
    @ApiStatus.Internal public static native RuntimeEffectUniformInfo[] _nGetUniforms(long ptr);
    @ApiStatus.Internal public static native RuntimeEffectUniformInfo _nGetUniform(long ptr, String name);
    @ApiStatus.Internal public static native RuntimeEffectChildInfo[] _nGetChildren(long ptr);
    @ApiStatus.Internal public static native RuntimeEffectChildInfo _nGetChild(long ptr, String name);
}