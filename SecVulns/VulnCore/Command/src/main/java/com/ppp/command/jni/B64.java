package com.ppp.command.jni;

import com.sun.org.apache.bcel.internal.Repository;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;

import java.util.Arrays;
import java.util.Base64;

/**
 * @author Whoopsunix
 */
public class B64 {
    public static void main(String[] args) {
//        encode_obj(Calc.class);
    }

    public static String encode_obj(Class cls){
        try {
            JavaClass javaClass = Repository.lookupClass(cls);
            System.out.println(Arrays.toString(javaClass.getBytes()));
            return encode_str(javaClass.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String encode_str(byte[] b) {
        try {
            String str = Base64.getEncoder().encodeToString(b);
            System.out.println(str);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
