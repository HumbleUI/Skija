package io.github.humbleui.skija.test;

import static io.github.humbleui.skija.test.runner.TestRunner.assertEquals;
import static io.github.humbleui.skija.test.runner.TestRunner.assertNotNull;
import static io.github.humbleui.skija.test.runner.TestRunner.assertNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import io.github.humbleui.skija.ColorSpace;
import io.github.humbleui.skija.test.runner.Executable;

public class ColorSpaceTest implements Executable {
    @Override
    public void execute() throws Exception {
        testICC();
    }

    private void testICC() throws IOException {
        ColorSpace cs1 = ColorSpace.makeFromICCProfile(new byte[0]);
        assertNull(cs1);

        ColorSpace cs2 = ColorSpace.makeFromICCProfile(new byte[] { 1, 2, 3, 4, 5 });
        assertNull(cs2);

        for (String path: new String[] { "color_spaces/sRGB Profile.icc", "color_spaces/Display P3.icc" }) {
            byte[] data = Files.readAllBytes(Path.of(path));
            try (ColorSpace cs = ColorSpace.makeFromICCProfile(data);) {
                assertNotNull(cs);
                assertEquals(true, cs.isGammaCloseToSRGB());
            }
        }
    }
}
