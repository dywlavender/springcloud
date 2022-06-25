package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author dyw
 * @date 2022-03-20  20:48
 */
@RestController
@Slf4j
@DefaultProperties(defaultFallback="payment_Global_FallbackMethod")
public class OrderHystrixController {
    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping(value = "/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        String result = paymentHystrixService.paymentInfo_OK(id);
        return result;
    }

    @GetMapping(value = "/consumer/payment/hystrix/timeout/{id}")
//    @HystrixCommand(fallbackMethod="paymentInfo_TimeoutFallbackMethod",commandProperties = {
//            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "1500")
//    })
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "1500")})
    public String paymentInfo_Timeout(@PathVariable("id") Integer id){
//        int a = 10/0;
        String result = paymentHystrixService.paymentInfo_Timeout(id);
        return result;
    }
    public String paymentInfo_TimeoutFallbackMethod(@PathVariable("id") Integer id){
        return "消费者80，对方支付系统繁忙，请稍后再试";
    }

    public String payment_Global_FallbackMethod(){
        return "Global异常 信息处理，请稍后再试";
    }
}
