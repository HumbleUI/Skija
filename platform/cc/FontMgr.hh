#pragma once
#include <mutex>
#include "SkFontMgr.h"

#if defined(SK_BUILD_FOR_WIN)
#include "include/ports/SkTypeface_win.h"
#endif

#if defined(SK_BUILD_FOR_MAC)
#include "include/ports/SkFontMgr_mac_ct.h"
#endif

#if defined(SK_BUILD_FOR_UNIX)
#include "include/ports/SkFontMgr_fontconfig.h"
#endif

namespace skija {

class FontMgr_impl {
public:
    static sk_sp<SkFontMgr> RefDefault() {
        static std::once_flag once;
        static sk_sp<SkFontMgr> singleton;

        std::call_once(once, []{
            sk_sp<SkFontMgr> mgr;
#if defined(SK_BUILD_FOR_WIN)
            mgr = SkFontMgr_New_DirectWrite();
#elif defined(SK_BUILD_FOR_MAC)
            mgr = SkFontMgr_New_CoreText(nullptr);
#elif defined(SK_BUILD_FOR_UNIX)
            mgr = SkFontMgr_New_FontConfig(nullptr);
#endif
            singleton = mgr;
        });

        return singleton;
    }
};

} // namespace skija
