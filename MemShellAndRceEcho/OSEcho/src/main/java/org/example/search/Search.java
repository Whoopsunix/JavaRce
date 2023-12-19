package org.example.search;

import org.example.gadgets.*;
import org.ppp.tools.ser.CC4Generator;


/**
 * @author Whoopsunix
 */
public class Search {
    public static void main(String[] args) throws Exception{
        CC4Generator cc4Generator = new CC4Generator();
        cc4Generator.make(WinEcho.class);
    }
}
