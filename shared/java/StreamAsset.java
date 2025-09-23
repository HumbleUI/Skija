package io.github.humbleui.skija;

import org.jetbrains.annotations.*;
import io.github.humbleui.skija.impl.*;

public class StreamAsset extends Managed {
    static { Library.staticLoad(); }

    @ApiStatus.Internal
    public StreamAsset(long ptr) {
        super(ptr, _FinalizerHolder.PTR);
    }

    /**
     * Check if the stream has reached the end
     * @return true if at end of stream
     */
    public boolean isAtEnd() {
        try {
            Stats.onNativeCall();
            return _nIsAtEnd(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Read data from the stream
     * @param buffer byte array to read into
     * @param size number of bytes to read
     * @return actual number of bytes read
     */
    public int read(byte[] buffer, int size) {
        try {
            Stats.onNativeCall();
            return _nRead(_ptr, buffer, size);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Peek at data without advancing the stream position
     * @param buffer byte array to peek into
     * @param size number of bytes to peek
     * @return actual number of bytes peeked
     */
    public int peek(byte[] buffer, int size) {
        try {
            Stats.onNativeCall();
            return _nPeek(_ptr, buffer, size);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Skip bytes in the stream
     * @param size number of bytes to skip
     * @return actual number of bytes skipped
     */
    public int skip(int size) {
        try {
            Stats.onNativeCall();
            return _nSkip(_ptr, size);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Rewind the stream to the beginning
     * @return true if rewind was successful
     */
    public boolean rewind() {
        try {
            Stats.onNativeCall();
            return _nRewind(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Create a duplicate of this stream
     * @return new StreamAsset duplicate, or null if not supported
     */
    @Nullable
    public StreamAsset duplicate() {
        try {
            Stats.onNativeCall();
            long ptr = _nDuplicate(_ptr);
            return ptr == 0 ? null : new StreamAsset(ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Create a fork of this stream
     * @return new StreamAsset fork, or null if not supported
     */
    @Nullable
    public StreamAsset fork() {
        try {
            Stats.onNativeCall();
            long ptr = _nFork(_ptr);
            return ptr == 0 ? null : new StreamAsset(ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Check if the stream supports position operations
     * @return true if position operations are supported
     */
    public boolean hasPosition() {
        try {
            Stats.onNativeCall();
            return _nHasPosition(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Get current position in the stream
     * @return current position, or -1 if not supported
     */
    public int getPosition() {
        try {
            Stats.onNativeCall();
            return _nGetPosition(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Seek to a specific position in the stream
     * @param position position to seek to
     * @return true if seek was successful
     */
    public boolean seek(int position) {
        try {
            Stats.onNativeCall();
            return _nSeek(_ptr, position);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Move by an offset from current position
     * @param offset offset to move by
     * @return true if move was successful
     */
    public boolean move(int offset) {
        try {
            Stats.onNativeCall();
            return _nMove(_ptr, offset);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Check if the stream has a known length
     * @return true if length is available
     */
    public boolean hasLength() {
        try {
            Stats.onNativeCall();
            return _nHasLength(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Get the length of the stream
     * @return stream length, or -1 if not available
     */
    public int getLength() {
        try {
            Stats.onNativeCall();
            return _nGetLength(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * Get the memory base address (if stream is memory-based)
     * @return memory base address, or 0 if not memory-based
     */
    public long getMemoryBase() {
        try {
            Stats.onNativeCall();
            return _nGetMemoryBase(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    // Native methods
    @ApiStatus.Internal public static native long _nGetFinalizer();
    @ApiStatus.Internal public static native boolean _nIsAtEnd(long ptr);
    @ApiStatus.Internal public static native int _nRead(long ptr, byte[] buffer, int size);
    @ApiStatus.Internal public static native int _nPeek(long ptr, byte[] buffer, int size);
    @ApiStatus.Internal public static native int _nSkip(long ptr, int size);
    @ApiStatus.Internal public static native boolean _nRewind(long ptr);
    @ApiStatus.Internal public static native long _nDuplicate(long ptr);
    @ApiStatus.Internal public static native long _nFork(long ptr);
    @ApiStatus.Internal public static native boolean _nHasPosition(long ptr);
    @ApiStatus.Internal public static native int _nGetPosition(long ptr);
    @ApiStatus.Internal public static native boolean _nSeek(long ptr, int position);
    @ApiStatus.Internal public static native boolean _nMove(long ptr, int offset);
    @ApiStatus.Internal public static native boolean _nHasLength(long ptr);
    @ApiStatus.Internal public static native int _nGetLength(long ptr);
    @ApiStatus.Internal public static native long _nGetMemoryBase(long ptr);

    @ApiStatus.Internal
    static class _FinalizerHolder {
        static final long PTR = _nGetFinalizer();
    }
}