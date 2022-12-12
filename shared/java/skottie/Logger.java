package io.github.humbleui.skija.skottie;

import org.jetbrains.annotations.*;
import io.github.humbleui.skija.*;
import io.github.humbleui.skija.impl.*;

/**
 * <p>A Logger subclass can be used to receive
 * {@link io.github.humbleui.skija.skottie.AnimationBuilder} parsing errors and warnings.</p>
 */
public abstract class Logger extends RefCnt {
    static { Library.staticLoad(); }

    public Logger() {
        super(_nMake());
        Stats.onNativeCall();
        Stats.onNativeCall();
        _nInit(_ptr);
    }

    @ApiStatus.OverrideOnly
    public abstract void log(LogLevel level, String message, @Nullable String json);

    @ApiStatus.Internal public static native long _nMake();
    @ApiStatus.Internal public        native void _nInit(long ptr);
}