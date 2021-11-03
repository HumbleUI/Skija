package io.github.humbleui.skija.shaper;

import java.util.*;
import org.jetbrains.annotations.*;
import io.github.humbleui.skija.*;

public class TrivialScriptRunIterator implements Iterator<ScriptRun> {
    @ApiStatus.Internal public final int     _length;
    @ApiStatus.Internal public final String  _script;
    @ApiStatus.Internal public       boolean _atEnd;
    
    public TrivialScriptRunIterator(String text, String script) {
        _length = text.length();
        _script = script;
        _atEnd  = _length == 0;
    }

    @Override
    public ScriptRun next() {
        _atEnd = true;
        return new ScriptRun(_length, _script);
    }

    @Override
    public boolean hasNext() {
        return !_atEnd;
    }
}