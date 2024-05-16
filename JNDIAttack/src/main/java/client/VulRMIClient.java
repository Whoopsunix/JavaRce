package client;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Whoopsunix
 *
 */
public class VulRMIClient {
    public static void main(String[] args) throws Exception {
        // M1 Naming
//        String url = "rmi://127.0.0.1:1099/xxx";
//
//        java.rmi.Naming.lookup(url);
//        java.rmi.Naming.list(url);

//        // M2 Registry
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
        AttackRemoteInterface remoteObject2 = (AttackRemoteInterface) registry.lookup("xxx");
//        System.out.println(remoteObject2.sayHello());

    }
}
