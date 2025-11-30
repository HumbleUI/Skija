package io.github.humbleui.skija;

import org.jetbrains.annotations.*;

public enum EncodeJPEGAlphaMode {
    IGNORE,
    BLEND_ON_BLACK;

    @ApiStatus.Internal public static final EncodeJPEGAlphaMode[] _values = values();
}
