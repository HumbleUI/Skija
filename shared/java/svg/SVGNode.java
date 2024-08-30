package io.github.humbleui.skija.svg;

import io.github.humbleui.skija.impl.Library;
import io.github.humbleui.skija.impl.RefCnt;
import io.github.humbleui.skija.impl.ReferenceUtil;
import io.github.humbleui.skija.impl.Stats;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Note about the lack of explicit inherit in attribute API: Internally, Skia uses a wrapper type called SkSVGProperty
 * in its attribute getters and setters. SkSVGProperty contains an instance variable _fstate of type
 * SkSVGPropertyState, which can take the values _kValue, _kUnspecified, or _kInherit, corresponding to the property
 * having a value, not having a value, or explicitly specifying it should inherit its value. However, as far as I can
 * tell from the SVG spec, most attributes are inherited when they do not have a value. Furthermore, from my reading of
 * Skia's source code, there are no public methods for accessing an SkSVGProperty's state, nor does Skia's SVG render
 * procedure make any distinction between the unspecified and inherit state. It's render logic only checks if properties
 * are in the value state, otherwise it applies attribute specific inheritance logic. In order to hide this seemingly
 * unnecessary complexity, the current Java API does not provide a way to explicitly set attributes to inherit. To
 * determine whether an attribute will be inherited, please refer to the SVG spec:
 * https://www.w3.org/TR/SVG2/painting.html.
 */
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

    @NotNull
    public Optional<SVGLength> getStrokeWidth() {
        try {
            Stats.onNativeCall();
            return Optional.ofNullable(_nGetStrokeWidth(_ptr));
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @NotNull @Contract("_ -> this")
    public SVGNode setStrokeWidth(@Nullable SVGLength length) {
        try {
            Stats.onNativeCall();
            if (length != null) {
                _nSetStrokeWidthValue(_ptr, length._value, length._unit.ordinal());
            } else {
                _nSetStrokeWidthNull(_ptr);
            }
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
        return this;
    }

    @ApiStatus.Internal public static native int _nGetTag(long ptr);
    @ApiStatus.Internal public static native boolean _nParseAndSetAttribute(long ptr, String name, String value);
    @ApiStatus.Internal public static native SVGLength _nGetStrokeWidth(long ptr);
    @ApiStatus.Internal public static native void _nSetStrokeWidthValue(long ptr, float value, int unit);
    @ApiStatus.Internal public static native void _nSetStrokeWidthNull(long ptr);
}