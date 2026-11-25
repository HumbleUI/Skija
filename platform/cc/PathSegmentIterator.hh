#pragma once
#include "SkPath.h"

namespace skija {
    struct PathSegmentIter {
        PathSegmentIter(const SkPath& path, bool forceClose)
            : fPath(path), fIter(fPath, forceClose), fVerbs(fPath.verbs()), fForceClose(forceClose) {}
        PathSegmentIter(const PathSegmentIter&) = delete;
        PathSegmentIter& operator=(const PathSegmentIter&) = delete;

        SkPath::Verb next(SkPoint pts[4]) {
            SkPath::Verb verb = fIter.next(pts);
            if (verb == SkPath::kMove_Verb)
                fMoveTo = pts[0];
            else if (verb == SkPath::kClose_Verb)
                pts[0] = fMoveTo; // after NaN, SkPath::Iter returns CLOSE without points
            bool closeLine = verb == SkPath::kLine_Verb && fIter.isCloseLine();
            bool forcedClose = verb == SkPath::kClose_Verb && !(fIndex < fVerbs.size() && fVerbs[fIndex] == SkPathVerb::kClose);
            if (verb != SkPath::kDone_Verb && !closeLine && !forcedClose)
                fIndex++;
            return verb;
        }

        bool isClosedContour() {
            if (fIndex >= fVerbs.size())
                return false;
            if (fForceClose)
                return true;
            size_t start = fVerbs[fIndex] == SkPathVerb::kMove ? fIndex + 1 : fIndex;
            if (!fScanned || start > fScanEnd) {
                fScanEnd = start;
                while (fScanEnd < fVerbs.size() && fVerbs[fScanEnd] != SkPathVerb::kMove && fVerbs[fScanEnd] != SkPathVerb::kClose)
                    fScanEnd++;
                fScanned = true;
            }
            return fScanEnd < fVerbs.size() && fVerbs[fScanEnd] == SkPathVerb::kClose;
        }

        SkPath                   fPath; // must be declared before fIter: fIter points into fPath's storage
        SkPath::Iter             fIter;
        SkSpan<const SkPathVerb> fVerbs;
        bool                     fForceClose;
        size_t                   fIndex = 0;
        size_t                   fScanEnd = 0;
        bool                     fScanned = false;
        SkPoint                  fMoveTo = {0, 0};
    };
}
