package com.atguigu.springcloud.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HellowController {

    @RequestMapping(value = "/home/{home}",method = RequestMethod.GET)
    public String home(@PathVariable("home") String home){
        return home;
    }
}
