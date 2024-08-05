package com.ppp.code;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;

/**
 * @author Whoopsunix
 * 参考：https://www.cnblogs.com/yyhuni/p/18012041
 */
public class GroovyDemo {
    public static void main(String[] args) throws Exception{
        /**
         * MethodClosure
         */
        // Runtime.getRuntime().exec
//        MethodClosure mc = new MethodClosure(Runtime.getRuntime(), "exec");
//        mc.call("open -a Calculator.app");
        // 字符串
//        MethodClosure mc = new MethodClosure("open -a Calculator.app", "execute");
//        mc.call();

        /**
         * GroovyShell
         */
//        GroovyShell groovyShell = new GroovyShell();
//        String cmd = "\"whoami\".execute().text";
//        // 还可以通过文件 url 等
//        System.out.println(groovyShell.evaluate(cmd));



        /**
         * GroovyScriptEngine
         */
//        GroovyScriptEngine scriptEngine = new GroovyScriptEngine("Command/src/main/java/org/command/code");
////        GroovyScriptEngine scriptEngine = new GroovyScriptEngine("http://127.0.0.1:1234/");
//        scriptEngine.run("Groovy.groovy", "");


        /**
         * GroovyScriptEvaluator
         */
        // StaticScriptSource
//        GroovyScriptEvaluator groovyScriptEvaluator = new GroovyScriptEvaluator();
//        ScriptSource scriptSource = new StaticScriptSource("\"whoami\".execute().text");
//        System.out.println(groovyScriptEvaluator.evaluate(scriptSource));

        // ResourceScriptSource
//        Resource urlResource = new UrlResource("http://127.0.0.1:8888/exp.groovy");
//        ScriptSource source = new ResourceScriptSource(urlResource);
//        System.out.println(groovyScriptEvaluator.evaluate(source));


        /**
         * GroovyClassLoader
         */
        GroovyClassLoader classLoader = new GroovyClassLoader();
        Class clazz = classLoader.parseClass("class Test {\n" +
                "    static void main(String[] args) {\n" +
                "        GroovyShell groovyShell = new GroovyShell();\n" +
                "        String cmd = \"\\\"whoami\\\".execute().text\";\n" +
                "        println(groovyShell.evaluate(cmd).toString());\n" +
                "    }\n" +
                "}");
        GroovyObject object = (GroovyObject) clazz.newInstance();
        object.invokeMethod("main", "");

        // loadClass
//        String code = "yv66vgAAADQALwoACgAXCQAYABkIABoKABsAHAoAHQAeCAAfCgAdACAHACEHACIHACMBAAY8aW5pdD4BAAMoKVYBAARDb2RlAQAPTGluZU51bWJlclRhYmxlAQASTG9jYWxWYXJpYWJsZVRhYmxlAQAEdGhpcwEAJUxvcmcvc3ByaW5nZnJhbWV3b3JrL2V4cHJlc3Npb24vVGVzdDsBAAg8Y2xpbml0PgEADVN0YWNrTWFwVGFibGUHACEBAApTb3VyY2VGaWxlAQAJVGVzdC5qYXZhDAALAAwHACQMACUAJgEAC3N0YXRpYyBFeGVjBwAnDAAoACkHACoMACsALAEAFm9wZW4gLWEgQ2FsY3VsYXRvci5hcHAMAC0ALgEAE2phdmEvbGFuZy9FeGNlcHRpb24BACNvcmcvc3ByaW5nZnJhbWV3b3JrL2V4cHJlc3Npb24vVGVzdAEAEGphdmEvbGFuZy9PYmplY3QBABBqYXZhL2xhbmcvU3lzdGVtAQADb3V0AQAVTGphdmEvaW8vUHJpbnRTdHJlYW07AQATamF2YS9pby9QcmludFN0cmVhbQEAB3ByaW50bG4BABUoTGphdmEvbGFuZy9TdHJpbmc7KVYBABFqYXZhL2xhbmcvUnVudGltZQEACmdldFJ1bnRpbWUBABUoKUxqYXZhL2xhbmcvUnVudGltZTsBAARleGVjAQAnKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9sYW5nL1Byb2Nlc3M7ACEACQAKAAAAAAACAAEACwAMAAEADQAAAC8AAQABAAAABSq3AAGxAAAAAgAOAAAABgABAAAABgAPAAAADAABAAAABQAQABEAAAAIABIADAABAA0AAABbAAIAAQAAABayAAISA7YABLgABRIGtgAHV6cABEuxAAEAAAARABQACAADAA4AAAAWAAUAAAAJAAgACgARAAwAFAALABUADQAPAAAAAgAAABMAAAAHAAJUBwAUAAABABUAAAACABY=";
//        new groovy.lang.GroovyClassLoader().defineClass(null, java.util.Base64.getDecoder().decode(code)).newInstance();

    }
}
