package clientserver;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Whoopsunix
 */
public class Client {
    public static void main(String[] args) throws Exception {
//        Registry registry = LocateRegistry.getRegistry("192.168.66.143", 1099);
//        registry.list();

        Naming.lookup("rmi://192.168.66.143:1099/xxx");
    }
}
