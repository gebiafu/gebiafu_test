package com.gebiafu.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


/**
 * @title: FirstQueue1Consumer
 * @Description:
 * @author: Gebiafu
 * @date: 2021/07/21 19:23
 * 监听direct.queue.1队列的消费者监听器
 * 一个类型,可以同时监听若干队列
 * 一个方法,监听一个队列
 *
 * Spring AMQP框架可以自动注册监听.必须有一个符合监听逻辑的对象和方法在Spring的ApplicationContext上下文中
 */
@Component
public class FirstQueue1Consumer {
    @RabbitListener(queues = "direct.queue.2")
    public void onMessage4Queue21(String message){
        System.out.println("onMessage4Queue2_1处理消息:"+message);
    }
    @RabbitListener(queues = "direct.queue.2")
    public void onMessage4Queue22(String message){
        System.out.println("onMessage4Queue2_2处理消息:"+message);
    }
    @RabbitListener(queues = "direct.queue.2")
    public void onMessage4Queue23(String message){
        System.out.println("onMessage4Queue2_3处理消息:"+message);
    }
    /*
    * 监听方法要求:
    * 1,如果消息是一个异步消息,方法没有返回值
    * 2,方法名称自定义
    * 3.方法参数是一个Object兼容类型,就是具体的消息体数据对象类型.也可以是Message类型(包含头和体)
    * 4,方法的访问修饰符是public
    * 5,方法必须使用@RabbitListener注解描述.至少提供队列名称配置(queues)
    * */
    @RabbitListener(queues = "direct.queue.1")
    public void onMessage(String message){/*(Object message){*/
        System.out.println("处理消息; "+message);
        /*System.out.println(message.getClass().getName());
        System.out.println(message);
        if(message instanceof Message){
            Message m=(Message) message;
            String messageBody =new String(m.getBody());
            System.out.println("消息体是:"+messageBody);
        }*/

    }
}
