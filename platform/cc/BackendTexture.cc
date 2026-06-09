#include <jni.h>
#include "include/gpu/ganesh/GrBackendSurface.h"
#include "include/gpu/ganesh/gl/GrGLBackendSurface.h"
#include "include/gpu/ganesh/gl/GrGLTypes.h"

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_BackendTexture__1nGetFinalizer(JNIEnv* env, jclass clazz) {
  return reinterpret_cast<jlong>(+[](GrBackendTexture* ptr) { delete ptr; });
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_BackendTexture__1nMakeGL
  (JNIEnv* env, jclass jclass, jint width, jint height, jboolean isMipmapped, jint target, jint id, jint format, jboolean isProtected) {
    GrGLTextureInfo info;
    info.fTarget = static_cast<GrGLenum>(target);
    info.fID = static_cast<GrGLuint>(id);
    info.fFormat = static_cast<GrGLenum>(format);
    info.fProtected = isProtected ? skgpu::Protected::kYes : skgpu::Protected::kNo;
    GrBackendTexture obj = GrBackendTextures::MakeGL(width, height, isMipmapped ? skgpu::Mipmapped::kYes : skgpu::Mipmapped::kNo, info);
    GrBackendTexture* backendTexture = new GrBackendTexture(obj);
    return reinterpret_cast<jlong>(backendTexture);
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_BackendTexture__1nGLTextureParametersModified
  (JNIEnv* env, jclass jclass, jlong ptr) {
    GrBackendTexture* backendTexture = reinterpret_cast<GrBackendTexture*>(static_cast<uintptr_t>(ptr));
    GrBackendTextures::GLTextureParametersModified(backendTexture);
}

#ifdef SK_VULKAN
#include "include/gpu/ganesh/vk/GrVkBackendSurface.h"
#include "include/gpu/ganesh/vk/GrVkTypes.h"

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_BackendTexture__1nMakeVulkan
  (JNIEnv* env, jclass jclass, jint width, jint height, jlong imagePtr, jlong deviceMemory, jlong memoryOffset, jlong memorySize, jint flags, jlong backendMemory, jint imageTiling, jint imageLayout, jint format, jint imageUsageFlags, jint sampleCount, jint levelCount, jint currentQueueFamily, jboolean isProtected, jint sharingMode, jboolean partOfSwapchainOrAndroidWindow) {
    GrVkImageInfo info;
    info.fImage = reinterpret_cast<VkImage>(static_cast<uintptr_t>(imagePtr));
    info.fAlloc.fMemory = reinterpret_cast<VkDeviceMemory>(deviceMemory);
    info.fAlloc.fOffset = static_cast<VkDeviceSize>(memoryOffset);
    info.fAlloc.fSize   = static_cast<VkDeviceSize>(memorySize);
    info.fAlloc.fFlags = static_cast<uint32_t>(flags);
    info.fAlloc.fBackendMemory = static_cast<intptr_t>(backendMemory);
    info.fImageTiling = static_cast<VkImageTiling>(imageTiling);
    info.fImageLayout = static_cast<VkImageLayout>(imageLayout);
    info.fFormat = static_cast<VkFormat>(format);
    info.fImageUsageFlags = static_cast<VkImageUsageFlags>(imageUsageFlags);
    info.fSampleCount = static_cast<uint32_t>(sampleCount);
    info.fLevelCount = static_cast<uint32_t>(levelCount);
    info.fCurrentQueueFamily = static_cast<uint32_t>(currentQueueFamily);
    info.fProtected = isProtected ? skgpu::Protected::kYes : skgpu::Protected::kNo;
    info.fSharingMode = static_cast<VkSharingMode>(sharingMode);
#ifdef SK_BUILD_FOR_ANDROID_FRAMEWORK
    info.fPartOfSwapchainOrAndroidWindow = static_cast<bool>(partOfSwapchainOrAndroidWindow);
#endif
    GrBackendTexture obj = GrBackendTextures::MakeVk(width, height, info);
    GrBackendTexture* backendTexture = new GrBackendTexture(obj);
    return reinterpret_cast<jlong>(backendTexture);
}
#endif // SK_VULKAN
