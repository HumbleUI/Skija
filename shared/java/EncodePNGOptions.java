package io.github.humbleui.skija;

import org.jetbrains.annotations.*;

public class EncodePNGOptions {
    public static final EncodePNGOptions DEFAULT = new EncodePNGOptions(EncodePNGFilterFlag.ALL._value, 6);

    @ApiStatus.Internal
    public final int _flags;

    @ApiStatus.Internal 
    public final int _zlibLevel;

    @ApiStatus.Internal 
    public EncodePNGOptions(int flags, int level) {
        _flags = flags;
        _zlibLevel = level;
    }

    public EncodePNGOptions withFlags(EncodePNGFilterFlag... flags) {
        int _flags = 0;
        for (EncodePNGFilterFlag flag: flags) {
            _flags = _flags | flag._value;
        }
        return new EncodePNGOptions(_flags, _zlibLevel);
    }

    public EncodePNGOptions withZlibLevel(int level) {
        return new EncodePNGOptions(_flags, level);
    }
}
