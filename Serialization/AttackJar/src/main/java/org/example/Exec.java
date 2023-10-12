package org.example;

/**
 * @author Whoopsunix
 */
public class Exec {
    public Exec() {
        try {
            Runtime.getRuntime().exec("open -a Calculator.app");
        } catch (Exception e) {
        }
    }

    static {
        try {
            Runtime.getRuntime().exec("open -a Calculator.app");
        } catch (Exception e) {
        }
    }
}
