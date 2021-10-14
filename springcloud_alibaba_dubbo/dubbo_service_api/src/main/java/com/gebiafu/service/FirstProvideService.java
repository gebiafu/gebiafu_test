package com.gebiafu.service;

/**
 * @title: FirstProvideService
 * @Description: 服务提供者接口
 * @author: Gebiafu
 */
public interface FirstProvideService {
    //测试方法
    String sayHello(String name);
    //测试传输的数据容量
    String testPayload(byte[] data);

    String test1(String age);
}
