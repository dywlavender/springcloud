package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author dyw
 * @date 2022-03-28  17:21
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AlibabaProviderMain9001 {
    public static void main(String[] args) {
        SpringApplication.run(AlibabaProviderMain9001.class,args);
    }
}
