package mysql;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Whoopsunix
 */
public class MysqlAttack {
    public static void connect(String jdbcUrl) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(jdbcUrl);
    }
}
