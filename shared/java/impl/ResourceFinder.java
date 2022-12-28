package io.github.humbleui.skija.impl;

import java.io.*;
import java.net.*;

public final class ResourceFinder implements Closeable {
    public ResourceFinder(String moduleName) {
    }

    public URL findResource(String path) throws IOException {
        return ResourceFinder.class.getClassLoader().getResource(path);
    }

    @Override
    public void close() throws IOException {
    }
}
