package com.atguigu.springclout.controller;

import com.atguigu.springclout.config.ConfirmConfig;
import com.atguigu.springclout.config.DelayedQueueConfig;
import com.rabbitmq.client.AMQP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.DateTimeException;
import java.util.Date;

/**
 * @author dyw
 * @date 2022-03-25  16:58
 */
@RestController
@Slf4j
public class SendMessageController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @GetMapping("/ttl/sendMsg/{message}")
    public void sendMesg(@PathVariable("message") String message){
        log.info("当前时间：{}， 发送一条消息 给两个TTL队列：{}",new Date().toString(),message);
        rabbitTemplate.convertAndSend("X","XA","消息来自ttl为10s的队列："+message);
        rabbitTemplate.convertAndSend("X","XB","消息来自ttl为40s的队列："+message);
    }
    @GetMapping("/ttl/sendMsg/{message}/{ttlTime}")
    public void sendMsgAndTime(@PathVariable("message") String message,
                               @PathVariable("ttlTime") String ttlTime){
        log.info("当前时间：{}， 发送一条消息 给两个TTL队列：{}, 时间是：{}",new Date().toString(),message,ttlTime);
        rabbitTemplate.convertAndSend("X","XC",message,msg->{
            msg.getMessageProperties().setExpiration(ttlTime);
            return msg;});
    }
    @GetMapping("/ttl/sendDelayMsg/{message}/{ttlTime}")
    public void sendDelayMsg(@PathVariable("message") String message,
                             @PathVariable("ttlTime") Integer ttlTime){
        log.info("当前时间：{}， 发送一条消息 给延迟队列：{}, 时间是：{}",new Date().toString(),message,ttlTime);
        rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAYED_EXCHANGE_NAME,
                DelayedQueueConfig.DELAYED_RONTING_KEY,message, msg->{
            msg.getMessageProperties().setDelay(ttlTime);
            return msg;});
    }
    @GetMapping("/confirm/sendMsg/{message}")
    public void sendConfirmMsg(@PathVariable("message") String message){
        CorrelationData correlationData = new CorrelationData("1");
        log.info("当前时间：{}， 发送一条消息 给队列：{}",new Date().toString(),message);
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME ,ConfirmConfig.CONFIRM_ROUTINGKEY+ "123",message,correlationData);
    }
    @GetMapping("/pro/sendMsg/{message}")
    public void sendProMsg(@PathVariable("message") String message){
        CorrelationData correlationData = new CorrelationData("1");
        log.info("当前时间：{}， 发送一条消息 给队列：{}",new Date().toString(),message);
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME ,ConfirmConfig.CONFIRM_ROUTINGKEY,message,correlationData);
    }
}
