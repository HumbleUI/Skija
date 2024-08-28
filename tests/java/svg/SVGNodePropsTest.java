package io.github.humbleui.skija.test.svg;

import io.github.humbleui.skija.svg.*;
import io.github.humbleui.skija.test.runner.Executable;

import static io.github.humbleui.skija.test.runner.TestRunner.assertEquals;
import static io.github.humbleui.skija.test.runner.TestRunner.runner;

public class SVGNodePropsTest implements Executable {
    @Override
    public void execute() throws Exception {
        SVGNode node = new SVGSVG(SVGSVGType.ROOT);

        node.setStrokeWidth(new SVGLength(1f));
        assertEquals(1f, node.getStrokeWidth().get().getValue());

        node.setStrokeWidth(SVGProperty.unspecified());
        if (node.getStrokeWidth().isPresent()) {
            runner.fail("StrokeWidth should not have value");
        }

        node.setStrokeWidth(SVGProperty.inherit());
        if (node.getStrokeWidth().isPresent()) {
            runner.fail("StrokeWidth should not have value");
        }
    }
}
