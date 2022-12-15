package io.github.humbleui.skija.svg;

import org.jetbrains.annotations.*;
import io.github.humbleui.skija.*;
import io.github.humbleui.skija.impl.*;

public abstract class SVGTransformableNode extends SVGNode {
    static { Library.staticLoad(); }

    @ApiStatus.Internal
    public SVGTransformableNode(long ptr) {
        super(ptr);
    }
}