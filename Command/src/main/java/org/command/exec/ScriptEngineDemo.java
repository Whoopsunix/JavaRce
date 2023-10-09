package org.command.exec;

import org.command.resultGet.ExecResultGet;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.InputStream;

/**
 * @author Whoopsunix
 */
public class ScriptEngineDemo {
    public static InputStream exec(String cmd) throws Exception {
        InputStream inputStream = null;

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");

        // 也可以直接全部写到js里
        // runtime
//        engine.eval("var runtime = java.lang./**/Runtime./**/getRuntime(); " +
//                "var process = runtime.exec(\"" + cmd + "\"); " +
//                "var inputStream = process.getInputStream(); " +
//                "var inputStreamReader = new java.io.InputStreamReader(inputStream); " +
//                "var bufferedReader = new java.io.BufferedReader(inputStreamReader); " +
//                "var line; " +
//                "while ((line = bufferedReader.readLine()) != null) { " +
//                "    print(line); " +
//                "}");
        // 直接返回对象
        Object obj = engine.eval("var runtime = java.lang./**/Runtime./**/getRuntime();" +
                "var process = runtime.exec(\"hostname\");" +
                "var inputStream = process.getInputStream();" +
                "var scanner = new java.util.Scanner(inputStream,\"GBK\").useDelimiter(\"\\\\A\");" +
                "var result = scanner.hasNext() ? scanner.next() : \"\";" +
                "scanner.close();" +
                "result;");
        System.out.println(obj.toString());


        engine.eval("var runtime = java.lang./**/Runtime./**/getRuntime(); " +
                "var process = runtime.exec(\"" + cmd + "\"); " +
                "var inputStream = process.getInputStream(); ");
        // 获取对象
        inputStream = (InputStream) engine.eval("inputStream;");



        return inputStream;
    }

    public static void main(String[] args) throws Exception {
        InputStream inputStream = exec("ifconfig -a");
        ExecResultGet execResultGet = new ExecResultGet();
        System.out.println(execResultGet.scanner(inputStream));
    }
}
