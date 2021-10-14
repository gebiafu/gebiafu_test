package com.bjsxt.controller;

import com.bjsxt.pojo.User;
import com.bjsxt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户控制器，实现用户控制逻辑
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 注册方法
     * 服务端校验是必要的，因为客户端可以关闭JS
     * @return
     */
    @RequestMapping("/register")
    public String register(User user, String repeatPassword){
        try {
            userService.register(user, repeatPassword);
            // 注册成功后，响应重定向到登录页面，去登录。
            return "redirect:/";
        }catch (Exception e){
            // 注册失败,响应重定向到注册页面，并提示错误
            return "redirect:/toRegister?error";
        }
    }
}
