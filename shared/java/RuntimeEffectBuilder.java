package io.github.humbleui.skija;

import lombok.*;
import org.jetbrains.annotations.*;
import io.github.humbleui.skija.impl.*;

/**
 * <p>RuntimeEffectBuilder is a utility to simplify creating {@link Shader}, {@link ColorFilter},
 * and {@link Blender} objects from {@link RuntimeEffect}s.</p>
 *
 * <p>NOTE: Like RuntimeEffect, this API is experimental and subject to change!</p>
 *
 * <p>Given a RuntimeEffect, the RuntimeEffectBuilder manages creating an input data block and
 * provides a convenient way to build shader, color filter, and blender objects with optional
 * uniforms and children.</p>
 *
 * <p>Usage:</p>
 * <pre>{@code
 * RuntimeEffect effect = RuntimeEffect.makeForShader(skslCode);
 * RuntimeEffectBuilder builder = new RuntimeEffectBuilder(effect);
 * builder.setUniform("name", ...);
 * builder.setChild("name", ...);
 * Shader shader = builder.makeShader(null);
 * }</pre>
 */
public class RuntimeEffectBuilder extends Managed {
    static { Library.staticLoad(); }

    @Getter @NotNull @ApiStatus.Internal
    public final RuntimeEffect _effect;

    @Getter @NotNull @ApiStatus.Internal
    public final Data _uniforms;

    /**
     * Creates a RuntimeEffectBuilder for the given effect with zero-initialized uniforms.
     *
     * @param effect  the RuntimeEffect to build with
     */
    @NotNull @Contract("_ -> new")
    public RuntimeEffectBuilder(@NotNull RuntimeEffect effect) {
        super(_nMake(Native.getPtr(effect)), _FinalizerHolder.PTR);
        _effect = effect;
        _uniforms = new Data(_nGetUniforms(_ptr));
    }

    /**
     * Creates a RuntimeEffectBuilder for the given effect with the specified uniforms.
     *
     * @param effect   the RuntimeEffect to build with
     * @param uniforms the uniform data to use
     */
    @NotNull @Contract("_, _ -> new")
    public RuntimeEffectBuilder(@NotNull RuntimeEffect effect, @NotNull Data uniforms) {
        super(_nMakeWithUniforms(Native.getPtr(effect), Native.getPtr(uniforms)), _FinalizerHolder.PTR);
        _effect = effect;
        _uniforms = uniforms;
    }

    /**
     * Copy constructor for RuntimeEffectBuilder.
     *
     * @param other  the RuntimeEffectBuilder to copy from
     */
    @NotNull @Contract("_ -> new")
    public RuntimeEffectBuilder(@NotNull RuntimeEffectBuilder other) {
        super(_nMakeCopy(Native.getPtr(other)), _FinalizerHolder.PTR);
        _effect = other._effect;
        _uniforms = other._uniforms;
        ReferenceUtil.reachabilityFence(other);
    }

    /**
     * Creates a shader from this builder's effect, uniforms, and children.
     *
     * @return  new Shader
     */
    @NotNull @Contract("-> new")
    public Shader makeShader() {
        return makeShader(null);
    }

    /**
     * Creates a shader from this builder's effect, uniforms, and children with a local matrix.
     *
     * @param localMatrix  optional local matrix to apply to the shader, or null
     * @return             new Shader
     */
    @NotNull @Contract("_ -> new")
    public Shader makeShader(@Nullable Matrix33 localMatrix) {
        try {
            Stats.onNativeCall();
            float[] matrix = localMatrix == null ? null : localMatrix._mat;
            return new Shader(_nMakeShader(_ptr, matrix));
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Creates a color filter from this builder's effect, uniforms, and children.
     *
     * @return  new ColorFilter
     */
    @NotNull @Contract("-> new")
    public ColorFilter makeColorFilter() {
        try {
            Stats.onNativeCall();
            return new ColorFilter(_nMakeColorFilter(_ptr));
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Creates a blender from this builder's effect, uniforms, and children.
     *
     * @return  new Blender
     */
    @NotNull @Contract("-> new")
    public Blender makeBlender() {
        try {
            Stats.onNativeCall();
            return new Blender(_nMakeBlender(_ptr));
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Sets a float uniform variable by name.
     *
     * @param name   the name of the uniform variable
     * @param value  the float value
     * @return       this builder for chaining
     */
    @NotNull @Contract("_, _ -> this")
    public RuntimeEffectBuilder setUniform(@NotNull String name, float value) {
        Stats.onNativeCall();
        _nSetUniformFloats(_ptr, name, new float[] { value });
        return this;
    }

    /**
     * Sets a float2 uniform variable by name.
     *
     * @param name  the name of the uniform variable
     * @param v1    first float value
     * @param v2    second float value
     * @return      this builder for chaining
     */
    @NotNull @Contract("_, _, _ -> this")
    public RuntimeEffectBuilder setUniform(@NotNull String name, float v1, float v2) {
        Stats.onNativeCall();
        _nSetUniformFloats(_ptr, name, new float[] { v1, v2 });
        return this;
    }

    /**
     * Sets a float3 uniform variable by name.
     *
     * @param name  the name of the uniform variable
     * @param v1    first float value
     * @param v2    second float value
     * @param v3    third float value
     * @return      this builder for chaining
     */
    @NotNull @Contract("_, _, _, _ -> this")
    public RuntimeEffectBuilder setUniform(@NotNull String name, float v1, float v2, float v3) {
        Stats.onNativeCall();
        _nSetUniformFloats(_ptr, name, new float[] { v1, v2, v3 });
        return this;
    }

    /**
     * Sets a float4 uniform variable by name.
     *
     * @param name  the name of the uniform variable
     * @param v1    first float value
     * @param v2    second float value
     * @param v3    third float value
     * @param v4    fourth float value
     * @return      this builder for chaining
     */
    @NotNull @Contract("_, _, _, _, _ -> this")
    public RuntimeEffectBuilder setUniform(@NotNull String name, float v1, float v2, float v3, float v4) {
        Stats.onNativeCall();
        _nSetUniformFloats(_ptr, name, new float[] { v1, v2, v3, v4 });
        return this;
    }

    /**
     * Sets a float array uniform variable by name.
     *
     * @param name   the name of the uniform variable
     * @param value  the float array
     * @return       this builder for chaining
     */
    @NotNull @Contract("_, _ -> this")
    public RuntimeEffectBuilder setUniform(@NotNull String name, @NotNull float[] value) {
        Stats.onNativeCall();
        _nSetUniformFloats(_ptr, name, value);
        return this;
    }

    /**
     * Sets a Matrix33 uniform variable by name.
     *
     * @param name   the name of the uniform variable
     * @param value  the Matrix33 value
     * @return       this builder for chaining
     */
    @NotNull @Contract("_, _ -> this")
    public RuntimeEffectBuilder setUniform(@NotNull String name, @NotNull Matrix33 value) {
        Stats.onNativeCall();
        _nSetUniformFloats(_ptr, name, value._mat);
        return this;
    }

    /**
     * Sets a Matrix44 uniform variable by name.
     *
     * @param name   the name of the uniform variable
     * @param value  the Matrix44 value
     * @return       this builder for chaining
     */
    @NotNull @Contract("_, _ -> this")
    public RuntimeEffectBuilder setUniform(@NotNull String name, @NotNull Matrix44 value) {
        Stats.onNativeCall();
        _nSetUniformFloats(_ptr, name, value._mat);
        return this;
    }

    /**
     * Sets an int uniform variable by name.
     *
     * @param name   the name of the uniform variable
     * @param value  the int value
     * @return       this builder for chaining
     */
    @NotNull @Contract("_, _ -> this")
    public RuntimeEffectBuilder setUniform(@NotNull String name, int value) {
        Stats.onNativeCall();
        _nSetUniformInts(_ptr, name, new int[] { value });
        return this;
    }

    /**
     * Sets an int2 uniform variable by name.
     *
     * @param name  the name of the uniform variable
     * @param v1    first int value
     * @param v2    second int value
     * @return      this builder for chaining
     */
    @NotNull @Contract("_, _, _ -> this")
    public RuntimeEffectBuilder setUniform(@NotNull String name, int v1, int v2) {
        Stats.onNativeCall();
        _nSetUniformInts(_ptr, name, new int[] { v1, v2 });
        return this;
    }

    /**
     * Sets an int3 uniform variable by name.
     *
     * @param name  the name of the uniform variable
     * @param v1    first int value
     * @param v2    second int value
     * @param v3    third int value
     * @return      this builder for chaining
     */
    @NotNull @Contract("_, _, _, _ -> this")
    public RuntimeEffectBuilder setUniform(@NotNull String name, int v1, int v2, int v3) {
        Stats.onNativeCall();
        _nSetUniformInts(_ptr, name, new int[] { v1, v2, v3 });
        return this;
    }

    /**
     * Sets an int4 uniform variable by name.
     *
     * @param name  the name of the uniform variable
     * @param v1    first int value
     * @param v2    second int value
     * @param v3    third int value
     * @param v4    fourth int value
     * @return      this builder for chaining
     */
    @NotNull @Contract("_, _, _, _, _ -> this")
    public RuntimeEffectBuilder setUniform(@NotNull String name, int v1, int v2, int v3, int v4) {
        Stats.onNativeCall();
        _nSetUniformInts(_ptr, name, new int[] { v1, v2, v3, v4 });
        return this;
    }

    /**
     * Sets an int array uniform variable by name.
     *
     * @param name   the name of the uniform variable
     * @param value  the int array
     * @return       this builder for chaining
     */
    @NotNull @Contract("_, _ -> this")
    public RuntimeEffectBuilder setUniform(@NotNull String name, @NotNull int[] value) {
        Stats.onNativeCall();
        _nSetUniformInts(_ptr, name, value);
        return this;
    }

    /**
     * Sets a child effect (shader, color filter, or blender) by name.
     *
     * @param name   the name of the child effect
     * @param child  the child effect (Shader, ColorFilter, or Blender), or null to unset
     * @return       this builder for chaining
     */
    @NotNull @Contract("_, _ -> this")
    public RuntimeEffectBuilder setChild(@NotNull String name, @Nullable RuntimeEffectChild child) {
        try {
            Stats.onNativeCall();
            _nSetChild(_ptr, name, Native.getPtr(child));
            return this;
        } finally {
            ReferenceUtil.reachabilityFence(child);
        }
    }

    @ApiStatus.Internal
    public static class _FinalizerHolder {
        public static final long PTR = _nGetFinalizer();
    }

    @ApiStatus.Internal public static native long _nGetFinalizer();
    @ApiStatus.Internal public static native long _nMake(long effectPtr);
    @ApiStatus.Internal public static native long _nMakeWithUniforms(long effectPtr, long uniformsPtr);
    @ApiStatus.Internal public static native long _nMakeCopy(long builderPtr);
    @ApiStatus.Internal public static native long _nMakeShader(long ptr, float[] localMatrix);
    @ApiStatus.Internal public static native long _nMakeColorFilter(long ptr);
    @ApiStatus.Internal public static native long _nMakeBlender(long ptr);
    @ApiStatus.Internal public static native long _nGetUniforms(long ptr);
    @ApiStatus.Internal public static native void _nSetUniformFloats(long ptr, String name, float[] values);
    @ApiStatus.Internal public static native void _nSetUniformInts(long ptr, String name, int[] values);
    @ApiStatus.Internal public static native void _nSetChild(long ptr, String name, long childPtr);
}
