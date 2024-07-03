package com.ppp.command;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author Whoopsunix
 */
public class ProcessBuilderDemo {
    public static InputStream exec(String cmd) throws Exception {
        InputStream inputStream = null;

        String[] cmds = null;
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            cmds = new String[]{"cmd.exe", "/c", cmd};
        } else {
            cmds = new String[]{"/bin/sh", "-c", cmd};
        }

        ProcessBuilder pb = new ProcessBuilder(cmds);
        inputStream = pb.start().getInputStream();

        return inputStream;
    }

    public static InputStream reflect(String cmd) throws Exception{
        InputStream inputStream = null;

        Class<?> cls = Class.forName("java.lang.ProcessBuilder");
        Constructor<?> constructor = cls.getDeclaredConstructor(String[].class);
        constructor.setAccessible(true);
        ProcessBuilder pb = (ProcessBuilder) constructor.newInstance(new Object[]{new String[]{"/bin/sh", "-c", cmd}});
        Method method = cls.getDeclaredMethod("start");
        method.setAccessible(true);
        inputStream = ((Process) method.invoke(pb)).getInputStream();

        return inputStream;
    }

    public static void main(String[] args) throws Exception {
        InputStream inputStream = exec("ifconfig -a");
        inputStream = reflect("ifconfig -a");
        ExecResultMod execResultMod = new ExecResultMod();
        System.out.println(execResultMod.stringBuilder(inputStream));
    }
}
