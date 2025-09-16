#include <jni.h>
#include "interop.hh"
#include "SkStream.h"

static void deleteStreamAsset(SkStreamAsset* stream) {
    delete stream;
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_StreamAsset__1nGetFinalizer(JNIEnv* env, jclass clazz) {
    return reinterpret_cast<jlong>(&deleteStreamAsset);
}

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_StreamAsset__1nIsAtEnd(JNIEnv* env, jclass clazz, jlong ptr) {
    SkStreamAsset* stream = reinterpret_cast<SkStreamAsset*>(static_cast<uintptr_t>(ptr));
    return stream->isAtEnd();
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_StreamAsset__1nRead(JNIEnv* env, jclass clazz, jlong ptr, jbyteArray buffer, jint size) {
    SkStreamAsset* stream = reinterpret_cast<SkStreamAsset*>(static_cast<uintptr_t>(ptr));
    jbyte* buf = env->GetByteArrayElements(buffer, nullptr);
    size_t bytesRead = stream->read(buf, size);
    env->ReleaseByteArrayElements(buffer, buf, 0);
    return static_cast<jint>(bytesRead);
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_StreamAsset__1nPeek(JNIEnv* env, jclass clazz, jlong ptr, jbyteArray buffer, jint size) {
    SkStreamAsset* stream = reinterpret_cast<SkStreamAsset*>(static_cast<uintptr_t>(ptr));
    jbyte* buf = env->GetByteArrayElements(buffer, nullptr);
    size_t bytesPeeked = stream->peek(buf, size);
    env->ReleaseByteArrayElements(buffer, buf, 0);
    return static_cast<jint>(bytesPeeked);
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_StreamAsset__1nSkip(JNIEnv* env, jclass clazz, jlong ptr, jint size) {
    SkStreamAsset* stream = reinterpret_cast<SkStreamAsset*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>(stream->skip(size));
}

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_StreamAsset__1nRewind(JNIEnv* env, jclass clazz, jlong ptr) {
    SkStreamAsset* stream = reinterpret_cast<SkStreamAsset*>(static_cast<uintptr_t>(ptr));
    return stream->rewind();
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_StreamAsset__1nDuplicate(JNIEnv* env, jclass clazz, jlong ptr) {
    SkStreamAsset* stream = reinterpret_cast<SkStreamAsset*>(static_cast<uintptr_t>(ptr));
    std::unique_ptr<SkStreamAsset> duplicate = stream->duplicate();
    return reinterpret_cast<jlong>(duplicate.release());
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_StreamAsset__1nFork(JNIEnv* env, jclass clazz, jlong ptr) {
    SkStreamAsset* stream = reinterpret_cast<SkStreamAsset*>(static_cast<uintptr_t>(ptr));
    std::unique_ptr<SkStreamAsset> fork = stream->fork();
    return reinterpret_cast<jlong>(fork.release());
}

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_StreamAsset__1nHasPosition(JNIEnv* env, jclass clazz, jlong ptr) {
    SkStreamAsset* stream = reinterpret_cast<SkStreamAsset*>(static_cast<uintptr_t>(ptr));
    return stream->hasPosition();
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_StreamAsset__1nGetPosition(JNIEnv* env, jclass clazz, jlong ptr) {
    SkStreamAsset* stream = reinterpret_cast<SkStreamAsset*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>(stream->getPosition());
}

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_StreamAsset__1nSeek(JNIEnv* env, jclass clazz, jlong ptr, jint position) {
    SkStreamAsset* stream = reinterpret_cast<SkStreamAsset*>(static_cast<uintptr_t>(ptr));
    return stream->seek(position);
}

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_StreamAsset__1nMove(JNIEnv* env, jclass clazz, jlong ptr, jint offset) {
    SkStreamAsset* stream = reinterpret_cast<SkStreamAsset*>(static_cast<uintptr_t>(ptr));
    return stream->move(offset);
}

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_humbleui_skija_StreamAsset__1nHasLength(JNIEnv* env, jclass clazz, jlong ptr) {
    SkStreamAsset* stream = reinterpret_cast<SkStreamAsset*>(static_cast<uintptr_t>(ptr));
    return stream->hasLength();
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_humbleui_skija_StreamAsset__1nGetLength(JNIEnv* env, jclass clazz, jlong ptr) {
    SkStreamAsset* stream = reinterpret_cast<SkStreamAsset*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>(stream->getLength());
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_StreamAsset__1nGetMemoryBase(JNIEnv* env, jclass clazz, jlong ptr) {
    SkStreamAsset* stream = reinterpret_cast<SkStreamAsset*>(static_cast<uintptr_t>(ptr));
    return reinterpret_cast<jlong>(stream->getMemoryBase());
}
