package io.github.humbleui.skija.examples.jwm;

import java.util.*;
import java.util.function.*;
import lombok.*;
import org.jetbrains.annotations.*;
import io.github.humbleui.jwm.*;
import io.github.humbleui.jwm.skija.*;
import io.github.humbleui.skija.*;
import io.github.humbleui.skija.examples.scenes.*;
import io.github.humbleui.skija.impl.*;

public class Main implements Consumer<Event> {
    public Window _window;
    public Layer _layer;
    public int _xpos = 720, _ypos = 405, _width = 0, _height = 0;
    public float _scale = 1;

    public String[] _layers = null;
    public int _layerIdx = 0;

    public Main() {
        Stats.enabled = true;

        switch (io.github.humbleui.skija.impl.OperatingSystem.CURRENT) {
            case MACOS:
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
        _window.accept(EventWindowScreenChange.INSTANCE);
    }

    public void paint(Canvas canvas) {
        Scenes.draw(canvas, _width, _height, _scale, Math.max(0, Math.min(_xpos, _width)), Math.max(0, Math.min(_ypos, _height)));
    }

    @SneakyThrows
    public void changeLayer() {
        if (HUD.extras.size() < 1)
            HUD.extras.add(null);

        String layerName = _layers[_layerIdx];
        String className = "io.github.humbleui.jwm.skija.Layer" + layerName + "Skija";

        _layer = (Layer) Main.class.forName(className).getDeclaredConstructor().newInstance();
        HUD.extras.set(0, new Pair("L", "Layer: " + layerName));
        _window.setLayer(_layer);
    }

    @Override
    public void accept(Event e) {
        if (e instanceof EventWindowScreenChange) {
            _scale = _window.getScreen().getScale();
        } else if (e instanceof EventWindowResize er) {
            _width = (int) (er.getContentWidth() / _scale);
            _height = (int) (er.getContentHeight() / _scale);            
        } else if (e instanceof EventMouseMove) {
            _xpos = (int) (((EventMouseMove) e).getX() / _scale);
            _ypos = (int) (((EventMouseMove) e).getY() / _scale);
        } else if (e instanceof EventMouseScroll ee) {
            Scenes.currentScene().onScroll(ee.getDeltaX() / _scale, ee.getDeltaY() / _scale);
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
        } else if (e instanceof EventFrameSkija ee) {
            paint(ee.getSurface().getCanvas());
            _window.requestFrame();
        } else if (e instanceof EventWindowCloseRequest) {
            _window.close();
            App.terminate();
        }
    }

    public static void main(String[] args) {
        App.start(() -> new Main());
    }
}
