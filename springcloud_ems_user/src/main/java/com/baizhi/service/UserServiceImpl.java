package com.baizhi.service;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@Service //声明给类为Service层
@Transactional(propagation = Propagation.SUPPORTS)
public class UserServiceImpl implements UserService {

    @Resource
    UserDao userDao;


    @Override
    public User queryByUsername(User user) {
        //1.判断用户是否存在
        //2.判断密码是否正确
        User user2 = null;
        try {
            user2 = userDao.queryByUsername(user.getUsername());

            if(user2!=null){
                if(user2.getPassword().equals(user.getPassword())){
                    return user2;
                }else{
                    throw new RuntimeException("密码不正确！");
                }
            }else{
                throw new RuntimeException("该用户不存在，请先注册用户！");
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("查询失败!");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public HashMap<String, Object> insertUser(User user,String code, HttpServletRequest request) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        //获取request中的验证码值
        String enCode = (String)request.getServletContext().getAttribute("code");

        try {
            //1判断验证码判断验证码是否正确   不正确   抛异常  Throw new RunTimeException("验证码不正确")
            if (code.equalsIgnoreCase(enCode)) {
                //2判断用户名是否存在
                User queryByUsername = userDao.queryByUsername(user.getUsername());
                if (queryByUsername == null) {
                    //3.注册用户   调用server   dao 向数据库注册 信息
                    String uuid = UUID.randomUUID().toString();
                    user.setId(uuid);
                    user.setStatus("0");
                    user.setRegisterTime(new Date());

                    userDao.insertUser(user);

                    map.put("msg", "提示:注册成功!");
                    map.put("state", true);

                } else {
                    throw new RuntimeException("用户名已存在!");
                }

            } else {
                throw new RuntimeException("验证码不正确!");
            }

        } catch (Exception e) {
            //打印异常信息
            map.put("msg", e.getMessage());
            map.put("state", false);
        }

        return map;
    }


}
