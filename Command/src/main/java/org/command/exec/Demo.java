package org.command.exec;

import org.command.resultGet.ExecResultGet;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author Whoopsunix
 */
public class Demo {
    public static InputStream exec(String cmd) throws Exception{
        InputStream inputStream = null;


        return inputStream;
    }

    public static InputStream reflect(String cmd) throws Exception{
        InputStream inputStream = null;


        return inputStream;
    }

    public static void main(String[] args) throws Exception{
        InputStream inputStream = exec("ifconfig -a");
        ExecResultGet execResultGet = new ExecResultGet();
        System.out.println(execResultGet.normal(inputStream));
    }
}
