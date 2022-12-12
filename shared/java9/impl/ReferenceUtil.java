package io.github.humbleui.skija.impl;

public class ReferenceUtil {

    /**
     * @see java.lang.ref.Reference#reachabilityFence(Object)
     */
    public static void reachabilityFence(Object ref) {
        // Forward to Reference.reachabilityFence(Object) to ensure that this method
        // will continue to work even if the JVM behavior changes in the future.
        java.lang.ref.Reference.reachabilityFence(ref);
    }
}
