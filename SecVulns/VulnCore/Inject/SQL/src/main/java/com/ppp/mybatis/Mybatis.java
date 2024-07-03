package com.ppp.mybatis;


import com.ppp.Users;
import com.ppp.mybatis.dao.UserDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;
import java.util.List;

/**
 * @author Whoopsunix
 */
public class Mybatis {

    Reader reader;
    SqlSessionFactory sqlSessionFactory;
    SqlSession session;
    UserDao userDao;

    public Mybatis() {
        try {
            reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            session = sqlSessionFactory.openSession();
            userDao = session.getMapper(UserDao.class);
        } catch (Exception e) {

        }

    }

    public static void main(String[] args) throws Exception {
//        vul();

        Mybatis mybatis = new Mybatis();
        Users users = new Users();
//        users.setUsername("test");
        users.setUsername("xxx' union select * from users#");
        users.setPassword("testpass");
        System.out.println(mybatis.userDao.getUserByNameVul(users));

    }

    public static void vul() {
        Mybatis mybatis = new Mybatis();
        // 预编译
//        System.out.println(mybatis.getAllUsers());
//        System.out.println(mybatis.getUserById(165827712));
//        System.out.println(mybatis.getUserByName("test"));


        Users users = new Users();
//        users.setUsername("test");
        users.setUsername("xxx' union select * from users#");
        System.out.println(mybatis.userDao.getUserByNameVul(users));
    }


    public List getAllUsers() {
        List<Users> userList = userDao.getAllUsers();
        return userList;
    }

    public Users getUserById(int id) {
        Users users = userDao.getUserById(id);
        return users;
    }

    public Users getUserByName(String name) {
        Users users = userDao.getUserByName(name);
        return users;
    }

    public List getUserByNameVul(String name) {
        Users users = new Users();
//        users.setUsername("test");
        users.setUsername(name);
        return userDao.getUserByNameVul(users);
    }

    public List getUserByNameVul2(String name, String password) {
        Users users = new Users();
        users.setUsername(name);
        users.setPassword(password);
        return userDao.getUserByNameVul(users);
    }

}
