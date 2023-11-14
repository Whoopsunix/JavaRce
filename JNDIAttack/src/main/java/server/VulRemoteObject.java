package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Whoopsunix
 */
public class VulRemoteObject extends UnicastRemoteObject implements VulRemoteInterface {
    public VulRemoteObject() throws RemoteException {
    }

    @Override
    public void sayHello(Object object) throws Exception {
        System.out.println(object.getClass().getName());
    }

    @Override
    public void sayUser(User name) throws Exception {

    }

//    @Override
//    public void sayUser(Object name) throws Exception {
//
//    }
}