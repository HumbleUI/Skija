package io.github.humbleui.skija.test;

import static io.github.humbleui.skija.test.runner.TestRunner.*;

import java.io.*;
import java.nio.file.*;

import io.github.humbleui.skija.EncodeJPEGOptions;
import io.github.humbleui.skija.EncodePNGOptions;
import io.github.humbleui.skija.EncodeWEBPOptions;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.Surface;
import io.github.humbleui.skija.Path;
import io.github.humbleui.skija.test.runner.*;

public class ImageTest implements Executable {
    @Override
    public void execute() throws Exception {
        try (var surface = Surface.makeRasterN32Premul(100, 100);
             var paint = new Paint().setColor(0xFFFF0000);
             var path = new Path().moveTo(20, 80).lineTo(50, 20).lineTo(80, 80).closePath();)
        {
            var canvas = surface.getCanvas();
            canvas.drawPath(path, paint);
            try (var image = surface.makeImageSnapshot()) {
                new File("target/tests/ImageTest/").mkdirs();
                Files.write(java.nio.file.Path.of("target/tests/ImageTest/polygon_default.png"), image.encodePNG().getBytes());
                Files.write(java.nio.file.Path.of("target/tests/ImageTest/polygon_default_none_1.png"), image.encodePNG(EncodePNGOptions.DEFAULT.withFlags(EncodePNGOptions.FilterFlag.NONE).withZlibLevel(1)).getBytes());
                Files.write(java.nio.file.Path.of("target/tests/ImageTest/polygon_jpeg_default.jpeg"), image.encodeJPEG().getBytes());
                Files.write(java.nio.file.Path.of("target/tests/ImageTest/polygon_jpeg_50.jpeg"), image.encodeJPEG(EncodeJPEGOptions.DEFAULT.withQuality(50)).getBytes());
                Files.write(java.nio.file.Path.of("target/tests/ImageTest/polygon_webp_default.webp"), image.encodeWEBP().getBytes());
                Files.write(java.nio.file.Path.of("target/tests/ImageTest/polygon_webp_50.webp"), image.encodeWEBP(EncodeWEBPOptions.DEFAULT.withQuality(50)).getBytes());
                Files.write(java.nio.file.Path.of("target/tests/ImageTest/polygon_webp_lossless.webp"), image.encodeWEBP(EncodeWEBPOptions.DEFAULT.withCompressionMode(EncodeWEBPOptions.CompressionMode.LOSSLESS)).getBytes());
            }
        }
    }
}