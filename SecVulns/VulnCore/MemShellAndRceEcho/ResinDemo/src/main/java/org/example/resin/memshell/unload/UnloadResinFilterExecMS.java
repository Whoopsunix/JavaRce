package org.example.resin.memshell.unload;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Whoopsunix
 */
public class UnloadResinFilterExecMS {
    private static String NAME = "Whoopsunix";
    private static String CLASSNAME = "ResinFilterExecMS";
    private static String PATTERN = "/WhoopsunixShell";

    static {
        try {
            UnloadResinFilterExecMS resinFilterExecMS = new UnloadResinFilterExecMS();

            Thread[] threads = (Thread[]) getFieldValue(Thread.currentThread().getThreadGroup(), "threads");

            for (int i = 0; i < threads.length; i++) {
                try {
                    Class cls = threads[i].currentThread().getContextClassLoader().loadClass("com.caucho.server.dispatch.ServletInvocation");
                    Object contextRequest = cls.getMethod("getContextRequest").invoke(null);
                    Object webapp = contextRequest.getClass().getMethod("getWebApp").invoke(contextRequest);
                    if (webapp == null)
                        continue;

                    if (isInject(webapp, resinFilterExecMS)) {
                        break;
                    }

                    inject(webapp, resinFilterExecMS);

                } catch (Exception e) {

                }
            }
        } catch (Exception e) {
        }
    }

    public static boolean isInject(Object webapp, Object object) {
        try {
            /**
             * Object chain = ((HttpServletRequestImpl) servletRequest)._invocation.getFilterChain();
             * 需要排查是不是因为 chain 中有所以删不掉
             */

            String NAME = (String) getFieldValue(object, "NAME");
            String PATTERN = (String) getFieldValue(object, "PATTERN");
// ((WebApp) webapp)._filterChainCache.clear();
            Object _filterMapper = getFieldValue(webapp, "_filterMapper");
            Object _filterMapper_filterManager = getFieldValue(_filterMapper, "_filterManager");
            HashMap _filterMapper_filters = (HashMap) getFieldValue(_filterMapper_filterManager, "_filters");
            if (_filterMapper_filters.containsKey(NAME)) {
                _filterMapper_filters.remove(NAME);
            }
            // 同时匹配到路径和类名才删除，避免对业务产生影响
            ArrayList _filterMap = (ArrayList) getFieldValue(_filterMapper, "_filterMap");
            for (int i = 0; i < _filterMap.size(); i++) {
                Object filterMap = _filterMap.get(i);
                String _filterName = (String) getFieldValue(filterMap, "_filterName");
                String _urlPattern = (String) getFieldValue(filterMap, "_urlPattern");
                if (_filterName.contains(NAME) && _urlPattern.contains(PATTERN)) {
                    _filterMap.remove(i);
                    break;
                }
            }

            Object _filterManager = getFieldValue(webapp, "_filterManager");
            HashMap _filters = (HashMap) getFieldValue(_filterManager, "_filters");
            if (_filters.containsKey(NAME)) {
                _filters.remove(NAME);
            }
            HashMap _urlPatterns = (HashMap) getFieldValue(_filterManager, "_urlPatterns");
            if (_urlPatterns.containsKey(NAME)) {
                _urlPatterns.remove(NAME);
            }

            // 在访问 Filter 后， _bean _instances 会被添加
            Object _bean = getFieldValue(_filterManager, "_bean");
            if (_bean != null) {
                Object _rawClass = getFieldValue(_bean, "_rawClass");
                String name = (String) getFieldValue(_rawClass, "name");
                if (name.contains(CLASSNAME)) {
                    setFieldValue(_filterManager, "_bean", null);
                }
            }

            HashMap _instances = (HashMap) getFieldValue(_filterManager, "_instances");
            if (_instances.containsKey(NAME)) {
                _instances.remove(NAME);
            }
            // 没去找调用了，直接写在这怕漏掉
            HashMap _servletNames = (HashMap) getFieldValue(_filterManager, "_servletNames");
            if (_servletNames.containsKey(NAME)) {
                _servletNames.remove(NAME);
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void inject(Object webapp, Object object) {
        return;
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

    public static Object invokeMethod(Object obj, String methodName, Class[] argsClass, Object[] args) {
        try {
            Method method;
            try {
                method = obj.getClass().getDeclaredMethod(methodName, argsClass);
            } catch (NoSuchMethodException e) {
                method = obj.getClass().getSuperclass().getDeclaredMethod(methodName, argsClass);
            }
            method.setAccessible(true);
            Object object = method.invoke(obj, args);
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
