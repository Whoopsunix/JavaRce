package org.command.exec;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.InputStream;

/**
 * @author Whoopsunix
 */
public class ScriptEngineManagerDemo {
    public static InputStream exec(String cmd) throws Exception {
        InputStream inputStream = null;

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        // runtime
        engine.eval("var runtime = java.lang./**/Runtime./**/getRuntime(); " +
                "var process = runtime.exec(\"" + cmd + "\"); " +
                "var inputStream = process.getInputStream(); " +
                "var inputStreamReader = new java.io.InputStreamReader(inputStream); " +
                "var bufferedReader = new java.io.BufferedReader(inputStreamReader); " +
                "var line; " +
                "while ((line = bufferedReader.readLine()) != null) { " +
                "    print(line); " +
                "}");

        return inputStream;
    }

    public static void main(String[] args) throws Exception {
        InputStream inputStream = exec("ifconfig -a");
    }
}
