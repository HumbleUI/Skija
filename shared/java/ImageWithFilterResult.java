package io.github.humbleui.skija;

import lombok.*;
import lombok.Data;
import org.jetbrains.annotations.*;
import io.github.humbleui.types.*;

/**
 * Result of filtering an image with {@link Image#makeWithFilter}.
 */
@Data @AllArgsConstructor
public class ImageWithFilterResult implements AutoCloseable {
    /**
     * The filtered image.
     */
    @NotNull
    public final Image _image;

    /**
     * The actual bounds of the filtered image.
     */
    @NotNull
    public final IRect _subset;

    /**
     * Translation offset for the returned image.
     */
    @NotNull
    public final IPoint _offset;

    @Override
    public void close() {
        _image.close();
    }
}
