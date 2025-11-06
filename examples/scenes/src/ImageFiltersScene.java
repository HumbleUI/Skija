package io.github.humbleui.skija.examples.scenes;

import java.io.IOException;
import java.nio.file.Files;

import io.github.humbleui.skija.*;
import io.github.humbleui.types.*;

public class ImageFiltersScene extends Scene {
    protected final Image image;
    protected final Image image2;
    protected final Path star;
    protected final Picture pic;

    public ImageFiltersScene() {
        try {
            image = Image.makeDeferredFromEncodedBytes(Files.readAllBytes(java.nio.file.Path.of(file("images/circus.jpg"))));
            image2 = Image.makeDeferredFromEncodedBytes(Files.readAllBytes(java.nio.file.Path.of(file("images/skia_fiddle/2.png"))));
            star = new PathBuilder().setFillMode(PathFillMode.EVEN_ODD)
                                    .moveTo(10, 10)
                                    .rMoveTo(20, 1.6f)
                                    .rLineTo(11.7f, 36.2f)
                                    .rLineTo(-30.8f, -22.4f)
                                    .rLineTo(38.1f, 0f)
                                    .rLineTo(-30.8f, 22.4f)
                                    .build();
            try (PictureRecorder recorder = new PictureRecorder();
                 Paint fill = new Paint().setColor(0xFFFF9F1B);)
            {
                Canvas rc = recorder.beginRecording(Rect.makeXYWH(0, 0, 60, 60));
                rc.drawPath(star, fill);
                pic = recorder.finishRecordingAsPicture();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void draw(Canvas canvas, int width, int height, float dpi, int xpos, int ypos) {
        canvas.translate(20, 20);
        drawShadowsBlurs(canvas);
        drawImageFilters(canvas, width, dpi);
        drawImageWithFilters(canvas);
        drawLights(canvas);
    }

    private void drawShadowsBlurs(Canvas canvas) {
        canvas.save();
        try (
            Paint fill = new Paint().setColor(0xFF8E86C9);
            Path path = new PathBuilder().setFillMode(PathFillMode.EVEN_ODD)
                                         .lineTo(0, 60).lineTo(60, 60).lineTo(60, 0).closePath()
                                         .moveTo(10, 5).lineTo(55, 10).lineTo(50, 55).lineTo(5, 50).closePath()
                                         .build();
            ImageFilter inner = ImageFilter.makeDropShadow(-2, -2, 2, 2, 0xFFCC3333);
        ) {
            ImageFilter[] filters = new ImageFilter[] {
                ImageFilter.makeDropShadow(0, 0, 10, 10, 0xFF000000),
                ImageFilter.makeDropShadow(2, 2, 0, 0, 0xFF000000),
                ImageFilter.makeDropShadow(0, 0, 10, 10, 0xFFF42372),
                ImageFilter.makeDropShadow(0, 0, 10, 10, new Color4f(0xFFF42372), null, null),
                ImageFilter.makeDropShadowOnly(0, 0, 2, 2, 0xFFCC3333),
                ImageFilter.makeDropShadowOnly(0, 0, 2, 2, new Color4f(0xFFCC3333), null, null),
                ImageFilter.makeDropShadow(0, 0, 2, 2, 0xFFCC3333, null, IRect.makeXYWH(30, 30, 30, 30)),
                ImageFilter.makeDropShadow(2, 2, 2, 2, 0xFF3333CC, inner),
                ImageFilter.makeBlur(2, 2, FilterTileMode.CLAMP),
                ImageFilter.makeBlur(2, 2, FilterTileMode.REPEAT),
                ImageFilter.makeBlur(2, 2, FilterTileMode.MIRROR),
                ImageFilter.makeBlur(2, 2, FilterTileMode.DECAL),
            };

            for (var filter: filters) {
                fill.setImageFilter(filter);
                canvas.drawPath(path, fill);
                canvas.translate(70, 0);
                filter.close();
            }
        }
        canvas.restore();
        canvas.translate(0, 70);
    }

    private void drawImageFilters(Canvas canvas, float width, float dpi) {
        canvas.save();
        try (
            Paint fill     = new Paint().setColor(0xFFFF9F1B);
            ColorFilter cf = ColorFilter.makeBlend(0x800000FF, BlendMode.SRC_OVER);
            ImageFilter ds = ImageFilter.makeDropShadow(0, 0, 10, 10, 0xFF000000);
            ImageFilter im = ImageFilter.makeImage(image, Rect.makeXYWH(200, 200, 200, 200), Rect.makeXYWH(10, 10, 40, 40), CubicResampler.MITCHELL);
            Shader sh      = Shader.makeTurbulence(0.01f, 0.01f, 2, 0.f);
        ) {
            IRect bb = IRect.makeXYWH(0, 0, 60, 60);
            ImageFilter[] filters = new ImageFilter[] {
                ImageFilter.makeOffset(0, 0, null, bb),
                ImageFilter.makeCrop(Rect.makeXYWH(20, 20, 20, 20), null),
                ImageFilter.makeMagnifier(Rect.makeXYWH(0 * dpi, 0 * dpi, 60 * dpi, 60 * dpi),  5f,  5f, SamplingMode.MITCHELL, null, bb),
                ImageFilter.makeMagnifier(Rect.makeXYWH(0 * dpi, 0 * dpi, 60 * dpi, 60 * dpi), 10f, 10f, SamplingMode.MITCHELL, null, bb),
                ImageFilter.makeMagnifier(Rect.makeXYWH(0 * dpi, 0 * dpi, 60 * dpi, 60 * dpi), 20f, 20f, SamplingMode.MITCHELL, null, bb),
                ImageFilter.makeTile(Rect.makeXYWH(20, 20, 20, 20), Rect.makeXYWH(0, 0, 60, 60), null),
                ImageFilter.makeDilate(2, 2, null, bb),
                ImageFilter.makeErode(2, 2, null, bb),
                ImageFilter.makeColorFilter(cf, ds, bb),
                ImageFilter.makeImage(image, Rect.makeXYWH(200, 200, 200, 200), Rect.makeXYWH(10, 10, 40, 40), CubicResampler.MITCHELL),
                ImageFilter.makeArithmetic(-0.25f, 0.5f, 0.5f, 0f, true, im, null),
                ImageFilter.makeBlend(Blender.makeArithmetic(-0.25f, 0.5f, 0.5f, 0f, true), im, null),
                ImageFilter.makeBlend(BlendMode.SCREEN, im, null),
                ImageFilter.makeBlend(Blender.makeWithMode(BlendMode.SCREEN), im, null),
                ImageFilter.makePicture(pic),
                ImageFilter.makePicture(pic, Rect.makeXYWH(20, 20, 20, 20)),
                ImageFilter.makeShader(sh, bb)
            };

            for (var filter: filters) {
                fill.setImageFilter(filter);
                canvas.drawPath(star, fill);
                canvas.translate(70, 0);
                filter.close();
            }
        }
        canvas.restore();
        canvas.translate(0, 70);
    }

    private void drawImageWithFilters(Canvas canvas) {
        canvas.save();

        try (
            ImageFilter shadowFilter = ImageFilter.makeDropShadow(-10.0f * phase(), 5.0f * phase(), 3.0f, 3.0f, 0xFF0000FF);
            ImageFilter offsetFilter = ImageFilter.makeOffset(40, 40, shadowFilter);
            Paint paint = new Paint().setAntiAlias(true).setStroke(true)
        ) {
            int w = image2.getImageInfo().getWidth();
            int h = image2.getImageInfo().getHeight();
            IRect subset = IRect.makeXYWH(0, 0, w, h);
            IRect clipBounds = IRect.makeXYWH(-60, -60, w + 120, h + 120);
            DirectContext context = ((Surface) canvas._owner)._context;
            for (ImageWithFilterResult result: new ImageWithFilterResult[] {
                Image.makeWithFilter(context, offsetFilter, image2, subset, clipBounds),
                Image.makeWithFilter(offsetFilter, image2, subset, clipBounds)
            }) {
                IPoint offset = result.getOffset();
                canvas.drawLine(0, 0, offset.getX(), offset.getY(), paint);
                canvas.translate(offset.getX(), offset.getY());
                result.getImage().getImageInfo();
                canvas.drawImage(result.getImage(), 0, 0);
                canvas.drawRect(result.getSubset().toRect(), paint);
                result.close();
                canvas.translate(100, 0);
            }
        }

        canvas.restore();
        canvas.translate(0, 200);
    }

    private void drawLights(Canvas canvas) {
        canvas.save();
        try (Paint fill = new Paint().setColor(0xFFFF9F1B);
             Path path = new PathBuilder().setFillMode(PathFillMode.EVEN_ODD)
                                          .moveTo(10, 10).rMoveTo(20, 1.6f).rLineTo(11.7f, 36.2f).rLineTo(-30.8f, -22.4f).rLineTo(38.1f, 0f).rLineTo(-30.8f, 22.4f)
                                          .build();)
        {
            IRect bb = IRect.makeXYWH(0, 0, 60, 60);
            ImageFilter[] filters = new ImageFilter[] {
                ImageFilter.makeDistantLitDiffuse( 0,  1, 1, 0xFFFF9F1B, 1, 0.5f, null, bb),
                ImageFilter.makeDistantLitDiffuse( 0, -1, 1, 0xFFFF9F1B, 1, 0.5f, null, bb),
                ImageFilter.makeDistantLitDiffuse( 1,  0, 1, 0xFFFF9F1B, 1, 0.5f, null, bb),
                ImageFilter.makeDistantLitDiffuse(-1,  0, 1, 0xFFFF9F1B, 1, 0.5f, null, bb),
                ImageFilter.makeDistantLitDiffuse(-1, -1, 1, 0xFFFF9F1B, 1, 0.5f, null, bb),
                ImageFilter.makePointLitDiffuse(0, 0, 30, 0xFFFF9F1B, 1, 0.5f, null, bb),
                ImageFilter.makeSpotLitDiffuse(0, 0, 30, 30, 30, 0, 1f, 30, 0xFFFF9F1B, 1, 0.5f, null, bb),
                ImageFilter.makeDistantLitSpecular(-1, -1, 1, 0xFFFF9F1B, 1, 1.1f, 1.1f, null, bb),
                ImageFilter.makePointLitSpecular(0, 0, 30, 0xFFFF9F1B, 1, 1.1f, 1.1f, null, bb),
                ImageFilter.makeSpotLitSpecular(0, 0, 30, 30, 30, 0, 1f, 30, 0xFFFF9F1B, 1, 1.1f, 1.1f, null, bb),
            };

            for (var filter: filters) {
                fill.setImageFilter(filter);
                canvas.drawPath(path, fill);
                canvas.translate(70, 0);
                filter.close();
            }
        }
        canvas.restore();
        canvas.translate(0, 70);
    }
}