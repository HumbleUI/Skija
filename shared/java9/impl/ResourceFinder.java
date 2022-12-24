package io.github.humbleui.skija.impl;

import java.io.*;
import java.lang.module.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import lombok.*;
import org.jetbrains.annotations.*;

public final class ResourceFinder implements Closeable {

    /*
     * For multi-release jars, the APIs of the same class in different versions should be the same.
     */

    private final String _moduleName;
    private Optional<ModuleReader> _moduleReader;

    public ResourceFinder(String moduleName) {
        this._moduleName = moduleName;
    }

    public URL findResource(String path) throws IOException {
        URL url = ResourceFinder.class.getClassLoader().getResource(path);
        if (url != null) {
            return url;
        }

        if (_moduleReader != null) {
            return _moduleReader.map(reader -> _findResource(reader, path)).orElse(null);
        }

        Optional<ModuleReference> moduleReference = ModuleFinder.ofSystem().find(_moduleName);
        if (moduleReference.isPresent()) {
            _moduleReader = Optional.of(moduleReference.get().open());
            return _findResource(_moduleReader.get(), path);
        }

        String modulePath = System.getProperty("jdk.module.path");
        if (modulePath != null && !modulePath.isEmpty()) {
            Path[] paths = Arrays.stream(modulePath.split(File.pathSeparator))
                    .map(Paths::get)
                    .toArray(Path[]::new);

            moduleReference = ModuleFinder.of(paths).find(_moduleName);
            if (moduleReference.isPresent()) {
                _moduleReader = Optional.of(moduleReference.get().open());
                return _findResource(_moduleReader.get(), path);
            }
        }

        _moduleReader = Optional.empty();
        return null;
    }

    @Override
    public void close() throws IOException {
        if (_moduleReader != null && _moduleReader.isPresent()) {
            _moduleReader.get().close();
        }
    }

    @SneakyThrows(IOException.class)
    private static URL _findResource(ModuleReader reader, String path) {
        URI uri = reader.find(path).orElse(null);
        return uri != null ? uri.toURL() : null;
    }
}
