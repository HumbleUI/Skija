package io.github.humbleui.skija.examples.scenes;

import java.nio.*;
import java.nio.file.*;
import java.nio.file.Path;
import java.io.*;
import io.github.humbleui.skija.*;
import io.github.humbleui.types.*;

public class RuntimeEffectScene extends Scene {
    public final Image _texture;
    public final RuntimeEffect _effectForColorFilter;
    public final RuntimeEffect _effectForShader;
    public final RuntimeEffect _effectForBlender;

    public RuntimeEffectScene() {
        try {
            _texture = Image.makeDeferredFromEncodedBytes(Files.readAllBytes(Path.of(file("images/IMG_7098.jpeg"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        _effectForColorFilter = RuntimeEffect.makeForColorFilter(
            "uniform float xScale;\n" +
            "uniform float xBias;\n" +
            "uniform float yScale;\n" +
            "uniform float yBias;\n" +
            "vec4 main(vec4 inColor) {\n" +
            "  return half4(xBias / xScale * inColor.r, yBias / yScale * inColor.g, inColor.b, inColor.a);\n" +
            "}"
        );

        _effectForShader = RuntimeEffect.makeForShader(
            "uniform float xScale;\n" +
            "uniform float xBias;\n" +
            "uniform float yScale;\n" +
            "uniform float yBias;\n" +
            "uniform shader image;\n" +
            "vec4 main(vec2 inCoords) {\n" +
            "  half4 tex = image.eval(mod(inCoords, 100));\n" +
            "  return half4((inCoords.x - xBias) /  xScale / 2 + 0.5, (inCoords.y - yBias) / yScale / 2 + 0.5, tex.b, 1);\n" +
            "}"
        );

        _effectForBlender = RuntimeEffect.makeForBlender(
            "uniform float a;\n" +
            "uniform float b;\n" +
            "uniform float c;\n" +
            "uniform float d;\n" +
            "half4 main(half4 src, half4 dst) {\n" +
            "  return ((src.rgb - 0.25) * (b / a + d / c) * 8).rgb1;\n" +
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

        try (
            var data  = Data.makeFromBytes(bb.array());
        ) {
            canvas.translate(20, 20);

            try (var filter = _effectForColorFilter.makeColorFilter(data);
                 var paint = new Paint().setColorFilter(filter);)
            {
                canvas.drawImageRect(_texture, Rect.makeXYWH(0, 0, 200, 200), paint);
                canvas.drawString("makeForColorFilter", 0, 220, inter13, blackFill);
                canvas.translate(220, 0);
            }

            try (var textureShader = _texture.makeShader();
                 var shader = _effectForShader.makeShader(data, new Shader[] { textureShader });
                 var paint = new Paint().setShader(shader);)
            {
                RuntimeEffectUniformInfo[] uniforms = _effectForShader.getUniforms();
                assert uniforms.length == 4 : uniforms.length;

                for (RuntimeEffectUniformInfo uniform: new RuntimeEffectUniformInfo[] {
                    uniforms[1],
                    _effectForShader.getUniform("xBias")
                }) {
                    assert uniform.getName().equals("xBias") : uniform.getName();
                    assert uniform.getOffset() == 4 : uniform.getOffset();
                    assert uniform.getType() == RuntimeEffectUniformType.FLOAT : uniform.getType();
                    assert uniform.getCount() == 1 : uniform.getCount();
                    assert uniform.getFlags() == 0 : uniform.getFlags();
                }

                RuntimeEffectChildInfo[] children = _effectForShader.getChildren();
                assert children.length == 1 : children.length;
                for (RuntimeEffectChildInfo child: new RuntimeEffectChildInfo[] {
                    children[0],
                    _effectForShader.getChild("image")
                }) {
                    assert child.getName().equals("image") : child.getName();
                    assert child.getType() == RuntimeEffectChildType.SHADER : child.getType();
                    assert child.getIndex() == 0 : child.getIndex();
                }

                canvas.drawRect(Rect.makeXYWH(0, 0, 200, 200), paint);
                canvas.drawString("makeForShader", 0, 220, inter13, blackFill);
                canvas.translate(220, 0);
            }

            try (var textureShader = _texture.makeShader();
                 var shader = _effectForShader.makeShader(data, new Shader[] { textureShader }, Matrix33.makeScale(0.5f));
                 var paint = new Paint().setShader(shader);)
            {
                canvas.drawRect(Rect.makeXYWH(0, 0, 200, 200), paint);
                canvas.drawString("makeForShader", 0, 220, inter13, blackFill);
                canvas.translate(220, 0);
            }

            try (
                var blender = _effectForBlender.makeBlender(data);
                var paint = new Paint().setBlender(blender);
            ) {
                canvas.drawImageRect(_texture, Rect.makeXYWH(0, 0, 200, 200), paint);
                canvas.drawString("makeForBlender", 0, 220, inter13, blackFill);
                canvas.translate(220, 0);
            }
        }
    }
}
