package io.github.humbleui.skija;

import io.github.humbleui.skija.impl.*;

/**
 * Uniform child (shader or colorFilter) in the effect's SkSL
 */
public abstract class RuntimeEffectChild extends RefCnt implements Flattenable {
    protected RuntimeEffectChild(long ptr) {
        super(ptr);
    }

    protected RuntimeEffectChild(long ptr, boolean allowClose) {
        super(ptr, allowClose);
    }
}
