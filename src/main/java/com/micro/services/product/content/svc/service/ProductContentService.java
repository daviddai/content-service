package com.micro.services.product.content.svc.service;

import com.micro.services.product.content.svc.event.EventSubscriber;
import com.micro.services.product.content.svc.model.ProductApiModel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ProductContentService {

    private Set<ProductApiModel> productApiModels = new HashSet<>();

//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = "product-content"),
//            exchange = @Exchange(value = "supplierExchange", type = ExchangeTypes.TOPIC),
//            key = {"supplier.createProduct"}
//    ))
    @EventSubscriber
    public void addProduct(String productCode) {
        productApiModels.add(new ProductApiModel(productCode, ""));
    }

    public ProductApiModel getProductContentByProductCode(String productCode) {
        return productApiModels.stream()
                .filter(productApiModel -> productApiModel.getProductCode().equals(productCode))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("product code " + productCode + " does not exist"));
    }

}
