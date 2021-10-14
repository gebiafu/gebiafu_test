package com.gebiafu.service.impl;

import com.gebiafu.service.FirstConsumerService;
import com.gebiafu.service.FirstProvideService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @title: FirstConsumerServiceImpl
 * @Description: 本地服务实现类型
 * @author: Gebiafu
 */
@Service
public class FirstConsumerServiceImpl implements FirstConsumerService {
    /**
     * 这是Provider提供的服务接口,基于接口,创建代理对象,调用方法;
     * 访问远程服务
     *
     * DubboReference - 使用配置文件中的配置信息,访问注册中心,
     *  发现同接口名称的provider,发现后,通过Spring和Dubbo创建动态代理对象;
     *  Spring容器管理,并注入到当前属性;
     *  loadbalance - 没有默认值,使用provider提供的负载均衡策略.定义后,忽略provider提供的负载均衡策略;
     *  默认环境中,consumer必须在provider启动后,才能启动;
     *  因为consumer启动的时候,要检查provider状态,provider是否存在;
     *  如果provider不存在,则启动错误;
     *  check = false,默认true.启动检查,至少关闭检查;
     *  当第一次访问调用当前provider得时候,还会创建代理对象,检查provider状态;
     */
    @DubboReference(group = "frontend",version = "1.1", loadbalance = "random",check = false)
    private FirstProvideService firstProvideService;
    //本地的服务方法.具体逻辑是.调用远程provider中的sayHello方法,返回结果
    @Override
    public String sayHello(String name) {
        String s = firstProvideService.sayHello(name);
        System.out.println("远程provider返回:"+s);
        return s;
    }

    /**
     * 用于测试网络通讯中数据最大容量
     * @param length
     * @return
     */
    @Override
    public String testPayload(int length) {
        byte[] data=new byte[length];
        String result=firstProvideService.testPayload(data);
        System.out.println("远程返回:"+result);
        return result;
    }

    @Override
    public String test1(String age) {
        String s = firstProvideService.test1(age);
        System.out.println("远程返回1:"+s);
        return s;
    }
}
