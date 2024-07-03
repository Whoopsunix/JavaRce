package com.ppp.springboot.vul.files;

/**
 * @author Whoopsunix
 */
public class FileUtils {
    public static String getResourcePath(){
        String resourcePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            if (resourcePath.startsWith("/")) {
                resourcePath = resourcePath.substring(1);
            }
        }
        return  resourcePath;
    }
}
