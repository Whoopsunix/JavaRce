package com.demo.memshell.whole.unload;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.util.Set;

/**
 * @author Whoopsunix
 *
 * 卸载 JMX 获取 StandardContext 注入 Tomcat Servlet 型内存马
 * Tomcat 7 8 9
 */
public class UnloadTomcatServletJMXMS {
    private static String NAME = "Whoopsunix";
    private static String pattern = "/WhoopsunixShell";


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

                Object container = standardContext.getClass().getSuperclass().getDeclaredMethod("findChild", String.class).invoke(standardContext, NAME);
                if (container != null) {
                    // 删除 servlet
                    standardContext.getClass().getSuperclass().getDeclaredMethod("removeChild", Class.forName("org.apache.catalina.Container")).invoke(standardContext, container);
                    // 删除 servlet 映射
                    standardContext.getClass().getDeclaredMethod("removeServletMapping", String.class).invoke(standardContext, pattern);
                    break;
                }
            }

        } catch (Exception e) {
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
