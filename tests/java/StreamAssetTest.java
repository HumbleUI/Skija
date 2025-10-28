package io.github.humbleui.skija.test;

import static io.github.humbleui.skija.test.runner.TestRunner.*;

import io.github.humbleui.skija.test.runner.*;
import io.github.humbleui.skija.*;

public class StreamAssetTest implements Executable {
    static final int fontStreamLength = 680112;

    @Override
    public void execute() throws Exception {
        Typeface typeface = FontMgr.getDefault().makeFromFile("fonts/InterHinted-Regular.ttf", 0);
        int[] ttcIndex = new int[1];
        ttcIndex[0] = -1;
        StreamAsset stream = typeface.openStream(ttcIndex);
        assertNotNull(stream);
        assertEquals(0, ttcIndex[0]);
        assertEquals(fontStreamLength, stream.getLength());

        byte[] buffer = new byte[1024];
        int bytesRead = stream.read(buffer, buffer.length);
        int pos = stream.getPosition();
        assertEquals(1024, bytesRead);
        assertEquals(1024, pos);
        assertEquals(true, stream.move(10));
        assertEquals(1034, stream.getPosition());
        assertEquals(true, stream.rewind());
        assertEquals(0, stream.getPosition());
        assertEquals(true, stream.seek(1000));
        assertEquals(1000, stream.getPosition());
        stream.close();
    }
}