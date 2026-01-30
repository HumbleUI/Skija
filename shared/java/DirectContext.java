package io.github.humbleui.skija;

import org.jetbrains.annotations.*;
import io.github.humbleui.skija.impl.*;

public class DirectContext extends RefCnt {
    static { Library.staticLoad(); }

    @NotNull @Contract("-> new")    
    public static DirectContext makeGL() {
        Stats.onNativeCall();
        return new DirectContext(_nMakeGL());
    }

    @NotNull @Contract("-> this")
    public static DirectContext makeMetal(long devicePtr, long queuePtr) {
        Stats.onNativeCall();
        return new DirectContext(_nMakeMetal(devicePtr, queuePtr));
    }

    /**
     * <p>Creates Direct3D direct rendering context from D3D12 native objects.</p>
     * <p>For more information refer to skia GrDirectContext class.</p>
     * 
     * @param adapterPtr    pointer to IDXGIAdapter1 object; must be not zero
     * @param devicePtr     pointer to ID3D12Device objetc, which is created with 
     *                      provided adapter in adapterPtr; must be not zero 
     * @param queuePtr      Pointer to ID3D12CommandQueue object, which
     *                      is created with provided device in devicePtr with
     *                      type D3D12_COMMAND_LIST_TYPE_DIRECT; must be not zero 
     */ 
    @NotNull @Contract("-> this")
    public static DirectContext makeDirect3D(long adapterPtr, long devicePtr, long queuePtr) {
        Stats.onNativeCall();
        return new DirectContext(_nMakeDirect3D(adapterPtr, devicePtr, queuePtr));
    }

    /**
     * <p>Creates Vulkan direct rendering context from Vulkan native objects.</p>
     * <p>For more information refer to skia GrDirectContext class.</p>
     *
     * @param instancePtr       pointer to VkInstance; must be not zero
     * @param physicalDevicePtr pointer to VkPhysicalDevice; must be not zero
     * @param devicePtr         pointer to VkDevice; must be not zero
     * @param queuePtr          pointer to VkQueue; must be not zero
     * @param graphicsQueueIndex index of the graphics queue
     * @param instanceProcAddr  pointer to vkGetInstanceProcAddr function
     * @param getProcAddr       pointer to vkGetDeviceProcAddr function
     * @param apiVersion        Vulkan API version
     */
    @NotNull @Contract("-> this")
    public static DirectContext makeVulkan(long instancePtr, long physicalDevicePtr, long devicePtr, long queuePtr, int graphicsQueueIndex, long instanceProcAddr, long getProcAddr, int apiVersion) {
        Stats.onNativeCall();
        return new DirectContext(_nMakeVulkan(instancePtr, physicalDevicePtr, devicePtr, queuePtr, graphicsQueueIndex, instanceProcAddr, getProcAddr, apiVersion));
    }

    @NotNull @Contract("-> this")
    public DirectContext flush() {
        Stats.onNativeCall();
        _nFlush(_ptr);
        return this;
    }

    /**
     * <p>Submit outstanding work to the gpu from all previously un-submitted flushes. The return
     * value of the submit will indicate whether or not the submission to the GPU was successful.</p>
     *
     * <p>If the call returns true, all previously passed in semaphores in flush calls will have been
     * submitted to the GPU and they can safely be waited on. The caller should wait on those
     * semaphores or perform some other global synchronization before deleting the semaphores.</p>
     *
     * <p>If it returns false, then those same semaphores will not have been submitted and we will not
     * try to submit them again. The caller is free to delete the semaphores at any time.</p>
     */
    public boolean submit() {
        Stats.onNativeCall();
        return _nSubmit(_ptr, false);
    }

    /**
     * <p>Submit outstanding work to the gpu from all previously un-submitted flushes. The return
     * value of the submit will indicate whether or not the submission to the GPU was successful.</p>
     *
     * <p>If the call returns true, all previously passed in semaphores in flush calls will have been
     * submitted to the GPU and they can safely be waited on. The caller should wait on those
     * semaphores or perform some other global synchronization before deleting the semaphores.</p>
     *
     * <p>If it returns false, then those same semaphores will not have been submitted and we will not
     * try to submit them again. The caller is free to delete the semaphores at any time.</p>
     *
     * @param syncCpu  If true, return once the gpu has finished with all submitted work.
     */
    public boolean submit(boolean syncCpu) {
        Stats.onNativeCall();
        return _nSubmit(_ptr, syncCpu);
    }

    /**
     * Call to ensure all drawing to the context has been flushed and submitted to the underlying 3D
     * API. This is equivalent to calling {@link #flush} followed by {@link #submit(boolean)}.
     */
    @NotNull @Contract("-> this")
    public DirectContext flushAndSubmit(boolean syncCpu) {
        Stats.onNativeCall();
        _nFlushAndSubmit(_ptr, syncCpu);
        return this;
    }

    @NotNull @Contract("-> this")
    public DirectContext flush(Surface surface) {
        try {
            Stats.onNativeCall();
            _nFlushSurface(_ptr, Native.getPtr(surface));
            return this;
        } finally {
            ReferenceUtil.reachabilityFence(surface);
        }
    }

    @NotNull @Contract("-> this")
    public DirectContext flushAndSubmit(Surface surface) {
        try {
            Stats.onNativeCall();
            _nFlushAndSubmitSurface(_ptr, Native.getPtr(surface), false);
            return this;
        } finally {
            ReferenceUtil.reachabilityFence(surface);
        }
    }

    @NotNull @Contract("-> this")
    public DirectContext flushAndSubmit(Surface surface, boolean syncCpu) {
        try {
            Stats.onNativeCall();
            _nFlushAndSubmitSurface(_ptr, Native.getPtr(surface), syncCpu);
            return this;
        } finally {
            ReferenceUtil.reachabilityFence(surface);
        }
    }

    @NotNull @Contract("-> this")
    public DirectContext resetAll() {
        return reset(BackendState.ALL);
    }

    @NotNull @Contract("-> this")
    public DirectContext resetGLAll() {
        return reset(BackendState.GL_ALL);
    }

    @NotNull @Contract("_ -> this")
    public DirectContext reset(BackendState... states) {
        Stats.onNativeCall();
        int flags = 0;
        for (BackendState state: states)
            flags |= state._value;
        _nReset(_ptr, flags);
        return this;
    }

    /**
     * <p>Abandons all GPU resources and assumes the underlying backend 3D API context is no longer
     * usable. Call this if you have lost the associated GPU context, and thus internal texture,
     * buffer, etc. references/IDs are now invalid. Calling this ensures that the destructors of the
     * context and any of its created resource objects will not make backend 3D API calls. Content
     * rendered but not previously flushed may be lost. After this function is called all subsequent
     * calls on the context will fail or be no-ops.</p>
     *
     * <p>The typical use case for this function is that the underlying 3D context was lost and further
     * API calls may crash.</p>
     *
     * <p>For Vulkan, even if the device becomes lost, the VkQueue, VkDevice, or VkInstance used to
     * create the context must be kept alive even after abandoning the context. Those objects must
     * live for the lifetime of the context object itself. The reason for this is so that
     * we can continue to delete any outstanding GrBackendTextures/RenderTargets which must be
     * cleaned up even in a device lost state.</p>
     */
    public void abandon() {
        try {
            Stats.onNativeCall();
            _nAbandon(_ptr);
        } finally {
            ReferenceUtil.reachabilityFence(this);
        }
   }

    @ApiStatus.Internal
    public DirectContext(long ptr) {
        super(ptr);
    }

    @ApiStatus.Internal public static native long    _nMakeGL();
    @ApiStatus.Internal public static native long    _nMakeMetal(long devicePtr, long queuePtr);
    @ApiStatus.Internal public static native long    _nMakeDirect3D(long adapterPtr, long devicePtr, long queuePtr);
    @ApiStatus.Internal public static native long    _nMakeVulkan(long instancePtr, long physicalDevicePtr, long devicePtr, long queuePtr, int graphicsQueueIndex, long instanceProcAddr, long getProcAddr, int apiVersion);
    @ApiStatus.Internal public static native void    _nFlush(long ptr);
    @ApiStatus.Internal public static native boolean _nSubmit(long ptr, boolean syncCpu);
    @ApiStatus.Internal public static native void    _nFlushAndSubmit(long ptr, boolean syncCpu);
    @ApiStatus.Internal public static native void    _nFlushSurface(long ptr, long surfacePtr);
    @ApiStatus.Internal public static native void    _nFlushAndSubmitSurface(long ptr, long surfacePtr, boolean syncCpu);
    @ApiStatus.Internal public static native void    _nReset(long ptr, int flags);
    @ApiStatus.Internal public static native void    _nAbandon(long ptr);
}