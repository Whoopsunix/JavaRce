package org.springframework.expression;

/**
 * @author Whoopsunix
 */
public class Test {
    static {
        try {
            System.out.println("static Exec");
            Runtime.getRuntime().exec("open -a Calculator.app");
        } catch (Exception e) {
        }
    }
}
