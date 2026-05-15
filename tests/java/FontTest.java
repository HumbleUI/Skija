package io.github.humbleui.skija.test;

import io.github.humbleui.skija.*;
import io.github.humbleui.types.Rect;
import io.github.humbleui.skija.test.runner.*;

import static io.github.humbleui.skija.test.runner.TestRunner.*;

public class FontTest implements Executable {
    @Override
    public void execute() throws Exception {
        try (Typeface jbMono = FontMgr.getDefault().makeFromFile("fonts/JetBrainsMono-Regular.ttf");
             Font font = new Font(jbMono, 12f); ) {

            float advance = font.measureTextWidth("a");

            // Empty strings
            assertEquals(0.0f, font.measureTextWidth(""));
            assertEquals(0, font.getStringGlyphs("").length);
            assertEquals(0, font.getStringGlyphsCount(""));
            assertEquals(Rect.makeLTRB(0, 0, 0, 0), font.measureText(""));
            
            // ASCII
            assertClose(3 * advance, font.measureTextWidth("abc"), 0.01f);

            // Cyrillic (multi-byte UTF-8, single UTF-16 code unit each)
            assertClose(3 * advance, font.measureTextWidth("абв"), 0.01f);

            // Valid surrogate pair (U+1D400)
            float wPair = font.measureTextWidth("\ud835\udc00");
            assertEquals(true, wPair >= 0 && Float.isFinite(wPair));

            // Malformed UTF-16 must throw IllegalArgumentException.
            String[] invalid = {
                "\udc00",         // lone low surrogate (first)
                "A\udc00B",       // lone low surrogate (middle)
                "\ud835",         // lone high surrogate (truncated)
                "hello\ud83d",    // lone high surrogate (at end)
                "\ud835X\ud835",  // high surrogate not followed by low
                "\ud83d\ud83d",   // high surrogate followed by high surrogate
                "\ud835\u0041",   // high surrogate followed by BMP char
            };
            for (String s : invalid) {
                assertThrows(IllegalArgumentException.class, () -> font.measureTextWidth(s));
                assertThrows(IllegalArgumentException.class, () -> font.measureText(s));
            }
        }
    }
}
