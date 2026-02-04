# WIP

Added:

- `Path::fillWithPaint` #95 via @vladimirsamsonov
- `Pixmap::erase`, `Pixmap implements IHasImageInfo` #101 via @Eatgrapes
- `Color::premultiply`, `unpremultiply`, `makeLerpPM` #101 via @Eatgrapes
- `Color4f::premultiply`, `unpremultiply`, `makeLerpPM` #101 via @Eatgrapes

Changed:

- [ BREAKING ] `Pixmap::getInfo` -> `Pixmap::getImageInfo` #101 via @Eatgrapes
- [ BREAKING ] `Bitmap::peekPixels` now returns `Pixmap`, same as Skia #101 via @Eatgrapes
- `Color::makeLerp`, `Color4f::makeLerp` now produce correct results with alpha #101 via @Eatgrapes

# 0.143.8 - Jan 29, 2026

Added

- `Document` #80 #97 via @Eatgrapes

# 0.143.7 - Jan 29, 2026

Fixed

- Actually publish Android build #96 via @Eatgrapes

# 0.143.6 - Jan 26, 2026

Added:

- Android build #93 via @Eatgrapes

Fixed:

- `Path::convertConicToQuads` returning incomplete array #94

# 0.143.5 - Dec 2, 2025

Changed:

- [ BREAKING ] `GLBackendState` -> `BackendState`
- [ BREAKING ] `DirectContext::resetGL` -> `DirectContext::reset`
- [ BREAKING ] `PathEffect::Style` -> `PathEffect1DStyle`
- [ BREAKING ] `Region::Op` -> `RegionOp`
- [ BREAKING ] `PathSegmentMask` -> `PathSegmentType`, `Path::getSegmentMask` -> `Path::getSegmentTypes`

Removed:

- [ BREAKING ] `SurfaceColorFormat` (it was duplicating `ColorType` 🙈)
- [ BREAKING ] `TextStyleAttribute.FONT_EXACT` (replaced with `TextStyleAttribute::equalsByFonts`)

Fixed:

- `ColorType` to match latest Skia changes
- `ColorType.N32` value on macOS
- `SVGTag` to match latest Skia changes

Added:

- `BlendMode`: `LAST_COEFF_MODE`, `LAST_SEPARABLE_MODE`
- `ColorChannelFlag`
- `ColorType`: `BGRA_10101010_XR`, `RGBA_10X6`, `RGB_F16F16F16X`, `SRGBA_8888`, `R8_UNORM`
- `EncodedImageFormat`: `AVIF`, `JPEGXL`
- `EncodedOrigin`: `DEFAULT`
- `FramebufferFormat`: `GR_GL_LUMINANCE8_ALPHA8`, `GR_GL_RGBX8`
- `ShadowUtilsFlag`: `NONE`, `ALL`
- `SVGTag`: `FE_COMPONENT_TRANSFER`, `FE_FUNC_A`, `FE_FUNC_R`, `FE_FUNC_G`, `FE_FUNC_B`, `FE_MERGE`, `FE_MERGE_NODE`,
- `TextStyleAttribute::equalsByFonts`

# 0.143.4 - Nov 22, 2025

Fixed:

- `FontMgr::matchFamily*(null)` now returns default font

# 0.143.3 - Nov 15, 2025

Changed:

- `Managed::close` does not throw now on non-managed or already closed objects

Added:

- `ColorSpace::makeFromICCProfile`

# 0.143.2 - Nov 11, 2025

Changed:

- Re-enabled linux ARM build, thanks to @Eatgrapes #88

# 0.143.0 - Nov 7, 2025

Changed:

- Skia version m132-9ab7c2064b -> m143-da51f0d60e
- `Region::getBoundaryPath` now returns new Path instead of accepting one and modifying it
- Most ImageFilter ctors now accept an optional cropRect
- [ BREAKING ] `ShadowUtils::drawShadow` now accepts `ShadowUtilsFlag...`

Added:

- PathBuilder
- Path.make*
- Canvas::drawPathOnce (convenience, draws path and closes it immediately)
- RuntimeEffect
- RuntimeEffectBuilder
- Image::makeWithFilter, makeScaled
- ImageFilter::makeCrop, makeEmpty, makePicture, makeShader, makeBlend, makeRuntimeShader
- Blender
- Paint::getBlender, setBlender
- Shader::isOpaque, getImage, makeWithLocalMatrix, makeWithWorkingColorSpace, makeFractalNoise, makeTurbulence
- FontArguments
- Typeface::makeClone(FontArguments)
- FontCollection::defaultFallback(int, FontStyle, String, FontArguments)
- Bitmap::setColorSpace
- SaveLayerRec: FilterTileMode and ColorSpace
- Codec::isAnimated

Removed:

- [ BREAKING ] Most of mutable methods on Path (moved to PathBuilder)

# 0.132.0 - Oct 30, 2025

Changed:

- Skia version m123-f44dbc40d8 -> m132-9ab7c2064b
- Updated build to support JDK 25

Added:

- `Shaper::makeBestAvailable`

# 0.123.0 - Oct 28, 2025

Changed:

- Skia version m116-d2c211228d -> m123-f44dbc40d8
- `DirectContext::submit` returns `boolean`

Added:

- `FontMgr::makeFromFile`
- `DirectContext::flushAndSubmit`

Removed:

- [ BREAKING ] `Font::getTypefaceOrDefault`
- [ BREAKING ] `Typeface::makeDefault`
- [ BREAKING ] `Typeface::makeFromName` (use `FontMgr::matchFamilyStyle`)
- [ BREAKING ] `Typeface::makeFromFile` (use `FontMgr::makeFromFile`)
- [ BREAKING ] `Typeface::makeFromData` (use `FontMgr::makeFromData`)
- [ BREAKING ] `Surface::flush` (use `DirectContext::flush(Surface)`)
- [ BREAKING ] `Surface::flushAndSubmit` (use `DirectContext::flushAndSubmit(Surface)`)

# 0.119.3 - Jan 31, 2026

Added:

- `Path::fillPath` #95 via @vladimirsamsonov

# 0.119.2 - Jan 21, 2026

Fixed:

- `Path::convertConicToQuads` returning incomplete array #94

# 0.119.1 - Dec 2, 2025

Fixed:

- `ColorType`, `EncodedImageFormat`, `FramebufferFormat`, `SurfaceColorFormat` updated to match Skia enums
- `ColorType.N32` value on macOS

# 0.119.0 - Nov 28, 2025

Changed:

- Skia version m116-d2c211228d -> m119-fcb55886b9
- `DirectContext::submit` returns `boolean`

Added:

- `FontMgr::makeFromFile`
- `DirectContext::flushAndSubmit`

Removed:

- [ BREAKING ] `Surface::flush` (use `DirectContext::flush(Surface)`)
- [ BREAKING ] `Surface::flushAndSubmit` (use `DirectContext::flushAndSubmit(Surface)`)

# 0.116.8 - Oct 11, 2025

Fixed:

- Prevent EXCEPTION_ACCESS_VIOLATION in Canvas.getSurface() method #86 via @vladimirsamsonov

# 0.116.7 - Oct 2, 2025

Fixed:

- toString on closed RefCnt objects #85 via @vladimirsamsonov

# 0.116.6 - Sep 29, 2025

Fixed:

- Bitmap swap method #84 via @vladimirsamsonov

# 0.116.5 - Sep 23, 2025

Added:

- `StreamAsset`, `Typeface::openStream` #82 via @vladimirsamsonov

# 0.116.4 - Feb 13, 2025

Added:

- `adoptGLTextureFrom` #67 #72 #74 #75 via @EldoDebug

Fixed:

- Memory leak in `Canvas::drawTriangles` #76

# 0.116.3 - Jan 13, 2025

Changed:

- Surface and PictureRecorder cache returned Canvas object and invalidate it when owner is closed #66

Fixed:

- ColorType enum mapping #73

# 0.116.2 - Dec 8, 2023

Changed:

- Skia version m116-f44dbc40d8 -> m116-d2c211228d
- Link with EGL on Linux

# 0.116.1 - Aug 9, 2023

Fixed:

- Broken shared library unpacking #54 #56

# 0.116.0 - Aug 8, 2023

Fixed:

- Race condition in shared library unpacking #54 #56 thx @dzaima
- NPE in Typeface::makeFromName #49

Changed:

- Skia version m109-664500fa93 -> m116-f44dbc40d8
- [ BREAKING ] `Bitmap::erase(Color4f)` no longer takes ColorSpace
- [ BREAKING ] `ImageFilter::MakeMagnifier` required two more arguments: `zoomAmount` and `SamplingMode`
- `Image::makeRaster(ImageInfo, byte[], long)` -> `makeRasterFromBytes`
- `Image::makeRaster(ImageInfo, Data, long)` -> `makeRasterFromData`
- `Image::makeRasterBitmap(Bitmap)` -> `makeRasterFromBitmap`
- `Image::makeRasterPixmap(Pixmap)` -> `makeRasterFromPixmap`
- `Image::makeFromEncoded(byte[])` -> `makeDeferredFromEncodedBytes`
- `Image::encodeToData` has been replaced with `EncoderJPEG`, `EncoderPNG`, `EncoderWEBP`, plus the encoding options
- `Surface::makeRasterDirect` -> `wrapPixels`
- `Surface::makeFromBackendRenderTarget` -> `wrapBackendRenderTarget`
- `Surface::makeFromMTKView` -> `wrapMTKView`

Deprecated:

- `Surface::makeRasterN32Premul`
- `EncodedImageFormat`

Removed:

- [ BREAKING ] `ImageFilter::MakeAlphaThreshold`
- [ BREAKING ] `ImageFilter::MakePaint`
- Disabled Linux ARM64 builds until https://github.com/HumbleUI/SkiaBuild/issues/8

# 0.109.2 - Feb 27, 2022

- Lombok version updated 1.18.22 -> 1.18.26 #43 via @Glavo
- Basic linux arm64 support #44 via @Glavo

# 0.109.1 - Dec 28, 2022

Changed:

- [ BREAKING ] Windows and Linux platform artifacts now have architecture classifiers, consistent with macOS (skija-windows -> skija-windows-x64, skija-linux -> skija-linux-x64) #34 via @Glavo
- [ BREAKING ] Platform was split into Architecture and OperatingSystem #34 via @Glavo

Added:

- jmods are now built and uploaded to Github Releases #34 via @Glavo
- `skija.loadFromLibraryPath` system property to load native libraries from system locations #34 via @Glavo
- `skija.library.path` system property to load native libraries from custom location #34 via @Glavo

# 0.109.0 - Dec 15, 2022

Added:

- Canvas::quickReject(Rect), Canvas::quickReject(Path) #33 via @Algeseven

Changed:

- Skia version m106-ba6bc7d02d -> m109-664500fa93
- Ubuntu bumped to 20.04
- Java 8 is now supported #30 via @Glavo

Fixed:

- Added `Library.staticLoad()` to `BufferUtil`, `Pixmap`, `ColorType` #29
- Don’t accept null font in shaper
- Implicit long to int conversion in BreakIterator::clone
- TextLine fBreakOffsets use uint32_t instead of floats

# 0.106.0 - Sep 14, 2022

Changed:

- Skia version m105-f204b137b9 -> m106-ba6bc7d02d

Added:

- Canvas::setMatrix(Matrix44) #26 via @Algeseven
- Canvas::saveLayerAlpha
- Canvas::saveLayer(SaveLayerRec) #27
- Canvas::getSurface

# 0.105.0 - Aug 10, 2022

Changed:

- Skia version m102-32283b3f00 -> m105-f204b137b9

Added:

- TextLineRunHandler
- FontMgrRunIterator ctor that accepts languageIterator
- SamplingModeAnisotropic
- Bitmap::erase(Color4f)
- Bitmap::erase(Color4f, ColorSpace)
- Bitmap::erase(Color4f, IRect)
- Bitmap::erase(Color4f, ColorSpace, IRect)
- Bitmap::getColor4f(int, int)
- Pixmap::getColor4f(int, int)
- Canvas::getBaseProps()
- Canvas::getTopProps()

Fixed:

- Surface::makeImageSnapshot can now correctly return null #23

Removed:

- [ BREAKING ] ParagraphBuilder::setParagraphStyle

# 0.102.0 - April 21, 2022

Changed:

- Skia version m100-d5a507cafd -> m102-32283b3f00
- Cache Font::getMetrics #17

# 0.100.0 - Feb 22, 2022

Changed:

- Skia version m98-e7cf73334f -> m100-d5a507cafd
- [ BREAKING ] RuntimeEffect::makeShader lost isOpaque argument
- [ BREAKING ] C++ 14 -> 17

# 0.98.1 - Jan 5, 2022

Added:

- RuntimeEffect::makeColorFilter

Changed:

- types 0.1.0 -> 0.1.1
- Removed unnecessary LineMetrics UTF-8 to UTF-16 conversion #9


# 0.98.0 - Dec 20, 2021

Changed:

- Skia version m96-2f1f21b8a9 -> m98-e7cf73334f
- groupId io.github.humbleui.skija -> io.github.humbleui
- [ BREAKING ] Moved IPoint, IRange, IRect, Point, Point3, Rect, RRect to `io.github.humbleui.types`

# 0.96.0 - Nov 3, 2021

Changed:

- Skia version m93-87e8842e8c -> m96-2f1f21b8a9
- Renamed package org.jetbrains.skija -> io.github.humbleui.skija
- Renamed maven group org.jetbrains.skija -> io.github.humbleui.skija
- Migrated to Maven Central

# 0.93.4 - Sep 20, 2021

Added:

- org.jetbrains.skija.resources.*
  - ResourceProvider
  - FileResourceProvider
  - DataURIResourceProviderProxy
  - CachingResourceProvider
- AnimationBuilder::setResourceProvider #129

# 0.93.1 - Aug 11, 2021

Added:

- ShapingOptions
  - Groups together FontMgr, FontFeature[], leftToRight
  - Adds approximateSpaces and approximatePunctuation
- FontMgrRunIterator(String, Font)
- Shaper::shapeLine(String, Font)

Changed:

- AnimationDisposalMethod -> AnimationDisposalMode

Signature updated from:

  - TextLine::make(String, Font, FontFeature[], boolean)
  - FontMgrRunIterator(ManagedString, boolean, Font, FontMgr)
  - FontMgrRunIterator(String, Font, FontMgr)
  - Shaper::shape(String, Font, boolean, float, Point)
  - Shaper::shape(String, Font, FontMgr, FontFeature[], boolean, float, RunHandler)
  - Shaper::shape(String, Iterator<FontRun>, Iterator<BidiRun>, Iterator<ScriptRun>, Iterator<LanguageRun>, FontFeature[], float width, RunHandler)
  - Shaper::shape(ManagedString, Iterator<FontRun>, Iterator<BidiRun>, Iterator<ScriptRun>, Iterator<LanguageRun>, FontFeature[], float width, RunHandler)
  - Shaper::shapeLine(String, Font, FontFeature[], boolean)

to:

  - TextLine::make(String, Font, ShapingOptions)
  - FontMgrRunIterator(ManagedString, boolean, Font, ShapingOptions)
  - FontMgrRunIterator(String, Font, ShapingOptions)
  - Shaper::shape(String, Font, ShapingOptions, float, Point)
  - Shaper::shape(String, Font, FontMgr, ShapingOptions, float, RunHandler)
  - Shaper::shape(String, Iterator<FontRun>, Iterator<BidiRun>, Iterator<ScriptRun>, Iterator<LanguageRun>, ShapingOptions, float width, RunHandler)
  - Shaper::shape(ManagedString, Iterator<FontRun>, Iterator<BidiRun>, Iterator<ScriptRun>, Iterator<LanguageRun>, ShapingOptions, float width, RunHandler)
  - Shaper::shapeLine(String, Font, ShapingOptions)

# 0.93.0 - Aug 10, 2021

Changed:

- Skia version m92-d9b8efde6d -> m93-87e8842e8c
- Paint::getHash is no longer structural

Removed:

- Shader::makeLerp #114 thx @cqjjjzr
- Paint::getFilterQuality, Paint::setFilterQuality #114 thx @cqjjjzr

# 0.92.30 - Aug 9, 2021

Changed:

- Skia version m92-f46c37ba85-2 -> m92-d9b8efde6d
- Linking freetype dynamically on Linux to avoid conflicts with JavaFX #113

# 0.92.29 - Aug 7, 2021

Added:

- RuntimeEffect #120 #124 thx @Vechro

# 0.92.22 - July 29, 2021

Added:

- DirectContext::submit(bool syncCPU), thx @EgorOrachyov
- Direct3D Context #121 thx @EgorOrachyov
- SkData->ByteBuffer #118 thx @cqjjjzr
- SkImage bindings for SkPixmap #117 thx @cqjjjzr

# 0.92.15 - July 16, 2021

Changed:

- Optimized use of Enum.values() #112
- Fixed source jar build on non-English Windows

Added:

- SkPixmap bindings, SkPixmap-related methods for SkSurface #116 thx @cqjjjzr

# 0.92.12 - June 22, 2021

Lowered minimal required version to Java 9.
Support for Java 9 modules (#110 #111 thx @comtel2000):

- org.jetbrains.skija.shared
- org.jetbrains.skija.windows
- org.jetbrains.skija.linux
- org.jetbrains.skija.macos.x64
- org.jetbrains.skija.macos.arm64

Code reorganization:

- removed maven
- flattened source dirs
  - native/src → platform/cc
  - shared/src/main/java/org/jetbrains/skija → shared/java
  - shared/src/test/java/org/jetbrains/skija → tests/java
  - added platform/java-{platform}

# 0.92.1 - June 7, 2021

Skia version m92-f46c37ba85 -> m92-f46c37ba85-2

Changed:

- [ BREAKING ] SVGDOM::setContainerSize() does not scale SVG anymore (https://bugs.chromium.org/p/skia/issues/detail?id=11144)

Added:

- SVGDOM::getRoot()
- SVGSVG (root SVG node)

# 0.92.0 - June 4, 2021

Skia version m91-b99622c05a -> m92-f46c37ba85

Changed:

- paragraph.Shadow::blurRadius -> paragraph.Shadow::blurSigma

Removed:

- PathEffect::computeFastBounds

# 0.91.8 - June 1, 2021

Fixed:

- Typeface.getFamilyNames return type (String[] -> FontFamilyName[]) #108
- Canvas.resetMatrix argument (Matrix33 -> None) #109, thx @AnzerWall

# 0.91.6 - May 21, 2021

Added:

- Canvas.drawRectShadow
- Canvas.drawRectShadowNoclip
- Rect.inflate
- Rect.isEmpty
- Managed.isClosed

Updated:

- org.jetbrains:annotations from 19.0.0 to 20.1.0

# 0.91.4 - May 11, 2021

Added:

- Surface::makeFromMTKView
- Optionally load dll/so/dylib from current dir

# 0.91.3 - May 4, 2021

Added:

- DirectContext::makeMetal
- BackendRenderTarget::makeMetal

# 0.91.2 - Apr 29, 2021

Fixed:

- `-Xcheck:jni` warnings #70

# 0.91.1 - Apr 28, 2021

Fixed:

- NPE in TextLine::getIntercepts

# 0.91.0 - Apr 28, 2021

Skia version m90-adbb69cd7f-2 -> m91-b99622c05a

Changed:

- All variants of Picture.makeShader now take extra FilterMode argument

# 0.90.16 - Apr 23, 2021

Added:

- Matrix33.rotate(deg, pivot)

# 0.90.14 - Apr 22, 2021

New FontRunIter for TextLine/TextBlob, groups grapheme clusters together correctly

# 0.90.9 - Apr 20, 2021

Lombok 1.18.18 -> 1.18.20 (support for Java 16)

# 0.90.6 - Apr 16, 2021

Added:

- TextLine.getIntercepts()

# 0.90.3 - Mar 26, 2021

Skia version m90-adbb69cd7f -> m90-adbb69cd7f-2

Changed:

- Fixed exception during reporting double close in Managed #102
- Fixed for locale-dependent SVGCanvas serialization https://skbug.com/11794

# 0.90.2 - Mar 25, 2021

Changed:

- Fixed Skottie linking on Linux

# 0.90.0 - March 19, 2021

Skia version m89-109bfc9052 -> m90-adbb69cd7f

Added:

- Canvas.drawImageRect(..., SamplingMode, ...)
- Canvas.drawImageNine()

Changed:

- SamplingMode.FilterMipmap -> FilterMipmap
- SamplingMode.CubicResampler -> CubicResampler
- ImageFilter.makeXfermode() -> ImageFilter.makeBlend()
- ImageFilter.makeImage(..., FilterQuality -> SamplingMode)
- ImageFilter.makeMatrixTransform(..., FilterQuality -> SamplingMode)

Removed:

- Canvas.drawImageIRect()
- Canvas.drawBitmap()
- Canvas.drawBitmapRect()
- Canvas.drawBitmapIRect()

(See https://bugs.chromium.org/p/skia/issues/detail?id=11764#c1)

# 0.89.39 - March 18, 2021

Lombok 1.18.16 -> 1.18.18

# 0.89.38 - March 17, 2021

Added:

- BreakIterator
- U16String

# 0.89.37 - March 11, 2021

Added:

- Codec
- AnimationFrameInfo
- AnimationDisposalMethod
