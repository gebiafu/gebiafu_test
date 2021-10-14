package com.gebiafu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @title: AmqpConsumerApp
 * @Description:
 * @author: Gebiafu
 * @date: 2021/07/21 19:21
 */
@SpringBootApplication
public class AmqpConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(AmqpConsumerApp.class,args);
    }
}
