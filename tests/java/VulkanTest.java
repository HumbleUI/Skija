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
        try {
            DirectContext context = DirectContext.makeVulkan(0, 0, 0, 0, 0, 0, 0);
        } catch (Exception e) {
        } catch (UnsatisfiedLinkError e) {
            throw new RuntimeException("Vulkan bindings not found!", e);
        }
    }
}