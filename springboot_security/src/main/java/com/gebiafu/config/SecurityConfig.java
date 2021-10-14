package com.gebiafu.config;

import com.gebiafu.encoder.MyPasswordEncoder;
import com.gebiafu.handler.MyAccessDeniedHandler;
import com.gebiafu.handler.MyFailureHandler;
import com.gebiafu.handler.MyLogoutHandler;
import com.gebiafu.handler.MySuccessHandler;
import com.gebiafu.security.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * title: SecurityConfig
 * author: Gebiafu
 * date: 2021/06/30 10:53
 */
/**
 * 和Security认证于授权相关的配置，都使用当前方法配置。
 * 当自定义当前方法的时候。Security框架的所有默认配置失效。如：登录页面是什么，登录请求地址是什么，登录成功后跳转到什么页面
 * 只能保留默认值。
 *
 * param  http 相当于Spring Security框架的上下文。类似Spring框架中的ApplicationContext。
 *             包括所有认证相关、授权相关、安全相关、退出相关、异常相关的配置类型。
 *             如： 认证相关配置。  FormLoginConfigure httpSecurity.formLogin()
 *             配置类型中定义若干相关的具体配置方法。
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private PersistentTokenRepository repository;

    /**
     * 创建一个remember-me需要的数据库访问对象,保存用户登陆信息
     * JdbcTokenRepositoryImpl需要数据库连接池DataSource对象
     * @param dataSource
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(DataSource dataSource){
        JdbcTokenRepositoryImpl repository=new JdbcTokenRepositoryImpl();
        //设置数据源
        repository.setDataSource(dataSource);
        //Security保存记住我的数据,需要使用表格,表格可以让系统自己创建
        //默认不会自动创建,且创建此逻辑,没有检查表格是否存在的能力
        //只在第一次启动项目的时候开启,后续都关闭,可以注释或传参数false
        repository.setCreateTableOnStartup(false);
        return repository;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()//配置登陆相关逻辑
                .loginPage("/")//登陆页面请求地址.默认就是/login.只处理get请求

                //登陆页面表单提交的请求地址是什么,默认是/login,只处理post请求,且只能让Security提供的过滤逻辑登陆请求
                .loginProcessingUrl("/login")//登陆请求的处理地址,对应security处理请求的地址,默认就算/login
        //下述的3个登陆成功后的处理逻辑配置底层逻辑相同,都是给配置类型的属性AuthenticationSuccessHandler赋值
        //多次调用,最后一个生效
                .successHandler(new MySuccessHandler("/toMain"))//自定义一个登陆成功的处理器
                //.successForwardUrl("/toMain")//设置登陆成功后的请求转发地址
                //.defaultSuccessUrl("/toMain")//设置登陆成功后的默认跳转地址,响应重定向,只在客户至极访问登陆页面时生效
                //.failureForwardUrl("/?flag=1")//设置登陆失败后的请求转发地址
                //.failureUrl("?flag=1")//设置登陆失败后的响应重定向地址
                .failureHandler(new MyFailureHandler("forward:/?flag=1"));//设置自定义登陆失败处理器
                //.usernameParameter("name")//设置请求参数中的用户名参数名
                //.passwordParameter("pswd");//设置请求参数中的密码参数名
        http.authorizeRequests()//配置验证权限的逻辑
                .antMatchers("/","/index","/default").permitAll()//对应得路径地址,任何人都能访问
                   // .antMatchers("/**")
                     //   .access('11')//判断权限,使用自定义类型对象的方法校验
                    .antMatchers("/customer")
                        .access("hasRole('ROLE_超级管理员')")//自定义权限管理,只要传递合法字符串即可.
                    .antMatchers("/hasRole")
                        .hasRole("超级管理员")//基于角色实现权限管理,参数是角色的名称.登陆用户有角色可以访问
                    .antMatchers("/hasAnyRole")
                        .hasAnyRole("超级管理员","管理员")//登陆用户有任意角色都可以访问
                    .antMatchers("/hasAuthority")
                        .hasAuthority("/sys")//登陆用户有对应资源可以访问
                    .antMatchers("/hasAnyAuthority")
                        .hasAnyAuthority("/sys","/user")//登陆用户有任意资源可以访问
                    .antMatchers("/hasIp")
                        .hasIpAddress("127.0.0.1")//客户端ip符合要求可以访问,是request.getRemoteAddress的结果
                    .antMatchers("/abc").permitAll()//都可以访问
                    .regexMatchers("^/i.*x$").permitAll()//i开头,x结尾的链接可以访问(正则)
                    .antMatchers("/default").authenticated()//已认证的可以访问
                    .antMatchers("/aaa").denyAll()//都不允许访问
                    .antMatchers("/bbb").anonymous()//可以匿名访问
                    .antMatchers("/ccc").rememberMe()//被“remember me”的用户允许访问
                    .antMatchers("/ddd").fullyAuthenticated()//如果用户不是被remember me的，才可以访问。
                    .anyRequest().authenticated();//除上述地址外,其他请求地址,必须登陆后才可以访问
        //配置退出登陆
        http.logout()
            .addLogoutHandler(new MyLogoutHandler())
            .invalidateHttpSession(true)//退出登陆的时候,是否会销毁会话对象.默认true
            .clearAuthentication(true)//退出登陆的时候,是否清空Security登陆用户的主体,默认true
            .logoutSuccessUrl("/");//登陆成功跳转路径
        //配置异常处理
        http.exceptionHandling()
                .accessDeniedHandler(new MyAccessDeniedHandler());//把自定义的访问受限处理器配置到Security中.
        //增加 记住我 配置
        http.rememberMe()//用于配置记住我相关
                .userDetailsService(userLoginService)//配置自定义的userLoginService实现类的对象
                .rememberMeCookieName("bjsxt")//设置记住我的cookie名称,默认remember-me
                .tokenValiditySeconds(3*24*60*60)//设置记住我的有效时长,默认14天
                //.rememberMeCookieDomain("localhost")//设置cookie的域,当用户访问什么地址的时候生效
                .rememberMeParameter("abc")//设置记住我的请求参数有名,默认remember-me
                .tokenRepository(repository);//如何把用户的登陆信息保存起来
        //http.csrf().disable();//关闭csrf.否则自定义的登陆页面不能实现登陆

    }

    /**
     * 使用Configuration,创建一个PasswordEncoder类型对象
     * @return
     */

    @Bean
    public PasswordEncoder getPwdEncoder(){
        //return new BCryptPasswordEncoder();
        //调用自带的加密算法
        return new MyPasswordEncoder();
        //调用自定义加密算法
    }
}
