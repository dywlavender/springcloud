package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author dyw
 * @date 2022-04-04  22:41
 */
@SpringBootApplication
@EnableDiscoveryClient
public interface OrderNacosMain9002 {
    public static void main(String[] args) {
        SpringApplication.run(OrderNacosMain9002.class,args);
    }
}
