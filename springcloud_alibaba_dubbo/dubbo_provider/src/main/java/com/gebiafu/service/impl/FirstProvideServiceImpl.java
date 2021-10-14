package com.gebiafu.service.impl;

import com.gebiafu.service.FirstProvideService;
import org.apache.dubbo.config.annotation.DubboService;


/**
 * @title: FirstProvideServiceImpl
 * @Description: 服务提供者方法
 * @author: Gebiafu
 * Dubbo是基于接口对外发布服务,基于接口远程调用服务;
 * 所有provider就是接口的具体实现,consumer就是接口的代理调用
 *
 * DubboService - Dubbo框架提供的Provider服务提供者注解;
 *  代表,当前类型的对象被Spring管理,并使用配置文件中的配置信息,做服务注册;
 *  group = "frontend",version = "1.1" - 不同客户端对应不同服务;
 */
@DubboService(group = "frontend",version = "1.1",loadbalance = "roundrobin",weight = 1)
public class FirstProvideServiceImpl implements FirstProvideService {
    //注入mapper.访问数据库

    /**
     * 测试服务提供
     * @param name
     */
    @Override
    public String sayHello(String name) {
        //通过打印输出,模拟数据库访问
        System.out.println("访问数据库,查询用户:"+name);
        return "hello "+name;
    }

    @Override
    public String testPayload(byte[] data) {
        System.out.println("参数字节数组长度为:"+data.length);
        return "已经处理";
    }

    @Override
    public String test1(String age) {
        System.out.println("测试数据---"+age);
        return "测试数据"+age;
    }
}
