package com.micro.services.product.content.svc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventSubscriber {

    @Autowired
    private ProductContentService productContentService;

    public void received(String message) {
        System.out.println("Received message: [" + message + "]");
        productContentService.addProduct(message);
    }

}
