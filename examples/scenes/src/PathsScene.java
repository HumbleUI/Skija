package io.github.humbleui.skija.examples.scenes;

import io.github.humbleui.skija.*;
import io.github.humbleui.types.*;

public class PathsScene extends Scene {
    @Override
    public void draw(Canvas canvas, int width, int height, float dpi, int xpos, int ypos) {
        canvas.translate(30, 30);

        try (var paint = new Paint().setColor(0xFFF6BC01);) {
            drawPaths(canvas, paint);
        }

        try (var paint = new Paint().setColor(0xFF437AA0).setMode(PaintMode.STROKE).setStrokeWidth(1f);) {
            drawPaths(canvas, paint);
        }

        drawAdds(canvas);
        drawTransforms(canvas);
        drawFillPaths(canvas);
        drawPathsOp(canvas);
        drawInterpolate(canvas);
        drawMeasure(canvas);
    }

    public void drawPaths(Canvas canvas, Paint paint) {
        try (var tangentStroke = new Paint().setColor(0xFFFAA6B2).setMode(PaintMode.STROKE).setStrokeWidth(1f);
             var pathBuilder = new PathBuilder();)
        {
            canvas.save();

            // moveTo, lineTo, close
            for (var fillMode: PathFillMode.values()) {
                canvas.save();
                canvas.clipRect(Rect.makeLTRB(0, 0, 40, 40));
                pathBuilder.setFillMode(fillMode);
                pathBuilder.moveTo(20, 1.6f);
                pathBuilder.lineTo(31.7f, 37.8f);
                pathBuilder.lineTo(0.9f, 15.4f);
                pathBuilder.lineTo(39f, 15.4f);
                pathBuilder.lineTo(8.2f, 37.8f);
                pathBuilder.closePath();
                canvas.drawPathOnce(pathBuilder.detach(), paint);
                canvas.restore();
                canvas.translate(50, 0);
            }

            // rMoveTo, rLineTo
            pathBuilder.setFillMode(PathFillMode.EVEN_ODD);
            pathBuilder.rMoveTo(20, 1.6f).rLineTo(11.7f, 36.2f).rLineTo(-30.8f, -22.4f).rLineTo(38.1f, 0f).rLineTo(-30.8f, 22.4f);
            canvas.drawPathOnce(pathBuilder.snapshot(), paint);
            pathBuilder.reset();
            canvas.translate(50, 0);

            // quadTo, rQuadTo
            canvas.drawLine(0, 20, 20, 0, tangentStroke);
            canvas.drawLine(20, 0, 40, 20, tangentStroke);
            canvas.drawLine(0, 20, 20, 40, tangentStroke);
            canvas.drawLine(20, 40, 40, 20, tangentStroke);

            pathBuilder.rMoveTo(0, 20).quadTo(20, 0, 40, 20).rQuadTo(-20, 20, -40, 0);
            canvas.drawPathOnce(pathBuilder.detach(), paint);
            canvas.translate(50, 0);

            // conicTo, rConicTo
            canvas.drawLine(0, 20, 20, 0, tangentStroke);
            canvas.drawLine(20, 0, 40, 20, tangentStroke);
            canvas.drawLine(0, 20, 20, 40, tangentStroke);
            canvas.drawLine(20, 40, 40, 20, tangentStroke);

            pathBuilder.rMoveTo(0, 20).conicTo(20, 0, 40, 20, 0.5f).rConicTo(-20, 20, -40, 0, 2);
            canvas.drawPathOnce(pathBuilder.detach(), paint);
            canvas.translate(50, 0);

            // cubicTo, rCubicTo
            canvas.drawLine(0, 20, 0, 0, tangentStroke);
            canvas.drawLine(40, 0, 40, 20, tangentStroke);
            canvas.drawLine(0, 20, 10, 30, tangentStroke);
            canvas.drawLine(40, 20, 30, 30, tangentStroke);

            pathBuilder.rMoveTo(0, 20).cubicTo(0, 0, 40, 0, 40, 20).rCubicTo(-10, 10, -30, 10, -40, 0);
            canvas.drawPathOnce(pathBuilder.detach(), paint);
            canvas.translate(50, 0);

            // cubic apple rounding
            pathBuilder.lineTo(0, 20).arcTo(Rect.makeLTRB(0, 0, 40, 40), 180, -90, false).lineTo(40, 40).lineTo(40, 0).closePath();
            canvas.drawPathOnce(pathBuilder.detach(), paint);
            canvas.translate(50, 0);

            pathBuilder.lineTo(0, 10).cubicTo(0, 30, 10, 40, 30, 40).lineTo(40, 40).lineTo(40, 0).closePath();
            canvas.drawPathOnce(pathBuilder.detach(), paint);
            canvas.translate(50, 0);

            // Infinity
            canvas.translate(25, 20);
            pathBuilder.moveTo(0, 0) // Center
                       .cubicTo(  5,     -5,      9.5f, -10,     15, -10) // To top of right loop
                       .cubicTo( 20.5f, -10,     25,     -5.5f,  25,   0) // To far right of right loop
                       .cubicTo( 25,      5.5f,  20.5f,  10,     15,  10) // To bottom of right loop
                       .cubicTo(  9.5f,  10,      5,      5,      0,   0) // Back to center
                       .cubicTo( -5,     -5,     -9.5f, -10,    -15, -10) // To top of left loop
                       .cubicTo(-20.5f, -10,    -25,     -5.5f, -25,   0) // To far left of left loop
                       .cubicTo(-25,      5.5f, -20.5f,  10,    -15,  10) // To bottom of left loop
                       .cubicTo( -9.5f,  10,     -5,      5,      0,   0) // Back to center
                       .closePath();
            canvas.drawPathOnce(pathBuilder.detach(), paint);
            canvas.translate(35, -20);

            // arcTo
            canvas.drawOval(Rect.makeXYWH(0, 0, 40, 40), tangentStroke);
            canvas.drawLine(20, 20, 20, 0, tangentStroke);
            canvas.drawLine(20, 20, 40, 20, tangentStroke);
            pathBuilder.moveTo(0, 0).arcTo(Rect.makeLTRB(0, 0, 40, 40), -90, 90, false);
            canvas.drawPathOnce(pathBuilder.detach(), paint);
            canvas.translate(50, 0);

            // arcTo
            canvas.drawOval(Rect.makeXYWH(0, 0, 40, 40), tangentStroke);
            canvas.drawLine(20, 20, 20, 0, tangentStroke);
            canvas.drawLine(20, 20, 40, 20, tangentStroke);
            pathBuilder.moveTo(0, 0).arcTo(Rect.makeLTRB(0, 0, 40, 40), -90, 90, true);
            canvas.drawPathOnce(pathBuilder.detach(), paint);
            canvas.translate(50, 0);

            // tangentArcTo
            canvas.drawLine(0, 20, 20, 0, tangentStroke);
            canvas.drawLine(20, 0, 40, 20, tangentStroke);
            canvas.drawOval(Rect.makeXYWH(10, 4, 20, 20), tangentStroke);
            pathBuilder.moveTo(0, 20).tangentArcTo(20, 0, 40, 20, 10);
            canvas.drawPathOnce(pathBuilder.detach(), paint);
            canvas.translate(50, 0);

            // ellipticalArcTo, getBounds
            pathBuilder.moveTo(0, 30).ellipticalArcTo(30, 15, 30, PathEllipseArc.SMALLER, PathDirection.CLOCKWISE, 40, 30);
            Path path = pathBuilder.detach();
            Rect bounds = path.getBounds();
            canvas.drawRect(bounds, tangentStroke);
            canvas.drawPathOnce(path, paint);
            canvas.translate(50, 0);

            // ellipticalArcTo, computeTightBounds
            pathBuilder.moveTo(0, 30).ellipticalArcTo(30, 15, 30, PathEllipseArc.LARGER, PathDirection.CLOCKWISE, 40, 30);
            path = pathBuilder.detach();
            bounds = path.computeTightBounds();
            canvas.drawRect(bounds, tangentStroke);
            canvas.drawPathOnce(path, paint);
            canvas.translate(50, 0);

            // rEllipticalArcTo, computeFiniteBounds
            pathBuilder.moveTo(0, 10).rEllipticalArcTo(30, 15, 30, PathEllipseArc.SMALLER, PathDirection.COUNTER_CLOCKWISE, 40, 0);
            bounds = pathBuilder.computeFiniteBounds();
            canvas.drawRect(bounds, tangentStroke);
            canvas.drawPathOnce(pathBuilder.detach(), paint);
            canvas.translate(50, 0);

            // rEllipticalArcTo, computeTightBounds
            pathBuilder.moveTo(0, 10).rEllipticalArcTo(30, 15, 30, PathEllipseArc.LARGER, PathDirection.COUNTER_CLOCKWISE, 40, 0);
            bounds = pathBuilder.computeTightBounds();
            canvas.drawRect(bounds, tangentStroke);
            canvas.drawPathOnce(pathBuilder.detach(), paint);
            canvas.translate(70, 0);

            // multi shapes
            pathBuilder.setFillMode(PathFillMode.EVEN_ODD)
                       .arcTo(Rect.makeLTRB(0, 5, 35, 40), 0, 359, true)
                       .closePath()
                       .addPolygon(new float[] { 5, 0, 35, 0, 35, 30, 5, 30 }, true)
                       .moveTo(5, 35)
                       .lineTo(20, 5)
                       .lineTo(35, 35)
                       .closePath();
            canvas.drawPathOnce(pathBuilder.detach(), paint);
            canvas.translate(50, 0);

            canvas.restore();
            canvas.translate(0, 50);
        }
    }

    private void drawAdds(Canvas canvas) {
        canvas.save();
        try (var pathBuilder = new PathBuilder();
             var paint = new Paint().setColor(0xFF437AA0).setMode(PaintMode.STROKE).setStrokeWidth(1f)) {

            // addRect
            pathBuilder.addRect(Rect.makeLTRB(10, 10, 30, 30), PathDirection.CLOCKWISE, 0).lineTo(20, 20);
            canvas.drawPathOnce(pathBuilder.detach(), paint);
            canvas.translate(50, 0);

            pathBuilder.addRect(Rect.makeLTRB(10, 10, 30, 30), PathDirection.COUNTER_CLOCKWISE, 1).lineTo(20, 20);
            canvas.drawPathOnce(pathBuilder.detach(), paint);
            canvas.translate(50, 0);

            // addOval
            pathBuilder.addOval(Rect.makeLTRB(10, 0, 30, 40), PathDirection.CLOCKWISE, 0).lineTo(20, 20);
            canvas.drawPathOnce(pathBuilder.detach(), paint);
            canvas.translate(50, 0);

            pathBuilder.addOval(Rect.makeLTRB(10, 0, 30, 40), PathDirection.COUNTER_CLOCKWISE, 1).lineTo(20, 20);
            canvas.drawPathOnce(pathBuilder.detach(), paint);
            canvas.translate(50, 0);

            // addCircle
            pathBuilder.addCircle(20, 20, 15, PathDirection.CLOCKWISE).lineTo(20, 20);
            canvas.drawPathOnce(pathBuilder.detach(), paint);
            canvas.translate(50, 0);

            // addArc
            pathBuilder.addArc(Rect.makeLTRB(-40, -40, 40, 40), 0, 90);
            canvas.drawPathOnce(pathBuilder.detach(), paint);
            canvas.translate(50, 0);

            // addRRect
            for (int i = 0; i < 8; ++i) {
                pathBuilder.addRRect(RRect.makeLTRB(10, 10, 30, 30, 5), PathDirection.CLOCKWISE, i).lineTo(20, 20);
                canvas.drawPathOnce(pathBuilder.detach(), paint);
                canvas.translate(50, 0);
            }

            // addPoly
            pathBuilder.addPolygon(new float[] { 40, 0, 40, 40, 30, 40, 10, 20, 10, 40, 0, 40, 0, 0, 10, 0, 30, 20, 30, 0 }, false);
            canvas.drawPathOnce(pathBuilder.detach(), paint);
            canvas.translate(50, 0);

            // addPath
            try (Path subpath = new PathBuilder().addRect(Rect.makeLTRB(0, 0, 10, 10)).closePath().build()) {
                pathBuilder.addRect(Rect.makeLTRB(0, 0, 40, 40)).addPath(subpath, 10, 10, true);
                canvas.drawPathOnce(pathBuilder.detach(), paint);
                canvas.translate(50, 0);

                pathBuilder.addRect(Rect.makeLTRB(0, 0, 40, 40)).addPath(subpath, 10, 10, false);
                canvas.drawPathOnce(pathBuilder.detach(), paint);
                canvas.translate(50, 0);

                pathBuilder.addRect(Rect.makeLTRB(0, 0, 40, 40)).addPath(subpath, Matrix33.makeRotate(-15), true);
                canvas.drawPathOnce(pathBuilder.detach(), paint);
                canvas.translate(50, 0);
            }
        }
        canvas.restore();
        canvas.translate(0, 50);
    }

    private void drawTransforms(Canvas canvas) {
        canvas.save();
        try (var pathBuilder = new PathBuilder();
             var paint = new Paint().setColor(0xFF437AA0).setMode(PaintMode.STROKE).setStrokeWidth(1f);
             var secondaryPaint = new Paint().setColor(0xFFFAA6B2).setMode(PaintMode.STROKE).setStrokeWidth(1f);) {

            // offset
            pathBuilder.addRRect(RRect.makeLTRB(0, 0, 20, 20, 5));
            canvas.drawPathOnce(pathBuilder.snapshot(), secondaryPaint);
            pathBuilder.offset(5, 5);
            canvas.drawPathOnce(pathBuilder.detach(), paint);
            canvas.translate(50, 0);

            // transform
            pathBuilder.addRRect(RRect.makeLTRB(0, 0, 20, 20, 5));
            canvas.drawPathOnce(pathBuilder.snapshot(), secondaryPaint);
            pathBuilder.transform(Matrix33.makeRotate(-15));
            canvas.drawPathOnce(pathBuilder.detach(), paint);
            canvas.translate(50, 0);
        }
        canvas.restore();
        canvas.translate(0, 50);
    }

    private void drawFillPaths(Canvas canvas) {
        canvas.save();

        try (Path path = new PathBuilder().moveTo(20, 5).lineTo(35, 35).lineTo(5, 35).closePath().build();
             Paint stroke1 = new Paint().setColor(0xFF247ba0).setMode(PaintMode.STROKE).setStrokeWidth(1))
        {
            Paint[] paints = new Paint[] {
                new Paint().setColor(0xFFffe066).setMode(PaintMode.STROKE).setStrokeWidth(10),
                new Paint().setColor(0xFFffe066).setMode(PaintMode.STROKE).setStrokeWidth(10).setStrokeMiter(2),
                new Paint().setColor(0xFFffe066).setMode(PaintMode.STROKE).setStrokeWidth(10).setStrokeJoin(PaintStrokeJoin.ROUND),
                new Paint().setColor(0xFFffe066).setMode(PaintMode.STROKE).setStrokeWidth(10).setStrokeJoin(PaintStrokeJoin.BEVEL)
            };

            for (Paint stroke5: paints) {
                canvas.drawPath(path, stroke5);
                canvas.drawPath(path, stroke1);
                canvas.translate(50, 0);

                try (Path fillPath = stroke5.getFillPath(path)) {
                    canvas.drawPath(fillPath, stroke1);
                    canvas.translate(50, 0);
                }

                try (Path fillPath = stroke5.getFillPath(path, Rect.makeLTRB(15, 15, 25, 25), 0.1f)) {
                    canvas.drawPath(fillPath, stroke1);
                    canvas.translate(50, 0);
                }

                stroke5.close();
            }
        }

        try (Path path = new PathBuilder().moveTo(5, 35).lineTo(15, 10).lineTo(25, 30).lineTo(35, 5).build();
             Paint stroke1 = new Paint().setColor(0xFF247ba0).setMode(PaintMode.STROKE).setStrokeWidth(1))
        {
            Paint[] paints = new Paint[] {
                new Paint().setColor(0xFFffe066).setMode(PaintMode.STROKE).setStrokeWidth(10),
                new Paint().setColor(0xFFffe066).setMode(PaintMode.STROKE).setStrokeWidth(10).setStrokeCap(PaintStrokeCap.ROUND),
                new Paint().setColor(0xFFffe066).setMode(PaintMode.STROKE).setStrokeWidth(10).setStrokeCap(PaintStrokeCap.SQUARE)
            };
            for (Paint stroke5: paints) {
                canvas.drawPath(path, stroke5);
                canvas.drawPath(path, stroke1);
                canvas.translate(50, 0);
                try (Path fillPath = stroke5.getFillPath(path)) {
                    canvas.drawPath(fillPath, stroke1);
                }
                canvas.translate(50, 0);
                stroke5.close();
            }
        }

        canvas.restore();
        canvas.translate(0, 50);
    }

    private void drawPathsOp(Canvas canvas) {
        canvas.save();

        try (
                Paint paint1 = new Paint().setMode(PaintMode.FILL).setColor(0xFF437AA0);
                Paint paint2 = new Paint().setMode(PaintMode.FILL).setColor(0x80FAA6B2);
                Paint paint3 = new Paint().setMode(PaintMode.FILL).setColor(0xFFF6BC01);
                Path one = new PathBuilder().moveTo(5, 5)
                                            .conicTo(0, 35, 20, 20, 3)
                                            .conicTo(35, 0, 35, 35, 2)
                                            .closePath()
                                            .build();
                Path two = new PathBuilder().addRect(Rect.makeLTRB(15, 15, 40, 40))
                                            .build();
        ) {
            canvas.drawPath(one, paint1);
            canvas.drawPath(two, paint2);

            for (PathOp op : PathOp.values()) {
                try (Path result = Path.makeCombining(one, two, op)) {
                    canvas.translate(50, 0);
                    canvas.drawPath(result, paint3);
                }
            }
        }

        canvas.restore();
        canvas.translate(0, 50);
    }

    private void drawInterpolate(Canvas canvas) {
        float weight = 1f - System.currentTimeMillis() % 500 / 500f;
        long pair = (System.currentTimeMillis() / 500) % 4;

        try (Path p1 = new PathBuilder().moveTo(0, 14).lineTo(20, 14).lineTo(20, 0).lineTo(40, 20).lineTo(20, 40).lineTo(20, 26).lineTo(0, 26).lineTo(0, 14).closePath().build();
             Path p2 = new PathBuilder().moveTo(0, 20).lineTo(14, 20).lineTo(14, 0).lineTo(26, 0).lineTo(26, 20).lineTo(40, 20).lineTo(20, 40).lineTo(0, 20).closePath().build();
             Path p3 = new PathBuilder().moveTo(0, 20).lineTo(20, 0).lineTo(20, 14).lineTo(40, 14).lineTo(40, 26).lineTo(20, 26).lineTo(20, 40).lineTo(0, 20).closePath().build();
             Path p4 = new PathBuilder().moveTo(0, 20).lineTo(20, 0).lineTo(40, 20).lineTo(26, 20).lineTo(26, 40).lineTo(14, 40).lineTo(14, 20).lineTo(0, 20).closePath().build();
             Path target = pair == 0 ? p1.makeLerp(p2, weight)
                         : pair == 1 ? p2.makeLerp(p3, weight)
                         : pair == 2 ? p3.makeLerp(p4, weight)
                         : p4.makeLerp(p1, weight);
             Paint stroke = new Paint().setColor(0xFF437AA0).setMode(PaintMode.STROKE).setStrokeWidth(1);)
        {
            target.setVolatile(true);
            assert p1.isInterpolatable(p2);
            assert p1.isInterpolatable(p3);
            assert p1.isInterpolatable(p4);

            canvas.drawPath(target, stroke);
            canvas.translate(0, 50);
        }
    }

    private void drawMeasure(Canvas canvas) {
        canvas.save();

        try (var path = new PathBuilder().addCircle(20, 20, 20, PathDirection.COUNTER_CLOCKWISE).build();
             var measure = new PathMeasure(path, false);
             var segment = new PathBuilder();
             var arcStroke = new Paint().setColor(0xFF437AA0).setMode(PaintMode.STROKE).setStrokeWidth(1);
             var tangentStroke = new Paint().setColor(0xFFFAA6B2).setMode(PaintMode.STROKE).setStrokeWidth(1);)
        {
            canvas.drawPath(path, arcStroke);
            canvas.translate(50, 0);

            measure.getSegment(50, 100, segment, true);
            canvas.drawPathOnce(segment.detach(), arcStroke);

            for (float d = 50; d < 100; d += 10) {
                Point position = measure.getPosition(d);
                Point tangent = measure.getTangent(d);
                canvas.drawLine(position.getX(), position.getY(), position.getX() + tangent.getX() * 15f, position.getY() + tangent.getY() * 15f, tangentStroke);
            }
        }

        canvas.restore();
        canvas.translate(0, 50);
    }
}
