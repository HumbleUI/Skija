package io.github.humbleui.skija.svg;

import lombok.Data;
import org.jetbrains.annotations.ApiStatus;

@Data
public class SVGPaint {
    @ApiStatus.Internal public final SVGPaintType _type;
    @ApiStatus.Internal public final int _color;
    @ApiStatus.Internal public final SVGIRI _iri;

    @ApiStatus.Internal
    public SVGPaint(SVGPaintType type, int color, SVGIRI iri) {
        _type = type;
        _color = color;
        _iri = iri;
    }

    public SVGPaint() {
        this(SVGPaintType.NONE, 0x000000, new SVGIRI());
    }

    public SVGPaint(int color) {
        this(SVGPaintType.COLOR, color, new SVGIRI());
    }

    public SVGPaint(SVGIRI iri, int fallbackColor) {
        this(SVGPaintType.IRI, fallbackColor, iri);
    }
}
