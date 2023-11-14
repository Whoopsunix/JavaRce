package server;

import java.rmi.Remote;

/**
 * @author Whoopsunix
 */
public interface VulRemoteInterface extends Remote {

    public void sayHello(Object name) throws Exception;

    public void sayUser(User name) throws Exception;

    // 当接收参数不是 Object 时，客户端手动构造一个代码
//    public void sayUser(Object name) throws Exception;
}
