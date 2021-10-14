package com.gebiafu;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @title: DobbuConsumerApp
 * @Description:
 * @author: Gebiafu
 */
@SpringBootApplication
@EnableDubboConfig
@EnableDubbo
public class DobbuConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(DobbuConsumerApp.class,args);
    }
}
