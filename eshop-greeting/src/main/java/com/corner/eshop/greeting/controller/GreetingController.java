package com.corner.eshop.greeting.controller;

import com.corner.eshop.greeting.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @Autowired
    private GreetingService greetingService;

    @GetMapping
    public String greeting(String name){
        return greetingService.greeting(name);
    }
}
