package com.ppp.command;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @author Whoopsunix
 */
public class MethodHandlesRuntime {
    public static void main(String[] args) throws Throwable {
        String cmd = "open -a Calculator.app";
        original(cmd);
    }

    public static void original(String cmd) throws Exception {
        Class<?> cls = Class.forName("java.lang.Runtime");
        Object runtime = cls.getMethod("getRuntime").invoke(null);
        cls.getMethod("exec", String.class).invoke(runtime, cmd);
    }

    public static void methodHandles(String cmd) throws Throwable {
        Class<?> cls = Class.forName("java.lang.Runtime");
        MethodHandle execMethod = MethodHandles.lookup().findVirtual(cls, "exec", MethodType.methodType(Process.class, String.class));
        execMethod.invoke(cls.getMethod("getRuntime").invoke(null), cmd);
    }
}
