package com.bjsxt.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限不足处理器
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * 权限不足时，如何处理
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        // 输出一个页面，通知权限不足请联系管理员
        httpServletResponse.setContentType("text/html; charset=UTF-8");
        httpServletResponse.getWriter().print("<div style='text-align:center; font-size:20px; color:red'>权限不足，请联系管理员");
        httpServletResponse.getWriter().print("<a href='/logout'>退出登录</a></div>");
        httpServletResponse.getWriter().flush();
    }
}
