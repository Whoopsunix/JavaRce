package base;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Whoopsunix
 */
public class RMIClient {
    public static void main(String[] args) throws Exception {
        // M1 Naming
//        String url = "rmi://127.0.0.1:1099/Hello";
//        RemoteInterface remoteObject = (RemoteInterface) java.rmi.Naming.lookup(url);
//        System.out.println(remoteObject.sayHello());

//        // M2 Registry
        Registry registry = LocateRegistry.getRegistry("192.168.66.175", 1099);
        RemoteInterface remoteObject2 = (RemoteInterface) registry.lookup("Hello");
        System.out.println(remoteObject2.sayHello());
    }
}
