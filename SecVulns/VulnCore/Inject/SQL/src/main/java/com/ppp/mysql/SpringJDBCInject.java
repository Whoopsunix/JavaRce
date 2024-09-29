package com.ppp.mysql;


import com.ppp.SQLInfo;
import com.ppp.Users;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Whoopsunix
 */
public class SpringJDBCInject {
    private static JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
//        List<Object> result = select3(1, "xxx' union select * from users#", "123");
        List<Object> result = select(1, "xxx' union select 1,2,user()#", "123");
        System.out.println(result);
    }

    public static void createJdbcTemplate() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(SQLInfo.MYSQLJDBCDRIVER);
        dataSource.setUrl(SQLInfo.DBURL);
        dataSource.setUsername(SQLInfo.USER);
        dataSource.setPassword(SQLInfo.PASS);

        // 创建 JdbcTemplate 对象
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 拼接导致的注入
     */
    public static List<Object> select(Integer id, String username, String password) {
        createJdbcTemplate();
        String sql = String.format("select * from users where `username`='%s' and `password`='%s';", username, password);

        // 使用匿名类实现 ResultSetExtractor
        return jdbcTemplate.query(sql, resultSet -> {
            List<Object> result = new ArrayList();
            while (resultSet.next()) {
                result.add(new Users(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password")));
            }
            return result;
        });
    }

    /**
     * 错误的预编译写法
     */
    public static List<Object> select2(Integer id, String username, String password) {
        createJdbcTemplate();

        String sql = String.format("select * from users where `username`='%s' and `password`='%s';", username, password);

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                return con.prepareStatement(sql);
            }
        };

        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
//                ps.setString(1, department);
            }
        };

        ResultSetExtractor<List<Object>> rse = new ResultSetExtractor<List<Object>>() {
            @Override
            public List<Object> extractData(ResultSet resultSet) throws SQLException {
                List<Object> result = new ArrayList();
                while (resultSet.next()) {
                    result.add(new Users(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password")));
                }
                return result;
            }
        };

        return jdbcTemplate.query(psc, pss, rse);
    }

    /**
     * 错误的预编译写法2
     */
    public static List<Object> select3(Integer id, String username, String password) {
        createJdbcTemplate();

        String sql = String.format("select * from users where `username`like '%s';", username);

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                return con.prepareStatement(sql);
            }
        };

        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
//                ps.setString(1, department);
            }
        };

        ResultSetExtractor<List<Object>> rse = new ResultSetExtractor<List<Object>>() {
            @Override
            public List<Object> extractData(ResultSet resultSet) throws SQLException {
                List<Object> result = new ArrayList();
                while (resultSet.next()) {
                    result.add(new Users(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password")));
                }
                return result;
            }
        };

        return jdbcTemplate.query(psc, pss, rse);
    }
}
