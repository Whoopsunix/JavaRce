package client;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Whoopsunix
 *
 * Server 攻击 Client
 */
public class AttackRemoteServer {
    public static void main(String[] args) throws Exception{
        Registry registry = LocateRegistry.createRegistry(1099);
        AttackRemoteInterface remoteObject = new AttackRemoteObject();

        // 这个地址是客户端要访问的地址
        Naming.bind("rmi://127.0.0.1/xxx", remoteObject);
        System.out.println("Server Start");

    }
}