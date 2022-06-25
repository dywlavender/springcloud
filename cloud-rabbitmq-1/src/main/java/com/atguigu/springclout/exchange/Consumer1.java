package com.atguigu.springclout.exchange;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author dyw
 * @date 2022-03-25  12:56
 */
public class Consumer1 {
    public static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.233.132");
        factory.setUsername("dyw");
        factory.setPassword("123");
        Connection connection = factory.newConnection();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 3, 100, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1), new ThreadPoolExecutor.DiscardPolicy());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String QUEUE_NAME = "qQ1";
                String rountingKEy = "*.orange.*";
                Consumer1 consumer1 = new Consumer1();
                try {
                    consumer1.FanoutType(connection,QUEUE_NAME,rountingKEy);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                String QUEUE_NAME = "qQ2";
                String rountingKEy = "*.*.rabbit";
                Consumer1 consumer1 = new Consumer1();
                try {
                    consumer1.FanoutType(connection,QUEUE_NAME,rountingKEy);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                String QUEUE_NAME = "qQ2";
                String rountingKEy = "lazy.#";
                Consumer1 consumer1 = new Consumer1();
                try {
                    consumer1.FanoutType(connection,QUEUE_NAME,rountingKEy);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        threadPoolExecutor.execute(runnable);
        threadPoolExecutor.execute(runnable1);
        threadPoolExecutor.execute(runnable2);


    }
    public void FanoutType(Connection connection,String QUEUE_NAME,String routingKey) throws IOException {
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,routingKey);

        System.out.println("waiting  message.....");
        DeliverCallback deliverCallback = ( consumerTag,  message)->{
            String mess = new String(message.getBody(),"UTF-8");
            System.out.println("quene name: "+QUEUE_NAME+"  bindkey: "+message.getEnvelope().getRoutingKey()+"   message: "+mess);
        };
        channel.basicConsume(QUEUE_NAME,deliverCallback,messge ->{});
    }
}
