package io.github.humbleui.skija.test;

import static io.github.humbleui.skija.test.runner.TestRunner.*;

import io.github.humbleui.types.Point;
import io.github.humbleui.skija.BlendMode;
import io.github.humbleui.skija.Matrix33;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.skija.Path;
import io.github.humbleui.types.Rect;
import io.github.humbleui.skija.test.runner.*;

public class PaintTest implements Executable {
    @Override
    public void execute() throws Exception {
        TestRunner.testMethod(this, "base");
        TestRunner.testMethod(this, "fillPathWithPaint");
    }

    public void base() throws Exception {
        assertEquals(new Paint().setColor(0x12345678), new Paint().setColor(0x12345678));
        assertNotEquals(new Paint().setColor(0x12345678).hashCode(), new Paint().setColor(0x12345678).hashCode());
        assertNotEquals(new Paint().setColor(0x12345678), new Paint().setColor(0xFF345678));
        assertNotEquals(new Paint().setColor(0x12345678).hashCode(), new Paint().setColor(0xFF345678).hashCode());
        assertNotEquals(new Paint(), new Paint().setAntiAlias(false));
        assertNotEquals(new Paint(), new Paint().setDither(true));

        try (var p = new Paint().setColor(0x12345678);) {
            assertEquals(false, p == p.makeClone());
            assertEquals(p, p.makeClone());
            assertNotEquals(p.hashCode(), p.makeClone().hashCode());
        }

        try (var p = new Paint();) {
            assertEquals(false, p.hasNothingToDraw());

            p.setBlendMode(BlendMode.DST);
            assertEquals(true, p.hasNothingToDraw());

            p.setBlendMode(BlendMode.SRC_OVER);
            assertEquals(false, p.hasNothingToDraw());

            p.setAlpha(0);
            assertEquals(true, p.hasNothingToDraw());
        }
    }

    public void fillPathWithPaint() throws Exception {
         try (var paint = new Paint();
             var src = new Path().addRect(Rect.makeXYWH(10, 10, 50, 50));
             var dst = new Path()) {
            
            paint.setMode(PaintMode.STROKE).setStrokeWidth(5);
          
            Matrix33 identityMatrix = Matrix33.IDENTITY;
            boolean result = paint.fillPath(src, dst, null, identityMatrix);
            assertEquals(true, result);
            assertEquals(false, dst.isEmpty());
    
            dst.reset();
            Matrix33 scaleMatrix = Matrix33.makeScale(2.0f, 2.0f);
            result = paint.fillPath(src, dst, null, scaleMatrix);
            assertEquals(true, result);
            assertEquals(false, dst.isEmpty());
           
            dst.reset();
            Rect cullRect = Rect.makeXYWH(0, 0, 100, 100);
            result = paint.fillPath(src, dst, cullRect, identityMatrix);
            assertEquals(true, result);
            assertEquals(false, dst.isEmpty());

            dst.reset();
            result = paint.fillPath(src, dst, identityMatrix);
            assertEquals(true, result);
            assertEquals(false, dst.isEmpty());

            dst.reset();
            result = paint.fillPath(src, dst);
            assertEquals(true, result);
            assertEquals(false, dst.isEmpty());

            dst.reset();
            result = paint.fillPath(src, dst, 3.0f);
            assertEquals(true, result);
            assertEquals(false, dst.isEmpty());
        }
    }
}