# Skija Vulkan Example

This example demonstrates how to use Skija with Vulkan.

## Prerequisites

- A Vulkan-capable GPU

## Usage

Key API used:

```java
DirectContext.makeVulkan(
    vkInstance,
    vkPhysicalDevice,
    vkDevice,
    vkQueue,
    queueIndex,
    vkGetInstanceProcAddr,
    vkGetDeviceProcAddr
);
```
