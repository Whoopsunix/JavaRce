package registry;

import base.RemoteInterface;
import base.RemoteObject;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Whoopsunix
 * 开启 registry 服务
 */
public class VulRegistryServer {

    public static void main(String args[]) {
        try {
            RemoteInterface remoteObject = new RemoteObject();
            Registry registry = LocateRegistry.createRegistry(1099);
            System.out.println("Registry Server Start");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}