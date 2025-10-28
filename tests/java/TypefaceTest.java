package io.github.humbleui.skija.test;

import java.util.*;

import io.github.humbleui.skija.impl.*;
import io.github.humbleui.skija.*;
import io.github.humbleui.skija.test.runner.*;

import static io.github.humbleui.skija.test.runner.TestRunner.*;

public class TypefaceTest implements Executable {
    @Override
    public void execute() throws Exception {
        Typeface inter = FontMgr.getDefault().makeFromFile("fonts/InterHinted-Regular.ttf");
        Typeface interV = FontMgr.getDefault().makeFromFile("fonts/Inter-V.ttf");
        Typeface jbMono = FontMgr.getDefault().makeFromFile("fonts/JetBrainsMono-Regular.ttf");
        Typeface jbMonoBold = FontMgr.getDefault().makeFromData(Data.makeFromFileName("fonts/JetBrainsMono-Bold.ttf"));

        assertEquals(FontStyle.NORMAL, inter.getFontStyle());
        assertEquals(false, inter.isBold());
        assertEquals(false, inter.isItalic());

        assertEquals(FontStyle.BOLD, jbMonoBold.getFontStyle());
        assertEquals(true, jbMonoBold.isBold());
        assertEquals(false, jbMonoBold.isItalic());

        assertEquals(false, inter.isFixedPitch());
        assertEquals(true, jbMono.isFixedPitch());

        assertArrayEquals(null, inter.getVariationAxes());
        assertArrayEquals(null, inter.getVariations());

        var axes = new FontVariationAxis[] { new FontVariationAxis("wght", 100f, 400f, 900f),
                                             new FontVariationAxis("slnt", -10f, 0f, 0f) };
        assertArrayEquals(axes, interV.getVariationAxes());
        if (OperatingSystem.CURRENT != OperatingSystem.LINUX)
            assertArrayEquals(FontVariation.parse("wght=400 slnt=0"), interV.getVariations());
        
        Typeface inter500 = interV.makeClone(new FontVariation("wght", 500));
        assertNotEquals(inter500, interV);
        assertArrayEquals(FontVariation.parse("wght=500 slnt=0"), inter500.getVariations());

        Typeface inter400 = interV.makeClone(new FontVariation("wght", 400));
        // assertEquals(inter400, interV);

        assertNotEquals(inter.getUniqueId(), interV.getUniqueId());
        assertNotEquals(inter, interV);
        
        if (OperatingSystem.CURRENT != OperatingSystem.LINUX)
            assertEquals("Arial", FontMgr.getDefault().matchFamilyStyle("Arial", FontStyle.NORMAL).getFamilyName());

        int[] Skia = new int[] { 83, 107, 105, 97 };
        assertArrayEquals(new short[] { 394, 713, 677, 503 }, inter.getUTF32Glyphs(Skia));
        assertArrayEquals(new short[] { 394, 713, 677, 503 }, inter.getStringGlyphs("Skia"));
        assertEquals(Short.valueOf((short) 394), inter.getUTF32Glyph(83));

        assertEquals(2548, interV.getGlyphsCount());

        assertEquals(17, inter.getTablesCount());
        // assertArrayEquals(null, inter.getTableTags());
        assertArrayEquals(new String[] {"GDEF", "GPOS", "GSUB", "OS/2", "cmap", "cvt ", "fpgm", "gasp", "glyf", "head", "hhea", "hmtx", "loca", "maxp", "name", "post", "prep"}, inter.getTableTags());

        assertEquals(true, inter.getTableData("loca").getSize() > 0);

        assertEquals(2816, inter.getUnitsPerEm());

        assertArrayEquals(null, jbMono.getKerningPairAdjustments(null));
        assertArrayEquals(null, jbMono.getKerningPairAdjustments(jbMono.getStringGlyphs("TAV")));
        // assertEquals(null, interV.getKerningPairAdjustments(null));
        // assertArrayEquals(null, interV.getKerningPairAdjustments(interV.getStringGlyphs("TAV")));

        assertArrayEquals(new FontFamilyName[] { new FontFamilyName("Inter", "en-US") }, interV.getFamilyNames());
        assertEquals("Inter", interV.getFamilyName());
        // if (OperatingSystem.CURRENT != OperatingSystem.LINUX)
        //     assertEquals(Rect.makeLTRB(-0.7386364f, -1.0909119f, 2.5830965f, 0.31959534f), interV.getBounds());
    
    
         int[] ttcIndex = new int[1];
         ttcIndex[0] = -1;
         StreamAsset stream1 = inter.openStream(ttcIndex);
         assertNotNull(stream1);
         assertEquals(0, ttcIndex[0]);
         assertEquals(680112, stream1.getLength());
         stream1.close();

         StreamAsset stream2 = interV.openStream();
         assertNotNull(stream2);
         assertEquals(777688, stream2.getLength());
         stream2.close();
    }
}

