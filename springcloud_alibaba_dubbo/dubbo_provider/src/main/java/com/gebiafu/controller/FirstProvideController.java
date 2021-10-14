package com.gebiafu.controller;

import com.gebiafu.service.FirstProvideService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @title: FirstProvideController
 * @Description:
 * @author: Gebiafu
 */
@RestController
public class FirstProvideController {
    @Autowired
    private FirstProvideService firstProvideService;
    @RequestMapping("/first")
    public String sayHello(String name){
        return firstProvideService.sayHello(name);
    }
}
