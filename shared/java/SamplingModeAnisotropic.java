package io.github.humbleui.skija;

import lombok.*;
import org.jetbrains.annotations.*;

@lombok.Data
public class SamplingModeAnisotropic implements SamplingMode {
    @ApiStatus.Internal public final int _maxAniso;

    public SamplingModeAnisotropic(int maxAniso) {
        assert maxAniso >= 1 : "Expected maxAniso = " + maxAniso + " >= 1";
        _maxAniso = maxAniso;
    }

    @ApiStatus.Internal @Override
    public long _pack() {
        return 0x4000000000000000L | (0x3FFFFFFFFFFFFFFFL & _maxAniso);
    }
}
