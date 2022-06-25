package com.atguigu.springclout.consumer;

import com.atguigu.springclout.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author dyw
 * @date 2022-03-25  19:07
 */
@Component
@Slf4j
public class DelayQueueConsumer {
    @RabbitListener(queues = DelayedQueueConfig.DELAYED_QUEUE_NAME)
    public void receiveDelayQueue(Message message){
        String  msg = new String(message.getBody());
        log.info("当前时间：{}，收到死信队列的信息：{}",new Date().toString(),msg);
    }
}
