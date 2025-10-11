package io.github.humbleui.skija.test;

import static io.github.humbleui.skija.test.runner.TestRunner.*;

import io.github.humbleui.skija.Bitmap;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.ColorAlphaType;
import io.github.humbleui.skija.ImageInfo;
import io.github.humbleui.skija.test.runner.*;

public class BitmapTest implements Executable {
    @Override
    public void execute() throws Exception {
        TestRunner.testMethod(this, "base");
        TestRunner.testMethod(this, "swap");
        TestRunner.testMethod(this, "getSurfaceNull");
    }

    public void base() throws Exception {
        try (var bitmap = new Bitmap()) {
            int id1 = bitmap.getGenerationId();
            assertEquals(true, bitmap.isNull());
            assertEquals(true, bitmap.isEmpty());

            bitmap.allocPixels(ImageInfo.makeS32(7, 3, ColorAlphaType.OPAQUE));
            assertNotEquals(id1, bitmap.getGenerationId());

            assertEquals(false, bitmap.isNull());
            assertEquals(false, bitmap.isEmpty());
            
            assertEquals(7L * 4, bitmap.getRowBytes());
            assertEquals(4, bitmap.getBytesPerPixel());
            assertEquals(7, bitmap.getRowBytesAsPixels());

            bitmap.allocPixels(ImageInfo.makeS32(7, 3, ColorAlphaType.OPAQUE), 32);
            assertEquals(32L, bitmap.getRowBytes());
            assertEquals(4, bitmap.getBytesPerPixel());
            assertEquals(8, bitmap.getRowBytesAsPixels());

            bitmap.setImageInfo(ImageInfo.makeS32(7, 3, ColorAlphaType.OPAQUE));
            assertEquals(true, bitmap.isNull());
            assertEquals(false, bitmap.isEmpty());
            assertEquals(false, bitmap.isReadyToDraw());
            bitmap.allocPixels();
            assertEquals(false, bitmap.isNull());
            assertEquals(true, bitmap.isReadyToDraw());

            bitmap.getGenerationId();
        }
    }

    public void swap() throws Exception {
        try (Bitmap a = new Bitmap(); Bitmap b = new Bitmap()) {
            a.allocN32Pixels(10, 20);
            b.allocN32Pixels(5, 6);
            int aW = a.getWidth();
            int aH = a.getHeight();
            int bW = b.getWidth();
            int bH = b.getHeight();
            assertNotEquals(aW, bW);
            a.swap(b);
            // After swap, a should now have original b dimensions and vice versa
            assertEquals(bW, a.getWidth());
            assertEquals(bH, a.getHeight());
            assertEquals(aW, b.getWidth());
            assertEquals(aH, b.getHeight());
        }
    }
    
    public void getSurfaceNull() throws Exception {
        try (Bitmap a = new Bitmap()) {
            a.allocN32Pixels(10, 20);
            Canvas canvas = new Canvas(a);
            assertNull(canvas.getSurface());
            canvas.close();
        }
    }
}