//package org.command.exec;
//
//import org.command.exec.jni.JniCmdDemo2;
//import org.command.resultGet.ExecResultGet;
//import sun.misc.Unsafe;
//
//import java.io.InputStream;
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.util.Map;
//
///**
// * @author Whoopsunix
// */
//public class Exec {
//    public static void main(String[] args) throws Exception {
//        String cmd = "open -a Calculator.app";
////        cmd = "whoami";
////        cmd = "ifconfig -a";
////        runtime(cmd);
////        processBuilder(cmd);
////        processImpl(cmd);
////        pu(cmd);
////        pu_unsafe(cmd);
////        thread(cmd);
//        ScriptEngineManager(cmd);
////        jni(cmd);
//        // test
//    }
//
//    public static void exec(String str){
//        try {
//            String[] cmd = null;
//            if (System.getProperty("os.name").contains("windows")) {
//                cmd = new String[]{"cmd.exe", "/c", str};
//            } else {
//                cmd = new String[]{"/bin/bash", "-c", str};
//            }
//            if (cmd != null) {
//                Runtime.getRuntime().exec(cmd);
//            }
//        } catch (Exception e){
//
//        }
//
//    }
//
//    /**
//     *  Runtime
//     */
//    public static void runtime(String cmd) {
//        // base
//        try {
////            Runtime.getRuntime().exec(cmd);
//            Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c", cmd});
//        } catch (Exception e) {
//
//        }
//
//        // Reflections
////        try {
////            Class<?> cls = Class.forName("java.lang.Runtime");
////            Method method = cls.getDeclaredMethod("getRuntime");
////            method.setAccessible(true);
////            Runtime runtime = (Runtime) method.invoke(null);
////            runtime.exec(cmd);
////        } catch (Exception e) {
////
////        }
//    }
//
//    /**
//     *  ProcessBuilder
//     */
//    public static void processBuilder(String cmd) {
////        // base
////        try {
////            ProcessBuilder pb = new ProcessBuilder(new String[]{"/bin/bash", "-c", cmd});
////            pb.start();
////        } catch (Exception e) {
////
////        }
//
//        // Reflections
//        try {
//            Class<?> cls = Class.forName("java.lang.ProcessBuilder");
//            Constructor<?> constructor = cls.getDeclaredConstructor(String[].class);
//            constructor.setAccessible(true);
//            ProcessBuilder pb = (ProcessBuilder) constructor.newInstance(new Object[]{new String[]{"/bin/bash", "-c", cmd}});
//            Method method = cls.getDeclaredMethod("start");
//            method.setAccessible(true);
//            method.invoke(pb);
//        } catch (Exception e) {
//
//        }
//    }
//
//    /**
//     *  ProcessImpl
//     */
//    public static void processImpl(String cmd) {
//        // Reflections
//        String[] cmds = new String[]{"/bin/bash", "-c", cmd};
//        try {
//            Class<?> cls = Class.forName("java.lang.ProcessImpl");
//            Method method = cls.getDeclaredMethod("start", String[].class, Map.class, String.class, ProcessBuilder.Redirect[].class, boolean.class);
//            method.setAccessible(true);
//            Process e = (Process) method.invoke(null, cmds, null, ".", null, true);
//
//            System.out.println(ExecResultGet.normal(e.getInputStream()));
//        } catch (Exception e) {
//
//        }
//    }
//
//    /**
//     *  ProcessImpl & UnixProcess
//     */
//    public static void pu(String cmd) {
//        try {
//            String[] strs = new String[]{"/bin/bash", "-c", cmd};
//            Class<?> processClass = null;
//            try {
//                processClass = Class.forName("java.lang.UNIXProcess");
//            } catch (ClassNotFoundException e) {
//                processClass = Class.forName("java.lang.ProcessImpl");
//            }
//            Constructor<?> constructor = processClass.getDeclaredConstructors()[0];
//            constructor.setAccessible(true);
//
//            // arguments
//            byte[][] args = new byte[strs.length - 1][];
//            int size = args.length;
//
//            for (int i = 0; i < args.length; i++) {
//                args[i] = strs[i + 1].getBytes();
//                size += args[i].length;
//            }
//
//            byte[] argBlock = new byte[size];
//            int i = 0;
//            for (byte[] arg : args) {
//                System.arraycopy(arg, 0, argBlock, i, arg.length);
//                i += arg.length + 1;
//            }
//            int[] envc = new int[1];
//            int[] std_fds = new int[]{-1, -1, -1};
//
//
//            Object object = constructor.newInstance(
//                    toCString(strs[0]), argBlock, args.length,
//                    null, envc[0], null, std_fds, false
//            );
//            // 获取命令执行的InputStream
//            Method inMethod = object.getClass().getDeclaredMethod("getInputStream");
//            inMethod.setAccessible(true);
//            InputStream inputStream = (InputStream) inMethod.invoke(object);
//            System.out.println(ExecResultGet.normal(inputStream));
//        } catch (Exception e){
//
//        }
//    }
//
//    /**
//     *  ProcessImpl & UnixProcess by unsafe + Native
//     */
//    public static void pu_unsafe(String cmd) {
//        try {
//            String[] strs = new String[]{"/bin/bash", "-c", cmd};
//            Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
//            theUnsafeField.setAccessible(true);
//            Unsafe unsafe = (Unsafe) theUnsafeField.get(null);
//            Class processClass = null;
//
//            try {
//                processClass = Class.forName("java.lang.UNIXProcess");
//            } catch (ClassNotFoundException e) {
//                processClass = Class.forName("java.lang.ProcessImpl");
//            }
//            Object processObject = unsafe.allocateInstance(processClass);
//
//            // arguments
//            byte[][] args = new byte[strs.length - 1][];
//            int size = args.length;
//
//            for (int i = 0; i < args.length; i++) {
//                args[i] = strs[i + 1].getBytes();
//                size += args[i].length;
//            }
//
//            byte[] argBlock = new byte[size];
//            int i = 0;
//
//            for (byte[] arg : args) {
//                System.arraycopy(arg, 0, argBlock, i, arg.length);
//                i += arg.length + 1;
//            }
//
//            int[] envc = new int[1];
//            int[] std_fds = new int[]{-1, -1, -1};
//            Field launchMechanismField = processClass.getDeclaredField("launchMechanism");
//            Field helperpathField = processClass.getDeclaredField("helperpath");
//            launchMechanismField.setAccessible(true);
//            helperpathField.setAccessible(true);
//            Object launchMechanismObject = launchMechanismField.get(processObject);
//            byte[] helperpathObject = (byte[]) helperpathField.get(processObject);
//
//            int ordinal = (int) launchMechanismObject.getClass().getMethod("ordinal").invoke(launchMechanismObject);
//
//            Method forkMethod = processClass.getDeclaredMethod("forkAndExec", new Class[]{
//                    int.class, byte[].class, byte[].class, byte[].class, int.class,
//                    byte[].class, int.class, byte[].class, int[].class, boolean.class
//            });
//
//            forkMethod.setAccessible(true);
//
//            int pid = (int) forkMethod.invoke(processObject, new Object[]{
//                    ordinal + 1, helperpathObject, toCString(strs[0]), argBlock, args.length,
//                    null, envc[0], null, std_fds, false
//            });
//
//            Method initStreamsMethod = processClass.getDeclaredMethod("initStreams", int[].class);
//            initStreamsMethod.setAccessible(true);
//            initStreamsMethod.invoke(processObject, std_fds);
//
//
//            Method getInputStreamMethod = processClass.getMethod("getInputStream");
//            getInputStreamMethod.setAccessible(true);
//            InputStream input = (InputStream) getInputStreamMethod.invoke(processObject);
//        }catch (Exception e){
//
//        }
//    }
//
//    public static byte[] toCString(String s) {
//        if (s == null)
//            return null;
//        byte[] bytes = s.getBytes();
//        byte[] result = new byte[bytes.length + 1];
//        System.arraycopy(bytes, 0,
//                result, 0,
//                bytes.length);
//        result[result.length - 1] = (byte) 0;
//        return result;
//    }
//
//
//    /**
//     *  Thread
//     */
//    public static void thread(String cmd) {
//        try {
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Runtime.getRuntime().exec(cmd);
//                    } catch (Exception e) {
//
//                    }
//                }
//            });
//            thread.start();
//        } catch (Exception e) {
//
//        }
//    }
//
//    /**
//     *  ScriptEngineManager
//     */
//    public static void ScriptEngineManager(String cmd) {
//        // base
////        try {
////            ScriptEngineManager manager = new ScriptEngineManager();
////            ScriptEngine engine = manager.getEngineByName("js");
////            // runtime
////            engine.eval("var runtime = java.lang./**/Runtime./**/getRuntime(); " +
////                    "var process = runtime.exec(\"" + cmd + "\"); " +
////                    "var inputStream = process.getInputStream(); " +
////                    "var inputStreamReader = new java.io.InputStreamReader(inputStream); " +
////                    "var bufferedReader = new java.io.BufferedReader(inputStreamReader); " +
////                    "var line; " +
////                    "while ((line = bufferedReader.readLine()) != null) { " +
////                    "    print(line); " +
////                    "}");
////        } catch (Exception e) {
////
////        }
//        // Reflections
//        try{
//            Class<?> clazz = Class.forName("javax.script.ScriptEngineManager");
//            Constructor<?> constructor = clazz.getConstructor();
//            constructor.setAccessible(true);
//            Object object = constructor.newInstance();
//            Method method = clazz.getMethod("getEngineByName", String.class);
//            method.setAccessible(true);
//            Object engine = method.invoke(object, "js");
//            method = engine.getClass().getMethod("eval", String.class);
//            method.setAccessible(true);
//            String payload = "var runtime = java.lang./**/Runtime./**/getRuntime(); " +
//                    "var process = runtime.exec(\"" + cmd + "\"); " +
//                    "var inputStream = process.getInputStream(); " +
//                    "var inputStreamReader = new java.io.InputStreamReader(inputStream); " +
//                    "var bufferedReader = new java.io.BufferedReader(inputStreamReader); " +
//                    "var line; " +
//                    "while ((line = bufferedReader.readLine()) != null) { " +
//                    "    print(line); " +
//                    "}";
//            method.invoke(engine, payload);
//
//        }catch (Exception e){
//
//        }
//    }
//
//    /**
//     * jni
//     */
//    public static void jni(String cmd){
//        new JniCmdDemo2().run(cmd);
//    }
//
//
//    // js.nashorn-compat
//
//}
