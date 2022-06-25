package com.atguigu.springclout.deadQueue;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author dyw
 * @date 2022-03-25  15:12
 */
public class Consumer2 {
    public static final String DEAD_EXCHANGE = "dead_exchange";
    public static final String NORMAL_QUEUE = "normal_queue";
    public static final String DEAD_QUEUE= "dead_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.233.132");
        factory.setUsername("dyw");
        factory.setPassword("123");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        System.out.println("dead 等待接收信息");
        DeliverCallback deliverCallback = (consumerTag, message)->{
            String mess = new String(message.getBody(),"UTF-8");
            System.out.println("Consumer2 dean_quene 接收到的信息："+mess);
        };
        channel.basicConsume(DEAD_QUEUE,deliverCallback,consumerTag -> {});
    }
}
