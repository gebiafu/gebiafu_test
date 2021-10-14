package com.gebiafu;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @title: DubboProviderApp
 * @Description: dubbo启动类
 * @author: Gebiafu
 * @date: 2021/08/11 18:11
 * EnableDubbo - 激活Dubbo,让Dubbo提供的注解生效,必要;
 * EnableDubboConfig - 让Dubbo配置的内容生效.默认激活;
 *  建议填写,因为部分特殊配置,必须激活后才生效;
 */
@SpringBootApplication
@EnableDubbo
//@EnableDubboConfig
public class DubboProviderApp9093 {
    public static void main(String[] args) {
        SpringApplication.run(DubboProviderApp9093.class,args);
    }
}
