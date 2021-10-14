package com.gebiafu.test;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * @title: TestAmqpPublisher
 * @Description:
 * @author: Gebiafu
 * @date: 2021/07/21 18:29
 */
@SpringBootTest
public class TestAmqpPublisher {
    @Autowired
    private AmqpTemplate amqpTemplate;
    /*
    * AmqpTemplate - Spring AMQP框架提供的顶级接口
    *  所有基于AMQP协议开发的MQ中间件,都可以基于当前类型的对象做访问
    *   访问包括:发送消息;
    * AmqpTemplate类型的对象,是Spring-boot-starter-amqp启动器自动装配的.
    *   创建对象的时候,需要提供RabbitMQ服务器的配置数据.
    *
    * Template - 模板方法设计模式
    * */
    @Test
    public void testClient(){
        System.out.println(amqpTemplate);
    }
    /**
     * 循环发送多个字符串消息
     */
    @Test
    public void testSendBatchStringMessage(){
        String message="消息 ";
        String exchange="first.direct";
        String routingKey="direct.routing.key.1";
        for (int i=0;i<3;i++){
            amqpTemplate.convertAndSend(exchange,routingKey,message + i);
        }
        System.out.println("发送完成");
    }
    @Test
    public void testSendBatchStringMessage2(){
        String message="测试消息 ";
        String exchange="first.direct";
        String routingKey="direct.routing.key.2";
        for (int i=0;i<10;i++){
            amqpTemplate.convertAndSend(exchange,routingKey,message + i);
        }
        System.out.println("发送完成");
    }

    /**
     * 发送消息的时候,需要提供消息发送到哪一个交换器,路由键是什么.消息内容是什么.
     */
    @Test
    public void testSendStringMessage(){
        String message="第一个消息";
        String exchange="first.direct";
        String routingKey="direct.routing.key.1";
        /*
        * 方法名解释:
        *  convert - 转换,把消息体和路由键,转换组合成一个完整的消息对象,消息对象的类型是Message类型
        *  send - 发送
        * messageBody - 消息内容.只有一个强制要求,消息体必须可序列化
        * exchange - 交换器名称
        * routingKey - 路由键
        * */
        amqpTemplate.convertAndSend(exchange,routingKey,message);
        System.out.println("发送消息结束");
    }

    /**
     * 主动接受处理信息
     * 在AmqpTemplate中.可以通过主动调用方法的方式,消费消息.
     * 消费消息: 是把MQ中间件队列中的消息获取到本地,处理.处理完成后,MQ中间件会删除已处理(消费)的消息.
     */
    @Test
    public void testReceive(){
        /*
        * Object receiveAndConvert(String queueName)
        * queueName - 队列名称
        * return - 消息体的内容
        * */
        Object messageBody = amqpTemplate.receiveAndConvert("direct.queue.1");
        System.out.println(messageBody.getClass().getName());
        System.out.println(messageBody);
    }

    /**
     * 发送消息到扇形交换器
     */
    @Test
    public void testSendMessage2Fanout(){
        for(int i=0;i<13;i++){
            amqpTemplate.convertAndSend("fanout.ex","k1","路由键是K1的消息-"+i);
            amqpTemplate.convertAndSend("fanout.ex","abc","路由键是abc的消息-"+i);
            amqpTemplate.convertAndSend("fanout.ex","","路由键是空的消息-"+i);
            amqpTemplate.convertAndSend("fanout.ex","null","路由键是null的消息-"+i);
        }
    }


}
