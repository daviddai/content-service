package com.micro.services.product.content.svc.service;

import com.micro.services.event.bus.event.ProductCreated;
import com.micro.services.event.bus.event.model.ProductContent;
import com.micro.services.event.bus.subscriber.annotation.EventSubscriber;
import com.micro.services.product.content.svc.dao.ProductDao;
import com.micro.services.product.content.svc.dao.model.Product;
import com.micro.services.product.content.svc.model.ProductApiModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductDao productDao;

    @EventSubscriber
    @CachePut(value = "products", key = "#productCreatedEvent.productContent.productCode")
    public void addProduct(ProductCreated productCreatedEvent) {
        ProductContent productContent = productCreatedEvent.getProductContent();
        Product product = constructProduct(productContent);
        productDao.save(product);
    }

    @Cacheable(value = "products", key = "#productCode")
    public ProductApiModel getByProductCode(String productCode) {
        Product product = productDao.find(productCode);

        if (product != null) {
            return mapFrom(product);
        } else {
            throw new RuntimeException("product code " + productCode + " does not exist");
        }
    }

    public List<ProductApiModel> getAll() {
        return productDao.getAll()
                .stream()
                .map(this::mapFrom)
                .collect(Collectors.toList());
    }

    private Product constructProduct(ProductContent productContent) {
        return new Product(productContent.getProductCode(),
                productContent.getProductName(), productContent.getProductDescription());
    }

    private ProductApiModel mapFrom(Product product) {
        return new ProductApiModel.Builder()
                .withProductCode(product.getProductCode())
                .withProductName(product.getProductName())
                .withProductDescription(product.getProductDescription())
                .build();
    }
}
