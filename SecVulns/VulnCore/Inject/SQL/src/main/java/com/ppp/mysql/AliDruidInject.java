package com.ppp.mysql;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.ppp.SQLInfo;
import com.ppp.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Whoopsunix
 */
public class AliDruidInject {
    public static void main(String[] args) throws Exception {
        List<Object> result;

//        result = select(1, "xxx' union select * from users#", "123");
        result = select2(1, "admin", "xxx'and(substr(username,3,1)='a')and'efdx'like'efdx");
        System.out.println(result);
    }

    public static DruidDataSource createDruidDataSource() {
        // 创建 DruidDataSource 对象
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(SQLInfo.DBURL);
        dataSource.setUsername(SQLInfo.USER);
        dataSource.setPassword(SQLInfo.PASS);
        return dataSource;
    }

    /**
     * 错误的预编译写法
     */
    public static List<Object> select(Integer id, String username, String password) throws Exception {
        DruidDataSource dataSource = createDruidDataSource();
        DruidPooledConnection connection = dataSource.getConnection();

        String sql = String.format("select * from users where `username`='%s' and `password`='%s';", username, password);
//        PreparedStatement preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        System.out.println(sql);
        ResultSet resultSet = preparedStatement.executeQuery(sql);
        List<Object> result = new ArrayList();
        while (resultSet.next()) {
            result.add(new Users(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password")));
        }

        preparedStatement.close();
        connection.close();
        return result;
    }

    /**
     * 错误的预编译写法2
     */
    public static List<Object> select2(Integer id, String username, String password) throws Exception {
        DruidDataSource dataSource = createDruidDataSource();
        DruidPooledConnection connection = dataSource.getConnection();

        String sql = String.format("select * from users where `username`='%s' and `password` like '%s';", username, password);
//        PreparedStatement preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        System.out.println(sql);
        ResultSet resultSet = preparedStatement.executeQuery(sql);
        List<Object> result = new ArrayList();
        while (resultSet.next()) {
            result.add(new Users(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password")));
        }

        preparedStatement.close();
        connection.close();
        return result;
    }
}
