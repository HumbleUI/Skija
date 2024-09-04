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

        // Fill Opacity

        node.setFillOpacity(1f);
        assertEquals(1f, node.getFillOpacity());

        node.setFillOpacity(null);
        if (node.getFillOpacity() != null) {
            runner.fail("fill opacity should not have value");
        }

        // Font Family

        node.setFontFamily(new SVGFontFamily("test"));
        assertEquals(new SVGFontFamily("test"), node.getFontFamily());
        node.setFontFamily(new SVGFontFamily());
        assertEquals(new SVGFontFamily(), node.getFontFamily());

        node.setFontFamily(null);
        if (node.getFontFamily() != null) {
            runner.fail("font family should not have value");
        }

        // Font Size

        node.setFontSize(new SVGFontSize(new SVGLength(1f)));
        assertEquals(new SVGFontSize(new SVGLength(1f)), node.getFontSize());
        node.setFontSize(new SVGFontSize());
        assertEquals(new SVGFontSize(), node.getFontSize());

        node.setFontSize(null);
        if (node.getFontSize() != null) {
            runner.fail("font size should not have value");
        }

        // Fill Style

        node.setFontStyle(SVGFontStyle.ITALIC);
        assertEquals(SVGFontStyle.ITALIC, node.getFontStyle());

        node.setFontStyle(null);
        if (node.getFontStyle() != null) {
            runner.fail("fill style should not have value");
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
