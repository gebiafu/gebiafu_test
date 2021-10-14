package com.gebiafu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * title: MySecurityApp
 * author: Gebiafu
 * date: 2021/06/30 10:47
 */
/**
 * EnableGlobalMethodSecurity - 定义Security框架中的权限管理注解是否生效的注解。
 *  此注解中，关闭所有的Security权限管理注解。使用什么注解，开启即可。
 *  securedEnabled - 开启@Secured注解
 *  prePostEnabled - 开启@PreAuthorize和@PostAuthorize注解
 *
 * 建议： 只要使用注解管理权限，一定在启动类上增加注解EnableGlobalMethodSecurity，
 *     并开启至少一个注解。一般都是securedEnabled = true, prePostEnabled = true
 */
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
@SpringBootApplication
public class MySecurityApp {
    public static void main(String[] args) {
        SpringApplication.run(MySecurityApp.class,args);
    }
}
