package io.github.humbleui.skija.test;

import static io.github.humbleui.skija.test.runner.TestRunner.assertEquals;
import static io.github.humbleui.skija.test.runner.TestRunner.assertNotEquals;
import static io.github.humbleui.skija.test.runner.TestRunner.assertNotNull;
import static io.github.humbleui.skija.test.runner.TestRunner.assertNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import io.github.humbleui.skija.ColorSpace;
import io.github.humbleui.skija.ColorSpaceNamedPrimaries;
import io.github.humbleui.skija.ColorSpaceNamedTransferFn;
import io.github.humbleui.skija.Data;
import io.github.humbleui.skija.Matrix33;
import io.github.humbleui.skija.TransferFunction;
import io.github.humbleui.skija.test.runner.Executable;

public class ColorSpaceTest implements Executable {
    @Override
    public void execute() throws Exception {
        testICC();
        testExtendedApi();
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

    private void testExtendedApi() throws IOException {
        ColorSpace srgb = ColorSpace.getSRGB();
        assertEquals(true, srgb.isSRGB());
        assertNotNull(srgb.getTransferFn());
        assertNotNull(srgb.getInvTransferFn());
        assertNotNull(srgb.getNumericalTransferFn());

        Matrix33 xyz = srgb.getToXYZD50();
        assertNotNull(xyz);
        assertNotNull(srgb.getGamutTransformTo(ColorSpace.getDisplayP3()));
        assertNotEquals(0, srgb.getToXYZD50Hash());
        assertNotEquals(0, srgb.getTransferFnHash());
        assertNotEquals(0L, srgb.getHash());

        try (ColorSpace linear = srgb.makeLinearGamma();
             ColorSpace srgbGamma = linear.makeSRGBGamma();
             ColorSpace spun = srgb.makeColorSpin();) {
            assertNotNull(linear);
            assertEquals(true, linear.isGammaLinear());
            assertNotNull(srgbGamma);
            assertNotNull(spun);
        }

        TransferFunction transferFn = srgb.getTransferFn();
        assertNotNull(transferFn);
        assertNotNull(xyz);
        try (ColorSpace fromRGB = ColorSpace.makeRGB(transferFn, xyz)) {
            assertNotNull(fromRGB);
            assertEquals(true, fromRGB.isSRGB());
        }

        try (ColorSpace cicp = ColorSpace.makeCICP(ColorSpaceNamedPrimaries.REC709, ColorSpaceNamedTransferFn.SRGB)) {
            assertNotNull(cicp);
        }

        try (Data serialized = srgb.serializeToData();
             ColorSpace deserialized = ColorSpace.makeFromData(serialized)) {
            assertNotNull(serialized);
            assertNotNull(deserialized);
            assertEquals(true, srgb.equals(deserialized));
        }
    }
}
