package com.baizhi.controller;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.utils.VerifyCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@RestController
@Slf4j
public class UserController {

    @Resource
    UserService userService;

    @Resource
    UserDao userDao;

    /**
     * 生成验证码图片
     */
    @GetMapping("/user/getImage")
    public String getImageCode(HttpServletRequest request) throws IOException {
        //1.使用工具类生成验证码
        String code = VerifyCodeUtils.generateVerifyCode(4);
        //2.将验证码放入servletContext作用域
        request.getServletContext().setAttribute("code", code);
        //3.将图片转为base64
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        VerifyCodeUtils.outputImage(120, 30, byteArrayOutputStream, code);
        return "data:image/png;base64," + Base64Utils.encodeToString(byteArrayOutputStream.toByteArray());
    }


    @ResponseBody
    @PostMapping("/user/login")
    public HashMap<String, Object> login(@RequestBody User user) {

        HashMap<String, Object> map = new HashMap<>();

        //1.调用业务方法 登陆
        try {
            User user2 = userService.queryByUsername(user);

            map.put("msg", "登录成功!");
            map.put("state", true);
            map.put("user", user2);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "账户或密码不正确!");
            map.put("state", false);
        }

        return map;
    }


    @PostMapping("/user/register")
    @ResponseBody
    public HashMap<String, Object> register(@RequestBody User user, String code, HttpServletRequest request) {

        HashMap<String, Object> map = userService.insertUser(user, code, request);

        return map;
    }


    public static void main(String[] args) {
       // FileInputStream inputStream = new FileInputStream("file/a.txt");


    }


}
