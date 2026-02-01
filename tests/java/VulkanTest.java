package io.github.humbleui.skija.test;

import static io.github.humbleui.skija.test.runner.TestRunner.*;

import io.github.humbleui.skija.*;
import io.github.humbleui.skija.test.runner.*;

public class VulkanTest implements Executable {
    @Override
    public void execute() throws Exception {
        TestRunner.testMethod(this, "testVulkanContext");
    }

    public void testVulkanContext() {
        assertThrows(RuntimeException.class, () -> {
            DirectContext.makeVulkan(0, 0, 0, 0, 0, 0, 0, 0);
        });
    }
}