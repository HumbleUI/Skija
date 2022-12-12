package io.github.humbleui.skija.shaper;

import java.util.*;
import org.jetbrains.annotations.*;
import io.github.humbleui.skija.*;
import io.github.humbleui.skija.impl.*;

public class FontMgrRunIterator extends ManagedRunIterator<FontRun> {
    static { Library.staticLoad(); }

    public FontMgrRunIterator(ManagedString text, boolean manageText, Font font, Iterator<LanguageRun> languageRunIterator, @NotNull ShapingOptions opts) {
        super(_nMake(Native.getPtr(text), Native.getPtr(font), languageRunIterator, opts), text, manageText);
        Stats.onNativeCall();
        ReferenceUtil.reachabilityFence(text);
        ReferenceUtil.reachabilityFence(font);
        ReferenceUtil.reachabilityFence(opts);
    }

    public FontMgrRunIterator(ManagedString text, boolean manageText, Font font, @NotNull ShapingOptions opts) {
        this(text, manageText, font, null, opts);
    }

    public FontMgrRunIterator(String text, Font font, @NotNull ShapingOptions opts) {
        this(new ManagedString(text), true, font, null, opts);
    }

    public FontMgrRunIterator(String text, Font font) {
        this(new ManagedString(text), true, font, null, ShapingOptions.DEFAULT);
    }

    @Override
    public FontRun next() {
        try {
            _nConsume(_ptr);
            return new FontRun(_getEndOfCurrentRun(), new Font(_nGetCurrentFont(_ptr)));
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    @ApiStatus.Internal public static native long _nMake(long textPtr, long fontPtr, Iterator<LanguageRun> languageRunIterator, ShapingOptions opts);
    @ApiStatus.Internal public static native long _nGetCurrentFont(long ptr);
}