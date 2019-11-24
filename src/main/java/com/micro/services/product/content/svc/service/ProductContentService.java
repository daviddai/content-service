package com.micro.services.product.content.svc.service;

import com.micro.services.product.content.svc.model.ProductApiModel;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ProductContentService {

    private Set<ProductApiModel> productApiModels = new HashSet<>();

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
