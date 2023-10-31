package org.example;

import org.tools.encryption.B64;

import java.lang.reflect.Constructor;

/**
 * @author Whoopsunix
 */
public class UnsafeDemo {
    public static void main(String[] args) throws Exception {
        String b64Str = new B64().encodeJavaClass(Exec.class);
        defineClass_unsafe(b64Str, "org.example.Exec");
    }

    // sun.misc.Unsafe
    public static void defineClass_unsafe(String calcBase64, String className) throws Exception {
//        sun.misc.Unsafe unsafe = sun.misc.Unsafe.getUnsafe();
        Class cls = Class.forName("sun.misc.Unsafe");
        Constructor constructor = cls.getDeclaredConstructor();
        constructor.setAccessible(true);
        sun.misc.Unsafe unsafe = (sun.misc.Unsafe) constructor.newInstance();

        byte[] bytes = java.util.Base64.getDecoder().decode(calcBase64);
        System.out.println(bytes.length);
        Class cls1 = unsafe.defineClass(className, bytes, 0, bytes.length, null, null);
        cls1.newInstance();
    }
}
