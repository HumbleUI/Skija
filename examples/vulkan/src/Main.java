package io.github.humbleui.skija.examples.vulkan;

import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.FloatBuffer;
import java.util.*;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.*;
import org.lwjgl.system.*;
import org.lwjgl.vulkan.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFWVulkan.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.vulkan.VK10.*;
import static org.lwjgl.vulkan.VK11.*;
import static org.lwjgl.vulkan.KHRSurface.*;
import static org.lwjgl.vulkan.KHRSwapchain.*;

import io.github.humbleui.skija.*;
import io.github.humbleui.skija.examples.scenes.*;
import io.github.humbleui.skija.impl.*;
import io.github.humbleui.types.*;

public class Main {
    private static long window;
    private static int width = 800;
    private static int height = 600;
    private static float dpi = 1f;
    private static int xpos = 0;
    private static int ypos = 0;

    private static VkInstance instance;
    private static long surface;
    private static VkPhysicalDevice physicalDevice;
    private static VkDevice device;
    private static VkQueue graphicsQueue;
    private static int graphicsQueueFamilyIndex;
    
    private static long swapchain;
    private static int swapchainFormat;
    private static VkExtent2D swapchainExtent;
    private static List<Long> swapchainImages;
    private static List<Long> swapchainImageViews; 
    
    private static long commandPool;
    private static VkCommandBuffer setupCmdBuffer;
    private static VkCommandBuffer presentCmdBuffer;
    
    private static long imageAvailableSemaphore;
    private static long renderFinishedSemaphore;
    private static long fence;

    private static DirectContext directContext;
    private static BackendRenderTarget[] renderTargets;
    private static Surface[] skiaSurfaces;

    public static void main(String[] args) {
        if ("false".equals(System.getProperty("skija.staticLoad")))
            io.github.humbleui.skija.impl.Library.load();
        initWindow();
        initVulkan();
        initSkia();
        loop();
        cleanup();
    }

    private static void initWindow() {
        if (System.getenv("WAYLAND_DISPLAY") != null) {
            glfwInitHint(GLFW_PLATFORM, GLFW_PLATFORM_WAYLAND);
        }
        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");
        glfwWindowHint(GLFW_CLIENT_API, GLFW_NO_API);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); 
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        window = glfwCreateWindow(width, height, "Skija Vulkan Example", NULL, NULL);

        glfwSetWindowSizeCallback(window, (window, w, h) -> {
            updateDimensions();
            recreateSwapchain();
        });

        glfwSetCursorPosCallback(window, (window, x, y) -> {
            xpos = (int) (x / dpi);
            ypos = (int) (y / dpi);
        });

        glfwSetScrollCallback(window, (window, xoffset, yoffset) -> {
            Scenes.currentScene().onScroll((float) xoffset * dpi, (float) yoffset * dpi);
        });

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
             if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
             if (action == GLFW_PRESS) {
                switch (key) {
                    case GLFW_KEY_LEFT:
                        Scenes.prevScene();
                        break;
                    case GLFW_KEY_RIGHT:
                        Scenes.nextScene();
                        break;
                    case GLFW_KEY_UP:
                        Scenes.currentScene().changeVariant(-1);
                        break;
                    case GLFW_KEY_DOWN:
                        Scenes.currentScene().changeVariant(1);
                        break;
                    case GLFW_KEY_S:
                        Scenes.stats = !Scenes.stats;
                        break;
                    case GLFW_KEY_G:
                        System.gc();
                        break;
                }
            }
        });

        updateDimensions();
        glfwShowWindow(window);
    }

    private static void updateDimensions() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer fw = stack.mallocInt(1);
            IntBuffer fh = stack.mallocInt(1);
            glfwGetFramebufferSize(window, fw, fh);

            FloatBuffer sx = stack.mallocFloat(1);
            FloatBuffer sy = stack.mallocFloat(1);
            glfwGetWindowContentScale(window, sx, sy);
            
            dpi = sx.get(0);
            width = (int) (fw.get(0) / dpi);
            height = (int) (fh.get(0) / dpi);
        }
    }

    private static void recreateSwapchain() {
        vkDeviceWaitIdle(device);
        cleanupSkia();
        cleanupSwapchain();
        
        try (MemoryStack stack = MemoryStack.stackPush()) {
            createSwapchain(stack);
        }
        initSkia();
    }

    private static void cleanupSwapchain() {
        for (long imageView : swapchainImageViews) {
            vkDestroyImageView(device, imageView, null);
        }
        vkDestroySwapchainKHR(device, swapchain, null);
    }

    private static void cleanupSkia() {
        if (skiaSurfaces != null) {
            for (Surface s : skiaSurfaces) if (s != null) s.close();
        }
        if (renderTargets != null) {
            for (BackendRenderTarget rt : renderTargets) if (rt != null) rt.close();
        }
    }

    private static void initVulkan() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            PointerBuffer extensions = glfwGetRequiredInstanceExtensions();
            
            VkApplicationInfo appInfo = VkApplicationInfo.calloc(stack)
                .sType(VK_STRUCTURE_TYPE_APPLICATION_INFO)
                .pApplicationName(stack.UTF8("Skija Vulkan Example"))
                .applicationVersion(VK_MAKE_VERSION(1, 0, 0))
                .pEngineName(stack.UTF8("No Engine"))
                .engineVersion(VK_MAKE_VERSION(1, 0, 0))
                .apiVersion(VK_API_VERSION_1_1);

            VkInstanceCreateInfo createInfo = VkInstanceCreateInfo.calloc(stack)
                .sType(VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO)
                .pApplicationInfo(appInfo)
                .ppEnabledExtensionNames(extensions);
            
            PointerBuffer pInstance = stack.mallocPointer(1);
            check(vkCreateInstance(createInfo, null, pInstance));
            instance = new VkInstance(pInstance.get(0), createInfo);

            LongBuffer pSurface = stack.mallocLong(1);
            check(glfwCreateWindowSurface(instance, window, null, pSurface));
            surface = pSurface.get(0);

            PointerBuffer pPhysicalDevices = stack.mallocPointer(1);
            IntBuffer pCount = stack.mallocInt(1);
            vkEnumeratePhysicalDevices(instance, pCount, null);
            vkEnumeratePhysicalDevices(instance, pCount, pPhysicalDevices);
            physicalDevice = new VkPhysicalDevice(pPhysicalDevices.get(0), instance);

            VkQueueFamilyProperties.Buffer queueProps = VkQueueFamilyProperties.malloc(10, stack);
            vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice, pCount, queueProps);
            graphicsQueueFamilyIndex = -1;
            for (int i = 0; i < pCount.get(0); i++) {
                if ((queueProps.get(i).queueFlags() & VK_QUEUE_GRAPHICS_BIT) != 0) {
                    IntBuffer pSupported = stack.mallocInt(1);
                    vkGetPhysicalDeviceSurfaceSupportKHR(physicalDevice, i, surface, pSupported);
                    if (pSupported.get(0) == VK_TRUE) {
                        graphicsQueueFamilyIndex = i;
                        break;
                    }
                }
            }
            if (graphicsQueueFamilyIndex == -1) throw new RuntimeException("No suitable queue family found");

            float[] queuePriorities = {1.0f};
            VkDeviceQueueCreateInfo queueCreateInfo = VkDeviceQueueCreateInfo.calloc(stack)
                .sType(VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO)
                .queueFamilyIndex(graphicsQueueFamilyIndex)
                .pQueuePriorities(stack.floats(queuePriorities));

            PointerBuffer deviceExtensions = stack.mallocPointer(1);
            deviceExtensions.put(stack.UTF8(VK_KHR_SWAPCHAIN_EXTENSION_NAME));
            deviceExtensions.flip();

            VkDeviceCreateInfo deviceCreateInfo = VkDeviceCreateInfo.calloc(stack)
                .sType(VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO)
                .pQueueCreateInfos(VkDeviceQueueCreateInfo.calloc(1, stack).put(0, queueCreateInfo))
                .ppEnabledExtensionNames(deviceExtensions);

            PointerBuffer pDevice = stack.mallocPointer(1);
            check(vkCreateDevice(physicalDevice, deviceCreateInfo, null, pDevice));
            device = new VkDevice(pDevice.get(0), physicalDevice, deviceCreateInfo);
            
            PointerBuffer pQueue = stack.mallocPointer(1);
            vkGetDeviceQueue(device, graphicsQueueFamilyIndex, 0, pQueue);
            graphicsQueue = new VkQueue(pQueue.get(0), device);

            VkCommandPoolCreateInfo poolInfo = VkCommandPoolCreateInfo.calloc(stack)
                .sType(VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO)
                .queueFamilyIndex(graphicsQueueFamilyIndex)
                .flags(VK_COMMAND_POOL_CREATE_RESET_COMMAND_BUFFER_BIT);
            LongBuffer pCommandPool = stack.mallocLong(1);
            check(vkCreateCommandPool(device, poolInfo, null, pCommandPool));
            commandPool = pCommandPool.get(0);

            createSwapchain(stack);

            VkSemaphoreCreateInfo semaphoreInfo = VkSemaphoreCreateInfo.calloc(stack)
                .sType(VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO);
            VkFenceCreateInfo fenceInfo = VkFenceCreateInfo.calloc(stack)
                .sType(VK_STRUCTURE_TYPE_FENCE_CREATE_INFO)
                .flags(VK_FENCE_CREATE_SIGNALED_BIT);
            
            LongBuffer pSemaphore = stack.mallocLong(1);
            LongBuffer pFence = stack.mallocLong(1);
            
            check(vkCreateSemaphore(device, semaphoreInfo, null, pSemaphore));
            imageAvailableSemaphore = pSemaphore.get(0);
            
            check(vkCreateSemaphore(device, semaphoreInfo, null, pSemaphore));
            renderFinishedSemaphore = pSemaphore.get(0);
            
            check(vkCreateFence(device, fenceInfo, null, pFence));
            fence = pFence.get(0);

            VkCommandBufferAllocateInfo allocInfo = VkCommandBufferAllocateInfo.calloc(stack)
                .sType(VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO)
                .commandPool(commandPool)
                .level(VK_COMMAND_BUFFER_LEVEL_PRIMARY)
                .commandBufferCount(2);
            PointerBuffer pCommandBuffers = stack.mallocPointer(2);
            check(vkAllocateCommandBuffers(device, allocInfo, pCommandBuffers));
            setupCmdBuffer = new VkCommandBuffer(pCommandBuffers.get(0), device);
            presentCmdBuffer = new VkCommandBuffer(pCommandBuffers.get(1), device);
        }
    }

    private static void createSwapchain(MemoryStack stack) {
        VkSurfaceCapabilitiesKHR capabilities = VkSurfaceCapabilitiesKHR.calloc(stack);
        vkGetPhysicalDeviceSurfaceCapabilitiesKHR(physicalDevice, surface, capabilities);

        swapchainExtent = VkExtent2D.calloc();
        if (capabilities.currentExtent().width() != 0xFFFFFFFF) {
            swapchainExtent.set(capabilities.currentExtent());
        } else {
            swapchainExtent.set(800, 600);
        }

        IntBuffer pFormatCount = stack.mallocInt(1);
        vkGetPhysicalDeviceSurfaceFormatsKHR(physicalDevice, surface, pFormatCount, null);
        VkSurfaceFormatKHR.Buffer formats = VkSurfaceFormatKHR.malloc(pFormatCount.get(0), stack);
        vkGetPhysicalDeviceSurfaceFormatsKHR(physicalDevice, surface, pFormatCount, formats);
        
        swapchainFormat = formats.get(0).format(); 
        int colorSpace = formats.get(0).colorSpace();
        for (int i = 0; i < formats.capacity(); i++) {
            if (formats.get(i).format() == VK_FORMAT_B8G8R8A8_UNORM && formats.get(i).colorSpace() == VK_COLOR_SPACE_SRGB_NONLINEAR_KHR) {
                swapchainFormat = VK_FORMAT_B8G8R8A8_UNORM;
                colorSpace = VK_COLOR_SPACE_SRGB_NONLINEAR_KHR;
                break;
            }
        }

        VkSwapchainCreateInfoKHR createInfo = VkSwapchainCreateInfoKHR.calloc(stack)
            .sType(VK_STRUCTURE_TYPE_SWAPCHAIN_CREATE_INFO_KHR)
            .surface(surface)
            .minImageCount(Math.max(2, capabilities.minImageCount()))
            .imageFormat(swapchainFormat)
            .imageColorSpace(colorSpace)
            .imageExtent(swapchainExtent)
            .imageArrayLayers(1)
            .imageUsage(VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT | VK_IMAGE_USAGE_TRANSFER_DST_BIT | VK_IMAGE_USAGE_SAMPLED_BIT | VK_IMAGE_USAGE_TRANSFER_SRC_BIT) 
            .imageSharingMode(VK_SHARING_MODE_EXCLUSIVE)
            .preTransform(capabilities.currentTransform())
            .compositeAlpha(VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR)
            .presentMode(VK_PRESENT_MODE_FIFO_KHR)
            .clipped(true)
            .oldSwapchain(NULL);

        LongBuffer pSwapchain = stack.mallocLong(1);
        check(vkCreateSwapchainKHR(device, createInfo, null, pSwapchain));
        swapchain = pSwapchain.get(0);

        IntBuffer pImageCount = stack.mallocInt(1);
        vkGetSwapchainImagesKHR(device, swapchain, pImageCount, null);
        LongBuffer pSwapchainImages = stack.mallocLong(pImageCount.get(0));
        vkGetSwapchainImagesKHR(device, swapchain, pImageCount, pSwapchainImages);
        
        swapchainImages = new ArrayList<>();
        swapchainImageViews = new ArrayList<>();
        for (int i = 0; i < pSwapchainImages.capacity(); i++) {
            long image = pSwapchainImages.get(i);
            swapchainImages.add(image);

            VkImageViewCreateInfo viewInfo = VkImageViewCreateInfo.calloc(stack)
                .sType(VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO)
                .image(image)
                .viewType(VK_IMAGE_VIEW_TYPE_2D)
                .format(swapchainFormat)
                .subresourceRange(it -> it
                    .aspectMask(VK_IMAGE_ASPECT_COLOR_BIT)
                    .baseMipLevel(0)
                    .levelCount(1)
                    .baseArrayLayer(0)
                    .layerCount(1));
            
            LongBuffer pView = stack.mallocLong(1);
            check(vkCreateImageView(device, viewInfo, null, pView));
            swapchainImageViews.add(pView.get(0));
        }
    }

    private static void initSkia() {
        Stats.enabled = true;
        long instanceProcAddr = VK.getFunctionProvider().getFunctionAddress("vkGetInstanceProcAddr");
        long deviceProcAddr = VK.getFunctionProvider().getFunctionAddress("vkGetDeviceProcAddr");

        directContext = DirectContext.makeVulkan(
            instance.address(),
            physicalDevice.address(),
            device.address(),
            graphicsQueue.address(),
            graphicsQueueFamilyIndex,
            instanceProcAddr,
            deviceProcAddr,
            VK_API_VERSION_1_1
        );

        renderTargets = new BackendRenderTarget[swapchainImages.size()];
        skiaSurfaces = new Surface[swapchainImages.size()];

        for (int i = 0; i < swapchainImages.size(); i++) {
            renderTargets[i] = BackendRenderTarget.makeVulkan(
                swapchainExtent.width(),
                swapchainExtent.height(),
                swapchainImages.get(i),
                VK_IMAGE_TILING_OPTIMAL,
                VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL, 
                swapchainFormat,
                VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT | VK_IMAGE_USAGE_TRANSFER_DST_BIT | VK_IMAGE_USAGE_SAMPLED_BIT | VK_IMAGE_USAGE_TRANSFER_SRC_BIT,
                1,
                1
            );

            ColorType colorType = ColorType.BGRA_8888;
            if (swapchainFormat == VK_FORMAT_R8G8B8A8_UNORM || swapchainFormat == VK_FORMAT_R8G8B8A8_SRGB)
                colorType = ColorType.RGBA_8888;
            else if (swapchainFormat == VK_FORMAT_B8G8R8A8_UNORM || swapchainFormat == VK_FORMAT_B8G8R8A8_SRGB)
                colorType = ColorType.BGRA_8888;

            skiaSurfaces[i] = Surface.wrapBackendRenderTarget(
                directContext,
                renderTargets[i],
                SurfaceOrigin.TOP_LEFT,
                colorType,
                null,
                new SurfaceProps(PixelGeometry.RGB_H)
            );
        }
    }

    private static void loop() {
        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();
            drawFrame();
        }
        vkDeviceWaitIdle(device);
    }

    private static void drawFrame() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            vkWaitForFences(device, stack.longs(fence), true, Long.MAX_VALUE);
            vkResetFences(device, stack.longs(fence));

            IntBuffer pImageIndex = stack.mallocInt(1);
            vkAcquireNextImageKHR(device, swapchain, Long.MAX_VALUE, imageAvailableSemaphore, NULL, pImageIndex);
            int imageIndex = pImageIndex.get(0);

            VkCommandBufferBeginInfo beginInfo = VkCommandBufferBeginInfo.calloc(stack)
                .sType(VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO)
                .flags(VK_COMMAND_BUFFER_USAGE_ONE_TIME_SUBMIT_BIT);
            
            vkBeginCommandBuffer(setupCmdBuffer, beginInfo);
            
            VkImageMemoryBarrier.Buffer barrier = VkImageMemoryBarrier.calloc(1, stack)
                .sType(VK_STRUCTURE_TYPE_IMAGE_MEMORY_BARRIER)
                .oldLayout(VK_IMAGE_LAYOUT_UNDEFINED) 
                .newLayout(VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL)
                .srcQueueFamilyIndex(VK_QUEUE_FAMILY_IGNORED)
                .dstQueueFamilyIndex(VK_QUEUE_FAMILY_IGNORED)
                .image(swapchainImages.get(imageIndex))
                .subresourceRange(it -> it
                    .aspectMask(VK_IMAGE_ASPECT_COLOR_BIT)
                    .baseMipLevel(0)
                    .levelCount(1)
                    .baseArrayLayer(0)
                    .layerCount(1))
                .srcAccessMask(0)
                .dstAccessMask(VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT);
            
            vkCmdPipelineBarrier(setupCmdBuffer,
                VK_PIPELINE_STAGE_TOP_OF_PIPE_BIT,
                VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT,
                0,
                null,
                null,
                barrier);
            
            vkEndCommandBuffer(setupCmdBuffer);

            VkSubmitInfo setupSubmitInfo = VkSubmitInfo.calloc(stack)
                .sType(VK_STRUCTURE_TYPE_SUBMIT_INFO)
                .waitSemaphoreCount(1)
                .pWaitSemaphores(stack.longs(imageAvailableSemaphore))
                .pWaitDstStageMask(stack.ints(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT))
                .pCommandBuffers(stack.pointers(setupCmdBuffer));
            
            vkQueueSubmit(graphicsQueue, setupSubmitInfo, NULL);

            Canvas canvas = skiaSurfaces[imageIndex].getCanvas();
            Scenes.draw(canvas, width, height, dpi, xpos, ypos);
            
            directContext.flushAndSubmit(false);

            vkBeginCommandBuffer(presentCmdBuffer, beginInfo);
            
            barrier
                .oldLayout(VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL)
                .newLayout(VK_IMAGE_LAYOUT_PRESENT_SRC_KHR)
                .srcAccessMask(VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT)
                .dstAccessMask(0); 
            
            vkCmdPipelineBarrier(presentCmdBuffer,
                VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT,
                VK_PIPELINE_STAGE_BOTTOM_OF_PIPE_BIT,
                0,
                null,
                null,
                barrier);
            
            vkEndCommandBuffer(presentCmdBuffer);

            VkSubmitInfo presentSubmitInfo = VkSubmitInfo.calloc(stack)
                .sType(VK_STRUCTURE_TYPE_SUBMIT_INFO)
                .pCommandBuffers(stack.pointers(presentCmdBuffer))
                .pSignalSemaphores(stack.longs(renderFinishedSemaphore));
            
            vkQueueSubmit(graphicsQueue, presentSubmitInfo, fence);

            VkPresentInfoKHR presentInfo = VkPresentInfoKHR.calloc(stack)
                .sType(VK_STRUCTURE_TYPE_PRESENT_INFO_KHR)
                .pWaitSemaphores(stack.longs(renderFinishedSemaphore))
                .swapchainCount(1)
                .pSwapchains(stack.longs(swapchain))
                .pImageIndices(pImageIndex);
            
            vkQueuePresentKHR(graphicsQueue, presentInfo);
        }
    }

    private static void check(int result) {
        if (result != VK_SUCCESS) throw new RuntimeException("Vulkan error: " + result);
    }

    private static void cleanup() {
        cleanupSkia();
        if (directContext != null) directContext.close();

        vkDestroySemaphore(device, imageAvailableSemaphore, null);
        vkDestroySemaphore(device, renderFinishedSemaphore, null);
        vkDestroyFence(device, fence, null);
        vkDestroyCommandPool(device, commandPool, null);
        cleanupSwapchain();
        vkDestroyDevice(device, null);
        vkDestroySurfaceKHR(instance, surface, null);
        vkDestroyInstance(instance, null);
        glfwDestroyWindow(window);
        glfwTerminate();
    }
}