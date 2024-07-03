package com.ppp.command;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Whoopsunix
 */
public class ProcessImplDemo {
    public static InputStream exec(String cmd) throws Exception {
        InputStream inputStream = null;

        String[] cmds = null;
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            cmds = new String[]{"cmd.exe", "/c", cmd};
        } else {
            cmds = new String[]{"/bin/sh", "-c", cmd};
        }
        Class<?> cls = Class.forName("java.lang.ProcessImpl");
        Method method = cls.getDeclaredMethod("start", String[].class, Map.class, String.class, ProcessBuilder.Redirect[].class, boolean.class);
        method.setAccessible(true);
        Process e = (Process) method.invoke(null, cmds, null, ".", null, true);
        inputStream = e.getInputStream();

        return inputStream;
    }

    public static void main(String[] args) throws Exception {
        InputStream inputStream = exec("ifconfig -a");
        ExecResultMod execResultMod = new ExecResultMod();
        System.out.println(execResultMod.stringBuilder(inputStream));
    }
}
