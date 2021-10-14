package com.bjsxt.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 视图跳转控制器
 * 只处理视图逻辑跳转
 */
@Controller
public class DispatcherController {

    /**
     * 上传文件失败的提示页面
     * @return
     */
    @RequestMapping("/fileUploadFailure")
    public String fileUploadFailure(){
        return "fileUploadFailure";
    }

    /**
     * 跳转到上传文件页面
     * @return
     */
    @RequestMapping("/toUpload")
    @PreAuthorize("hasAuthority('/toUpload')")
    public String toUpload(){
        return "uploadFile";
    }

    /**
     * 跳转到注册页面
     * @return
     */
    @RequestMapping("/toRegister")
    public String toRegister(){
        return "register";
    }

    /**
     * 首页面，进入登录页面。
     * @return
     */
    @RequestMapping({"/", "/login", "/default", "/welcome"})
    public String toLogin(){
        return "login";
    }

}
