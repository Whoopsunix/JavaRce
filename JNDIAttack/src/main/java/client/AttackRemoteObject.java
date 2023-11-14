package client;

import utils.Serializer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Whoopsunix
 */
public class AttackRemoteObject extends UnicastRemoteObject implements AttackRemoteInterface {
    public AttackRemoteObject() throws RemoteException {
    }

    @Override
    public Object sayHello() throws Exception {
        return Serializer.cc6("calc");
    }
}