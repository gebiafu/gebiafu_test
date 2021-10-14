package com.gebiafu.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * title: MyFailureHandler
 * author: Gebiafu
 * date: 2021/06/30 19:15
 * 自定义登陆失败处理器
 */
public class MyFailureHandler implements AuthenticationFailureHandler {
    private String url;
    private MyFailureHandler(){}
    public MyFailureHandler(String url){this.url=url;}
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        if(url==null){
            httpServletResponse.sendRedirect("/");
        }else{
            if(url.startsWith("redirect:")){
                //响应重定向处理
                //删除前缀
                String tmp=url.substring(url.indexOf(":")+1);
                httpServletResponse.sendRedirect(tmp);
            }else{
                //请求数据
                String tmp="";
                if(url.startsWith("forward:")){
                    //删除前缀
                    tmp=url.substring(url.indexOf(":")+1);
                }else{
                    //没有前缀直接使用
                    tmp=url;
                }
                httpServletRequest.getRequestDispatcher(tmp).forward(httpServletRequest,httpServletResponse);
            }
        }
    }
}
