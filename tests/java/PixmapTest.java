package io.github.humbleui.skija.test;

import static io.github.humbleui.skija.test.runner.TestRunner.*;

import io.github.humbleui.skija.Bitmap;
import io.github.humbleui.skija.Color4f;
import io.github.humbleui.skija.ColorAlphaType;
import io.github.humbleui.skija.ColorType;
import io.github.humbleui.skija.ImageInfo;
import io.github.humbleui.skija.Pixmap;
import io.github.humbleui.skija.test.runner.*;
import io.github.humbleui.types.IRect;

public class PixmapTest implements Executable {
    @Override
    public void execute() throws Exception {
        try (var bitmap = new Bitmap()) {
            bitmap.allocPixels(ImageInfo.makeN32Premul(10, 20));
            bitmap.erase(0xFF112233);

            try (var pixmap = new Pixmap()) {
                assertEquals(true, bitmap.peekPixels(pixmap));

                assertEquals(10, pixmap.getWidth());
                assertEquals(20, pixmap.getHeight());
                assertEquals(ColorType.N32, pixmap.getColorType());
                assertEquals(ColorAlphaType.PREMUL, pixmap.getAlphaType());
                assertEquals(null, pixmap.getColorSpace());
                assertEquals(2, pixmap.getShiftPerPixel());
                assertEquals(40, pixmap.getRowBytes());
                assertEquals(10, pixmap.getRowBytesAsPixels());

                assertEquals(0xFF112233, pixmap.getColor(0, 0));
                
                pixmap.erase(new Color4f(1f, 1f, 1f, 1f));
                assertEquals(0xFFFFFFFF, pixmap.getColor(0, 0));

                pixmap.erase(new Color4f(1f, 0f, 0f, 1f), IRect.makeXYWH(0, 0, 5, 5));
                assertEquals(0xFFFF0000, pixmap.getColor(0, 0));
                assertEquals(0xFFFFFFFF, pixmap.getColor(6, 6));

                assertNotEquals(0, pixmap.getAddr());
                assertNotEquals(0, pixmap.getAddrAt(1, 1));
                
                var info = pixmap.getInfo();
                assertEquals(10, info._width);
                assertEquals(20, info._height);
            }
        }
    }
}
