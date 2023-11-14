package base;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Whoopsunix
 */
public class RemoteObject extends UnicastRemoteObject implements RemoteInterface {
    public RemoteObject() throws RemoteException {
    }

    @Override
    public Calc sayHello() throws RemoteException {
        return new Calc();
//        return "Hello World";
    }

    @Override
    public String sayHello(Object name) throws RemoteException {
        return name.getClass().getName();
    }

}