package org.example;

/**
 * @author Whoopsunix
 */
public class ExecArg {
    public ExecArg() {
    }

    public ExecArg(String cmd) {
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (Exception e) {
        }
    }

    public void exec(String cmd) {
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (Exception e) {
        }
    }
}
