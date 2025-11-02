package io.github.humbleui.skija.examples.scenes;

import io.github.humbleui.skija.*;
import io.github.humbleui.types.*;
import java.util.*;

public class ShadowUtilsScene extends Scene {
    @Override
    public void draw(Canvas canvas, int width, int height, float dpi, int xpos, int ypos) {
        try (PathBuilder builder = new PathBuilder().moveTo(100, 8)
                                                    .lineTo(158, 189)
                                                    .lineTo(4, 77)
                                                    .lineTo(195, 77)
                                                    .lineTo(41, 189)
                                                    .closePath();
             Path path    = builder.snapshot();
             Path path2   = builder.snapshot(Matrix33.makeTranslate(200, 0));
             Path path3   = builder.snapshot(Matrix33.makeTranslate(400, 0));
             Path path4   = builder.snapshot(Matrix33.makeTranslate(600, 0));
             Paint stroke = new Paint().setColor(0xFFFFFFFF);)
        {
            canvas.translate(xpos - 400, ypos - 100);

            var zPlaneParams = new Point3(0, 0, 100);
            var lightPos = new Point3(width / 2 * dpi, 0, 5000);
            var lightRadius = 1000;
            var ambientColor = 0x80ff0000;
            var spotColor = 0x800000ff;

            ShadowUtils.drawShadow(canvas, path, zPlaneParams, lightPos, lightRadius, ambientColor, spotColor, false, false);
            canvas.drawPath(path, stroke);

            ShadowUtils.drawShadow(canvas, path2, zPlaneParams, lightPos, lightRadius, ambientColor, spotColor, true, false);
            canvas.drawPath(path2, stroke);

            ShadowUtils.drawShadow(canvas, path3, zPlaneParams, lightPos, lightRadius, ambientColor, spotColor, false, true);
            canvas.drawPath(path3, stroke);

            ShadowUtils.drawShadow(canvas, path4, zPlaneParams, lightPos, lightRadius, ambientColor, spotColor, true, true);
            canvas.drawPath(path4, stroke);
        }

        assert 0x80000000 == ShadowUtils.computeTonalAmbientColor(0x80ff0000, 0x800000ff) : ShadowUtils.computeTonalAmbientColor(0x80ff0000, 0x800000ff);
        assert 0xC900007D == ShadowUtils.computeTonalSpotColor(0x80ff0000, 0x800000ff) : ShadowUtils.computeTonalSpotColor(0x80ff0000, 0x800000ff);
    }
}