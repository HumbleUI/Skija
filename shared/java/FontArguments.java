package io.github.humbleui.skija;

import lombok.*;
import org.jetbrains.annotations.*;

@AllArgsConstructor
@lombok.Data
@With
public class FontArguments {
    public static final FontArguments DEFAULT = new FontArguments(0, FontVariation.EMPTY, 0, FontPaletteOverride.EMPTY);

    public final int                    _collectionIndex;
    public final FontVariation[]        _variations;
    public final int                    _paletteIndex;
    public final FontPaletteOverride[] _paletteOverrides;
}