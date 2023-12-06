package com.demo.memshell.whole.unload;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Whoopsunix
 *
 * 卸载 ContextClassLoader 注入 Tomcat Filter 型内存马
 * Tomcat 8 9
 */
public class UnloadTomcatFilterContextClassMS {
    private static String NAME = "TomcatServletThreadMS";

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

            Object filterDef = standardContext.getClass().getDeclaredMethod("findFilterDef", String.class).invoke(standardContext, NAME);
            // 卸载
            if (filterDef != null) {
                // 移除 FilterDef
                standardContext.getClass().getDeclaredMethod("removeFilterDef", filterDef.getClass()).invoke(standardContext, filterDef);

                // 移除 filterMap
                Object filterMaps = getFieldValue(standardContext, "filterMaps");
                Object array = getFieldValue(filterMaps, "array");
                if (array != null && array.getClass().isArray()) {
                    int length = Array.getLength(array);
                    for (int i = 0; i < length; i++) {
                        Object element = Array.get(array, i);
                        String name = getFieldValue(element, "filterName").toString();
                        if (name.equals(NAME)) {
                            standardContext.getClass().getDeclaredMethod("removeFilterMap", element.getClass()).invoke(standardContext, element);
                            break;
                        }
                    }
                }

                // 移除 ApplicationFilterConfig
                java.util.Map filterConfigs = (java.util.Map) getFieldValue(standardContext, "filterConfigs");
                java.lang.reflect.Constructor constructor = Class.forName("org.apache.catalina.core.ApplicationFilterConfig").getDeclaredConstructor(Class.forName("org.apache.catalina.Context"), Class.forName("org.apache.tomcat.util.descriptor.web.FilterDef"));
                constructor.setAccessible(true);
                filterConfigs.remove(NAME);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object getFieldValue(Object obj, String fieldName) throws Exception {
        Field field = getField(obj.getClass(), fieldName);
        return field.get(obj);
    }

    public static Field getField(Class<?> clazz, String fieldName) {
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
