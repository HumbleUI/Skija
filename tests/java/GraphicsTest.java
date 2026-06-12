package io.github.humbleui.skija.test;

import static io.github.humbleui.skija.test.runner.TestRunner.*;

import io.github.humbleui.skija.Graphics;
import io.github.humbleui.skija.test.runner.*;

public class GraphicsTest implements Executable {
    @Override
    public void execute() throws Exception {
        assertDoesNotThrow(Graphics::init);
        assertDoesNotThrow(Graphics::init); // calling twice to verify idempotency

        assertDoesNotThrow(Graphics::purgeFontCache);
        assertDoesNotThrow(Graphics::purgeResourceCache);
        assertDoesNotThrow(Graphics::purgeAllCaches);
    }
}
