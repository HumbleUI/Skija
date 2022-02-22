package io.github.humbleui.skija;

import org.jetbrains.annotations.*;
import io.github.humbleui.skija.impl.*;

public class RuntimeEffect extends RefCnt {
    static {
        Library.staticLoad();
    }

    public static RuntimeEffect makeForShader(String sksl) {
        Stats.onNativeCall();
        return new RuntimeEffect(_nMakeForShader(sksl));
    }

    @NotNull
    public Shader makeShader(@Nullable Data uniforms, @Nullable Shader[] children, @Nullable Matrix33 localMatrix) {
        Stats.onNativeCall();
        int childCount = children == null ? 0 : children.length;
        long[] childrenPtrs = new long[childCount];
        for (int i = 0; i < childCount; i++)
            childrenPtrs[i] = Native.getPtr(children[i]);
        float[] matrix = localMatrix == null ? null : localMatrix._mat;
        return new Shader(_nMakeShader(_ptr, Native.getPtr(uniforms), childrenPtrs, matrix));
    }

    @NotNull
    public static RuntimeEffect makeForColorFilter(String sksl) {
        Stats.onNativeCall();
        return new RuntimeEffect(_nMakeForColorFilter(sksl));
    }

    @NotNull
    public ColorFilter makeColorFilter(@Nullable Data uniforms, @Nullable ColorFilter[] children) {
        Stats.onNativeCall();
        int childCount = children == null ? 0 : children.length;
        long[] childrenPtrs = new long[childCount];
        for (int i = 0; i < childCount; i++)
            childrenPtrs[i] = Native.getPtr(children[i]);
        return new ColorFilter(_nMakeColorFilter(_ptr, Native.getPtr(uniforms), childrenPtrs));
    }

    @ApiStatus.Internal
    public RuntimeEffect(long ptr) {
        super(ptr);
    }

    @ApiStatus.Internal public static native long _nMakeForShader(String sksl);
    @ApiStatus.Internal public static native long _nMakeShader(long runtimeEffectPtr, long uniformPtr, long[] childrenPtrs, float[] localMatrix);
    @ApiStatus.Internal public static native long _nMakeForColorFilter(String sksl);
    @ApiStatus.Internal public static native long _nMakeColorFilter(long runtimeEffectPtr, long uniformPtr, long[] childrenPtrs);
}