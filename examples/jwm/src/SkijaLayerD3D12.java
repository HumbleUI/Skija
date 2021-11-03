package io.github.humbleui.skija.examples.jwm;

import io.github.humbleui.jwm.*;
import io.github.humbleui.skija.*;

public class SkijaLayerD3D12 extends LayerD3D12 implements SkijaLayer {
    public DirectContext _directContext;
    public BackendRenderTarget _renderTarget;
    public Surface _surface;

    @Override
    public void attach(Window window) {
        super.attach(window);
        _directContext = DirectContext.makeDirect3D(getAdapterPtr(), getDevicePtr(), getQueuePtr());
    }

    @Override
    public Canvas beforePaint() {
        _renderTarget = BackendRenderTarget.makeDirect3D(
                    getWidth(),
                    getHeight(),
                    nextDrawableTexturePtr(),
                    getFormat(),
                    getSampleCount(),
                    getLevelCount());

        _surface = Surface.makeFromBackendRenderTarget(
                    _directContext,
                    _renderTarget,
                    SurfaceOrigin.TOP_LEFT,
                    SurfaceColorFormat.RGBA_8888,   // TODO proper format (without that creates null surface)
                    ColorSpace.getSRGB(),           // TODO load monitor profile
                    new SurfaceProps(PixelGeometry.RGB_H));

        return _surface.getCanvas();
    }

    @Override
    public void afterPaint() {
        // todo: call flush for present (instead of default flush)
        // since we want to have BackendSurfaceAccess::kPresent final render target layout
        _surface.flush();
        _directContext.submit(false);

        swapBuffers();

        _surface.close();
        _renderTarget.close();
    }

    @Override
    public void resize(int width, int height) {
        // HACK: if direct context is not null, we must call submit here
        // to clean up all outstanding resources in the command queue
        _directContext.submit(true);

        // Resize layer (native call to resize swap chain images)
        super.resize(width, height);
    }

    @Override
    public void close() {
        if (_directContext != null) {
            _directContext.abandon();
            _directContext.close();
        }

        super.close();
    }
}