package com.ppp.mybatis.dao;


import com.ppp.Users;

import java.util.List;

public interface UserDao {

    Users getUserById(int id);

    Users getUserByName(String name);

    List<Users> getUserByNameVul(Users name);

    /**
     * 混用
     *
     * @param name
     * @return
     */

    List<Users> getUserByNameVul2(Users name);

    List<Users> getAllUsers();
}
