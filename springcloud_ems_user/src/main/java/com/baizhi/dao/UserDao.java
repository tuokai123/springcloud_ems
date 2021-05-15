package com.baizhi.dao;

import com.baizhi.entity.User;

public interface UserDao {

    User queryByUsername(String username);

    void insertUser(User user);


}
