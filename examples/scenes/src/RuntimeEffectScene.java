package io.github.humbleui.skija.examples.scenes;

import java.nio.*;
import java.nio.file.*;
import java.nio.file.Path;
import java.io.*;
import io.github.humbleui.skija.*;
import io.github.humbleui.types.*;

public class RuntimeEffectScene extends Scene {
    public final Image _texture;
    public final RuntimeEffect _effectForShader;
    public final RuntimeEffect _effectForColorFilter;

    public RuntimeEffectScene() {
        try {
            _texture = Image.makeDeferredFromEncodedBytes(Files.readAllBytes(Path.of(file("images/triangle.png"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        _effectForShader = RuntimeEffect.makeForShader(
            "uniform float xScale;\n" +
            "uniform float xBias;\n" +
            "uniform float yScale;\n" +
            "uniform float yBias;\n" +
            "uniform shader image;\n" +
            "half4 main(float2 xy) {\n" +
            "  half4 tex = image.eval(mod(xy, 100));\n" +
            "  return half4((xy.x - xBias) /  xScale / 2 + 0.5, (xy.y - yBias) / yScale / 2 + 0.5, tex.b, 1);\n" +
            "}"
        );
        
        _effectForColorFilter = RuntimeEffect.makeForColorFilter(
            "uniform float xScale;\n" +
            "uniform float xBias;\n" +
            "uniform float yScale;\n" +
            "uniform float yBias;\n" +
            "half4 main(half4 p) {\n" +
            "  return half4(xBias / xScale * p.r, yBias / yScale * p.g, p.b, p.a);\n" +
            "}"
        );
    }

    @Override
    public void draw(Canvas canvas, int width, int height, float dpi, int xpos, int ypos) {
        var bb = ByteBuffer.allocate(4 * 4).order(ByteOrder.nativeOrder());
        bb.putFloat((float) width);
        bb.putFloat((float) xpos);
        bb.putFloat((float) height);
        bb.putFloat((float) ypos);
        
        canvas.save();

        try (var data = Data.makeFromBytes(bb.array())) {
            canvas.translate(20, 20);

            try (var child = _texture.makeShader();
                 var shader = _effectForShader.makeShader(data, new Shader[] { child }, null);
                 var paint = new Paint().setShader(shader);)
            {
                canvas.drawRect(Rect.makeXYWH(0, 0, 200, 200), paint);
                canvas.drawString("makeForShader", 0, 220, inter13, blackFill);
                canvas.translate(220, 0);
            }

            try (var filter = _effectForColorFilter.makeColorFilter(data, null);
                 var paint = new Paint().setColorFilter(filter);)
            {
                canvas.drawImageRect(_texture, Rect.makeXYWH(0, 0, 200, 200), paint);
                canvas.drawString("makeForColorFilter", 0, 220, inter13, blackFill);
            }
        }
    }
}
