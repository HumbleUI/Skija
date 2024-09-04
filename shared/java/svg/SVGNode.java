package io.github.humbleui.skija.svg;

import io.github.humbleui.skija.impl.Library;
import io.github.humbleui.skija.impl.RefCnt;
import io.github.humbleui.skija.impl.ReferenceUtil;
import io.github.humbleui.skija.impl.Stats;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    @Nullable
    public SVGFillRule getClipRule() {
        try {
            Stats.onNativeCall();
            if (_nHasClipRule(_ptr)) {
                Stats.onNativeCall();
                return SVGFillRule._values[_nGetClipRule(_ptr)];
            }
            else {
                return null;
            }
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @NotNull @Contract("_ -> this")
    public SVGNode setClipRule(@Nullable SVGFillRule type) {
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

    @Nullable
    public Integer getColor() {
        try {
            Stats.onNativeCall();
            if (_nHasColor(_ptr)) {
                Stats.onNativeCall();
                return _nGetColor(_ptr);
            }
            else {
                return null;
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

    @Nullable
    public SVGColorSpace getColorInterpolation() {
        try {
            Stats.onNativeCall();
            if (_nHasColorInterpolation(_ptr)) {
                Stats.onNativeCall();
                return SVGColorSpace._values[_nGetColorInterpolation(_ptr)];
            }  else {
                return null;
            }
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @NotNull @Contract("_ -> this")
    public SVGNode setColorInterpolation(@Nullable SVGColorSpace color) {
        try {
            Stats.onNativeCall();
            if (color != null) {
                _nSetColorInterpolation(_ptr, color.ordinal());
            } else {
                _nSetColorInterpolationNull(_ptr);
            }
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
        return this;
    }

    @Nullable
    public SVGColorSpace getColorInterpolationFilters() {
        try {
            Stats.onNativeCall();
            if (_nHasColorInterpolationFilters(_ptr)) {
                Stats.onNativeCall();
                return SVGColorSpace._values[_nGetColorInterpolationFilters(_ptr)];
            }
            else {
                return null;
            }
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @NotNull @Contract("_ -> this")
    public SVGNode setColorInterpolationFilters(@Nullable SVGColorSpace color) {
        try {
            Stats.onNativeCall();
            if (color != null) {
                _nSetColorInterpolationFilters(_ptr, color.ordinal());
            } else {
                _nSetColorInterpolationFiltersNull(_ptr);
            }
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
        return this;
    }

    @Nullable
    public SVGFillRule getFillRule() {
        try {
            Stats.onNativeCall();
            if (_nHasFillRule(_ptr)) {
                Stats.onNativeCall();
                return SVGFillRule._values[_nGetFillRule(_ptr)];
            }
            else {
                return null;
            }
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @NotNull @Contract("_ -> this")
    public SVGNode setFillRule(@Nullable SVGFillRule type) {
        try {
            Stats.onNativeCall();
            if (type != null) {
                _nSetFillRule(_ptr, type.ordinal());
            } else {
                _nSetFillRuleNull(_ptr);
            }
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
        return this;
    }

    @Nullable
    public SVGPaint getFill() {
        try {
            Stats.onNativeCall();
            return _nGetFill(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @NotNull @Contract("_ -> this")
    public SVGNode setFill(int color) {
        return setFill(new SVGPaint(color));
    }

    @NotNull @Contract("_ -> this")
    public SVGNode setFill(@Nullable SVGPaint paint) {
        try {
            Stats.onNativeCall();
            if (paint != null) {
                _nSetFill(_ptr,
                        paint.getType().ordinal(),
                        paint.getColor().getType().ordinal(),
                        paint.getColor().getColor(),
                        paint.getColor().getVars(),
                        paint.getIri().getType().ordinal(),
                        paint.getIri().getIri()
                );
            } else {
                _nSetFillNull(_ptr);
            }
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
        return this;
    }

    @Nullable
    public Float getFillOpacity() {
        try {
            Stats.onNativeCall();
            if (_nHasFillOpacity(_ptr)) {
                Stats.onNativeCall();
                return _nGetFillOpacity(_ptr);
            }
            else {
                return null;
            }
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @NotNull @Contract("_ -> this")
    public SVGNode setFillOpacity(@Nullable Float opacity) {
        try {
            Stats.onNativeCall();
            if (opacity != null) {
                _nSetFillOpacity(_ptr, opacity);
            } else {
                _nSetFillOpacityNull(_ptr);
            }
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
        return this;
    }

    @Nullable
    public SVGFontFamily getFontFamily() {
        try {
            Stats.onNativeCall();
            return _nGetFontFamily(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @NotNull @Contract("_ -> this")
    public SVGNode setFontFamily(@Nullable SVGFontFamily family) {
        try {
            Stats.onNativeCall();
            if (family != null) {
                _nSetFontFamily(_ptr, family._type.ordinal(), family._family);
            } else {
                _nSetFontFamilyNull(_ptr);
            }
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
        return this;
    }

    @Nullable
    public SVGFontSize getFontSize() {
        try {
            Stats.onNativeCall();
            return _nGetFontSize(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @NotNull @Contract("_ -> this")
    public SVGNode setFontSize(@Nullable SVGFontSize family) {
        try {
            Stats.onNativeCall();
            if (family != null) {
                _nSetFontSize(_ptr, family._type.ordinal(), family._size._value, family._size._unit.ordinal());
            } else {
                _nSetFontSizeNull(_ptr);
            }
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
        return this;
    }

    @Nullable
    public SVGFontStyle getFontStyle() {
        try {
            Stats.onNativeCall();
            if (_nHasFontStyle(_ptr)) {
                Stats.onNativeCall();
                return SVGFontStyle._values[_nGetFontStyle(_ptr)];
            }
            else {
                return null;
            }
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @NotNull @Contract("_ -> this")
    public SVGNode setFontStyle(@Nullable SVGFontStyle style) {
        try {
            Stats.onNativeCall();
            if (style != null) {
                _nSetFontStyle(_ptr, style.ordinal());
            } else {
                _nSetFontStyleNull(_ptr);
            }
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
        return this;
    }

    @Nullable
    public SVGFontWeight getFontWeight() {
        try {
            Stats.onNativeCall();
            if (_nHasFontWeight(_ptr)) {
                Stats.onNativeCall();
                return SVGFontWeight._values[_nGetFontWeight(_ptr)];
            }
            else {
                return null;
            }
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @NotNull @Contract("_ -> this")
    public SVGNode setFontWeight(@Nullable SVGFontWeight style) {
        try {
            Stats.onNativeCall();
            if (style != null) {
                _nSetFontWeight(_ptr, style.ordinal());
            } else {
                _nSetFontWeightNull(_ptr);
            }
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
        return this;
    }

    @Nullable
    public SVGPaint getStroke() {
        try {
            Stats.onNativeCall();
            return _nGetStroke(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @NotNull @Contract("_ -> this")
    public SVGNode setStroke(int color) {
        return setStroke(new SVGPaint(color));
    }

    @NotNull @Contract("_ -> this")
    public SVGNode setStroke(@Nullable SVGPaint paint) {
        try {
            Stats.onNativeCall();
            if (paint != null) {
                _nSetStroke(_ptr,
                        paint.getType().ordinal(),
                        paint.getColor().getType().ordinal(),
                        paint.getColor().getColor(),
                        paint.getColor().getVars(),
                        paint.getIri().getType().ordinal(),
                        paint.getIri().getIri()
                );
            } else {
                _nSetStrokeNull(_ptr);
            }
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
        return this;
    }

    @Nullable
    public SVGLength getStrokeWidth() {
        try {
            Stats.onNativeCall();
            return _nGetStrokeWidth(_ptr);
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

    @ApiStatus.Internal public static native boolean _nHasColorInterpolation(long ptr);
    @ApiStatus.Internal public static native int _nGetColorInterpolation(long ptr);
    @ApiStatus.Internal public static native void _nSetColorInterpolation(long ptr, int color);
    @ApiStatus.Internal public static native void _nSetColorInterpolationNull(long ptr);

    @ApiStatus.Internal public static native boolean _nHasColorInterpolationFilters(long ptr);
    @ApiStatus.Internal public static native int _nGetColorInterpolationFilters(long ptr);
    @ApiStatus.Internal public static native void _nSetColorInterpolationFilters(long ptr, int color);
    @ApiStatus.Internal public static native void _nSetColorInterpolationFiltersNull(long ptr);

    @ApiStatus.Internal public static native boolean _nHasFillRule(long ptr);
    @ApiStatus.Internal public static native int _nGetFillRule(long ptr);
    @ApiStatus.Internal public static native void _nSetFillRule(long ptr, int type);
    @ApiStatus.Internal public static native void _nSetFillRuleNull(long ptr);

    @ApiStatus.Internal public static native SVGPaint _nGetFill(long ptr);
    @ApiStatus.Internal public static native void _nSetFill(long ptr, int type, int colorType, int color, String[] vars, int iriType, String iri);
    @ApiStatus.Internal public static native void _nSetFillNull(long ptr);

    @ApiStatus.Internal public static native boolean _nHasFillOpacity(long ptr);
    @ApiStatus.Internal public static native float _nGetFillOpacity(long ptr);
    @ApiStatus.Internal public static native void _nSetFillOpacity(long ptr, float opacity);
    @ApiStatus.Internal public static native void _nSetFillOpacityNull(long ptr);

    @ApiStatus.Internal public static native SVGFontFamily _nGetFontFamily(long ptr);
    @ApiStatus.Internal public static native void _nSetFontFamily(long ptr, int type, String family);
    @ApiStatus.Internal public static native void _nSetFontFamilyNull(long ptr);

    @ApiStatus.Internal public static native SVGFontSize _nGetFontSize(long ptr);
    @ApiStatus.Internal public static native void _nSetFontSize(long ptr, int type, float value, int unit);
    @ApiStatus.Internal public static native void _nSetFontSizeNull(long ptr);

    @ApiStatus.Internal public static native boolean _nHasFontStyle(long ptr);
    @ApiStatus.Internal public static native int _nGetFontStyle(long ptr);
    @ApiStatus.Internal public static native void _nSetFontStyle(long ptr, int type);
    @ApiStatus.Internal public static native void _nSetFontStyleNull(long ptr);

    @ApiStatus.Internal public static native boolean _nHasFontWeight(long ptr);
    @ApiStatus.Internal public static native int _nGetFontWeight(long ptr);
    @ApiStatus.Internal public static native void _nSetFontWeight(long ptr, int type);
    @ApiStatus.Internal public static native void _nSetFontWeightNull(long ptr);

    @ApiStatus.Internal public static native SVGPaint _nGetStroke(long ptr);
    @ApiStatus.Internal public static native void _nSetStroke(long ptr, int type, int colorType, int color, String[] vars, int iriType, String iri);
    @ApiStatus.Internal public static native void _nSetStrokeNull(long ptr);

    @ApiStatus.Internal public static native SVGLength _nGetStrokeWidth(long ptr);
    @ApiStatus.Internal public static native void _nSetStrokeWidth(long ptr, float value, int unit);
    @ApiStatus.Internal public static native void _nSetStrokeWidthNull(long ptr);
}