package base;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * @author Whoopsunix
 * Naming 方式注册
 */
public class RemoteServer {
    public static void main(String[] args) throws Exception{
        LocateRegistry.createRegistry(1099);
        RemoteInterface remoteObject = new RemoteObject();
//        Naming.bind("rmi://127.0.0.1/Hello", remoteObject);
        Naming.bind("rmi://127.0.0.1/Hello", remoteObject);
    }
}