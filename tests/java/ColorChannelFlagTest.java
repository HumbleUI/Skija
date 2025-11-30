package io.github.humbleui.skija.test;

import static io.github.humbleui.skija.test.runner.TestRunner.*;

import java.util.*;

import io.github.humbleui.skija.ColorChannelFlag;
import io.github.humbleui.skija.test.runner.*;

public class ColorChannelFlagTest implements Executable {
    @Override
    public void execute() throws Exception {
        int[] ccValues = ColorChannelFlag._nGetValues();
        int[] javaValues = {
            ColorChannelFlag.RED.getValue(),
            ColorChannelFlag.GREEN.getValue(),
            ColorChannelFlag.BLUE.getValue(),
            ColorChannelFlag.ALPHA.getValue(),
            ColorChannelFlag.GRAY.getValue(),

            // Convenience values
            ColorChannelFlag.GRAY_ALPHA.getValue(),
            ColorChannelFlag.RG.getValue(),
            ColorChannelFlag.RGB.getValue(),
            ColorChannelFlag.RGBA.getValue()
        };
        TestRunner.assertArrayEquals(ccValues, javaValues);
    }
}
