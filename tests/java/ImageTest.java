package io.github.humbleui.skija.test;

import static io.github.humbleui.skija.test.runner.TestRunner.*;

import java.io.*;
import java.nio.file.*;
import java.nio.file.Path;

import io.github.humbleui.skija.*;
import io.github.humbleui.skija.test.runner.*;

public class ImageTest implements Executable {
    @Override
    public void execute() throws Exception {
        TestRunner.testMethod(this, "base");
        TestRunner.testMethod(this, "refCntToStringAfterClose");
    }
    public void base() throws Exception {
        try (var surface = Surface.makeRaster(ImageInfo.makeN32Premul(100, 100));
             var paint = new Paint().setColor(0xFFFF0000);
             var path = new PathBuilder().moveTo(20, 80).lineTo(50, 20).lineTo(80, 80).closePath().build();)
        {
            var canvas = surface.getCanvas();
            canvas.drawPath(path, paint);
            try (var image = surface.makeImageSnapshot()) {
                var dir = "target/tests/ImageTest/";
                new File(dir).mkdirs();
                Files.write(Path.of(dir, "polygon_default.png"), EncoderPNG.encode(image).getBytes());
                Files.write(Path.of(dir, "polygon_default_none_1.png"), EncoderPNG.encode(image, EncodePNGOptions.DEFAULT.withFlags(EncodePNGFilterFlag.NONE).withZlibLevel(1)).getBytes());
                Files.write(Path.of(dir, "polygon_jpeg_default.jpeg"), EncoderJPEG.encode(image).getBytes());
                Files.write(Path.of(dir, "polygon_jpeg_50.jpeg"), EncoderJPEG.encode(image, EncodeJPEGOptions.DEFAULT.withQuality(50)).getBytes());
                Files.write(Path.of(dir, "polygon_webp_default.webp"), EncoderWEBP.encode(image).getBytes());
                Files.write(Path.of(dir, "polygon_webp_50.webp"), EncoderWEBP.encode(image,EncodeWEBPOptions.DEFAULT.withQuality(50)).getBytes());
                Files.write(Path.of(dir, "polygon_webp_lossless.webp"), EncoderWEBP.encode(image,EncodeWEBPOptions.DEFAULT.withCompressionMode(EncodeWEBPCompressionMode.LOSSLESS)).getBytes());
            }
        }
    }

    public void refCntToStringAfterClose() throws Exception {
        Bitmap bmp = new Bitmap(); 
        bmp.allocN32Pixels(10, 20);
        Image img = Image.makeRasterFromBitmap(bmp);
        assertEquals(1, img.getRefCount());
        img.toString();
        img.close();
        assertDoesNotThrow(() -> {
            img.toString();
        });
        bmp.close();
    }
}