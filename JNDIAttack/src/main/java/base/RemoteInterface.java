package base;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Whoopsunix
 */
public interface RemoteInterface extends Remote {
    public Calc sayHello() throws RemoteException;

    public String sayHello(Object name) throws RemoteException;

    public int sayHello(int num) throws RemoteException;
}
