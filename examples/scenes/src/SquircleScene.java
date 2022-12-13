package io.github.humbleui.skija.examples.scenes;

import io.github.humbleui.skija.*;
import io.github.humbleui.types.*;

public class SquircleScene extends Scene {
    @Override
    public void draw(Canvas canvas, int width, int height, float dpi, int xpos, int ypos) {
        canvas.translate(30, 30);
        try (var paint = new Paint().setColor(0xFF0B99FF)) {
            try (var path = squircle(100, 100, 10)) {
                canvas.drawPath(path, paint);
                canvas.drawRRect(RRect.makeXYWH(0, 110, 100, 100, 10), paint);
                canvas.translate(110, 0);
            }

            try (var path = squircle(100, 100, 30)) {
                canvas.drawPath(path, paint);
                canvas.drawRRect(RRect.makeXYWH(0, 110, 100, 100, 30), paint);
                canvas.translate(110, 0);
            }

            try (var path = squircle(100, 100, 50)) {
                canvas.drawPath(path, paint);
                canvas.drawRRect(RRect.makeXYWH(0, 110, 100, 100, 50), paint);
                canvas.translate(110, 0);
            }

            try (var path = squircle(100, 40, 10)) {
                canvas.drawPath(path, paint);
                canvas.drawRRect(RRect.makeXYWH(0, 110, 100, 40, 10), paint);
                canvas.translate(110, 0);
            }

            try (var path = squircle(40, 100, 10)) {
                canvas.drawPath(path, paint);
                canvas.drawRRect(RRect.makeXYWH(0, 110, 40, 100, 10), paint);
                canvas.translate(110, 0);
            }
        }
    }

    public Path squircle(float w, float h, float r) {
        Path p = new Path();
        r = Math.min(w / 3.2f, Math.min(h / 3.2f, r));
        p.moveTo(0, 1.6f * r);
        p.cubicTo(0, 1.04f * r, 0, 0.76f * r, 0.109f * r, 0.546f * r);
        p.cubicTo(0.205f * r, 0.358f * r, 0.358f * r, 0.205f * r, 0.546f * r, 0.109f * r);
        p.cubicTo(0.76f * r, 0, 1.04f * r, 0, 1.6f * r, 0);

        p.lineTo(w - 1.6f * r, 0);
        p.cubicTo(w - 1.04f * r, 0, w - 0.76f * r, 0, w - 0.546f * r, 0.109f * r);
        p.cubicTo(w - 0.358f * r, 0.205f * r, w - 0.205f * r, 0.358f * r, w - 0.109f * r, 0.546f * r);
        p.cubicTo(w, 0.76f * r, w, 1.04f * r, w, 1.6f * r);
        
        p.lineTo(w, h - 1.6f * r);
        p.cubicTo(w, h - 1.04f * r, w, h - 0.76f * r, w - 0.109f * r, h - 0.546f * r);
        p.cubicTo(w - 0.205f * r, h - 0.358f * r, w - 0.358f * r, h - 0.205f * r, w - 0.546f * r, h - 0.109f * r);
        p.cubicTo(w - 0.76f * r, h, w - 1.04f * r, h, w - 1.6f * r, h);

        p.lineTo(1.6f * r, h);
        p.cubicTo(1.04f * r, h, 0.76f * r, h, 0.546f * r, h - 0.109f * r);
        p.cubicTo(0.358f * r, h - 0.205f * r, 0.205f * r, h - 0.358f * r, 0.109f * r, h - 0.546f * r);
        p.cubicTo(0, h - 0.76f * r, 0, h - 1.04f * r, 0, h - 1.6f * r);

        p.closePath();

        return p;
    }
}
