package io.github.humbleui.skija.svg;

import org.jetbrains.annotations.*;
import io.github.humbleui.skija.*;
import io.github.humbleui.skija.impl.*;

public abstract class SVGContainer extends SVGTransformableNode {
    static { Library.staticLoad(); }

    @ApiStatus.Internal
    public SVGContainer(long ptr) {
        super(ptr);
    }
}