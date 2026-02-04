package io.github.humbleui.skija;

public class Color {
    public static int premultiply(int color) {
        int a = getA(color);
        if (a == 255) return color;
        if (a == 0) return 0;
        int r = (getR(color) * a + 128); r = (r + (r >> 8)) >> 8;
        int g = (getG(color) * a + 128); g = (g + (g >> 8)) >> 8;
        int b = (getB(color) * a + 128); b = (b + (b >> 8)) >> 8;
        return makeARGB(a, r, g, b);
    }

    public static int unpremultiply(int color) {
        int a = getA(color);
        if (a == 255 || a == 0) return color;
        int r = Math.min(255, (getR(color) * 255 + a / 2) / a);
        int g = Math.min(255, (getG(color) * 255 + a / 2) / a);
        int b = Math.min(255, (getB(color) * 255 + a / 2) / a);
        return makeARGB(a, r, g, b);
    }

    /**
     * Interpolates between two unpremultiplied colors.
     *
     * @param c1  color to interpolate from
     * @param c2  color to interpolate to
     * @param t   interpolation ratio 0..1, 0 == fully c1, 1 == fully c2
     *
     * @return    interpolated color, unpremultiplied
     */
    public static int makeLerp(int c1, int c2, float t) {
        if (t <= 0) return c1;
        if (t >= 1) return c2;

        int a1 = getA(c1);
        int r1 = getR(c1);
        int g1 = getG(c1);
        int b1 = getB(c1);

        int a2 = getA(c2);
        int r2 = getR(c2);
        int g2 = getG(c2);
        int b2 = getB(c2);

        if (a1 != 255) {
            r1 = (r1 * a1 + 128); r1 = (r1 + (r1 >> 8)) >> 8;
            g1 = (g1 * a1 + 128); g1 = (g1 + (g1 >> 8)) >> 8;
            b1 = (b1 * a1 + 128); b1 = (b1 + (b1 >> 8)) >> 8;
        }
        if (a2 != 255) {
            r2 = (r2 * a2 + 128); r2 = (r2 + (r2 >> 8)) >> 8;
            g2 = (g2 * a2 + 128); g2 = (g2 + (g2 >> 8)) >> 8;
            b2 = (b2 * a2 + 128); b2 = (b2 + (b2 >> 8)) >> 8;
        }

        float a = a1 + (a2 - a1) * t;
        float r = r1 + (r2 - r1) * t;
        float g = g1 + (g2 - g1) * t;
        float b = b1 + (b2 - b1) * t;

        int ia = Math.round(a);
        if (ia <= 0) return 0;
        if (ia >= 255) return makeARGB(255, Math.round(r), Math.round(g), Math.round(b));

        return makeARGB(ia,
                        Math.min(255, Math.round(r * 255f / a)),
                        Math.min(255, Math.round(g * 255f / a)),
                        Math.min(255, Math.round(b * 255f / a)));
    }

    /**
     * Interpolates between two premultiplied colors.
     *
     * @param c1  color to interpolate from
     * @param c2  color to interpolate to
     * @param t   interpolation ratio 0..1, 0 == fully c1, 1 == fully c2
     *
     * @return    interpolated color, premultiplied
     */
    public static int makeLerpPM(int c1, int c2, float t) {
        if (t <= 0) return c1;
        if (t >= 1) return c2;

        int a1 = getA(c1);
        int r1 = getR(c1);
        int g1 = getG(c1);
        int b1 = getB(c1);

        int a2 = getA(c2);
        int r2 = getR(c2);
        int g2 = getG(c2);
        int b2 = getB(c2);

        float a = a1 + (a2 - a1) * t;
        float r = r1 + (r2 - r1) * t;
        float g = g1 + (g2 - g1) * t;
        float b = b1 + (b2 - b1) * t;

        return makeARGB(
            Math.min(255, Math.max(0, Math.round(a))),
            Math.min(255, Math.max(0, Math.round(r))),
            Math.min(255, Math.max(0, Math.round(g))),
            Math.min(255, Math.max(0, Math.round(b)))
        );
    }

    public static int makeARGB(int a, int r, int g, int b) {
        assert 0 <= a && a <= 255 : "Alpha is out of 0..255 range: " + a;
        assert 0 <= r && r <= 255 : "Red is out of 0..255 range: " + r;
        assert 0 <= g && g <= 255 : "Green is out of 0..255 range: " + g;
        assert 0 <= b && b <= 255 : "Blue is out of 0..255 range: " + b;
        return ((a & 0xFF) << 24)
            | ((r & 0xFF) << 16)
            | ((g & 0xFF) << 8)
            | (b & 0xFF);
    }

    public static int makeRGB(int r, int g, int b) {
        return makeARGB(255, r, g, b);
    }

    public static int getA(int color) {
        return (color >> 24) & 0xFF;
    }

    public static int getR(int color) {
        return (color >> 16) & 0xFF;
    }

    public static int getG(int color) {
        return (color >> 8) & 0xFF;
    }

    public static int getB(int color) {
        return color & 0xFF;
    }    

    public static int withA(int color, int a) {
        assert 0 <= a && a <= 255 : "Alpha is out of 0..255 range: " + a;
        return ((a & 0xFF) << 24) | (color & 0x00FFFFFF);
    }

    public static int withR(int color, int r) {
        assert 0 <= r && r <= 255 : "Red is out of 0..255 range: " + r;
        return ((r & 0xFF) << 16) | (color & 0xFF00FFFF);
    }

    public static int withG(int color, int g) {
        assert 0 <= g && g <= 255 : "Green is out of 0..255 range: " + g;
        return ((g & 0xFF) << 8) | (color & 0xFFFF00FF);
    }

    public static int withB(int color, int b) {
        assert 0 <= b && b <= 255 : "Blue is out of 0..255 range: " + b;
        return (b & 0xFF) | (color & 0xFFFFFF00);
    }
}
