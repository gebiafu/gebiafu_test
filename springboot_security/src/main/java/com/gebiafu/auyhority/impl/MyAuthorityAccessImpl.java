package com.gebiafu.auyhority.impl;

import com.gebiafu.auyhority.MyAuthorityAccess;
import com.gebiafu.pojo.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * title: MyAuthorityAccessImpl
 * author: Gebiafu
 * date: 2021/07/01 18:58
 * 当前类型的对象,必须被Spring容器管理
 */
public class MyAuthorityAccessImpl implements MyAuthorityAccess {
    /**
     * 验证登录用户是否拥有权限的方法。
     * 当前方法，忽略角色，只考虑权限。
     * 用户有的权限，一定和访问地址一致。
     * 如： 超级管理员有权限 /user /sys等。
     * 请求地址是： /user /sys等，可以访问，其他不可以访问。
     * @param request 请求对象，用于判断客户端地址等。
     * @param authentication 登录用户的主体。用于获取登录用户权限集合
     * @return
     */
    @Override
    public boolean isAccessed(HttpServletRequest request, Authentication authentication) {
        //获得请求路径地址
        String path = request.getServletPath();
        System.out.println(path);
        //获得登陆用户的UserDetails实现对象
        //获得身份对象,类型就是UserDetails
        Object principal = authentication.getPrincipal();
        if(principal instanceof UserDetails){
            //类型正确
            //转换类型
            System.out.println("身份的具体类型:"+principal.getClass().getName());
            UserDetails userDetails=(UserDetails) principal;
            //获得登陆用户有得权限集合
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            //判断当前登陆用户是否有对应的权限
            return authorities.contains(new SimpleGrantedAuthority(path));
        }else{
            //类型错误
            return false;
        }

    }
}
