package io.github.humbleui.skija.test.svg;

import io.github.humbleui.skija.svg.*;
import io.github.humbleui.skija.test.runner.Executable;

import static io.github.humbleui.skija.test.runner.TestRunner.assertEquals;
import static io.github.humbleui.skija.test.runner.TestRunner.runner;

public class SVGNodePropsTest implements Executable {
    @Override
    public void execute() throws Exception {
        SVGNode node = new SVGSVG(SVGSVGType.ROOT);

        node.setClipRule(SVGFillRuleType.EVEN_ODD);
        assertEquals(SVGFillRuleType.EVEN_ODD, node.getClipRule().get());

        node.setClipRule(null);
        if (node.getClipRule().isPresent()) {
            runner.fail("Clip rule should not have value");
        }

        node.setColor(0xFFFFFF);
        assertEquals(0xFFFFFF, node.getColor().getAsInt());

        node.setColor(null);
        if (node.getColor().isPresent()) {
            runner.fail("Color should not have value");
        }

        node.setClipRule(null);
        if (node.getClipRule().isPresent()) {
            runner.fail("Clip rule should not have value");
        }

        node.setStrokeWidth(new SVGLength(1f));
        assertEquals(1f, node.getStrokeWidth().get().getValue());

        node.setStrokeWidth(null);
        if (node.getStrokeWidth().isPresent()) {
            runner.fail("StrokeWidth should not have value");
        }
    }
}
