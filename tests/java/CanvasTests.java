package io.github.humbleui.skija.test;

import static io.github.humbleui.skija.test.runner.TestRunner.*;

import java.util.concurrent.ArrayBlockingQueue;

import io.github.humbleui.types.*;
import io.github.humbleui.skija.Bitmap;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Path;
import io.github.humbleui.skija.test.runner.*;


public class CanvasTests implements Executable {
    @Override
    public void execute() throws Exception {
        TestRunner.testMethod(this, "localClipBounds");
        TestRunner.testMethod(this, "deviceClipBounds");
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
            Path clipPath = new Path();
            clipPath.moveTo(clipPoints[0], clipPoints[1]);
            for (int i = 2; i < clipPoints.length; i += 2) {
                clipPath.lineTo(clipPoints[i], clipPoints[i + 1]);
            }
            clipPath.closePath();
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
            Path clipPath = new Path();
            clipPath.moveTo(clipPoints[0], clipPoints[1]);
            for (int i = 2; i < clipPoints.length; i += 2) {
                clipPath.lineTo(clipPoints[i], clipPoints[i + 1]);
            }
            clipPath.closePath();
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
}