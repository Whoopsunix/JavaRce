package jndi;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

/**
 * @author Whoopsunix
 */
public class JNDIClient {
    public static void main(String[] args) throws Exception {
//        client1();
        client2();
    }

    public static void client1() throws Exception {
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
        env.put(Context.PROVIDER_URL, "rmi://localhost:1099");
        Context ctx = new InitialContext(env);
        ctx.lookup("Exec");
    }

    public static void client2() throws Exception {
        // JDK >= 8u121
//        System.setProperty("com.sun.jndi.rmi.object.trustURLCodebase", "true");

        new InitialContext().lookup("rmi://127.0.0.1:1099/Exec");
    }
}
