package io.github.humbleui.skija;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Configuration flags for {@link VulkanAlloc}, represented as bitmask bits.
 */
@RequiredArgsConstructor
@Getter
public enum VulkanAllocFlag {
    /**
     * <p>No special memory properties are requested.</p>
     */
    NONE(0),
    /**
     * <p>Memory must be flushed to device after mapping.</p>
     */
    NONCOHERENT(1),
    /**
     * <p>Memory is able to be mapped.</p>
     */
    MAPPABLE(1 << 1),
    /**
     * <p>Memory was created with lazy allocation.</p>
     */
    LAZILY_ALLOCATED(1 << 2);

    private final int _value;

    public static int toBitmask(VulkanAllocFlag... flags) {
        int combined = 0;
        for (VulkanAllocFlag flag : flags) {
            combined |= flag._value;
        }
        return combined;
    }

    public static VulkanAllocFlag valueOf(int value) {
        for (VulkanAllocFlag flag : VulkanAllocFlag.values()) {
            if (flag._value == value) {
                return flag;
            }
        }
        return null;
    }
}
