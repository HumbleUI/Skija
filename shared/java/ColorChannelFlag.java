package io.github.humbleui.skija;

import lombok.*;
import org.jetbrains.annotations.*;

/**
 * Used to represent the channels available in a color type or texture format as a mask.
 */
public enum ColorChannelFlag {
    RED           (0x01),
    GREEN         (0x02),
    BLUE          (0x04),
    ALPHA         (0x08),
    GRAY          (0x10),

    // Convenience values
    GRAY_ALPHA    (0x10 | 0x08),
    RG            (0x01 | 0x02),
    RGB           (0x01 | 0x02 | 0x04),
    RGBA          (0x01 | 0x02 | 0x04 | 0x08);

    @Getter
    @ApiStatus.Internal public final int _value;

    ColorChannelFlag(int value) {
        this._value = value;
    }

    @ApiStatus.Internal public static native int[] _nGetValues();
}
