package com.ppp.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.AviatorEvaluatorInstance;

/**
 * @author guchangan1
 */
public class AviatorAttack {

    public static Object execute(String script) throws Exception {
        AviatorEvaluatorInstance evaluator = AviatorEvaluator.newInstance();

        return evaluator.execute(script);
    }
}
