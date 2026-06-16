package io.github.humbleui.skija;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>Represents the struct {@code VulkanAlloc} in VulkanTypes.h</p>
 */
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public final class VulkanAlloc {
    /**
     * <p>The native Vulkan device memory handle ({@code VkDeviceMemory}).</p>
     *
     * <p>Can be {@code VK_NULL_HANDLE} iff is an RT and is borrowed</p>
     */
    private final long _memory;

    /**
     * <p>Offset into the native memory allocation ({@code VkDeviceSize}).</p>
     */
    private final long _offset;

    /**
     * <p>Size of the native memory allocation ({@code VkDeviceSize}).</p>
     *
     * <p>This can be indeterminate iff Tex uses borrow semantics.</p>
     */
    private final long _size;

    /**
     * <p>Flat bitmask representing Skia-specific custom allocation states.</p>
     *
     * <p><b>CRITICAL:</b> This field expects a bitmask combined from {@link VulkanAllocFlag},
     * <i>not</i> native Vulkan driver property flags (such as {@code VkMemoryPropertyFlags}).
     */
    private final int _flags;

    /**
     * <p>Opaque handle or pointer to the native memory allocator block.</p>
     *
     * <p>Typically represents a handle managed via {@code skgpu::VulkanMemoryAllocator} (e.g., VMA).</p>
     */
    private final long _backendMemory;

    public VulkanAlloc(long memory, long offset, long size, VulkanAllocFlag... flags) {
        this(memory, offset, size, VulkanAllocFlag.toBitmask(flags), 0L);
    }

    public VulkanAlloc(long memory, long offset, long size, long backendMemory, VulkanAllocFlag... flags) {
        this(memory, offset, size, VulkanAllocFlag.toBitmask(flags), backendMemory);
    }

    public boolean hasFlag(VulkanAllocFlag flag) {
        if (flag == VulkanAllocFlag.NONE) return _flags == 0;
        return (_flags & flag.getValue()) != 0;
    }
}