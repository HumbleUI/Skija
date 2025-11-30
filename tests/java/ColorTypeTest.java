package io.github.humbleui.skija.test;

import static io.github.humbleui.skija.test.runner.TestRunner.*;

import java.util.*;

import io.github.humbleui.skija.ColorAlphaType;
import io.github.humbleui.skija.ColorType;
import io.github.humbleui.skija.test.runner.*;

public class ColorTypeTest implements Executable {
    @Override
    public void execute() throws Exception {
        int[] ccValues = ColorType._nGetValues();
        int[] javaValues = {
            ColorType.UNKNOWN.ordinal(),
            ColorType.ALPHA_8.ordinal(),
            ColorType.RGB_565.ordinal(),
            ColorType.ARGB_4444.ordinal(),
            ColorType.RGBA_8888.ordinal(),
            ColorType.RGB_888X.ordinal(),
            ColorType.BGRA_8888.ordinal(),
            ColorType.RGBA_1010102.ordinal(),
            ColorType.BGRA_1010102.ordinal(),
            ColorType.RGB_101010X.ordinal(),
            ColorType.BGR_101010X.ordinal(),
            ColorType.BGR_101010X_XR.ordinal(),
            ColorType.BGRA_10101010_XR.ordinal(),
            ColorType.RGBA_10X6.ordinal(),
            ColorType.GRAY_8.ordinal(),
            ColorType.RGBA_F16NORM.ordinal(),
            ColorType.RGBA_F16.ordinal(),
            ColorType.RGB_F16F16F16X.ordinal(),
            ColorType.RGBA_F32.ordinal(),
            ColorType.R8G8_UNORM.ordinal(),
            ColorType.A16_FLOAT.ordinal(),
            ColorType.R16G16_FLOAT.ordinal(),
            ColorType.A16_UNORM.ordinal(),
            ColorType.R16G16_UNORM.ordinal(),
            ColorType.R16G16B16A16_UNORM.ordinal(),
            ColorType.SRGBA_8888.ordinal(),
            ColorType.R8_UNORM.ordinal(),

            // last value
            ColorType.R8_UNORM.ordinal(),

            // N32
            ColorType.N32.ordinal()
        };
        TestRunner.assertArrayEquals(ccValues, javaValues);

        for (ColorType type: ColorType.values()) {
            if (ColorType.UNKNOWN == type) {
                TestRunner.assertEquals(0, type.getBytesPerPixel());
                TestRunner.assertEquals(0, type.getShiftPerPixel());
                TestRunner.assertEquals(0, type.getChannelFlags());
                TestRunner.assertEquals(0, type.getNumChannels());
            } else {
                TestRunner.assertNotEquals(0, type.getBytesPerPixel());
                TestRunner.assertNotEquals(-1, type.getShiftPerPixel());
                TestRunner.assertNotEquals(0, type.getChannelFlags());
                TestRunner.assertNotEquals(0, type.getNumChannels());
            }

            for (ColorAlphaType alpha: ColorAlphaType.values()) {
                type.validateAlphaType(alpha);
            }
        }
    }
}
