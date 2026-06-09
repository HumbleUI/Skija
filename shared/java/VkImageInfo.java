package io.github.humbleui.skija;

import java.util.Objects;

/**
 * <p>Represents the struct {@code GrVkImageInfo} in GrVkTypes.h</p>
 *
 * <p>When wrapping a BackendTexture or BackendRendenderTarget, the {@code currentQueueFamily} should
 * either be {@code VK_QUEUE_FAMILY_IGNORED}, {@code VK_QUEUE_FAMILY_EXTERNAL}, or {@code VK_QUEUE_FAMILY_FOREIGN_EXT}. If
 * {@code sharingMode} is {@code VK_SHARING_MODE_EXCLUSIVE} then {@code currentQueueFamily} can also be the graphics
 * queue index passed into Skia.</p>
 */
public final class VkImageInfo {
    private final long vkImage;
    /**
     * @see VulkanAlloc
     */
    private final VulkanAlloc alloc;
    private final int imageTiling;
    private final int imageLayout;
    private final int format;
    private final int imageUsageFlags;
    private final int sampleCount;
    private final int levelCount;
    private final int currentQueueFamily;
    private final boolean isProtected;
    private final int sharingMode;
    private final boolean partOfSwapchainOrAndroidWindow;

    public VkImageInfo(long vkImage, VulkanAlloc alloc, int imageTiling, int imageLayout, int format, int imageUsageFlags, int sampleCount, int levelCount) {
        this(vkImage, alloc, imageTiling, imageLayout, format, imageUsageFlags, sampleCount, levelCount, (~0), false, 0, false);
    }

    public VkImageInfo(long vkImage, VulkanAlloc alloc, int imageTiling, int imageLayout, int format, int imageUsageFlags, int sampleCount, int levelCount, int currentQueueFamily, boolean isProtected, int sharingMode) {
        this(vkImage, alloc, imageTiling, imageLayout, format, imageUsageFlags, sampleCount, levelCount, currentQueueFamily, isProtected, sharingMode, false);
    }

    public VkImageInfo(long vkImage, VulkanAlloc alloc, int imageTiling, int imageLayout, int format, int imageUsageFlags, int sampleCount, int levelCount, int currentQueueFamily, boolean isProtected, int sharingMode, boolean partOfSwapchainOrAndroidWindow) {
        this.vkImage = vkImage;
        this.alloc = alloc;
        this.imageTiling = imageTiling;
        this.imageLayout = imageLayout;
        this.format = format;
        this.imageUsageFlags = imageUsageFlags;
        this.sampleCount = sampleCount;
        this.levelCount = levelCount;
        this.currentQueueFamily = currentQueueFamily;
        this.isProtected = isProtected;
        this.sharingMode = sharingMode;
        this.partOfSwapchainOrAndroidWindow = partOfSwapchainOrAndroidWindow;
    }

    public long getVkImage() {
        return vkImage;
    }

    public VulkanAlloc getAlloc() {
        return alloc;
    }

    public int getImageTiling() {
        return imageTiling;
    }

    public int getImageLayout() {
        return imageLayout;
    }

    public int getFormat() {
        return format;
    }

    public int getImageUsageFlags() {
        return imageUsageFlags;
    }

    public int getSampleCount() {
        return sampleCount;
    }

    public int getLevelCount() {
        return levelCount;
    }

    public int getCurrentQueueFamily() {
        return currentQueueFamily;
    }

    public boolean isProtected() {
        return isProtected;
    }

    public int getSharingMode() {
        return sharingMode;
    }

    public boolean isPartOfSwapchainOrAndroidWindow() {
        return partOfSwapchainOrAndroidWindow;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        VkImageInfo that = (VkImageInfo) obj;
        return vkImage == that.vkImage && imageTiling == that.imageTiling && imageLayout == that.imageLayout && format == that.format && imageUsageFlags == that.imageUsageFlags && sampleCount == that.sampleCount && levelCount == that.levelCount && currentQueueFamily == that.currentQueueFamily && isProtected == that.isProtected && sharingMode == that.sharingMode && partOfSwapchainOrAndroidWindow == that.partOfSwapchainOrAndroidWindow && Objects.equals(alloc, that.alloc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vkImage, alloc, imageTiling, imageLayout, format, imageUsageFlags, sampleCount, levelCount, currentQueueFamily, isProtected, sharingMode, partOfSwapchainOrAndroidWindow);
    }
}