package io.github.humbleui.skija.impl;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Objects;

public final class Cleanable {

    private static final MethodHandle createCleanerHandle;
    private static final MethodHandle cleanHandle;

    static {
        try {
            Class<?> cleanerClass = Class.forName("sun.misc.Cleaner");
            createCleanerHandle = MethodHandles.publicLookup().findStatic(cleanerClass, "create", MethodType.methodType(cleanerClass, Object.class, Runnable.class));
            cleanHandle = MethodHandles.publicLookup().findVirtual(cleanerClass, "clean", MethodType.methodType(void.class));
        } catch (Throwable e) {
            throw new NoClassDefFoundError("sun.misc.Cleaner");
        }
    }

    public static Cleanable register(Object obj, Runnable action) {
        Objects.requireNonNull(obj);
        Objects.requireNonNull(action);
        try {
            return new Cleanable(createCleanerHandle.invokeWithArguments(obj, action));
        } catch (Throwable e) {
            throw new RuntimeException("Unreachable", e);
        }
    }

    private final Object cleaner;

    private Cleanable(Object cleaner) {
        this.cleaner = cleaner;
    }

    public void clean() {
        try {
            cleanHandle.invokeWithArguments(cleaner);
        } catch (Throwable e) {
            throw new RuntimeException("Unreachable", e);
        }
    }
}
