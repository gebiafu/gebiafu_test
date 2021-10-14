package com.bjsxt.service;

import com.bjsxt.pojo.User;

/**
 * 开发的时候。服务是和业务相关的。
 * 业务给什么，方法参数是什么。
 * 业务需要什么，方法返回是什么。
 */
public interface UserService {
    // 注册
    int register(User user, String repeatPassword);
    // 登录
    User login(String username);
}
