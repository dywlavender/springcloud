package com.atguigu.springclout;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author dyw
 * @date 2022-03-23  21:44
 */
public class Producer {
    private static final String QUEUE_NAME = "mirror_hello";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.233.133");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123");
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            Map<String,Object> map = new HashMap<>();
            map.put("x-max-priority",10);
            channel.queueDeclare(QUEUE_NAME,false,false,false,map);
            AMQP.BasicProperties build = new AMQP.BasicProperties().builder().priority(5).build();
            for (int i = 0; i < 10; i++) {
                String message = i + "";
                if (i == 5){
                    channel.basicPublish("",QUEUE_NAME,build,message.getBytes());
                }else{
                    channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
                }

            }
            System.out.println("message send");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }
}
