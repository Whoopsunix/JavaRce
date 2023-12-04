package org.example;

import java.util.Arrays;

/**
 * @author Whoopsunix
 */
public class ClassLoaderUtils {
    public static void main(String[] args) {
        // 获取系统类加载器
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println("System ClassLoader: " + systemClassLoader);

        // 获取扩展类加载器
        ClassLoader extensionClassLoader = systemClassLoader.getParent();
        System.out.println("Extension ClassLoader: " + extensionClassLoader);

        // 获取引导类加载器
        ClassLoader bootstrapClassLoader = extensionClassLoader.getParent();
        System.out.println("Bootstrap ClassLoader: " + bootstrapClassLoader);

        // 获取当前上下文的类加载器
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        System.out.println("Context ClassLoader: " + contextClassLoader);

        // 获取所有已加载的类加载器
        ClassLoader[] allClassLoaders = getAllClassLoaders();
        System.out.println("All ClassLoaders:");
        for (ClassLoader classLoader : allClassLoaders) {
            System.out.println(classLoader);
        }
    }

    private static ClassLoader[] getAllClassLoaders() {
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        ClassLoader[] allClassLoaders = new ClassLoader[]{systemClassLoader};

        // 获取扩展类加载器
        ClassLoader extensionClassLoader = systemClassLoader.getParent();
        if (extensionClassLoader != null) {
            allClassLoaders = Arrays.copyOf(allClassLoaders, allClassLoaders.length + 1);
            allClassLoaders[allClassLoaders.length - 1] = extensionClassLoader;
        }

        // 获取引导类加载器
        ClassLoader bootstrapClassLoader = extensionClassLoader.getParent();
        if (bootstrapClassLoader != null) {
            allClassLoaders = Arrays.copyOf(allClassLoaders, allClassLoaders.length + 1);
            allClassLoaders[allClassLoaders.length - 1] = bootstrapClassLoader;
        }

        // 获取当前上下文的类加载器
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        if (contextClassLoader != null) {
            allClassLoaders = Arrays.copyOf(allClassLoaders, allClassLoaders.length + 1);
            allClassLoaders[allClassLoaders.length - 1] = contextClassLoader;
        }

        return allClassLoaders;
    }
}
