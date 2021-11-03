package io.github.humbleui.skija;

public interface PathSegmentMask {
    int LINE  = 0b0001;
    int QUAD  = 0b0010;
    int CONIC = 0b0100;
    int CUBIC = 0b1000;
}
