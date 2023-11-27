package com.demo.memshell.unload;

import com.sun.jmx.mbeanserver.NamedObject;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

/**
 * @author Whoopsunix
 *
 * 卸载 JMX 获取 StandardContext 注入 Tomcat Listener 型内存马
 * Tomcat 7 8 9
 */
public class UnloadTomcatListenerJMXMS {
    private static String NAME = "TomcatListenerJMXMS";

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
            for (NamedObject namedObject : objectSet) {
                javax.management.DynamicMBean dynamicMBean = namedObject.getObject();
                Object authenticatorBase = getFieldValue(dynamicMBean, "resource");
                Object standardContext =  getFieldValue(authenticatorBase, "context");

                /**
                 * 卸载 Listener
                 */
                List<Object> applicationEventListeners = (List<Object>) getFieldValue(standardContext, "applicationEventListenersList");
                for (Object applicationEventListener : applicationEventListeners) {
                    if (applicationEventListener.getClass().getName().contains(NAME)) {
                        applicationEventListeners.remove(applicationEventListener);
                        break;
                    }
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
