package com.gebiafu.consumer;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @title: FanoutConsumer
 * @Description:
 * @author: Gebiafu
 * @date: 2021/07/21 20:10
 * 扇形交换器消费者
 * 扇形交换器,在发送消息的时候.和direct交换器没有任何区别
 * 在消费消息的时候,所有的绑定到同一个交换器的队列,会收到相同的消息
 */
@Component
public class FanoutConsumer {
    /**
     *RabbitListener - 注册监听的注解
     *  queues - 监听的队列名称,要求队列必须存在,才能监听.
     *  bindings - QueueBinding[]类型的属性,代表一个绑定规则.
     *  QueueBinding - 注解类型,代表一个绑定规则.用来描述队列&交换器&路由键的绑定规则.
     *   value - Queue类型的属性.代表一个队列.队列存在直接使用,不在则创建.必要
     *   exchange - Exchange类型的属性,代表一个交换器,存在直接使用,不存在创建,必要
     *   key - String[] 类型的属性,队列和交换器绑定的路由键/key设置多个,可选
     * Queue注解,代表一个队列
     *  name - 队列的名称.默认是空字符串
     *  autoDelete - 是否是自动删除的队列.默认是空字符串.
     *      赋值 "true" 没有消费者监听时则自动删除.
     *      赋值 "false" ,不自动删除;
     * Exchange注解,代表一个交换器
     *  name - 交换器名称
     *  type - 交换器类型,只能是direct|fanout | topic| header , 默认是direct
     *  autoDelete - 是否自动删除的交换器
     *
     */
    @RabbitListener(bindings = {
        @QueueBinding(
                value = @Queue(name="fanout.queue.1",autoDelete = "false"),
                exchange = @Exchange(name="fanout.ex",autoDelete = "false", type = "fanout"),
                key = {"k1","k2"}
        )
    })
    public void onMessage1(String message){
        System.out.println("队列1: "+message);
    }
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(name="fanout.queue.2",autoDelete = "false"),
                    exchange = @Exchange(name="fanout.ex",type = "fanout"),
                    key = "k3"
            )
    })
    public void onMessage2(String message){
        System.out.println("队列2: "+message);
    }
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(name="fanout.queue.3",autoDelete = "false"),
                    exchange = @Exchange(name = "fanout.ex",type = "fanout")
            )
    })
    public void onMessage3(String message){
        System.out.println("队列3: "+message);
    }
}
