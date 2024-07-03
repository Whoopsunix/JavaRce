package jndi;

import javax.naming.InitialContext;

/**
 * @author Whoopsunix
 */
public class JNDIAttack {
    public static Object lookup(String url) throws Exception {
        InitialContext ctx = new InitialContext();
        return ctx.lookup(url);
    }
}
