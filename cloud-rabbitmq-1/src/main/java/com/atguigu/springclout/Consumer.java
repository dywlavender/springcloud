package com.atguigu.springclout;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author dyw
 * @date 2022-03-23  22:02
 */
public class Consumer {
    private static final String QUEUE_NAME = "hello";
    private static final String FED_EXCHANGE_NAME = "fed_exchange";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.233.134");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123");
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(FED_EXCHANGE_NAME,BuiltinExchangeType.DIRECT);
            channel.queueDeclare("node2_queue",false,false,false,null);
            channel.queueBind("node2_queue",FED_EXCHANGE_NAME,"routeKet");
            System.out.println("waiting message");
            DeliverCallback deliverCallback = (consumerTag,delivery)->{
                String message = new String(delivery.getBody());
                System.out.println("message:"+message);
                System.out.println("consumerTag:"+consumerTag);
            };
            CancelCallback cancelCallback = (consumerTag) -> {
                System.out.println("消息消费被中断");
            };
            channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
