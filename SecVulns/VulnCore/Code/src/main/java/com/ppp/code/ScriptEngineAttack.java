package com.ppp.code;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * @author Whoopsunix
 */
public class ScriptEngineAttack {
    public Object eval(String script) throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");

        return engine.eval(script);
    }
}
