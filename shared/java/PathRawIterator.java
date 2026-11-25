package io.github.humbleui.skija;

import org.jetbrains.annotations.*;
import io.github.humbleui.skija.impl.*;

/**
 * <p>Iterates over path verbs and points exactly as stored (SkPath::RawIter):
 * no implicit closing lines, {@link PathVerb#CLOSE} has no points.</p>
 *
 * <p>Works on its own copy of the path, so changing or closing the source
 * {@link Path} or {@link PathBuilder} does not affect it. A path with
 * non-finite points returns {@link PathVerb#DONE} right away.</p>
 */
public class PathRawIterator extends Managed {
    static { Library.staticLoad(); }

    /** Minimal length of the array passed to {@link #next(float[])}: 4 points, 2 coordinates each. */
    public static final int COORDS_BUFFER_SIZE = 8;

    @ApiStatus.Internal
    public static class _FinalizerHolder {
        public static final long PTR = _nGetFinalizer();
    }

    @ApiStatus.Internal
    public float _conicWeight;

    @ApiStatus.Internal
    public PathRawIterator(long ptr) {
        super(ptr, _FinalizerHolder.PTR);
    }

    /**
     * Iterates over a copy of the path. Cheap: point storage is shared.
     *
     * @param path  path to iterate
     */
    public PathRawIterator(@NotNull Path path) {
        this(_nMakeFromPath(Native.getPtr(path)));
        Stats.onNativeCall();
        ReferenceUtil.reachabilityFence(path);
    }

    /**
     * Iterates over a snapshot of the builder. Points are copied once.
     *
     * @param builder  builder to iterate
     */
    public PathRawIterator(@NotNull PathBuilder builder) {
        this(_nMakeFromPathBuilder(Native.getPtr(builder)));
        Stats.onNativeCall();
        ReferenceUtil.reachabilityFence(builder);
    }

    /**
     * <p>Returns the next verb and writes its points to pts as x0, y0, x1, y1, ...
     * The rest of pts stays unchanged. After the last verb, returns {@link PathVerb#DONE}.</p>
     *
     * <p>Points: MOVE: start point; LINE, QUAD, CONIC, CUBIC: previous point,
     * control points, end point; CLOSE, DONE: none. See {@link #getPointsCount(PathVerb)}.</p>
     *
     * @param pts  at least {@link #COORDS_BUFFER_SIZE} floats
     * @return     next verb
     * @throws IllegalArgumentException if pts is shorter than {@link #COORDS_BUFFER_SIZE}
     */
    @NotNull
    public PathVerb next(@NotNull float[] pts) {
        if (pts.length < COORDS_BUFFER_SIZE)
            throw new IllegalArgumentException("Expected at least " + COORDS_BUFFER_SIZE + " floats, got " + pts.length);
        try {
            Stats.onNativeCall();
            PathVerb verb = PathVerb._values[_nNext(_ptr, pts)];
            if (verb == PathVerb.CONIC) {
                Stats.onNativeCall();
                _conicWeight = _nGetConicWeight(_ptr);
            }
            return verb;
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Returns the next verb without advancing.
     *
     * @return  next verb
     */
    @NotNull
    public PathVerb peek() {
        try {
            Stats.onNativeCall();
            return PathVerb._values[_nPeek(_ptr)];
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Returns the weight of the last {@link PathVerb#CONIC} returned by
     * {@link #next(float[])}, or 0 if there was none.
     *
     * @return  conic weight
     */
    public float getConicWeight() {
        return _conicWeight;
    }

    /**
     * Returns how many points {@link #next(float[])} writes for the verb.
     *
     * @param verb  verb returned by {@link #next(float[])}
     * @return      0 to 4
     */
    public static int getPointsCount(@NotNull PathVerb verb) {
        switch (verb) {
            case MOVE:  return 1;
            case LINE:  return 2;
            case QUAD:  return 3;
            case CONIC: return 3;
            case CUBIC: return 4;
            default:    return 0;
        }
    }

    @ApiStatus.Internal public static native long  _nGetFinalizer();
    @ApiStatus.Internal public static native long  _nMakeFromPath(long pathPtr);
    @ApiStatus.Internal public static native long  _nMakeFromPathBuilder(long builderPtr);
    @ApiStatus.Internal public static native int   _nNext(long ptr, float[] pts);
    @ApiStatus.Internal public static native int   _nPeek(long ptr);
    @ApiStatus.Internal public static native float _nGetConicWeight(long ptr);
}
