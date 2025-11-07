package io.github.humbleui.skija.test;

import io.github.humbleui.skija.*;
import io.github.humbleui.skija.test.runner.*;

public class RuntimeEffectBuilderTest implements Executable {
    @Override
    public void execute() throws Exception {
        String shaderSksl =
            "vec4 main(vec2 coords) {" +
            "  return vec4(coords.x / 100.0, coords.y / 100.0, 0.5, 1.0);" +
            "}";

        try (RuntimeEffect effect = RuntimeEffect.makeForShader(shaderSksl)) {
            TestRunner.assertNotNull(effect);

            try (RuntimeEffectBuilder builder = new RuntimeEffectBuilder(effect)) {
                TestRunner.assertNotNull(builder);
                try (Shader shader = builder.makeShader()) {
                    TestRunner.assertNotNull(shader);
                }
                try (Shader shader = builder.makeShader(Matrix33.IDENTITY)) {
                    TestRunner.assertNotNull(shader);
                }
                TestRunner.assertNotNull(builder.getEffect());
                TestRunner.assertNotNull(builder.getUniforms());
            }

            try (Data uniformData = Data.makeEmpty();
                 RuntimeEffectBuilder builder = new RuntimeEffectBuilder(effect, uniformData)) {
                TestRunner.assertNotNull(builder);

                try (Shader shader = builder.makeShader()) {
                    TestRunner.assertNotNull(shader);
                }
            }

            try (RuntimeEffectBuilder builder1 = new RuntimeEffectBuilder(effect);
                 RuntimeEffectBuilder builder2 = new RuntimeEffectBuilder(builder1)) {
                TestRunner.assertNotNull(builder2);

                try (Shader shader = builder2.makeShader()) {
                    TestRunner.assertNotNull(shader);
                }
            }
        }

        String colorFilterSksl =
            "vec4 main(vec4 color) {" +
            "  return vec4(1.0 - color.r, 1.0 - color.g, 1.0 - color.b, color.a);" +
            "}";

        try (RuntimeEffect effect = RuntimeEffect.makeForColorFilter(colorFilterSksl);
             RuntimeEffectBuilder builder = new RuntimeEffectBuilder(effect)) {
            TestRunner.assertNotNull(builder);

            try (ColorFilter colorFilter = builder.makeColorFilter()) {
                TestRunner.assertNotNull(colorFilter);
            }
        }

        String blenderSksl =
            "vec4 main(vec4 src, vec4 dst) {" +
            "  return src * dst;" +
            "}";

        try (RuntimeEffect effect = RuntimeEffect.makeForBlender(blenderSksl);
             RuntimeEffectBuilder builder = new RuntimeEffectBuilder(effect)){
            TestRunner.assertNotNull(builder);

            try (Blender blender = builder.makeBlender()) {
                TestRunner.assertNotNull(blender);
            }
        }

        // Test setUniform methods with a shader that uses uniforms
        String uniformShaderSksl =
            "uniform float scale;" +
            "uniform float2 offset;" +
            "uniform float3 color;" +
            "uniform int mode;" +
            "vec4 main(vec2 coords) {" +
            "  vec2 scaledCoords = (coords + offset) * scale;" +
            "  return vec4(color, 1.0) * float(mode);" +
            "}";

        try (RuntimeEffect effect = RuntimeEffect.makeForShader(uniformShaderSksl);
             RuntimeEffectBuilder builder = new RuntimeEffectBuilder(effect)) {
            TestRunner.assertNotNull(builder);

            // Test setting uniforms
            builder.setUniform("scale", 2.0f);
            builder.setUniform("offset", 10.0f, 20.0f);
            builder.setUniform("color", 1.0f, 0.5f, 0.25f);
            builder.setUniform("mode", 1);

            try (Shader shader = builder.makeShader()) {
                TestRunner.assertNotNull(shader);
            }

            // Test setting uniforms with arrays
            builder.setUniform("color", new float[] { 0.8f, 0.6f, 0.4f });

            try (Shader shader = builder.makeShader()) {
                TestRunner.assertNotNull(shader);
            }
        }

        // Test setUniform with Matrix33
        String matrixShaderSksl =
            "uniform float3x3 transform;" +
            "vec4 main(vec2 coords) {" +
            "  vec3 transformed = transform * vec3(coords, 1.0);" +
            "  return vec4(transformed.xy / 100.0, 0.5, 1.0);" +
            "}";

        try (RuntimeEffect effect = RuntimeEffect.makeForShader(matrixShaderSksl);
             RuntimeEffectBuilder builder = new RuntimeEffectBuilder(effect)) {
            TestRunner.assertNotNull(builder);

            builder.setUniform("transform", Matrix33.makeScale(2.0f, 2.0f));

            try (Shader shader = builder.makeShader()) {
                TestRunner.assertNotNull(shader);
            }
        }

        // Test setChild with a shader that uses child effects
        String childShaderSksl =
            "uniform shader child;" +
            "vec4 main(vec2 coords) {" +
            "  return child.eval(coords);" +
            "}";

        try (RuntimeEffect effect = RuntimeEffect.makeForShader(childShaderSksl);
             RuntimeEffectBuilder builder = new RuntimeEffectBuilder(effect);
             Shader childShader = Shader.makeColor(0xFF0000FF)) {
            TestRunner.assertNotNull(builder);

            builder.setChild("child", childShader);

            try (Shader shader = builder.makeShader()) {
                TestRunner.assertNotNull(shader);
            }
        }

        // Test setChild with a color filter that uses child effects
        String childColorFilterSksl =
            "uniform colorFilter child;" +
            "vec4 main(vec4 color) {" +
            "  return child.eval(color);" +
            "}";

        try (RuntimeEffect effect = RuntimeEffect.makeForColorFilter(childColorFilterSksl);
             RuntimeEffectBuilder builder = new RuntimeEffectBuilder(effect);
             ColorFilter childFilter = ColorFilter.makeBlend(0xFF00FF00, BlendMode.MULTIPLY)) {
            TestRunner.assertNotNull(builder);

            builder.setChild("child", childFilter);

            try (ColorFilter colorFilter = builder.makeColorFilter()) {
                TestRunner.assertNotNull(colorFilter);
            }
        }
    }
}
