package com.ppp;

/**
 * @author Whoopsunix
 */
public class SQLInfo {
    /**
     * Mysql
     */
    public static final String MYSQLJDBCDRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DBURL = "jdbc:mysql://127.0.0.1:3306/SecVulns?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
    public static final String USER = "root";
    public static final String PASS = "123456";

    /**
     * MSSQL
     */
    public static final String MSSQLDBURL = "jdbc:sqlserver://192.168.66.175:1433;databaseName=Secvulns";
    public static final String MSSQLUSER = "sa";
    public static final String MSSQLPASSWORD = "Passw0rd";
}
