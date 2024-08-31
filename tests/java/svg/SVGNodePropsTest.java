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
        assertEquals(SVGFillRule.EVEN_ODD, node.getClipRule());

        node.setClipRule(null);
        if (node.getClipRule() != null) {
            runner.fail("clip rule should not have value");
        }

        // Color

        node.setColor(0xFFFFFF);
        assertEquals(0xFFFFFF, node.getColor());

        node.setColor(null);
        if (node.getColor() != null) {
            runner.fail("color should not have value");
        }

        // Color Interpolation

        node.setColorInterpolation(SVGColorSpace.SRGB);
        assertEquals(SVGColorSpace.SRGB, node.getColorInterpolation());

        node.setColorInterpolation(null);
        if (node.getColorInterpolation() != null) {
            runner.fail("color interpolation should not have value");
        }

        // Color Interpolation Filters

        node.setColorInterpolationFilters(SVGColorSpace.SRGB);
        assertEquals(SVGColorSpace.SRGB, node.getColorInterpolationFilters());

        node.setColorInterpolationFilters(null);
        if (node.getColorInterpolationFilters() != null) {
            runner.fail("color interpolation filters should not have value");
        }

        // Fill Rule

        node.setFillRule(SVGFillRule.EVEN_ODD);
        assertEquals(SVGFillRule.EVEN_ODD, node.getFillRule());

        node.setFillRule(null);
        if (node.getFillRule() != null) {
            runner.fail("fill rule should not have value");
        }

        // Fill

        node.setFill(new SVGPaint(0xFFFFFF));
        assertEquals(new SVGPaint(0xFFFFFF), node.getFill());
        node.setFill(new SVGPaint(new SVGColor()));
        assertEquals(new SVGPaint(new SVGColor()), node.getFill());
        node.setFill(new SVGPaint(new SVGColor(0xFFFFFF, new String[]{"test"})));
        assertEquals(new SVGPaint(new SVGColor(0xFFFFFF, new String[]{"test"})), node.getFill());
        node.setFill(new SVGPaint(new SVGIRI(), 0xFFFFFF));
        assertEquals(new SVGPaint(new SVGIRI(), 0xFFFFFF), node.getFill());
        node.setFill(new SVGPaint(new SVGIRI(SVGIRIType.DATA_URI, "test"), 0xFFFFFF));
        assertEquals(new SVGPaint(new SVGIRI(SVGIRIType.DATA_URI, "test"), 0xFFFFFF), node.getFill());

        node.setFill(null);
        if (node.getFill() != null) {
            runner.fail("fill should not have value");
        }

        // Stroke Width

        node.setStrokeWidth(new SVGLength(1f));
        assertEquals(1f, node.getStrokeWidth().getValue());

        node.setStrokeWidth(null);
        if (node.getStrokeWidth() != null) {
            runner.fail("stroke width should not have value");
        }
    }
}
