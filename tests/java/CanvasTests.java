package io.github.humbleui.skija.test;

import static io.github.humbleui.skija.test.runner.TestRunner.*;

import java.util.concurrent.ArrayBlockingQueue;

import io.github.humbleui.types.*;
import io.github.humbleui.skija.Bitmap;
import io.github.humbleui.skija.BlendMode;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.ColorFilter;
import io.github.humbleui.skija.ColorSpace;
import io.github.humbleui.skija.FilterTileMode;
import io.github.humbleui.skija.ImageFilter;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.Path;
import io.github.humbleui.skija.PathBuilder;
import io.github.humbleui.skija.SaveLayerRec;
import io.github.humbleui.skija.SaveLayerRecFlag;
import io.github.humbleui.skija.test.runner.*;


public class CanvasTests implements Executable {
    @Override
    public void execute() throws Exception {
        TestRunner.testMethod(this, "localClipBounds");
        TestRunner.testMethod(this, "deviceClipBounds");
        TestRunner.testMethod(this, "saveLayerFilters");
        TestRunner.testMethod(this, "saveLayerFilterOrder");
        TestRunner.testMethod(this, "saveLayerEmptyFilters");
        TestRunner.testMethod(this, "saveLayerInvalidFilters");
        TestRunner.testMethod(this, "saveLayerFilterOptions");
    }

    public void localClipBounds() {
        try (Bitmap a = new Bitmap()) {
            a.allocN32Pixels(256, 256);
            Canvas canvas = new Canvas(a);
            
            Rect bounds = canvas.getLocalClipBounds();
            assertEquals(-1f, bounds._left);
            assertEquals(-1f, bounds._top); 
            assertEquals(257f, bounds._right);
            assertEquals(257f, bounds._bottom);  
        
            float[] clipPoints = {30, 130, 120, 130, 120, 230};
            PathBuilder builder = new PathBuilder();
            builder.moveTo(clipPoints[0], clipPoints[1]);
            for (int i = 2; i < clipPoints.length; i += 2) {
                builder.lineTo(clipPoints[i], clipPoints[i + 1]);
            }
            builder.closePath();
            Path clipPath = builder.build();
            canvas.clipPath(clipPath, true);
            bounds = canvas.getLocalClipBounds();
            assertEquals(29f, bounds._left);
            assertEquals(129f, bounds._top);
            assertEquals(121f, bounds._right);
            assertEquals(231f, bounds._bottom);
        
            canvas.scale(2, 2);
            bounds = canvas.getLocalClipBounds();
            assertEquals(14.5f, bounds._left);
            assertEquals(64.5f, bounds._top);
            assertEquals(60.5f, bounds._right);
            assertEquals(115.5f, bounds._bottom);

            canvas.close();
        }
    }

    public void deviceClipBounds() {
        try (Bitmap a = new Bitmap()) {
            a.allocN32Pixels(256, 256);
            Canvas canvas = new Canvas(a);
            IRect bounds = canvas.getDeviceClipBounds();
            assertEquals(0, bounds._left);
            assertEquals(0, bounds._top); 
            assertEquals(256, bounds._right);
            assertEquals(256, bounds._bottom);  

            float[] clipPoints = {30, 130, 120, 130, 120, 230};
            PathBuilder builder = new PathBuilder();
            builder.moveTo(clipPoints[0], clipPoints[1]);
            for (int i = 2; i < clipPoints.length; i += 2) {
                builder.lineTo(clipPoints[i], clipPoints[i + 1]);
            }
            builder.closePath();
            Path clipPath = builder.build();
            canvas.save();
            canvas.clipPath(clipPath, true);
            bounds = canvas.getDeviceClipBounds();
            assertEquals(30, bounds._left);
            assertEquals(130, bounds._top);
            assertEquals(120, bounds._right);
            assertEquals(230, bounds._bottom);

            canvas.restore();
            canvas.scale(0.5f, 0.5f);
            canvas.clipPath(clipPath, true);
            bounds = canvas.getDeviceClipBounds();
            assertEquals(15, bounds._left);
            assertEquals(65, bounds._top);
            assertEquals(60, bounds._right);
            assertEquals(115, bounds._bottom);

            canvas.close();
        }
    }

    public void saveLayerFilters() {
        for (Rect bounds : new Rect[] {null, Rect.makeWH(32, 32)}) {
            try (Bitmap bitmap = new Bitmap();
                 Paint paint = new Paint().setColor(0xFFFF0000)) {
                bitmap.allocN32Pixels(32, 32);
                try (Canvas canvas = new Canvas(bitmap)) {
                    canvas.clear(0);
                    int saveCount;
                    try (ImageFilter offsetX = ImageFilter.makeOffset(12, 0, null);
                         ImageFilter offsetY = ImageFilter.makeOffset(0, 12, null)) {
                        ImageFilter[] filters = {offsetX, offsetY, null};
                        SaveLayerRec original = new SaveLayerRec(bounds, null);
                        SaveLayerRec layer = original.withFilters(filters);
                        assertEquals(0, original.getFilters().length);
                        filters[0] = null;
                        assertEquals(offsetX, layer.getFilters()[0]);
                        layer.getFilters()[0] = null;
                        assertEquals(offsetX, layer.getFilters()[0]);
                        saveCount = canvas.saveLayer(layer);
                    }
                    canvas.drawRect(Rect.makeXYWH(4, 4, 8, 8), paint);
                    canvas.restoreToCount(saveCount);
                    assertEquals(saveCount, canvas.getSaveCount());
                    assertEquals(0xFFFF0000, bitmap.getColor(6, 6));
                    assertEquals(0xFFFF0000, bitmap.getColor(18, 6));
                    assertEquals(0xFFFF0000, bitmap.getColor(6, 18));
                    assertEquals(0, bitmap.getColor(18, 18));
                }
            }
        }
    }

    public void saveLayerFilterOrder() {
        try (Bitmap bitmap = new Bitmap();
             Paint paint = new Paint().setColor(0xFFFF0000);
             Paint restorePaint = new Paint().setAlpha(128);
             ColorFilter green = ColorFilter.makeBlend(0xFF00FF00, BlendMode.SRC_IN);
             ImageFilter tint = ImageFilter.makeColorFilter(green, null)) {
            bitmap.allocN32Pixels(16, 16);
            try (Canvas canvas = new Canvas(bitmap)) {
                ImageFilter[][] orders = {{null, tint}, {tint, null}};
                int[] colors = {0xFF00FF00, 0xFFFF0000};
                for (int i = 0; i < orders.length; ++i) {
                    canvas.clear(0);
                    int saveCount = canvas.saveLayer(new SaveLayerRec().withFilters(orders[i]));
                    canvas.drawRect(Rect.makeXYWH(4, 4, 8, 8), paint);
                    canvas.restoreToCount(saveCount);
                    assertEquals(colors[i], bitmap.getColor(6, 6));
                    assertEquals(0, bitmap.getColor(0, 0));
                }
                canvas.clear(0);
                int saveCount = canvas.saveLayer(new SaveLayerRec(null, restorePaint).withFilters(null, null));
                canvas.drawRect(Rect.makeXYWH(4, 4, 8, 8), paint);
                canvas.restoreToCount(saveCount);
                assertEquals(0xC0FF0000, bitmap.getColor(6, 6));
            }
        }
    }

    public void saveLayerEmptyFilters() {
        for (Rect bounds : new Rect[] {null, Rect.makeWH(32, 32)}) {
            try (Bitmap bitmap = new Bitmap();
                 Paint paint = new Paint().setColor(0xFFFF0000);
                 ImageFilter offset = ImageFilter.makeOffset(12, 0, null);
                 Paint restorePaint = new Paint().setImageFilter(offset)) {
                bitmap.allocN32Pixels(32, 32);
                try (Canvas canvas = new Canvas(bitmap)) {
                    SaveLayerRec original = new SaveLayerRec(bounds, restorePaint);
                    SaveLayerRec cleared = original.withFilters(offset).withFilters();
                    for (SaveLayerRec layer : new SaveLayerRec[] {original, cleared}) {
                        canvas.clear(0);
                        int saveCount = canvas.saveLayer(layer);
                        canvas.drawRect(Rect.makeXYWH(4, 4, 8, 8), paint);
                        canvas.restoreToCount(saveCount);
                        assertEquals(0, bitmap.getColor(6, 6));
                        assertEquals(0xFFFF0000, bitmap.getColor(18, 6));
                    }
                }
            }
        }
    }

    public void saveLayerInvalidFilters() {
        for (Rect bounds : new Rect[] {null, Rect.makeWH(32, 32)}) {
            try (Bitmap bitmap = new Bitmap();
                 Paint paint = new Paint().setColor(0xFFFF0000);
                 ImageFilter offset = ImageFilter.makeOffset(12, 0, null);
                 Paint restorePaint = new Paint().setImageFilter(offset)) {
                bitmap.allocN32Pixels(32, 32);
                try (Canvas canvas = new Canvas(bitmap)) {
                    int saveCount = canvas.getSaveCount();
                    SaveLayerRec tooMany = new SaveLayerRec(bounds, null).withFilters(new ImageFilter[17]);
                    assertThrows(IllegalArgumentException.class, () -> canvas.saveLayer(tooMany));
                    assertEquals(saveCount, canvas.getSaveCount());

                    SaveLayerRec conflict = new SaveLayerRec(bounds, restorePaint).withFilters(offset);
                    assertThrows(IllegalArgumentException.class, () -> canvas.saveLayer(conflict));
                    assertEquals(saveCount, canvas.getSaveCount());

                    offset.close();
                    SaveLayerRec closed = new SaveLayerRec(bounds, null).withFilters(offset);
                    assertThrows(IllegalStateException.class, () -> canvas.saveLayer(closed));
                    assertEquals(saveCount, canvas.getSaveCount());

                    canvas.clear(0);
                    canvas.saveLayer(new SaveLayerRec(bounds, null).withFilters(new ImageFilter[16]));
                    canvas.drawRect(Rect.makeXYWH(4, 4, 8, 8), paint);
                    canvas.restoreToCount(saveCount);
                    assertEquals(0xFFFF0000, bitmap.getColor(6, 6));
                }
            }
        }
    }

    public void saveLayerFilterOptions() {
        try (Paint paint = new Paint().setAlpha(128);
             ImageFilter backdrop = ImageFilter.makeOffset(1, 1, null)) {
            SaveLayerRec original = new SaveLayerRec(Rect.makeWH(32, 32), paint, backdrop,
                FilterTileMode.MIRROR, ColorSpace.getSRGB(),
                SaveLayerRecFlag.INIT_WITH_PREVIOUS, SaveLayerRecFlag.F16_COLOR_TYPE);
            SaveLayerRec filtered = original.withFilters(backdrop, null);
            assertEquals(original.getBounds(), filtered.getBounds());
            assertEquals(original.getPaint(), filtered.getPaint());
            assertEquals(original.getBackdrop(), filtered.getBackdrop());
            assertEquals(original.getTileMode(), filtered.getTileMode());
            assertEquals(original.getColorSpace(), filtered.getColorSpace());
            assertEquals(original.getFlags(), filtered.getFlags());
            assertArrayEquals(new ImageFilter[] {backdrop, null}, filtered.getFilters());
        }
    }
}
