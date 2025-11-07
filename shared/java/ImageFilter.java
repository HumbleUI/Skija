package io.github.humbleui.skija;

import org.jetbrains.annotations.*;
import io.github.humbleui.skija.impl.*;
import io.github.humbleui.types.*;

import java.util.Arrays;

public class ImageFilter extends RefCnt implements Flattenable {
    static { Library.staticLoad(); }

    /**
     * Create a filter that implements a custom blend mode. Each output pixel is the result of
     * combining the corresponding background and foreground pixels using the 4 coefficients:
     *
     *     k1 * foreground * background + k2 * foreground + k3 * background + k4
     *
     * @param k1              Coefficient for multiplied foreground and background
     * @param k2              Coefficient for foreground
     * @param k3              Coefficient for background
     * @param k4              Constant
     * @param enforcePMColor  If true, the RGB channels will be clamped to the calculated alpha
     * @param bg              The background content, using the source bitmap when this is null
     * @param fg              The foreground content, using the source bitmap when this is null
     * @param crop            Optional rectangle that crops the inputs and output
     * @return                filter that applies the arithmetic blend
     */
    @NotNull @Contract("_, _, _, _, _, _, _, _ -> new")
    public static ImageFilter makeArithmetic(float k1, float k2, float k3, float k4, boolean enforcePMColor, @Nullable ImageFilter bg, @Nullable ImageFilter fg, @Nullable Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeArithmetic(k1, k2, k3, k4, enforcePMColor, Native.getPtr(bg), Native.getPtr(fg), crop));
        } finally {
            ReferenceUtil.reachabilityFence(bg);
            ReferenceUtil.reachabilityFence(fg);
        }
    }

    @NotNull @Contract("_, _, _, _, _, _, _, _ -> new")
    public static ImageFilter makeArithmetic(float k1, float k2, float k3, float k4, boolean enforcePMColor, @Nullable ImageFilter bg, @Nullable ImageFilter fg, @Nullable IRect crop) {
        return makeArithmetic(k1, k2, k3, k4, enforcePMColor, bg, fg, crop == null ? null : crop.toRect());
    }

    @NotNull @Contract("_, _, _, _, _, _, _ -> new")
    public static ImageFilter makeArithmetic(float k1, float k2, float k3, float k4, boolean enforcePMColor, @Nullable ImageFilter bg, @Nullable ImageFilter fg) {
        return makeArithmetic(k1, k2, k3, k4, enforcePMColor, bg, fg, (Rect) null);
    }

    /**
     * This filter takes a BlendMode and uses it to composite the two filters together.
     *
     * @param blendMode  The blend mode that defines the compositing operation
     * @param bg         The Dst pixels used in blending, if null the source bitmap is used
     * @param fg         The Src pixels used in blending, if null the source bitmap is used
     * @param crop       Optional rectangle to crop input and output
     * @return           filter that blends the two inputs
     */
    @NotNull @Contract("_, _, _, _ -> new")
    public static ImageFilter makeBlend(@NotNull BlendMode blendMode, @Nullable ImageFilter bg, @Nullable ImageFilter fg, @Nullable Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeBlend(blendMode.ordinal(), Native.getPtr(bg), Native.getPtr(fg), crop));
        } finally {
            ReferenceUtil.reachabilityFence(bg);
            ReferenceUtil.reachabilityFence(fg);
        }
    }

    @NotNull @Contract("_, _, _, _ -> new")
    public static ImageFilter makeBlend(@NotNull BlendMode blendMode, @Nullable ImageFilter bg, @Nullable ImageFilter fg, @Nullable IRect crop) {
        return makeBlend(blendMode, bg, fg, crop == null ? null : crop.toRect());
    }

    @NotNull @Contract("_, _, _ -> new")
    public static ImageFilter makeBlend(@NotNull BlendMode blendMode, @Nullable ImageFilter bg, @Nullable ImageFilter fg) {
        return makeBlend(blendMode, bg, fg, (Rect) null);
    }

    /**
     * This filter takes a Blender and uses it to composite the two filters together.
     *
     * @param blender  The blender that defines the compositing operation
     * @param bg       The Dst pixels used in blending, if null the source bitmap is used
     * @param fg       The Src pixels used in blending, if null the source bitmap is used
     * @param crop     Optional rectangle to crop input and output
     * @return         filter that blends the two inputs
     */
    @NotNull @Contract("_, _, _, _ -> new")
    public static ImageFilter makeBlend(@NotNull Blender blender, @Nullable ImageFilter bg, @Nullable ImageFilter fg, @Nullable Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeBlendBlender(Native.getPtr(blender), Native.getPtr(bg), Native.getPtr(fg), crop));
        } finally {
            ReferenceUtil.reachabilityFence(blender);
            ReferenceUtil.reachabilityFence(bg);
            ReferenceUtil.reachabilityFence(fg);
        }
    }

    @NotNull @Contract("_, _, _, _ -> new")
    public static ImageFilter makeBlend(@NotNull Blender blender, @Nullable ImageFilter bg, @Nullable ImageFilter fg, @Nullable IRect crop) {
        return makeBlend(blender, bg, fg, crop == null ? null : crop.toRect());
    }

    @NotNull @Contract("_, _, _ -> new")
    public static ImageFilter makeBlend(@NotNull Blender blender, @Nullable ImageFilter bg, @Nullable ImageFilter fg) {
        return makeBlend(blender, bg, fg, (Rect) null);
    }

    /**
     * Create a filter that blurs its input by the separate X and Y sigmas. The provided tile mode
     * is used when the blur kernel goes outside the input image.
     *
     * @param sigmaX  The Gaussian sigma value for blurring along the X axis
     * @param sigmaY  The Gaussian sigma value for blurring along the Y axis
     * @param mode    The tile mode applied at edges
     * @param input   The input filter that is blurred, uses source bitmap if this is null
     * @param crop    Optional rectangle that crops the input and output
     * @return        filter that blurs the input
     */
    @NotNull @Contract("_, _, _, _, _ -> new")
    public static ImageFilter makeBlur(float sigmaX, float sigmaY, @NotNull FilterTileMode mode, @Nullable ImageFilter input, @Nullable Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeBlur(sigmaX, sigmaY, mode.ordinal(), Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    @NotNull @Contract("_, _, _, _, _ -> new")
    public static ImageFilter makeBlur(float sigmaX, float sigmaY, @NotNull FilterTileMode mode, @Nullable ImageFilter input, @Nullable IRect crop) {
        return makeBlur(sigmaX, sigmaY, mode, input, crop == null ? null : crop.toRect());
    }

    @NotNull @Contract("_, _, _ -> new")
    public static ImageFilter makeBlur(float sigmaX, float sigmaY, @NotNull FilterTileMode mode) {
        return makeBlur(sigmaX, sigmaY, mode, null, (Rect) null);
    }

    /**
     * Create a filter that applies the color filter to the input filter results.
     *
     * @param f      The color filter that transforms the input image
     * @param input  The input filter, or uses the source bitmap if this is null
     * @param crop   Optional rectangle that crops the input and output
     * @return       filter that applies the color filter
     */
    @NotNull @Contract("_, _, _ -> new")
    public static ImageFilter makeColorFilter(@NotNull ColorFilter f, @Nullable ImageFilter input, @Nullable Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeColorFilter(Native.getPtr(f), Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(f);
            ReferenceUtil.reachabilityFence(input);
        }
    }

    @NotNull @Contract("_, _, _ -> new")
    public static ImageFilter makeColorFilter(@NotNull ColorFilter f, @Nullable ImageFilter input, @Nullable IRect crop) {
        return makeColorFilter(f, input, crop == null ? null : crop.toRect());
    }

    @NotNull @Contract("_, _ -> new")
    public static ImageFilter makeColorFilter(@NotNull ColorFilter f, @Nullable ImageFilter input) {
        return makeColorFilter(f, input, (Rect) null);
    }

    /**
     * Create a filter that composes 'inner' with 'outer', such that the results of 'inner' are
     * treated as the source bitmap passed to 'outer', i.e. result = outer(inner(source)).
     *
     * @param outer  The outer filter that evaluates the results of inner
     * @param inner  The inner filter that produces the input to outer
     * @return       filter that composes the two filters
     */
    @NotNull @Contract("_, _ -> new")
    public static ImageFilter makeCompose(@NotNull ImageFilter outer, @NotNull ImageFilter inner) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeCompose(Native.getPtr(outer), Native.getPtr(inner)));
        } finally {
            ReferenceUtil.reachabilityFence(outer);
            ReferenceUtil.reachabilityFence(inner);
        }
    }

    /**
     * <p>Create a filter that applies a crop to the result of the input filter.
     * Pixels within the crop rectangle are unmodified from what the input
     * produced. Pixels outside of crop match the provided
     * {@link FilterTileMode} (defaulting to {@link FilterTileMode#DECAL}).</p>
     *
     * <p>NOTE: The optional Rect crop argument for many of the factory methods
     * is equivalent to creating the filter without a crop and then wrapping it
     * in makeCrop(rect, DECAL). Explicitly adding Crop filters lets you control
     * their tiling and use different geometry for the input and the output of
     * another filter.</p>
     *
     * @param rect      The cropping geometry
     * @param tileMode  The tile mode applied to pixels outside of the crop
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
     * Create a filter that applies a crop to the result of the input filter. Pixels within the
     * crop rectangle are unmodified from what the input produced. Pixels outside of crop are transparent.
     *
     * @param rect   The cropping geometry
     * @param input  The input filter that is cropped, uses source image if this is null
     * @return       filter that crops its input
     */
    @NotNull @Contract("_, _ -> new")
    public static ImageFilter makeCrop(@NotNull Rect rect, @Nullable ImageFilter input) {
        return makeCrop(rect, FilterTileMode.DECAL, input);
    }

    /**
     * Create a filter that moves each pixel in its color input based on an (x,y) vector encoded
     * in its displacement input filter. Two color components of the displacement image are
     * mapped into a vector as scale * (color[xChannel], color[yChannel]), where the channel
     * selectors are one of R, G, B, or A.
     *
     * @param x             RGBA channel that encodes the x displacement per pixel
     * @param y             RGBA channel that encodes the y displacement per pixel
     * @param scale         Scale applied to displacement extracted from image
     * @param displacement  The filter defining the displacement image, or null to use source
     * @param color         The filter providing the color pixels to be displaced. If null, it will use the source
     * @param crop          Optional rectangle that crops the color input and output
     * @return              filter that displaces the color input
     */
    @NotNull @Contract("_, _, _, _, _, _ -> new")
    public static ImageFilter makeDisplacementMap(@NotNull ColorChannel x, @NotNull ColorChannel y, float scale, @Nullable ImageFilter displacement, @Nullable ImageFilter color, @Nullable Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeDisplacementMap(x.ordinal(), y.ordinal(), scale, Native.getPtr(displacement), Native.getPtr(color), crop));
        } finally {
            ReferenceUtil.reachabilityFence(displacement);
            ReferenceUtil.reachabilityFence(color);
        }
    }

    @NotNull @Contract("_, _, _, _, _, _ -> new")
    public static ImageFilter makeDisplacementMap(@NotNull ColorChannel x, @NotNull ColorChannel y, float scale, @Nullable ImageFilter displacement, @Nullable ImageFilter color, @Nullable IRect crop) {
        return makeDisplacementMap(x, y, scale, displacement, color, crop == null ? null : crop.toRect());
    }

    @NotNull @Contract("_, _, _, _, _ -> new")
    public static ImageFilter makeDisplacementMap(@NotNull ColorChannel x, @NotNull ColorChannel y, float scale, @Nullable ImageFilter displacement, @Nullable ImageFilter color) {
        return makeDisplacementMap(x, y, scale, displacement, color, (Rect) null);
    }

    /**
     * Create a filter that draws a drop shadow under the input content. This filter produces an
     * image that includes the inputs' content.
     *
     * @param dx          X offset of the shadow
     * @param dy          Y offset of the shadow
     * @param sigmaX      Blur radius for the shadow, along the X axis
     * @param sigmaY      Blur radius for the shadow, along the Y axis
     * @param color       Color of the drop shadow
     * @param colorSpace  The color space of the drop shadow color
     * @param input       The input filter, or will use the source bitmap if this is null
     * @param crop        Optional rectangle that crops the input and output
     * @return            filter that draws a drop shadow
     */
    @NotNull @Contract("_, _, _, _, _, _, _, _ -> new")
    public static ImageFilter makeDropShadow(float dx, float dy, float sigmaX, float sigmaY, @NotNull Color4f color, @Nullable ColorSpace colorSpace, @Nullable ImageFilter input, @Nullable Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeDropShadowCS(dx, dy, sigmaX, sigmaY, color.getR(), color.getG(), color.getB(), color.getA(), Native.getPtr(colorSpace), Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(colorSpace);
            ReferenceUtil.reachabilityFence(input);
        }
    }

    @NotNull @Contract("_, _, _, _, _, _, _, _ -> new")
    public static ImageFilter makeDropShadow(float dx, float dy, float sigmaX, float sigmaY, @NotNull Color4f color, @Nullable ColorSpace colorSpace, @Nullable ImageFilter input, @Nullable IRect crop) {
        return makeDropShadow(dx, dy, sigmaX, sigmaY, color, colorSpace, input, crop == null ? null : crop.toRect());
    }

    @NotNull @Contract("_, _, _, _, _, _, _ -> new")
    public static ImageFilter makeDropShadow(float dx, float dy, float sigmaX, float sigmaY, @NotNull Color4f color, @Nullable ColorSpace colorSpace, @Nullable ImageFilter input) {
        return makeDropShadow(dx, dy, sigmaX, sigmaY, color, colorSpace, input, (Rect) null);
    }

    /**
     * Create a filter that draws a drop shadow under the input content. This filter produces an
     * image that includes the inputs' content.
     *
     * @param dx      X offset of the shadow
     * @param dy      Y offset of the shadow
     * @param sigmaX  Blur radius for the shadow, along the X axis
     * @param sigmaY  Blur radius for the shadow, along the Y axis
     * @param color   Color of the drop shadow
     * @param input   The input filter, or will use the source bitmap if this is null
     * @param crop    Optional rectangle that crops the input and output
     * @return        filter that draws a drop shadow
     */
    @NotNull @Contract("_, _, _, _, _, _, _ -> new")
    public static ImageFilter makeDropShadow(float dx, float dy, float sigmaX, float sigmaY, int color, @Nullable ImageFilter input, @Nullable Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeDropShadow(dx, dy, sigmaX, sigmaY, color, Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    @NotNull @Contract("_, _, _, _, _, _, _ -> new")
    public static ImageFilter makeDropShadow(float dx, float dy, float sigmaX, float sigmaY, int color, @Nullable ImageFilter input, @Nullable IRect crop) {
        return makeDropShadow(dx, dy, sigmaX, sigmaY, color, input, crop == null ? null : crop.toRect());
    }

    @NotNull @Contract("_, _, _, _, _, _ -> new")
    public static ImageFilter makeDropShadow(float dx, float dy, float sigmaX, float sigmaY, int color, @Nullable ImageFilter input) {
        return makeDropShadow(dx, dy, sigmaX, sigmaY, color, input, (Rect) null);
    }

    @NotNull @Contract("_, _, _, _, _ -> new")
    public static ImageFilter makeDropShadow(float dx, float dy, float sigmaX, float sigmaY, int color) {
        return makeDropShadow(dx, dy, sigmaX, sigmaY, color, null, (Rect) null);
    }

    /**
     * Create a filter that renders a drop shadow, in exactly the same manner as makeDropShadow,
     * except that the resulting image does not include the input content. This allows the shadow
     * and input to be composed by a filter DAG in a more flexible manner.
     *
     * @param dx          The X offset of the shadow
     * @param dy          The Y offset of the shadow
     * @param sigmaX      The blur radius for the shadow, along the X axis
     * @param sigmaY      The blur radius for the shadow, along the Y axis
     * @param color       The color of the drop shadow
     * @param colorSpace  The color space of the drop shadow color
     * @param input       The input filter, or will use the source bitmap if this is null
     * @param crop        Optional rectangle that crops the input and output
     * @return            filter that renders only the drop shadow
     */
    @NotNull @Contract("_, _, _, _, _, _, _, _ -> new")
    public static ImageFilter makeDropShadowOnly(float dx, float dy, float sigmaX, float sigmaY, @NotNull Color4f color, @Nullable ColorSpace colorSpace, @Nullable ImageFilter input, @Nullable Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeDropShadowOnlyCS(dx, dy, sigmaX, sigmaY, color.getR(), color.getG(), color.getB(), color.getA(), Native.getPtr(colorSpace), Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(colorSpace);
            ReferenceUtil.reachabilityFence(input);
        }
    }

    @NotNull @Contract("_, _, _, _, _, _, _, _ -> new")
    public static ImageFilter makeDropShadowOnly(float dx, float dy, float sigmaX, float sigmaY, @NotNull Color4f color, @Nullable ColorSpace colorSpace, @Nullable ImageFilter input, @Nullable IRect crop) {
        return makeDropShadowOnly(dx, dy, sigmaX, sigmaY, color, colorSpace, input, crop == null ? null : crop.toRect());
    }

    @NotNull @Contract("_, _, _, _, _, _, _ -> new")
    public static ImageFilter makeDropShadowOnly(float dx, float dy, float sigmaX, float sigmaY, @NotNull Color4f color, @Nullable ColorSpace colorSpace, @Nullable ImageFilter input) {
        return makeDropShadowOnly(dx, dy, sigmaX, sigmaY, color, colorSpace, input, (Rect) null);
    }

    /**
     * Create a filter that renders a drop shadow, in exactly the same manner as makeDropShadow,
     * except that the resulting image does not include the input content. This allows the shadow
     * and input to be composed by a filter DAG in a more flexible manner.
     *
     * @param dx      The X offset of the shadow
     * @param dy      The Y offset of the shadow
     * @param sigmaX  The blur radius for the shadow, along the X axis
     * @param sigmaY  The blur radius for the shadow, along the Y axis
     * @param color   The color of the drop shadow
     * @param input   The input filter, or will use the source bitmap if this is null
     * @param crop    Optional rectangle that crops the input and output
     * @return        filter that renders only the drop shadow
     */
    @NotNull @Contract("_, _, _, _, _, _, _ -> new")
    public static ImageFilter makeDropShadowOnly(float dx, float dy, float sigmaX, float sigmaY, int color, @Nullable ImageFilter input, @Nullable Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeDropShadowOnly(dx, dy, sigmaX, sigmaY, color, Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    @NotNull @Contract("_, _, _, _, _, _, _ -> new")
    public static ImageFilter makeDropShadowOnly(float dx, float dy, float sigmaX, float sigmaY, int color, @Nullable ImageFilter input, @Nullable IRect crop) {
        return makeDropShadowOnly(dx, dy, sigmaX, sigmaY, color, input, crop == null ? null : crop.toRect());
    }

    @NotNull @Contract("_, _, _, _, _ -> new")
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

    /**
     * Create a filter that draws the 'src' portion of image into 'dst' using the given
     * filter quality. The returned image filter evaluates to transparent black if 'image' is null.
     *
     * @param image   The image that is output by the filter, subset by 'src'
     * @param src     The source pixels sampled into 'dst'
     * @param dst     The local rectangle to draw the image into
     * @param mode    The sampling to use when drawing the image
     * @return        filter that draws the image
     */
    @NotNull @Contract("_, _, _, _ -> new")
    public static ImageFilter makeImage(@Nullable Image image, @NotNull Rect src, @NotNull Rect dst, @NotNull SamplingMode mode) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeImage(Native.getPtr(image), src._left, src._top, src._right, src._bottom, dst._left, dst._top, dst._right, dst._bottom, mode._pack()));
        } finally {
            ReferenceUtil.reachabilityFence(image);
        }
    }

    /**
     * Create a filter that draws the image using the given sampling.
     *
     * @param image  The image that is output by the filter
     * @return       filter that draws the image
     */
    @NotNull @Contract("_ -> new")
    public static ImageFilter makeImage(@Nullable Image image) {
        Rect r = Rect.makeWH(image.getWidth(), image.getHeight());
        return makeImage(image, r, r, SamplingMode.DEFAULT);
    }

    /**
     * Create a filter that fills 'lensBounds' with a magnification of the input.
     *
     * @param r           The outer bounds of the magnifier effect
     * @param zoomAmount  The amount of magnification applied to the input image
     * @param inset       The size or width of the fish-eye distortion around the magnified content
     * @param mode        The SamplingOptions applied to the input image when magnified
     * @param input       The input filter that is magnified; if null the source bitmap is used
     * @param crop        Optional rectangle that crops the input and output
     * @return            filter that magnifies the input
     */
    @NotNull @Contract("_, _, _, _, _, _ -> new")
    public static ImageFilter makeMagnifier(@NotNull Rect r, float zoomAmount, float inset, @NotNull SamplingMode mode, @Nullable ImageFilter input, @Nullable Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeMagnifier(r._left, r._top, r._right, r._bottom, zoomAmount, inset, mode._pack(), Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    @NotNull @Contract("_, _, _, _, _, _ -> new")
    public static ImageFilter makeMagnifier(@NotNull Rect r, float zoomAmount, float inset, @NotNull SamplingMode mode, @Nullable ImageFilter input, @Nullable IRect crop) {
        return makeMagnifier(r, zoomAmount, inset, mode, input, crop == null ? null : crop.toRect());
    }

    @NotNull @Contract("_, _, _, _, _ -> new")
    public static ImageFilter makeMagnifier(@NotNull Rect r, float zoomAmount, float inset, @NotNull SamplingMode mode, @Nullable ImageFilter input) {
        return makeMagnifier(r, zoomAmount, inset, mode, input, (Rect) null);
    }

    /**
     * Create a filter that applies an NxM image processing kernel to the input image. This can be
     * used to produce effects such as sharpening, blurring, edge detection, etc.
     *
     * @param kernelW       The kernel size in pixels, in each dimension (N by M)
     * @param kernelH       The kernel height in pixels
     * @param kernel        The image processing kernel. Must contain N * M elements, in row order
     * @param gain          A scale factor applied to each pixel after convolution
     * @param bias          A bias factor added to each pixel after convolution
     * @param offsetX       An offset applied to each pixel coordinate before convolution
     * @param offsetY       An offset applied to each pixel coordinate before convolution
     * @param tileMode      How accesses outside the image are treated
     * @param convolveAlpha If true, all channels are convolved. If false, only RGB channels are convolved
     * @param input         The input image filter, if null the source bitmap is used instead
     * @param crop          Optional rectangle to which the output processing will be limited
     * @return              filter that applies matrix convolution
     */
    @NotNull @Contract("_, _, _, _, _, _, _, _, _, _, _ -> new")
    public static ImageFilter makeMatrixConvolution(int kernelW, int kernelH, @NotNull float[] kernel, float gain, float bias, int offsetX, int offsetY, @NotNull FilterTileMode tileMode, boolean convolveAlpha, @Nullable ImageFilter input, @Nullable Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeMatrixConvolution(kernelW, kernelH, kernel, gain, bias, offsetX, offsetY, tileMode.ordinal(), convolveAlpha, Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    @NotNull @Contract("_, _, _, _, _, _, _, _, _, _, _ -> new")
    public static ImageFilter makeMatrixConvolution(int kernelW, int kernelH, @NotNull float[] kernel, float gain, float bias, int offsetX, int offsetY, @NotNull FilterTileMode tileMode, boolean convolveAlpha, @Nullable ImageFilter input, @Nullable IRect crop) {
        return makeMatrixConvolution(kernelW, kernelH, kernel, gain, bias, offsetX, offsetY, tileMode, convolveAlpha, input, crop == null ? null : crop.toRect());
    }

    @NotNull @Contract("_, _, _, _, _, _, _, _, _, _ -> new")
    public static ImageFilter makeMatrixConvolution(int kernelW, int kernelH, @NotNull float[] kernel, float gain, float bias, int offsetX, int offsetY, @NotNull FilterTileMode tileMode, boolean convolveAlpha, @Nullable ImageFilter input) {
        return makeMatrixConvolution(kernelW, kernelH, kernel, gain, bias, offsetX, offsetY, tileMode, convolveAlpha, input, (Rect) null);
    }

    /**
     * Create a filter that transforms the input image by 'matrix'. This matrix transforms the
     * local space, which means it effectively happens prior to any transformation coming from the
     * Canvas initiating the filtering.
     *
     * @param matrix  The matrix to apply to the original content
     * @param mode    How the image will be sampled when it is transformed
     * @param input   The image filter to transform, or null to use the source image
     * @return        filter that transforms the input
     */
    @NotNull @Contract("_, _, _ -> new")
    public static ImageFilter makeMatrixTransform(@NotNull Matrix33 matrix, @NotNull SamplingMode mode, @Nullable ImageFilter input) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeMatrixTransform(matrix.getMat(), mode._pack(), Native.getPtr(input)));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    /**
     * Create a filter that merges the filter array together by drawing their results in order
     * with src-over blending.
     *
     * @param filters  The input filter array to merge. Any null filter pointers will use the source bitmap instead
     * @param crop     Optional rectangle that crops all input filters and the output
     * @return         filter that merges the inputs
     */
    @NotNull @Contract("_, _ -> new")
    public static ImageFilter makeMerge(@NotNull ImageFilter[] filters, @Nullable Rect crop) {
        try {
            Stats.onNativeCall();
            long[] filterPtrs = new long[filters.length];
            Arrays.setAll(filterPtrs, i -> Native.getPtr(filters[i]));
            return new ImageFilter(_nMakeMerge(filterPtrs, crop));
        } finally {
            ReferenceUtil.reachabilityFence(filters);
        }
    }

    @NotNull @Contract("_, _ -> new")
    public static ImageFilter makeMerge(@NotNull ImageFilter[] filters, @Nullable IRect crop) {
        return makeMerge(filters, crop == null ? null : crop.toRect());
    }

    @NotNull @Contract("_ -> new")
    public static ImageFilter makeMerge(@NotNull ImageFilter[] filters) {
        return makeMerge(filters, (Rect) null);
    }

    /**
     * Create a filter that offsets the input filter by the given vector.
     *
     * @param dx     The x offset in local space that the image is shifted
     * @param dy     The y offset in local space that the image is shifted
     * @param input  The input that will be moved, if null the source bitmap is used instead
     * @param crop   Optional rectangle to crop the input and output
     * @return       filter that offsets the input
     */
    @NotNull @Contract("_, _, _, _ -> new")
    public static ImageFilter makeOffset(float dx, float dy, @Nullable ImageFilter input, @Nullable Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeOffset(dx, dy, Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    @NotNull @Contract("_, _, _, _ -> new")
    public static ImageFilter makeOffset(float dx, float dy, @Nullable ImageFilter input, @Nullable IRect crop) {
        return makeOffset(dx, dy, input, crop == null ? null : crop.toRect());
    }

    @NotNull @Contract("_, _, _ -> new")
    public static ImageFilter makeOffset(float dx, float dy, @Nullable ImageFilter input) {
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

    /**
     * Create a filter that produces a runtime shader effect. The runtime shader is evaluated per-pixel
     * and the result fills the output. The childShaderName is used to bind the input filter to a shader
     * child in the builder, or uses the source bitmap when input is null.
     *
     * @param builder          The RuntimeEffectBuilder that defines the shader
     * @param childShaderName  The name of the child shader in the builder to bind the input to
     * @param input            The input filter, or uses the source bitmap when null
     * @return                 filter that applies the runtime shader
     */
    @NotNull @Contract("_, _, _ -> new")
    public static ImageFilter makeRuntimeShader(@NotNull RuntimeEffectBuilder builder, @Nullable String childShaderName, @Nullable ImageFilter input) {
        return makeRuntimeShader(builder, 0.0f, childShaderName, input);
    }

    /**
     * Create a filter that produces a runtime shader effect with a sample radius. The runtime shader
     * is evaluated per-pixel and the result fills the output. The sampleRadius defines how far
     * from the output pixel coordinate the child shader may be sampled.
     *
     * @param builder          The RuntimeEffectBuilder that defines the shader
     * @param sampleRadius     Maximum distance (in pixels) that the child shader may be sampled from the output coordinate
     * @param childShaderName  The name of the child shader in the builder to bind the input to
     * @param input            The input filter, or uses the source bitmap when null
     * @return                 filter that applies the runtime shader
     */
    @NotNull @Contract("_, _, _, _ -> new")
    public static ImageFilter makeRuntimeShader(@NotNull RuntimeEffectBuilder builder, float sampleRadius, @Nullable String childShaderName, @Nullable ImageFilter input) {
        if (childShaderName == null || "".equals(childShaderName)) {
            RuntimeEffectChildInfo[] children = builder._effect.getChildren();
            assert children.length == 1 : "Expected 1 child, got " + children.length;
            childShaderName = children[0]._name;
        }
        return makeRuntimeShader(builder, sampleRadius, new String[] { childShaderName }, new ImageFilter[] { input });
    }

    /**
     * Create a filter that produces a runtime shader effect with multiple child shaders. Each child
     * shader name is bound to the corresponding input filter, or uses the source bitmap when the input is null.
     *
     * @param builder           The RuntimeEffectBuilder that defines the shader
     * @param childShaderNames  The names of the child shaders in the builder to bind the inputs to
     * @param inputs            The input filters corresponding to each child shader name
     * @return                  filter that applies the runtime shader
     */
    @NotNull @Contract("_, _, _ -> new")
    public static ImageFilter makeRuntimeShader(@NotNull RuntimeEffectBuilder builder, @NotNull String[] childShaderNames, @Nullable ImageFilter[] inputs) {
        return makeRuntimeShader(builder, 0.0f, childShaderNames, inputs);
    }

    /**
     * Create a filter that produces a runtime shader effect with multiple child shaders and a maximum
     * sample radius. The maxSampleRadius defines how far from the output pixel coordinate any child
     * shader may be sampled.
     *
     * @param builder           The RuntimeEffectBuilder that defines the shader
     * @param maxSampleRadius   Maximum distance (in pixels) that any child shader may be sampled from the output coordinate
     * @param childShaderNames  The names of the child shaders in the builder to bind the inputs to
     * @param inputs            The input filters corresponding to each child shader name
     * @return                  filter that applies the runtime shader
     */
    @NotNull @Contract("_, _, _, _ -> new")
    public static ImageFilter makeRuntimeShader(@NotNull RuntimeEffectBuilder builder, float maxSampleRadius, @NotNull String[] childShaderNames, @Nullable ImageFilter[] inputs) {
        try {
            Stats.onNativeCall();
            long[] inputPtrs = new long[inputs.length];
            for (int i = 0; i < inputs.length; i++) {
                inputPtrs[i] = Native.getPtr(inputs[i]);
            }
            return new ImageFilter(_nMakeRuntimeShader(Native.getPtr(builder), maxSampleRadius, childShaderNames, inputPtrs));
        } finally {
            ReferenceUtil.reachabilityFence(builder);
            ReferenceUtil.reachabilityFence(inputs);
            for (ImageFilter input: inputs) {
                ReferenceUtil.reachabilityFence(input);
            }
        }
    }

    /**
     * <p>Create a filter that fills the output with the per-pixel evaluation of
     * the shader.</p>
     *
     * <p>The shader is defined in the image filter's local coordinate system,
     * so will automatically be affected by {@link Canvas}' transform.</p>
     *
     * <p>Like {@link makeImage} and {@link makePicture}, this is a leaf filter
     * that can be used to introduce inputs to a complex filter graph, but
     * should generally be combined with a filter that as at least one null
     * input to use the implicit source image.</p>
     *
     * <p>Returns an image filter that evaluates to transparent black if
     * 'shader' is null.</p>
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

    @NotNull @Contract("_, _, _ -> new")
    public static ImageFilter makeShader(@NotNull Shader shader, boolean dither, @Nullable IRect crop) {
        return makeShader(shader, dither, crop == null ? (Rect) null : crop.toRect());
    }

    @NotNull @Contract("_, _ -> new")
    public static ImageFilter makeShader(@NotNull Shader shader, @Nullable Rect crop) {
        return makeShader(shader, false, crop);
    }

    @NotNull @Contract("_, _ -> new")
    public static ImageFilter makeShader(@NotNull Shader shader, @Nullable IRect crop) {
        return makeShader(shader, false, crop == null ? (Rect) null : crop.toRect());
    }

    @NotNull @Contract("_ -> new")
    public static ImageFilter makeShader(@NotNull Shader shader) {
        return makeShader(shader, false, (Rect) null);
    }

    /**
     * Create a tile image filter.
     *
     * @param src    Defines the pixels to tile
     * @param dst    Defines the pixel region that the tiles will be drawn to
     * @param input  The input that will be tiled, if null the source bitmap is used instead
     * @return       filter that tiles the input
     */
    @NotNull @Contract("_, _, _ -> new")
    public static ImageFilter makeTile(@NotNull Rect src, @NotNull Rect dst, @Nullable ImageFilter input) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeTile(src._left, src._top, src._right, src._bottom, dst._left, dst._top, dst._right, dst._bottom, Native.getPtr(input)));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    /**
     * Create a filter that dilates each input pixel's channel values to the max value within the
     * given radii along the x and y axes.
     *
     * @param rx     The distance to dilate along the x axis to either side of each pixel
     * @param ry     The distance to dilate along the y axis to either side of each pixel
     * @param input  The image filter that is dilated, using source bitmap if this is null
     * @param crop   Optional rectangle that crops the input and output
     * @return       filter that dilates the input
     */
    @NotNull @Contract("_, _, _, _ -> new")
    public static ImageFilter makeDilate(float rx, float ry, @Nullable ImageFilter input, @Nullable Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeDilate(rx, ry, Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    @NotNull @Contract("_, _, _, _ -> new")
    public static ImageFilter makeDilate(float rx, float ry, @Nullable ImageFilter input, @Nullable IRect crop) {
        return makeDilate(rx, ry, input, crop == null ? null : crop.toRect());
    }

    @NotNull @Contract("_, _, _ -> new")
    public static ImageFilter makeDilate(float rx, float ry, @Nullable ImageFilter input) {
        return makeDilate(rx, ry, input, (Rect) null);
    }

    /**
     * Create a filter that erodes each input pixel's channel values to the minimum channel value
     * within the given radii along the x and y axes.
     *
     * @param rx     The distance to erode along the x axis to either side of each pixel
     * @param ry     The distance to erode along the y axis to either side of each pixel
     * @param input  The image filter that is eroded, using source bitmap if this is null
     * @param crop   Optional rectangle that crops the input and output
     * @return       filter that erodes the input
     */
    @NotNull @Contract("_, _, _, _ -> new")
    public static ImageFilter makeErode(float rx, float ry, @Nullable ImageFilter input, @Nullable Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeErode(rx, ry, Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    @NotNull @Contract("_, _, _, _ -> new")
    public static ImageFilter makeErode(float rx, float ry, @Nullable ImageFilter input, @Nullable IRect crop) {
        return makeErode(rx, ry, input, crop == null ? null : crop.toRect());
    }

    @NotNull @Contract("_, _, _ -> new")
    public static ImageFilter makeErode(float rx, float ry, @Nullable ImageFilter input) {
        return makeErode(rx, ry, input, (Rect) null);
    }

    /**
     * Create a filter that calculates the diffuse illumination from a distant light source,
     * interpreting the alpha channel of the input as the height profile of the surface (to
     * approximate normal vectors).
     *
     * @param x             The direction to the distance light (X component)
     * @param y             The direction to the distance light (Y component)
     * @param z             The direction to the distance light (Z component)
     * @param lightColor    The color of the diffuse light source
     * @param surfaceScale  Scale factor to transform from alpha values to physical height
     * @param kd            Diffuse reflectance coefficient
     * @param input         The input filter that defines surface normals (as alpha), or uses the source bitmap when null
     * @param crop          Optional rectangle that crops the input and output
     * @return              filter that applies distant diffuse lighting
     */
    @NotNull @Contract("_, _, _, _, _, _, _, _ -> new")
    public static ImageFilter makeDistantLitDiffuse(float x, float y, float z, int lightColor, float surfaceScale, float kd, @Nullable ImageFilter input, @Nullable Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeDistantLitDiffuse(x, y, z, lightColor, surfaceScale, kd, Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    @NotNull @Contract("_, _, _, _, _, _, _, _ -> new")
    public static ImageFilter makeDistantLitDiffuse(float x, float y, float z, int lightColor, float surfaceScale, float kd, @Nullable ImageFilter input, @Nullable IRect crop) {
        return makeDistantLitDiffuse(x, y, z, lightColor, surfaceScale, kd, input, crop == null ? null : crop.toRect());
    }

    @NotNull @Contract("_, _, _, _, _, _, _ -> new")
    public static ImageFilter makeDistantLitDiffuse(float x, float y, float z, int lightColor, float surfaceScale, float kd, @Nullable ImageFilter input) {
        return makeDistantLitDiffuse(x, y, z, lightColor, surfaceScale, kd, input, (Rect) null);
    }

    /**
     * Create a filter that calculates the diffuse illumination from a point light source, using
     * alpha channel of the input as the height profile of the surface (to approximate normal vectors).
     *
     * @param x             The location of the point light (X component)
     * @param y             The location of the point light (Y component)
     * @param z             The location of the point light (Z component)
     * @param lightColor    The color of the diffuse light source
     * @param surfaceScale  Scale factor to transform from alpha values to physical height
     * @param kd            Diffuse reflectance coefficient
     * @param input         The input filter that defines surface normals (as alpha), or uses the source bitmap when null
     * @param crop          Optional rectangle that crops the input and output
     * @return              filter that applies point diffuse lighting
     */
    @NotNull @Contract("_, _, _, _, _, _, _, _ -> new")
    public static ImageFilter makePointLitDiffuse(float x, float y, float z, int lightColor, float surfaceScale, float kd, @Nullable ImageFilter input, @Nullable Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakePointLitDiffuse(x, y, z, lightColor, surfaceScale, kd, Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    @NotNull @Contract("_, _, _, _, _, _, _, _ -> new")
    public static ImageFilter makePointLitDiffuse(float x, float y, float z, int lightColor, float surfaceScale, float kd, @Nullable ImageFilter input, @Nullable IRect crop) {
        return makePointLitDiffuse(x, y, z, lightColor, surfaceScale, kd, input, crop == null ? null : crop.toRect());
    }

    @NotNull @Contract("_, _, _, _, _, _, _ -> new")
    public static ImageFilter makePointLitDiffuse(float x, float y, float z, int lightColor, float surfaceScale, float kd, @Nullable ImageFilter input) {
        return makePointLitDiffuse(x, y, z, lightColor, surfaceScale, kd, input, (Rect) null);
    }

    /**
     * Create a filter that calculates the diffuse illumination from a spot light source, using
     * alpha channel of the input as the height profile of the surface (to approximate normal vectors).
     * The spot light is restricted to be within 'cutoffAngle' of the vector between the location and target.
     *
     * @param x0               The location of the spot light (X component)
     * @param y0               The location of the spot light (Y component)
     * @param z0               The location of the spot light (Z component)
     * @param x1               The location that the spot light is point towards (X component)
     * @param y1               The location that the spot light is point towards (Y component)
     * @param z1               The location that the spot light is point towards (Z component)
     * @param falloffExponent  Exponential falloff parameter for illumination outside of cutoffAngle
     * @param cutoffAngle      Maximum angle from lighting direction that receives full light
     * @param lightColor       The color of the diffuse light source
     * @param surfaceScale     Scale factor to transform from alpha values to physical height
     * @param kd               Diffuse reflectance coefficient
     * @param input            The input filter that defines surface normals (as alpha), or uses the source bitmap when null
     * @param crop             Optional rectangle that crops the input and output
     * @return                 filter that applies spot diffuse lighting
     */
    @NotNull @Contract("_, _, _, _, _, _, _, _, _, _, _, _, _ -> new")
    public static ImageFilter makeSpotLitDiffuse(float x0, float y0, float z0, float x1, float y1, float z1, float falloffExponent, float cutoffAngle, int lightColor, float surfaceScale, float kd, @Nullable ImageFilter input, @Nullable Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeSpotLitDiffuse(x0, y0, z0, x1, y1, z1, falloffExponent, cutoffAngle, lightColor, surfaceScale, kd, Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    @NotNull @Contract("_, _, _, _, _, _, _, _, _, _, _, _, _ -> new")
    public static ImageFilter makeSpotLitDiffuse(float x0, float y0, float z0, float x1, float y1, float z1, float falloffExponent, float cutoffAngle, int lightColor, float surfaceScale, float kd, @Nullable ImageFilter input, @Nullable IRect crop) {
        return makeSpotLitDiffuse(x0, y0, z0, x1, y1, z1, falloffExponent, cutoffAngle, lightColor, surfaceScale, kd, input, crop == null ? null : crop.toRect());
    }

    @NotNull @Contract("_, _, _, _, _, _, _, _, _, _, _, _ -> new")
    public static ImageFilter makeSpotLitDiffuse(float x0, float y0, float z0, float x1, float y1, float z1, float falloffExponent, float cutoffAngle, int lightColor, float surfaceScale, float kd, @Nullable ImageFilter input) {
        return makeSpotLitDiffuse(x0, y0, z0, x1, y1, z1, falloffExponent, cutoffAngle, lightColor, surfaceScale, kd, input, (Rect) null);
    }

    /**
     * Create a filter that calculates the specular illumination from a distant light source,
     * interpreting the alpha channel of the input as the height profile of the surface (to
     * approximate normal vectors).
     *
     * @param x             The direction to the distance light (X component)
     * @param y             The direction to the distance light (Y component)
     * @param z             The direction to the distance light (Z component)
     * @param lightColor    The color of the specular light source
     * @param surfaceScale  Scale factor to transform from alpha values to physical height
     * @param ks            Specular reflectance coefficient
     * @param shininess     The specular exponent determining how shiny the surface is
     * @param input         The input filter that defines surface normals (as alpha), or uses the source bitmap when null
     * @param crop          Optional rectangle that crops the input and output
     * @return              filter that applies distant specular lighting
     */
    @NotNull @Contract("_, _, _, _, _, _, _, _, _ -> new")
    public static ImageFilter makeDistantLitSpecular(float x, float y, float z, int lightColor, float surfaceScale, float ks, float shininess, @Nullable ImageFilter input, @Nullable Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeDistantLitSpecular(x, y, z, lightColor, surfaceScale, ks, shininess, Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    @NotNull @Contract("_, _, _, _, _, _, _, _, _ -> new")
    public static ImageFilter makeDistantLitSpecular(float x, float y, float z, int lightColor, float surfaceScale, float ks, float shininess, @Nullable ImageFilter input, @Nullable IRect crop) {
        return makeDistantLitSpecular(x, y, z, lightColor, surfaceScale, ks, shininess, input, crop == null ? null : crop.toRect());
    }

    @NotNull @Contract("_, _, _, _, _, _, _, _ -> new")
    public static ImageFilter makeDistantLitSpecular(float x, float y, float z, int lightColor, float surfaceScale, float ks, float shininess, @Nullable ImageFilter input) {
        return makeDistantLitSpecular(x, y, z, lightColor, surfaceScale, ks, shininess, input, (Rect) null);
    }

    /**
     * Create a filter that calculates the specular illumination from a point light source, using
     * alpha channel of the input as the height profile of the surface (to approximate normal vectors).
     *
     * @param x             The location of the point light (X component)
     * @param y             The location of the point light (Y component)
     * @param z             The location of the point light (Z component)
     * @param lightColor    The color of the specular light source
     * @param surfaceScale  Scale factor to transform from alpha values to physical height
     * @param ks            Specular reflectance coefficient
     * @param shininess     The specular exponent determining how shiny the surface is
     * @param input         The input filter that defines surface normals (as alpha), or uses the source bitmap when null
     * @param crop          Optional rectangle that crops the input and output
     * @return              filter that applies point specular lighting
     */
    @NotNull @Contract("_, _, _, _, _, _, _, _, _ -> new")
    public static ImageFilter makePointLitSpecular(float x, float y, float z, int lightColor, float surfaceScale, float ks, float shininess, @Nullable ImageFilter input, @Nullable Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakePointLitSpecular(x, y, z, lightColor, surfaceScale, ks, shininess, Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    @NotNull @Contract("_, _, _, _, _, _, _, _, _ -> new")
    public static ImageFilter makePointLitSpecular(float x, float y, float z, int lightColor, float surfaceScale, float ks, float shininess, @Nullable ImageFilter input, @Nullable IRect crop) {
        return makePointLitSpecular(x, y, z, lightColor, surfaceScale, ks, shininess, input, crop == null ? null : crop.toRect());
    }

    @NotNull @Contract("_, _, _, _, _, _, _, _ -> new")
    public static ImageFilter makePointLitSpecular(float x, float y, float z, int lightColor, float surfaceScale, float ks, float shininess, @Nullable ImageFilter input) {
        return makePointLitSpecular(x, y, z, lightColor, surfaceScale, ks, shininess, input, (Rect) null);
    }

    /**
     * Create a filter that calculates the specular illumination from a spot light source, using
     * alpha channel of the input as the height profile of the surface (to approximate normal vectors).
     * The spot light is restricted to be within 'cutoffAngle' of the vector between the location and target.
     *
     * @param x0               The location of the spot light (X component)
     * @param y0               The location of the spot light (Y component)
     * @param z0               The location of the spot light (Z component)
     * @param x1               The location that the spot light is point towards (X component)
     * @param y1               The location that the spot light is point towards (Y component)
     * @param z1               The location that the spot light is point towards (Z component)
     * @param falloffExponent  Exponential falloff parameter for illumination outside of cutoffAngle
     * @param cutoffAngle      Maximum angle from lighting direction that receives full light
     * @param lightColor       The color of the specular light source
     * @param surfaceScale     Scale factor to transform from alpha values to physical height
     * @param ks               Specular reflectance coefficient
     * @param shininess        The specular exponent determining how shiny the surface is
     * @param input            The input filter that defines surface normals (as alpha), or uses the source bitmap when null
     * @param crop             Optional rectangle that crops the input and output
     * @return                 filter that applies spot specular lighting
     */
    @NotNull @Contract("_, _, _, _, _, _, _, _, _, _, _, _, _, _ -> new")
    public static ImageFilter makeSpotLitSpecular(float x0, float y0, float z0, float x1, float y1, float z1, float falloffExponent, float cutoffAngle, int lightColor, float surfaceScale, float ks, float shininess, @Nullable ImageFilter input, @Nullable Rect crop) {
        try {
            Stats.onNativeCall();
            return new ImageFilter(_nMakeSpotLitSpecular(x0, y0, z0, x1, y1, z1, falloffExponent, cutoffAngle, lightColor, surfaceScale, ks, shininess, Native.getPtr(input), crop));
        } finally {
            ReferenceUtil.reachabilityFence(input);
        }
    }

    @NotNull @Contract("_, _, _, _, _, _, _, _, _, _, _, _, _, _ -> new")
    public static ImageFilter makeSpotLitSpecular(float x0, float y0, float z0, float x1, float y1, float z1, float falloffExponent, float cutoffAngle, int lightColor, float surfaceScale, float ks, float shininess, @Nullable ImageFilter input, @Nullable IRect crop) {
        return makeSpotLitSpecular(x0, y0, z0, x1, y1, z1, falloffExponent, cutoffAngle, lightColor, surfaceScale, ks, shininess, input, crop == null ? null : crop.toRect());
    }

    @NotNull @Contract("_, _, _, _, _, _, _, _, _, _, _, _, _ -> new")
    public static ImageFilter makeSpotLitSpecular(float x0, float y0, float z0, float x1, float y1, float z1, float falloffAngle, float cutoffAngle, int lightColor, float surfaceScale, float ks, float shininess, @Nullable ImageFilter input) {
        return makeSpotLitSpecular(x0, y0, z0, x1, y1, z1, falloffAngle, cutoffAngle, lightColor, surfaceScale, ks, shininess, input, (Rect) null);
    }

    @ApiStatus.Internal
    public ImageFilter(long ptr) {
        super(ptr);
    }
    
    public static native long _nMakeArithmetic(float k1, float k2, float k3, float k4, boolean enforcePMColor, long bg, long fg, Rect crop);
    public static native long _nMakeBlend(int blendMode, long bg, long fg, Rect crop);
    public static native long _nMakeBlendBlender(long blenderPtr, long bg, long fg, Rect crop);
    public static native long _nMakeBlur(float sigmaX, float sigmaY, int tileMode, long input, Rect crop);
    public static native long _nMakeColorFilter(long colorFilterPtr, long input, Rect crop);
    public static native long _nMakeCompose(long outer, long inner);
    public static native long _nMakeCrop(float l, float t, float r, float b, int tileMode, long input);
    public static native long _nMakeDisplacementMap(int xChan, int yChan, float scale, long displacement, long color, Rect crop);
    public static native long _nMakeDropShadowCS(float dx, float dy, float sigmaX, float sigmaY, float r, float g, float b, float a, long colorSpace, long input, Rect crop);
    public static native long _nMakeDropShadow(float dx, float dy, float sigmaX, float sigmaY, int color, long input, Rect crop);
    public static native long _nMakeDropShadowOnlyCS(float dx, float dy, float sigmaX, float sigmaY, float r, float g, float b, float a, long colorSpace, long input, Rect crop);
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
    public static native long _nMakeRuntimeShader(long builderPtr, float maxSampleRadius, String[] childShaderNames, long[] inputs);
}