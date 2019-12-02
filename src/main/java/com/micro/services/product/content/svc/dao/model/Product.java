package com.micro.services.product.content.svc.dao.model;

public class Product {

    private String productCode;
    private String productName;
    private String productDescription;

    public Product(String productCode, String productName, String productDescription) {
        this.productCode = productCode;
        this.productName = productName;
        this.productDescription = productDescription;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }
}
