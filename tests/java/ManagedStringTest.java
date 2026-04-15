package io.github.humbleui.skija.test;

import io.github.humbleui.skija.*;
import io.github.humbleui.skija.test.runner.*;

import static io.github.humbleui.skija.test.runner.TestRunner.*;

public class ManagedStringTest implements Executable {
    @Override
    public void execute() throws Exception {
        TestRunner.testMethod(this, "utfConversions");
    }

    public void utfConversions() throws Exception {
        String[] valid = {
            "abc",
            "абв",
            "\ud835\udc00",  // valid surrogate pair (U+1D400)
        };
        for (String s : valid) {
            try (var ms = new ManagedString(s)) {
                assertEquals(s, ms.toString());
            }
        }

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
            assertThrows(IllegalArgumentException.class, () -> {
                new ManagedString(s).close();
            });
        }
    }
}
