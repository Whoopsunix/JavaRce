package com.ppp;

import org.ppp.tools.encryption.B64;
import org.springframework.expression.Test;

/**
 * @author Whoopsunix
 */
public class Main {
    public static void main(String[] args) {
        String b64Str = new B64().encodeJavaClass(Test.class);
    }
}
