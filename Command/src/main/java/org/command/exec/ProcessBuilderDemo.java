package org.command.exec;

import org.command.resultGet.ExecResultGet;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author Whoopsunix
 */
public class ProcessBuilderDemo {
    public static InputStream exec(String cmd) throws Exception {
        InputStream inputStream = null;

        ProcessBuilder pb = new ProcessBuilder(new String[]{"/bin/bash", "-c", cmd});
        inputStream = pb.start().getInputStream();

        return inputStream;
    }

    public static InputStream reflect(String cmd) throws Exception{
        InputStream inputStream = null;

        Class<?> cls = Class.forName("java.lang.ProcessBuilder");
        Constructor<?> constructor = cls.getDeclaredConstructor(String[].class);
        constructor.setAccessible(true);
        ProcessBuilder pb = (ProcessBuilder) constructor.newInstance(new Object[]{new String[]{"/bin/bash", "-c", cmd}});
        Method method = cls.getDeclaredMethod("start");
        method.setAccessible(true);
        inputStream = ((Process) method.invoke(pb)).getInputStream();

        return inputStream;
    }

    public static void main(String[] args) throws Exception {
        InputStream inputStream = exec("ifconfig -a");
        inputStream = reflect("ifconfig -a");
        ExecResultGet execResultGet = new ExecResultGet();
        System.out.println(execResultGet.normal(inputStream));
    }
}
