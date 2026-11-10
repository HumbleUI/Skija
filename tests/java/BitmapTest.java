package io.github.humbleui.skija.test;

import static io.github.humbleui.skija.test.runner.TestRunner.*;

import io.github.humbleui.skija.Bitmap;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.ColorAlphaType;
import io.github.humbleui.skija.ColorType;
import io.github.humbleui.skija.ImageInfo;
import io.github.humbleui.skija.test.runner.*;
import io.github.humbleui.types.IRect;

public class BitmapTest implements Executable {
    @Override
    public void execute() throws Exception {
        TestRunner.testMethod(this, "base");
        TestRunner.testMethod(this, "swap");
        TestRunner.testMethod(this, "getSurfaceNull");
        TestRunner.testMethod(this, "readPixels");
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

    public void readPixels() throws Exception {
        try (var bitmap = new Bitmap()) {
            bitmap.allocN32Pixels(2, 3);
            bitmap.erase(0xFF112233);
            bitmap.erase(0xFF445566, IRect.makeXYWH(0, 1, 2, 1));

            byte[] all = bitmap.readPixels();
            assertEquals(2 * 3 * 4, all.length);

            var rgba = new ImageInfo(2, 3, ColorType.RGBA_8888, ColorAlphaType.PREMUL);
            byte[] a = {0x11, 0x22, 0x33, (byte) 0xFF};
            byte[] b = {0x44, 0x55, 0x66, (byte) 0xFF};
            assertArrayEquals(concat(a, a, b, b, a, a), bitmap.readPixels(rgba, 8, 0, 0));
            assertArrayEquals(concat(b, b, a, a), bitmap.readPixels(rgba, 8, 0, 1));
            assertNull(bitmap.readPixels(rgba, 8, 0, 3));
            assertNull(bitmap.readPixels(rgba, 4, 0, 0));
        }
    }

    public byte[] concat(byte[]... parts) {
        byte[] res = new byte[parts.length * 4];
        for (int i = 0; i < parts.length; ++i)
            System.arraycopy(parts[i], 0, res, i * 4, 4);
        return res;
    }
}
