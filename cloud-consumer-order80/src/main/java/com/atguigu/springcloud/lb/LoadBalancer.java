package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * @author dyw
 * @date 2022-03-18  21:47
 */
public interface LoadBalancer {
    public ServiceInstance instance(List<ServiceInstance> serviceInstanceList);
}
