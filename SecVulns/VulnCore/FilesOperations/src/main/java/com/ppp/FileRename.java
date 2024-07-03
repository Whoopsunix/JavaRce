package com.ppp;

import java.io.File;

/**
 * @author Whoopsunix
 * <p>
 * 文件重命名
 */
public class FileRename {
    public static void main(String[] args) {

    }

    public static boolean rename(String oldFile, String newFile) {
        File of = new File(oldFile);
        File nf = new File(newFile);
        if (of.isFile() && of.exists()) {
            if (of.renameTo(nf)) {
                return true;
            }
        }
        return false;
    }
}
