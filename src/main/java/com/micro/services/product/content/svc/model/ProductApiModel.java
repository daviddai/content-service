package com.micro.services.product.content.svc.model;

public class ProductApiModel {

    private String productCode;
    private String productDescription;

    public ProductApiModel(String productCode, String productDescription) {
        this.productCode = productCode;
        this.productDescription = productDescription;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getProductDescription() {
        return productDescription;
    }
}
