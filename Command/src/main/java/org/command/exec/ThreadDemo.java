package org.command.exec;

import org.command.resultGet.ExecResultGet;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Whoopsunix
 */
public class ThreadDemo {
    public static InputStream exec(String cmd) throws Exception {
        AtomicReference<InputStream> inputStreamRef = new AtomicReference<>();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String[] cmds = null;
                    if (System.getProperty("os.name").toLowerCase().contains("win")) {
                        cmds = new String[]{"cmd.exe", "/c", cmd};
                    } else {
                        cmds = new String[]{"/bin/sh", "-c", cmd};
                    }
                    InputStream inputStream = Runtime.getRuntime().exec(cmds).getInputStream();
                    inputStreamRef.set(inputStream);
                } catch (Exception e) {
                    // Handle the exception
                }
            }
        });
        thread.start();
        thread.join();

        return inputStreamRef.get();
    }

    public static void main(String[] args) throws Exception {
        InputStream inputStream = exec("ifconfig -a");
        ExecResultGet execResultGet = new ExecResultGet();
        System.out.println(execResultGet.stringBuilder(inputStream));
    }
}
