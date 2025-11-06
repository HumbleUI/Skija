package io.github.humbleui.skija;

import org.jetbrains.annotations.*;
import io.github.humbleui.skija.impl.*;
import io.github.humbleui.types.*;

public class Shader extends RefCnt {
    static { Library.staticLoad(); }
    
    public Shader makeWithColorFilter(ColorFilter f) {
        try {
            return new Shader(_nMakeWithColorFilter(_ptr, Native.getPtr(f)));
        } finally {
            ReferenceUtil.reachabilityFence(this);
            ReferenceUtil.reachabilityFence(f);
        }
    }

    // Linear

    public static Shader makeLinearGradient(Point p0, Point p1, int[] colors) {
        return makeLinearGradient(p0._x, p0._y, p1._x, p1._y, colors);
    }

    public static Shader makeLinearGradient(float x0, float y0, float x1, float y1, int[] colors) {
        return makeLinearGradient(x0, y0, x1, y1, colors, null, GradientStyle.DEFAULT);
    }
    
    public static Shader makeLinearGradient(Point p0, Point p1, int[] colors, float[] positions) {
        return makeLinearGradient(p0._x, p0._y, p1._x, p1._y, colors, positions);
    }

    public static Shader makeLinearGradient(float x0, float y0, float x1, float y1, int[] colors, float[] positions) {
        return makeLinearGradient(x0, y0, x1, y1, colors, positions, GradientStyle.DEFAULT);
    }

    public static Shader makeLinearGradient(Point p0, Point p1, int[] colors, float[] positions, GradientStyle style) {
        return makeLinearGradient(p0._x, p0._y, p1._x, p1._y, colors, positions, style);
    }

    public static Shader makeLinearGradient(float x0, float y0, float x1, float y1, int[] colors, float[] positions, GradientStyle style) {
        assert positions == null || colors.length == positions.length : "colors.length " + colors.length + "!= positions.length " + positions.length;
        Stats.onNativeCall();
        return new Shader(_nMakeLinearGradient(x0, y0, x1, y1, colors, positions, style.getTileMode().ordinal(), style._getFlags(), style._getMatrixArray()));
    }

    public static Shader makeLinearGradient(Point p0, Point p1, Color4f[] colors, ColorSpace cs, float[] positions, GradientStyle style) {
        return makeLinearGradient(p0._x, p0._y, p1._x, p1._y, colors, cs, positions, style);
    }

    public static Shader makeLinearGradient(float x0, float y0, float x1, float y1, Color4f[] colors, ColorSpace cs, float[] positions, GradientStyle style) {
        try {
            assert positions == null || colors.length == positions.length : "colors.length " + colors.length + "!= positions.length " + positions.length;
            Stats.onNativeCall();
            return new Shader(_nMakeLinearGradientCS(x0, y0, x1, y1, Color4f.flattenArray(colors), Native.getPtr(cs), positions, style.getTileMode().ordinal(), style._getFlags(), style._getMatrixArray()));
        } finally {
            ReferenceUtil.reachabilityFence(cs);
        }
    }

    // Radial

    public static Shader makeRadialGradient(Point center, float r, int[] colors) {
        return makeRadialGradient(center._x, center._y, r, colors);
    }

    public static Shader makeRadialGradient(float x, float y, float r, int[] colors) {
        return makeRadialGradient(x, y, r, colors, null, GradientStyle.DEFAULT);
    }

    public static Shader makeRadialGradient(Point center, float r, int[] colors, float[] positions) {
        return makeRadialGradient(center._x, center._y, r, colors, positions);
    }

    public static Shader makeRadialGradient(float x, float y, float r, int[] colors, float[] positions) {
        return makeRadialGradient(x, y, r, colors, positions, GradientStyle.DEFAULT);
    }

    public static Shader makeRadialGradient(Point center, float r, int[] colors, float[] positions, GradientStyle style) {
        return makeRadialGradient(center._x, center._y, r, colors, positions, style);
    }

    public static Shader makeRadialGradient(float x, float y, float r, int[] colors, float[] positions, GradientStyle style) {
        assert positions == null || colors.length == positions.length : "colors.length " + colors.length + "!= positions.length " + positions.length;
        Stats.onNativeCall();
        return new Shader(_nMakeRadialGradient(x, y, r, colors, positions, style.getTileMode().ordinal(), style._getFlags(), style._getMatrixArray()));
    }

    public static Shader makeRadialGradient(Point center, float r, Color4f[] colors, ColorSpace cs, float[] positions, GradientStyle style) {
        return makeRadialGradient(center._x, center._y, r, colors, cs, positions, style);
    }

    public static Shader makeRadialGradient(float x, float y, float r, Color4f[] colors, ColorSpace cs, float[] positions, GradientStyle style) {
        try {
            assert positions == null || colors.length == positions.length : "colors.length " + colors.length + "!= positions.length " + positions.length;
            Stats.onNativeCall();
            return new Shader(_nMakeRadialGradientCS(x, y, r, Color4f.flattenArray(colors), Native.getPtr(cs), positions, style.getTileMode().ordinal(), style._getFlags(), style._getMatrixArray()));
        } finally {
            ReferenceUtil.reachabilityFence(cs);
        }
    }

    // Two-point Conical

    public static Shader makeTwoPointConicalGradient(Point p0, float r0, Point p1, float r1, int[] colors) {
        return makeTwoPointConicalGradient(p0._x, p0._y, r0, p1._x, p1._y, r1, colors);
    }

    public static Shader makeTwoPointConicalGradient(float x0, float y0, float r0, float x1, float y1, float r1, int[] colors) {
        return makeTwoPointConicalGradient(x0, y0, r0, x1, y1, r1, colors, null, GradientStyle.DEFAULT);
    }

    public static Shader makeTwoPointConicalGradient(Point p0, float r0, Point p1, float r1, int[] colors, float[] positions) {
        return makeTwoPointConicalGradient(p0._x, p0._y, r0, p1._x, p1._y, r1, colors, positions);
    }

    public static Shader makeTwoPointConicalGradient(float x0, float y0, float r0, float x1, float y1, float r1, int[] colors, float[] positions) {
        return makeTwoPointConicalGradient(x0, y0, r0, x1, y1, r1, colors, positions, GradientStyle.DEFAULT);
    }

    public static Shader makeTwoPointConicalGradient(Point p0, float r0, Point p1, float r1, int[] colors, float[] positions, GradientStyle style) {
        return makeTwoPointConicalGradient(p0._x, p0._y, r0, p1._x, p1._y, r1, colors, positions, style);
    }

    public static Shader makeTwoPointConicalGradient(float x0, float y0, float r0, float x1, float y1, float r1, int[] colors, float[] positions, GradientStyle style) {
        assert positions == null || colors.length == positions.length : "colors.length " + colors.length + "!= positions.length " + positions.length;
        Stats.onNativeCall();
        return new Shader(_nMakeTwoPointConicalGradient(x0, y0, r0, x1, y1, r1, colors, positions, style.getTileMode().ordinal(), style._getFlags(), style._getMatrixArray()));
    }

    public static Shader makeTwoPointConicalGradient(Point p0, float r0, Point p1, float r1, Color4f[] colors, ColorSpace cs, float[] positions, GradientStyle style) {
        return makeTwoPointConicalGradient(p0._x, p0._y, r0, p1._x, p1._y, r1, colors, cs, positions, style);
    }

    public static Shader makeTwoPointConicalGradient(float x0, float y0, float r0, float x1, float y1, float r1, Color4f[] colors, ColorSpace cs, float[] positions, GradientStyle style) {
        try {
            assert positions == null || colors.length == positions.length : "colors.length " + colors.length + "!= positions.length " + positions.length;
            Stats.onNativeCall();
            return new Shader(_nMakeTwoPointConicalGradientCS(x0, y0, r0, x1, y1, r1, Color4f.flattenArray(colors), Native.getPtr(cs), positions, style.getTileMode().ordinal(), style._getFlags(), style._getMatrixArray()));
        } finally {
            ReferenceUtil.reachabilityFence(cs);
        }
    }

    // Sweep

    public static Shader makeSweepGradient(Point center, int[] colors) {
        return makeSweepGradient(center._x, center._y, colors);
    }

    public static Shader makeSweepGradient(float x, float y, int[] colors) {
        return makeSweepGradient(x, y, 0, 360, colors, null, GradientStyle.DEFAULT);
    }

    public static Shader makeSweepGradient(Point center, int[] colors, float[] positions) {
        return makeSweepGradient(center._x, center._y, colors, positions);
    }

    public static Shader makeSweepGradient(float x, float y, int[] colors, float[] positions) {
        return makeSweepGradient(x, y, 0, 360, colors, positions, GradientStyle.DEFAULT);
    }

    public static Shader makeSweepGradient(Point center, int[] colors, float[] positions, GradientStyle style) {
        return makeSweepGradient(center._x, center._y, colors, positions, style);
    }

    public static Shader makeSweepGradient(float x, float y, int[] colors, float[] positions, GradientStyle style) {
        return makeSweepGradient(x, y, 0, 360, colors, positions, style);
    }

    public static Shader makeSweepGradient(Point center, float startAngle, float endAngle, int[] colors, float[] positions, GradientStyle style) {
        return makeSweepGradient(center._x, center._y, startAngle, endAngle, colors, positions, style);
    }

    public static Shader makeSweepGradient(float x, float y, float startAngle, float endAngle, int[] colors, float[] positions, GradientStyle style) {
        assert positions == null || colors.length == positions.length : "colors.length " + colors.length + "!= positions.length " + positions.length;
        Stats.onNativeCall();
        return new Shader(_nMakeSweepGradient(x, y, startAngle, endAngle, colors, positions, style.getTileMode().ordinal(), style._getFlags(), style._getMatrixArray()));
    }

    public static Shader makeSweepGradient(Point center, float startAngle, float endAngle, Color4f[] colors, ColorSpace cs, float[] positions, GradientStyle style) {
        return makeSweepGradient(center._x, center._y, startAngle, endAngle, colors, cs, positions, style);
    }

    public static Shader makeSweepGradient(float x, float y, float startAngle, float endAngle, Color4f[] colors, ColorSpace cs, float[] positions, GradientStyle style) {
        try {
            assert positions == null || colors.length == positions.length : "colors.length " + colors.length + "!= positions.length " + positions.length;
            Stats.onNativeCall();
            return new Shader(_nMakeSweepGradientCS(x, y, startAngle, endAngle, Color4f.flattenArray(colors), Native.getPtr(cs), positions, style.getTileMode().ordinal(), style._getFlags(), style._getMatrixArray()));
        } finally {
            ReferenceUtil.reachabilityFence(cs);
        }
    }

    // Perlin Noise

    /**
     * <p>Creates a shader that generates fractal Perlin noise.</p>
     *
     * @param baseFrequencyX Base frequency in the X direction; usually in the range [0..1]
     * @param baseFrequencyY Base frequency in the Y direction; usually in the range [0..1]
     * @param numOctaves     Number of octaves of noise to add together; should be fairly small (limit of 255)
     * @param seed           Random seed value
     * @param tileSize       If non-null, modifies frequencies to make tileable noise for the given tile size
     * @return               Shader that generates fractal Perlin noise
     */
    @NotNull
    public static Shader makeFractalNoise(float baseFrequencyX, float baseFrequencyY, int numOctaves, float seed, @Nullable IPoint tileSize) {
        Stats.onNativeCall();
        return new Shader(_nMakeFractalNoise(baseFrequencyX, baseFrequencyY, numOctaves, seed, tileSize == null ? 0 : tileSize.getX(), tileSize == null ? 0 : tileSize.getY()));
    }

    /**
     * <p>Creates a shader that generates fractal Perlin noise without tiling.</p>
     *
     * @param baseFrequencyX Base frequency in the X direction; usually in the range [0..1]
     * @param baseFrequencyY Base frequency in the Y direction; usually in the range [0..1]
     * @param numOctaves     Number of octaves of noise to add together; should be fairly small (limit of 255)
     * @param seed           Random seed value
     * @return               Shader that generates fractal Perlin noise
     */
    @NotNull
    public static Shader makeFractalNoise(float baseFrequencyX, float baseFrequencyY, int numOctaves, float seed) {
        return makeFractalNoise(baseFrequencyX, baseFrequencyY, numOctaves, seed, null);
    }

    /**
     * <p>Creates a shader that generates turbulence using Perlin noise.</p>
     *
     * @param baseFrequencyX Base frequency in the X direction; usually in the range [0..1]
     * @param baseFrequencyY Base frequency in the Y direction; usually in the range [0..1]
     * @param numOctaves     Number of octaves of noise to add together; should be fairly small (limit of 255)
     * @param seed           Random seed value
     * @param tileSize       If non-null, modifies frequencies to make tileable noise for the given tile size
     * @return               Shader that generates turbulent Perlin noise
     */
    @NotNull
    public static Shader makeTurbulence(float baseFrequencyX, float baseFrequencyY, int numOctaves, float seed, @Nullable IPoint tileSize) {
        Stats.onNativeCall();
        return new Shader(_nMakeTurbulence(baseFrequencyX, baseFrequencyY, numOctaves, seed, tileSize == null ? 0 : tileSize.getX(), tileSize == null ? 0 : tileSize.getY()));
    }

    /**
     * <p>Creates a shader that generates turbulence using Perlin noise without tiling.</p>
     *
     * @param baseFrequencyX Base frequency in the X direction; usually in the range [0..1]
     * @param baseFrequencyY Base frequency in the Y direction; usually in the range [0..1]
     * @param numOctaves     Number of octaves of noise to add together; should be fairly small (limit of 255)
     * @param seed           Random seed value
     * @return               Shader that generates turbulent Perlin noise
     */
    @NotNull
    public static Shader makeTurbulence(float baseFrequencyX, float baseFrequencyY, int numOctaves, float seed) {
        return makeTurbulence(baseFrequencyX, baseFrequencyY, numOctaves, seed, null);
    }

    //

    public static Shader makeEmpty() {
        Stats.onNativeCall();
        return new Shader(_nMakeEmpty());
    }

    public static Shader makeColor(int color) {
        Stats.onNativeCall();
        return new Shader(_nMakeColor(color));
    }

    public static Shader makeColor(Color4f color, ColorSpace space) {
        try {
            Stats.onNativeCall();
            return new Shader(_nMakeColorCS(color.getR(), color.getG(), color.getB(), color.getA(), Native.getPtr(space)));
        } finally {
            ReferenceUtil.reachabilityFence(space);
        }
    }

    public static Shader makeBlend(BlendMode mode, Shader dst, Shader src) {
        try {
            Stats.onNativeCall();
            return new Shader(_nMakeBlend(mode.ordinal(), Native.getPtr(dst), Native.getPtr(src)));
        } finally {
            ReferenceUtil.reachabilityFence(dst);
            ReferenceUtil.reachabilityFence(src);
        }
    }

    @ApiStatus.Internal
    public Shader(long ptr) {
        super(ptr);
    }

    public static native long _nMakeWithColorFilter(long ptr, long colorFilterPtr);
    public static native long _nMakeLinearGradient(float x0, float y0, float x1, float y1, int[] colors, float[] positions, int tileType, int flags, float[] matrix);
    public static native long _nMakeLinearGradientCS(float x0, float y0, float x1, float y1, float[] colors, long colorSpacePtr, float[] positions, int tileType, int flags, float[] matrix);
    public static native long _nMakeRadialGradient(float x, float y, float r, int[] colors, float[] positions, int tileType, int flags, float[] matrix);
    public static native long _nMakeRadialGradientCS(float x, float y, float r, float[] colors, long colorSpacePtr, float[] positions, int tileType, int flags, float[] matrix);
    public static native long _nMakeTwoPointConicalGradient(float x0, float y0, float r0, float x1, float y1, float r1, int[] colors, float[] positions, int tileType, int flags, float[] matrix);
    public static native long _nMakeTwoPointConicalGradientCS(float x0, float y0, float r0, float x1, float y1, float r1, float[] colors, long colorSpacePtr, float[] positions, int tileType, int flags, float[] matrix);
    public static native long _nMakeSweepGradient(float x, float y, float startAngle, float endAngle, int[] colors, float[] positions, int tileType, int flags, float[] matrix);
    public static native long _nMakeSweepGradientCS(float x, float y, float startAngle, float endAngle, float[] colors, long colorSpacePtr, float[] positions, int tileType, int flags, float[] matrix);
    public static native long _nMakeFractalNoise(float baseFrequencyX, float baseFrequencyY, int numOctaves, float seed, int tileSizeX, int tileSizeY);
    public static native long _nMakeTurbulence(float baseFrequencyX, float baseFrequencyY, int numOctaves, float seed, int tileSizeX, int tileSizeY);
    public static native long _nMakeEmpty();
    public static native long _nMakeColor(int color);
    public static native long _nMakeColorCS(float r, float g, float b, float a, long colorSpacePtr);
    public static native long _nMakeBlend(int blendMode, long dst, long src);
}