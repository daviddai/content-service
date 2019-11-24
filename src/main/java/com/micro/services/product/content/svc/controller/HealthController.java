package com.micro.services.product.content.svc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/health")
public class HealthController {

    @GetMapping(value = "/ping")
    public String ping() {
        return "pong";
    }

}
