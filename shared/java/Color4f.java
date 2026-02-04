package io.github.humbleui.skija;

import lombok.*;

@AllArgsConstructor
@lombok.Data
@With
public class Color4f {
    public final float _r;
    public final float _g;
    public final float _b;
    public final float _a;

    public Color4f(float r, float g, float b) {
        this(r, g, b, 1f);
    }

    public Color4f(float[] rgba) {
        this(rgba[0], rgba[1], rgba[2], rgba[3]);
    }

    public Color4f(int c) {
        this((c >> 16 & 0xFF) / 255f,
             (c >> 8 & 0xFF) / 255f,
             (c & 0xFF) / 255f,
             (c >> 24 & 0xFF) / 255f);
    }

    public int toColor() {
        return ((Math.round(_a * 255f)) << 24)
          | ((Math.round(_r * 255f)) << 16)
          | ((Math.round(_g * 255f)) << 8)
          | (Math.round(_b * 255f));
    }

    public float[] flatten() {
        return new float[] {_r, _g, _b, _a};
    }

    public static float[] flattenArray(Color4f[] colors) {
        float[] arr = new float[colors.length * 4];
        for (int i = 0; i < colors.length; ++i) {
            arr[i * 4]     = colors[i]._r;
            arr[i * 4 + 1] = colors[i]._g;
            arr[i * 4 + 2] = colors[i]._b;
            arr[i * 4 + 3] = colors[i]._a;
        }
        return arr;
    }

    public Color4f premultiply() {
        if (_a == 1f)
            return this;
        return new Color4f(_r * _a, _g * _a, _b * _a, _a);
    }

    public Color4f unpremultiply() {
        if (_a == 1f)
            return this;
        if (_a == 0f)
            return new Color4f(0, 0, 0, 0);
        return new Color4f(_r / _a, _g / _a, _b / _a, _a);
    }

    /**
     * Interpolate between two unpremultiplied colors.
     *
     * @param other  color to interpolate to
     * @param t      interpolation ratio 0..1, 0 == fully this, 1 == fully other
     *
     * @return       interpolated color, unpremultiplied
     */
    public Color4f makeLerp(Color4f other, float t) {
        if (t <= 0) return this;
        if (t >= 1) return other;
        Color4f c1 = this.premultiply();
        Color4f c2 = other.premultiply();
        return new Color4f(c1._r + (c2._r - c1._r) * t,
                           c1._g + (c2._g - c1._g) * t,
                           c1._b + (c2._b - c1._b) * t,
                           c1._a + (c2._a - c1._a) * t).unpremultiply();
    }

    /**
     * Interpolate between two premultiplied colors.
     *
     * @param other  color to interpolate to
     * @param t      interpolation ratio 0..1, 0 == fully this, 1 == fully other
     *
     * @return       interpolated color, premultiplied
     */
    public Color4f makeLerpPM(Color4f other, float t) {
        if (t <= 0) return this;
        if (t >= 1) return other;
        return new Color4f(_r + (other._r - _r) * t,
                           _g + (other._g - _g) * t,
                           _b + (other._b - _b) * t,
                           _a + (other._a - _a) * t);
    }

}