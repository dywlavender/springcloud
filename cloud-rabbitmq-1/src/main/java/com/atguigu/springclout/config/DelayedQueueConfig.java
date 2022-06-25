package com.atguigu.springclout.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dyw
 * @date 2022-03-25  18:54
 */
@Configuration
public class DelayedQueueConfig {
    public static final String DELAYED_QUEUE_NAME = "dalayed_queue";
    public static final String DELAYED_EXCHANGE_NAME = "dalayed_exchange";
    public static final String DELAYED_RONTING_KEY = "dalayed_routingkey";

    @Bean
    public Queue queueDelayed(){
        return new Queue(DELAYED_QUEUE_NAME);
    }

    @Bean
    public CustomExchange delayedExchange(){
        Map<String,Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type","direct");
        return new CustomExchange(DELAYED_EXCHANGE_NAME,"x-delayed-message",false,false,arguments);
    }
    @Bean
    public Binding delayedBinding(@Qualifier("queueDelayed") Queue queueDelayed,
                                  @Qualifier("delayedExchange") CustomExchange delayedExchange){
        return BindingBuilder.bind(queueDelayed).to(delayedExchange).with(DELAYED_RONTING_KEY).noargs();

    }
}
