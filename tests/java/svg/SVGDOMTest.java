package io.github.humbleui.skija.test.svg;

import static io.github.humbleui.skija.test.runner.TestRunner.*;

import java.nio.charset.*;

import io.github.humbleui.skija.*;
import io.github.humbleui.skija.paragraph.TypefaceFontProvider;
import io.github.humbleui.skija.svg.SVGDOM;
import io.github.humbleui.skija.test.runner.*;

public class SVGDOMTest implements Executable {
    @Override
    public void execute() throws Exception {
        TestRunner.testMethod(this, "textNeedsFontMgr");
    }

    public void textNeedsFontMgr() throws Exception {
        String svg = "<svg xmlns='http://www.w3.org/2000/svg' width='100' height='40'>"
                   + "<text x='5' y='30' font-family='Inter' font-size='24' fill='black'>Skija</text>"
                   + "</svg>";
        try (var data = Data.makeFromBytes(svg.getBytes(StandardCharsets.UTF_8));
             var fontMgr = new TypefaceFontProvider();
             var inter = FontMgr.getDefault().makeFromFile("fonts/InterHinted-Regular.ttf", 0);)
        {
            fontMgr.registerTypeface(inter, "Inter");

            try (var dom = new SVGDOM(data)) {
                assertEquals(0, countPixels(dom));
            }

            try (var dom = new SVGDOM(data, null)) {
                assertEquals(0, countPixels(dom));
            }

            try (var dom = new SVGDOM(data, fontMgr)) {
                assertEquals(true, countPixels(dom) > 0);
            }
        }
    }

    public int countPixels(SVGDOM dom) {
        try (var bitmap = new Bitmap()) {
            bitmap.allocN32Pixels(100, 40);
            bitmap.erase(0);
            try (var canvas = new Canvas(bitmap)) {
                dom.render(canvas);
            }
            int count = 0;
            for (int y = 0; y < 40; ++y)
                for (int x = 0; x < 100; ++x)
                    if (bitmap.getColor(x, y) != 0)
                        ++count;
            return count;
        }
    }
}
