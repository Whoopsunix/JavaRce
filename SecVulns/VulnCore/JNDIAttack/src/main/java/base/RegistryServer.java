package base;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Whoopsunix
 * Registry 方式注册
 */
public class RegistryServer {

    public static void main(String args[]) {
        try {
            RemoteInterface remoteObject = new RemoteObject();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("Hello", remoteObject);
            System.out.println("Registry Server Start");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}