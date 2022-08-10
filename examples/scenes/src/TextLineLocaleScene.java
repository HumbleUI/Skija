package io.github.humbleui.skija.examples.scenes;

import java.util.*;
import java.util.stream.*;
import io.github.humbleui.skija.*;
import io.github.humbleui.skija.shaper.*;
import io.github.humbleui.skija.paragraph.*;
import io.github.humbleui.types.*;

// TODO: doesn’t work on macOS

public class TextLineLocaleScene extends Scene {
    public final Paint textFill = new Paint().setColor(0xFF000000);
    public final Font font = new Font().setSize(36f);
    public final FontMgr fontMgr = FontMgr.getDefault();

    @Override
    public void draw(Canvas canvas, int width, int height, float dpi, int xpos, int ypos) {
        canvas.translate(100, 100);
        try (var shaper = Shaper.makeShapeThenWrap();) {
            ShapingOptions opts = ShapingOptions.DEFAULT;
            Typeface face = fontMgr.matchFamilyStyleCharacter(null, FontStyle.NORMAL, new String[] { "ja" }, "刃".codePointAt(0));
            String text = "刃 ja " + face.getFamilyName();
            Iterator<LanguageRun> langIter = new TrivialLanguageRunIterator(text, "ja");
            try (ManagedString textUtf8 = new ManagedString(text);
                 FontMgrRunIterator fontIter = new FontMgrRunIterator(textUtf8, false, font, langIter, opts);
                 IcuBidiRunIterator bidiIter = new IcuBidiRunIterator(textUtf8, false, java.text.Bidi.DIRECTION_LEFT_TO_RIGHT);
                 HbIcuScriptRunIterator scriptIter = new HbIcuScriptRunIterator(textUtf8, false);
                 TextLineRunHandler runHandler = new TextLineRunHandler(textUtf8, false);)
            {
                // Iterator<ScriptRun> scriptIter = new TrivialScriptRunIterator(text, "Hrkt");
                // Iterator<ScriptRun> scriptIter = new TrivialScriptRunIterator(text, "Jpan");
                // Iterator<ScriptRun> scriptIter = new TrivialScriptRunIterator(text, "Zinh");
                // Iterator<ScriptRun> scriptIter = new TrivialScriptRunIterator(text, "Zyyy");
                // Iterator<ScriptRun> scriptIter = new TrivialScriptRunIterator(text, "Zzzz");
                
                shaper.shape(textUtf8, fontIter, bidiIter, scriptIter, langIter, opts, width, runHandler);
                try (TextLine line = runHandler.makeLine();) {
                    canvas.drawTextLine(line, 0, 0, textFill);
                    canvas.translate(0, 50);
                }
            }

            face = fontMgr.matchFamilyStyleCharacter(null, FontStyle.NORMAL, new String[] { "zh-Hans" }, "刃".codePointAt(0));
            text = "刃 zh-Hans " + face.getFamilyName();
            Iterator<ScriptRun> scriptIter = new TrivialScriptRunIterator(text, "Hans");
            langIter = new TrivialLanguageRunIterator(text, "zh");
            try (ManagedString textUtf8 = new ManagedString(text);
                 FontMgrRunIterator fontIter = new FontMgrRunIterator(textUtf8, false, font, langIter, opts);
                 IcuBidiRunIterator bidiIter = new IcuBidiRunIterator(textUtf8, false, java.text.Bidi.DIRECTION_LEFT_TO_RIGHT);
                 TextLineRunHandler runHandler = new TextLineRunHandler(textUtf8, false);)
            {
                
                shaper.shape(textUtf8, fontIter, bidiIter, scriptIter, langIter, opts, width, runHandler);
                try (TextLine line = runHandler.makeLine();) {
                    canvas.drawTextLine(line, 0, 0, textFill);
                    canvas.translate(0, 50);
                }
            }

            face = fontMgr.matchFamilyStyleCharacter(null, FontStyle.NORMAL, new String[] { "zh-Hant" }, "刃".codePointAt(0));
            text = "刃 zh-Hant " + face.getFamilyName();
            langIter = new TrivialLanguageRunIterator(text, "zh");
            scriptIter = new TrivialScriptRunIterator(text, "Hant");
            try (ManagedString textUtf8 = new ManagedString(text);
                 FontMgrRunIterator fontIter = new FontMgrRunIterator(textUtf8, false, font, langIter, opts);
                 IcuBidiRunIterator bidiIter = new IcuBidiRunIterator(textUtf8, false, java.text.Bidi.DIRECTION_LEFT_TO_RIGHT);
                 TextLineRunHandler runHandler = new TextLineRunHandler(textUtf8, false);)
            {
                shaper.shape(textUtf8, fontIter, bidiIter, scriptIter, langIter, opts, width, runHandler);
                try (TextLine line = runHandler.makeLine();) {
                    canvas.drawTextLine(line, 0, 0, textFill);
                    canvas.translate(0, 50);
                }
            }

            try (var line = shaper.shapeLine("ja: 刃, zh-Hans: 刃, zh-Hant: 刃", font)) {
                canvas.drawTextLine(line, 0, 0, textFill);
                canvas.translate(0, 50);
            }
        }
    }
}