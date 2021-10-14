package com.gebiafu.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * title: MyAccessDeniedHandler
 * author: Gebiafu
 * date: 2021/07/01 18:44
 * 访问权限不足处理器,当权限不足返回403错误得时候,自动触发并处理
 * 类型必须被spring管理
 */
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        System.out.println("登陆用户权限不足:"+e.getMessage());
        httpServletResponse.setContentType("text/html; charset=UTF-8");
        //HttpServletResponse.sendXXX系列方法,执行结束后,立刻返回,后续代码忽略.
        //修改返回状态,状态编码200
        httpServletResponse.getWriter().println("<h1>权限不足,请联系管理员</h1>");
        httpServletResponse.getWriter().flush();
    }
}
