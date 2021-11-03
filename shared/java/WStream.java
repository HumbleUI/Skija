package io.github.humbleui.skija;

import io.github.humbleui.skija.impl.*;

public abstract class WStream extends Managed {
    public WStream(long ptr, long finalizer) {
        super(ptr, finalizer);
    }

    public WStream(long ptr, long finalizer, boolean managed) {
        super(ptr, finalizer, managed);
    }
}