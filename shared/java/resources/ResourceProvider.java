package io.github.humbleui.skija.resources;

import org.jetbrains.annotations.*;
import io.github.humbleui.skija.impl.*;

public abstract class ResourceProvider extends RefCnt {
    @ApiStatus.Internal
    public ResourceProvider(long ptr) {
        super(ptr);
    }
}