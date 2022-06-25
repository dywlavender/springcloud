package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * @author dyw
 * @date 2022-03-20  22:12
 */
@Component
public class PaymentFallbackService implements PaymentHystrixService{
    @Override
    public String paymentInfo_OK(Integer id) {
        return "------paymentFallbackService  fall back";
    }

    @Override
    public String paymentInfo_Timeout(Integer id) {
        return "------paymentFallbackService  fall back";
    }
}

