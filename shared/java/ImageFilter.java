package io.github.humbleui.skija;

import org.jetbrains.annotations.*;
import io.github.humbleui.skija.impl.*;
import io.github.humbleui.types.*;

import java.util.Arrays;

public class ImageFilter extends RefCnt {
    static { Library.staticLoad(); }
    
    public static ImageFilter makeArithmetic(float k1, float k2, float k3, float k4, boolean enforcePMColor, ImageFilter bg, ImageFilter fg, Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeArithmetic(k1, k2, k3, k4, enforcePMColor, Native.getPtr(bg), Native.getPtr(fg), crop));
        } finally {
            ReferenceUtil.reachabilityFence(bg);
            ReferenceUtil.reachabilityFence(fg);
        }
    }

    public static ImageFilter makeArithmetic(float k1, float k2, float k3, float k4, boolean enforcePMColor, ImageFilter bg, ImageFilter fg, IRect crop) {
        return makeArithmetic(k1, k2, k3, k4, enforcePMColor, bg, fg, crop == null ? null : crop.toRect());
    }

    public static ImageFilter makeArithmetic(float k1, float k2, float k3, float k4, boolean enforcePMColor, ImageFilter bg, ImageFilter fg) {
        return makeArithmetic(k1, k2, k3, k4, enforcePMColor, bg, fg, (Rect) null);
    }

    public static ImageFilter makeBlend(BlendMode blendMode, ImageFilter bg, ImageFilter fg, Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeBlend(blendMode.ordinal(), Native.getPtr(bg), Native.getPtr(fg), crop));
        } finally {
            ReferenceUtil.reachabilityFence(bg);
            ReferenceUtil.reachabilityFence(fg);
        }
    }

    public static ImageFilter makeBlend(BlendMode blendMode, ImageFilter bg, ImageFilter fg, IRect crop) {
        return makeBlend(blendMode, bg, fg, crop == null ? null : crop.toRect());
    }

    public static ImageFilter makeBlend(BlendMode blendMode, ImageFilter bg, ImageFilter fg) {
        return makeBlend(blendMode, bg, fg, (Rect) null);
    }

    public static ImageFilter makeBlur(float sigmaX, float sigmaY, FilterTileMode mode, ImageFilter input, Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeBlur(sigmaX, sigmaY, mode.ordinal(), Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    public static ImageFilter makeBlur(float sigmaX, float sigmaY, FilterTileMode mode, ImageFilter input, IRect crop) {
        return makeBlur(sigmaX, sigmaY, mode, input, crop == null ? null : crop.toRect());
    }

    public static ImageFilter makeBlur(float sigmaX, float sigmaY, FilterTileMode mode) {
        return makeBlur(sigmaX, sigmaY, mode, null, (Rect) null);
    }

    public static ImageFilter makeColorFilter(ColorFilter f, ImageFilter input, Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeColorFilter(Native.getPtr(f), Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(f);
            ReferenceUtil.reachabilityFence(input);
        }
    }

    public static ImageFilter makeColorFilter(ColorFilter f, ImageFilter input, IRect crop) {
        return makeColorFilter(f, input, crop == null ? null : crop.toRect());
    }

    public static ImageFilter makeColorFilter(ColorFilter f, ImageFilter input) {
        return makeColorFilter(f, input, (Rect) null);
    }

    public static ImageFilter makeCompose(ImageFilter outer, ImageFilter inner) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeCompose(Native.getPtr(outer), Native.getPtr(inner)));
        } finally {
            ReferenceUtil.reachabilityFence(outer);
            ReferenceUtil.reachabilityFence(inner);
        }
    }

    /**
     * <p>Create a filter that applies a crop to the result of the input filter. Pixels within the
     * crop rectangle are unmodified from what the input produced. Pixels outside of crop match the
     * provided FilterTileMode (defaulting to DECAL).</p>
     *
     * <p>NOTE: The optional Rect crop argument for many of the factory methods is equivalent to creating the
     * filter without a crop and then wrapping it in makeCrop(rect, DECAL). Explicitly adding
     * Crop filters lets you control their tiling and use different geometry for the input and the
     * output of another filter.</p>
     *
     * @param rect      The cropping geometry
     * @param tileMode  The tile mode applied to pixels outside of the crop rectangle
     * @param input     The input filter that is cropped, uses source image if this is null
     * @return          filter that crops its input
     */
    @NotNull @Contract("_, _, _ -> new")
    public static ImageFilter makeCrop(@NotNull Rect rect, @NotNull FilterTileMode tileMode, @Nullable ImageFilter input) {
        try {
            assert rect != null : "Can't makeCrop with rect == null";
            assert tileMode != null : "Can't makeCrop with tileMode == null";
            Stats.onNativeCall();
            return new ImageFilter(_nMakeCrop(rect._left, rect._top, rect._right, rect._bottom, tileMode.ordinal(), Native.getPtr(input)));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    /**
     * <p>Create a filter that applies a crop to the result of the input filter. Pixels within the
     * crop rectangle are unmodified from what the input produced. Pixels outside of crop are transparent.</p>
     *
     * @param rect   The cropping geometry
     * @param input  The input filter that is cropped, uses source image if this is null
     * @return       filter that crops its input
     */
    @NotNull @Contract("_, _ -> new")
    public static ImageFilter makeCrop(@NotNull Rect rect, @Nullable ImageFilter input) {
        return makeCrop(rect, FilterTileMode.DECAL, input);
    }

    public static ImageFilter makeDisplacementMap(ColorChannel x, ColorChannel y, float scale, ImageFilter displacement, ImageFilter color, Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeDisplacementMap(x.ordinal(), y.ordinal(), scale, Native.getPtr(displacement), Native.getPtr(color), crop));
        } finally {
            ReferenceUtil.reachabilityFence(displacement);
            ReferenceUtil.reachabilityFence(color);
        }
    }

    public static ImageFilter makeDisplacementMap(ColorChannel x, ColorChannel y, float scale, ImageFilter displacement, ImageFilter color, IRect crop) {
        return makeDisplacementMap(x, y, scale, displacement, color, crop == null ? null : crop.toRect());
    }

    public static ImageFilter makeDisplacementMap(ColorChannel x, ColorChannel y, float scale, ImageFilter displacement, ImageFilter color) {
        return makeDisplacementMap(x, y, scale, displacement, color, (Rect) null);
    }

    public static ImageFilter makeDropShadow(float dx, float dy, float sigmaX, float sigmaY, int color, ImageFilter input, Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeDropShadow(dx, dy, sigmaX, sigmaY, color, Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    public static ImageFilter makeDropShadow(float dx, float dy, float sigmaX, float sigmaY, int color, ImageFilter input, IRect crop) {
        return makeDropShadow(dx, dy, sigmaX, sigmaY, color, input, crop == null ? null : crop.toRect());
    }

    public static ImageFilter makeDropShadow(float dx, float dy, float sigmaX, float sigmaY, int color, ImageFilter input) {
        return makeDropShadow(dx, dy, sigmaX, sigmaY, color, input, (Rect) null);
    }

    public static ImageFilter makeDropShadow(float dx, float dy, float sigmaX, float sigmaY, int color) {
        return makeDropShadow(dx, dy, sigmaX, sigmaY, color, null, (Rect) null);
    }

    public static ImageFilter makeDropShadowOnly(float dx, float dy, float sigmaX, float sigmaY, int color, ImageFilter input, Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeDropShadowOnly(dx, dy, sigmaX, sigmaY, color, Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    public static ImageFilter makeDropShadowOnly(float dx, float dy, float sigmaX, float sigmaY, int color, ImageFilter input, IRect crop) {
        return makeDropShadowOnly(dx, dy, sigmaX, sigmaY, color, input, crop == null ? null : crop.toRect());
    }

    public static ImageFilter makeDropShadowOnly(float dx, float dy, float sigmaX, float sigmaY, int color) {
        return makeDropShadowOnly(dx, dy, sigmaX, sigmaY, color, null, (Rect) null);
    }

    /**
     * Create a filter that always produces transparent black.
     *
     * @return filter that produces transparent black
     */
    @NotNull @Contract("-> new")
    public static ImageFilter makeEmpty() {
        Stats.onNativeCall();
        return new ImageFilter(_nMakeEmpty());
    }

    public static ImageFilter makeImage(Image image, Rect src, Rect dst, SamplingMode mode) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeImage(Native.getPtr(image), src._left, src._top, src._right, src._bottom, dst._left, dst._top, dst._right, dst._bottom, mode._pack()));
        } finally {
            ReferenceUtil.reachabilityFence(image);
        }
    }

    public static ImageFilter makeImage(Image image) {
        Rect r = Rect.makeWH(image.getWidth(), image.getHeight());
        return makeImage(image, r, r, SamplingMode.DEFAULT);
    }

    public static ImageFilter makeMagnifier(Rect r, float zoomAmount, float inset, SamplingMode mode, ImageFilter input, Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeMagnifier(r._left, r._top, r._right, r._bottom, zoomAmount, inset, mode._pack(), Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    public static ImageFilter makeMagnifier(Rect r, float zoomAmount, float inset, SamplingMode mode, ImageFilter input, IRect crop) {
        return makeMagnifier(r, zoomAmount, inset, mode, input, crop == null ? null : crop.toRect());
    }

    public static ImageFilter makeMagnifier(Rect r, float zoomAmount, float inset, SamplingMode mode, ImageFilter input) {
        return makeMagnifier(r, zoomAmount, inset, mode, input, (Rect) null);
    }

    public static ImageFilter makeMatrixConvolution(int kernelW, int kernelH, float[] kernel, float gain, float bias, int offsetX, int offsetY, FilterTileMode tileMode, boolean convolveAlpha, ImageFilter input, Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeMatrixConvolution(kernelW, kernelH, kernel, gain, bias, offsetX, offsetY, tileMode.ordinal(), convolveAlpha, Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    public static ImageFilter makeMatrixConvolution(int kernelW, int kernelH, float[] kernel, float gain, float bias, int offsetX, int offsetY, FilterTileMode tileMode, boolean convolveAlpha, ImageFilter input, IRect crop) {
        return makeMatrixConvolution(kernelW, kernelH, kernel, gain, bias, offsetX, offsetY, tileMode, convolveAlpha, input, crop == null ? null : crop.toRect());
    }

    public static ImageFilter makeMatrixConvolution(int kernelW, int kernelH, float[] kernel, float gain, float bias, int offsetX, int offsetY, FilterTileMode tileMode, boolean convolveAlpha, ImageFilter input) {
        return makeMatrixConvolution(kernelW, kernelH, kernel, gain, bias, offsetX, offsetY, tileMode, convolveAlpha, input, (Rect) null);
    }

    public static ImageFilter makeMatrixTransform(Matrix33 matrix, SamplingMode mode, ImageFilter input) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeMatrixTransform(matrix.getMat(), mode._pack(), Native.getPtr(input)));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    public static ImageFilter makeMerge(ImageFilter[] filters, Rect crop) {
        try {
            Stats.onNativeCall();
            long[] filterPtrs = new long[filters.length];
            Arrays.setAll(filterPtrs, i -> Native.getPtr(filters[i]));
            return new ImageFilter(_nMakeMerge(filterPtrs, crop));
        } finally {
            ReferenceUtil.reachabilityFence(filters);
        }
    }

    public static ImageFilter makeMerge(ImageFilter[] filters, IRect crop) {
        return makeMerge(filters, crop == null ? null : crop.toRect());
    }

    public static ImageFilter makeMerge(ImageFilter[] filters) {
        return makeMerge(filters, (Rect) null);
    }

    public static ImageFilter makeOffset(float dx, float dy, ImageFilter input, Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeOffset(dx, dy, Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    public static ImageFilter makeOffset(float dx, float dy, ImageFilter input, IRect crop) {
        return makeOffset(dx, dy, input, crop == null ? null : crop.toRect());
    }

    public static ImageFilter makeOffset(float dx, float dy, ImageFilter input) {
        return makeOffset(dx, dy, input, (Rect) null);
    }

    /**
     * <p>Create a filter that produces the Picture as its output, clipped to both targetRect and
     * the picture's internal cull rect.</p>
     *
     * <p>If picture is null, the returned image filter produces transparent black.</p>
     *
     * @param picture    The picture that is drawn for the filter output
     * @param targetRect The drawing region for the picture
     * @return           filter that draws the picture
     */
    @NotNull @Contract("_, _ -> new")
    public static ImageFilter makePicture(@Nullable Picture picture, @NotNull Rect targetRect) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakePicture(Native.getPtr(picture), targetRect._left, targetRect._top, targetRect._right, targetRect._bottom));
        } finally {
            ReferenceUtil.reachabilityFence(picture);
        }
    }

    /**
     * <p>Create a filter that produces the Picture as its output, using the picture's cull rect
     * as the drawing region.</p>
     *
     * <p>If picture is null, the returned image filter produces transparent black.</p>
     *
     * @param picture The picture that is drawn for the filter output
     * @return        filter that draws the picture
     */
    @NotNull @Contract("_ -> new")
    public static ImageFilter makePicture(@Nullable Picture picture) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakePicture(Native.getPtr(picture)));
        } finally {
            ReferenceUtil.reachabilityFence(picture);
        }
    }

    // RuntimeShader

    /**
     * <p>Create a filter that fills the output with the per-pixel evaluation of the shader.</p>
     *
     * @param shader The shader to evaluate on every output pixel
     * @param dither Whether to apply dithering
     * @param crop   Optional crop rect to apply to the result
     * @return       filter that fills output with shader evaluation
     */
    @NotNull @Contract("_, _, _ -> new")
    public static ImageFilter makeShader(@NotNull Shader shader, boolean dither, @Nullable Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeShader(Native.getPtr(shader), dither, crop));
        } finally {
            ReferenceUtil.reachabilityFence(shader);
        }
    }

    /**
     * <p>Create a filter that fills the output with the per-pixel evaluation of the shader,
     * without dithering.</p>
     *
     * @param shader The shader to evaluate on every output pixel
     * @param crop   Optional crop rect to apply to the result
     * @return       filter that fills output with shader evaluation
     */
    @NotNull @Contract("_, _ -> new")
    public static ImageFilter makeShader(@NotNull Shader shader, @Nullable Rect crop) {
        return makeShader(shader, false, crop);
    }

    /**
     * <p>Create a filter that fills the output with the per-pixel evaluation of the shader,
     * without dithering or cropping.</p>
     *
     * @param shader The shader to evaluate on every output pixel
     * @return       filter that fills output with shader evaluation
     */
    @NotNull @Contract("_ -> new")
    public static ImageFilter makeShader(@NotNull Shader shader) {
        return makeShader(shader, false, (Rect) null);
    }

    public static ImageFilter makeTile(Rect src, Rect dst, ImageFilter input) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeTile(src._left, src._top, src._right, src._bottom, dst._left, dst._top, dst._right, dst._bottom, Native.getPtr(input)));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    public static ImageFilter makeDilate(float rx, float ry, ImageFilter input, Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeDilate(rx, ry, Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    public static ImageFilter makeDilate(float rx, float ry, ImageFilter input, IRect crop) {
        return makeDilate(rx, ry, input, crop == null ? null : crop.toRect());
    }

    public static ImageFilter makeDilate(float rx, float ry, ImageFilter input) {
        return makeDilate(rx, ry, input, (Rect) null);
    }

    public static ImageFilter makeErode(float rx, float ry, ImageFilter input, Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeErode(rx, ry, Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    public static ImageFilter makeErode(float rx, float ry, ImageFilter input, IRect crop) {
        return makeErode(rx, ry, input, crop == null ? null : crop.toRect());
    }

    public static ImageFilter makeErode(float rx, float ry, ImageFilter input) {
        return makeErode(rx, ry, input, (Rect) null);
    }

    public static ImageFilter makeDistantLitDiffuse(float x, float y, float z, int lightColor, float surfaceScale, float kd, ImageFilter input, Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeDistantLitDiffuse(x, y, z, lightColor, surfaceScale, kd, Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    public static ImageFilter makeDistantLitDiffuse(float x, float y, float z, int lightColor, float surfaceScale, float kd, ImageFilter input, IRect crop) {
        return makeDistantLitDiffuse(x, y, z, lightColor, surfaceScale, kd, input, crop == null ? null : crop.toRect());
    }

    public static ImageFilter makeDistantLitDiffuse(float x, float y, float z, int lightColor, float surfaceScale, float kd, ImageFilter input) {
        return makeDistantLitDiffuse(x, y, z, lightColor, surfaceScale, kd, input, (Rect) null);
    }

    public static ImageFilter makePointLitDiffuse(float x, float y, float z, int lightColor, float surfaceScale, float kd, ImageFilter input, Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakePointLitDiffuse(x, y, z, lightColor, surfaceScale, kd, Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    public static ImageFilter makePointLitDiffuse(float x, float y, float z, int lightColor, float surfaceScale, float kd, ImageFilter input, IRect crop) {
        return makePointLitDiffuse(x, y, z, lightColor, surfaceScale, kd, input, crop == null ? null : crop.toRect());
    }

    public static ImageFilter makePointLitDiffuse(float x, float y, float z, int lightColor, float surfaceScale, float kd, ImageFilter input) {
        return makePointLitDiffuse(x, y, z, lightColor, surfaceScale, kd, input, (Rect) null);
    }

    public static ImageFilter makeSpotLitDiffuse(float x0, float y0, float z0, float x1, float y1, float z1, float falloffExponent, float cutoffAngle, int lightColor, float surfaceScale, float kd, ImageFilter input, Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeSpotLitDiffuse(x0, y0, z0, x1, y1, z1, falloffExponent, cutoffAngle, lightColor, surfaceScale, kd, Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    public static ImageFilter makeSpotLitDiffuse(float x0, float y0, float z0, float x1, float y1, float z1, float falloffExponent, float cutoffAngle, int lightColor, float surfaceScale, float kd, ImageFilter input, IRect crop) {
        return makeSpotLitDiffuse(x0, y0, z0, x1, y1, z1, falloffExponent, cutoffAngle, lightColor, surfaceScale, kd, input, crop == null ? null : crop.toRect());
    }

    public static ImageFilter makeSpotLitDiffuse(float x0, float y0, float z0, float x1, float y1, float z1, float falloffExponent, float cutoffAngle, int lightColor, float surfaceScale, float kd, ImageFilter input) {
        return makeSpotLitDiffuse(x0, y0, z0, x1, y1, z1, falloffExponent, cutoffAngle, lightColor, surfaceScale, kd, input, (Rect) null);
    }

    public static ImageFilter makeDistantLitSpecular(float x, float y, float z, int lightColor, float surfaceScale, float ks, float shininess, ImageFilter input, Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeDistantLitSpecular(x, y, z, lightColor, surfaceScale, ks, shininess, Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    public static ImageFilter makeDistantLitSpecular(float x, float y, float z, int lightColor, float surfaceScale, float ks, float shininess, ImageFilter input, IRect crop) {
        return makeDistantLitSpecular(x, y, z, lightColor, surfaceScale, ks, shininess, input, crop == null ? null : crop.toRect());
    }

    public static ImageFilter makeDistantLitSpecular(float x, float y, float z, int lightColor, float surfaceScale, float ks, float shininess, ImageFilter input) {
        return makeDistantLitSpecular(x, y, z, lightColor, surfaceScale, ks, shininess, input, (Rect) null);
    }

    public static ImageFilter makePointLitSpecular(float x, float y, float z, int lightColor, float surfaceScale, float ks, float shininess, ImageFilter input, Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakePointLitSpecular(x, y, z, lightColor, surfaceScale, ks, shininess, Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    public static ImageFilter makePointLitSpecular(float x, float y, float z, int lightColor, float surfaceScale, float ks, float shininess, ImageFilter input, IRect crop) {
        return makePointLitSpecular(x, y, z, lightColor, surfaceScale, ks, shininess, input, crop == null ? null : crop.toRect());
    }

    public static ImageFilter makePointLitSpecular(float x, float y, float z, int lightColor, float surfaceScale, float ks, float shininess, ImageFilter input) {
        return makePointLitSpecular(x, y, z, lightColor, surfaceScale, ks, shininess, input, (Rect) null);
    }

    public static ImageFilter makeSpotLitSpecular(float x0, float y0, float z0, float x1, float y1, float z1, float falloffExponent, float cutoffAngle, int lightColor, float surfaceScale, float ks, float shininess, ImageFilter input, Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeSpotLitSpecular(x0, y0, z0, x1, y1, z1, falloffExponent, cutoffAngle, lightColor, surfaceScale, ks, shininess, Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    public static ImageFilter makeSpotLitSpecular(float x0, float y0, float z0, float x1, float y1, float z1, float falloffExponent, float cutoffAngle, int lightColor, float surfaceScale, float ks, float shininess, ImageFilter input, IRect crop) {
        return makeSpotLitSpecular(x0, y0, z0, x1, y1, z1, falloffExponent, cutoffAngle, lightColor, surfaceScale, ks, shininess, input, crop == null ? null : crop.toRect());
    }

    public static ImageFilter makeSpotLitSpecular(float x0, float y0, float z0, float x1, float y1, float z1, float falloffExponent, float cutoffAngle, int lightColor, float surfaceScale, float ks, float shininess, ImageFilter input) {
        return makeSpotLitSpecular(x0, y0, z0, x1, y1, z1, falloffExponent, cutoffAngle, lightColor, surfaceScale, ks, shininess, input, (Rect) null);
    }

    @ApiStatus.Internal
    public ImageFilter(long ptr) {
        super(ptr);
    }
    
    public static native long _nMakeArithmetic(float k1, float k2, float k3, float k4, boolean enforcePMColor, long bg, long fg, Rect crop);
    public static native long _nMakeBlend(int blendMode, long bg, long fg, Rect crop);
    public static native long _nMakeBlur(float sigmaX, float sigmaY, int tileMode, long input, Rect crop);
    public static native long _nMakeColorFilter(long colorFilterPtr, long input, Rect crop);
    public static native long _nMakeCompose(long outer, long inner);
    public static native long _nMakeCrop(float l, float t, float r, float b, int tileMode, long input);
    public static native long _nMakeDisplacementMap(int xChan, int yChan, float scale, long displacement, long color, Rect crop);
    public static native long _nMakeDropShadow(float dx, float dy, float sigmaX, float sigmaY, int color, long input, Rect crop);
    public static native long _nMakeDropShadowOnly(float dx, float dy, float sigmaX, float sigmaY, int color, long input, Rect crop);
    public static native long _nMakeEmpty();
    public static native long _nMakeImage(long image, float l0, float t0, float r0, float b0, float l1, float t1, float r1, float b1, long samplingMode);
    public static native long _nMakeMagnifier(float l, float t, float r, float b, float zoomAmount, float inset, long samplingMode, long input, Rect crop);
    public static native long _nMakeMatrixConvolution(int kernelW, int kernelH, float[] kernel, float gain, float bias, int offsetX, int offsetY, int tileMode, boolean convolveAlpha, long input, Rect crop);
    public static native long _nMakeMatrixTransform(float[] matrix, long samplingMode, long input);
    public static native long _nMakeMerge(long[] filters, Rect crop);
    public static native long _nMakeOffset(float dx, float dy, long input, Rect crop);
    public static native long _nMakePicture(long picture, float l, float t, float r, float b);
    public static native long _nMakePicture(long picture);
    public static native long _nMakeShader(long shader, boolean dither, Rect crop);
    public static native long _nMakeTile(float l0, float t0, float r0, float b0, float l1, float t1, float r1, float b1, long input);
    public static native long _nMakeDilate(float rx, float ry, long input, Rect crop);
    public static native long _nMakeErode(float rx, float ry, long input, Rect crop);
    public static native long _nMakeDistantLitDiffuse(float x, float y, float z, int lightColor, float surfaceScale, float kd, long input, Rect crop);
    public static native long _nMakePointLitDiffuse(float x, float y, float z, int lightColor, float surfaceScale, float kd, long input, Rect crop);
    public static native long _nMakeSpotLitDiffuse(float x0, float y0, float z0, float x1, float y1, float z1, float falloffExponent, float cutoffAngle, int lightColor, float surfaceScale, float kd, long input, Rect crop);
    public static native long _nMakeDistantLitSpecular(float x, float y, float z, int lightColor, float surfaceScale, float ks, float shininess, long input, Rect crop);
    public static native long _nMakePointLitSpecular(float x, float y, float z, int lightColor, float surfaceScale, float ks, float shininess, long input, Rect crop);
    public static native long _nMakeSpotLitSpecular(float x0, float y0, float z0, float x1, float y1, float z1, float falloffExponent, float cutoffAngle, int lightColor, float surfaceScale, float ks, float shininess, long input, Rect crop);
}