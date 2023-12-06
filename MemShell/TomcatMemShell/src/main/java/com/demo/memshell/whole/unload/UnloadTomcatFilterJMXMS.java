package com.demo.memshell.whole.unload;

import com.sun.jmx.mbeanserver.NamedObject;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Set;

/**
 * @author Whoopsunix
 *
 * 卸载 JMX 获取 StandardContext 注入 Tomcat Filter 型内存马
 * Tomcat 7 8 9
 */
public class UnloadTomcatFilterJMXMS {

    private static String NAME = "TomcatServletThreadMS";

    static {
        try {
            // 获取 MBeanServer, 用于注册和管理 MBeans
            javax.management.MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
//            javax.management.MBeanServer mbeanServer = org.apache.tomcat.util.modeler.Registry.getRegistry(null, null).getMBeanServer();
            // 获取 DefaultMBeanServerInterceptor, 用于处理 MBeanServer 的请求和操作
            com.sun.jmx.interceptor.DefaultMBeanServerInterceptor defaultMBeanServerInterceptor = (com.sun.jmx.interceptor.DefaultMBeanServerInterceptor) getFieldValue(mbeanServer, "mbsInterceptor");
            // 获取 Repository, 用于存储注册的 MBean
            com.sun.jmx.mbeanserver.Repository repository = (com.sun.jmx.mbeanserver.Repository) getFieldValue(defaultMBeanServerInterceptor, "repository");
            // 查询匹配指定条件的 MBean spring 和 tomcat 不同
            Set<NamedObject> objectSet = repository.query(new javax.management.ObjectName("Catalina:host=localhost,name=NonLoginAuthenticator,type=Valve,*"), null);
            if (objectSet.size() == 0) {
                // springboot 中是 Tomcat
                objectSet = repository.query(new javax.management.ObjectName("Tomcat:host=localhost,name=NonLoginAuthenticator,type=Valve,*"), null);
            }
            boolean flag = false;
            for (NamedObject namedObject : objectSet) {
                if (flag) break;
                javax.management.DynamicMBean dynamicMBean = namedObject.getObject();
                org.apache.catalina.authenticator.AuthenticatorBase authenticatorBase = (org.apache.catalina.authenticator.AuthenticatorBase) getFieldValue(dynamicMBean, "resource");
                org.apache.catalina.core.StandardContext standardContext = (org.apache.catalina.core.StandardContext) getFieldValue(authenticatorBase, "context");

                Object filterDef = standardContext.getClass().getDeclaredMethod("findFilterDef", String.class).invoke(standardContext, NAME);

                if (filterDef == null) {
                    continue;
                }

                // tomcat 7
                // org.apache.catalina.deploy.FilterDef
                // tomcat 8 9
                // org.apache.tomcat.util.descriptor.web.FilterDef
                try {
                    // 移除 FilterDef
                    standardContext.getClass().getDeclaredMethod("removeFilterDef", filterDef.getClass()).invoke(standardContext, filterDef);

                    // 移除 filterMap
                    Object filterMaps = getFieldValue(standardContext, "filterMaps");
                    Object array = getFieldValue(filterMaps, "array");
                    if (array != null && array.getClass().isArray()) {
                        int length = Array.getLength(array);
                        for (int i = 0; i < length; i++) {
                            Object element = Array.get(array, i);
                            String name = getFieldValue(element, "filterName").toString();
                            if (name.equals(NAME)) {
                                standardContext.getClass().getDeclaredMethod("removeFilterMap", element.getClass()).invoke(standardContext, element);
                                break;
                            }
                        }
                    }

                    // 移除 ApplicationFilterConfig
                    java.util.Map filterConfigs = (java.util.Map) getFieldValue(standardContext, "filterConfigs");
                    java.lang.reflect.Constructor constructor = null;
                    try {
                        constructor = Class.forName("org.apache.catalina.core.ApplicationFilterConfig").getDeclaredConstructor(Class.forName("org.apache.catalina.Context"), Class.forName("org.apache.catalina.deploy.FilterDef"));

                    } catch (ClassNotFoundException e) {
                        constructor = Class.forName("org.apache.catalina.core.ApplicationFilterConfig").getDeclaredConstructor(Class.forName("org.apache.catalina.Context"), Class.forName("org.apache.tomcat.util.descriptor.web.FilterDef"));
                    }
                    constructor.setAccessible(true);
                    filterConfigs.remove(NAME);
                    flag = true;
                } catch (Exception e) {
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
