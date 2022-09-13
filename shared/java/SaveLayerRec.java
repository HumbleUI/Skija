package io.github.humbleui.skija;

import io.github.humbleui.types.*;
import lombok.Data;
import org.jetbrains.annotations.*;

/**
 * Contains the state used to create the layer
 */
@Data
public class SaveLayerRec {
    @ApiStatus.Internal @Nullable public final Rect        _bounds;
    @ApiStatus.Internal @Nullable public final Paint       _paint;
    @ApiStatus.Internal @Nullable public final ImageFilter _backdrop;
    @ApiStatus.Internal           public final int         _flags;

    /**
     * Sets bounds, paint, and backdrop to null. Clears flags.
     */
    public SaveLayerRec() {
        this(null, null, null, new SaveLayerRecFlag[0]);
    }

    /** 
     * Sets bounds and paint. Sets backdrop to null, clears flags
     *
     * @param bounds    layer dimensions; may be null
     * @param paint     applied to layer when overlaying prior layer; may be null
     */
    public SaveLayerRec(@Nullable Rect bounds, @Nullable Paint paint) {
        this(bounds, paint, null, new SaveLayerRecFlag[0]);
    }

    /** 
     * Sets bounds, paint, and flags. Sets backdrop to null
     *
     * @param bounds    layer dimensions; may be null
     * @param paint     applied to layer when overlaying prior layer; may be null
     * @param flags     options to modify layer
     */
    public SaveLayerRec(@Nullable Rect bounds, @Nullable Paint paint, SaveLayerRecFlag... flags) {
        this(bounds, paint, null, flags);
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
        this(bounds, paint, backdrop, new SaveLayerRecFlag[0]);
    }

    /** 
     * Sets bounds, paint, backdrop and flags
     *
     * @param bounds    layer dimensions; may be null
     * @param paint     applied to layer when overlaying prior layer; may be null
     * @param backdrop  If not null, this causes the current layer to be filtered by
     *                  backdrop, and then drawn into the new layer
     *                  (respecting the current clip).
     *                  If null, the new layer is initialized with transparent-black.
     * @param flags     options to modify layer
     */
     public SaveLayerRec(Rect bounds, Paint paint, ImageFilter backdrop, SaveLayerRecFlag... flags) {
        _bounds = bounds;
        _paint = paint;
        _backdrop = backdrop;
        int mask = 0;
        for (SaveLayerRecFlag flag: flags)
            mask |= flag._flag;
        _flags = mask;
    }
}
