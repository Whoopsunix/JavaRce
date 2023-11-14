package client;

import java.rmi.Remote;

/**
 * @author Whoopsunix
 */
public interface AttackRemoteInterface extends Remote {

    public Object sayHello() throws Exception;
}
