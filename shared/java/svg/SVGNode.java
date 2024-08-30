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
import java.util.OptionalInt;

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
    public Optional<SVGFillRuleType> getClipRule() {
        try {
            Stats.onNativeCall();
            if (_nHasClipRule(_ptr)) {
                Stats.onNativeCall();
                return Optional.of(SVGFillRuleType._values[_nGetClipRule(_ptr)]);
            }
            else {
                return Optional.empty();
            }
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @NotNull @Contract("_ -> this")
    public SVGNode setClipRule(@Nullable SVGFillRuleType type) {
        try {
            Stats.onNativeCall();
            if (type != null) {
                _nSetClipRule(_ptr, type.ordinal());
            } else {
                _nSetClipRuleNull(_ptr);
            }
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
        return this;
    }

    @NotNull
    public OptionalInt getColor() {
        try {
            Stats.onNativeCall();
            if (_nHasColor(_ptr)) {
                Stats.onNativeCall();
                return OptionalInt.of(_nGetColor(_ptr));
            }
            else {
                return OptionalInt.empty();
            }
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @NotNull @Contract("_ -> this")
    public SVGNode setColor(@Nullable Integer color) {
        try {
            Stats.onNativeCall();
            if (color != null) {
                _nSetColor(_ptr, color);
            } else {
                _nSetColorNull(_ptr);
            }
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
        return this;
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
                _nSetStrokeWidth(_ptr, length._value, length._unit.ordinal());
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

    @ApiStatus.Internal public static native boolean _nHasClipRule(long ptr);
    @ApiStatus.Internal public static native int _nGetClipRule(long ptr);
    @ApiStatus.Internal public static native void _nSetClipRule(long ptr, int type);
    @ApiStatus.Internal public static native void _nSetClipRuleNull(long ptr);

    @ApiStatus.Internal public static native boolean _nHasColor(long ptr);
    @ApiStatus.Internal public static native int _nGetColor(long ptr);
    @ApiStatus.Internal public static native void _nSetColor(long ptr, int color);
    @ApiStatus.Internal public static native void _nSetColorNull(long ptr);

    @ApiStatus.Internal public static native SVGLength _nGetStrokeWidth(long ptr);
    @ApiStatus.Internal public static native void _nSetStrokeWidth(long ptr, float value, int unit);
    @ApiStatus.Internal public static native void _nSetStrokeWidthNull(long ptr);
}