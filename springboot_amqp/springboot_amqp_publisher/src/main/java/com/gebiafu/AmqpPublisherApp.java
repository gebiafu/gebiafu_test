package com.gebiafu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @title: AmqpPublisherApp
 * @Description:
 * @author: Gebiafu
 * @date: 2021/07/21 18:28
 */
@SpringBootApplication
public class AmqpPublisherApp {
    public static void main(String[] args) {
        SpringApplication.run(AmqpPublisherApp.class,args);
    }
}
