package io.github.humbleui.skija.shaper;

import lombok.*;
import org.jetbrains.annotations.*;
import io.github.humbleui.skija.*;

@AllArgsConstructor
@lombok.Data
public class ScriptRun {
    @ApiStatus.Internal public final int _end;
    @ApiStatus.Internal public final int _scriptTag;

    public ScriptRun(int end, String script) {
        this(end, FourByteTag.fromString(script));
    }

    /** Should be iso15924 codes. */
    public String getScript() {
        return FourByteTag.toString(_scriptTag);
    }
}
