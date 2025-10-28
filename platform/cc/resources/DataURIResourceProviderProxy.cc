#include <jni.h>
#include "../interop.hh"
#include "SkResources.h"

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_resources_DataURIResourceProviderProxy__1nMake
  (JNIEnv* env, jclass jclass, jlong resourceProviderPtr, jboolean predecode) {
    skresources::ResourceProvider* resourceProvider = reinterpret_cast<skresources::ResourceProvider*>(static_cast<uintptr_t>(resourceProviderPtr));
    auto strategy = predecode ? skresources::ImageDecodeStrategy::kPreDecode : skresources::ImageDecodeStrategy::kLazyDecode;
    auto instance = skresources::DataURIResourceProviderProxy::Make(sk_ref_sp(resourceProvider), strategy);
    return ptrToJlong(instance.release());
}