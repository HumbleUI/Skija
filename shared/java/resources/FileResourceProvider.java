package io.github.humbleui.skija.resources;

import org.jetbrains.annotations.*;
import io.github.humbleui.skija.*;
import io.github.humbleui.skija.impl.*;

public class FileResourceProvider extends ResourceProvider {
    static { Library.staticLoad(); }

    @ApiStatus.Internal
    public FileResourceProvider(long ptr) {
        super(ptr);
    }

    @NotNull @Contract("_ -> new")
    public static FileResourceProvider make(@NotNull String baseDir) {
        return make(baseDir, false);
    }

    @NotNull @Contract("_, _ -> new")
    public static FileResourceProvider make(@NotNull String baseDir, boolean predecode) {
        assert baseDir != null : "Can’t FileResourceProvider::make with baseDir == null";
        Stats.onNativeCall();
        return new FileResourceProvider(_nMake(baseDir, predecode));
    }

    @ApiStatus.Internal public static native long _nMake(String baseDir, boolean predecode);
}