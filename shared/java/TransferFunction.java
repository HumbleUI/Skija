package io.github.humbleui.skija;

import lombok.*;

/**
 * <p>A transfer function mapping encoded values to linear values,
 * represented by this 7-parameter piecewise function:</p>
 *
 * <pre>{@code
 *   linear = sign(encoded) *  (c*|encoded| + f)       , 0 <= |encoded| < d
 *          = sign(encoded) * ((a*|encoded| + b)^g + e), d <= |encoded|
 * }</pre>
 *
 * <p>(A simple gamma transfer function sets g to gamma and a to 1.)</p>
 */
@AllArgsConstructor
@lombok.Data
@With
public class TransferFunction {
    public final float _g;
    public final float _a;
    public final float _b;
    public final float _c;
    public final float _d;
    public final float _e;
    public final float _f;

    public TransferFunction(float[] gabcdef) {
        this(gabcdef[0], gabcdef[1], gabcdef[2], gabcdef[3], gabcdef[4], gabcdef[5], gabcdef[6]);
    }
}
