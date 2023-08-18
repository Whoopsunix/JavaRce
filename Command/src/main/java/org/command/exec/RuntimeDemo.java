package org.command.exec;

/**
 * @author Whoopsunix
 */
public class RuntimeDemo {
    public static void exec(String str){
        try {
            String[] cmd = null;
            if (System.getProperty("os.name").contains("windows")) {
                cmd = new String[]{"cmd.exe", "/c", str};
            } else {
                cmd = new String[]{"/bin/bash", "-c", str};
            }
            if (cmd != null) {
                Runtime.getRuntime().exec(cmd);
            }
        } catch (Exception e){

        }
    }
}
