package com.atguigu.springclout.workQuene;

import com.rabbitmq.client.*;
import sun.security.pkcs11.wrapper.Constants;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * @author dyw
 * @date 2022-03-24  15:03
 */
public class Consumer {
    private static final String QUEUE_NAME = "work_queue";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.233.133");
        factory.setUsername("dyw");
        factory.setPassword("123");

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 100, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1), new ThreadPoolExecutor.DiscardPolicy());

        try {
            Connection connection = factory.newConnection();

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Channel channel = connection.createChannel();
                        DeliverCallback deliverCallback = ( consumerTag,  message)->{
                            String mes = new String(message.getBody());
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println(Thread.currentThread().getName() + "C1 get message: "+ mes);

                            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
                        };
                        CancelCallback cancelCallback = (consumerTag)->{
                            System.out.println("stop");
                        };
                        System.out.println(Thread.currentThread().getName() + " 等待消息。。。。");
                        try {
                            channel.basicQos(5);
                            channel.basicConsume(QUEUE_NAME,false,deliverCallback,cancelCallback);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            Runnable runnable1 = new Runnable() {
                @Override
                public void run() {
                    try {
                        Channel channel = connection.createChannel();
                        DeliverCallback deliverCallback = ( consumerTag,  message)->{
                            String mes = new String(message.getBody());
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println(Thread.currentThread().getName() + "C2 get message: "+ mes);

                            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
                        };
                        CancelCallback cancelCallback = (consumerTag)->{
                            System.out.println("stop");
                        };
                        System.out.println(Thread.currentThread().getName() + " 等待消息。。。。");
                        try {
                            channel.basicQos(2);
                            channel.basicConsume(QUEUE_NAME,false,deliverCallback,cancelCallback);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            threadPoolExecutor.execute(runnable);
            threadPoolExecutor.execute(runnable1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
