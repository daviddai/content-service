package com.micro.services.product.content.svc.service;

import com.micro.services.event.bus.event.ProductCreated;
import com.micro.services.event.bus.event.model.ProductContent;
import com.micro.services.event.bus.subscriber.annotation.EventSubscriber;
import com.micro.services.product.content.svc.dao.ProductDao;
import com.micro.services.product.content.svc.dao.model.Product;
import com.micro.services.product.content.svc.exception.ContentServiceException;
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
    public void addProduct(ProductCreated productCreatedEvent) {
        ProductContent productContent = productCreatedEvent.getProductContent();
        Product product = constructProduct(productContent);
        addProduct(product);
    }

    @Cacheable(value = "products", key = "#productCode")
    public ProductApiModel getByProductCode(String productCode) throws ContentServiceException {
        Product product = productDao
                .find(productCode)
                .orElseThrow(() -> new ContentServiceException(ContentServiceException.ErrorCode.PRODUCT_DOES_NOT_EXIST));

        return mapFrom(product);
    }

    public List<ProductApiModel> getAll() {
        return productDao.getAll()
                .stream()
                .map(this::mapFrom)
                .collect(Collectors.toList());
    }

    @CachePut(value = "products", key = "#product.productCode")
    public Product addProduct(Product product) {
        productDao.save(product);
        return product;
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
