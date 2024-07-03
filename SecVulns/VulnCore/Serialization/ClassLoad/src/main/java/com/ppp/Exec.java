package com.ppp;

/**
 * @author Whoopsunix
 */
public class Exec {
    public Exec() {
        try {
            System.out.println("Exec");
            Runtime.getRuntime().exec("open -a Calculator.app");
        } catch (Exception e) {
        }
    }

    static {
        try {
            System.out.println("static Exec");
            Runtime.getRuntime().exec("open -a Calculator.app");
        } catch (Exception e) {
        }
    }
}
