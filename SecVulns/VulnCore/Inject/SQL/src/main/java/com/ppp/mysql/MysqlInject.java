package com.ppp.mysql;



import com.ppp.SQLInfo;
import com.ppp.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Whoopsunix
 */
public class MysqlInject {

    public static void main(String[] args) throws Exception {
        List<Object> result;
        boolean re;

        result = select2(1, "xxx' union select * from users#", "123");
        System.out.println(result);
//        result = select(1, "xxx' union select * from users#", "123");
//        System.out.println(result);
//
//        re = insert("inject", "123456");
//        System.out.println(re);
//
//        re = update("inject", "xxxx");
//        System.out.println(re);
//
//        re = delete("inject", "xxxx");
//        System.out.println(re);
    }

    public static Connection createConnection() throws Exception {
        Class.forName(SQLInfo.MYSQLJDBCDRIVER);
        Connection connection = DriverManager.getConnection(SQLInfo.DBURL, SQLInfo.USER, SQLInfo.PASS);
        return connection;
    }

    /**
     * 拼接导致的注入
     */
    public static List<Object> select(Integer id, String username, String password) throws Exception {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();

        String sql = null;

//        if (id != null) {
//            sql = String.format("select * from users where `id`=%d;", id);
//        } else
        if (username != null && password != null) {
            sql = String.format("select * from users where `username`='%s' and `password`='%s';", username, password);
        } else {
            return null;
        }
        System.out.println(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        List<Object> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(new Users(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password")));
        }

        statement.close();
        connection.close();
        return result;
    }

    /**
     * 错误的预编译写法
     */
    public static List<Object> select2(Integer id, String username, String password) throws Exception {
        Connection connection = createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(String.format("select * from users where `username`='%s' and `password`='%s';", username, password));
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Object> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(new Users(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password")));
        }

        preparedStatement.close();
        connection.close();
        return result;
    }

    public static boolean insert(String username, String password) throws Exception {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        String sql = String.format("insert into users(id, username, password) values(%d, '%s', '%s');", new Random().nextInt(Integer.MAX_VALUE - 1), username, password);
        System.out.println(sql);
        statement.execute(sql);
        statement.close();
        connection.close();

        return true;
    }

    public static boolean update(String username, String password) throws Exception {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();

        String sql = String.format("update users set password='%s' where username='%s';", password, username);
        System.out.println(sql);
        statement.execute(sql);
        statement.close();
        connection.close();

        return true;
    }

    public static boolean delete(String username, String password) throws Exception {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();

        String sql = String.format("delete from users where username='%s' and password='%s';", username, password);
        System.out.println(sql);
        statement.execute(sql);
        statement.close();
        connection.close();

        return true;
    }
}
