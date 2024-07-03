package com.ppp;

import java.io.File;
import java.util.Arrays;

/**
 * @author Whoopsunix
 *
 * 路径遍历
 */
public class FileDirectory {

    public static void main(String[] args) {
        String[] directory = listFiles("/tmp");
        System.out.println(Arrays.toString(directory));
    }

    public static String[] list(String filePath) {
        String[] files = new File(filePath).list();
        return files;
    }

    public static String[] listFiles(String filePath) {
        File[] fileLists = new File(filePath).listFiles();

        String[] arrayList = new String[]{};

        for (File file : fileLists) {
            if (file.isFile()) {
                arrayList = Arrays.copyOf(arrayList, arrayList.length + 1);
                arrayList[arrayList.length - 1] = file.getName();
            }
        }

        return arrayList;
    }
}
