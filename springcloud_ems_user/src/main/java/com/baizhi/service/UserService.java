package com.baizhi.service;

import com.baizhi.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface UserService {
    User queryByUsername(User user);

    HashMap<String, Object> insertUser(User user, String code, HttpServletRequest request);


}
