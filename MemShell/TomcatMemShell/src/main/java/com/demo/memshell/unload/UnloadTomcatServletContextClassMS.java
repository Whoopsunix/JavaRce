package com.demo.memshell.unload;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Whoopsunix
 *
 * 卸载 ContextClassLoader 注入 Tomcat Servlet 型内存马
 * Tomcat 8 9
 */
public class UnloadTomcatServletContextClassMS {
    private static String NAME = "Whoopsunix";
    private static String pattern = "/WhoopsunixShell";

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
            } catch (Exception e) {
                Object root = getFieldValue(webappClassLoaderBase, "resources");
                standardContext = getFieldValue(root, "context");
            }
            Object container = standardContext.getClass().getSuperclass().getDeclaredMethod("findChild", String.class).invoke(standardContext, NAME);
            if (container != null) {
                // 删除 servlet
                standardContext.getClass().getSuperclass().getDeclaredMethod("removeChild", Class.forName("org.apache.catalina.Container")).invoke(standardContext, container);
                // 删除 servlet 映射
                standardContext.getClass().getDeclaredMethod("removeServletMapping", String.class).invoke(standardContext, pattern);
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
