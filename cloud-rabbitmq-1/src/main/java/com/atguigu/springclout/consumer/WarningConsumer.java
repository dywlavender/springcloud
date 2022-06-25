package com.atguigu.springclout.consumer;

import com.atguigu.springclout.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author dyw
 * @date 2022-03-25  23:49
 */
@Component
@Slf4j
public class WarningConsumer {
    @RabbitListener(queues = ConfirmConfig.WARNING_QUNUE_NAME)
    public void receviceWarningMessage(Message message){
        String msg = new String(message.getBody());
        log.info("报警发现不可路由消息，{}",msg);
    }
}
