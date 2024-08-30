package io.github.humbleui.skija.test.svg;

import io.github.humbleui.skija.svg.*;
import io.github.humbleui.skija.test.runner.Executable;

import static io.github.humbleui.skija.test.runner.TestRunner.assertEquals;
import static io.github.humbleui.skija.test.runner.TestRunner.runner;

public class SVGNodePropsTest implements Executable {
    @Override
    public void execute() throws Exception {
        SVGNode node = new SVGSVG(SVGSVGType.ROOT);

        // Clip Rule

        node.setClipRule(SVGFillRule.EVEN_ODD);
        assertEquals(SVGFillRule.EVEN_ODD, node.getClipRule().get());

        node.setClipRule(null);
        if (node.getClipRule().isPresent()) {
            runner.fail("clip rule should not have value");
        }

        // Color

        node.setColor(0xFFFFFF);
        assertEquals(0xFFFFFF, node.getColor().getAsInt());

        node.setColor(null);
        if (node.getColor().isPresent()) {
            runner.fail("color should not have value");
        }

        // Color Interpolation

        node.setColorInterpolation(SVGColorSpace.SRGB);
        assertEquals(SVGColorSpace.SRGB, node.getColorInterpolation().get());

        node.setColorInterpolation(null);
        if (node.getColorInterpolation().isPresent()) {
            runner.fail("color interpolation should not have value");
        }

        // Color Interpolation Filters

        node.setColorInterpolationFilters(SVGColorSpace.SRGB);
        assertEquals(SVGColorSpace.SRGB, node.getColorInterpolationFilters().get());

        node.setColorInterpolationFilters(null);
        if (node.getColorInterpolationFilters().isPresent()) {
            runner.fail("color interpolation filters should not have value");
        }

        // Fill Rule

        node.setFillRule(SVGFillRule.EVEN_ODD);
        assertEquals(SVGFillRule.EVEN_ODD, node.getFillRule().get());

        node.setFillRule(null);
        if (node.getFillRule().isPresent()) {
            runner.fail("fill rule should not have value");
        }

        // Stroke Width

        node.setStrokeWidth(new SVGLength(1f));
        assertEquals(1f, node.getStrokeWidth().get().getValue());

        node.setStrokeWidth(null);
        if (node.getStrokeWidth().isPresent()) {
            runner.fail("stroke width should not have value");
        }
    }
}
