package client;


/**
 * @author Whoopsunix
 *
 */
public class VulRMIClient {
    public static void main(String[] args) throws Exception {
        // M1 Naming
        String url = "rmi://192.168.66.143:1099/xxx";
        AttackRemoteInterface remoteObject = (AttackRemoteInterface) java.rmi.Naming.lookup(url);
        System.out.println(remoteObject.sayHello());

//        // M2 Registry
//        Registry registry = LocateRegistry.getRegistry("192.168.66.143", 1099);
//        AttackRemoteInterface remoteObject2 = (AttackRemoteInterface) registry.lookup("xxx");
//        System.out.println(remoteObject2.sayHello());

    }
}
