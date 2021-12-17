package io.github.humbleui.skija.paragraph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.With;

import io.github.humbleui.skija.*;
import io.github.humbleui.types.*;

@AllArgsConstructor @Data @With
public class Shadow {
    public final int    _color;
    public final float  _offsetX;
    public final float  _offsetY;
    public final double _blurSigma;

    public Shadow(int color, Point offset, double blurSigma) {
        this(color, offset.getX(), offset.getY(), blurSigma);
    }

    public Point getOffset() {
        return new Point(_offsetX, _offsetY);
    }
}
