package com.atguigu.springclout.consumer;

import com.atguigu.springclout.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author dyw
 * @date 2022-03-25  22:34
 */
@Component
@Slf4j
public class ConfirmConsumer {
    @RabbitListener(queues = ConfirmConfig.CONFIRM_QUNUE_NAME)
    public void receviceConfirmMessage(Message message){
        String msg = new String(message.getBody());
        log.info("接受到确认消息： " + msg);
    }
}
