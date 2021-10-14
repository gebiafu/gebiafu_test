package com.gebiafu.controller;

import com.gebiafu.service.FirstConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @title: FirstConsumerController
 * @Description:
 * @author: Gebiafu
 */
@RestController
public class FirstConsumerController {
    @Autowired
    private FirstConsumerService firstConsumerService;
    @RequestMapping("/first")
    public String sayHello(String name){
        return firstConsumerService.sayHello(name);
    }
    @RequestMapping("/payload")
    public String payload(int length){
        return firstConsumerService.testPayload(length);
    }
    @RequestMapping("/test1")
    public String test1(String age){
        return firstConsumerService.test1(age);
    }
}
