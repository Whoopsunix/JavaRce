package com.ppp.command;

import java.io.InputStream;

/**
 * @author Whoopsunix
 */
public class RuntimeDemo {
    public static InputStream exec(String str) throws Exception {
        InputStream inputStream = null;
        String[] cmd = null;
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            cmd = new String[]{"cmd.exe", "/c", str};
        } else {
            cmd = new String[]{"/bin/sh", "-c", str};
        }
        if (cmd != null) {
            inputStream = Runtime.getRuntime().exec(cmd).getInputStream();
        }

        return inputStream;
    }

    public static void main(String[] args) throws Exception {
        InputStream inputStream = exec("ifconfig -a");
        ExecResultMod execResultMod = new ExecResultMod();
//        System.out.println(execResultGet.stringBuilder(inputStream));
//        System.out.println(execResultGet.byteArrayOutputStream(inputStream));
        System.out.println(execResultMod.scanner(inputStream));
//        System.out.println(execResultGet.bufferedReader(inputStream));
//        System.out.println(execResultGet.bufferedReader2(inputStream));
//        System.out.println(execResultGet.readNBytes(inputStream));

//        System.out.println(execResultGet.commons_io(inputStream));

//        System.out.println(execResultGet.spring_core(inputStream));

//        System.out.println(execResultGet.guava(inputStream));

    }
}
