package com.ppp.code;

import groovy.lang.GroovyShell;

/**
 * @author Whoopsunix
 */
public class GroovyAttack {

    // groovyShell
    public Object groovyShell(String script) throws Exception {
        GroovyShell groovyShell = new GroovyShell();

        return groovyShell.evaluate(script);
    }
}
