package com.gebiafu.controller;

import com.gebiafu.security.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * title: MyController
 * author: Gebiafu
 * date: 2021/06/30 11:01
 */

@Controller
public class MyController {
    @Autowired
    private UserLoginService userLoginService;

    /**
     * Security默认拦截所有得请求路径,都必须登录后才可以访问
     * 只有/login可以未登录匿名访问
     * 希望通过修改此逻辑,需要使用Configuration进行配置
     * @return
     */
    @RequestMapping({"/", "/index","/default"})
    public String toLoginPage(){
        return "login";
    }

    /**
     * 登陆成功后跳转页面的方法
     * @return
     */
    @RequestMapping("/toMain")
    public String toMain(){
        return "main";
    }

    /**
     * 用户管理方法
     * PostAuthorize当前方法执行后,校验权限,和PreAuthorize是成对管理的注解
     * 参数value 类型String  内容是:access方法的参数表达式,如:hasRole('ROLE_角色名')
     * @return
     */
    @RequestMapping("/user")
    @ResponseBody
    //@Secured({"ROLE_超级管理员","ROLE_管理员"})
    @PostAuthorize("hasAnyRole('ROLE_管理员')")
    public String user(){
        System.out.println("用户管理方法执行");
        return "用户管理";
    }
    /**
     * 系统管理方法
     * Secured,基于角色的访问控制注解,基于注解实现访问控制,必须给启动类型增加新的注解
     * 参数value 类型String[]  相当于hasAnyRole("","")
     * 要求角色名必须以ROLE_作为前缀
     * @return
     */
    @RequestMapping("/sys")
    @ResponseBody
    //@Secured({"ROLE_超级管理员"})
    @PostAuthorize("hasRole('ROLE_超级管理员')")
    public String sys(){
        System.out.println("系统管理方法执行");
        return "系统管理";
    }
    /**
     * 查询用户方法
     * PreAuthorize执行当前方法之前,校验权限,有权限执行方法逻辑.无权限抛出异常,进入403错误处理逻辑
     * 参数value 类型String  内容是:access方法的参数表达式,如:hasRole('ROLE_角色名')
     * @return
     */
    @RequestMapping("/user/select")
    @ResponseBody
    //@Secured({"ROLE_超级管理员","ROLE_管理员"})
    @PostAuthorize("hasAnyRole('ROLE_管理员')")
    public String userSelect(){
        System.out.println("查询用户方法执行");
        return "查询用户";
    }
}

