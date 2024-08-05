package com.ppp;


import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Base64;

/**
 * @author Whoopsunix
 */
public class UnsafeDemo {
    public static void main(String[] args) throws Exception {
        test1();

        System.out.println(1);
    }

    public static void test1() throws Exception {
        addModule();

        Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, Integer.TYPE, Integer.TYPE);
        defineClass.setAccessible(true);
        byte[] bytecode = Base64.getDecoder().decode("yv66vgAAADQALwoACgAXCQAYABkIABoKABsAHAoAHQAeCAAfCgAdACAHACEHACIHACMBAAY8aW5pdD4BAAMoKVYBAARDb2RlAQAPTGluZU51bWJlclRhYmxlAQASTG9jYWxWYXJpYWJsZVRhYmxlAQAEdGhpcwEAJUxvcmcvc3ByaW5nZnJhbWV3b3JrL2V4cHJlc3Npb24vVGVzdDsBAAg8Y2xpbml0PgEADVN0YWNrTWFwVGFibGUHACEBAApTb3VyY2VGaWxlAQAJVGVzdC5qYXZhDAALAAwHACQMACUAJgEAC3N0YXRpYyBFeGVjBwAnDAAoACkHACoMACsALAEAFm9wZW4gLWEgQ2FsY3VsYXRvci5hcHAMAC0ALgEAE2phdmEvbGFuZy9FeGNlcHRpb24BACNvcmcvc3ByaW5nZnJhbWV3b3JrL2V4cHJlc3Npb24vVGVzdAEAEGphdmEvbGFuZy9PYmplY3QBABBqYXZhL2xhbmcvU3lzdGVtAQADb3V0AQAVTGphdmEvaW8vUHJpbnRTdHJlYW07AQATamF2YS9pby9QcmludFN0cmVhbQEAB3ByaW50bG4BABUoTGphdmEvbGFuZy9TdHJpbmc7KVYBABFqYXZhL2xhbmcvUnVudGltZQEACmdldFJ1bnRpbWUBABUoKUxqYXZhL2xhbmcvUnVudGltZTsBAARleGVjAQAnKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9sYW5nL1Byb2Nlc3M7ACEACQAKAAAAAAACAAEACwAMAAEADQAAAC8AAQABAAAABSq3AAGxAAAAAgAOAAAABgABAAAABgAPAAAADAABAAAABQAQABEAAAAIABIADAABAA0AAABbAAIAAQAAABayAAISA7YABLgABRIGtgAHV6cABEuxAAEAAAARABQACAADAA4AAAAWAAUAAAAJAAgACgARAAwAFAALABUADQAPAAAAAgAAABMAAAAHAAJUBwAUAAABABUAAAACABY=");
        Class clazz = (Class) defineClass.invoke(Thread.currentThread().getContextClassLoader(), bytecode, 0, bytecode.length);
        clazz.newInstance();
    }

    public static void addModule() throws Exception{
//        Class unsafeClass = Class.forName("sun.misc.Unsafe");
//        Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
//        unsafeField.setAccessible(true);
//        Unsafe unsafe = (Unsafe) unsafeField.get(null);
//
//        Module module = Object.class.getModule();
//        Class cls = UnsafeDemo.class;
//        long offset = unsafe.objectFieldOffset(Class.class.getDeclaredField("module"));
//
//        unsafe.getAndSetObject(cls, offset, module);
//        //        unsafe.putObject(cls, offset, module);

        try {
            Class unsafeClass = Class.forName("sun.misc.Unsafe");
            Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            Unsafe unsafe = (Unsafe) unsafeField.get(null);
            Method method = Class.class.getDeclaredMethod("getModule");
            method.setAccessible(true);
            Object module = method.invoke(Object.class);
            Class cls = UnsafeDemo.class;
            long offset = unsafe.objectFieldOffset(Class.class.getDeclaredField("module"));
            Method getAndSetObjectMethod = unsafeClass.getMethod("getAndSetObject", Object.class, long.class, Object.class);
            getAndSetObjectMethod.setAccessible(true);
            getAndSetObjectMethod.invoke(unsafe, cls, offset, module);
        } catch (Exception e) {

        }
    }
}
