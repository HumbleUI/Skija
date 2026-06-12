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
     * Free as much globally cached memory as possible. This will purge all private caches in Skia,
     * including font and image caches.
     *
     * If there are caches associated with GPU context, those will not be affected by this call.
     */
    public static void purgeAllCaches() {
        Stats.onNativeCall();
        _nPurgeAllCaches();
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
     * For debugging purposes, this will attempt to purge the resource cache. It
     * does not change the limit.
     */
    public static void purgeResourceCache() {
        Stats.onNativeCall();
        _nPurgeResourceCache();
    }

    @ApiStatus.Internal public static native void _nInit();
    @ApiStatus.Internal public static native void _nPurgeAllCaches();
    @ApiStatus.Internal public static native void _nPurgeFontCache();
    @ApiStatus.Internal public static native void _nPurgeResourceCache();
}
