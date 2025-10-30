package io.github.humbleui.skija.test;

import static io.github.humbleui.skija.test.runner.TestRunner.assertArrayEquals;
import static io.github.humbleui.skija.test.runner.TestRunner.assertEquals;
import static io.github.humbleui.skija.test.runner.TestRunner.assertNotNull;

import io.github.humbleui.skija.Data;
import io.github.humbleui.skija.FontMgr;
import io.github.humbleui.skija.FontStyle;
import io.github.humbleui.skija.Typeface;
import io.github.humbleui.skija.paragraph.FontCollection;
import io.github.humbleui.skija.paragraph.TypefaceFontProvider;
import io.github.humbleui.skija.test.runner.Executable;
import io.github.humbleui.skija.test.runner.TestRunner;

public class FontMgrTest implements Executable {
    @Override
    public void execute() throws Exception {
        // FontManager
        TypefaceFontProvider fm = new TypefaceFontProvider();
        Typeface jbMono = FontMgr.getDefault().makeFromFile("fonts/JetBrainsMono-Regular.ttf", 0);
        fm.registerTypeface(jbMono);
        Typeface jbMonoBold = FontMgr.getDefault().makeFromFile("fonts/JetBrainsMono-Bold.ttf", 0);
        fm.registerTypeface(jbMonoBold);
        Typeface inter = FontMgr.getDefault().makeFromFile("fonts/InterHinted-Regular.ttf", 0);
        fm.registerTypeface(inter, "Interface");

        assertEquals(2, fm.getFamiliesCount());
        assertEquals("JetBrains Mono", fm.getFamilyName(0));
        assertEquals("Interface", fm.getFamilyName(1));

        try (var ss = fm.makeStyleSet(0)) {
            assertEquals(2, ss.count()); // ?
        }

        try (var ss = fm.makeStyleSet(1)) {
            assertEquals(1, ss.count()); // ?
        }

        // FontStyleSet
        try (var ss = fm.matchFamily("JetBrains Mono")) {
            assertEquals(2, ss.count());
            assertEquals(FontStyle.NORMAL, ss.getStyle(0));
            assertEquals("JetBrains Mono", ss.getStyleName(0));

            assertEquals(FontStyle.BOLD, ss.getStyle(1));
            assertEquals("JetBrains Mono", ss.getStyleName(1));

            assertEquals(2, jbMono.getRefCount());
            try (var face = ss.getTypeface(0)) {
                assertEquals(3, jbMono.getRefCount());
                assertEquals(jbMono, face);
            }
            assertEquals(2, jbMono.getRefCount());

            assertEquals(2, jbMonoBold.getRefCount());
            try (var face = ss.getTypeface(1)) {
                assertEquals(3, jbMonoBold.getRefCount());
                assertEquals(jbMonoBold, face);
            }
            assertEquals(2, jbMonoBold.getRefCount());

            assertEquals(2, jbMono.getRefCount());
            try (var face = ss.matchStyle(FontStyle.NORMAL)) {
                assertEquals(3, jbMono.getRefCount());
                assertEquals(jbMono, face);
            }
            assertEquals(2, jbMono.getRefCount());            

            assertEquals(2, jbMonoBold.getRefCount());
            try (var face = ss.matchStyle(FontStyle.BOLD)) {
                assertEquals(3, jbMonoBold.getRefCount());
                assertEquals(jbMonoBold, face);
            }
            assertEquals(2, jbMonoBold.getRefCount());

            assertEquals(jbMono, ss.matchStyle(FontStyle.ITALIC)); // ?
        }

        assertNotNull(fm.matchFamilyStyle("JetBrains Mono", FontStyle.BOLD));
        assertNotNull(fm.matchFamilyStyle("Interface", FontStyle.NORMAL));

        assertEquals(null, fm.matchFamilyStyleCharacter("JetBrains Mono", FontStyle.BOLD, new String[] {"en-US"}, 65 /* A */)); // ?

        try (var data = Data.makeFromFileName("fonts/JetBrainsMono-Italic.ttf");
             var face = fm.makeFromData(data);
             var ss = fm.matchFamily("JetBrains Mono"); )
        {
            assertEquals(2, fm.getFamiliesCount());
            assertEquals(2, ss.count()); // ?
        }

        fm.close();
    }
}