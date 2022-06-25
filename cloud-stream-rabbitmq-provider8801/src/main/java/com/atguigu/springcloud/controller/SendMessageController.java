package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.IMessageProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author dyw
 * @date 2022-03-27  18:14
 */
@RestController
@Slf4j
public class SendMessageController {
    @Resource
    private IMessageProvider iMessageProvider;
    @GetMapping("/send")
    public String sendMessage(){
        return iMessageProvider.send();
    }
}
