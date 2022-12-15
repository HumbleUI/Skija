package io.github.humbleui.skija.shaper;

import org.jetbrains.annotations.*;
import io.github.humbleui.skija.*;
import io.github.humbleui.skija.impl.*;
import io.github.humbleui.types.*;

public class TextLineRunHandler<T> extends Managed implements RunHandler {
    static { Library.staticLoad(); }

    @ApiStatus.Internal
    public final ManagedString _text;

    @ApiStatus.Internal
    public TextLineRunHandler(ManagedString text, boolean manageText) {
        super(_nMake(Native.getPtr(text)), _FinalizerHolder.PTR);
        _text = manageText ? text : null;
        ReferenceUtil.reachabilityFence(text);
    }

    public TextLineRunHandler(String text) {
        this(new ManagedString(text), true);
    }

    @Override
    public void close() {
        super.close();
        if (_text != null)
            _text.close();
    }

    @Override
    public void beginLine() {
        throw new UnsupportedOperationException("TextLineRunHandler::beginLine");
    }

    @Override
    public void runInfo(RunInfo info) {
        throw new UnsupportedOperationException("TextLineRunHandler::runInfo");
    }

    @Override
    public void commitRunInfo() {
        throw new UnsupportedOperationException("TextLineRunHandler::commitRunInfo");
    }

    @Override
    public Point runOffset(RunInfo info) {
        throw new UnsupportedOperationException("TextLineRunHandler::runOffset");
    }

    @Override
    public void commitRun(RunInfo info, short[] glyphs, Point[] positions, int[] clusters) {
        throw new UnsupportedOperationException("TextLineRunHandler::commitRun");
    }

    @Override
    public void commitLine() {
        throw new UnsupportedOperationException("TextLineRunHandler::commitLine");
    }

    @Nullable
    public TextLine makeLine() {
        try {
            Stats.onNativeCall();
            long ptr = _nMakeLine(_ptr);
            return 0 == ptr ? null : new TextLine(ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @ApiStatus.Internal
    public static class _FinalizerHolder {
        public static final long PTR = _nGetFinalizer();
    }

    @ApiStatus.Internal public static native long _nGetFinalizer();
    @ApiStatus.Internal public static native long _nMake(long textPtr);
    @ApiStatus.Internal public static native long _nMakeLine(long ptr);
}