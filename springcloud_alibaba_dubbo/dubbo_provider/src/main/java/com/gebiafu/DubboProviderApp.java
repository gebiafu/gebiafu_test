package com.gebiafu;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @title: DubboProviderApp
 * @Description: dubbo启动类
 * @author: Gebiafu
 * EnableDubbo - 激活Dubbo,让Dubbo提供的注解生效,必要;
 * EnableDubboConfig - 让Dubbo配置的内容生效.默认激活;
 *  建议填写,因为部分特殊配置,必须激活后才生效;
 */
@SpringBootApplication
@EnableDubbo
@EnableDubboConfig
public class DubboProviderApp {
    public static void main(String[] args) {
        SpringApplication.run(DubboProviderApp.class,args);
    }
}
