package com.demo.memshell.whole.unload;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * @author Whoopsunix
 *
 * 卸载 Thread 获取上下文注入 Tomcat Servlet 型内存马
 * Tomcat 7 8 9
 */
public class UnloadTomcatFilterThreadMS {
    private static String NAME = "Whoopsunix";
    private static HttpServletRequest request;

    static {
        try {
            /**
             * 获取 request、response 对象
             */
            Thread[] threads = (Thread[]) getFieldValue(Thread.currentThread().getThreadGroup(), "threads");
            boolean isFind = false;
            for (int i = 0; i < threads.length; i++) {
                if (isFind) break;
                Thread thread = threads[i];
                // Thread 筛选
                if (thread == null)
                    continue;
                String threadName = thread.getName();
                if (!(
                        ((threadName.contains("http-nio") || threadName.contains("http-apr")) && threadName.contains("Poller"))
                                || (threadName.contains("http-bio") && threadName.contains("AsyncTimeout"))
                                || (threadName.contains("http-") && threadName.contains("Acceptor"))
                ))
                    continue;
                Object target = getFieldValue(thread, "target");
                Object this0 = getFieldValue(target, "this$0");
                Object handler = getFieldValue(this0, "handler");
                Object global = getFieldValue(handler, "global");
                java.util.List processors = (java.util.List) getFieldValue(global, "processors");

                for (int j = 0; j < processors.size(); j++) {
                    Object processor = processors.get(j);
                    Object req = getFieldValue(processor, "req");
                    request = (HttpServletRequest) req.getClass().getDeclaredMethod("getNote", Integer.TYPE).invoke(req, new Integer(1));
                    isFind = true;
                    break;
                }
            }

            /**
             * 注入 Filter
             */
            Object standardContext;
            try {
                Object servletContext = request.getClass().getDeclaredMethod("getServletContext").invoke(request);
                Object applicationContext = getFieldValue(servletContext, "context");
                standardContext = getFieldValue(applicationContext, "context");
            } catch (NoSuchMethodException e) {
                standardContext = getFieldValue(request, "context");
            }

            Object filterDef = standardContext.getClass().getDeclaredMethod("findFilterDef", String.class).invoke(standardContext, NAME);
            // tomcat 7
            // org.apache.catalina.deploy.FilterDef
            // tomcat 8 9
            // org.apache.tomcat.util.descriptor.web.FilterDef
            try {
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
                java.lang.reflect.Constructor constructor = null;
                try {
                    constructor = Class.forName("org.apache.catalina.core.ApplicationFilterConfig").getDeclaredConstructor(Class.forName("org.apache.catalina.Context"), Class.forName("org.apache.catalina.deploy.FilterDef"));

                } catch (ClassNotFoundException e) {
                    constructor = Class.forName("org.apache.catalina.core.ApplicationFilterConfig").getDeclaredConstructor(Class.forName("org.apache.catalina.Context"), Class.forName("org.apache.tomcat.util.descriptor.web.FilterDef"));
                }
                constructor.setAccessible(true);
                filterConfigs.remove(NAME);
            } catch (Exception e) {
            }


        } catch (Exception e) {
        }
    }

    /**
     * tools
     */
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

    public static void setFieldValue(final Object obj, final String fieldName, final Object value) throws Exception {
        final Field field = getField(obj.getClass(), fieldName);
        field.set(obj, value);
    }
}
