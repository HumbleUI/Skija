package io.github.humbleui.skija.svg;

import lombok.Data;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@Data
public class SVGPaint {
    @ApiStatus.Internal public final SVGPaintType _type;

    // logical union
    @ApiStatus.Internal @Nullable public final Integer _color;
    @ApiStatus.Internal @Nullable public final SVGIRI _iri;

    @ApiStatus.Internal
    public SVGPaint(SVGPaintType type, Integer color, SVGIRI iri) {
        _type = type;
        _color = color;
        _iri = iri;
    }

    public SVGPaint() {
        this(SVGPaintType.NONE, 0x000000, null);
    }

    public SVGPaint(SVGPaintType type) {
        this(type, 0x000000, null);
    }

    public SVGPaint(int color) {
        this(SVGPaintType.COLOR, color, null);
    }

    public SVGPaint(SVGIRI iri, int fallbackColor) {
        this(SVGPaintType.IRI, fallbackColor, iri);
    }
}
