package org.ppp.tools;

import sun.reflect.ReflectionFactory;

import java.lang.reflect.*;

/**
 * 反序列化工具类
 */
public class Reflections {

    public static void setAccessible(AccessibleObject member) {
        String versionStr = System.getProperty("java.version");
        int javaVersion = Integer.parseInt(versionStr.split("\\.")[0]);
        member.setAccessible(true);
    }

    public static Field getField(final Class<?> clazz, final String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            setAccessible(field);
        } catch (NoSuchFieldException ex) {
            if (clazz.getSuperclass() != null)
                field = getField(clazz.getSuperclass(), fieldName);
        }
        return field;
    }

    public static void setFieldValue(final Object obj, final String fieldName, final Object value) throws Exception {
        final Field field = getField(obj.getClass(), fieldName);
        field.set(obj, value);
    }

    public static Object getFieldValue(final Object obj, final String fieldName) throws Exception {
        final Field field = getField(obj.getClass(), fieldName);
        return field.get(obj);
    }

    public static Constructor<?> getFirstCtor(final String name) throws Exception {
        final Constructor<?> ctor = Class.forName(name).getDeclaredConstructors()[0];
        setAccessible(ctor);
        return ctor;
    }

    public static Constructor<?> getFirstCtor(Class clazz) throws Exception {
        final Constructor<?> ctor = clazz.getDeclaredConstructors()[0];
        setAccessible(ctor);
        return ctor;
    }

    public static Object newInstance(String className, Object... args) throws Exception {
        return getFirstCtor(className).newInstance(args);
    }

    public static <T> T createWithoutConstructor(Class<T> classToInstantiate)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return createWithConstructor(classToInstantiate, Object.class, new Class[0], new Object[0]);
    }

    @SuppressWarnings({"unchecked"})
    public static <T> T createWithConstructor(Class<T> classToInstantiate, Class<? super T> constructorClass, Class<?>[] consArgTypes, Object[] consArgs)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<? super T> objCons = constructorClass.getDeclaredConstructor(consArgTypes);
        setAccessible(objCons);
        Constructor<?> sc = ReflectionFactory.getReflectionFactory().newConstructorForSerialization(classToInstantiate, objCons);
        setAccessible(sc);
        return (T) sc.newInstance(consArgs);
    }

    public static void invokeMethod(Object obj, String methodName, Object... args){
        try {
            Class<?>[] argsClass = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                argsClass[i] = args[i].getClass();
                if(argsClass[i].equals(Integer.class))
                    argsClass[i] =Integer.TYPE;
                else if(argsClass[i].equals(Boolean.class))
                    argsClass[i] =Boolean.TYPE;
                else if(argsClass[i].equals(Byte.class))
                    argsClass[i] =Byte.TYPE;
                else if(argsClass[i].equals(Long.class))
                    argsClass[i] =Long.TYPE;
                else if(argsClass[i].equals(Double.class))
                    argsClass[i] =Double.TYPE;
                else if(argsClass[i].equals(Float.class))
                    argsClass[i] =Float.TYPE;
                else if(argsClass[i].equals(Character.class))
                    argsClass[i] =Character.TYPE;
                else if(argsClass[i].equals(Short.class))
                    argsClass[i] =Short.TYPE;
            }
            try {
                Method method = obj.getClass().getDeclaredMethod(methodName, argsClass);
                method.invoke(obj, args);
            }catch (NoSuchMethodException e){
                Method method = obj.getClass().getSuperclass().getDeclaredMethod(methodName, argsClass);
                method.invoke(obj, args);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object invokeMethod(Object obj, String methodName, Class[] argsClass, Object[] args) throws Exception {
        Method method;
        try {
            method = obj.getClass().getDeclaredMethod(methodName, argsClass);
        } catch (NoSuchMethodException e) {
            method = obj.getClass().getSuperclass().getDeclaredMethod(methodName, argsClass);
        }
        method.setAccessible(true);
        Object object = method.invoke(obj, args);
        return object;
    }

}
