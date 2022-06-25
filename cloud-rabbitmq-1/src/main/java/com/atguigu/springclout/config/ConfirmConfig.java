package com.atguigu.springclout.config;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dyw
 * @date 2022-03-25  22:23
 */
@Configuration
public class ConfirmConfig {
    public static final String CONFIRM_QUNUE_NAME = "confirm_queue";
    public static final String BACKUP_QUNUE_NAME = "backup_queue";
    public static final String WARNING_QUNUE_NAME = "warning_queue";
    public static final String CONFIRM_EXCHANGE_NAME = "confirm_exchange";
    public static final String BACKUP_EXCHANGE_NAME = "backup_exchange";
    public static final String CONFIRM_ROUTINGKEY = "key1";

    @Bean
    public Queue confirmQueue(){
        return QueueBuilder.durable(CONFIRM_QUNUE_NAME).withArgument("x-max-priority",10).build();
    }
    @Bean
    public Queue backupQueue(){
        return new Queue(BACKUP_QUNUE_NAME);
    }
    @Bean
    public Queue warningQueue(){
        return new Queue(WARNING_QUNUE_NAME);
    }
    @Bean
    public DirectExchange confirmExchange(){
        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE_NAME).durable(false)
                .withArgument("alternate-exchange",BACKUP_EXCHANGE_NAME).build();
    }
    @Bean
    public FanoutExchange backupExchange(){
        return new FanoutExchange(BACKUP_EXCHANGE_NAME);
    }
    @Bean
    public Binding confirmBinding(@Qualifier("confirmQueue") Queue confirmQueue,
                                  @Qualifier("confirmExchange") DirectExchange confirmExchange){
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(CONFIRM_ROUTINGKEY);
    }
    @Bean
    public Binding backupBinding(@Qualifier("backupQueue") Queue backupQueue,
                                 @Qualifier("backupExchange") FanoutExchange backupExchange){
        return BindingBuilder.bind(backupQueue).to(backupExchange);
    }
    @Bean
    public Binding warningBinding(@Qualifier("warningQueue") Queue warningQueue,
                                 @Qualifier("backupExchange") FanoutExchange backupExchange){
        return BindingBuilder.bind(warningQueue).to(backupExchange);
    }
}
