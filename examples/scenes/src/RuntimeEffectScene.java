package io.github.humbleui.skija.examples.scenes;

import java.nio.*;
import java.nio.file.*;
import java.nio.file.Path;
import java.io.*;
import io.github.humbleui.skija.*;
import io.github.humbleui.types.*;

public class RuntimeEffectScene extends Scene {
    public final Image _texture;
    public final Image _builderImage;
    public final RuntimeEffect _effectForColorFilter;
    public final RuntimeEffect _effectForShader;
    public final RuntimeEffect _effectForBlender;
    public final RuntimeEffect _effectForBuilder;

    public RuntimeEffectScene() {
        try {
            _texture = Image.makeDeferredFromEncodedBytes(Files.readAllBytes(Path.of(file("images/IMG_7098.jpeg"))));
            _builderImage = Image.makeDeferredFromEncodedBytes(Files.readAllBytes(Path.of(file("images/skia_fiddle/4.png"))));
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

        _effectForBuilder = RuntimeEffect.makeForShader(
            "uniform shader image;\n" +
            "uniform float  sharp;  // 1/m    0 --> NN, 1 --> Linear\n" +
            "uniform int    do_smooth;   // bool\n" +
            "\n" +
            "float2 smooth(float2 t) {\n" +
            "    return t * t * (3.0 - 2.0 * t);\n" +
            "}\n" +
            "\n" +
            "float2 sharpen(float2 w) {\n" +
            "    return saturate(sharp * (w - 0.5) + 0.5);\n" +
            "}\n" +
            "\n" +
            "half4 main(float2 p) {\n" +
            "    half4 pa = image.eval(float2(p.x-0.5, p.y-0.5));\n" +
            "    half4 pb = image.eval(float2(p.x+0.5, p.y-0.5));\n" +
            "    half4 pc = image.eval(float2(p.x-0.5, p.y+0.5));\n" +
            "    half4 pd = image.eval(float2(p.x+0.5, p.y+0.5));\n" +
            "    float2 w = sharpen(fract(p + 0.5));\n" +
            "    if (do_smooth > 0) {\n" +
            "        w = smooth(w);\n" +
            "    }\n" +
            "    return mix(mix(pa, pb, w.x), mix(pc, pd, w.x), w.y);\n" +
            "}"
        );
    }

    @Override
    public void draw(Canvas canvas, int width, int height, float dpi, int xpos, int ypos) {
        float dist = (float) Math.min(1f, Math.sqrt(xpos * xpos + ypos * ypos) / Math.sqrt(Math.min(width, height) * Math.min(width, height) * 2f));

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

        canvas.restore();
        canvas.save();
        canvas.translate(20, 260);

        // RuntimeEffectBuilder
        try (
            var imageShader = _builderImage.makeShader();
            var builder = new RuntimeEffectBuilder(_effectForBuilder);
         ) {
            builder.setChild("image", imageShader);
            builder.setUniform("sharp", 1.0f / Math.max(0.00001f, dist));
            builder.setUniform("do_smooth", 0);

            try (var shader = builder.makeShader();
                 var paint = new Paint().setShader(shader)) {
                canvas.save();
                canvas.scale(200f/64f, 200f/64f);
                canvas.drawRect(Rect.makeXYWH(0, 0, 64, 64), paint);
                canvas.restore();
                canvas.drawString("RuntimeEffectBuilder (no smooth)", 0, 220, inter13, blackFill);
                canvas.translate(220, 0);
            }

            builder.setUniform("do_smooth", 1);

            try (var shader = builder.makeShader();
                 var paint = new Paint().setShader(shader)) {
                canvas.save();
                canvas.scale(200f/64f, 200f/64f);
                canvas.drawRect(Rect.makeXYWH(0, 0, 64, 64), paint);
                canvas.restore();
                canvas.drawString("RuntimeEffectBuilder (smooth)", 0, 220, inter13, blackFill);
                canvas.translate(220, 0);
            }
        }

        canvas.restore();
    }
}
