package io.github.humbleui.skija.examples.scenes;

import lombok.*;
import io.github.humbleui.types.*;
import io.github.humbleui.skija.*;
import io.github.humbleui.skija.shaper.*;
import io.github.humbleui.types.*;

@AllArgsConstructor
@lombok.Data
public class DebugTextRun {
    public RunInfo _info;
    public Font    _font;
    public Rect    _bounds;
    public short[] _glyphs;
    public Point[] _positions;
    public int[]   _clusters;
}
