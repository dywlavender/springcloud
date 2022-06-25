package com.atguigu.springclout.workQuene;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeoutException;

/**
 * @author dyw
 * @date 2022-03-24  20:08
 */
public class ProducerConfirm {
    private static final String QUEUE_NAME = "confirm_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.233.133");
        factory.setUsername("dyw");
        factory.setPassword("123");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.confirmSelect();
        /**
         *
         */
        ConcurrentSkipListMap<Long,String> outstandingConfirms = new ConcurrentSkipListMap<>();

        ConfirmCallback ackCallback = (deliveryTag, multiple)->{
            if (multiple) {
                System.out.println("确认应答: " + deliveryTag);
                ConcurrentNavigableMap<Long, String> confirmed = outstandingConfirms.headMap(deliveryTag);
                confirmed.clear();
            }else{
                outstandingConfirms.remove(deliveryTag);
            }
        };
        ConfirmCallback nackCallback = (deliveryTag, multiple)->{
            String failMesage = outstandingConfirms.get(deliveryTag);
            System.out.println("失败应答消息： "+failMesage+"  序号是："+deliveryTag);

        };
        channel.addConfirmListener(ackCallback,nackCallback);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            String message = i+": mess";
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes(StandardCharsets.UTF_8));
//            if (i%99==0){
//            try {
//                boolean flag = channel.waitForConfirms();
//                if (flag){
//                    System.out.println("send successful");
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }}
            outstandingConfirms.put(channel.getNextPublishSeqNo(),message);
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

}
