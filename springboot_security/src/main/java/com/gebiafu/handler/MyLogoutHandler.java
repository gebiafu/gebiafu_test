package com.gebiafu.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * title: MyLogoutHandler
 * author: Gebiafu
 * date: 2021/07/01 20:19
 */
public class MyLogoutHandler implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       Authentication authentication) {
        System.out.println("退出登陆处理器");
    }
}
