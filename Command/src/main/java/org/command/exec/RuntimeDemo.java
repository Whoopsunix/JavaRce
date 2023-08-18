package org.command.exec;

import org.command.resultGet.ExecResultGet;

import java.io.InputStream;

/**
 * @author Whoopsunix
 */
public class RuntimeDemo {
    public static InputStream exec(String str) throws Exception {
        InputStream inputStream = null;
        String[] cmd = null;
        if (System.getProperty("os.name").contains("windows")) {
            cmd = new String[]{"cmd.exe", "/c", str};
        } else {
            cmd = new String[]{"/bin/bash", "-c", str};
        }
        if (cmd != null) {
            inputStream = Runtime.getRuntime().exec(cmd).getInputStream();
        }

        return inputStream;
    }

    public static void main(String[] args) throws Exception {
        InputStream inputStream = exec("ifconfig -a");
        ExecResultGet execResultGet = new ExecResultGet();
        System.out.println(execResultGet.normal(inputStream));
    }
}
