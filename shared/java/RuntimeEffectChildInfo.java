package io.github.humbleui.skija;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.*;

/**
 * Reflected description of a uniform child (shader or colorFilter) in the {@link RuntimeEffect}'s SkSL
 */
@AllArgsConstructor @Data
public class RuntimeEffectChildInfo {
    @NotNull
    public final String _name;

    @NotNull
    public final RuntimeEffectChildType _type;

    public final int _index;

    @ApiStatus.Internal
    public RuntimeEffectChildInfo(String name, int type, int index) {
        this(name, RuntimeEffectChildType._values[type], index);
    }
}
