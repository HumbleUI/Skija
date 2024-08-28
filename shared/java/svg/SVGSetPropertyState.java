package io.github.humbleui.skija.svg;

public enum SVGSetPropertyState {
    UNSPECIFIED,
    INHERIT;

    public SVGPropertyState toSvgPropertyState() {
        if (this == INHERIT) {
            return SVGPropertyState.INHERIT;
        } else {
            return SVGPropertyState.VALUE;
        }
    }
}
