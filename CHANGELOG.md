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