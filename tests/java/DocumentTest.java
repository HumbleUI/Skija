package io.github.humbleui.skija.test;

import static io.github.humbleui.skija.test.runner.TestRunner.*;
import java.io.ByteArrayOutputStream;
import io.github.humbleui.skija.*;
import io.github.humbleui.types.Rect;
import io.github.humbleui.skija.test.runner.*;

public class DocumentTest implements Executable {
    @Override
    public void execute() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (OutputWStream stream = new OutputWStream(out);
             Document doc = Document.makePDF(stream)) {
            
            try (Canvas canvas = doc.beginPage(100, 100)) {
                Paint paint = new Paint().setColor(0xFFFF0000);
                canvas.drawRect(Rect.makeXYWH(10, 10, 80, 80), paint);
            }
            doc.endPage();
            doc.close();
        }
        
        byte[] pdfBytes = out.toByteArray();
        if (pdfBytes.length < 4) {
             throw new RuntimeException("PDF output too short");
        }
        assertEquals((byte) '%', pdfBytes[0]);
        assertEquals((byte) 'P', pdfBytes[1]);
        assertEquals((byte) 'D', pdfBytes[2]);
        assertEquals((byte) 'F', pdfBytes[3]);
    }
}
