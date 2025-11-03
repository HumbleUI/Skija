package io.github.humbleui.skija;

import org.jetbrains.annotations.*;
import io.github.humbleui.skija.impl.*;
import io.github.humbleui.types.*;

/**
 * <p>PathBuilder is used to create immutable Path objects. It provides a fluent API for
 * constructing paths by adding verbs (moveTo, lineTo, cubicTo, etc.) and points.</p>
 *
 * <p>Unlike Path (which may become immutable), PathBuilder is explicitly designed for
 * building paths. When finished, call {@link #snapshot()} to get an immutable Path,
 * or {@link #detach()} to get a Path and reset the builder for reuse.</p>
 *
 * <p>PathBuilder follows the builder pattern with fluent API - most methods return
 * 'this' to allow method chaining.</p>
 */
public class PathBuilder extends Managed {
    static { Library.staticLoad(); }

    @ApiStatus.Internal
    public static class _FinalizerHolder {
        public static final long PTR = _nGetFinalizer();
    }

    /**
     * Constructs an empty PathBuilder. By default, PathBuilder has no verbs, no
     * {@link Point}, and no weights. FillMode is set to
     * {@link PathFillMode#WINDING}.
     */
    public PathBuilder() {
        this(_nMake(), _FinalizerHolder.PTR);
        Stats.onNativeCall();
    }

    /**
     * Constructs an empty PathBuilder with the specified fill mode. By default,
     * PathBuilder has no verbs, no {@link Point}, and no weights.
     *
     * @param fillMode  fill mode for paths created by this builder
     */
    public PathBuilder(PathFillMode fillMode) {
        this(_nMakeFromFillMode(fillMode.ordinal()), _FinalizerHolder.PTR);
        Stats.onNativeCall();
    }

    /**
     * Constructs a PathBuilder from an existing PathBuilder, copying its contents.
     *
     * @param builder  PathBuilder to copy
     */
    public PathBuilder(PathBuilder builder) {
        this(_nMakeFromPathBuilder(Native.getPtr(builder)), _FinalizerHolder.PTR);
        Stats.onNativeCall();
        ReferenceUtil.reachabilityFence(builder);
    }

    /**
     * Constructs an {@link PathBuilder} that is a copy of an existing
     * {@link Path}. Copies the FillType and replays all of the verbs from the
     * {@link Path} into the {@link PathBuilder}.
     *
     * @param path  {@link Path} to copy
     */
    public PathBuilder(Path path) {
        this(_nMakeFromPath(Native.getPtr(path)), _FinalizerHolder.PTR);
        Stats.onNativeCall();
        ReferenceUtil.reachabilityFence(path);
    }

    /**
     * Returns {@link PathFillMode}, the rule used to fill {@link Path}.
     *
     * @return  current {@link PathFillMode} setting
     */
    @NotNull
    public PathFillMode getFillMode() {
        try {
            Stats.onNativeCall();
            return PathFillMode.values()[_nGetFillMode(_ptr)];
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * <p>Returns minimum and maximum axes values of {@link Point} array.
     * Returns (0, 0, 0, 0) if PathBuilder contains no points.</p>
     *
     * <p>{@link Rect} returned includes all {@link Point} added to PathBuilder,
     * including {@link Point} associated with {@link PathVerb#MOVE} that define
     * empty contours.</p>
     *
     * <p>If any of the points are non-finite, returns null.</p>
     *
     * @return  bounds of all {@link Point} in {@link Point} array, or null
     */
    @Nullable
    public Rect computeFiniteBounds() {
        try {
            Stats.onNativeCall();
            return _nComputeFiniteBounds(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Like {@link #computeFiniteBounds()} but returns a 'tight' bounds, meaning
     * when there are curve segments, this computes the X/Y limits of the curve
     * itself, not the curve's control point(s). For a polygon, this returns the
     * same as {@link #computeFiniteBounds()}.
     *
     * @return  tight bounds of the path, or null if empty
     */
    @Nullable
    public Rect computeTightBounds() {
        try {
            Stats.onNativeCall();
            return _nComputeTightBounds(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }


    /**
     * Returns an immutable {@link Path} representing the current state of the
     * builder. The builder is unchanged after returning the path.
     *
     * @return  {@link Path} representing the current state of the builder.
     */
    @NotNull
    public Path snapshot() {
        try {
            Stats.onNativeCall();
            return new Path(_nSnapshot(_ptr, null));
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Returns an immutable {@link Path} representing the current state of the
     * builder. The builder is unchanged after returning the path.
     *
     * @param matrix  transformation matrix to apply to the points after they
     *                are copied into the resulting path.
     * @return        {@link Path} representing the current state of the builder.
     */
    @NotNull
    public Path snapshot(@NotNull Matrix33 matrix) {
        try {
            Stats.onNativeCall();
            return new Path(_nSnapshot(_ptr, matrix._mat));
        } finally {
            ReferenceUtil.reachabilityFence(this);
            ReferenceUtil.reachabilityFence(matrix);
        }
    }

    /**
     * Returns an immutable Path representing the current state of the builder,
     * and resets the builder to empty. This is more efficient than calling
     * {@link #snapshot()} followed by {@link #reset()} if you don't need to
     * preserve the builder state.
     *
     * @return  immutable {@link Path}
     */
    @NotNull
    public Path detach() {
        try {
            Stats.onNativeCall();
            return new Path(_nDetach(_ptr, null));
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Returns an immutable Path representing the current state of the builder,
     * transformed by the specified matrix, and resets the builder to empty.
     *
     * @param matrix  transformation matrix to apply
     * @return        immutable {@link Path}
     */
    @NotNull
    public Path detach(@NotNull Matrix33 matrix) {
        try {
            Stats.onNativeCall();
            return new Path(_nDetach(_ptr, matrix._mat));
        } finally {
            ReferenceUtil.reachabilityFence(this);
            ReferenceUtil.reachabilityFence(matrix);
        }
    }

    /**
     * Returns an immutable Path representing the current state of the builder.
     * The builder is closed.
     *
     * @return  immutable Path
     */
    @NotNull
    public Path build() {
        try {
            Stats.onNativeCall();
            return new Path(_nSnapshot(_ptr, null));
        } finally {
            ReferenceUtil.reachabilityFence(this);
            close();
        }
    }

    /**
     * Sets the fill mode for paths generated by this builder.
     *
     * @param mode  fill mode to use
     * @return      reference to PathBuilder
     */
    @NotNull @Contract("_ -> this")
    public PathBuilder setFillMode(PathFillMode mode) {
        Stats.onNativeCall();
        _nSetFillMode(_ptr, mode.ordinal());
        return this;
    }

    /**
     * <p>Specifies whether {@link Path} is volatile; whether it will be altered
     * or discarded by the caller after it is drawn. {@link Path} by default
     * have volatile set false, allowing Skia to attach a cache of data which
     * speeds repeated drawing.</p>
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
     * @param isVolatile  volatile flag value
     * @return            reference to PathBuilder
     */
    @NotNull @Contract("_ -> this")
    public PathBuilder setVolatile(boolean isVolatile) {
        Stats.onNativeCall();
        _nSetVolatile(_ptr, isVolatile);
        return this;
    }

    /**
     * Sets PathBuilder to its initial state. Removes verb array, {@link Point}
     * array, and weights, and sets {@link PathFillMode} to
     * {@link PathFillMode#WINDING}. Internal storage associated with
     * PathBuilder is preserved.
     *
     * @return  reference to PathBuilder
     */
    @NotNull @Contract("_ -> this")
    public PathBuilder reset() {
        Stats.onNativeCall();
        _nReset(_ptr);
        return this;
    }

    /**
     * <p>Specifies the beginning of contour. If the previous verb was a "move"
     * verb, then this just replaces the point value of that move, otherwise it
     * appends a new "move" verb to the builder using the point.</p>
     *
     * <p>Thus, each contour can only have 1 move verb in it (the last one
     * specified).</p>
     *
     * @param x  x-axis value of contour start
     * @param y  y-axis value of contour start
     * @return   reference to PathBuilder
     */
    @NotNull @Contract("_, _ -> this")
    public PathBuilder moveTo(float x, float y) {
        Stats.onNativeCall();
        _nMoveTo(_ptr, x, y);
        return this;
    }

    /**
     * <p>Specifies the beginning of contour. If the previous verb was a "move"
     * verb, then this just replaces the point value of that move, otherwise it
     * appends a new "move" verb to the builder using the point.</p>
     *
     * <p>Thus, each contour can only have 1 move verb in it (the last one
     * specified).</p>
     *
     * @param p  contour start
     * @return   reference to PathBuilder
     */
    @NotNull @Contract("_, _ -> this")
    public PathBuilder moveTo(@NotNull Point p) {
        return moveTo(p._x, p._y);
    }
    /**
     * <p>Adds line from last point to (x, y). If PathBuilder is empty, or last
     * verb is {@link PathVerb#CLOSE}, last point is set to (0, 0) before adding
     * line.</p>
     *
     * <p>lineTo() appends {@link PathVerb#MOVE} to verb array and (0, 0) to
     * Point array, if needed. lineTo() then appends {@link PathVerb#LINE} to
     * verb array and (x, y) to Point array.</p>
     *
     * @param x  end of added line on x-axis
     * @param y  end of added line on y-axis
     * @return   this
     */
    @NotNull @Contract("_, _ -> this")
    public PathBuilder lineTo(float x, float y) {
        Stats.onNativeCall();
        _nLineTo(_ptr, x, y);
        return this;
    }

    /**
     * <p>Adds line from last point to (x, y). If PathBuilder is empty, or last
     * verb is {@link PathVerb#CLOSE}, last point is set to (0, 0) before adding
     * line.</p>
     *
     * <p>lineTo() appends {@link PathVerb#MOVE} to verb array and (0, 0) to
     * Point array, if needed. lineTo() then appends {@link PathVerb#LINE} to
     * verb array and (x, y) to Point array.</p>
     *
     * @param p  end Point of added line
     * @return   reference to PathBuilder
     */
    @NotNull @Contract("_ -> this")
    public PathBuilder lineTo(@NotNull Point p) {
        return lineTo(p._x, p._y);
    }

    /**
     * Adds quad from last point towards (x1, y1), to (x2, y2).
     * If PathBuilder is empty, or last {@link PathVerb} is {@link PathVerb#CLOSE}, last point is set to (0, 0)
     * before adding quad.
     *
     * Appends {@link PathVerb#MOVE} to verb array and (0, 0) to Point array, if needed;
     * then appends {@link PathVerb#QUAD} to verb array; and (x1, y1), (x2, y2)
     * to Point array.
     *
     * @param x1  control Point of quad on x-axis
     * @param y1  control Point of quad on y-axis
     * @param x2  end Point of quad on x-axis
     * @param y2  end Point of quad on y-axis
     * @return    reference to PathBuilder
     */
    @NotNull @Contract("_, _, _, _ -> this")
    public PathBuilder quadTo(float x1, float y1, float x2, float y2) {
        Stats.onNativeCall();
        _nQuadTo(_ptr, x1, y1, x2, y2);
        return this;
    }

    /**
     * <p>Adds quad from last point towards Point p1, to Point p2.</p>
     *
     * <p>If PathBuilder is empty, or last {@link PathVerb} is {@link PathVerb#CLOSE}, last point is set to (0, 0)
     * before adding quad.</p>
     *
     * <p>Appends {@link PathVerb#MOVE} to verb array and (0, 0) to Point array, if needed;
     * then appends {@link PathVerb#QUAD} to verb array; and Point p1, p2
     * to Point array.</p>
     *
     * @param p1  control Point of added quad
     * @param p2  end Point of added quad
     * @return    reference to PathBuilder
     */
    @NotNull @Contract("_, _ -> this")
    public PathBuilder quadTo(@NotNull Point p1, @NotNull Point p2) {
        return quadTo(p1._x, p1._y, p2._x, p2._y);
    }

    /**
     * <p>Adds conic from last point towards (x1, y1), to (x2, y2), weighted by w.</p>
     *
     * <p>If PathBuilder is empty, or last {@link PathVerb} is {@link PathVerb#CLOSE}, last point is set to (0, 0)
     * before adding conic.</p>
     *
     * <p>Appends {@link PathVerb#MOVE} to verb array and (0, 0) to Point array, if needed.</p>
     *
     * <p>If w is finite and not one, appends {@link PathVerb#CONIC} to verb array;
     * and (x1, y1), (x2, y2) to Point array; and w to conic weights.</p>
     *
     * <p>If w is one, appends {@link PathVerb#QUAD} to verb array, and
     * (x1, y1), (x2, y2) to Point array.</p>
     *
     * <p>If w is not finite, appends {@link PathVerb#LINE} twice to verb array, and
     * (x1, y1), (x2, y2) to Point array.</p>
     *
     * @param x1  control Point of conic on x-axis
     * @param y1  control Point of conic on y-axis
     * @param x2  end Point of conic on x-axis
     * @param y2  end Point of conic on y-axis
     * @param w   weight of added conic
     * @return    reference to PathBuilder
     */
    @NotNull @Contract("_, _, _, _, _ -> this")
    public PathBuilder conicTo(float x1, float y1, float x2, float y2, float w) {
        Stats.onNativeCall();
        _nConicTo(_ptr, x1, y1, x2, y2, w);
        return this;
    }

    /**
     * <p>Adds conic from last point towards Point p1, to Point p2, weighted by w.</p>
     *
     * <p>If PathBuilder is empty, or last {@link PathVerb} is {@link PathVerb#CLOSE}, last point is set to (0, 0)
     * before adding conic.</p>
     *
     * <p>Appends {@link PathVerb#MOVE} to verb array and (0, 0) to Point array, if needed.</p>
     *
     * <p>If w is finite and not one, appends {@link PathVerb#CONIC} to verb array;
     * and Point p1, p2 to Point array; and w to conic weights.</p>
     *
     * <p>If w is one, appends {@link PathVerb#QUAD} to verb array, and Point p1, p2
     * to Point array.</p>
     *
     * <p>If w is not finite, appends {@link PathVerb#LINE} twice to verb array, and
     * Point p1, p2 to Point array.</p>
     *
     * @param p1  control Point of added conic
     * @param p2  end Point of added conic
     * @param w   weight of added conic
     * @return    reference to PathBuilder
     */
    @NotNull @Contract("_, _, _ -> this")
    public PathBuilder conicTo(@NotNull Point p1, @NotNull Point p2, float w) {
        return conicTo(p1._x, p1._y, p2._x, p2._y, w);
    }

    /**
     * <p>Adds cubic from last point towards (x1, y1), then towards (x2, y2), ending at
     * (x3, y3). If PathBuilder is empty, or last {@link PathVerb} is {@link PathVerb#CLOSE}, last point is set to
     * (0, 0) before adding cubic.</p>
     *
     * <p>Appends {@link PathVerb#MOVE} to verb array and (0, 0) to Point array, if needed;
     * then appends {@link PathVerb#CUBIC} to verb array; and (x1, y1), (x2, y2), (x3, y3)
     * to Point array.</p>
     *
     * @param x1  first control Point of cubic on x-axis
     * @param y1  first control Point of cubic on y-axis
     * @param x2  second control Point of cubic on x-axis
     * @param y2  second control Point of cubic on y-axis
     * @param x3  end Point of cubic on x-axis
     * @param y3  end Point of cubic on y-axis
     * @return    reference to PathBuilder
     */
    @NotNull @Contract("_, _, _, _, _, _ -> this")
    public PathBuilder cubicTo(float x1, float y1, float x2, float y2, float x3, float y3) {
        Stats.onNativeCall();
        _nCubicTo(_ptr, x1, y1, x2, y2, x3, y3);
        return this;
    }

    /**
     * <p>Adds cubic from last point towards Point p1, then towards Point p2, ending at
     * Point p3. If PathBuilder is empty, or last {@link PathVerb} is {@link PathVerb#CLOSE}, last point is set to
     * (0, 0) before adding cubic.</p>
     *
     * <p>Appends {@link PathVerb#MOVE} to verb array and (0, 0) to Point array, if needed;
     * then appends {@link PathVerb#CUBIC} to verb array; and Point p1, p2, p3
     * to Point array.</p>
     *
     * @param p1  first control Point of cubic
     * @param p2  second control Point of cubic
     * @param p3  end Point of cubic
     * @return    reference to PathBuilder
     */
    @NotNull @Contract("_, _, _ -> this")
    public PathBuilder cubicTo(@NotNull Point p1, @NotNull Point p2, @NotNull Point p3) {
        return cubicTo(p1._x, p1._y, p2._x, p2._y, p3._x, p3._y);
    }

    /**
     * <p>Appends {@link PathVerb#CLOSE} to PathBuilder. A closed contour connects the first and last Point
     * with line, forming a continuous loop. Open and closed contour draw the same
     * with {@link PaintMode#FILL}. With {@link PaintMode#STROKE}, open contour draws
     * {@link PaintStrokeCap} at contour start and end; closed contour draws
     * {@link PaintStrokeJoin} at contour start and end.</p>
     *
     * <p>close() has no effect if PathBuilder is empty or last PathBuilder {@link PathVerb} is {@link PathVerb#CLOSE}.</p>
     *
     * @return  reference to PathBuilder
     */
    @NotNull @Contract("-> this")
    public PathBuilder closePath() {
        Stats.onNativeCall();
        _nClosePath(_ptr);
        return this;
    }

    /**
     * Append a series of {@link #lineTo(float, float)}
     *
     * @param pts  array of coordinates (x1, y1, x2, y2, ...)
     * @return     reference to PathBuilder.
     */
    @NotNull @Contract("_ -> this")
    public PathBuilder polylineTo(@NotNull float[] pts) {
        Stats.onNativeCall();
        _nPolylineTo(_ptr, pts);
        return this;
    }

    /**
     * Append a series of {@link #lineTo(Point)}
     *
     * @param pts  array of {@link Point}
     * @return     reference to PathBuilder.
     */
    @NotNull @Contract("_ -> this")
    public PathBuilder polylineTo(@NotNull Point[] pts) {
        float[] coords = new float[pts.length * 2];
        for (int i = 0; i < pts.length; i++) {
            coords[i * 2] = pts[i]._x;
            coords[i * 2 + 1] = pts[i]._y;
        }
        return polylineTo(coords);
    }

    /**
     * <p>Adds beginning of contour relative to last point.</p>
     *
     * <p>If PathBuilder is empty, starts contour at (dx, dy).
     * Otherwise, start contour at last point offset by (dx, dy).
     * Function name stands for "relative move to".</p>
     *
     * @param dx  offset from last point to contour start on x-axis
     * @param dy  offset from last point to contour start on y-axis
     * @return    reference to PathBuilder
     * @see <a href="https://fiddle.skia.org/c/@Path_rMoveTo">https://fiddle.skia.org/c/@Path_rMoveTo</a>
     */
    @NotNull @Contract("_, _ -> this")
    public PathBuilder rMoveTo(float dx, float dy) {
        Stats.onNativeCall();
        _nRMoveTo(_ptr, dx, dy);
        return this;
    }

    /**
     * <p>Adds beginning of contour relative to last point.</p>
     *
     * <p>If PathBuilder is empty, starts contour at (dx, dy).
     * Otherwise, start contour at last point offset by (dx, dy).
     * Function name stands for "relative move to".</p>
     *
     * @param p  offset from last point to contour start
     * @return   reference to PathBuilder
     * @see <a href="https://fiddle.skia.org/c/@Path_rMoveTo">https://fiddle.skia.org/c/@Path_rMoveTo</a>
     */
    @NotNull @Contract("_ -> this")
    public PathBuilder rMoveTo(@NotNull Point p) {
        return rMoveTo(p._x, p._y);
    }

    /**
     * <p>Adds line from last point to vector (dx, dy). If PathBuilder is empty, or last {@link PathVerb} is
     * {@link PathVerb#CLOSE}, last point is set to (0, 0) before adding line.</p>
     *
     * <p>Appends {@link PathVerb#MOVE} to verb array and (0, 0) to Point array, if needed;
     * then appends {@link PathVerb#LINE} to verb array and line end to Point array.</p>
     *
     * <p>Line end is last point plus vector (dx, dy).</p>
     *
     * <p>Function name stands for "relative line to".</p>
     *
     * @param dx  offset from last point to line end on x-axis
     * @param dy  offset from last point to line end on y-axis
     * @return    reference to PathBuilder
     */
    @NotNull @Contract("_, _ -> this")
    public PathBuilder rLineTo(float dx, float dy) {
        Stats.onNativeCall();
        _nRLineTo(_ptr, dx, dy);
        return this;
    }

    /**
     * <p>Adds line from last point to vector (dx, dy). If PathBuilder is empty, or last {@link PathVerb} is
     * {@link PathVerb#CLOSE}, last point is set to (0, 0) before adding line.</p>
     *
     * <p>Appends {@link PathVerb#MOVE} to verb array and (0, 0) to Point array, if needed;
     * then appends {@link PathVerb#LINE} to verb array and line end to Point array.</p>
     *
     * <p>Line end is last point plus vector (dx, dy).</p>
     *
     * <p>Function name stands for "relative line to".</p>
     *
     * @param p  offset from last point to line end
     * @return   reference to PathBuilder
     */
    @NotNull @Contract("_ -> this")
    public PathBuilder rLineTo(@NotNull Point p) {
        return rLineTo(p._x, p._y);
    }

    /**
     * <p>Adds quad from last point towards vector (dx1, dy1), to vector (dx2, dy2).
     * If PathBuilder is empty, or last {@link PathVerb}
     * is {@link PathVerb#CLOSE}, last point is set to (0, 0) before adding quad.</p>
     *
     * <p>Appends {@link PathVerb#MOVE} to verb array and (0, 0) to Point array,
     * if needed; then appends {@link PathVerb#QUAD} to verb array; and appends quad
     * control and quad end to Point array.</p>
     *
     * <p>Quad control is last point plus vector (dx1, dy1).</p>
     *
     * <p>Quad end is last point plus vector (dx2, dy2).</p>
     *
     * <p>Function name stands for "relative quad to".</p>
     *
     * @param dx1  offset from last point to quad control on x-axis
     * @param dy1  offset from last point to quad control on y-axis
     * @param dx2  offset from last point to quad end on x-axis
     * @param dy2  offset from last point to quad end on y-axis
     * @return     reference to PathBuilder
     */
    @NotNull @Contract("_, _, _, _ -> this")
    public PathBuilder rQuadTo(float dx1, float dy1, float dx2, float dy2) {
        Stats.onNativeCall();
        _nRQuadTo(_ptr, dx1, dy1, dx2, dy2);
        return this;
    }

    /**
     * <p>Adds quad from last point towards vector (dx1, dy1), to vector (dx2, dy2).
     * If PathBuilder is empty, or last {@link PathVerb}
     * is {@link PathVerb#CLOSE}, last point is set to (0, 0) before adding quad.</p>
     *
     * <p>Appends {@link PathVerb#MOVE} to verb array and (0, 0) to Point array,
     * if needed; then appends {@link PathVerb#QUAD} to verb array; and appends quad
     * control and quad end to Point array.</p>
     *
     * <p>Quad control is last point plus vector (dx1, dy1).</p>
     *
     * <p>Quad end is last point plus vector (dx2, dy2).</p>
     *
     * <p>Function name stands for "relative quad to".</p>
     *
     * @param p1  offset from last point to quad control
     * @param p2  offset from last point to quad end
     * @return    reference to PathBuilder
     */
    @NotNull @Contract("_, _ -> this")
    public PathBuilder rQuadTo(@NotNull Point p1, @NotNull Point p2) {
        return rQuadTo(p1._x, p1._y, p2._x, p2._y);
    }

    /**
     * <p>Adds conic from last point towards vector (dx1, dy1), to vector (dx2, dy2),
     * weighted by w. If PathBuilder is empty, or last {@link PathVerb}
     * is {@link PathVerb#CLOSE}, last point is set to (0, 0) before adding conic.</p>
     *
     * <p>Appends {@link PathVerb#MOVE} to verb array and (0, 0) to Point array,
     * if needed.</p>
     *
     * <p>If w is finite and not one, next appends {@link PathVerb#CONIC} to verb array,
     * and w is recorded as conic weight; otherwise, if w is one, appends
     * {@link PathVerb#QUAD} to verb array; or if w is not finite, appends {@link PathVerb#LINE}
     * twice to verb array.</p>
     *
     * <p>In all cases appends Point control and end to Point array.
     * control is last point plus vector (dx1, dy1).
     * end is last point plus vector (dx2, dy2).</p>
     *
     * <p>Function name stands for "relative conic to".</p>
     *
     * @param dx1  offset from last point to conic control on x-axis
     * @param dy1  offset from last point to conic control on y-axis
     * @param dx2  offset from last point to conic end on x-axis
     * @param dy2  offset from last point to conic end on y-axis
     * @param w    weight of added conic
     * @return     reference to PathBuilder
     */
    @NotNull @Contract("_, _, _, _, _ -> this")
    public PathBuilder rConicTo(float dx1, float dy1, float dx2, float dy2, float w) {
        Stats.onNativeCall();
        _nRConicTo(_ptr, dx1, dy1, dx2, dy2, w);
        return this;
    }

    /**
     * <p>Adds conic from last point towards vector (dx1, dy1), to vector (dx2, dy2),
     * weighted by w. If PathBuilder is empty, or last {@link PathVerb}
     * is {@link PathVerb#CLOSE}, last point is set to (0, 0) before adding conic.</p>
     *
     * <p>Appends {@link PathVerb#MOVE} to verb array and (0, 0) to Point array,
     * if needed.</p>
     *
     * <p>If w is finite and not one, next appends {@link PathVerb#CONIC} to verb array,
     * and w is recorded as conic weight; otherwise, if w is one, appends
     * {@link PathVerb#QUAD} to verb array; or if w is not finite, appends {@link PathVerb#LINE}
     * twice to verb array.</p>
     *
     * <p>In all cases appends Point control and end to Point array.
     * control is last point plus vector (dx1, dy1).
     * end is last point plus vector (dx2, dy2).</p>
     *
     * <p>Function name stands for "relative conic to".</p>
     *
     * @param p1  offset from last point to conic control
     * @param p2  offset from last point to conic end
     * @param w   weight of added conic
     * @return    reference to PathBuilder
     */
    @NotNull @Contract("_, _, _ -> this")
    public PathBuilder rConicTo(@NotNull Point p1, @NotNull Point p2, float w) {
        return rConicTo(p1._x, p1._y, p2._x, p2._y, w);
    }

    /**
     * <p>Adds cubic from last point towards vector (dx1, dy1), then towards
     * vector (dx2, dy2), to vector (dx3, dy3).
     * If PathBuilder is empty, or last {@link PathVerb}
     * is {@link PathVerb#CLOSE}, last point is set to (0, 0) before adding cubic.</p>
     *
     * <p>Appends {@link PathVerb#MOVE} to verb array and (0, 0) to Point array,
     * if needed; then appends {@link PathVerb#CUBIC} to verb array; and appends cubic
     * control and cubic end to Point array.</p>
     *
     * <p>Cubic control is last point plus vector (dx1, dy1).</p>
     *
     * <p>Cubic end is last point plus vector (dx2, dy2).</p>
     *
     * <p>Function name stands for "relative cubic to".</p>
     *
     * @param dx1  offset from last point to first cubic control on x-axis
     * @param dy1  offset from last point to first cubic control on y-axis
     * @param dx2  offset from last point to second cubic control on x-axis
     * @param dy2  offset from last point to second cubic control on y-axis
     * @param dx3  offset from last point to cubic end on x-axis
     * @param dy3  offset from last point to cubic end on y-axis
     * @return    reference to PathBuilder
     */
    @NotNull @Contract("_, _, _, _, _, _ -> this")
    public PathBuilder rCubicTo(float dx1, float dy1, float dx2, float dy2, float dx3, float dy3) {
        Stats.onNativeCall();
        _nRCubicTo(_ptr, dx1, dy1, dx2, dy2, dx3, dy3);
        return this;
    }

    /**
     * <p>Adds cubic from last point towards vector (dx1, dy1), then towards
     * vector (dx2, dy2), to vector (dx3, dy3).
     * If PathBuilder is empty, or last {@link PathVerb}
     * is {@link PathVerb#CLOSE}, last point is set to (0, 0) before adding cubic.</p>
     *
     * <p>Appends {@link PathVerb#MOVE} to verb array and (0, 0) to Point array,
     * if needed; then appends {@link PathVerb#CUBIC} to verb array; and appends cubic
     * control and cubic end to Point array.</p>
     *
     * <p>Cubic control is last point plus vector (dx1, dy1).</p>
     *
     * <p>Cubic end is last point plus vector (dx2, dy2).</p>
     *
     * <p>Function name stands for "relative cubic to".</p>
     *
     * @param p1  offset from last point to first cubic control
     * @param p2  offset from last point to second cubic control
     * @param p3  offset from last point to cubic end
     * @return    reference to PathBuilder
     */
    @NotNull @Contract("_, _, _ -> this")
    public PathBuilder rCubicTo(@NotNull Point p1, @NotNull Point p2, @NotNull Point p3) {
        return rCubicTo(p1._x, p1._y, p2._x, p2._y, p3._x, p3._y);
    }

    /**
     * <p>Appends arc to PathBuilder, relative to last PathBuilder Point. Arc is implemented by one or
     * more conic, weighted to describe part of oval with radii (rx, ry) rotated by
     * xAxisRotate degrees. Arc curves from last PathBuilder Point to relative end Point:
     * (dx, dy), choosing one of four possible routes: clockwise or
     * counterclockwise, and smaller or larger. If PathBuilder is empty, the start arc Point
     * is (0, 0).</p>
     *
     * <p>Arc sweep is always less than 360 degrees. rEllipticalArcTo() appends line to end Point
     * if either radii are zero, or if last PathBuilder Point equals end Point.
     * rEllipticalArcTo() scales radii (rx, ry) to fit last PathBuilder Point and end Point if both are
     * greater than zero but too small to describe an arc.</p>
     *
     * <p>rEllipticalArcTo() appends up to four conic curves.</p>
     *
     * <p>rEllipticalArcTo() implements the functionality of svg arc, although SVG "sweep-flag" value is
     * opposite the integer value of sweep; SVG "sweep-flag" uses 1 for clockwise, while
     * {@link PathDirection#CLOCKWISE} cast to int is zero.</p>
     *
     * @param rx           radius before x-axis rotation
     * @param ry           radius before x-axis rotation
     * @param xAxisRotate  x-axis rotation in degrees; positive values are clockwise
     * @param arc          chooses smaller or larger arc
     * @param direction    chooses clockwise or counterclockwise arc
     * @param dx           x-axis offset end of arc from last PathBuilder Point
     * @param dy           y-axis offset end of arc from last PathBuilder Point
     * @return             reference to PathBuilder
     */
    @NotNull @Contract("_, _, _, _, _, _, _ -> this")
    public PathBuilder rEllipticalArcTo(float rx, float ry, float xAxisRotate, @NotNull PathEllipseArc arc, @NotNull PathDirection direction, float dx, float dy) {
        Stats.onNativeCall();
        _nREllipticalArcTo(_ptr, rx, ry, xAxisRotate, arc.ordinal(), direction.ordinal(), dx, dy);
        return this;
    }

    /**
     * <p>Appends arc to PathBuilder. Arc added is part of ellipse
     * bounded by oval, from startAngle through sweepAngle. Both startAngle and
     * sweepAngle are measured in degrees, where zero degrees is aligned with the
     * positive x-axis, and positive sweeps extends arc clockwise.</p>
     *
     * <p>arcTo() adds line connecting PathBuilder last Point to initial arc Point if forceMoveTo
     * is false and PathBuilder is not empty. Otherwise, added contour begins with first point
     * of arc. Angles greater than -360 and less than 360 are treated modulo 360.</p>
     *
     * @param oval         bounds of ellipse containing arc
     * @param startAngle   starting angle of arc in degrees
     * @param sweepAngle   sweep, in degrees. Positive is clockwise; treated modulo 360
     * @param forceMoveTo  true to start a new contour with arc
     * @return             reference to PathBuilder
     */
    @NotNull @Contract("_, _, _, _ -> this")
    public PathBuilder arcTo(@NotNull Rect oval, float startAngle, float sweepAngle, boolean forceMoveTo) {
        Stats.onNativeCall();
        _nArcTo(_ptr, oval._left, oval._top, oval._right, oval._bottom, startAngle, sweepAngle, forceMoveTo);
        return this;
    }

    /**
     * <p>Appends arc to PathBuilder, after appending line if needed. Arc is implemented by conic
     * weighted to describe part of circle. Arc is contained by tangent from
     * last PathBuilder point to (x1, y1), and tangent from (x1, y1) to (x2, y2). Arc
     * is part of circle sized to radius, positioned so it touches both tangent lines.</p>
     *
     * <p>If last PathBuilder Point does not start Arc, tangentArcTo appends connecting Line to PathBuilder.
     * The length of Vector from (x1, y1) to (x2, y2) does not affect Arc.</p>
     *
     * <p>Arc sweep is always less than 180 degrees. If radius is zero, or if
     * tangents are nearly parallel, tangentArcTo appends Line from last PathBuilder Point to (x1, y1).</p>
     *
     * <p>tangentArcTo appends at most one Line and one conic.</p>
     *
     * <p>tangentArcTo implements the functionality of PostScript arct and HTML Canvas tangentArcTo.</p>
     *
     * @param x1      x-axis value common to pair of tangents
     * @param y1      y-axis value common to pair of tangents
     * @param x2      x-axis value end of second tangent
     * @param y2      y-axis value end of second tangent
     * @param radius  distance from arc to circle center
     * @return        reference to PathBuilder
     */
    @NotNull @Contract("_, _, _, _, _ -> this")
    public PathBuilder tangentArcTo(float x1, float y1, float x2, float y2, float radius) {
        Stats.onNativeCall();
        _nTangentArcTo(_ptr, x1, y1, x2, y2, radius);
        return this;
    }

    /**
     * <p>Appends arc to PathBuilder, after appending line if needed. Arc is implemented by conic
     * weighted to describe part of circle. Arc is contained by tangent from
     * last PathBuilder point to p1, and tangent from p1 to p2. Arc
     * is part of circle sized to radius, positioned so it touches both tangent lines.</p>
     *
     * <p>If last PathBuilder Point does not start arc, tangentArcTo() appends connecting line to PathBuilder.
     * The length of vector from p1 to p2 does not affect arc.</p>
     *
     * <p>Arc sweep is always less than 180 degrees. If radius is zero, or if
     * tangents are nearly parallel, tangentArcTo() appends line from last PathBuilder Point to p1.</p>
     *
     * <p>tangentArcTo() appends at most one line and one conic.</p>
     *
     * <p>tangentArcTo() implements the functionality of PostScript arct and HTML Canvas tangentArcTo.</p>
     *
     * @param p1      Point common to pair of tangents
     * @param p2      end of second tangent
     * @param radius  distance from arc to circle center
     * @return        reference to PathBuilder
     */
    @NotNull @Contract("_, _, _ -> this")
    public PathBuilder tangentArcTo(@NotNull Point p1, @NotNull Point p2, float radius) {
        return tangentArcTo(p1._x, p1._y, p2._x, p2._y, radius);
    }

    /** <p>Appends arc to PathBuilder. Arc is implemented by one or more conics weighted to
     * describe part of oval with radii (rx, ry) rotated by xAxisRotate degrees. Arc
     * curves from last PathBuilder Point to (x, y), choosing one of four possible routes:
     * clockwise or counterclockwise, and smaller or larger.</p>
     *
     * <p>Arc sweep is always less than 360 degrees. ellipticalArcTo() appends line to (x, y) if
     * either radii are zero, or if last PathBuilder Point equals (x, y). ellipticalArcTo() scales radii
     * (rx, ry) to fit last PathBuilder Point and (x, y) if both are greater than zero but
     * too small.</p>
     *
     * <p>ellipticalArcTo() appends up to four conic curves.</p>
     *
     * <p>ellipticalArcTo() implements the functionality of SVG arc, although SVG sweep-flag value
     * is opposite the integer value of sweep; SVG sweep-flag uses 1 for clockwise,
     * while {@link PathDirection#CLOCKWISE} cast to int is zero.</p>
     *
     * @param rx           radius on x-axis before x-axis rotation
     * @param ry           radius on y-axis before x-axis rotation
     * @param xAxisRotate  x-axis rotation in degrees; positive values are clockwise
     * @param arc          chooses smaller or larger arc
     * @param direction    chooses clockwise or counterclockwise arc
     * @param x            end of arc
     * @param y            end of arc
     * @return             reference to PathBuilder
     */
    @NotNull @Contract("_, _, _, _, _, _, _ -> this")
    public PathBuilder ellipticalArcTo(float rx, float ry, float xAxisRotate, @NotNull PathEllipseArc arc, @NotNull PathDirection direction, float x, float y) {
        Stats.onNativeCall();
        _nEllipticalArcTo(_ptr, rx, ry, xAxisRotate, arc.ordinal(), direction.ordinal(), x, y);
        return this;
    }

    /**
     * <p>Appends arc to PathBuilder. Arc is implemented by one or more conic weighted to describe
     * part of oval with radii (r.fX, r.fY) rotated by xAxisRotate degrees. Arc curves
     * from last PathBuilder Point to (xy.fX, xy.fY), choosing one of four possible routes:
     * clockwise or counterclockwise, and smaller or larger.</p>
     *
     * <p>Arc sweep is always less than 360 degrees. ellipticalArcTo() appends line to xy if either
     * radii are zero, or if last PathBuilder Point equals (xy.fX, xy.fY). ellipticalArcTo() scales radii r to
     * fit last PathBuilder Point and xy if both are greater than zero but too small to describe
     * an arc.</p>
     *
     * <p>ellipticalArcTo() appends up to four conic curves.</p>
     *
     * <p>ellipticalArcTo() implements the functionality of SVG arc, although SVG sweep-flag value is
     * opposite the integer value of sweep; SVG sweep-flag uses 1 for clockwise, while
     * {@link PathDirection#CLOCKWISE} cast to int is zero.</p>
     *
     * @param r            radii on axes before x-axis rotation
     * @param xAxisRotate  x-axis rotation in degrees; positive values are clockwise
     * @param arc          chooses smaller or larger arc
     * @param direction    chooses clockwise or counterclockwise arc
     * @param xy           end of arc
     * @return             reference to PathBuilder
     */
    @NotNull @Contract("_, _, _, _, _ -> this")
    public PathBuilder ellipticalArcTo(@NotNull Point r, float xAxisRotate, @NotNull PathEllipseArc arc, @NotNull PathDirection direction, @NotNull Point xy) {
        return ellipticalArcTo(r._x, r._y, xAxisRotate, arc, direction, xy._x, xy._y);
    }

    /**
     * Appends an arc to the path, as a new contour. Arc is implemented as a portion of an oval.
     *
     * @param oval        rectangle bounding the oval
     * @param startAngle  starting angle in degrees
     * @param sweepAngle  sweep angle in degrees
     * @return            reference to PathBuilder
     */
    @NotNull @Contract("_, _, _ -> this")
    public PathBuilder addArc(@NotNull Rect oval, float startAngle, float sweepAngle) {
        Stats.onNativeCall();
        _nAddArc(_ptr, oval._left, oval._top, oval._right, oval._bottom, startAngle, sweepAngle);
        return this;
    }

    @NotNull @Contract("_, _, _, _ -> this")
    public PathBuilder addLine(float x1, float y1, float x2, float y2) {
        return moveTo(x1, y1).lineTo(x2, y2);
    }

    @NotNull @Contract("_, _ -> this")
    public PathBuilder addLine(@NotNull Point a, @NotNull Point b) {
        return moveTo(a).lineTo(b);
    }

    /**
     * Adds a new contour to the PathBuilder, defined by the rect. The verbs added to the path will be:
     *
     * {@link PathVerb#MOVE}, {@link PathVerb#LINE}, {@link PathVerb#LINE}, {@link PathVerb#LINE}, {@link PathVerb#CLOSE}
     *
     * @param rect  rectangle to add
     * @return      reference to PathBuilder
     */
    @NotNull @Contract("_ -> this")
    public PathBuilder addRect(@NotNull Rect rect) {
        return addRect(rect, PathDirection.COUNTER_CLOCKWISE, 0);
    }

    /**
     * Adds a new contour to the PathBuilder, defined by the rect, and wound in
     * the specified direction. The verbs added to the path will be:
     *
     * {@link PathVerb#MOVE}, {@link PathVerb#LINE}, {@link PathVerb#LINE}, {@link PathVerb#LINE}, {@link PathVerb#CLOSE}
     *
     * @param rect  rectangle to add
     * @param dir   winding direction for the rectangle
     * @return      reference to PathBuilder
     */
    @NotNull @Contract("_, _ -> this")
    public PathBuilder addRect(@NotNull Rect rect, @NotNull PathDirection dir) {
        return addRect(rect, dir, 0);
    }

    /**
     * Adds a new contour to the PathBuilder, defined by the rect, and wound in
     * the specified direction. The verbs added to the path will be:
     *
     * {@link PathVerb#MOVE}, {@link PathVerb#LINE}, {@link PathVerb#LINE}, {@link PathVerb#LINE}, {@link PathVerb#CLOSE}
     *
     *  start specifies which corner to begin the contour:
     *      0: upper-left  corner
     *      1: upper-right corner
     *      2: lower-right corner
     *      3: lower-left  corner
     *
     * This start point also acts as the implied beginning of the subsequent,
        contour, if it does not have an explicit moveTo(). e.g.
     *
     *      path.addRect(...)
     *      // if we don't say moveTo() here, we will use the rect's start point
     *      path.lineTo(...)
     *
     * @param rect   rectangle to add
     * @param dir    winding direction for the rectangle
     * @param start  initial corner of the rectangle (0-3)
     * @return       reference to PathBuilder
     */
    @NotNull @Contract("_, _, _ -> this")
    public PathBuilder addRect(@NotNull Rect rect, @NotNull PathDirection dir, int start) {
        Stats.onNativeCall();
        _nAddRect(_ptr, rect._left, rect._top, rect._right, rect._bottom, dir.ordinal(), start);
        return this;
    }

    /**
     * Adds an oval to the path, as a new contour.
     *
     * @param oval  rectangle bounding the oval
     * @return      reference to PathBuilder
     */
    @NotNull @Contract("_ -> this")
    public PathBuilder addOval(@NotNull Rect oval) {
        return addOval(oval, PathDirection.COUNTER_CLOCKWISE, 1);
    }

    /**
     * Adds an oval to the path, as a new contour, with the specified winding direction.
     *
     * @param oval  rectangle bounding the oval
     * @param dir   winding direction for the oval
     * @return      reference to PathBuilder
     */
    @NotNull @Contract("_, _ -> this")
    public PathBuilder addOval(@NotNull Rect oval, @NotNull PathDirection dir) {
        return addOval(oval, dir, 1);
    }

    /**
     * Adds an oval to the path, as a new contour, with the specified winding direction
     * and starting point.
     *
     * @param oval   rectangle bounding the oval
     * @param dir    winding direction for the oval
     * @param start  starting point index (0-3)
     * @return       reference to PathBuilder
     */
    @NotNull @Contract("_, _, _ -> this")
    public PathBuilder addOval(@NotNull Rect oval, @NotNull PathDirection dir, int start) {
        Stats.onNativeCall();
        _nAddOval(_ptr, oval._left, oval._top, oval._right, oval._bottom, dir.ordinal(), start);
        return this;
    }

    /**
     * Adds a rounded rectangle to the path, as a new contour.
     *
     * @param rrect  rounded rectangle to add
     * @return       reference to PathBuilder
     */
    @NotNull @Contract("_ -> this")
    public PathBuilder addRRect(@NotNull RRect rrect) {
        return addRRect(rrect, PathDirection.COUNTER_CLOCKWISE);
    }

    /**
     * Adds a rounded rectangle to the path, as a new contour, with the specified winding direction.
     *
     * @param rrect  rounded rectangle to add
     * @param dir    winding direction
     * @return       reference to PathBuilder
     */
    @NotNull @Contract("_, _ -> this")
    public PathBuilder addRRect(@NotNull RRect rrect, @NotNull PathDirection dir) {
        return addRRect(rrect, dir, 0);
    }

    /**
     * Adds a rounded rectangle to the path, as a new contour, with the specified winding direction
     * and starting point.
     *
     * @param rrect  rounded rectangle to add
     * @param dir    winding direction
     * @param start  starting point index
     * @return       reference to PathBuilder
     */
    @NotNull @Contract("_, _, _ -> this")
    public PathBuilder addRRect(@NotNull RRect rrect, @NotNull PathDirection dir, int start) {
        Stats.onNativeCall();
        _nAddRRect(_ptr, rrect._left, rrect._top, rrect._right, rrect._bottom, rrect._radii, dir.ordinal(), start);
        return this;
    }

    /**
     * Adds a circle to the path, as a new contour.
     *
     * @param x       center x-coordinate
     * @param y       center y-coordinate
     * @param radius  radius of the circle
     * @return        reference to PathBuilder
     */
    @NotNull @Contract("_, _, _ -> this")
    public PathBuilder addCircle(float x, float y, float radius) {
        return addCircle(x, y, radius, PathDirection.COUNTER_CLOCKWISE);
    }

    /**
     * Adds a circle to the path, as a new contour.
     *
     * @param center  center point
     * @param radius  radius of the circle
     * @return        reference to PathBuilder
     */
    @NotNull @Contract("_, _ -> this")
    public PathBuilder addCircle(@NotNull Point center, float radius) {
        return addCircle(center._x, center._y, radius, PathDirection.COUNTER_CLOCKWISE);
    }

    /**
     * Adds a circle to the path, as a new contour, with the specified winding direction.
     *
     * @param x       center x-coordinate
     * @param y       center y-coordinate
     * @param radius  radius of the circle
     * @param dir     winding direction for the circle
     * @return        reference to PathBuilder
     */
    @NotNull @Contract("_, _, _ -> this")
    public PathBuilder addCircle(float x, float y, float radius, @NotNull PathDirection dir) {
        Stats.onNativeCall();
        _nAddCircle(_ptr, x, y, radius, dir.ordinal());
        return this;
    }

    /**
     * Adds a circle to the path, as a new contour, with the specified winding direction.
     *
     * @param center  center point
     * @param radius  radius of the circle
     * @param dir     winding direction for the circle
     * @return        reference to PathBuilder
     */
    @NotNull @Contract("_, _, _ -> this")
    public PathBuilder addCircle(@NotNull Point center, float radius, @NotNull PathDirection dir) {
        Stats.onNativeCall();
        _nAddCircle(_ptr, center._x, center._y, radius, dir.ordinal());
        return this;
    }

    /**
     * Adds a contour created from an array of points, optionally closing the contour.
     *
     * @param pts    array of points
     * @param close  if true, close the contour
     * @return       reference to PathBuilder
     */
    @NotNull @Contract("_, _ -> this")
    public PathBuilder addPolygon(@NotNull Point[] pts, boolean close) {
        float[] coords = new float[pts.length * 2];
        for (int i = 0; i < pts.length; i++) {
            coords[i * 2] = pts[i]._x;
            coords[i * 2 + 1] = pts[i]._y;
        }
        return addPolygon(coords, close);
    }

    /**
     * Adds a contour created from an array of coordinates, optionally closing the contour.
     *
     * @param pts    array of coordinates (x1, y1, x2, y2, ...)
     * @param close  if true, close the contour
     * @return       reference to PathBuilder
     */
    @NotNull @Contract("_, _ -> this")
    public PathBuilder addPolygon(@NotNull float[] pts, boolean close) {
        Stats.onNativeCall();
        _nAddPolygon(_ptr, pts, close);
        return this;
    }

    /**
     * Adds a copy of the specified path's contents to this builder.
     *
     * @param src  path to add
     * @return     reference to PathBuilder
     */
    @NotNull @Contract("_ -> this")
    public PathBuilder addPath(@NotNull Path src) {
        return addPath(src, false);
    }

    /**
     * Adds a copy of the specified path's contents to this builder.
     *
     * @param src     path to add
     * @param extend  if true, extend the last contour of this builder with the first contour of src
     * @return        reference to PathBuilder
     */
    @NotNull @Contract("_, _ -> this")
    public PathBuilder addPath(@NotNull Path src, boolean extend) {
        try {
            Stats.onNativeCall();
            _nAddPath(_ptr, Native.getPtr(src), extend);
            return this;
        } finally {
            ReferenceUtil.reachabilityFence(src);
        }
    }

    /**
     * Adds a copy of the specified path's contents to this builder, offset by (dx, dy).
     *
     * @param src  path to add
     * @param dx   x-axis offset
     * @param dy   y-axis offset
     * @return     reference to PathBuilder
     */
    @NotNull @Contract("_, _, _ -> this")
    public PathBuilder addPath(@NotNull Path src, float dx, float dy) {
        return addPath(src, dx, dy, false);
    }

    /**
     * Adds a copy of the specified path's contents to this builder, offset.
     *
     * @param src     path to add
     * @param offset  offset
     * @return        reference to PathBuilder
     */
    @NotNull @Contract("_, _, _ -> this")
    public PathBuilder addPath(@NotNull Path src, @NotNull Point offset) {
        return addPath(src, offset._x, offset._y, false);
    }

    /**
     * Adds a copy of the specified path's contents to this builder, offset by (dx, dy).
     *
     * @param src     path to add
     * @param dx      x-axis offset
     * @param dy      y-axis offset
     * @param extend  if false, src verb array, {@link Point} array, and conic
     *                weights are added unaltered. If true, add line before
     *                appending verbs, {@link Point}, and conic weights.
     * @return        reference to PathBuilder
     */
    @NotNull @Contract("_, _, _, _ -> this")
    public PathBuilder addPath(@NotNull Path src, float dx, float dy, boolean extend) {
        try {
            Stats.onNativeCall();
            _nAddPathOffset(_ptr, Native.getPtr(src), dx, dy, extend);
            return this;
        } finally {
            ReferenceUtil.reachabilityFence(src);
        }
    }

    /**
     * Adds a copy of the specified path's contents to this builder, offset.
     *
     * @param src     path to add
     * @param offset  offset
     * @param extend  if false, src verb array, {@link Point} array, and conic
     *                weights are added unaltered. If true, add line before
     *                appending verbs, {@link Point}, and conic weights.
     * @return        reference to PathBuilder
     */
    @NotNull @Contract("_, _, _ -> this")
    public PathBuilder addPath(@NotNull Path src, @NotNull Point offset, boolean extend) {
        return addPath(src, offset._x, offset._y, extend);
    }

    /**
     * Adds a copy of the specified path's contents to this builder, transformed by the matrix.
     *
     * @param src     path to add
     * @param matrix  transformation matrix
     * @return        reference to PathBuilder
     */
    @NotNull @Contract("_, _ -> this")
    public PathBuilder addPath(@NotNull Path src, @NotNull Matrix33 matrix) {
        return addPath(src, matrix, false);
    }

    /**
     * Adds a copy of the specified path's contents to this builder, transformed by the matrix.
     *
     * @param src     path to add
     * @param matrix  transformation matrix
     * @param extend  if false, src verb array, {@link Point} array, and conic
     *                weights are added unaltered. If true, add line before
     *                appending verbs, {@link Point}, and conic weights.
     * @return        reference to PathBuilder
     */
    @NotNull @Contract("_, _, _ -> this")
    public PathBuilder addPath(@NotNull Path src, @NotNull Matrix33 matrix, boolean extend) {
        try {
            Stats.onNativeCall();
            _nAddPathTransform(_ptr, Native.getPtr(src), matrix._mat, extend);
            return this;
        } finally {
            ReferenceUtil.reachabilityFence(src);
            ReferenceUtil.reachabilityFence(matrix);
        }
    }

    /**
     * <p>Grows PathBuilder verb array and {@link Point} array to contain additional space.
     * May improve performance and use less memory by
     * reducing the number and size of allocations when creating PathBuilder.</p>
     *
     * @param extraPtCount     number of additional {@link Point} to allocate
     * @param extraVerbCount   number of additional verbs
     * @param extraConicCount  number of additional conic weights
     * @return                 reference to PathBuilder
     */
    @NotNull @Contract("_, _ ,_ -> this")
    public PathBuilder incReserve(int extraPtCount, int extraVerbCount, int extraConicCount) {
        Stats.onNativeCall();
        _nIncReserve(_ptr, extraPtCount, extraVerbCount, extraConicCount);
        return this;
    }

    /**
     * <p>Grows PathBuilder verb array and {@link Point} array to contain additional space.
     * May improve performance and use less memory by
     * reducing the number and size of allocations when creating PathBuilder.</p>
     *
     * @param extraPtCount  number of additional {@link Point} and verbs to allocate
     * @return              reference to PathBuilder
     */
    @NotNull @Contract("_ -> this")
    public PathBuilder incReserve(int extraPtCount) {
        return incReserve(extraPtCount, extraPtCount, 0);
    }

    /**
     * Offsets all points in the builder by (dx, dy).
     *
     * @param dx  x-axis offset
     * @param dy  y-axis offset
     * @return    reference to PathBuilder
     */
    @NotNull @Contract("_, _ -> this")
    public PathBuilder offset(float dx, float dy) {
        Stats.onNativeCall();
        _nOffset(_ptr, dx, dy);
        return this;
    }

    /**
     * Offsets all points in the builder.
     *
     * @param   offset offset
     * @return  reference to PathBuilder
     */
    @NotNull @Contract("_ -> this")
    public PathBuilder offset(@NotNull Point offset) {
        Stats.onNativeCall();
        _nOffset(_ptr, offset._x, offset._y);
        return this;
    }

    /**
     * Transforms all points in the builder by the specified matrix.
     *
     * @param matrix  transformation matrix
     * @return        reference to PathBuilder
     */
    @NotNull @Contract("_ -> this")
    public PathBuilder transform(@NotNull Matrix33 matrix) {
        try {
            Stats.onNativeCall();
            _nTransform(_ptr, matrix._mat);
            return this;
        } finally {
            ReferenceUtil.reachabilityFence(matrix);
        }
    }

    /**
     * Returns true if the builder is empty, or all of its points are finite.
     *
     * @return  true if all points are finite or builder is empty
     */
    public boolean isFinite() {
        try {
            Stats.onNativeCall();
            return _nIsFinite(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Toggles between winding and inverse winding for the current fill mode.
     *
     * @return  reference to PathBuilder
     */
    @NotNull @Contract("-> this")
    public PathBuilder toggleInverseFillType() {
        Stats.onNativeCall();
        _nToggleInverseFillType(_ptr);
        return this;
    }

    /**
     * Returns if {@link Path} is empty. Empty {@link PathBuilder} may have
     * {@link PathFillMode} but has no {@link Point}, {@link PathVerb}, or conic
     * weight. {@link PathBuilder()} constructs empty {@link PathBuilder};
     * {@link #reset()} makes {@link Path} empty.
     *
     * @return  true if the builder is empty
     */
    public boolean isEmpty() {
        try {
            Stats.onNativeCall();
            return _nIsEmpty(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Returns the last point in the builder, or null if {@link Point} array is
     * empty.
     *
     * @return  last point, or null if empty
     */
    @Nullable
    public Point getLastPt() {
        try {
            Stats.onNativeCall();
            return _nGetLastPt(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Change the point at the specified index (see {@link #countPoints()}).
     * If index is out of range, the call does nothing.
     *
     * @param index  which point to replace
     * @param x      x-coordinate
     * @param y      y-coordinate
     */
    @NotNull @Contract("_, _, _ -> this")
    public PathBuilder setPoint(int index, float x, float y) {
        Stats.onNativeCall();
        _nSetPoint(_ptr, index, x, y);
        return this;
    }

    /**
     * Change the point at the specified index (see {@link #countPoints()}).
     * If index is out of range, the call does nothing.
     *
     * @param index  which point to replace
     * @param p      the new point value
     */
    @NotNull @Contract("_, _ -> this")
    public PathBuilder setPoint(int index, @NotNull Point p) {
        setPoint(index, p._x, p._y);
        return this;
    }

    /**
     * Sets the last point on the path. If {@link Point} array is empty, append
     * {@link PathVerb#MOVE} to verb array and append p to {@link Point} array.
     *
     * @param x  x-coordinate
     * @param y  y-coordinate
     * @return   reference to PathBuilder
     */
    @NotNull @Contract("_, _ -> this")
    public PathBuilder setLastPt(float x, float y) {
        Stats.onNativeCall();
        _nSetLastPt(_ptr, x, y);
        return this;
    }

    /**
     * Sets the last point on the path. If {@link Point} array is empty, append
     * {@link PathVerb#MOVE} to verb array and append p to {@link Point} array.
     *
     * @param p  point to set
     * @return   reference to PathBuilder
     */
    @NotNull @Contract("_ -> this")
    public PathBuilder setLastPt(@NotNull Point p) {
        return setLastPt(p._x, p._y);
    }

    /**
     * Returns the number of points in the builder. {@link Point} count is
     * initially zero.
     *
     * @return  PathBuilder Point array length
     */
    public int countPoints() {
        try {
            Stats.onNativeCall();
            return _nCountPoints(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Returns if {@link PathFillMode} describes area outside {@link Path}
     * geometry. The inverse fill area extends indefinitely.
     *
     * @return  true if fill mode is {@link PathFillMode#INVERSE_WINDING} or
     *          {@link PathFillMode#INVERSE_EVEN_ODD}
     */
    public boolean isInverseFillType() {
        try {
            Stats.onNativeCall();
            return _nIsInverseFillType(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @NotNull
    public Point[] getPoints() {
        try {
            Stats.onNativeCall();
            return _nGetPoints(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @NotNull
    public PathVerb[] getVerbs() {
        try {
            Stats.onNativeCall();
            int[] ordinals = _nGetVerbs(_ptr);
            PathVerb[] verbs = new PathVerb[ordinals.length];
            for (int i = 0; i < ordinals.length; i++) {
                verbs[i] = PathVerb.values()[ordinals[i]];
            }
            return verbs;
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @NotNull
    public float[] getConicWeights() {
        try {
            Stats.onNativeCall();
            return _nGetConicWeights(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @ApiStatus.Internal
    public PathBuilder(long ptr, long finalizer) {
        super(ptr, finalizer);
    }

    public static native long    _nGetFinalizer();
    public static native long    _nMake();
    public static native long    _nMakeFromFillMode(int fillMode);
    public static native long    _nMakeFromPathBuilder(long builderPtr);
    public static native long    _nMakeFromPath(long pathPtr);
    public static native int     _nGetFillMode(long ptr);
    public static native Rect    _nComputeFiniteBounds(long ptr);
    public static native Rect    _nComputeTightBounds(long ptr);
    public static native long    _nSnapshot(long ptr, float[] matrix);
    public static native long    _nDetach(long ptr, float[] matrix);
    public static native void    _nSetFillMode(long ptr, int fillMode);
    public static native void    _nSetVolatile(long ptr, boolean isVolatile);
    public static native void    _nReset(long ptr);
    public static native void    _nMoveTo(long ptr, float x, float y);
    public static native void    _nLineTo(long ptr, float x, float y);
    public static native void    _nQuadTo(long ptr, float x1, float y1, float x2, float y2);
    public static native void    _nConicTo(long ptr, float x1, float y1, float x2, float y2, float w);
    public static native void    _nCubicTo(long ptr, float x1, float y1, float x2, float y2, float x3, float y3);
    public static native void    _nClosePath(long ptr);
    public static native void    _nPolylineTo(long ptr, float[] coords);
    public static native void    _nRMoveTo(long ptr, float dx, float dy);
    public static native void    _nRLineTo(long ptr, float dx, float dy);
    public static native void    _nRQuadTo(long ptr, float dx1, float dy1, float dx2, float dy2);
    public static native void    _nRConicTo(long ptr, float dx1, float dy1, float dx2, float dy2, float w);
    public static native void    _nRCubicTo(long ptr, float dx1, float dy1, float dx2, float dy2, float dx3, float dy3);
    public static native void    _nREllipticalArcTo(long ptr, float rx, float ry, float xAxisRotate, int size, int direction, float dx, float dy);
    public static native void    _nArcTo(long ptr, float left, float top, float right, float bottom, float startAngle, float sweepAngle, boolean forceMoveTo);
    public static native void    _nTangentArcTo(long ptr, float x1, float y1, float x2, float y2, float radius);
    public static native void    _nEllipticalArcTo(long ptr, float rx, float ry, float xAxisRotate, int size, int direction, float x, float y);
    public static native void    _nAddArc(long ptr, float l, float t, float r, float b, float startAngle, float sweepAngle);
    public static native void    _nAddRect(long ptr, float l, float t, float r, float b, int dir, int start);
    public static native void    _nAddOval(long ptr, float l, float t, float r, float b, int dir, int start);
    public static native void    _nAddRRect(long ptr, float l, float t, float r, float b, float[] radii, int dir, int start);
    public static native void    _nAddCircle(long ptr, float x, float y, float r, int dir);
    public static native void    _nAddPolygon(long ptr, float[] coords, boolean close);
    public static native void    _nAddPath(long ptr, long srcPtr, boolean extend);
    public static native void    _nAddPathOffset(long ptr, long srcPtr, float dx, float dy, boolean extend);
    public static native void    _nAddPathTransform(long ptr, long srcPtr, float[] matrix, boolean extend);
    public static native void    _nIncReserve(long ptr, int extraPtCount, int extraVerbCount, int extraConicCount);
    public static native void    _nOffset(long ptr, float dx, float dy);
    public static native void    _nTransform(long ptr, float[] matrix);
    public static native boolean _nIsFinite(long ptr);
    public static native void    _nToggleInverseFillType(long ptr);
    public static native boolean _nIsEmpty(long ptr);
    public static native Point   _nGetLastPt(long ptr);
    public static native void    _nSetPoint(long ptr, int index, float x, float y);
    public static native void    _nSetLastPt(long ptr, float x, float y);
    public static native int     _nCountPoints(long ptr);
    public static native boolean _nIsInverseFillType(long ptr);
    public static native Point[] _nGetPoints(long ptr);
    public static native int[]   _nGetVerbs(long ptr);
    public static native float[] _nGetConicWeights(long ptr);
}
