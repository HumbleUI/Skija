package io.github.humbleui.skija.examples.jwm;

import io.github.humbleui.jwm.Layer;
import io.github.humbleui.skija.Canvas;

public interface SkijaLayer extends Layer {
    Canvas beforePaint();
    void afterPaint();
}