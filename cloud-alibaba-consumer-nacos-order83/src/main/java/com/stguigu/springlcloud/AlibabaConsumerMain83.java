package com.stguigu.springlcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author dyw
 * @date 2022-03-28  18:02
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AlibabaConsumerMain83 {
    public static void main(String[] args) {
        SpringApplication.run(AlibabaConsumerMain83.class,args);
    }
}
