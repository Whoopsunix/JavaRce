package org.example;

/**
 * @author Whoopsunix
 */
public class ExecArg {
    public ExecArg() {
    }

    private ExecArg(String cmd) {
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (Exception e) {
        }
    }

    public ExecArg(Integer num) {
        try {
            if (num == 123) {
                Runtime.getRuntime().exec("open -a Calculator.app");
            }

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
