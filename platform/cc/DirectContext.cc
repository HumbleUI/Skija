#include <iostream>
#include <jni.h>
#include "include/gpu/ganesh/GrTypes.h"
#include "include/gpu/ganesh/GrDirectContext.h"
#include "include/gpu/ganesh/gl/GrGLDirectContext.h"

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_DirectContext__1nMakeGL
  (JNIEnv* env, jclass jclass) {
    return reinterpret_cast<jlong>(GrDirectContexts::MakeGL().release());
}

#ifdef SK_METAL
#include "include/gpu/ganesh/mtl/GrMtlBackendContext.h"
#include "include/gpu/ganesh/mtl/GrMtlDirectContext.h"

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_DirectContext__1nMakeMetal
  (JNIEnv* env, jclass jclass, long devicePtr, long queuePtr) {
    GrMtlBackendContext backendContext = {};
    GrMTLHandle device = reinterpret_cast<GrMTLHandle>(static_cast<uintptr_t>(devicePtr));
    GrMTLHandle queue = reinterpret_cast<GrMTLHandle>(static_cast<uintptr_t>(queuePtr));
    backendContext.fDevice.retain(device);
    backendContext.fQueue.retain(queue);
    sk_sp<GrDirectContext> instance = GrDirectContexts::MakeMetal(backendContext);
    return reinterpret_cast<jlong>(instance.release());
}
#endif

#ifdef SK_DIRECT3D
#include "include/gpu/ganesh/d3d/GrD3DBackendContext.h"

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_DirectContext__1nMakeDirect3D
  (JNIEnv* env, jclass jclass, jlong adapterPtr, jlong devicePtr, jlong queuePtr) {
    GrD3DBackendContext backendContext = {};
    IDXGIAdapter1* adapter = reinterpret_cast<IDXGIAdapter1*>(static_cast<uintptr_t>(adapterPtr));
    ID3D12Device* device = reinterpret_cast<ID3D12Device*>(static_cast<uintptr_t>(devicePtr));
    ID3D12CommandQueue* queue = reinterpret_cast<ID3D12CommandQueue*>(static_cast<uintptr_t>(queuePtr));
    backendContext.fAdapter.retain(adapter);
    backendContext.fDevice.retain(device);
    backendContext.fQueue.retain(queue);
    sk_sp<GrDirectContext> instance = GrDirectContext::MakeDirect3D(backendContext);
    return reinterpret_cast<jlong>(instance.release());
}
#endif //SK_DIRECT3D 

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_DirectContext__1nFlush
  (JNIEnv* env, jclass jclass, jlong ptr) {
    GrDirectContext* context = reinterpret_cast<GrDirectContext*>(static_cast<uintptr_t>(ptr));
    context->flush(GrFlushInfo());
}

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_DirectContext__1nSubmit
  (JNIEnv* env, jclass jclass, jlong ptr, jboolean syncCpu) {
    GrDirectContext* context = reinterpret_cast<GrDirectContext*>(static_cast<uintptr_t>(ptr));
    return context->submit(syncCpu ? GrSyncCpu::kYes : GrSyncCpu::kNo);
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_DirectContext__1nFlushAndSubmit
  (JNIEnv* env, jclass jclass, jlong ptr, jboolean syncCpu) {
    GrDirectContext* context = reinterpret_cast<GrDirectContext*>(static_cast<uintptr_t>(ptr));
    context->flushAndSubmit(syncCpu ? GrSyncCpu::kYes : GrSyncCpu::kNo);
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_DirectContext__1nFlushSurface
  (JNIEnv* env, jclass jclass, jlong ptr, jlong surfacePtr) {
    GrDirectContext* context = reinterpret_cast<GrDirectContext*>(static_cast<uintptr_t>(ptr));
    SkSurface* surface = reinterpret_cast<SkSurface*>(static_cast<uintptr_t>(surfacePtr));
    context->flush(surface);
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_DirectContext__1nFlushAndSubmitSurface
  (JNIEnv* env, jclass jclass, jlong ptr, jlong surfacePtr, jboolean syncCpu) {
    GrDirectContext* context = reinterpret_cast<GrDirectContext*>(static_cast<uintptr_t>(ptr));
    SkSurface* surface = reinterpret_cast<SkSurface*>(static_cast<uintptr_t>(surfacePtr));
    context->flushAndSubmit(surface, syncCpu ? GrSyncCpu::kYes : GrSyncCpu::kNo);
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_DirectContext__1nReset
  (JNIEnv* env, jclass jclass, jlong ptr, jint flags) {
    GrDirectContext* context = reinterpret_cast<GrDirectContext*>(static_cast<uintptr_t>(ptr));
    context->resetContext((uint32_t) flags);
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_DirectContext__1nAbandon
  (JNIEnv* env, jclass jclass, jlong ptr, jint flags) {
    GrDirectContext* context = reinterpret_cast<GrDirectContext*>(static_cast<uintptr_t>(ptr));
    context->abandonContext();
}

