package com.ppp.code;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.AviatorEvaluatorInstance;
import groovy.lang.GroovyShell;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * @author guchangan1
 */
public class AviatorScriptAttack {

    public Object aviatorEvaluator(String script) throws Exception {
        AviatorEvaluatorInstance evaluator = AviatorEvaluator.newInstance();

        return evaluator.execute(script);
    }
}
