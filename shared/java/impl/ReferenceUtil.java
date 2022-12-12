package io.github.humbleui.skija.impl;

public class ReferenceUtil {

    /**
     * @see java.lang.ref.Reference#reachabilityFence(Object)
     */
    public static void reachabilityFence(Object ref) {
        // Don't need to do anything.
        // On HotSpot JVM, any method call with the object as the argument
        // has the same effect as Reference.reachabilityFence(Object).
        // See https://mail.openjdk.org/pipermail/core-libs-dev/2018-February/051312.html
    }
}
