package io.github.humbleui.skija.impl;

import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

import lombok.*;
import org.jetbrains.annotations.*;

public class Library {
    @ApiStatus.Internal
    public static volatile boolean _loaded = false;

    public static final String LIBRARY_NAME = "skija";

    public static void staticLoad() {
        if (!_loaded && !"false".equals(System.getProperty("skija.staticLoad")))
            load();
    }

    public static String readResource(String path) {
        URL url = Library.class.getClassLoader().getResource(path);
        if (url == null)
            return null;

        try (Reader reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)) {
            StringBuilder builder = new StringBuilder();

            int ch;
            while ((ch = reader.read()) >= 0) {
                builder.append((char) ch);
            }
            return builder.toString().trim();
        } catch (IOException e) {
            return null;
        }
    }

    public static synchronized void load() {
        if (_loaded) return;

        // If `skija.loadFromLibraryPath` is set to true, use System.loadLibrary to load the native library
        String loadFromLibraryPath = System.getProperty("skija.loadFromLibraryPath");
        if ("true".equals(loadFromLibraryPath)) {
            _loadFromLibraryPath();
            return;
        }

        // If `skija.library.path` is set, load the native library from this path
        String libraryPath = System.getProperty("skija.library.path");
        if (libraryPath != null) {
            _loadFromDir(new File(libraryPath));
            return;
        }

        // If skija is bundled in JRE, try to load the bundled native library
        // User can disable this behavior by set `skija.loadFromLibraryPath` to false
        if (loadFromLibraryPath == null) {
            URL theClassFile = Library.class.getResource("Library.class");
            if (theClassFile != null && "jrt".equals(theClassFile.getProtocol())) {
                try {
                    _loadFromLibraryPath();
                    return;
                } catch (UnsatisfiedLinkError e) {
                    Log.warn("Please use the jmod file of skija when using jlink");
                }
            }
        }

        // Finally, try to load the bundled native library
        String osName = Platform.CURRENT.getOperatingSystem().name().toLowerCase(Locale.ROOT);
        String archName = Platform.CURRENT.getArchitecture().name().toLowerCase(Locale.ROOT);
        String basePath = "io/github/humbleui/skija/" + osName + "/" + archName + "/";
        String version = readResource(basePath + "skija.version");
        File tempDir = new File(System.getProperty("java.io.tmpdir"),
                "skija_" + (version == null || version.endsWith("SNAPSHOT") ? String.valueOf(System.nanoTime()) : version));

        File libFile = _extract(basePath, System.mapLibraryName(LIBRARY_NAME), tempDir);
        if (Platform.CURRENT.getOperatingSystem() == Platform.OperatingSystem.WINDOWS)
            _extract(basePath, "icudtl.dat", tempDir);

        if (tempDir.exists() && version == null) {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    Files.walk(tempDir.toPath())
                         .map(Path::toFile)
                         .sorted(Comparator.reverseOrder())
                         .forEach((f) -> {
                            Log.debug("Deleting " + f);
                            f.delete();
                         });
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }));
        }

        _loadFromFile(libFile);
    }

    @ApiStatus.Internal
    public static void _loadFromLibraryPath() {
        Log.debug("Loading skija native library from library path");
        System.loadLibrary(LIBRARY_NAME);
        _loaded = true;
        _nAfterLoad();
    }

    @ApiStatus.Internal
    public static void _loadFromDir(File dir) {
        String libPath = new File(dir, System.mapLibraryName(LIBRARY_NAME)).getAbsolutePath();
        Log.debug("Loading " + libPath);
        System.load(libPath);
        _loaded = true;
        _nAfterLoad();
    }

    @ApiStatus.Internal
    public static void _loadFromFile(File file) {
        String libPath = file.getAbsolutePath();
        Log.debug("Loading " + libPath);
        System.load(libPath);
        _loaded = true;
        _nAfterLoad();
    }

    @ApiStatus.Internal
    @SneakyThrows
    public static File _extract(String resourcePath, String fileName, File tempDir) {
        File file;
        URL url = Library.class.getClassLoader().getResource(resourcePath + fileName);
        if (url == null) {
            file = new File(fileName);
            if (!file.exists())
                throw new UnsatisfiedLinkError("Library file " + fileName + " not found in " + resourcePath);
        } else if ("file".equals(url.getProtocol())) {
            file = new File(url.toURI());
        } else {
            file = new File(tempDir, fileName);
            Log.debug("Extracting " + fileName + " to " + file);
            try (InputStream is = url.openStream()) {
                if (file.exists() && file.length() != is.available()) {
                    file.delete();
                }
                if (!file.exists()) {
                    if (!tempDir.exists()) {
                        tempDir.mkdirs();
                    }
                    Files.copy(is, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
        return file;
    }

    @ApiStatus.Internal
    public static native void _nAfterLoad();
}
