package com.ppp;

import java.io.File;

/**
 * @author Whoopsunix
 *
 * 文件删除
 */
public class FileDelete {
    public static boolean delete(String filePath) {
        File delFile = new File(filePath);
        if (delFile.isFile() && delFile.exists()) {
            if (delFile.delete()) {
                return true;
            }
        }
        return false;
    }
}
