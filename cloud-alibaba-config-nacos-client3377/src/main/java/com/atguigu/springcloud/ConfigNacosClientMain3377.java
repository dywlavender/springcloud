package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author dyw
 * @date 2022-04-04  23:14
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ConfigNacosClientMain3377 {
    public static void main(String[] args) {
        SpringApplication.run(ConfigNacosClientMain3377.class,args);
    }
}
