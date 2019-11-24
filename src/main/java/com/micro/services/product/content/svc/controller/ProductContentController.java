package com.micro.services.product.content.svc.controller;

import com.micro.services.product.content.svc.model.ProductApiModel;
import com.micro.services.product.content.svc.service.ProductContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/product-content")
public class ProductContentController {

    @Autowired
    private ProductContentService productContentService;

    @GetMapping(value = "/{productCode}")
    public ProductApiModel getProduct(@PathVariable("productCode") String productCode) {
        return productContentService.getProductContentByProductCode(productCode);
    }

}
