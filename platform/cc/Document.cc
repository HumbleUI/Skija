#include <iostream>
#include <jni.h>
#include "interop.hh"
#include "include/core/SkDocument.h"
#include "include/docs/SkPDFDocument.h"
#include "include/core/SkStream.h"
#include "include/encode/SkJpegEncoder.h"
#include "include/codec/SkJpegDecoder.h"
#include <ctime>

bool encode_jpeg(SkWStream* stream, const SkPixmap& pixmap, int quality) {
    SkJpegEncoder::Options options;
    options.fQuality = quality;
    return SkJpegEncoder::Encode(stream, pixmap, options);
}

std::unique_ptr<SkCodec> decode_jpeg(sk_sp<SkData> data) {
    return SkJpegDecoder::Decode(data, nullptr, nullptr);
}

void toSkDateTime(jlong ms, SkPDF::DateTime* dateTime) {
    if (ms == 0) {
        return;
    }
    time_t seconds = static_cast<time_t>(ms / 1000);
    struct tm t;
#ifdef _WIN32
    gmtime_s(&t, &seconds);
#else
    gmtime_r(&seconds, &t);
#endif
    dateTime->fTimeZoneMinutes = 0;
    dateTime->fYear       = t.tm_year + 1900;
    dateTime->fMonth      = t.tm_mon + 1;
    dateTime->fDayOfWeek  = t.tm_wday;
    dateTime->fDay        = t.tm_mday;
    dateTime->fHour       = t.tm_hour;
    dateTime->fMinute     = t.tm_min;
    dateTime->fSecond     = t.tm_sec;
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_Document__1nMakePDF
  (JNIEnv* env, jclass jclass, jlong streamPtr, jstring title, jstring author, jstring subject, jstring keywords, jstring creator, jstring producer, jlong creationDate, jlong modifiedDate) {
    SkWStream* stream = reinterpret_cast<SkWStream*>(static_cast<uintptr_t>(streamPtr));
    SkPDF::Metadata metadata;
    if (title) metadata.fTitle = *skString(env, title);
    if (author) metadata.fAuthor = *skString(env, author);
    if (subject) metadata.fSubject = *skString(env, subject);
    if (keywords) metadata.fKeywords = *skString(env, keywords);
    if (creator) metadata.fCreator = *skString(env, creator);
    if (producer) metadata.fProducer = *skString(env, producer);
    
    metadata.jpegEncoder = encode_jpeg;
    metadata.jpegDecoder = decode_jpeg;

    toSkDateTime(creationDate, &metadata.fCreation);
    toSkDateTime(modifiedDate, &metadata.fModified);
    
    sk_sp<SkDocument> document = SkPDF::MakeDocument(stream, metadata);
    return reinterpret_cast<jlong>(document.release());
}

extern "C" JNIEXPORT jlong JNICALL Java_io_github_humbleui_skija_Document__1nBeginPage
  (JNIEnv* env, jclass jclass, jlong ptr, jfloat width, jfloat height) {
    SkDocument* document = reinterpret_cast<SkDocument*>(static_cast<uintptr_t>(ptr));
    SkCanvas* canvas = document->beginPage(width, height);
    return reinterpret_cast<jlong>(canvas);
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_Document__1nEndPage
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkDocument* document = reinterpret_cast<SkDocument*>(static_cast<uintptr_t>(ptr));
    document->endPage();
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_Document__1nClose
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkDocument* document = reinterpret_cast<SkDocument*>(static_cast<uintptr_t>(ptr));
    document->close();
}

extern "C" JNIEXPORT void JNICALL Java_io_github_humbleui_skija_Document__1nAbort
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkDocument* document = reinterpret_cast<SkDocument*>(static_cast<uintptr_t>(ptr));
    document->abort();
}
