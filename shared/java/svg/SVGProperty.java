package io.github.humbleui.skija.svg;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SVGProperty<T> {
    @ApiStatus.Internal @Nullable public final T _value;
    @ApiStatus.Internal public static SVGPropertyState _state;

    @ApiStatus.Internal
    public SVGProperty(@Nullable Object value, SVGPropertyState state) {
        _value = (T)value;
        _state = state;
    }

    public T getValue() {
        assert _value != null;
        return _value;
    }

    public SVGPropertyState getState() {
        return _state;
    }

    public static <T> SVGProperty<T> make(T value) {
        if (value != null) {
            return new SVGProperty<>(value, SVGPropertyState.VALUE);
        } else {
            return unspecified();
        }
    }

    public static <T> SVGProperty<T> unspecified() {
        return new SVGProperty<>(null, SVGPropertyState.UNSPECIFIED);
    }

    public static <T> SVGProperty<T> inherit() {
        return new SVGProperty<>(null, SVGPropertyState.INHERIT);
    }
}
