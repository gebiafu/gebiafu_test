package com.gebiafu.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * title: MySuccessHandler
 * author: Gebiafu
 * date: 2021/06/30 19:25
 * 自定义登陆成功后的重定向处理器
 */
public class MySuccessHandler implements AuthenticationSuccessHandler {
    private String url;
    public MySuccessHandler(){}
    public MySuccessHandler(String url){
        this.url=url;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.sendRedirect(url);
    }
}
