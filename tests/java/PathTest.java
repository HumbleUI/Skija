package io.github.humbleui.skija.test;

import static io.github.humbleui.skija.test.runner.TestRunner.assertArrayEquals;
import static io.github.humbleui.skija.test.runner.TestRunner.assertEquals;
import static io.github.humbleui.skija.test.runner.TestRunner.assertNotEquals;
import static io.github.humbleui.skija.test.runner.TestRunner.assertThrows;

import java.util.NoSuchElementException;
import java.util.Random;

import io.github.humbleui.skija.*;
import io.github.humbleui.skija.impl.Native;
import io.github.humbleui.types.Point;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import io.github.humbleui.skija.test.runner.Executable;
import io.github.humbleui.skija.test.runner.TestRunner;

public class PathTest implements Executable {
    @Override
    public void execute() throws Exception {
        TestRunner.testMethod(this, "iter");
        TestRunner.testMethod(this, "convexity");
        TestRunner.testMethod(this, "isShape");
        TestRunner.testMethod(this, "checks");
        TestRunner.testMethod(this, "storage");
        TestRunner.testMethod(this, "swap");
        TestRunner.testMethod(this, "contains");
        TestRunner.testMethod(this, "utils");
        TestRunner.testMethod(this, "serialize");
        TestRunner.testMethod(this, "fillWithPaint");
        TestRunner.testMethod(this, "dashedFillPathBoundsTest");
        TestRunner.testMethod(this, "iterClosedContour");
        TestRunner.testMethod(this, "iterAfterClose");
        TestRunner.testMethod(this, "iterLarge");
        TestRunner.testMethod(this, "iterMatchesSkia");
        TestRunner.testMethod(this, "iterCloseAfterNaN");
    }

    public void iter() {
        try (Path p = new PathBuilder().moveTo(10, 10).lineTo(20, 0).lineTo(20, 20).closePath().build();
             var i = p.iterator();) {
            assertEquals(true, i.hasNext());
            PathSegment s = i.next();
            assertEquals(PathVerb.MOVE, s.getVerb());
            assertEquals(new Point(10, 10), s.getP0());
            assertEquals(true, s.isClosedContour());

            assertEquals(true, i.hasNext());
            s = i.next();
            assertEquals(PathVerb.LINE, s.getVerb());
            assertEquals(new Point(10, 10), s.getP0());
            assertEquals(new Point(20, 0), s.getP1());
            assertEquals(false, s.isCloseLine());

            assertEquals(true, i.hasNext());
            s = i.next();
            assertEquals(PathVerb.LINE, s.getVerb());
            assertEquals(new Point(20, 0), s.getP0());
            assertEquals(new Point(20, 20), s.getP1());
            assertEquals(false, s.isCloseLine());

            assertEquals(true, i.hasNext());
            s = i.next();
            assertEquals(PathVerb.LINE, s.getVerb());
            assertEquals(new Point(20, 20), s.getP0());
            assertEquals(new Point(10, 10), s.getP1());
            assertEquals(true, s.isCloseLine());

            assertEquals(true, i.hasNext());
            s = i.next();
            assertEquals(PathVerb.CLOSE, s.getVerb());
            assertEquals(new Point(10, 10), s.getP0());

            assertEquals(false, i.hasNext());
            assertThrows(NoSuchElementException.class, () -> i.next());
        }
    }


    public void convexity() {
        try (Path p = new PathBuilder().lineTo(40, 20).lineTo(0, 40).lineTo(0, 0).closePath().build()) {
            assertEquals(true, p.isConvex());
        }

        try (Path p = new PathBuilder().lineTo(40, 40).lineTo(40, 0).lineTo(0, 40).lineTo(0, 0).closePath().build()) {
            assertEquals(false, p.isConvex());
        }
    }

    public void isShape() {
        for (var dir: PathDirection.values()) {
            for (int start = 0; start < 4; ++start) {
                try (Path p = new PathBuilder().addRect(Rect.makeLTRB(0, 0, 40, 20), dir, start).build()) {
                    assertEquals(Rect.makeLTRB(0, 0, 40, 20), p.isRect());
                    assertEquals(null, p.isOval());
                    assertEquals(null, p.isRRect());
                }
            }
        }

        for (var dir: PathDirection.values()) {
            for (int start = 0; start < 4; ++start) {
                try (Path p = new PathBuilder().addOval(Rect.makeLTRB(0, 0, 40, 20), dir, start).build()) {
                    assertEquals(null, p.isRect());
                    assertEquals(Rect.makeLTRB(0, 0, 40, 20), p.isOval());
                    assertEquals(null, p.isRRect());
                }
            }
        }

        for (var dir: PathDirection.values()) {
            try (Path p = new PathBuilder().addCircle(20, 20, 20, dir).build()) {
                assertEquals(null, p.isRect());
                assertEquals(Rect.makeLTRB(0, 0, 40, 40), p.isOval());
                assertEquals(null, p.isRRect());
            }
        }

        for (var dir: PathDirection.values()) {
            for (int start = 0; start < 8; ++start) {
                try (Path p = new PathBuilder().addRRect(RRect.makeLTRB(0, 0, 40, 20, 5), dir, start).build()) {
                    assertEquals(null, p.isRect());
                    assertEquals(null, p.isOval());
                    assertEquals(RRect.makeLTRB(0, 0, 40, 20, 5), p.isRRect());
                }

                try (Path p = new PathBuilder().addRRect(RRect.makeLTRB(0, 0, 40, 20, 0), dir, start).build()) {
                    assertEquals(Rect.makeLTRB(0, 0, 40, 20), p.isRect());
                    assertEquals(null, p.isOval());
                    assertEquals(null, p.isRRect());
                }

                try (Path p = new PathBuilder().addRRect(RRect.makeLTRB(0f, 0f, 40f, 20f, 20f, 10f), dir, start).build()) {
                    assertEquals(null, p.isRect());
                    assertEquals(Rect.makeLTRB(0, 0, 40, 20), p.isOval());
                    assertEquals(null, p.isRRect());
                }
            }
        }

        try (Path p = new PathBuilder().lineTo(40, 40).lineTo(40, 0).lineTo(0, 40).lineTo(0, 0).closePath().build()) {
            assertEquals(null, p.isRect());
            assertEquals(null, p.isOval());
            assertEquals(null, p.isRRect());
        }
    }

    public void checks() {
        try (PathBuilder b = new PathBuilder().lineTo(40, 40).lineTo(40, 0).lineTo(0, 40).lineTo(0, 0).closePath()) {
            assertEquals(false, b.isEmpty());
            b.reset();
            assertEquals(true, b.isEmpty());
        }

        try (PathBuilder b = new PathBuilder().lineTo(40, 40).lineTo(40, 0).lineTo(0, 40).lineTo(0, 0)) {
            assertEquals(false, b.snapshot().isLastContourClosed());
            b.closePath();
            assertEquals(true, b.snapshot().isLastContourClosed());
            b.moveTo(100, 100).lineTo(140, 140).lineTo(140, 100).lineTo(100, 140);
            assertEquals(false, b.snapshot().isLastContourClosed());
            b.closePath();
            assertEquals(true, b.snapshot().isLastContourClosed());
        }

        try (Path p = new PathBuilder().lineTo(40, 40).lineTo(40, 0).lineTo(0, 40).lineTo(0, 0).build()) {
            assertEquals(true, p.isFinite());
        }

        try (Path p = new PathBuilder().lineTo(40, 40).lineTo(Float.POSITIVE_INFINITY, 0).lineTo(0, 40).lineTo(0, 0).closePath().build()) {
            assertEquals(false, p.isFinite());
        }

        try (Path p = new PathBuilder().lineTo(40, 40).lineTo(40, 0).lineTo(0, 40).lineTo(0, 0).build()) {
            assertEquals(false, p.isVolatile());
            p.setVolatile(true);
            assertEquals(true, p.isVolatile());
            p.setVolatile(false);
            assertEquals(false, p.isVolatile());
        }

        try (Path p = new PathBuilder().lineTo(40, 40).lineTo(40, 0).lineTo(0, 40).lineTo(0, 0).closePath().build()) {
            assertEquals(null, p.getAsLine());
        }

        try (Path p = new PathBuilder().moveTo(20, 20).lineTo(40, 40).build()) {
            assertArrayEquals(new Point[] { new Point(20, 20), new Point(40, 40) }, p.getAsLine());
        }
    }

    public void storage() {
        Path subpath = new PathBuilder().lineTo(40, 40).lineTo(40, 0).lineTo(0, 40).lineTo(0, 0).closePath().build();
        for (PathBuilder b: new PathBuilder[] {
                       new PathBuilder().addPath(subpath),
                       new PathBuilder().incReserve(10).addPath(subpath).closePath()
                     })
        {
            TestRunner.pushStack(b.toString());
            try {
                Point p0 = new Point(0, 0);
                Point p1 = new Point(40, 40);
                Point p2 = new Point(40, 0);
                Point p3 = new Point(0, 40);
                Point p4 = new Point(0, 0);
                Point p5 = new Point(10, 10);

                assertArrayEquals(new Point[] { p0, p1, p2, p3, p4 }, b.getPoints());
                assertEquals(p4, b.getLastPt());
                b.setLastPt(p5);
                assertArrayEquals(new Point[] { p0, p1, p2, p3, p5 }, b.getPoints());
                assertEquals(p5, b.getLastPt());

                assertArrayEquals(new PathVerb[] { PathVerb.MOVE, PathVerb.LINE, PathVerb.LINE, PathVerb.LINE, PathVerb.LINE, PathVerb.CLOSE }, b.getVerbs());

                Path p = b.build();
                assertNotEquals(0L, p.getApproximateBytesUsed());

                assertArrayEquals(new PathSegmentType[] { PathSegmentType.LINE }, p.getSegmentTypes());
            } finally {
                TestRunner.popStack();
            }
        }
    }

    public void swap() {
        try (Path p1 = new PathBuilder().lineTo(40, 40).lineTo(40, 0).lineTo(0, 40).lineTo(0, 0).closePath().build();
             Path p2 = new PathBuilder().lineTo(0, 0).lineTo(20, 20).build();) {
            p1.swap(p2);
            assertEquals(new PathBuilder().lineTo(0, 0).lineTo(20, 20).build(), p1);
            assertEquals(new PathBuilder().lineTo(40, 40).lineTo(40, 0).lineTo(0, 40).lineTo(0, 0).closePath().build(), p2);
        }
    }

    public void contains() {
        try (Path p = new PathBuilder().addRRect(RRect.makeLTRB(10, 20, 54, 120, 10, 20)).build()) {
            assertEquals(true, p.conservativelyContainsRect(Rect.makeLTRB(10, 40, 54, 80)));
            assertEquals(true, p.conservativelyContainsRect(Rect.makeLTRB(25, 20, 39, 120)));
            assertEquals(true, p.conservativelyContainsRect(Rect.makeLTRB(15, 25, 49, 115)));
            assertEquals(true, p.conservativelyContainsRect(Rect.makeLTRB(13, 27, 51, 113)));

            assertEquals(false, p.conservativelyContainsRect(Rect.makeLTRB(0, 40, 60, 80)));

            assertEquals(true, p.contains(30, 70));
            assertEquals(false, p.contains(0, 0));
        }
    }

    public void utils() {
        assertEquals(false, Path.isLineDegenerate(new Point(0, 0), new Point(10, 0), false));
        assertEquals(true, Path.isLineDegenerate(new Point(0, 0), new Point(0, 0), true));
        assertEquals(true, Path.isLineDegenerate(new Point(0, 0), new Point(0, 0), false));
        assertEquals(false, Path.isLineDegenerate(new Point(0, 0), new Point(0, 1e-13f), true));
        // assertEquals(true, Path.isLineDegenerate(new Point(0, 0), new Point(0, 1e-13f), false));

        assertEquals(false, Path.isQuadDegenerate(new Point(0, 0), new Point(10, 0), new Point(0, 0), false));
        assertEquals(true, Path.isQuadDegenerate(new Point(0, 0), new Point(0, 0), new Point(0, 0), false));

        assertEquals(false, Path.isCubicDegenerate(new Point(0, 0), new Point(10, 0), new Point(0, 0), new Point(0, 0), false));
        assertEquals(true, Path.isCubicDegenerate(new Point(0, 0), new Point(0, 0), new Point(0, 0), new Point(0, 0), false));        

        assertArrayEquals(
            new Point[] {new Point(0.0f, 20.0f), new Point(6.666667f, 13.333334f), new Point(20.0f, 13.333334f), new Point(33.333336f, 13.333334f), new Point(40.0f, 20.0f)},
            Path.convertConicToQuads(new Point(0, 20), new Point(20, 0), new Point(40, 20), 0.5f, 1)
        );
        assertArrayEquals(
            new Point[] {new Point(0.0f, 20.0f), new Point(3.0940108f, 16.90599f), new Point(8.452995f, 15.119661f), new Point(13.811979f, 13.333334f), new Point(20.0f, 13.333334f), new Point(26.188023f, 13.333334f), new Point(31.547007f, 15.119661f), new Point(36.90599f, 16.90599f), new Point(40.0f, 20.0f)},
            Path.convertConicToQuads(new Point(0, 20), new Point(20, 0), new Point(40, 20), 0.5f, 2)
        );
        assertArrayEquals(
            new Point[] {new Point(20.0f, 170.0f), new Point(44.850613f, 170.0f), new Point(62.425304f, 187.57468f), new Point(80.0f, 205.14938f), new Point(80.0f, 230.0f)},
            Path.convertConicToQuads(new Point(20, 170), new Point(80, 170), new Point(80, 230), 0.707f, 1)
        );

        try (PathBuilder b = new PathBuilder().lineTo(40, 40)) {
            var g1 = b.snapshot().getGenerationId();
            b.lineTo(10, 40);
            var g2 = b.snapshot().getGenerationId();
            assertNotEquals(g1, g2);
            var g3 = b.snapshot().getGenerationId();
            assertNotEquals(g2, g3);
        }
    }

    public void serialize() {
        try (Path p = new PathBuilder().lineTo(40, 40).lineTo(40, 0).lineTo(0, 40).lineTo(0, 0).closePath().build();) {
            Path p2 = Path.makeFromBytes(p.serializeToBytes());
            assertEquals(p, p2);
        }
    }

    public void fillWithPaint() throws Exception {
         try (var paint = new Paint();
             var src = new PathBuilder().addRect(Rect.makeXYWH(10, 10, 50, 50)).build();
             var dst = new PathBuilder();
         ) {

            paint.setMode(PaintMode.STROKE).setStrokeWidth(5);

            Matrix33 identityMatrix = Matrix33.IDENTITY;
            boolean result = src.fillWithPaint(paint, dst, null, identityMatrix);
            assertEquals(true, result);
            assertEquals(false, dst.isEmpty());

            dst.reset();
            Matrix33 scaleMatrix = Matrix33.makeScale(2.0f, 2.0f);
            result = src.fillWithPaint(paint, dst, null, scaleMatrix);
            assertEquals(true, result);
            assertEquals(false, dst.isEmpty());

            dst.reset();
            Rect cullRect = Rect.makeXYWH(0, 0, 100, 100);
            result = src.fillWithPaint(paint, dst, cullRect, identityMatrix);
            assertEquals(true, result);
            assertEquals(false, dst.isEmpty());

            dst.reset();
            result = src.fillWithPaint(paint, dst, identityMatrix);
            assertEquals(true, result);
            assertEquals(false, dst.isEmpty());

            dst.reset();
            result = src.fillWithPaint(paint, dst);
            assertEquals(true, result);
            assertEquals(false, dst.isEmpty());

            dst.reset();
            result = src.fillWithPaint(paint, dst, 3.0f);
            assertEquals(true, result);
            assertEquals(false, dst.isEmpty());
        }
    }

    public void dashedFillPathBoundsTest() {
        final float w = 3.5f;
        final float[] dash = { 12.5f, 9.375f };
        final float expectedWidth = 2000f;

         try (Paint paint = new Paint();
              Path src = new PathBuilder()
                           .moveTo(0f, 1000f)
                           .lineTo(2000f, 1000f)
                           .build();)
         {
            paint.setMode(PaintMode.STROKE);
            paint.setStrokeWidth(w);
            paint.setStrokeCap(PaintStrokeCap.BUTT);
            paint.setPathEffect(PathEffect.makeDash(dash, 0f));

            try (PathBuilder dst = new PathBuilder()) {
                src.fillWithPaint(paint, dst);
                Rect bounds = dst.detach().getBounds();
                assertEquals(expectedWidth, bounds.getWidth());
                assertEquals(w, bounds.getHeight());
            }
            try (PathBuilder dst = new PathBuilder()) {
               src.fillWithPaint(paint, dst, Rect.makeLTRB(-1e7f, -1e7f, 1e7f, 1e7f), Matrix33.IDENTITY);
               Rect bounds = dst.detach().getBounds();
               assertEquals(expectedWidth, bounds.getWidth());
               assertEquals(w, bounds.getHeight());
            }
            try (Path dst = paint.getFillPath(src)) {
                Rect bounds = dst.getBounds();
                assertEquals(expectedWidth, bounds.getWidth());
                assertEquals(w, bounds.getHeight());
            }
        }
    }

    public void iterClosedContour() {
        try (Path p = new PathBuilder()
                .moveTo(0, 0).lineTo(10, 0).lineTo(10, 10)
                .moveTo(20, 0).lineTo(30, 0).lineTo(30, 10).closePath()
                .moveTo(40, 0).lineTo(50, 0)
                .build()) {
            // Skia quirk: the last segment of an open contour and CLOSE report the next contour
            assertEquals(String.join(", ",
                "MOVE", "LINE", "LINE closed",
                "MOVE closed", "LINE closed", "LINE closed", "LINE closeLine closed", "CLOSE",
                "MOVE", "LINE"
            ), segments(p, false));
            // Skia quirk: after the last verb, the contour is reported open even with forceClose
            assertEquals(String.join(", ",
                "MOVE closed", "LINE closed", "LINE closed", "LINE closeLine closed", "CLOSE closed",
                "MOVE closed", "LINE closed", "LINE closed", "LINE closeLine closed", "CLOSE closed",
                "MOVE closed", "LINE", "LINE closeLine", "CLOSE"
            ), segments(p, true));
        }
    }

    public void iterAfterClose() {
        Path p = new PathBuilder().moveTo(1, 2).lineTo(3, 4).build();
        try (var it = p.iterator()) {
            p.close();
            assertEquals(new PathSegment(PathVerb.MOVE.ordinal(), 1, 2, false), it.next());
            assertEquals(new PathSegment(1, 2, 3, 4, false, false), it.next());
            assertEquals(false, it.hasNext());
        }
    }

    public void iterLarge() {
        int segments = 400_000;
        float[] coords = new float[segments * 2];
        for (int i = 0; i < coords.length; i++)
            coords[i] = i % 1000;
        try (Path p = new PathBuilder().moveTo(0, 0).polylineTo(coords).build()) {
            long start = System.nanoTime();
            int count = 0;
            try (var it = p.iterator()) {
                while (it.hasNext()) {
                    it.next();
                    count++;
                }
            }
            long elapsedMs = (System.nanoTime() - start) / 1_000_000;
            assertEquals(segments + 1, count);
            assertEquals("under 2000 ms", elapsedMs < 2000 ? "under 2000 ms" : elapsedMs + " ms");
        }
    }

    private static String segments(Path p, boolean forceClose) {
        StringBuilder res = new StringBuilder();
        try (var it = p.iterator(forceClose)) {
            while (it.hasNext()) {
                PathSegment s = it.next();
                if (res.length() > 0)
                    res.append(", ");
                res.append(s.getVerb()).append(s.isCloseLine() ? " closeLine" : "").append(s.isClosedContour() ? " closed" : "");
            }
        }
        return res.toString();
    }

    // PathSegmentIterator repeats SkPath::Iter::isClosedContour() logic: compare with Skia on random paths
    public void iterMatchesSkia() {
        String mismatch = null;
        for (int seed = 0; seed < 1000 && mismatch == null; seed++) {
            try (Path p = randomPath(new Random(seed))) {
                for (boolean forceClose : new boolean[] {false, true}) {
                    String m = _nFindIterMismatch(Native.getPtr(p), forceClose);
                    if (m != null && mismatch == null)
                        mismatch = "seed " + seed + ", forceClose " + forceClose + ", " + m;
                }
            }
        }
        assertEquals(null, mismatch);
    }

    private static Path randomPath(Random r) {
        try (PathBuilder b = new PathBuilder()) {
            int ops = 1 + r.nextInt(30);
            for (int i = 0; i < ops; i++) {
                float x = r.nextInt(5) * 10, y = r.nextInt(5) * 10; // small grid: frequent coincident points
                switch (r.nextInt(10)) {
                    case 0: case 1: b.moveTo(x, y); break;
                    case 2: case 3: b.lineTo(x, y); break;
                    case 4: b.quadTo(x, y, y, x); break;
                    case 5: b.conicTo(x, y, y, x, 0.5f); break;
                    case 6: b.cubicTo(x, y, y, x, x, x); break;
                    case 7: case 8: b.closePath(); break;
                    default: b.lineTo(r.nextInt(20) == 0 ? Float.NaN : x, y); break;
                }
            }
            return b.detach();
        }
    }

    public void iterCloseAfterNaN() {
        try (Path closed = new PathBuilder().moveTo(1, 2).lineTo(5, 6).lineTo(Float.NaN, 3).closePath().build();
             Path open = new PathBuilder().moveTo(1, 2).lineTo(5, 6).lineTo(Float.NaN, 3).build()) {
            // Skia gives no point for CLOSE after NaN; like any CLOSE, it gets the contour start
            assertEquals(new Point(1, 2), lastSegment(closed, false).getP0());
            assertEquals(new Point(1, 2), lastSegment(open, true).getP0());
        }
    }

    private static PathSegment lastSegment(Path p, boolean forceClose) {
        PathSegment last = null;
        try (var it = p.iterator(forceClose)) {
            while (it.hasNext())
                last = it.next();
        }
        return last;
    }

    public static native String _nFindIterMismatch(long pathPtr, boolean forceClose);
}