package io.github.humbleui.skija;

import org.jetbrains.annotations.*;
import io.github.humbleui.skija.impl.*;
import io.github.humbleui.types.*;

/**
 * <p>Path contain geometry. Path may be empty, or contain one or more verbs that
 * outline a figure. Path always starts with a move verb to a Cartesian coordinate,
 * and may be followed by additional verbs that add lines or curves.</p>
 * 
 * <p>Adding a close verb makes the geometry into a continuous loop, a closed contour.
 * Path may contain any number of contours, each beginning with a move verb.</p>
 *
 * <p>Path contours may contain only a move verb, or may also contain lines,
 * quadratic beziers, conics, and cubic beziers. Path contours may be open or
 * closed.</p>
 *
 * <p>When used to draw a filled area, Path describes whether the fill is inside or
 * outside the geometry. Path also describes the winding rule used to fill
 * overlapping contours.</p>
 *
 * <p>Internally, Path lazily computes metrics likes bounds and convexity. Call
 * {@link #updateBoundsCache()} to make Path thread safe.</p>
 */
public class Path extends Managed implements Iterable<PathSegment> {
    static { Library.staticLoad(); }

    @ApiStatus.Internal
    public static class _FinalizerHolder {
        public static final long PTR = _nGetFinalizer();
    }

    @NotNull @Contract("_, _, _, _ -> new")
    public static Path makeRaw(@NotNull Point[] points, @NotNull PathVerb[] verbs, @NotNull float[] conicWeights, @NotNull PathFillMode fillMode) {
        return makeRaw(points, verbs, conicWeights, fillMode, false);
    }

    @NotNull @Contract("_, _, _, _, _ -> new")
    public static Path makeRaw(@NotNull Point[] points, @NotNull PathVerb[] verbs, @NotNull float[] conicWeights, @NotNull PathFillMode fillMode, boolean isVolatile) {
        Stats.onNativeCall();
        int[] verbOrdinals = new int[verbs.length];
        for (int i = 0; i < verbs.length; i++) {
            verbOrdinals[i] = verbs[i].ordinal();
        }
        float[] coords = new float[points.length * 2];
        for (int i = 0; i < points.length; i++) {
            coords[i * 2] = points[i]._x;
            coords[i * 2 + 1] = points[i]._y;
        }
        return new Path(_nMakeRaw(coords, verbOrdinals, conicWeights, fillMode.ordinal(), isVolatile));
    }

    @NotNull @Contract("_ -> new")
    public static Path makeRect(@NotNull Rect rect) {
        return makeRect(rect, PathDirection.CLOCKWISE, 0);
    }

    @NotNull @Contract("_, _ -> new")
    public static Path makeRect(@NotNull Rect rect, @NotNull PathDirection dir) {
        return makeRect(rect, dir, 0);
    }

    @NotNull @Contract("_, _, _ -> new")
    public static Path makeRect(@NotNull Rect rect, @NotNull PathDirection dir, int startIndex) {
        Stats.onNativeCall();
        return new Path(_nMakeRect(rect._left, rect._top, rect._right, rect._bottom, dir.ordinal(), startIndex));
    }

    @NotNull @Contract("_ -> new")
    public static Path makeOval(@NotNull Rect oval) {
        return makeOval(oval, PathDirection.CLOCKWISE);
    }

    @NotNull @Contract("_, _ -> new")
    public static Path makeOval(@NotNull Rect oval, @NotNull PathDirection dir) {
        Stats.onNativeCall();
        return new Path(_nMakeOval(oval._left, oval._top, oval._right, oval._bottom, dir.ordinal()));
    }

    @NotNull @Contract("_, _, _ -> new")
    public static Path makeCircle(float x, float y, float radius) {
        return makeCircle(x, y, radius, PathDirection.CLOCKWISE);
    }

    @NotNull @Contract("_, _, _, _ -> new")
    public static Path makeCircle(float x, float y, float radius, @NotNull PathDirection dir) {
        Stats.onNativeCall();
        return new Path(_nMakeCircle(x, y, radius, dir.ordinal()));
    }

    @NotNull @Contract("_ -> new")
    public static Path makeRRect(@NotNull RRect rrect) {
        return makeRRect(rrect, PathDirection.CLOCKWISE, 0);
    }

    @NotNull @Contract("_, _ -> new")
    public static Path makeRRect(@NotNull RRect rrect, @NotNull PathDirection dir) {
        return makeRRect(rrect, dir, 0);
    }

    @NotNull @Contract("_, _, _ -> new")
    public static Path makeRRect(@NotNull RRect rrect, @NotNull PathDirection dir, int startIndex) {
        try {
            Stats.onNativeCall();
            return new Path(_nMakeRRect(rrect._left, rrect._top, rrect._right, rrect._bottom,
                                        rrect._radii, dir.ordinal(), startIndex));
        } finally {
            ReferenceUtil.reachabilityFence(rrect);
        }
    }

    @NotNull @Contract("_, _ -> new")
    public static Path makePolygon(@NotNull Point[] pts, boolean isClosed) {
        return makePolygon(pts, isClosed, PathFillMode.WINDING, false);
    }

    @NotNull @Contract("_, _, _ -> new")
    public static Path makePolygon(@NotNull Point[] pts, boolean isClosed, @NotNull PathFillMode fillMode) {
        return makePolygon(pts, isClosed, fillMode, false);
    }

    @NotNull @Contract("_, _, _, _ -> new")
    public static Path makePolygon(@NotNull Point[] pts, boolean isClosed, @NotNull PathFillMode fillMode, boolean isVolatile) {
        Stats.onNativeCall();
        float[] coords = new float[pts.length * 2];
        for (int i = 0; i < pts.length; i++) {
            coords[i * 2] = pts[i]._x;
            coords[i * 2 + 1] = pts[i]._y;
        }
        return new Path(_nMakePolygon(coords, isClosed, fillMode.ordinal(), isVolatile));
    }

    @NotNull @Contract("_, _ -> new")
    public static Path makeLine(@NotNull Point a, @NotNull Point b) {
        return makeLine(a._x, a._y, b._x, b._y);
    }

    @NotNull @Contract("_, _, _, _ -> new")
    public static Path makeLine(float x0, float y0, float x1, float y1) {
        Stats.onNativeCall();
        return new Path(_nMakeLine(x0, y0, x1, y1));
    }

    @NotNull @Contract("_ -> new")
    public static Path makeFromSVGString(@NotNull String svg) {
        assert svg != null : "Can't makeFromSVGString with svg == null";
        Stats.onNativeCall();
        long res = _nMakeFromSVGString(svg);
        if (res == 0)
            throw new IllegalArgumentException("Failed to parse SVG Path string: " + svg);
        else
            return new Path(res);
    }

    /**
     * <p>Returns Path that is the result of applying the Op to the first path and the second path.
     * <p>The resulting path will be constructed from non-overlapping contours.
     * <p>The curve order is reduced where possible so that cubics may be turned
     * into quadratics, and quadratics maybe turned into lines.
     *
     * @param one The first operand (for difference, the minuend)
     * @param two The second operand (for difference, the subtrahend)
     * @param op  The operator to apply.
     * @return    Path if operation was able to produce a result, null otherwise
     */
    @Nullable @Contract("_, _, _ -> new")
    public static Path makeCombining(@NotNull Path one, @NotNull Path two, @NotNull PathOp op) {
        try {
            Stats.onNativeCall();
            long ptr = _nMakeCombining(Native.getPtr(one), Native.getPtr(two), op.ordinal());
            return ptr == 0 ? null : new Path(ptr);
        } finally {
            ReferenceUtil.reachabilityFence(one);
            ReferenceUtil.reachabilityFence(two);
        }
    }

    /**
     * Constructs an empty Path. By default, Path has no verbs, no {@link Point}, and no weights.
     * FillMode is set to {@link PathFillMode#WINDING}.
     *
     * @see <a href="https://fiddle.skia.org/c/@Path_empty_constructor">https://fiddle.skia.org/c/@Path_empty_constructor</a>
     */
    public Path() {
        this(_nMake(PathFillMode.WINDING.ordinal()));
        Stats.onNativeCall();
    }

    /**
     * Constructs an empty {@link Path} with specified {@link PathFillMode}.
     * By default, {@link Path} has no verbs, no {@link Point}, and no weights.
     */
    public Path(@NotNull PathFillMode fillMode) {
        this(_nMake(fillMode.ordinal()));
        Stats.onNativeCall();
    }

    /**
     * <p>Constructs a copy of an existing path. Copy constructor makes two
     * paths identical by value. Internally, path and the returned result share
     * pointer values. The underlying verb array, {@link Point} array and
     * weights are copied when modified.</p>
     *
     * <p>Creating a {@link Path} copy is very efficient and never allocates
     * memory. {@link Path} are always copied by value from the interface; the
     * underlying shared pointers are not exposed.</p>
     *
     * @param path  Path to copy by value
     * @see <a href="https://fiddle.skia.org/c/@Path_copy_const_SkPath">https://fiddle.skia.org/c/@Path_copy_const_SkPath</a>
     */
    public Path(@NotNull Path path) {
        this(_nMakeCopy(Native.getPtr(path)));
        ReferenceUtil.reachabilityFence(path);
        Stats.onNativeCall();
    }

    /**
     * Compares this path and o; Returns true if {@link PathFillMode}, verb array, Point array, and weights
     * are equivalent.
     *
     * @param other  Path to compare
     * @return   true if this and Path are equivalent
    */
    @ApiStatus.Internal @Override
    public boolean _nativeEquals(@NotNull Native other) {
        try {
            return _nEquals(_ptr, Native.getPtr(other));
        } finally {
            ReferenceUtil.reachabilityFence(this);
            ReferenceUtil.reachabilityFence(other);
        }
    }

    /**
     * <p>Returns true if Path contain equal verbs and equal weights.
     * If Path contain one or more conics, the weights must match.</p>
     *
     * <p>{@link PathBuilder#conicTo(float, float, float, float, float)} may add different verbs
     * depending on conic weight, so it is not trivial to interpolate a pair of Path
     * containing conics with different conic weight values.</p>
     *
     * @param compare  Path to compare
     * @return         true if Path verb array and weights are equivalent
     *
     * @see <a href="https://fiddle.skia.org/c/@Path_isInterpolatable">https://fiddle.skia.org/c/@Path_isInterpolatable</a>
     */
    @Contract(pure = true)
    public boolean isInterpolatable(@NotNull Path compare) {
        try {
            Stats.onNativeCall();
            return _nIsInterpolatable(_ptr, Native.getPtr(compare));
        } finally {
            ReferenceUtil.reachabilityFence(this);
            ReferenceUtil.reachabilityFence(compare);
        }
    }

    /**
     * <p>Interpolates between Path with {@link Point} array of equal size.
     * Copy verb array and weights to out, and set out Point array to a weighted
     * average of this Point array and ending Point array, using the formula:
     *
     * <p>{@code (Path Point * weight) + ending Point * (1 - weight)}
     *
     * <p>weight is most useful when between zero (ending Point array) and
     * one (this Point_Array); will work with values outside of this
     * range.</p>
     *
     * <p>interpolate() returns null if Point array is not
     * the same size as ending Point array. Call {@link #isInterpolatable(Path)} to check Path
     * compatibility prior to calling interpolate().</p>
     *
     * @param ending  Point array averaged with this Point array
     * @param weight  contribution of this Point array, and
     *                one minus contribution of ending Point array
     * @return        interpolated Path if Path contain same number of Point, null otherwise
     *
     * @see <a href="https://fiddle.skia.org/c/@Path_interpolate">https://fiddle.skia.org/c/@Path_interpolate</a>
     */
    @NotNull @Contract("_, _ -> new")
    public Path makeInterpolate(@NotNull Path ending, float weight) {
        try {
            Stats.onNativeCall();
            long ptr = _nMakeInterpolate(_ptr, Native.getPtr(ending), weight);
            if (ptr == 0)
                throw new IllegalArgumentException("Point array is not the same size as ending Point array");
            return new Path(ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
            ReferenceUtil.reachabilityFence(ending);
        }
    }

    /**
     * Returns {@link PathFillMode}, the rule used to fill {@link Path}.
     *
     * @return  current {@link PathFillMode} setting
     */
    @NotNull @Contract(pure = true)
    public PathFillMode getFillMode() {
        try {
            Stats.onNativeCall();
            return PathFillMode._values[_nGetFillMode(_ptr)];
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Creates an {@link Path} with the same properties and data, and with
     * {@link PathFillMode} set to fillMode.
     *
     * @return  new {@link Path}
     */
    @NotNull @Contract("_ -> new")
    public Path makeWithFillMode(@NotNull PathFillMode fillMode) {
        try {
            Stats.onNativeCall();
            return new Path(_nMakeWithFillMode(_ptr, fillMode.ordinal()));
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

   /**
    * Returns if {@link PathFillMode} describes area outside {@link Path}
    * geometry. The inverse fill area extends indefinitely.
    *
    * @return  true if {@link PathFillMode} is {@link PathFillMode#INVERSE_WINDING}
    *          or {@link PathFillMode#INVERSE_EVEN_ODD}
    */
    @Contract(pure = true)
    public boolean isInverseFillType() {
        try {
            Stats.onNativeCall();
            return _nIsInverseFillType(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Creates an {@link Path} with the same properties and data, and with
     * {@link PathFillMode} replaced with its inverse. The inverse of
     * {@link PathFillMode} describes the area unmodified by the original
     * {@link PathFillMode}.
     *
     * @return  new {@link Path}
     */
    @NotNull @Contract("-> new")
    public Path makeToggleInverseFillType() {
        try {
            Stats.onNativeCall();
            return new Path(_nMakeToggleInverseFillType(_ptr));
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Returns true if the path is convex. If necessary, it will first compute the convexity.
     *
     * @return  true or false
     */
    @Contract(pure = true)
    public boolean isConvex() {
        try {
            Stats.onNativeCall();
            return _nIsConvex(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Returns oval bounds if this path is recognized as an oval or circle.
     *
     * @return  bounds is recognized as an oval or circle, null otherwise
     *
     * @see <a href="https://fiddle.skia.org/c/@Path_isOval">https://fiddle.skia.org/c/@Path_isOval</a>
     */
    @Nullable @Contract(pure = true)
    public Rect isOval() {
        try {
            Stats.onNativeCall();
            return _nIsOval(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Returns {@link RRect} if this path is recognized as an oval, circle or RRect.
     *
     * @return  bounds is recognized as an oval, circle or RRect, null otherwise
     *
     * @see <a href="https://fiddle.skia.org/c/@Path_isRRect">https://fiddle.skia.org/c/@Path_isRRect</a>
     */
    @Nullable @Contract(pure = true)
    public RRect isRRect() {
        try {
            Stats.onNativeCall();
            return _nIsRRect(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }


    /**
     * <p>Returns if Path is empty.</p>
     *
     * <p>Empty Path may have FillMode but has no {@link Point}, {@link PathVerb}, or conic weight.
     * {@link Path()} constructs empty Path; {@link #reset()} make Path empty.</p>
     *
     * @return  true if the path contains no Verb array
     */
    @Contract(pure = true)
    public boolean isEmpty() {
        try {
            Stats.onNativeCall();
            return _nIsEmpty(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * <p>Returns if contour is closed.</p>
     *
     * <p>Contour is closed if Path Verb array was last modified by {@link PathBuilder#closePath()}. When stroked,
     * closed contour draws {@link PaintStrokeJoin} instead of {@link PaintStrokeCap} at first and last Point.</p>
     *
     * @return  true if the last contour ends with a {@link PathVerb#CLOSE}
     *
     * @see <a href="https://fiddle.skia.org/c/@Path_isLastContourClosed">https://fiddle.skia.org/c/@Path_isLastContourClosed</a>
     */
    @Contract(pure = true)
    public boolean isLastContourClosed() {
        try {
            Stats.onNativeCall();
            return _nIsLastContourClosed(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Returns true for finite Point array values between negative Float.MIN_VALUE and
     * positive Float.MAX_VALUE. Returns false for any Point array value of
     * Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, or Float.NaN.
     *
     * @return  true if all Point values are finite
     */
    @Contract(pure = true)
    public boolean isFinite() {
        try {
            Stats.onNativeCall();
            return _nIsFinite(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Returns true if the path is volatile; it will not be altered or discarded
     * by the caller after it is drawn. Path by default have volatile set false, allowing
     * {@link Surface} to attach a cache of data which speeds repeated drawing. If true, {@link Surface}
     * may not speed repeated drawing.
     *
     * @return  true if caller will alter Path after drawing
     */
    @Contract(pure = true)
    public boolean isVolatile() {
        try {
            Stats.onNativeCall();
            return _nIsVolatile(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * <p>Return a copy of {@link Path} with isVolatile indicating whether it
     * will be altered or discarded by the caller after it is drawn. {@link Path}
     * by default have volatile set false, allowing Skia to attach a cache of
     * data which speeds repeated drawing.</p>
     *
     * <p>Mark temporary paths, discarded or modified after use, as volatile
     * to inform Skia that the path need not be cached.</p>
     *
     * <p>Mark animating {@link Path} volatile to improve performance. Mark
     * unchanging {@link Path} non-volatile to improve repeated rendering.</p>
     *
     * <p>raster surface {@link Path} draws are affected by volatile for some
     * shadows. GPU surface {@link Path} draws are affected by volatile for some
     * shadows and concave geometries.</p>
     *
     * @param isVolatile  true if caller will alter {@link Path} after drawing
     * @return            new {@link Path}
     */
    @NotNull @Contract("_ -> new")
    public Path makeWithVolatile(boolean isVolatile) {
        try {
            Stats.onNativeCall();
            return new Path(_nMakeWithVolatile(_ptr, isVolatile));
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * <p>Tests if line between Point pair is degenerate.</p>
     *
     * <p>Line with no length or that moves a very short distance is degenerate; it is
     * treated as a point.</p>
     *
     * <p>exact changes the equality test. If true, returns true only if p1 equals p2.
     * If false, returns true if p1 equals or nearly equals p2.</p>
     *
     * @param p1     line start point
     * @param p2     line end point
     * @param exact  if false, allow nearly equals
     * @return       true if line is degenerate; its length is effectively zero
     *
     * @see <a href="https://fiddle.skia.org/c/@Path_IsLineDegenerate">https://fiddle.skia.org/c/@Path_IsLineDegenerate</a>
     */
    @Contract(pure = true)
    public static boolean isLineDegenerate(@NotNull Point p1, @NotNull Point p2, boolean exact) {
        Stats.onNativeCall();
        return _nIsLineDegenerate(p1._x, p1._y, p2._x, p2._y, exact);
    }

    /**
     * <p>Tests if quad is degenerate.</p>
     *
     * <p>Quad with no length or that moves a very short distance is degenerate; it is
     * treated as a point.</p>
     *
     * @param p1     quad start point
     * @param p2     quad control point
     * @param p3     quad end point
     * @param exact  if true, returns true only if p1, p2, and p3 are equal;
     *               if false, returns true if p1, p2, and p3 are equal or nearly equal
     * @return       true if quad is degenerate; its length is effectively zero
     */
    @Contract(pure = true)
    public static boolean isQuadDegenerate(@NotNull Point p1, @NotNull Point p2, @NotNull Point p3, boolean exact) {
        Stats.onNativeCall();
        return _nIsQuadDegenerate(p1._x, p1._y, p2._x, p2._y, p3._x, p3._y, exact);
    }

    /**
     * <p>Tests if cubic is degenerate.</p>
     *
     * <p>Cubic with no length or that moves a very short distance is degenerate; it is
     * treated as a point.</p>
     *
     * @param p1     cubic start point
     * @param p2     cubic control point 1
     * @param p3     cubic control point 2
     * @param p4     cubic end point
     * @param exact  if true, returns true only if p1, p2, p3, and p4 are equal;
     *               if false, returns true if p1, p2, p3, and p4 are equal or nearly equal
     * @return       true if cubic is degenerate; its length is effectively zero
     */
    @Contract(pure = true)
    public static boolean isCubicDegenerate(@NotNull Point p1, @NotNull Point p2, @NotNull Point p3, @NotNull Point p4, boolean exact) {
        Stats.onNativeCall();
        return _nIsCubicDegenerate(p1._x, p1._y, p2._x, p2._y, p3._x, p3._y, p4._x, p4._y, exact);
    }

    /**
     * Returns array of two points if Path contains only one line;
     * Verb array has two entries: {@link PathVerb#MOVE}, {@link PathVerb#LINE}.
     * Returns null if Path is not one line.
     *
     * @return  Point[2] if Path contains exactly one line, null otherwise
     *
     * @see <a href="https://fiddle.skia.org/c/@Path_isLine">https://fiddle.skia.org/c/@Path_isLine</a>
     */
    @Nullable @Contract(pure = true)
    public Point[] getAsLine() {
        try {
            Stats.onNativeCall();
            return _nMaybeGetAsLine(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Return a read-only view into the path's points.
     *
     * @return  array of {@link Point}s
     */
    @NotNull @Contract(pure = true)
    public Point[] getPoints() {
        try {
            Stats.onNativeCall();
            return _nGetPoints(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Return a read-only view into the path's verbs.
     *
     * @return  array of {@link PathVerb}s
     */
    @NotNull @Contract(pure = true)
    public PathVerb[] getVerbs() {
        try {
            Stats.onNativeCall();
            int[] verbOrdinals = _nGetVerbs(_ptr);
            PathVerb[] verbs = new PathVerb[verbOrdinals.length];
            for (int i = 0; i < verbOrdinals.length; i++) {
                verbs[i] = PathVerb._values[verbOrdinals[i]];
            }
            return verbs;
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Return a read-only view into the path's conic-weights.
     *
     * @return  array of conic weights.
     */
    @NotNull @Contract(pure = true)
    public float[] getConicWeights() {
        try {
            Stats.onNativeCall();
            return _nGetConicWeights(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Returns the number of points in Path.
     * Point count is initially zero.
     *
     * @return  Path Point array length
     *
     * @see <a href="https://fiddle.skia.org/c/@Path_countPoints">https://fiddle.skia.org/c/@Path_countPoints</a>
     */
    @Contract(pure = true)
    public int getPointsCount() {
        try {
            Stats.onNativeCall();
            return _nGetPointsCount(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Returns the number of verbs: {@link PathVerb#MOVE}, {@link PathVerb#LINE}, {@link PathVerb#QUAD}, {@link PathVerb#CONIC},
     * {@link PathVerb#CUBIC}, and {@link PathVerb#CLOSE}; added to Path.
     *
     * @return  length of verb array
     *
     * @see <a href="https://fiddle.skia.org/c/@Path_countVerbs">https://fiddle.skia.org/c/@Path_countVerbs</a>
     */
    @Contract(pure = true)
    public int getVerbsCount() {
        try {
            Stats.onNativeCall();
            return _nCountVerbs(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Return the last point, or null.
     *
     * @return  The last if the path contains one or more {@link Point}, else
     *          returns null
     * @see     <a href="https://fiddle.skia.org/c/@Path_getLastPt">https://fiddle.skia.org/c/@Path_getLastPt</a>
     */
    @Nullable @Contract(pure = true)
    public Point getLastPt() {
        try {
            Stats.onNativeCall();
            return _nGetLastPt(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Returns the approximate byte size of the Path in memory.
     *
     * @return  approximate size
     */
    @Contract(pure = true)
    public long getApproximateBytesUsed() {
        try {
            Stats.onNativeCall();
            return _nApproximateBytesUsed(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * <p>Returns minimum and maximum axes values of Point array.</p>
     *
     * <p>Returns (0, 0, 0, 0) if Path contains no points. Returned bounds width and height may
     * be larger or smaller than area affected when Path is drawn.</p>
     *
     * <p>Rect returned includes all Point added to Path, including Point associated with
     * {@link PathVerb#MOVE} that define empty contours.</p>
     *
     * @return  bounds of all Point in Point array
     */
    @NotNull @Contract(pure = true)
    public Rect getBounds() {
        try {
            Stats.onNativeCall();
            return _nGetBounds(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * <p>Updates internal bounds so that subsequent calls to {@link #getBounds()} are instantaneous.
     * Unaltered copies of Path may also access cached bounds through {@link #getBounds()}.</p>
     *
     * <p>For now, identical to calling {@link #getBounds()} and ignoring the returned value.</p>
     *
     * <p>Call to prepare Path subsequently drawn from multiple threads,
     * to avoid a race condition where each draw separately computes the bounds.</p>
     *
     * @return  this
     */
    @NotNull @Contract("-> this")
    public Path updateBoundsCache() {
        Stats.onNativeCall();
        _nUpdateBoundsCache(_ptr);
        return this;
    }

    /**
     * <p>Returns minimum and maximum axes values of the lines and curves in Path.
     * Returns (0, 0, 0, 0) if Path contains no points.
     * Returned bounds width and height may be larger or smaller than area affected
     * when Path is drawn.</p>
     *
     * <p>Includes Point associated with {@link PathVerb#MOVE} that define empty
     * contours.</p>
     *
     * Behaves identically to {@link #getBounds()} when Path contains
     * only lines. If Path contains curves, computed bounds includes
     * the maximum extent of the quad, conic, or cubic; is slower than {@link #getBounds()};
     * and unlike {@link #getBounds()}, does not cache the result.
     *
     * @return  tight bounds of curves in Path
     *
     * @see <a href="https://fiddle.skia.org/c/@Path_computeTightBounds">https://fiddle.skia.org/c/@Path_computeTightBounds</a>
     */
    @NotNull @Contract(pure = true)
    public Rect computeTightBounds() {
        try {
            Stats.onNativeCall();
            return _nComputeTightBounds(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * <p>Returns true if rect is contained by Path.
     * May return false when rect is contained by Path.</p>
     *
     * <p>For now, only returns true if Path has one contour and is convex.
     * rect may share points and edges with Path and be contained.
     * Returns true if rect is empty, that is, it has zero width or height; and
     * the Point or line described by rect is contained by Path.</p>
     *
     * @param rect  Rect, line, or Point checked for containment
     * @return      true if rect is contained
     *
     * @see <a href="https://fiddle.skia.org/c/@Path_conservativelyContainsRect">https://fiddle.skia.org/c/@Path_conservativelyContainsRect</a>
     */
    @Contract(pure = true)
    public boolean conservativelyContainsRect(@NotNull Rect rect) {
        try {
            Stats.onNativeCall();
            return _nConservativelyContainsRect(_ptr, rect._left, rect._top, rect._right, rect._bottom);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * <p>Approximates conic with quad array. Conic is constructed from start Point p0,
     * control Point p1, end Point p2, and weight w.</p>
     *
     * <p>Quad array is stored in pts; this storage is supplied by caller.</p>
     *
     * <p>Maximum quad count is 2 to the pow2.</p>
     *
     * <p>Every third point in array shares last Point of previous quad and first Point of
     * next quad. Maximum pts storage size is given by: {@code (1 + 2 * (1 << pow2)).</p>}</p>
     *
     * <p>Returns quad count used the approximation, which may be smaller
     * than the number requested.</p>
     *
     * <p>conic weight determines the amount of influence conic control point has on the curve.</p>
     *
     * <p>w less than one represents an elliptical section. w greater than one represents
     * a hyperbolic section. w equal to one represents a parabolic section.</p>
     *
     * <p>Two quad curves are sufficient to approximate an elliptical conic with a sweep
     * of up to 90 degrees; in this case, set pow2 to one.</p>
     *
     * @param p0    conic start Point
     * @param p1    conic control Point
     * @param p2    conic end Point
     * @param w     conic weight
     * @param pow2  quad count, as power of two, normally 0 to 5 (1 to 32 quad curves)
     * @return      number of quad curves written to pts
     */
    @NotNull @Contract(pure = true)
    public static Point[] convertConicToQuads(@NotNull Point p0, @NotNull Point p1, @NotNull Point p2, float w, int pow2) {
        Stats.onNativeCall();
        return _nConvertConicToQuads(p0._x, p0._y, p1._x, p1._y, p2._x, p2._y, w, pow2);
    }

    /**
     * <p>Returns Rect if Path is equivalent to Rect when filled.</p>
     *
     * rect may be smaller than the Path bounds. Path bounds may include {@link PathVerb#MOVE} points
     * that do not alter the area drawn by the returned rect.
     *
     * @return  bounds if Path contains Rect, null otherwise
     *
     * @see <a href="https://fiddle.skia.org/c/@Path_isRect">https://fiddle.skia.org/c/@Path_isRect</a>
     */
    @Nullable @Contract(pure = true)
    public Rect isRect() {
        try {
            Stats.onNativeCall();
            return _nIsRect(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * <p>Return a copy of {@link Path} with verb array, {@link Point} array,
     * and weight transformed by matrix. {@link #makeTransform(Matrix33)} may
     * change verbs and increase their number.</p>
     *
     * <p>If the resulting path has any non-finite values, returns null.</p>
     *
     * @param matrix  {@link Matrix33} to apply to {@link Path}
     * @return        {@link Path} if finite, or null
     */
    @Nullable @Contract("_ -> new")
    public Path makeTransform(@NotNull Matrix33 matrix) {
        try {
            Stats.onNativeCall();
            long ptr = _nMakeTransform(_ptr, matrix._mat);
            return ptr == 0 ? null : new Path(ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Returns {@link Path} with {@link Point} array offset by (dx, dy).
     *
     * @param dx  offset added to {@link Point} array x-axis coordinates
     * @param dy  offset added to {@link Point} array y-axis coordinates
     * @return    new {@link Path}
     */
    @Nullable @Contract("_, _ -> new")
    public Path makeOffset(float dx, float dy) {
        try {
            Stats.onNativeCall();
            long ptr = _nMakeOffset(_ptr, dx, dy);
            return ptr == 0 ? null : new Path(ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Returns {@link Path} with {@link Point} array scaled by s.
     *
     * @param s  scale applied to {@link Point} array coordinates
     * @return   new {@link Path}
     */
    @Nullable @Contract("_ -> new")
    public Path makeScale(float s) {
        return makeScale(s, s);
    }

    /**
     * Returns {@link Path} with {@link Point} array scaled by (sx, sy).
     *
     * @param sx  scale applied to {@link Point} array x-axis coordinates
     * @param sy  scale applied to {@link Point} array y-axis coordinates
     * @return    new {@link Path}
     */
    @Nullable @Contract("_, _ -> new")
    public Path makeScale(float sx, float sy) {
        try {
            Stats.onNativeCall();
            long ptr = _nMakeScale(_ptr, sx, sy);
            return ptr == 0 ? null : new Path(ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * <p>Returns a mask, where each set bit corresponds to a SegmentMask constant
     * if Path contains one or more verbs of that type.</p>
     *
     * <p>Returns zero if Path contains no lines, or curves: quads, conics, or cubics.</p>
     *
     * <p>getSegmentMasks() returns a cached result; it is very fast.</p>
     *
     * @return  SegmentMask bits or zero
     *
     * @see PathSegmentMask#LINE
     * @see PathSegmentMask#QUAD
     * @see PathSegmentMask#CONIC
     * @see PathSegmentMask#CUBIC
     */
    @Contract(pure = true)
    public PathSegmentType[] getSegmentTypes() {
        try {
            Stats.onNativeCall();
            int mask = _nGetSegmentMasks(_ptr);
            PathSegmentType[] result = new PathSegmentType[Integer.bitCount(mask)];
            int idx = 0;
            for (PathSegmentType type: PathSegmentType._values) {
                if ((mask & type._value) != 0) {
                    result[idx] = type;
                    ++idx;
                }
            }
            return result;
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * <p>Specifies whether Path is volatile; whether it will be altered or discarded
     * by the caller after it is drawn. Path by default have volatile set false, allowing
     * SkBaseDevice to attach a cache of data which speeds repeated drawing.</p>
     *
     * <p>Mark temporary paths, discarded or modified after use, as volatile
     * to inform SkBaseDevice that the path need not be cached.</p>
     *
     * <p>Mark animating Path volatile to improve performance.
     * Mark unchanging Path non-volatile to improve repeated rendering.</p>
     *
     * <p>raster surface Path draws are affected by volatile for some shadows.
     * GPU surface Path draws are affected by volatile for some shadows and concave geometries.</p>
     *
     * @param isVolatile  true if caller will alter Path after drawing
     * @return            this
     */
    @NotNull @Contract("_ -> this")
    public Path setVolatile(boolean isVolatile) {
        Stats.onNativeCall();
        _nSetVolatile(_ptr, isVolatile);
        return this;
    }

    /**
     * <p>Exchanges the verb array, Point array, weights, and FillMode with other.
     * Cached state is also exchanged. swap() internally exchanges pointers, so
     * it is lightweight and does not allocate memory.</p>
     *
     * @param   other  Path exchanged by value
     * @return  this
     *
     * @see <a href="https://fiddle.skia.org/c/@Path_swap">https://fiddle.skia.org/c/@Path_swap</a>
     */
    @NotNull @Contract("_ -> this")
    public Path swap(@NotNull Path other) {
        try {
            Stats.onNativeCall();
            _nSwap(_ptr, Native.getPtr(other));
            return this;
        } finally {
            ReferenceUtil.reachabilityFence(other);
        }
    }

    /**
     * Sets {@link PathFillMode}, the rule used to fill {@link Path}
     *
     * @return this
     */
    @NotNull @Contract("_ -> this")
    public Path setFillMode(@NotNull PathFillMode fillMode) {
        Stats.onNativeCall();
        _nSetFillMode(_ptr, fillMode.ordinal());
        return this;
    }

    /**
     * <p>Sets Path to its initial state.</p>
     *
     * <p>Removes verb array, Point array, and weights, and sets FillMode to {@link PathFillMode#WINDING}.
     * Internal storage associated with Path is released.</p>
     *
     * @return  this
     *
     * @see <a href="https://fiddle.skia.org/c/@Path_reset">https://fiddle.skia.org/c/@Path_reset</a>
     */
    @NotNull @Contract("-> this")
    public Path reset() {
        Stats.onNativeCall();
        _nReset(_ptr);
        return this;
    }

    /**
     * Iterates through verb array, and associated {@link Point} array and conic
     * weight.
     *
     * @return  {@link PathSegmentIterator}
     */
    @NotNull @Override
    public PathSegmentIterator iterator() {
        return iterator(false);
    }

    /**
     * Iterates through verb array, and associated {@link Point} array and conic
     * weight. Provides options to treat open contours as closed, and to ignore
     * degenerate data.
     *
     * @param forceClose  true if open contours generate {@link PathVerb#CLOSE}
     * @return            {@link PathSegmentIterator}
     */
    @NotNull @Contract("_ -> new")
    public PathSegmentIterator iterator(boolean forceClose) {
        return PathSegmentIterator.make(this, forceClose);
    }

    /**
     * Returns true if the point (x, y) is contained by Path, taking into
     * account {@link PathFillMode}.
     *
     * @param x  x-axis value of containment test
     * @param y  y-axis value of containment test
     * @return   true if Point is in Path
     *
     * @see <a href="https://fiddle.skia.org/c/@Path_contains">https://fiddle.skia.org/c/@Path_contains</a>
     */
    @Contract(pure = true)
    public boolean contains(float x, float y) {
        try {
            Stats.onNativeCall();
            return _nContains(_ptr, x, y);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Returns true if the point is contained by Path, taking into
     * account {@link PathFillMode}.
     *
     * @param p  point of containment test
     * @return   true if Point is in Path
     *
     * @see <a href="https://fiddle.skia.org/c/@Path_contains">https://fiddle.skia.org/c/@Path_contains</a>
     */
    @Contract(pure = true)
    public boolean contains(@NotNull Point p) {
        return contains(p._x, p._y);
    }

    /**
     * Writes text representation of Path to standard output. The representation may be
     * directly compiled as C++ code. Floating point values are written
     * with limited precision; it may not be possible to reconstruct original Path
     * from output.
     *
     * @return  this
     *
     * @see <a href="https://fiddle.skia.org/c/@Path_dump_2">https://fiddle.skia.org/c/@Path_dump_2</a>
     */
    @NotNull @Contract("-> this")
    public Path dump() {
        Stats.onNativeCall();
        _nDump(_ptr);
        return this;
    }

    /**
     * <p>Writes text representation of Path to standard output. The representation may be
     * directly compiled as C++ code. Floating point values are written
     * in hexadecimal to preserve their exact bit pattern. The output reconstructs the
     * original Path.</p>
     *
     * <p>Use instead of {@link dump()} when submitting</p>
     *
     * @return  this
     *
     * @see <a href="https://fiddle.skia.org/c/@Path_dumpHex">https://fiddle.skia.org/c/@Path_dumpHex</a>
     */
    @NotNull @Contract("-> this")
    public Path dumpHex() {
        Stats.onNativeCall();
        _nDumpHex(_ptr);
        return this;
    }

    /**
     * <p>Writes Path to byte buffer.</p>
     *
     * <p>Writes {@link PathFillMode}, verb array, Point array, conic weight, and
     * additionally writes computed information like path convexity and bounds.</p>
     *
     * <p>Use only be used in concert with {@link makeFromBytes(byte[])};
     * the format used for Path in memory is not guaranteed.</p>
     *
     * @return  serialized Path; length always a multiple of 4
     *
     * @see <a href="https://fiddle.skia.org/c/@Path_writeToMemory">https://fiddle.skia.org/c/@Path_writeToMemory</a>
     */
    @NotNull @Contract(pure = true)
    public byte[] serializeToBytes() {
        try {
            Stats.onNativeCall();
            return _nSerializeToBytes(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * <p>Initializes Path from byte buffer. Returns null if the buffer is
     * data is inconsistent, or the length is too small.</p>
     *
     * <p>Reads {@link PathFillMode}, verb array, Point array, conic weight, and
     * additionally reads computed information like path convexity and bounds.</p>
     *
     * <p>Used only in concert with {@link serializeToBytes()};
     * the format used for Path in memory is not guaranteed.</p>
     *
     * @param data  storage for Path
     * @return      reconstructed Path, or null if data is invalid
     *
     * @see <a href="https://fiddle.skia.org/c/@Path_readFromMemory">https://fiddle.skia.org/c/@Path_readFromMemory</a>
     */
    @Nullable @Contract("_ -> new")
    public static Path makeFromBytes(@NotNull byte[] data) {
        Stats.onNativeCall();
        long ptr = _nMakeFromBytes(data);
        return ptr == 0 ? null : new Path(ptr);
    }

    /**
     * <p>Returns a non-zero, globally unique value. A different value is returned
     * if verb array, Point array, or conic weight changes.</p>
     *
     * <p>Setting {@link PathFillMode} does not change generation identifier.</p>
     *
     * <p>Each time the path is modified, a different generation identifier will be returned.
     * {@link PathFillMode} does affect generation identifier on Android framework.</p>
     *
     * @return  non-zero, globally unique value
     *
     * @see <a href="https://fiddle.skia.org/c/@Path_getGenerationID">https://fiddle.skia.org/c/@Path_getGenerationID</a>
     * @see <a href="https://bugs.chromium.org/p/skia/issues/detail?id=1762">https://bugs.chromium.org/p/skia/issues/detail?id=1762</a>
     */
    @Contract(pure = true)
    public int getGenerationId() {
        try {
            Stats.onNativeCall();
            return _nGetGenerationId(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Returns if Path data is consistent. Corrupt Path data is detected if
     * internal values are out of range or internal storage does not match
     * array dimensions.
     *
     * @return  true if Path data is consistent
     */
    @Contract(pure = true)
    public boolean isValid() {
        try {
            Stats.onNativeCall();
            return _nIsValid(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @ApiStatus.Internal
    public Path(long ptr) {
        super(ptr, _FinalizerHolder.PTR);
    }

    public static native long    _nGetFinalizer();
    public static native long    _nMakeRaw(float[] coords, int[] verbs, float[] conicWeights, int fillMode, boolean isVolatile);
    public static native long    _nMakeRect(float l, float t, float r, float b, int dir, int startIndex);
    public static native long    _nMakeOval(float l, float t, float r, float b, int dir);
    public static native long    _nMakeCircle(float x, float y, float radius, int dir);
    public static native long    _nMakeRRect(float l, float t, float r, float b, float[] radii, int dir, int startIndex);
    public static native long    _nMakePolygon(float[] coords, boolean isClosed, int fillMode, boolean isVolatile);
    public static native long    _nMakeLine(float x0, float y0, float x1, float y1);
    public static native long    _nMakeFromSVGString(String s);
    public static native long    _nMake(int fillMode);
    public static native long    _nMakeCopy(long ptr);
    public static native long    _nMakeCombining(long onePtr, long twoPtr, int op);
    public static native boolean _nEquals(long aPtr, long bPtr);
    public static native boolean _nIsInterpolatable(long ptr, long comparePtr);
    public static native long    _nMakeInterpolate(long ptr, long endingPtr, float weight);
    public static native int     _nGetFillMode(long ptr);
    public static native long    _nMakeWithFillMode(long ptr, int fillMode);
    public static native boolean _nIsInverseFillType(long ptr);
    public static native long    _nMakeToggleInverseFillType(long ptr);
    public static native boolean _nIsConvex(long ptr);
    public static native Rect    _nIsOval(long ptr);
    public static native RRect   _nIsRRect(long ptr);
    public static native boolean _nIsEmpty(long ptr);
    public static native boolean _nIsLastContourClosed(long ptr);
    public static native boolean _nIsFinite(long ptr);
    public static native boolean _nIsVolatile(long ptr);
    public static native long    _nMakeWithVolatile(long ptr, boolean isVolatile);
    public static native boolean _nIsLineDegenerate(float x0, float y0, float x1, float y1, boolean exact);
    public static native boolean _nIsQuadDegenerate(float x0, float y0, float x1, float y1, float x2, float y2, boolean exact);
    public static native boolean _nIsCubicDegenerate(float x0, float y0, float x1, float y1, float x2, float y2, float x3, float y3, boolean exact);
    public static native Point[] _nMaybeGetAsLine(long ptr);
    public static native Point[] _nGetPoints(long ptr);
    public static native int[]   _nGetVerbs(long ptr);
    public static native float[] _nGetConicWeights(long ptr);
    public static native int     _nGetPointsCount(long ptr);
    public static native int     _nCountVerbs(long ptr);
    public static native Point   _nGetLastPt(long ptr);
    public static native long    _nApproximateBytesUsed(long ptr);
    public static native Rect    _nGetBounds(long ptr);
    public static native void    _nUpdateBoundsCache(long ptr);
    public static native Rect    _nComputeTightBounds(long ptr);
    public static native boolean _nConservativelyContainsRect(long ptr, float l, float t, float r, float b);
    public static native Point[] _nConvertConicToQuads(float x0, float y0, float x1, float y1, float x2, float y2, float w, int pow2);
    public static native Rect    _nIsRect(long ptr);
    public static native long    _nMakeTransform(long ptr, float[] matrix);
    public static native long    _nMakeOffset(long ptr, float dx, float dy);
    public static native long    _nMakeScale(long ptr, float sx, float sy);
    public static native int     _nGetSegmentMasks(long ptr);
    public static native void    _nSetVolatile(long ptr, boolean isVolatile);
    public static native void    _nSwap(long ptr, long otherPtr);
    public static native void    _nSetFillMode(long ptr, int fillMode);
    public static native void    _nReset(long ptr);
    public static native boolean _nContains(long ptr, float x, float y);
    public static native void    _nDump(long ptr);
    public static native void    _nDumpHex(long ptr);
    public static native byte[]  _nSerializeToBytes(long ptr);
    public static native long    _nMakeFromBytes(byte[] data);
    public static native int     _nGetGenerationId(long ptr);
    public static native boolean _nIsValid(long ptr);
}
