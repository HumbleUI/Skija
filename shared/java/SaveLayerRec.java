package io.github.humbleui.skija;

import io.github.humbleui.types.*;
import lombok.Data;
import org.jetbrains.annotations.*;

/**
 * Contains the state used to create the layer
 */
@Data
public class SaveLayerRec {
    @ApiStatus.Internal @Nullable public final Rect           _bounds;
    @ApiStatus.Internal @Nullable public final Paint          _paint;
    @ApiStatus.Internal @NotNull  public final ImageFilter[]  _filters;
    @ApiStatus.Internal @Nullable public final ImageFilter    _backdrop;
    @ApiStatus.Internal @NotNull  public final FilterTileMode _tileMode;
    @ApiStatus.Internal @Nullable public final ColorSpace     _colorSpace;
    @ApiStatus.Internal           public final int            _flags;

    /**
     * Sets bounds, paint, and backdrop to null. Clears flags.
     */
    public SaveLayerRec() {
        this(null, null, null, FilterTileMode.CLAMP, null, new SaveLayerRecFlag[0]);
    }

    /** 
     * Sets bounds and paint. Sets backdrop to null, clears flags
     *
     * @param bounds    layer dimensions; may be null
     * @param paint     applied to layer when overlaying prior layer; may be null
     */
    public SaveLayerRec(@Nullable Rect bounds, @Nullable Paint paint) {
        this(bounds, paint, null, FilterTileMode.CLAMP, null, new SaveLayerRecFlag[0]);
    }

    /** 
     * Sets bounds, paint, and flags. Sets backdrop to null
     *
     * @param bounds    layer dimensions; may be null
     * @param paint     applied to layer when overlaying prior layer; may be null
     * @param flags     options to modify layer
     */
    public SaveLayerRec(@Nullable Rect bounds, @Nullable Paint paint, SaveLayerRecFlag... flags) {
        this(bounds, paint, null, FilterTileMode.CLAMP, null, flags);
    }

    /** 
     * Sets bounds, paint, and backdrop
     *
     * @param bounds    layer dimensions; may be null
     * @param paint     applied to layer when overlaying prior layer; may be null
     * @param backdrop  If not null, this causes the current layer to be filtered by
     *                  backdrop, and then drawn into the new layer
     *                  (respecting the current clip).
     *                  If null, the new layer is initialized with transparent-black.
     */
    public SaveLayerRec(@Nullable Rect bounds, @Nullable Paint paint, @Nullable ImageFilter backdrop) {
        this(bounds, paint, backdrop, FilterTileMode.CLAMP, null, new SaveLayerRecFlag[0]);
    }

    /**
     * Sets bounds, paint, backdrop, and flags
     *
     * @param bounds     layer dimensions; may be null
     * @param paint      applied to layer when overlaying prior layer; may be null
     * @param backdrop   If not null, this causes the current layer to be filtered by
     *                   backdrop, and then drawn into the new layer
     *                   (respecting the current clip).
     *                   If null, the new layer is initialized with transparent-black.
     * @param flags      options to modify layer
     */
    public SaveLayerRec(@Nullable Rect bounds, @Nullable Paint paint, @Nullable ImageFilter backdrop, SaveLayerRecFlag... flags) {
        this(bounds, paint, backdrop, FilterTileMode.CLAMP, null, flags);
    }

    /**
     * Sets bounds, paint, backdrop, color space and flags
     *
     * @param bounds     layer dimensions; may be null
     * @param paint      applied to layer when overlaying prior layer; may be null
     * @param backdrop   If not null, this causes the current layer to be filtered by
     *                   backdrop, and then drawn into the new layer
     *                   (respecting the current clip).
     *                   If null, the new layer is initialized with transparent-black.
     * @param colorSpace If not null, when the layer is restored, a color space
     *                   conversion will be applied from this color space to the parent's
     *                   color space. The restore paint and backdrop filters will be
     *                   applied in this color space. If null, the new layer will
     *                   inherit the color space from its parent.
     * @param flags      options to modify layer
     */
    public SaveLayerRec(@Nullable Rect bounds,
                        @Nullable Paint paint,
                        @Nullable ImageFilter backdrop,
                        @Nullable ColorSpace colorSpace,
                        SaveLayerRecFlag... flags) {
        this(bounds, paint, backdrop, FilterTileMode.CLAMP, colorSpace, flags);
    }

    /** 
     * Sets bounds, paint, backdrop, tile mode, color space and flags
     *
     * @param bounds     layer dimensions; may be null
     * @param paint      applied to layer when overlaying prior layer; may be null
     * @param backdrop   If not null, this causes the current layer to be filtered by
     *                   backdrop, and then drawn into the new layer
     *                   (respecting the current clip).
     *                   If null, the new layer is initialized with transparent-black.
     * @param tileMode   If the 'backdrop' is not null, or 'flags' has
     *                   {@link SaveLayerRecFlag#INIT_WITH_PREVIOUS} set, this tile
     *                   mode is used when the new layer would read outside the backdrop
     *                   image's available content.
     * @param colorSpace If not null, when the layer is restored, a color space
     *                   conversion will be applied from this color space to the parent's
     *                   color space. The restore paint and backdrop filters will be
     *                   applied in this color space. If null, the new layer will
     *                   inherit the color space from its parent.
     * @param flags      options to modify layer
     */
     public SaveLayerRec(@Nullable Rect bounds,
                         @Nullable Paint paint,
                         @Nullable ImageFilter backdrop,
                         @NotNull FilterTileMode tileMode,
                         @Nullable ColorSpace colorSpace,
                         SaveLayerRecFlag... flags) {
        _bounds = bounds;
        _paint = paint;
        _filters = new ImageFilter[0];
        _backdrop = backdrop;
        _tileMode = tileMode;
        _colorSpace = colorSpace;
        int mask = 0;
        for (SaveLayerRecFlag flag: flags)
            mask |= flag._flag;
        _flags = mask;
    }

    @ApiStatus.Internal
    public SaveLayerRec(@Nullable Rect bounds,
                        @Nullable Paint paint,
                        @NotNull ImageFilter[] filters,
                        @Nullable ImageFilter backdrop,
                        @NotNull FilterTileMode tileMode,
                        @Nullable ColorSpace colorSpace,
                        int flags) {
        _bounds = bounds;
        _paint = paint;
        _filters = filters.clone();
        _backdrop = backdrop;
        _tileMode = tileMode;
        _colorSpace = colorSpace;
        _flags = flags;
    }

    /**
     * Returns a copy of the filter array. Null entries draw the unfiltered layer content.
     */
    @NotNull
    public ImageFilter[] getFilters() {
        return _filters.clone();
    }

    /**
     * <p>Returns a layer description with filters applied when the layer is restored.
     * Each filter processes the same layer content, and the results are drawn in array order
     * using the restore paint. The filters are not chained together.</p>
     *
     * <p>A null entry draws the unfiltered layer content. An empty array uses the restore
     * paint's image filter, if any. A non-empty array cannot be combined with an image filter
     * on the restore paint. At most 16 filters are supported.</p>
     *
     * <p>The array is copied, but the filters are not. Keep them open until
     * {@link Canvas#saveLayer(SaveLayerRec)} returns.</p>
     *
     * @param filters  filters to draw in order, including optional null entries
     * @return         a new layer description with the same bounds, paint, backdrop and flags
     */
    @NotNull @Contract("_ -> new")
    public SaveLayerRec withFilters(@NotNull ImageFilter... filters) {
        return new SaveLayerRec(_bounds, _paint, filters, _backdrop, _tileMode, _colorSpace, _flags);
    }
}
