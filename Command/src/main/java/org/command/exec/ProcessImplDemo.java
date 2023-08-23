package org.command.exec;

import org.command.resultGet.ExecResultGet;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Whoopsunix
 */
public class ProcessImplDemo {
    public static InputStream reflect(String cmd) throws Exception {
        InputStream inputStream = null;

        String[] cmds = new String[]{"/bin/bash", "-c", cmd};
        Class<?> cls = Class.forName("java.lang.ProcessImpl");
        Method method = cls.getDeclaredMethod("start", String[].class, Map.class, String.class, ProcessBuilder.Redirect[].class, boolean.class);
        method.setAccessible(true);
        Process e = (Process) method.invoke(null, cmds, null, ".", null, true);
        inputStream = e.getInputStream();

        return inputStream;
    }

    public static void main(String[] args) throws Exception {
        InputStream inputStream = reflect("ifconfig -a");
        ExecResultGet execResultGet = new ExecResultGet();
        System.out.println(execResultGet.stringBuilder(inputStream));
    }
}
