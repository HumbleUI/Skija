package io.github.humbleui.skija.svg;

import lombok.Data;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@Data
public class SVGIRI {
    @ApiStatus.Internal public final SVGIRIType _type;
    @ApiStatus.Internal @Nullable public final String _iri;

    @ApiStatus.Internal
    public SVGIRI(SVGIRIType type, @Nullable String iri) {
        _type = type;
        _iri = iri;
    }
}
