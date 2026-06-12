package io.github.humbleui.skija;

import org.jetbrains.annotations.*;
import io.github.humbleui.skija.impl.*;

public class Graphics {
    static { Library.staticLoad(); }

    /**
     * Call this at process initialization time if your environment does not
     * permit static global initializers that execute code.
     * Init() is thread-safe and idempotent.
     */
    public static void init() {
        Stats.onNativeCall();
        _nInit();
    }

    /**
     * Return the max number of bytes that should be used by the font cache.
     * If the cache needs to allocate more, it will purge previous entries.
     * This max can be changed by calling {@link #setFontCacheLimit}.
     */
    public static long getFontCacheLimit() {
        Stats.onNativeCall();
        return _nGetFontCacheLimit();
    }

    /**
     * Specify the max number of bytes that should be used by the font cache.
     * If the cache needs to allocate more, it will purge previous entries.
     *
     * This function returns the previous setting, as if {@link #getFontCacheLimit}
     * had be called before the new limit was set.
     */
    public static long setFontCacheLimit(long bytes) {
        Stats.onNativeCall();
        return _nSetFontCacheLimit(bytes);
    }

    /**
     * Return the number of bytes currently used by the font cache.
     */
    public static long getFontCacheUsed() {
        Stats.onNativeCall();
        return _nGetFontCacheUsed();
    }

    /**
     * Return the number of entries in the font cache.
     * A cache "entry" is associated with each typeface + pointSize + matrix.
     */
    public static int getFontCacheCountUsed() {
        Stats.onNativeCall();
        return _nGetFontCacheCountUsed();
    }

    /**
     * Return the current limit to the number of entries in the font cache.
     * A cache "entry" is associated with each typeface + pointSize + matrix.
     */
    public static int getFontCacheCountLimit() {
        Stats.onNativeCall();
        return _nGetFontCacheCountLimit();
    }

    /**
     * Set the limit to the number of entries in the font cache, and return
     * the previous value. If this new value is lower than the previous,
     * it will automatically try to purge entries to meet the new limit.
     */
    public static int setFontCacheCountLimit(int count) {
        Stats.onNativeCall();
        return _nSetFontCacheCountLimit(count);
    }

    /**
     * For debugging purposes, this will attempt to purge the font cache. It
     * does not change the limit, but will cause subsequent font measures and
     * draws to be recreated, since they will no longer be in the cache.
     */
    public static void purgeFontCache() {
        Stats.onNativeCall();
        _nPurgeFontCache();
    }

    /**
     * If the strike cache is above the cache limit, attempt to purge strikes
     * with pinners. This should be called after clients release locks on
     * pinned strikes.
     */
    public static void purgePinnedFontCache() {
        Stats.onNativeCall();
        _nPurgePinnedFontCache();
    }

    /**
     * This function returns the memory used for temporary images and other resources.
     */
    public static long getResourceCacheTotalBytesUsed() {
        Stats.onNativeCall();
        return _nGetResourceCacheTotalBytesUsed();
    }

    /**
     * Returns the memory usage limit for the resource cache, used for temporary
     * bitmaps and other resources. Entries are purged from the cache when the memory useage
     * exceeds this limit.
     */
    public static long getResourceCacheTotalByteLimit() {
        Stats.onNativeCall();
        return _nGetResourceCacheTotalByteLimit();
    }

    /**
     * Sets the memory usage limit for the resource cache, used for temporary
     * bitmaps and other resources. Entries are purged from the cache when the memory useage
     * exceeds this limit.
     */
    public static long setResourceCacheTotalByteLimit(long newLimit) {
        Stats.onNativeCall();
        return _nSetResourceCacheTotalByteLimit(newLimit);
    }

    /**
     * For debugging purposes, this will attempt to purge the resource cache. It
     * does not change the limit.
     */
    public static void purgeResourceCache() {
        Stats.onNativeCall();
        _nPurgeResourceCache();
    }

    /**
     * When the cachable entry is very lage (e.g. a large scaled bitmap), adding it to the cache
     * can cause most/all of the existing entries to be purged. To avoid the, the client can set
     * a limit for a single allocation. If a cacheable entry would have been cached, but its size
     * exceeds this limit, then we do not attempt to cache it at all.
     *
     * Zero is the default value, meaning we always attempt to cache entries.
     */
    public static long getResourceCacheSingleAllocationByteLimit() {
        Stats.onNativeCall();
        return _nGetResourceCacheSingleAllocationByteLimit();
    }

    /**
     * When the cachable entry is very lage (e.g. a large scaled bitmap), adding it to the cache
     * can cause most/all of the existing entries to be purged. To avoid the, the client can set
     * a limit for a single allocation. If a cacheable entry would have been cached, but its size
     * exceeds this limit, then we do not attempt to cache it at all.
     *
     * Zero is the default value, meaning we always attempt to cache entries.
     */
    public static long setResourceCacheSingleAllocationByteLimit(long newLimit) {
        Stats.onNativeCall();
        return _nSetResourceCacheSingleAllocationByteLimit(newLimit);
    }

    /**
     * Free as much globally cached memory as possible. This will purge all private caches in Skia,
     * including font and image caches.
     *
     * If there are caches associated with GPU context, those will not be affected by this call.
     */
    public static void purgeAllCaches() {
        Stats.onNativeCall();
        _nPurgeAllCaches();
    }

    @ApiStatus.Internal public static native void _nInit();
    @ApiStatus.Internal public static native long _nGetFontCacheLimit();
    @ApiStatus.Internal public static native long _nSetFontCacheLimit(long bytes);
    @ApiStatus.Internal public static native long _nGetFontCacheUsed();
    @ApiStatus.Internal public static native int  _nGetFontCacheCountUsed();
    @ApiStatus.Internal public static native int  _nGetFontCacheCountLimit();
    @ApiStatus.Internal public static native int  _nSetFontCacheCountLimit(int count);
    @ApiStatus.Internal public static native void _nPurgeFontCache();
    @ApiStatus.Internal public static native void _nPurgePinnedFontCache();
    @ApiStatus.Internal public static native long _nGetResourceCacheTotalBytesUsed();
    @ApiStatus.Internal public static native long _nGetResourceCacheTotalByteLimit();
    @ApiStatus.Internal public static native long _nSetResourceCacheTotalByteLimit(long newLimit);
    @ApiStatus.Internal public static native void _nPurgeResourceCache();
    @ApiStatus.Internal public static native long _nGetResourceCacheSingleAllocationByteLimit();
    @ApiStatus.Internal public static native long _nSetResourceCacheSingleAllocationByteLimit(long newLimit);
    @ApiStatus.Internal public static native void _nPurgeAllCaches();
}
