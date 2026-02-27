package io.github.humbleui.skija;

import org.jetbrains.annotations.*;

public enum ColorSpaceNamedTransferFn {
    REC709(1),
    REC470_SYSTEM_M(4),
    REC470_SYSTEM_BG(5),
    REC601(6),
    SMPTE_ST_240(7),
    LINEAR(8),
    IEC61966_2_4(11),
    SRGB(13),
    REC2020_10BIT(14),
    REC2020_12BIT(15),
    PQ(16),
    SMPTE_ST_428_1(17),
    HLG(18);

    @ApiStatus.Internal
    public final int _value;

    ColorSpaceNamedTransferFn(int value) {
        _value = value;
    }
}
