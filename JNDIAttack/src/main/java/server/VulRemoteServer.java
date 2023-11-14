package server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Whoopsunix
 *
 * 服务端
 */
public class VulRemoteServer {
    public static void main(String[] args) throws Exception{
        Registry registry = LocateRegistry.createRegistry(1099);
        VulRemoteInterface remoteObject = new VulRemoteObject();

        // 这个地址是客户端要访问的地址
        Naming.rebind("rmi://192.168.66.143/xxx", remoteObject);
        System.out.println("Server Start");

    }
}