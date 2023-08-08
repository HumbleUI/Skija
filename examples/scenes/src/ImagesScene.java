package io.github.humbleui.skija.examples.scenes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import io.github.humbleui.skija.*;
import io.github.humbleui.types.*;

public class ImagesScene extends Scene {
    protected final Image circus;
    protected final Image circusCropped;
    protected final Image cloud;
    protected final Image ducks;
    protected final Image[] tests;

    public ImagesScene() {
        try {
            circus = Image.makeDeferredFromEncodedBytes(Files.readAllBytes(Path.of(file("images/circus.jpg"))));
            circusCropped = Image.makeDeferredFromEncodedBytes(Files.readAllBytes(Path.of(file("images/circus.jpg"))));
            cloud = Image.makeDeferredFromEncodedBytes(Files.readAllBytes(Path.of(file("images/cloud.png"))));
            ducks = Image.makeDeferredFromEncodedBytes(Files.readAllBytes(Path.of(file("images/ducks.jpg"))));
            tests = new Image[] {
                Image.makeDeferredFromEncodedBytes(Files.readAllBytes(Path.of(file("images/icc-v2-gbr.jpg")))),
                Image.makeDeferredFromEncodedBytes(Files.readAllBytes(Path.of(file("images/purple-displayprofile.png")))),
                Image.makeDeferredFromEncodedBytes(Files.readAllBytes(Path.of(file("images/wide-gamut.png")))),
                Image.makeDeferredFromEncodedBytes(Files.readAllBytes(Path.of(file("images/wide_gamut_yellow_224_224_64.jpeg")))),
                Image.makeDeferredFromEncodedBytes(Files.readAllBytes(Path.of(file("images/webkit_logo_p3.png")))),
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void draw(Canvas canvas, int width, int height, float dpi, int xpos, int ypos) {
        canvas.translate(30, 30);

        canvas.save();
        canvas.drawImageRect(circus, Rect.makeXYWH(0, 0, 640, 640), Rect.makeXYWH(0, 0, 160, 160), null, true);
        canvas.translate(170, 0);
        canvas.drawImageRect(circusCropped, Rect.makeXYWH(0, 0, 320, 320), Rect.makeXYWH(0, 0, 160, 160), null, true);
        canvas.translate(170, 0);
        canvas.drawImageRect(cloud, Rect.makeXYWH(0, 0, 666, 456), Rect.makeXYWH(0, 0, 160, 110), null, true);
        canvas.drawImageRect(cloud, Rect.makeXYWH(0, 0, 666, 456), Rect.makeXYWH(0, 50, 160, 110), null, true);
        canvas.translate(170, 0);
        canvas.drawImageRect(ducks, Rect.makeXYWH(0, 0, 640, 640), Rect.makeXYWH(0, 0, 80, 160), null, true);
        canvas.translate(90, 0);
        canvas.drawImageRect(ducks, Rect.makeXYWH(0, 0, 640, 640), Rect.makeXYWH(0, 0, 160, 80), null, true);
        canvas.translate(170, 0);
        canvas.restore();
        canvas.translate(0, 170);

        canvas.save();
        for (var pair: Pair.arrayOf("None/None", SamplingMode.DEFAULT,
                                    "Linear/None", SamplingMode.LINEAR,
                                    "Linear/Nearest", new FilterMipmap(FilterMode.LINEAR, MipmapMode.NEAREST),
                                    "Linear/Linear", new FilterMipmap(FilterMode.LINEAR, MipmapMode.LINEAR),
                                    "Mitchell", SamplingMode.MITCHELL,
                                    "Catmull-Rom", SamplingMode.CATMULL_ROM,
                                    "Anisotropic(10)", new SamplingModeAnisotropic(10)))
        {
            String name = pair.getFirst();
            SamplingMode mode = pair.getSecond();

            canvas.drawImageRect(circus, Rect.makeXYWH(0, 0, 320, 640), Rect.makeXYWH(0, 0, 80, 160), mode, null, false);
            canvas.drawImageRect(circus, Rect.makeXYWH(200, 220, 60, 100), Rect.makeXYWH(80, 0, 80, 160), mode, null, false);
            canvas.drawString(name, 0, 175, inter13, blackFill);
            canvas.translate(170, 0);
        }
        canvas.restore();
        canvas.translate(0, 200);

        canvas.save();
        try (Paint paint = new Paint().setBlendMode(BlendMode.SCREEN)) {
            canvas.drawImageRect(circus, Rect.makeXYWH(0, 0, 160, 160));
            canvas.drawImageRect(circus, Rect.makeXYWH(0, 0, 160, 160), paint);
        }
        canvas.translate(170, 0);
        try (Paint paint = new Paint().setBlendMode(BlendMode.OVERLAY)) {
            canvas.drawImageRect(circus, Rect.makeXYWH(0, 0, 160, 160));
            canvas.drawImageRect(circus, Rect.makeXYWH(0, 0, 160, 160), paint);
        }
        canvas.translate(170, 0);
        try (ImageFilter blur = ImageFilter.makeBlur(5, 5, FilterTileMode.DECAL);
             Paint paint = new Paint().setImageFilter(blur)) {
            canvas.drawImageRect(circus, Rect.makeXYWH(0, 0, 160, 160), paint);
        }
        canvas.translate(170, 0);
        canvas.restore();
        canvas.translate(0, 170);

        canvas.save();
        var maxH = 0;
        for (Image image: tests) {
            maxH = Math.max(maxH, image.getHeight());
            canvas.drawImage(image, 0, 0);
            canvas.translate(image.getWidth() + 10, 0);
        }
        canvas.restore();
        canvas.translate(0, maxH + 10);
    }
}