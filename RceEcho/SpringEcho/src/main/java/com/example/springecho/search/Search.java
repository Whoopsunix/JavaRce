package com.example.springecho.search;

import com.example.springecho.gadget.SpringEcho;
import org.tools.ser.CC4Generator;

/**
 * @author Whoopsunix
 */
public class Search {
    public static void main(String[] args) throws Exception{
        CC4Generator cc4Generator = new CC4Generator();
        cc4Generator.make(SpringEcho.class);
    }
}
