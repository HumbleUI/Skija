package io.github.humbleui.skija.examples.scenes;

import io.github.humbleui.skija.*;
import io.github.humbleui.types.*;
import java.util.*;

public class BackdropScene extends Scene {
    private List<Pair<Path, Integer>> shapes = new ArrayList<>(100);

    public BackdropScene() {
        var random = new Random(0);
        for (int i = 0; i < 100; ++i) {
            var path = new Path();

            switch (random.nextInt(4)) {
                case 0:
                    path.addRect(Rect.makeXYWH(-0.5f, -0.5f, 1, 1));
                    break;
                case 1:
                    path.addCircle(0, 0, 0.5f);
                    break;
                case 2:
                    path.moveTo(0, -0.5f).lineTo(0.5f, 0.36f).lineTo(-0.5f, 0.36f).closePath();
                    break;
                case 3:
                    path.addRRect(RRect.makeXYWH(-0.6f, -0.4f, 1.2f, 0.8f, 0.4f));
                    break;
            }

            path.transform(Matrix33.makeRotate(random.nextInt(360)));
            path.transform(Matrix33.makeScale(10 + random.nextInt(250)));
            path.transform(Matrix33.makeTranslate(random.nextInt(1920), random.nextInt(1080)));

            int color = 0xFF000000 | random.nextInt(0xFFFFFF);

            shapes.add(new Pair<>(path, color));
        }
    }

    @Override
    public void draw(Canvas canvas, int width, int height, float dpi, int xpos, int ypos) {
        assert null != canvas.getBaseProps();
        assert null != canvas.getTopProps();

        try (var fill = new Paint();)
        {
            for (var tuple: shapes) {
                fill.setColor(tuple.getSecond());
                canvas.drawPath(tuple.getFirst(), fill);
            }
        }

        var colorMatrix = new ColorMatrix(
            0.21f, 0.72f, 0.07f, 0, 0,
            0.21f, 0.72f, 0.07f, 0, 0,
            0.21f, 0.72f, 0.07f, 0, 0,
            0,     0,     0,     1, 0
        );

        try (var backdrop20  = ImageFilter.makeBlur(20, 20, FilterTileMode.CLAMP);
             var backdrop100 = ImageFilter.makeBlur(100, 100, FilterTileMode.CLAMP);
             var colorFilter = ColorFilter.makeMatrix(colorMatrix);
             var grayscale   = ImageFilter.makeColorFilter(colorFilter, null, null);
             var paint = new Paint().setImageFilter(backdrop100);)
        {
            Rect screen = Rect.makeXYWH(0, 0, width, height);

            Rect rect = Rect.makeXYWH(xpos - 210, ypos - 210, 200, 200).intersect(screen);
            if (rect != null) {
                int layer = canvas.save();
                canvas.clipRect(rect, true);
                canvas.saveLayer(new SaveLayerRec(rect, null, backdrop20));
                canvas.drawColor(0x40000000);
                canvas.restoreToCount(layer);
            }

            rect = Rect.makeXYWH(xpos + 10, ypos - 210, 200, 200).intersect(screen);
            if (rect != null) {
                int layer = canvas.save();
                canvas.clipRect(rect, true);
                canvas.saveLayer(new SaveLayerRec(null, paint, SaveLayerRecFlag.INIT_WITH_PREVIOUS));
                canvas.drawColor(0x40FFFFFF);
                canvas.restoreToCount(layer);
            }

            rect = Rect.makeXYWH(xpos - 210, ypos + 10, 200, 200).intersect(screen);
            if (rect != null) {
                int layer = canvas.save();
                canvas.clipRect(rect, true);
                canvas.saveLayer(new SaveLayerRec(rect, null, backdrop20));
                canvas.translate(xpos - 210, ypos + 10);
                canvas.drawColor(0x80000000);
                try (var fill = new Paint().setColor(0x80FFFFFF)) {
                    canvas.drawRect(Rect.makeXYWH(50, 50, 100, 100), fill);
                }
                canvas.restoreToCount(layer);
            }

            rect = Rect.makeXYWH(xpos + 10, ypos + 10, 200, 200).intersect(screen);
            if (rect != null) {
                int layer = canvas.save();
                canvas.clipRect(rect, true);
                canvas.saveLayer(new SaveLayerRec(rect, null, grayscale, SaveLayerRecFlag.INIT_WITH_PREVIOUS));
                canvas.restoreToCount(layer);
            }
        }
    }
}