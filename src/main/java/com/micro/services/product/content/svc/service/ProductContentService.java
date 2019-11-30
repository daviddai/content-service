package com.micro.services.product.content.svc.service;

import com.micro.services.event.bus.event.ProductCreated;
import com.micro.services.event.bus.event.model.ProductContent;
import com.micro.services.event.bus.subscriber.annotation.EventSubscriber;
import com.micro.services.product.content.svc.model.ProductApiModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductContentService {

    private Set<ProductApiModel> productApiModels = new HashSet<>();

    @EventSubscriber
    public void addProduct(ProductCreated productCreatedEvent) {
        ProductContent productContent = productCreatedEvent.getProductContent();

        productApiModels.add(
                new ProductApiModel.Builder()
                        .withProductCode(productContent.getProductCode())
                        .withProductName(productContent.getProductName())
                        .withProductDescription(productContent.getProductDescription())
                        .build()
        );
    }

    public List<ProductApiModel> getAll() {
        return new ArrayList<>(productApiModels);
    }

    public ProductApiModel getByProductCode(String productCode) {
        return productApiModels.stream()
                .filter(productApiModel -> productApiModel.getProductCode().equals(productCode))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("product code " + productCode + " does not exist"));
    }

}
