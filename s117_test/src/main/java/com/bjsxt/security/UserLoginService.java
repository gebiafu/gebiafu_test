package com.bjsxt.security;

import com.bjsxt.exception.DaoException;
import com.bjsxt.pojo.User;
import com.bjsxt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserLoginService implements UserDetailsService {
    @Autowired
    private UserService userService;

    // 登录逻辑
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 根据登录名查询用户
        User user = null;
        try{
            user = userService.login(s);
        }catch (DaoException e){
            e.printStackTrace();
            // 查询用户错误， 通知登录失败
            throw new UsernameNotFoundException("用户登录失败，数据库查询错误");
        }
        // 判断用户是否存在
        if(user == null){ // 用户名不存在
            throw new UsernameNotFoundException("用户登录失败，用户不存在");
        }
        // 处理权限
        String [] authorities = user.getAuthorities().split(";");

        // 创建返回结果对象
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                AuthorityUtils.createAuthorityList(authorities)
        );
    }
}
