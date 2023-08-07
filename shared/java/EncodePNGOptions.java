package io.github.humbleui.skija;

import org.jetbrains.annotations.*;

public class EncodePNGOptions {
    public enum FilterFlag {
        ZERO  (0x00),
        NONE  (0x08),
        SUB   (0x10),
        UP    (0x20),
        AVG   (0x40),
        PAETH (0x80),
        ALL   (0x08 | 0x10 | 0x20 | 0x40 | 0x80);

        @ApiStatus.Internal public final int _value;

        FilterFlag(int value) {
            this._value = value;
        }
    };

    public static final EncodePNGOptions DEFAULT = new EncodePNGOptions(FilterFlag.ALL._value, 6);

    @ApiStatus.Internal
    public final int _flags;

    @ApiStatus.Internal 
    public final int _zlibLevel;

    @ApiStatus.Internal 
    public EncodePNGOptions(int flags, int level) {
        _flags = flags;
        _zlibLevel = level;
    }

    public EncodePNGOptions withFlags(FilterFlag... flags) {
        int _flags = 0;
        for (FilterFlag flag: flags) {
            _flags = _flags | flag._value;
        }
        return new EncodePNGOptions(_flags, _zlibLevel);
    }

    public EncodePNGOptions withZlibLevel(int level) {
        return new EncodePNGOptions(_flags, level);
    }
}
