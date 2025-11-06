package io.github.humbleui.skija;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.*;

@AllArgsConstructor @Data
public class RuntimeEffectOptions {
    public static final RuntimeEffectOptions DEFAULT = new RuntimeEffectOptions(false, null);

    /**
     * For testing purposes, disables optimization and inlining. (Normally, Runtime Effects
     * don't run the inliner directly, but they still get an inlining pass once they are
     * painted.)
     */
    public final boolean _forceUnoptimized;

    /**
     * When possible this name will be used to identify the created runtime effect.
     */
    @Nullable
    public final String _name;

    public RuntimeEffectOptions(boolean forceUnoptimized) {
        this(forceUnoptimized, null);
    }

    public RuntimeEffectOptions(@Nullable String name) {
        this(false, name);
    }
}
