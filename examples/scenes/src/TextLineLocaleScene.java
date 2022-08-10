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
    public final Font font;

    public TextLineLocaleScene() {
        // fontMgr.registerTypeface(Typeface.makeFromFile(file("fonts/NotoSans-Regular.ttf")));
        // fontMgr.registerTypeface(Typeface.makeFromFile(file("fonts/NotoSansJP-Regular.otf")));
        // fontMgr.registerTypeface(Typeface.makeFromFile(file("fonts/NotoSansSC-Regular.otf")));
        // fontMgr.registerTypeface(Typeface.makeFromFile(file("fonts/NotoSansTC-Regular.otf")));
        font = new Font().setSize(36f);
    }

    @Override
    public void draw(Canvas canvas, int width, int height, float dpi, int xpos, int ypos) {
        try (var shaper = Shaper.makeShapeThenWrap();) {
            ShapingOptions opts = ShapingOptions.DEFAULT;
            String text = "ja: 刃";
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
                    canvas.drawTextLine(line, 20, 100, textFill);
                }
            }

            text = "zh-Hans: 刃";
            Iterator<ScriptRun> scriptIter = new TrivialScriptRunIterator(text, "Hans");
            langIter = new TrivialLanguageRunIterator(text, "zh");
            try (ManagedString textUtf8 = new ManagedString(text);
                 FontMgrRunIterator fontIter = new FontMgrRunIterator(textUtf8, false, font, langIter, opts);
                 IcuBidiRunIterator bidiIter = new IcuBidiRunIterator(textUtf8, false, java.text.Bidi.DIRECTION_LEFT_TO_RIGHT);
                 TextLineRunHandler runHandler = new TextLineRunHandler(textUtf8, false);)
            {
                
                shaper.shape(textUtf8, fontIter, bidiIter, scriptIter, langIter, opts, width, runHandler);
                try (TextLine line = runHandler.makeLine();) {
                    canvas.drawTextLine(line, 20, 200, textFill);
                }
            }

            text = "zh-Hant: 刃";
            langIter = new TrivialLanguageRunIterator(text, "zh");
            scriptIter = new TrivialScriptRunIterator(text, "Hant");
            try (ManagedString textUtf8 = new ManagedString(text);
                 FontMgrRunIterator fontIter = new FontMgrRunIterator(textUtf8, false, font, langIter, opts);
                 IcuBidiRunIterator bidiIter = new IcuBidiRunIterator(textUtf8, false, java.text.Bidi.DIRECTION_LEFT_TO_RIGHT);
                 TextLineRunHandler runHandler = new TextLineRunHandler(textUtf8, false);)
            {
                shaper.shape(textUtf8, fontIter, bidiIter, scriptIter, langIter, opts, width, runHandler);
                try (TextLine line = runHandler.makeLine();) {
                    canvas.drawTextLine(line, 20, 300, textFill);
                }
            }

            try (var line = shaper.shapeLine("ja: 刃, zh-Hans: 刃, zh-Hant: 刃", font)) {
                canvas.drawTextLine(line, 20, 400, textFill);
            }
        }
    }
}