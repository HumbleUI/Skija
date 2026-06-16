package io.github.humbleui.skija;

import io.github.humbleui.skija.impl.*;
import org.jetbrains.annotations.ApiStatus;

/**
 * <p>Represents {@code GrBackendTexture} in GrDirectContext.h</p>
 */
public class BackendTexture extends Managed {
    static { Library.staticLoad(); }

    @ApiStatus.Internal
    public BackendTexture(long ptr) {
        super(ptr, _FinalizerHolder.PTR);
    }

    /**
     * <p>Creates GrBackendTexture from GL texture.</p>
     *
     * @param width       width of the texture
     * @param height      height of the texture
     * @param isMipmapped if the passed textureId has a GL mipmap, this should be true, otherwise false
     * @param info        GLTextureInfo describing the GL texture to be used as a backend texture
     * @return            BackendTexture wrapping the passed GL texture
     */
    public static BackendTexture makeGL(int width, int height, boolean isMipmapped, GLTextureInfo info) {
        Stats.onNativeCall();
        return new BackendTexture(_nMakeGL(width, height, isMipmapped, info.getTarget(), info.getId(), info.getFormat(), info.isProtected()));
    }

    /**
     * <p>Call this to indicate that the texture parameters have been modified
     * in the GL context externally to Gr context.</p>
     */
    public void glTextureParametersModified() {
        try {
            Stats.onNativeCall();
            _nGLTextureParametersModified(Native.getPtr(this));
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
    }

    /**
     * <p>Creates GrBackendTexture from Vulkan image.</p>
     *
     * @param width  width of the texture
     * @param height height of the texture
     * @param info   VkImageInfo describing the Vulkan image to be used as backend texture
     * @return       BackendTexture wrapping the passed Vulkan image
     */
    public static BackendTexture makeVulkan(int width, int height, VkImageInfo info) {
        Stats.onNativeCall();
        return new BackendTexture(_nMakeVulkan(width, height, info.getVkImage(), info.getAlloc().getMemory(), info.getAlloc().getOffset(), info.getAlloc().getSize(), info.getAlloc().getFlags(), info.getAlloc().getBackendMemory(), info.getImageTiling(), info.getImageLayout(), info.getFormat(), info.getImageUsageFlags(), info.getSampleCount(), info.getLevelCount(), info.getCurrentQueueFamily(), info.isProtected(), info.getSharingMode(), info.isPartOfSwapchainOrAndroidWindow()));
    }

    @ApiStatus.Internal
    public static class _FinalizerHolder {
        public static final long PTR = _nGetFinalizer();
    }

    @ApiStatus.Internal public static native long _nGetFinalizer();
    @ApiStatus.Internal public static native long _nMakeGL(int width, int height, boolean isMipmapped, int target, int id, int format, boolean isProtected);
    @ApiStatus.Internal public static native void _nGLTextureParametersModified(long ptr);
    @ApiStatus.Internal public static native long _nMakeVulkan(int width, int height, long imagePtr, long deviceMemory, long memoryOffset, long memorySize, int flags, long backendMemory, int imageTiling, int imageLayout, int format, int imageUsageFlags, int sampleCount, int levelCount, int currentQueueFamily, boolean isProtected, int sharingMode, boolean partOfSwapchainOrAndroidWindow);
}