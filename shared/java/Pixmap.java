package io.github.humbleui.skija;

import java.nio.ByteBuffer;
import org.jetbrains.annotations.*;
import io.github.humbleui.types.IRect;
import io.github.humbleui.skija.ImageInfo;
import io.github.humbleui.skija.SamplingMode;
import io.github.humbleui.skija.impl.*;
import io.github.humbleui.types.*;

public class Pixmap extends Managed implements IHasImageInfo {
    static { Library.staticLoad(); }

    @ApiStatus.Internal
    public Pixmap(long ptr, boolean managed) {
        super(ptr, _FinalizerHolder.PTR, managed);
    }

    public Pixmap() {
        this(_nMakeNull(), true);
        Stats.onNativeCall();
    }

    @Override @NotNull
    public ImageInfo getImageInfo() {
        return getInfo();
    }

    public static Pixmap make(ImageInfo info, ByteBuffer buffer, int rowBytes) {
        return make(info, BufferUtil.getPointerFromByteBuffer(buffer), rowBytes);
    }

    public static Pixmap make(ImageInfo info, long addr, int rowBytes) {
        try {
            long ptr = _nMake(
                info._width, info._height,
                info._colorInfo._colorType.ordinal(),
                info._colorInfo._alphaType.ordinal(),
                Native.getPtr(info._colorInfo._colorSpace), addr, rowBytes);
            if (ptr == 0)
                throw new IllegalArgumentException("Failed to create Pixmap.");
            return new Pixmap(ptr, true);
        } finally {
            ReferenceUtil.reachabilityFence(info._colorInfo._colorSpace);
        }
    }

    public void reset() {
        Stats.onNativeCall();
        _nReset(_ptr);
        ReferenceUtil.reachabilityFence(this);
    }

    public void reset(ImageInfo info, long addr, int rowBytes) {
        Stats.onNativeCall();
        _nResetWithInfo(_ptr,
            info._width, info._height,
            info._colorInfo._colorType.ordinal(),
            info._colorInfo._alphaType.ordinal(),
            Native.getPtr(info._colorInfo._colorSpace), addr, rowBytes);
        ReferenceUtil.reachabilityFence(this);
        ReferenceUtil.reachabilityFence(info._colorInfo._colorSpace);
    }

    public void reset(ImageInfo info, ByteBuffer buffer, int rowBytes) {
        reset(info, BufferUtil.getPointerFromByteBuffer(buffer), rowBytes);
    }

    public void setColorSpace(ColorSpace colorSpace) {
        Stats.onNativeCall();
        _nSetColorSpace(_ptr, Native.getPtr(colorSpace));
        ReferenceUtil.reachabilityFence(this);
        ReferenceUtil.reachabilityFence(colorSpace);
    }

    public boolean extractSubset(long subsetPtr, IRect area) {
        try {
            Stats.onNativeCall();
            return _nExtractSubset(_ptr, subsetPtr, area._left, area._top, area._right, area._bottom);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    public boolean extractSubset(ByteBuffer buffer, IRect area) {
        return extractSubset(BufferUtil.getPointerFromByteBuffer(buffer), area);
    }

    public ImageInfo getInfo() {
        Stats.onNativeCall();
        try {
            return _nGetInfo(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    public int getWidth() {
        Stats.onNativeCall();
        try {
            return _nGetWidth(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    public int getHeight() {
        Stats.onNativeCall();
        try {
            return _nGetHeight(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    public ColorType getColorType() {
        Stats.onNativeCall();
        try {
            return ColorType.values()[_nGetColorType(_ptr)];
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    public ColorAlphaType getAlphaType() {
        Stats.onNativeCall();
        try {
            return ColorAlphaType.values()[_nGetAlphaType(_ptr)];
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @Nullable
    public ColorSpace getColorSpace() {
        Stats.onNativeCall();
        try {
            long ptr = _nGetColorSpace(_ptr);
            return ptr == 0 ? null : new ColorSpace(ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    public int getShiftPerPixel() {
        Stats.onNativeCall();
        try {
            return _nGetShiftPerPixel(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    public int getRowBytes() {
        Stats.onNativeCall();
        try {
            return _nGetRowBytes(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    public int getRowBytesAsPixels() {
        Stats.onNativeCall();
        try {
            return _nGetRowBytesAsPixels(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    public int computeByteSize() {
        Stats.onNativeCall();
        try {
            return _nComputeByteSize(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    public boolean computeIsOpaque() {
        Stats.onNativeCall();
        try {
            return _nComputeIsOpaque(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    public int getColor(int x, int y) {
        Stats.onNativeCall();
        try {
            return _nGetColor(_ptr, x, y);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    public Color4f getColor4f(int x, int y) {
        Stats.onNativeCall();
        try {
            return _nGetColor4f(_ptr, x, y);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    public float getAlphaF(int x, int y) {
        Stats.onNativeCall();
        try {
            return _nGetAlphaF(_ptr, x, y);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    public long getAddr() {
        Stats.onNativeCall();
        try {
            return _nGetAddr(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    public long getAddrAt(int x, int y) {
        Stats.onNativeCall();
        try {
            return _nGetAddrAt(_ptr, x, y);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    public boolean readPixels(ImageInfo info, long addr, int rowBytes) {
        Stats.onNativeCall();
        try {
            return _nReadPixels(_ptr,
                info._width, info._height,
                info._colorInfo._colorType.ordinal(),
                info._colorInfo._alphaType.ordinal(),
                Native.getPtr(info._colorInfo._colorSpace), addr, rowBytes);
        } finally {
            ReferenceUtil.reachabilityFence(this);
            ReferenceUtil.reachabilityFence(info._colorInfo._colorSpace);
        }
    }

    public boolean readPixels(ImageInfo info, long addr, int rowBytes, int srcX, int srcY) {
        Stats.onNativeCall();
        try {
            return _nReadPixelsFromPoint(_ptr,
                info._width, info._height,
                info._colorInfo._colorType.ordinal(),
                info._colorInfo._alphaType.ordinal(),
                Native.getPtr(info._colorInfo._colorSpace), addr, rowBytes,
                srcX, srcY);
        } finally {
            ReferenceUtil.reachabilityFence(this);
            ReferenceUtil.reachabilityFence(info._colorInfo._colorSpace);
        }
    }

    public boolean readPixels(Pixmap pixmap) {
        Stats.onNativeCall();
        try {
            return _nReadPixelsToPixmap(_ptr, Native.getPtr(pixmap));
        } finally {
            ReferenceUtil.reachabilityFence(this);
            ReferenceUtil.reachabilityFence(pixmap);
        }
    }

    public boolean readPixels(Pixmap pixmap, int srcX, int srcY) {
        Stats.onNativeCall();
        try {
            return _nReadPixelsToPixmapFromPoint(_ptr, Native.getPtr(pixmap), srcX, srcY);
        } finally {
            ReferenceUtil.reachabilityFence(this);
            ReferenceUtil.reachabilityFence(pixmap);
        }
    }

    public boolean scalePixels(Pixmap dstPixmap, SamplingMode samplingMode) {
        Stats.onNativeCall();
        try {
            return _nScalePixels(_ptr, Native.getPtr(dstPixmap), samplingMode._pack());
        } finally {
            ReferenceUtil.reachabilityFence(this);
            ReferenceUtil.reachabilityFence(dstPixmap);
        }
    }

    public boolean erase(int color) {
        Stats.onNativeCall();
        try {
            return _nErase(_ptr, color);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    public boolean erase(int color, IRect subset) {
        Stats.onNativeCall();
        try {
            return _nEraseSubset(_ptr, color, subset._left, subset._top, subset._right, subset._bottom);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    public boolean erase(Color4f color) {
        Stats.onNativeCall();
        try {
            return _nErase4f(_ptr, color._r, color._g, color._b, color._a);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    public boolean erase(Color4f color, IRect subset) {
        Stats.onNativeCall();
        try {
            return _nEraseSubset4f(_ptr, color._r, color._g, color._b, color._a, subset._left, subset._top, subset._right, subset._bottom);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    public ByteBuffer getBuffer() {
        return BufferUtil.getByteBufferFromPointer(getAddr(), computeByteSize());
    }

    @ApiStatus.Internal
    public static class _FinalizerHolder {
        public static final long PTR = _nGetFinalizer();
    }

    public static native long _nGetFinalizer();
    public static native long _nMakeNull();
    public static native long _nMake(int width, int height, int colorType, int alphaType, long colorSpacePtr, long pixelsPtr, int rowBytes);

    public static native void _nReset(long ptr);
    public static native void _nResetWithInfo(long ptr, int width, int height, int colorType, int alphaType, long colorSpacePtr, long pixelsPtr, int rowBytes);
    public static native void _nSetColorSpace(long ptr, long colorSpacePtr);
    public static native boolean _nExtractSubset(long ptr, long subsetPtr, int l, int t, int r, int b);
    public static native ImageInfo _nGetInfo(long ptr);
    public static native int _nGetWidth(long ptr);
    public static native int _nGetHeight(long ptr);
    public static native int _nGetColorType(long ptr);
    public static native int _nGetAlphaType(long ptr);
    public static native long _nGetColorSpace(long ptr);
    public static native int _nGetRowBytes(long ptr);
    public static native long _nGetAddr(long ptr);
    public static native int _nGetRowBytesAsPixels(long ptr);
    public static native int _nGetShiftPerPixel(long ptr);
    public static native int _nComputeByteSize(long ptr);
    public static native boolean _nComputeIsOpaque(long ptr);
    public static native int _nGetColor(long ptr, int x, int y);
    public static native Color4f _nGetColor4f(long ptr, int x, int y);
    public static native float _nGetAlphaF(long ptr, int x, int y);
    public static native long _nGetAddrAt(long ptr, int x, int y);
    // methods related to C++ types(addr8/16/32/64, writable_addr8/16/32/64) not included - not needed
    public static native boolean _nReadPixels(long ptr, int width, int height, int colorType, int alphaType, long colorSpacePtr, long dstPixelsPtr, int dstRowBytes);
    public static native boolean _nReadPixelsFromPoint(long ptr, int width, int height, int colorType, int alphaType, long colorSpacePtr, long dstPixelsPtr, int dstRowBytes, int srcX, int srcY);
    public static native boolean _nReadPixelsToPixmap(long ptr, long dstPixmapPtr);
    public static native boolean _nReadPixelsToPixmapFromPoint(long ptr, long dstPixmapPtr, int srcX, int srcY);
    public static native boolean _nScalePixels(long ptr, long dstPixmapPtr, long samplingOptions);
    public static native boolean _nErase(long ptr, int color);
    public static native boolean _nEraseSubset(long ptr, int color, int l, int t, int r, int b);
    public static native boolean _nErase4f(long ptr, float r, float g, float b, float a);
    public static native boolean _nEraseSubset4f(long ptr, float r, float g, float b, float a, int l, int t, int r1, int b1);
}