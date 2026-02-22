package io.github.humbleui.skija;

import org.jetbrains.annotations.*;
import io.github.humbleui.skija.impl.*;

public class ColorSpace extends Managed {
    static { Library.staticLoad(); }
    
    public static class _SRGBHolder {
        static { Stats.onNativeCall(); }
        public static final ColorSpace INSTANCE = new ColorSpace(_nMakeSRGB(), false);
    }

    public static ColorSpace getSRGB() {
        return _SRGBHolder.INSTANCE;
    }

    public static class _SRGBLinearHolder {
        static { Stats.onNativeCall(); }
        public static final ColorSpace INSTANCE = new ColorSpace(_nMakeSRGBLinear(), false);
    }

    public static ColorSpace getSRGBLinear() {
        return _SRGBLinearHolder.INSTANCE;
    }

    public static class _DisplayP3Holder {
        static { Stats.onNativeCall(); }
        public static final ColorSpace INSTANCE = new ColorSpace(_nMakeDisplayP3(), false);
    }

    public static ColorSpace getDisplayP3() {
        return _DisplayP3Holder.INSTANCE;
    }

    @Nullable
    public static ColorSpace makeRGB(@NotNull float[] transferFn, @NotNull Matrix33 toXYZD50) {
        assert transferFn != null : "Can't makeRGB with transferFn == null";
        assert transferFn.length == 7 : "Expected 7 transferFn elements, got " + transferFn.length;
        assert toXYZD50 != null : "Can't makeRGB with toXYZD50 == null";
        Stats.onNativeCall();
        long ptr = _nMakeRGB(transferFn, toXYZD50._mat);
        return ptr == 0 ? null : new ColorSpace(ptr);
    }

    @Nullable
    public static ColorSpace makeCICP(@NotNull ColorSpaceNamedPrimaries primaries, @NotNull ColorSpaceNamedTransferFn transferFn) {
        assert primaries != null : "Can't makeCICP with primaries == null";
        assert transferFn != null : "Can't makeCICP with transferFn == null";
        Stats.onNativeCall();
        long ptr = _nMakeCICP(primaries._value, transferFn._value);
        return ptr == 0 ? null : new ColorSpace(ptr);
    }

    public Color4f convert(ColorSpace to, Color4f color) {
        to = to == null ? getSRGB() : to;
        try {
            return new Color4f(_nConvert(_ptr, Native.getPtr(to), color.getR(), color.getG(), color.getB(), color.getA()));
        } finally {
            ReferenceUtil.reachabilityFence(this);
            ReferenceUtil.reachabilityFence(to);
            ReferenceUtil.reachabilityFence(color);
        }
    }

    @ApiStatus.Internal @Override
    public boolean _nativeEquals(Native other) {
        try {
            Stats.onNativeCall();
            return _nEquals(_ptr, Native.getPtr(other));
        } finally {
            ReferenceUtil.reachabilityFence(this);
            ReferenceUtil.reachabilityFence(other);
        }
    }

    @ApiStatus.Internal
    public ColorSpace(long ptr) {
        super(ptr, _FinalizerHolder.PTR, true);
    }

    @ApiStatus.Internal
    public ColorSpace(long ptr, boolean managed) {
        super(ptr, _FinalizerHolder.PTR, managed);
    }

    /**
     * @return  true if the color space gamma is near enough to be approximated as sRGB
     */
    public boolean isGammaCloseToSRGB() {
        try {
            Stats.onNativeCall();
            return _nIsGammaCloseToSRGB(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * @return  true if the color space gamma is linear
     */
    public boolean isGammaLinear() {
        try {
            Stats.onNativeCall();
            return _nIsGammaLinear(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * <p>Returns true if the color space is sRGB. Returns false otherwise.</p>
     *
     * <p>This allows a little bit of tolerance, given that we might see small numerical error
     * in some cases: converting ICC fixed point to float, converting white point to D50,
     * rounding decisions on transfer function and matrix.</p>
     *
     * <p>This does not consider a 2.2f exponential transfer function to be sRGB.  While these
     * functions are similar (and it is sometimes useful to consider them together), this
     * function checks for logical equality.</p>
     */
    public boolean isSRGB() {
        try {
            Stats.onNativeCall();
            return _nIsSRGB(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @Nullable
    public float[] getNumericalTransferFn() {
        try {
            Stats.onNativeCall();
            return _nIsNumericalTransferFn(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @Nullable
    public Matrix33 getToXYZD50() {
        try {
            Stats.onNativeCall();
            return _matrix33FromArray(_nGetToXYZD50(_ptr));
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    public int getToXYZD50Hash() {
        try {
            Stats.onNativeCall();
            return _nGetToXYZD50Hash(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    public int getTransferFnHash() {
        try {
            Stats.onNativeCall();
            return _nGetTransferFnHash(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    public long getHash() {
        try {
            Stats.onNativeCall();
            return _nGetHash(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @NotNull
    public ColorSpace makeLinearGamma() {
        try {
            Stats.onNativeCall();
            return new ColorSpace(_nMakeLinearGamma(_ptr));
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @NotNull
    public ColorSpace makeSRGBGamma() {
        try {
            Stats.onNativeCall();
            return new ColorSpace(_nMakeSRGBGamma(_ptr));
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @NotNull
    public ColorSpace makeColorSpin() {
        try {
            Stats.onNativeCall();
            return new ColorSpace(_nMakeColorSpin(_ptr));
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @NotNull
    public Data serializeToData() {
        try {
            Stats.onNativeCall();
            return new Data(_nSerializeToData(_ptr));
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @Nullable
    public static ColorSpace makeFromData(@NotNull Data data) {
        try {
            assert data != null : "Can't makeFromData with data == null";
            Stats.onNativeCall();
            long ptr = _nMakeFromData(Native.getPtr(data));
            return ptr == 0 ? null : new ColorSpace(ptr);
        } finally {
            ReferenceUtil.reachabilityFence(data);
        }
    }

    @NotNull
    public float[] getTransferFn() {
        try {
            Stats.onNativeCall();
            return _nTransferFn(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @NotNull
    public float[] getInvTransferFn() {
        try {
            Stats.onNativeCall();
            return _nInvTransferFn(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @NotNull
    public Matrix33 getGamutTransformTo(@NotNull ColorSpace dst) {
        try {
            assert dst != null : "Can't getGamutTransformTo with dst == null";
            Stats.onNativeCall();
            return new Matrix33(_nGamutTransformTo(_ptr, Native.getPtr(dst)));
        } finally {
            ReferenceUtil.reachabilityFence(this);
            ReferenceUtil.reachabilityFence(dst);
        }
    }

    /**
     * Create a ColorSpace from ICC profile bytes.
     *
     * @param bytes  ICC profile data
     * @return       ColorSpace instance, or null if the profile could not be parsed
     */
    @Nullable
    public static ColorSpace makeFromICCProfile(byte[] bytes) {
        Stats.onNativeCall();
        long ptr = _nMakeFromICCProfile(bytes);
        return ptr == 0 ? null : new ColorSpace(ptr);
    }

    @Nullable
    private static Matrix33 _matrix33FromArray(@Nullable float[] values) {
        return values == null ? null : new Matrix33(values);
    }

    @ApiStatus.Internal
    public static class _FinalizerHolder {
        public static final long PTR = _nGetFinalizer();
    }

    public static native long _nGetFinalizer();
    public static native long _nMakeSRGB();
    public static native long _nMakeDisplayP3();
    public static native long _nMakeSRGBLinear();
    public static native long _nMakeRGB(float[] transferFn, float[] toXYZD50);
    public static native long _nMakeCICP(int primaries, int transferFn);
    public static native float[] _nConvert(long fromPtr, long toPtr, float r, float g, float b, float a);
    public static native boolean _nIsGammaCloseToSRGB(long ptr);
    public static native boolean _nIsGammaLinear(long ptr);
    public static native boolean _nIsSRGB(long ptr);
    public static native float[] _nIsNumericalTransferFn(long ptr);
    public static native float[] _nGetToXYZD50(long ptr);
    public static native int _nGetToXYZD50Hash(long ptr);
    public static native int _nGetTransferFnHash(long ptr);
    public static native long _nGetHash(long ptr);
    public static native long _nMakeLinearGamma(long ptr);
    public static native long _nMakeSRGBGamma(long ptr);
    public static native long _nMakeColorSpin(long ptr);
    public static native long _nSerializeToData(long ptr);
    public static native long _nMakeFromData(long dataPtr);
    public static native float[] _nTransferFn(long ptr);
    public static native float[] _nInvTransferFn(long ptr);
    public static native float[] _nGamutTransformTo(long ptr, long dstPtr);
    public static native boolean _nEquals(long ptr, long otherPtr);
    public static native long _nMakeFromICCProfile(byte[] bytes);
}
