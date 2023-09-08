package org.example;

/**
 * @author Whoopsunix
 */
public class Exec {
    public Exec() {
    }

    static {
        try {
            Runtime.getRuntime().exec("open -a Calculator.app");
        } catch (Exception e){
        }
    }
}
