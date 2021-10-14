package com.gebiafu.auyhority;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * title: MyAuthorityAccess
 * author: Gebiafu
 * date: 2021/07/01 18:55
 */
/**
 * 自定义权限管理接口。
 * 接口中定义一个方法。
 * 方法要求：
 *  1. 返回值类型： boolean。 true代表可访问。false代表不可访问。
 *  2. 方法参数： 只能是Spring容器中可以获取的类型对象。
 *     必须是WebSecurityExpressionRoot类型（包含父类型）中有的属性
 *     一般使用请求HttpServletRequest和登录用户的主体Authentication
 */
public interface MyAuthorityAccess {
    boolean isAccessed(HttpServletRequest request, Authentication authentication);
}
