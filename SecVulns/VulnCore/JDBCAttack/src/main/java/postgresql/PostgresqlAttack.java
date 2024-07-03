package postgresql;

import org.postgresql.Driver;

import java.sql.DriverManager;

/**
 * @author Whoopsunix
 */
public class PostgresqlAttack {
    public static void connect(String jdbcUrl) throws Exception {
        DriverManager.registerDriver(new Driver());
        DriverManager.getConnection(jdbcUrl);
    }
}
