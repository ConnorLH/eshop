package com.corner.eshop.greeting.service.impl;

import com.corner.eshop.greeting.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GreetingServiceImpl implements GreetingService {

    @Autowired
    private RestTemplate restTemplate;

    public String greeting(String name) {
        return null;
    }
}
