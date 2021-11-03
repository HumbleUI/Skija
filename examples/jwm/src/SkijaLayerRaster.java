package io.github.humbleui.skija.examples.jwm;

import io.github.humbleui.jwm.LayerRaster;
import io.github.humbleui.skija.*;

public class SkijaLayerRaster extends LayerRaster implements SkijaLayer {
    public Surface _surface;

    @Override
    public Canvas beforePaint() {
        if (_surface == null) {
            ImageInfo imageInfo = ImageInfo.makeN32Premul(getWidth(), getHeight());
            _surface = Surface.makeRasterDirect(imageInfo, getPixelsPtr(), getRowBytes());
        }

        return _surface.getCanvas();
    }

    @Override
    public void afterPaint() {
        swapBuffers();
    }

    @Override
    public void resize(int width, int height) {
        if (_surface != null) {
            _surface.close();
            _surface = null;
        }

        super.resize(width, height);
    }

    @Override
    public void close() {
        if (_surface != null) {
            _surface.close();
            _surface = null;
        }

        super.close();
    }
}