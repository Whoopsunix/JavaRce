package org.example;

import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @author Whoopsunix
 */
public class SPEL {
    public static void spel(String payload) {
        new SpelExpressionParser().parseExpression(payload).getValue();
    }
}
