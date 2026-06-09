package io.github.humbleui.skija;

import java.util.Objects;

/**
 * <p>Represents the struct {@code VulkanAlloc} in VulkanTypes.h</p>
 */
public final class VulkanAlloc {
    /**
     * <p>The native Vulkan device memory handle ({@code VkDeviceMemory}).</p>
     *
     * <p>Can be {@code VK_NULL_HANDLE} iff is an RT and is borrowed</p>
     */
    private final long memory;

    /**
     * <p>Offset into the native memory allocation ({@code VkDeviceSize}).</p>
     */
    private final long offset;

    /**
     * <p>Size of the native memory allocation ({@code VkDeviceSize}).</p>
     *
     * <p>This can be indeterminate iff Tex uses borrow semantics.</p>
     */
    private final long size;

    /**
     * <p>Flat bitmask representing Skia-specific custom allocation states.</p>
     *
     * <p><b>CRITICAL:</b> This field expects a bitmask combined from {@link VulkanAlloc.Flag},
     * <i>not</i> native Vulkan driver property flags (such as {@code VkMemoryPropertyFlags}).
     */
    private final int flags;

    /**
     * <p>Opaque handle or pointer to the native memory allocator block.</p>
     *
     * <p>Typically represents a handle managed via {@code skgpu::VulkanMemoryAllocator} (e.g., VMA).</p>
     */
    private final long backendMemory;

    /**
     * Configuration flags for {@link VulkanAlloc}, represented as bitmask bits.
     */
    public enum Flag {
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

        private final int value;

        Flag(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int toBitmask(Flag... flags) {
            int combined = 0;
            for (Flag flag : flags) {
                combined |= flag.value;
            }
            return combined;
        }

        public static Flag valueOf(int value) {
            for (Flag flag : Flag.values()) {
                if (flag.value == value) {
                    return flag;
                }
            }
            return null;
        }
    }

    public VulkanAlloc() {
        this(0, 0, 0, 0, Flag.NONE);
    }

    public VulkanAlloc(long memory, long offset, long size, Flag... flags) {
        this(memory, offset, size, 0L, flags);
    }

    public VulkanAlloc(long memory, long offset, long size, long backendMemory, Flag... flags) {
        this.memory = memory;
        this.offset = offset;
        this.size = size;
        this.flags = Flag.toBitmask(flags);
        this.backendMemory = backendMemory;
    }

    public long getMemory() {
        return memory;
    }

    public long getOffset() {
        return offset;
    }

    public long getSize() {
        return size;
    }

    public int getFlags() {
        return flags;
    }

    public long getBackendMemory() {
        return backendMemory;
    }

    public boolean hasFlag(Flag flag) {
        if (flag == Flag.NONE) return flags == 0;
        return (flags & flag.getValue()) != 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        VulkanAlloc that = (VulkanAlloc) obj;
        return memory == that.memory && offset == that.offset && size == that.size && flags == that.flags && backendMemory == that.backendMemory;
    }

    @Override
    public int hashCode() {
        return Objects.hash(memory, offset, size, flags, backendMemory);
    }
}