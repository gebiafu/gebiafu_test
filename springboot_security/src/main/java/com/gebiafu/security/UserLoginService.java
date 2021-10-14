package com.gebiafu.security;

import com.gebiafu.pojo.Resource;
import com.gebiafu.pojo.Role;

import com.gebiafu.pojo.User;
import com.gebiafu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * title: UserLoginSecurity
 * author: Gebiafu
 * date: 2021/06/30 09:57
 */
/**
 * 用户登录服务。
 * 给Spring Security提供的登录逻辑。
 * Security框架，要求，登录逻辑必须实现接口UserDetailsService
 * 接口中唯一方法是：
 *  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
 *  当用户名不存在的时候，抛出异常。
 *  只要用户名存在，即可返回。由Spring Security自动检查密码是否正确。
 *  Security框架，检查密码是否正确的流程：
 *   1. 通过loadUserByUsername的返回值的方法getPassword()获取正确密码
 *   2. 使用PasswordEncoder加密请求参数中传递的密码
 *   3. 使用加密后的请求参数密码和UserDetails.getPassword()正确密码做匹配。
 *
 *  UserDetails - Security框架定义的主体接口。内部定义和用户主体相关的所有方法。
 *   可以使用Security内置的实现User，也可以自主开发实现类型。
 *
 * 认证登录的完整流程：
 *  1. 启动Spring 应用，初始化容器。
 *  2. 检查是否有自定义的UserDetailsService实现。如果有 使用自定义实现类型对象。
 *     如果没有，使用默认的实现（配置文件或user+UUID）
 *  3. 用户访问系统。必须先登录才能访问所有资源（除/login以外）。如果用户未登录
 *     使用响应重定向访问/login，显示登录页面。
 *  4. 输入用户名和密码，登录逻辑。使用post请求，访问/login实现登录。
 *     注意：这里的/login请求，由Security提供的控制逻辑处理。实际上是过滤逻辑处理。
 *           自定义的/login请求地址处理控制器方法，不会运行。
 *  5. 过滤器，收集请求参数username和password。调用UserDetailsService中的登录方法。
 *     先查询用户名是否正确。再匹配密码是否正确。
 *  6. 用户名和密码都正确，则进入后续流程。默认是登录之前访问的页面。
 *     用户名和密码由任何错误，返回登录页面，显示用户名或密码错误
 */
@Service
public class UserLoginService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findOne(username);
        System.out.println("测试"+user);
        //查询数据库判断用户名是否存在,如果不存在抛出异常
        if(user==null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        //处理返回结果
        System.out.println("登陆的用户是"+user);
        //查询权限
        //查询角色
        List<Role> roles = userService.getRolesByUser(user);
        System.out.println("roles"+roles);
        //查询资源

        List<Resource> resources = userService.getResourcesByUser(user);
        System.out.println("resources"+resources);
        //整理权限集合,角色使用角色名称(name),资源使用请求地址(url)
        String[] authorizes=null;
        if(roles!=null||resources!=null){
            int length=0;
            if(roles!=null){
                length+=roles.size();//获得角色数量
            }
            if(resources!=null){
                length+=resources.size();//获得资源数量
            }
            authorizes=new String[length];
        }
        if(authorizes!=null){
            //有角色有资源.开始处理
            int index=0;//起始下标
            if(roles!=null){
                //Security对角色权限有特殊要求,角色权限数据必须以ROLE_开头
                //不是以ROLE_开头的权限,都是资源
                for(Role role:roles){//循环用户拥有的角色
                    authorizes[index++]="ROLE_"+role.getName();//保存角色名称到权限数组中
                }
            }
            if(resources!=null){
                for(Resource resource:resources){//循环用户拥有的资源
                    authorizes[index++]=resource.getUrl();//保存资源的地址到权限数组中
                }
            }
        }
        //为了保证无角色和资源的用户登陆后也可以正常进入系统,必须保证权限数组非空
        if(authorizes==null){
            //保证权限非空
            authorizes=new String[]{"无权限用户"};
        }
        System.out.println("当前登陆用户的权限是:"+ Arrays.toString(authorizes));
        //处理返回结果
        org.springframework.security.core.userdetails.User userDetails=
                new org.springframework.security.core.userdetails.User(
                        user.getUsername(),//登陆用户的身份
                        user.getPassword(),//登陆用户的凭证(密码)
                        AuthorityUtils.createAuthorityList(authorizes)//登陆用户的权限

                );
        return userDetails;
    }
}
