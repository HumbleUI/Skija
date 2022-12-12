package io.github.humbleui.skija.impl;

import java.lang.ref.Cleaner;

public final class Cleanable {
    private static final Cleaner cleaner = Cleaner.create();

    public static Cleanable register(Object obj, Runnable action) {
        return new Cleanable(cleaner.register(obj, action));
    }

    private final Cleaner.Cleanable cleanable;

    private Cleanable(Cleaner.Cleanable cleanable) {
        this.cleanable = cleanable;
    }

    public void clean() {
        this.cleanable.clean();
    }
}
