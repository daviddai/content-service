package com.micro.services.product.content.svc.model;

public class ProductApiModel {

    private String productCode;
    private String productName;
    private String productDescription;

    public ProductApiModel() {}

    public ProductApiModel(String productCode, String productName, String productDescription) {
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

    public static class Builder {

        private ProductApiModel productApiModel;

        public Builder() {
            productApiModel = new ProductApiModel();
        }

        public Builder withProductCode(String productCode) {
            productApiModel.productCode = productCode;
            return this;
        }

        public Builder withProductName(String productName) {
            productApiModel.productName = productName;
            return this;
        }

        public Builder withProductDescription(String productDescription) {
            productApiModel.productDescription = productDescription;
            return this;
        }

        public ProductApiModel build() {
            return productApiModel;
        }
    }
}
