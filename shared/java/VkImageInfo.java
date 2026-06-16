package io.github.humbleui.skija;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>Represents the struct {@code GrVkImageInfo} in GrVkTypes.h</p>
 *
 * <p>When wrapping a BackendTexture or BackendRendenderTarget, the {@code currentQueueFamily} should
 * either be {@code VK_QUEUE_FAMILY_IGNORED}, {@code VK_QUEUE_FAMILY_EXTERNAL}, or {@code VK_QUEUE_FAMILY_FOREIGN_EXT}. If
 * {@code sharingMode} is {@code VK_SHARING_MODE_EXCLUSIVE} then {@code currentQueueFamily} can also be the graphics
 * queue index passed into Skia.</p>
 */
@AllArgsConstructor @Data
public final class VkImageInfo {

    private final long _vkImage;

    /**
     * @see VulkanAlloc
     */
    private final VulkanAlloc _alloc;

    private final int _imageTiling;
    private final int _imageLayout;
    private final int _format;
    private final int _imageUsageFlags;
    private final int _sampleCount;
    private final int _levelCount;
    private final int _currentQueueFamily;
    private final boolean _isProtected;
    private final int _sharingMode;
    private final boolean _partOfSwapchainOrAndroidWindow;

    public VkImageInfo(long vkImage, VulkanAlloc alloc, int imageTiling, int imageLayout, int format, int imageUsageFlags, int sampleCount, int levelCount) {
        this(vkImage, alloc, imageTiling, imageLayout, format, imageUsageFlags, sampleCount, levelCount, (~0), false, 0, false);
    }

    public VkImageInfo(long vkImage, VulkanAlloc alloc, int imageTiling, int imageLayout, int format, int imageUsageFlags, int sampleCount, int levelCount, int currentQueueFamily, boolean isProtected, int sharingMode) {
        this(vkImage, alloc, imageTiling, imageLayout, format, imageUsageFlags, sampleCount, levelCount, currentQueueFamily, isProtected, sharingMode, false);
    }
}