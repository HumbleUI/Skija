package io.github.humbleui.skija.svg;

import io.github.humbleui.skija.impl.Library;
import io.github.humbleui.skija.impl.RefCnt;
import io.github.humbleui.skija.impl.ReferenceUtil;
import io.github.humbleui.skija.impl.Stats;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class SVGNode extends RefCnt {
    static { Library.staticLoad(); }

    @ApiStatus.Internal
    public SVGNode(long ptr) {
        super(ptr);
    }

    @NotNull
    public SVGTag getTag() {
        try {
            Stats.onNativeCall();
            return SVGTag._values[_nGetTag(_ptr)];
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    public boolean parseAndSetAttribute(String name, String value) {
        try {
            Stats.onNativeCall();
            return _nParseAndSetAttribute(_ptr, name, value);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    public SVGProperty<SVGLength> getStrokeWidth() {
        try {
            Stats.onNativeCall();
            return _nGetStrokeWidth(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    public SVGNode setStrokeWidth(SVGLength length) {
        return setStrokeWidth(SVGProperty.make(length));
    }

    public SVGNode setStrokeWidth(SVGProperty<SVGLength> length) {
        try {
            Stats.onNativeCall();
            if (length.getState() == SVGPropertyState.VALUE) {
                _nSetStrokeWidthValue(_ptr, length.getValue()._value, length.getValue()._unit.ordinal());
            } else {
                _nSetStrokeWidthNull(_ptr, length.getState().ordinal());
            }
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
        return this;
    }

    @ApiStatus.Internal public static native int _nGetTag(long ptr);
    @ApiStatus.Internal public static native boolean _nParseAndSetAttribute(long ptr, String name, String value);
    @ApiStatus.Internal public static native SVGProperty _nGetStrokeWidth(long ptr);
    @ApiStatus.Internal public static native void _nSetStrokeWidthValue(long ptr, float value, int unit);
    @ApiStatus.Internal public static native void _nSetStrokeWidthNull(long ptr, int state);
}