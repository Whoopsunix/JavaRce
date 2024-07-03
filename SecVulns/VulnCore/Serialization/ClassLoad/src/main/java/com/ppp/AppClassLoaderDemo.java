package com.ppp;

import java.lang.reflect.Method;
import java.util.Base64;

/**
 * @author Whoopsunix
 */
public class AppClassLoaderDemo {
    public static void main(String[] args) throws Exception{
        /**
         * defineClass
         *  Base64解密后加载
         */
//        // generate
////        String b64Str = new B64().encodeJavaClass(Exec.class);
//        // open -a Calculator.app
        String b64Str = "yv66vgAAADQAIgoABwAVCgAWABcIABgKABYAGQcAGgcAGwcAHAEABjxpbml0PgEAAygpVgEABENvZGUBAA9MaW5lTnVtYmVyVGFibGUBABJMb2NhbFZhcmlhYmxlVGFibGUBAAR0aGlzAQASTG9yZy9leGFtcGxlL0V4ZWM7AQANU3RhY2tNYXBUYWJsZQcAGwcAGgEACDxjbGluaXQ+AQAKU291cmNlRmlsZQEACUV4ZWMuamF2YQwACAAJBwAdDAAeAB8BABZvcGVuIC1hIENhbGN1bGF0b3IuYXBwDAAgACEBABNqYXZhL2xhbmcvRXhjZXB0aW9uAQAQb3JnL2V4YW1wbGUvRXhlYwEAEGphdmEvbGFuZy9PYmplY3QBABFqYXZhL2xhbmcvUnVudGltZQEACmdldFJ1bnRpbWUBABUoKUxqYXZhL2xhbmcvUnVudGltZTsBAARleGVjAQAnKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9sYW5nL1Byb2Nlc3M7ACEABgAHAAAAAAACAAEACAAJAAEACgAAAGoAAgACAAAAEiq3AAG4AAISA7YABFenAARMsQABAAQADQAQAAUAAwALAAAAFgAFAAAABwAEAAkADQALABAACgARAAwADAAAAAwAAQAAABIADQAOAAAADwAAABAAAv8AEAABBwAQAAEHABEAAAgAEgAJAAEACgAAAE8AAgABAAAADrgAAhIDtgAEV6cABEuxAAEAAAAJAAwABQADAAsAAAASAAQAAAAQAAkAEgAMABEADQATAAwAAAACAAAADwAAAAcAAkwHABEAAAEAEwAAAAIAFA==";

        byte[] bytes = Base64.getDecoder().decode(b64Str);
//        ClassLoader classLoader = this.getClass().getClassLoader();
//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        ClassLoader classLoader = new ClassLoader() {};

        Method defineClassMethod = Class.forName("java.lang.ClassLoader").getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
        defineClassMethod.setAccessible(true);
        Class<?> loadedClass = (Class<?>) defineClassMethod.invoke(classLoader, bytes, 0, bytes.length);
        loadedClass.newInstance();

        /**
         * Class.forName
         */
//        // static
//        Class cls = Class.forName("org.example.Exec");
//        // 构造方法
//        cls.newInstance();

        /**
         * ClassLoader
         */
        // static 和 构造方法都不会触发
//        Class cls1 = ClassLoader.getSystemClassLoader().loadClass("org.example.Exec");
//        cls1.newInstance();

    }
}
