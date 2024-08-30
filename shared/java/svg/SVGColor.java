package io.github.humbleui.skija.svg;

import lombok.Data;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

@Data
public class SVGColor {
    @ApiStatus.Internal public final SVGColorType _type;
    @ApiStatus.Internal public final int _color;
    @ApiStatus.Internal public final List<String> _vars;

    @ApiStatus.Internal
    public SVGColor(SVGColorType type, int color, List<String> vars) {
        _type = type;
        _color = color;
        _vars = vars;
    }
}
