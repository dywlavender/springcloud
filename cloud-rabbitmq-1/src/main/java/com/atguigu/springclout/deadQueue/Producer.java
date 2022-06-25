package com.atguigu.springclout.deadQueue;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author dyw
 * @date 2022-03-25  15:07
 */
public class Producer {
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.233.132");
        factory.setUsername("dyw");
        factory.setPassword("123");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().expiration("2000000").build();
        for (int i = 0; i < 11; i++) {
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            String message = "info: " + i;
            channel.basicPublish(NORMAL_EXCHANGE,"zhangsan",properties,message.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发送信息： " + message);
        }
    }
}
