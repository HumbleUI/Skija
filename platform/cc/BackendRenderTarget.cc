#include <iostream>
#include <jni.h>
#include "include/gpu/ganesh/GrBackendSurface.h"

static void deleteBackendRenderTarget(GrBackendRenderTarget* rt) {
    // std::cout << "Deleting [GrBackendRenderTarget " << rt << "]" << std::endl;
    delete rt;
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_BackendRenderTarget__1nGetFinalizer
  (JNIEnv* env, jclass jclass) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deleteBackendRenderTarget));
}

#include "include/gpu/ganesh/gl/GrGLBackendSurface.h"
#include "include/gpu/ganesh/gl/GrGLTypes.h"

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_BackendRenderTarget__1nMakeGL
  (JNIEnv* env, jclass jclass, jint width, jint height, jint sampleCnt, jint stencilBits, jint fbId, jint fbFormat) {
    GrGLFramebufferInfo glInfo = { static_cast<unsigned int>(fbId), static_cast<unsigned int>(fbFormat) };
    GrBackendRenderTarget target = GrBackendRenderTargets::MakeGL(width, height, sampleCnt, stencilBits, glInfo);
    GrBackendRenderTarget* instance = new GrBackendRenderTarget(target);
    return reinterpret_cast<jlong>(instance);
}

#ifdef SK_METAL
#include "include/gpu/ganesh/mtl/GrMtlBackendSurface.h"
#include "include/gpu/ganesh/mtl/GrMtlTypes.h"

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_BackendRenderTarget__1nMakeMetal
  (JNIEnv* env, jclass jclass, jint width, jint height, jlong texturePtr) {
    GrMTLHandle texture = reinterpret_cast<GrMTLHandle>(static_cast<uintptr_t>(texturePtr));
    GrMtlTextureInfo fbInfo;
    fbInfo.fTexture.retain(texture);
    GrBackendRenderTarget target = GrBackendRenderTargets::MakeMtl(width, height, fbInfo);
    GrBackendRenderTarget* instance = new GrBackendRenderTarget(target);
    return reinterpret_cast<jlong>(instance);
}
#endif //SK_METAL

#ifdef SK_DIRECT3D
#include "include/gpu/ganesh/d3d/GrD3DTypes.h"

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_BackendRenderTarget__1nMakeDirect3D
  (JNIEnv* env, jclass jclass, jint width, jint height, jlong texturePtr, jint format, jint sampleCnt, jint levelCnt) {
    GrD3DTextureResourceInfo texResInfo = {};
    ID3D12Resource* resource = reinterpret_cast<ID3D12Resource*>(static_cast<uintptr_t>(texturePtr));
    texResInfo.fResource.retain(resource);
    texResInfo.fResourceState = D3D12_RESOURCE_STATE_COMMON;
    texResInfo.fFormat = static_cast<DXGI_FORMAT>(format);
    texResInfo.fSampleCount = static_cast<uint32_t>(sampleCnt);
    texResInfo.fLevelCount = static_cast<uint32_t>(levelCnt);
    GrBackendRenderTarget* instance = new GrBackendRenderTarget(width, height, texResInfo);
    return reinterpret_cast<jlong>(instance);
}
#endif //SK_DIRECT3D

#ifdef SK_VULKAN
#include "include/gpu/ganesh/vk/GrVkBackendSurface.h"
#include "include/gpu/ganesh/vk/GrVkTypes.h"

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_BackendRenderTarget__1nMakeVulkan
  (JNIEnv* env, jclass jclass, jint width, jint height, jlong imagePtr, jint imageTiling, jint imageLayout, jint format, jint imageUsageFlags, jint sampleCnt, jint levelCnt) {
    GrVkImageInfo vkInfo;
    vkInfo.fImage = reinterpret_cast<VkImage>(static_cast<uintptr_t>(imagePtr));
    vkInfo.fAlloc.fMemory = VK_NULL_HANDLE;
    vkInfo.fAlloc.fOffset = 0;
    vkInfo.fAlloc.fSize = 0;
    vkInfo.fAlloc.fFlags = 0;
    vkInfo.fAlloc.fBackendMemory = 0;
    vkInfo.fImageTiling = static_cast<VkImageTiling>(imageTiling);
    vkInfo.fImageLayout = static_cast<VkImageLayout>(imageLayout);
    vkInfo.fFormat = static_cast<VkFormat>(format);
    vkInfo.fImageUsageFlags = static_cast<VkImageUsageFlags>(imageUsageFlags);
    vkInfo.fSampleCount = static_cast<uint32_t>(sampleCnt);
    vkInfo.fLevelCount = static_cast<uint32_t>(levelCnt);

    GrBackendRenderTarget target = GrBackendRenderTargets::MakeVk(width, height, vkInfo);
    if (!target.isValid()) {
        printf("MakeVk returned invalid target\n");
    }
    GrBackendRenderTarget* instance = new GrBackendRenderTarget(target);
    return reinterpret_cast<jlong>(instance);
}
#endif // SK_VULKAN