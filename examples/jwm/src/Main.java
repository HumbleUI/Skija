package io.github.humbleui.skija.examples.jwm;

import java.util.*;
import java.util.function.*;
import lombok.*;
import org.jetbrains.annotations.*;
import io.github.humbleui.jwm.*;
import io.github.humbleui.skija.*;
import io.github.humbleui.skija.examples.scenes.*;
import io.github.humbleui.skija.impl.*;

public class Main implements Consumer<Event> {
    public Window _window;
    public SkijaLayer _layer;
    public int _xpos = 720, _ypos = 405, _width = 0, _height = 0;
    public float _scale = 1;

    public String[] _layers = null;
    public int _layerIdx = 0;

    public Main() {
        Stats.enabled = true;

        switch (io.github.humbleui.skija.impl.Platform.CURRENT) {
            case MACOS_X64:
            case MACOS_ARM64:
                _layers = new String[] { "Metal", "GL" };
                break;
            case WINDOWS:
                _layers = new String[] { "D3D12", "GL", "Raster" };
                break;
            case LINUX:
                _layers = new String[] { "GL", "Raster" };
                break;
        }

        _window = App.makeWindow();
        _window.setEventListener(this);
        changeLayer();
        var scale = _window.getScreen().getScale();
        _window.setWindowSize((int) (1440 * scale), (int) (810 * scale));
        _window.setWindowPosition((int) (240 * scale), (int) (135 * scale));
        _window.setVisible(true);
        accept(EventWindowScreenChange.INSTANCE);
        _window.requestFrame();
    }

    public void paint() {
        if (_layer == null)
            return;

        var canvas = _layer.beforePaint();
        Scenes.draw(canvas, _width, _height, _scale, Math.max(0, Math.min(_xpos, _width)), Math.max(0, Math.min(_ypos, _height)));
        _layer.afterPaint();
    }

    @SneakyThrows
    public void changeLayer() {
        if (_layer != null)
            _layer.close();

        if (HUD.extras.size() < 1)
            HUD.extras.add(null);

        String layerName = _layers[_layerIdx];
        String className = "io.github.humbleui.skija.examples.jwm.SkijaLayer" + layerName;

        _layer = (SkijaLayer) Main.class.forName(className).getDeclaredConstructor().newInstance();
        HUD.extras.set(0, new Pair("L", "Layer: " + layerName));

        _layer.attach(_window);
        _layer.reconfigure();
        _layer.resize(_window.getContentRect().getWidth(), _window.getContentRect().getHeight());
    }

    @Override
    public void accept(Event e) {
        if (e instanceof EventWindowScreenChange) {
            _layer.reconfigure();
            _scale = _window.getScreen().getScale();
            accept(new EventWindowResize(
                    _window.getWindowRect().getWidth(),
                    _window.getWindowRect().getHeight(),
                    _window.getContentRect().getWidth(),
                    _window.getContentRect().getHeight()));
        } else if (e instanceof EventWindowResize er) {
            _width = (int) (er.getContentWidth() / _scale);
            _height = (int) (er.getContentHeight() / _scale);
            _layer.resize(er.getContentWidth(), er.getContentHeight());
            paint();
        } else if (e instanceof EventMouseMove) {
            _xpos = (int) (((EventMouseMove) e).getX() / _scale);
            _ypos = (int) (((EventMouseMove) e).getY() / _scale);
        } else if (e instanceof EventKey eventKey) {
            if (eventKey.isPressed() == true) {
                switch (eventKey.getKey()) {
                    case S -> {
                        Scenes.stats = !Scenes.stats;
                        Stats.enabled = Scenes.stats;
                    }
                    case G -> {
                        System.out.println("Before GC " + Stats.allocated);
                        System.gc();
                    }
                    case L -> {
                        _layerIdx = (_layerIdx + _layers.length - 1) % _layers.length;
                        changeLayer();
                    }
                    case LEFT ->
                        Scenes.prevScene();

                    case RIGHT ->
                        Scenes.nextScene();

                    case DOWN ->
                        Scenes.currentScene().changeVariant(1);

                    case UP ->
                        Scenes.currentScene().changeVariant(-1);
                    default ->
                        System.out.println("Key pressed: " + eventKey.getKey());
                }
            }
        } else if (e instanceof EventFrame) {
            paint();
            _window.requestFrame();
        } else if (e instanceof EventWindowCloseRequest) {
            _layer.close();
            _window.close();
            App.terminate();
        }
    }

    public static void main(String[] args) {
        App.init();
        new Main();
        App.start();
    }
}
