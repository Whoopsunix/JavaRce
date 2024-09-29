package com.ppp.mysql;

import com.ppp.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Whoopsunix
 */
public class HQLInject {
    public static void main(String[] args) throws Exception {
        List<Object> result;
        boolean re;

//        result = select(1, null, null);
        result = select(1, "xxx' union select * from users#", "123");
        System.out.println(result);

    }

    public static List<Object> select(Integer id, String username, String password) throws Exception {
        //Hibernate 加载核心配置文件（有数据库连接信息）
        Configuration configuration = new Configuration().configure();
        //创建一个 SessionFactory 用来获取 Session 连接对象
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        //获取session 连接对象
        Session session = sessionFactory.openSession();
        //开始事务
        Transaction transaction = session.beginTransaction();

        String sql = null;
        if (username != null && password != null) {
            sql = String.format("select * from users where `username`='%s' and `password`='%s';", username, password);
        } else {
            return null;
        }

        NativeQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.addEntity(Users.class);

        List<Object> users = new ArrayList();

        List<Users> rows = sqlQuery.list();
        if (rows.size() > 0) {
            for (Users o : rows) {
                users.add(new Users(o.getId(), o.getUsername(), o.getPassword()));
            }
            //提交事务
            transaction.commit();
            //释放资源
            session.close();
            sessionFactory.close();
        }
        return users;
    }
}
