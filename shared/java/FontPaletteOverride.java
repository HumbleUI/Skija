package io.github.humbleui.skija;

import lombok.*;

@lombok.Data
public class FontPaletteOverride {
    public static final FontPaletteOverride[] EMPTY = new FontPaletteOverride[0];

    public final int _index;
    public final int _color;
}