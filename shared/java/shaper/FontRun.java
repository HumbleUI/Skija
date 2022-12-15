package io.github.humbleui.skija.shaper;

import lombok.*;
import org.jetbrains.annotations.*;
import io.github.humbleui.skija.*;
import io.github.humbleui.skija.impl.*;

@lombok.Data
public class FontRun {
    @ApiStatus.Internal public final int _end;
    @ApiStatus.Internal public final Font _font;

    @ApiStatus.Internal 
    public long _getFontPtr() {
        try {
            return Native.getPtr(_font);
        } finally {
            ReferenceUtil.reachabilityFence(_font);
        }
    }
}
