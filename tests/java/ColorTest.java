package io.github.humbleui.skija.test;

import static io.github.humbleui.skija.test.runner.TestRunner.*;

import java.util.*;

import io.github.humbleui.skija.Color;
import io.github.humbleui.skija.Color4f;
import io.github.humbleui.skija.test.runner.*;

public class ColorTest implements Executable {
    @Override
    public void execute() throws Exception {
        Map<Integer, Color4f> cases = new HashMap<>();
        cases.put(0x00000000, new Color4f(0, 0, 0, 0));
        cases.put(0xFF000000, new Color4f(0, 0, 0, 1));
        cases.put(0x00FF0000, new Color4f(1, 0, 0, 0));
        cases.put(0x0000FF00, new Color4f(0, 1, 0, 0));
        cases.put(0x000000FF, new Color4f(0, 0, 1, 0));
        cases.put(0x80808080, new Color4f(128/255f, 128/255f, 128/255f, 128/255f));

        for (var entry: cases.entrySet()) {
            int color = entry.getKey();
            Color4f color4f = entry.getValue();
            pushStack(Integer.toString(color, 16) + " <-> " + color4f);
            assertEquals(color, color4f.toColor());
            assertEquals(new Color4f(color), color4f);
            popStack();
        }

        assertEquals(0x12, Color.getA(0x12345678));
        assertEquals(0x34, Color.getR(0x12345678));
        assertEquals(0x56, Color.getG(0x12345678));
        assertEquals(0x78, Color.getB(0x12345678));
        assertEquals(0xFE345678, Color.withA(0x12345678, 0xFE));
        assertEquals(0x12FE5678, Color.withR(0x12345678, 0xFE));
        assertEquals(0x1234FE78, Color.withG(0x12345678, 0xFE));
        assertEquals(0x123456FE, Color.withB(0x12345678, 0xFE));

        assertEquals(0xFF000000, Color.makeLerp(0xFF000000, 0xFFFFFFFF, 0));
        assertEquals(0xFFFFFFFF, Color.makeLerp(0xFF000000, 0xFFFFFFFF, 1));
        assertEquals(0xFF808080, Color.makeLerp(0xFF000000, 0xFFFFFFFF, 0.5f));

        assertEquals(0x80000000, Color.makeLerp(0xFF000000, 0x00000000, 0.5f));
        assertEquals(0x800000FF, Color.makeLerp(0xFF0000FF, 0x00FF0000, 0.5f));

        assertEquals(new Color4f(1, 1, 1, 1), new Color4f(0, 0, 0, 1).makeLerp(new Color4f(1, 1, 1, 1), 1));
        assertEquals(new Color4f(0, 0, 1, 0.5f), new Color4f(0, 0, 1, 1).makeLerp(new Color4f(1, 0, 0, 0), 0.5f));

        assertEquals(0x80000080, Color.premultiply(0x800000FF));
        assertEquals(0x800000FF, Color.unpremultiply(0x80000080));
        assertEquals(new Color4f(0.5f, 0, 0, 0.5f), new Color4f(1, 0, 0, 0.5f).premultiply());
        assertEquals(new Color4f(1, 0, 0, 0.5f), new Color4f(0.5f, 0, 0, 0.5f).unpremultiply());

        assertEquals(0x00, Color.ALPHA_TRANSPARENT);
        assertEquals(0xFF, Color.ALPHA_OPAQUE);
        assertEquals(0x00000000, Color.TRANSPARENT);
        assertEquals(0xFF000000, Color.BLACK);
        assertEquals(0xFF444444, Color.DARK_GRAY);
        assertEquals(0xFF888888, Color.GRAY);
        assertEquals(0xFFCCCCCC, Color.LIGHT_GRAY);
        assertEquals(0xFFFFFFFF, Color.WHITE);
        assertEquals(0xFFFF0000, Color.RED);
        assertEquals(0xFF00FF00, Color.GREEN);
        assertEquals(0xFF0000FF, Color.BLUE);
        assertEquals(0xFFFFFF00, Color.YELLOW);
        assertEquals(0xFF00FFFF, Color.CYAN);
        assertEquals(0xFFFF00FF, Color.MAGENTA);

        float[] hsvRed = Color.convertToHSV(0xFFFF0000);
        assertClose(0f, hsvRed[0], 0.001f);
        assertClose(1f, hsvRed[1], 0.001f);
        assertClose(1f, hsvRed[2], 0.001f);

        float[] hsvGreen = Color.convertRGBToHSV(0, 255, 0);
        assertClose(120f, hsvGreen[0], 0.001f);
        assertClose(1f, hsvGreen[1], 0.001f);
        assertClose(1f, hsvGreen[2], 0.001f);

        assertEquals(0xFFFF0000, Color.makeFromHSV(new float[] {0f, 1f, 1f}));
        assertEquals(0x8000FF00, Color.makeFromHSV(0x80, new float[] {120f, 1f, 1f}));
    }
}
