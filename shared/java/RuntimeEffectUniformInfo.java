package io.github.humbleui.skija;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.*;

/**
 * Reflected description of a uniform variable in the {@link RuntimeEffect}'s SkSL
 */
@AllArgsConstructor @Data
public class RuntimeEffectUniformInfo {
    @NotNull
    public final String _name;

    public final int _offset;

    @NotNull
    public final RuntimeEffectUniformType _type;

    public final int _count;

    public final int _flags;

    public boolean isArray() {
        return (_flags & RuntimeEffectUniformFlag.ARRAY._value) != 0;
    }

    public boolean isColor() {
        return (_flags & RuntimeEffectUniformFlag.COLOR._value) != 0;
    }

    public int getSizeInBytes() {
        int sizeOfFloat = 4;
        int sizeOfInt = 4;
        int baseSize;
        switch (_type) {
            case FLOAT:
                baseSize = sizeOfFloat;
                break;
            case FLOAT2:
                baseSize = sizeOfFloat * 2;
                break;
            case FLOAT3:
                baseSize = sizeOfFloat * 3;
                break;
            case FLOAT4:
                baseSize = sizeOfFloat * 4;
                break;
            case FLOAT2X2:
                baseSize = sizeOfFloat * 4;
                break;
            case FLOAT3X3:
                baseSize = sizeOfFloat * 9;
                break;
            case FLOAT4X4:
                baseSize = sizeOfFloat * 16;
                break;
            case INT:
                baseSize = sizeOfInt;
                break;
            case INT2:
                baseSize = sizeOfInt * 2;
                break;
            case INT3:
                baseSize = sizeOfInt * 3;
                break;
            case INT4:
                baseSize = sizeOfInt * 4;
                break;
            default:
                throw new IllegalArgumentException("Unknown type: " + _type);
        }
        return baseSize * _count;
    }

    @ApiStatus.Internal
    public RuntimeEffectUniformInfo(String name, int offset, int type, int count, int flags) {
        this(name, offset, RuntimeEffectUniformType._values[type], count, flags);
    }
}
