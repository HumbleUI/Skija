package io.github.humbleui.skija;

import org.jetbrains.annotations.*;

public enum SaveLayerRecFlag {
    PRESERVE_LCD_TEXT(1 << 1),

    /**
     * initializes with previous contents
     */
    INIT_WITH_PREVIOUS(1 << 2),

    /**
     * instead of matching previous layer's colortype, use F16
     */
    F16_COLOR_TYPE(1 << 4);

    @ApiStatus.Internal public static final SaveLayerRecFlag[] _values = values();

    @ApiStatus.Internal public final int _flag;

    SaveLayerRecFlag(int flag) {
        this._flag = flag;
    }
}