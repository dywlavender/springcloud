package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author dyw
 * @date 2022-03-27  18:02
 */
@SpringBootApplication
@EnableEurekaClient
public class StreamRabbitmqMain8801 {
    public static void main(String[] args) {
        SpringApplication.run(StreamRabbitmqMain8801.class,args);
    }
}
