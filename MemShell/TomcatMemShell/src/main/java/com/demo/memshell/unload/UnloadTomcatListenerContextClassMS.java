package com.demo.memshell.unload;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Whoopsunix
 *
 * 卸载 ContextClassLoader 注入 Tomcat Listener 型内存马
 * Tomcat 8 9
 */
public class UnloadTomcatListenerContextClassMS {
    private static String NAME = "TomcatListenerContextClassMS";

    public UnloadTomcatListenerContextClassMS() {

    }

    static {
        try {
            // 获取 standardContext
            Object webappClassLoaderBase = Thread.currentThread().getContextClassLoader();
            Object standardContext;
            try {
                Method getResourcesmethod = webappClassLoaderBase.getClass().getDeclaredMethod("getResources");
                getResourcesmethod.setAccessible(true);
                Object resources = getResourcesmethod.invoke(webappClassLoaderBase);
                Method getContextmethod = resources.getClass().getDeclaredMethod("getContext");
                getContextmethod.setAccessible(true);
                standardContext = getContextmethod.invoke(resources);
            } catch (Exception ignored) {
                Object root = getFieldValue(webappClassLoaderBase, "resources");
                standardContext = getFieldValue(root, "context");
            }
            List<Object> applicationEventListeners = (List<Object>) getFieldValue(standardContext, "applicationEventListenersList");
            for (Object applicationEventListener : applicationEventListeners) {
                if (applicationEventListener.getClass().getName().contains(NAME)) {
                    applicationEventListeners.remove(applicationEventListener);
                    break;
                }
            }

        } catch (Exception e) {
        }
    }
    public static Object getFieldValue(final Object obj, final String fieldName) throws Exception {
        final Field field = getField(obj.getClass(), fieldName);
        return field.get(obj);
    }

    public static Field getField(final Class<?> clazz, final String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
        } catch (NoSuchFieldException ex) {
            if (clazz.getSuperclass() != null)
                field = getField(clazz.getSuperclass(), fieldName);
        }
        return field;
    }
}
