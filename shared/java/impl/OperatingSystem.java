package io.github.humbleui.skija.impl;

import java.util.Locale;

public enum OperatingSystem {
    WINDOWS,
    LINUX,
    MACOS,
    UNKNOWN;

    public static final OperatingSystem CURRENT;

    static {
        String os = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        if (os.contains("mac") || os.contains("darwin")) {
            CURRENT = MACOS;
        } else if (os.contains("windows")) {
            CURRENT = WINDOWS;
        } else if (os.contains("nux") || os.contains("nix")) {
            CURRENT = LINUX;
        } else {
            CURRENT = UNKNOWN;
        }
    }
}
