package com.example.springecho.search;

import com.example.springecho.gadget.SpringEcho;
import org.tools.ser.CC2Generator;

/**
 * @author Whoopsunix
 */
public class Search {
    public static void main(String[] args) throws Exception{
        CC2Generator cc2Generator = new CC2Generator();
        cc2Generator.make(SpringEcho.class);
    }
}
