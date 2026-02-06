package io.github.humbleui.skija.test;

import io.github.humbleui.skija.impl.*;
import io.github.humbleui.skija.test.paragraph.*;
import io.github.humbleui.skija.test.svg.*;
import io.github.humbleui.skija.test.runner.TestRunner;

public class TestSuite {
    static { Library.staticLoad(); }

    public static void main(String[] args) {
        TestRunner.startTesting();
        TestRunner.testClass(BitmapTest.class);
        TestRunner.testClass(PixmapTest.class);
        TestRunner.testClass(ColorTest.class);
        TestRunner.testClass(ColorSpaceTest.class);
        TestRunner.testClass(DataTest.class);
        TestRunner.testClass(EnumTest.class);
        TestRunner.testClass(FontMgrTest.class);
        TestRunner.testClass(ImageTest.class);
        TestRunner.testClass(PathTest.class);
        TestRunner.testClass(SurfaceTest.class);
        TestRunner.testClass(DocumentTest.class);
        TestRunner.testClass(PaintTest.class);
        TestRunner.testClass(PathMeasureTest.class);
        TestRunner.testClass(TextLineTest.class);
        TestRunner.testClass(TypefaceTest.class);

        TestRunner.testClass(FontCollectionTest.class);
        TestRunner.testClass(TextStyleTest.class);

        TestRunner.testClass(SVGCanvasTest.class);
        TestRunner.testClass(StreamAssetTest.class);
        TestRunner.testClass(RuntimeEffectBuilderTest.class);
        if (OperatingSystem.CURRENT != OperatingSystem.MACOS)
            TestRunner.testClass(VulkanTest.class);

        // TestRunner.testClass(TestTest.class);
        int res = TestRunner.finishTesting();
        System.exit(res > 0 ? 1 : 0);
    }
}